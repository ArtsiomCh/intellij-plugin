package app.teamhub

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vcs.VcsListener
import git4idea.repo.GitRepositoryManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import org.jetbrains.plugins.github.authentication.GithubAuthenticationManager
import org.jetbrains.plugins.github.util.GithubGitHelper
import org.jetbrains.plugins.github.util.GithubUrlUtil
import org.jetbrains.plugins.github.util.GithubUtil
import java.net.URL

class ProjectService(private val project: Project) : ProjectComponent {

    private val model by lazy {
        ProjectModel(GlobalScope.async {
            val account = GithubAuthenticationManager.getInstance().getSingleOrDefaultAccount(project)
            (account ?: GithubAuthenticationManager.getInstance().getAccounts().firstOrNull())!!.let { a ->
                val myId = a.javaClass.getDeclaredField("myId")
                myId.isAccessible = true
                val serviceName = generateServiceName(GithubUtil.SERVICE_DISPLAY_NAME, myId.get(a) as String)
                PasswordSafe.instance.get(CredentialAttributes(serviceName))!!.let {
                    it.getPasswordAsString()!!.let { token -> GitHubAccount(a.server.host, a.name, token) }
                }
            }
        })
    }

    override fun projectOpened() {
        model.launch {
            model.openTeamWindow.openSubscription().consumeEach { TeamWindow(project, model) }
        }
        project.messageBus.connect().subscribe(ProjectLevelVcsManager.VCS_CONFIGURATION_CHANGED, VcsListener {
            model.workingCopies = GitRepositoryManager.getInstance(project).repositories.fold(mutableSetOf()) { copies, repo ->
                GithubGitHelper.getInstance().getAccessibleRemoteUrls(repo).forEach { url ->
                    GithubUrlUtil.getUserAndRepositoryFromRemoteUrl(url)?.let {
                        copies.add(WorkingCopy(repo.root.path, URL(url).host, it.user, it.repository, repo.currentBranchName))
                    }
                }
                copies
            }
        })
    }

    override fun projectClosed() {
        model.close()
    }

}
