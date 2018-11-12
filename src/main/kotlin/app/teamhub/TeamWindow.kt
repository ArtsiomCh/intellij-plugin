package app.teamhub

import app.teamhub.ProjectModel
import app.teamhub.TeamWindowModel
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowAnchor
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.treeStructure.SimpleNode
import com.intellij.ui.treeStructure.SimpleTree
import com.intellij.ui.treeStructure.SimpleTreeBuilder
import com.intellij.ui.treeStructure.SimpleTreeStructure
import icons.Icons
import icons.withIndicator
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class TeamWindow(project: Project, parentModel: ProjectModel) {

    private val model = TeamWindowModel(parentModel)

    private val windowId = "Team"

    private val manager = ToolWindowManager.getInstance(project)

    private val window = manager.registerToolWindow(
            windowId,
            false,
            ToolWindowAnchor.LEFT,
            project,
            true,
            true
    )

    private val tree = SimpleTree()

    private var children = emptyList<UserNode>()

    private val builder = SimpleTreeBuilder(tree, tree.builderModel, SimpleTreeStructure.Impl(object : SimpleNode(project) {
        override fun getChildren() = this@TeamWindow.children.toTypedArray()
    }), null)

    init {
        window.icon = Icons.TEAM_TOOL_WINDOW
        window.contentManager.addContent(window.contentManager.factory.createContent(
                tree,
                null,
                true
        ))
        tree.isRootVisible = false
    }

    init {
        model.closeWindow.invokeOnCompletion {
            manager.unregisterToolWindow(windowId)
        }
        model.launch {
            model.indicateOnline.openSubscription().consumeEach {
                window.icon = if (!it) Icons.TEAM_TOOL_WINDOW else
                    withIndicator(Icons.TEAM_TOOL_WINDOW, true)
            }
        }
        model.launch {
            model.userNodes.openSubscription().consumeEach {
                children = it.map { model -> UserNode(model, builder) }
                builder.queueUpdate(true)
            }
        }
    }
}