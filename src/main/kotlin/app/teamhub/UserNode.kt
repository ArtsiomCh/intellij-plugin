package app.teamhub

import app.teamhub.UserNodeModel
import app.teamhub.WorkingCopy
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode
import com.intellij.ui.treeStructure.SimpleTreeBuilder
import icons.Icons
import icons.fromUrl
import icons.toSize
import icons.withIndicator
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.zip
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

class UserNode(
        private val model: UserNodeModel,
        private val builder: SimpleTreeBuilder
) : SimpleNode(builder.rootElement as SimpleNode) {

    private var children = emptyList<WorkingCopyNode>()

    private var title = ""
    private var iconUrl = ""
    private var indicateOnline = false

    init {
//        model.launch {
//            while(isActive) {
//                select<Any> {
//                    model.title.openSubscription().onReceive
//                    model.iconUrl.openSubscription().onReceive
//                    model.indicateOnline.openSubscription().onReceive
//                }
//                builder.queueUpdateFrom(this, false, false)
//              }
//          }
        model.launch {
            model.title.openSubscription().consumeEach {
                title = it
                builder.queueUpdateFrom(this@UserNode, false, false)
            }
        }
        model.launch {
            model.iconUrl.openSubscription().consumeEach {
                iconUrl = it
                builder.queueUpdateFrom(this@UserNode, false, false)
            }
        }
        model.launch {
            model.indicateOnline.openSubscription().consumeEach {
                indicateOnline = it
                builder.queueUpdateFrom(this@UserNode, false, false)
            }
        }
        model.launch {
            model.workingCopyNodes.openSubscription().consumeEach {
                children = it.map { model -> WorkingCopyNode(model, builder, this@UserNode) }
                builder.queueUpdateFrom(this@UserNode, false, true)
            }
        }
    }

    override fun getChildren(): Array<SimpleNode> {
        return children.toTypedArray()
    }

    override fun isAutoExpandNode(): Boolean {
        return true
    }

    override fun update(presentation: PresentationData) {
        presentation.presentableText = title
        val iconUrl = iconUrl
        presentation.setIcon(withIndicator(when (iconUrl) {
            null -> Icons.TEAM_USER_AVATAR
            else -> toSize(fromUrl(iconUrl, builder.tree), builder.tree, 16)
        }, indicateOnline))
    }
}