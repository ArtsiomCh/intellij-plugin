package app.teamhub

import app.teamhub.UserNodeModel
import app.teamhub.WorkingCopyNodeModel
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode
import com.intellij.ui.treeStructure.SimpleTree
import com.intellij.ui.treeStructure.SimpleTreeBuilder
import icons.Icons
import icons.withIndicator
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import java.awt.event.InputEvent

class WorkingCopyNode(
        private val model: WorkingCopyNodeModel,
        private val builder: SimpleTreeBuilder,
        parent: SimpleNode
) : SimpleNode(parent) {

    private var title = ""
    private var subtitle: String? = null
    private var indicateOnline = false

    init {
//        model.launch {
//            while(isActive) {
//                select<Any> {
//                    model.title.openSubscription().onReceive
//                    model.subtitle.openSubscription().onReceive
//                    model.indicateOnline.openSubscription().onReceive
//                }
//                builder.queueUpdateFrom(this, false, false)
//        }
        model.launch {
            model.title.openSubscription().consumeEach {
                title = it
                builder.queueUpdateFrom(this@WorkingCopyNode, false, false)
            }
        }
        model.launch {
            model.subtitle.openSubscription().consumeEach {
                subtitle = it
                builder.queueUpdateFrom(this@WorkingCopyNode, false, false)
            }
        }
        model.launch {
            model.indicateOnline.openSubscription().consumeEach {
                indicateOnline = it
                builder.queueUpdateFrom(this@WorkingCopyNode, false, false)
            }
        }
    }

    override fun getChildren(): Array<SimpleNode> {
        return NO_CHILDREN
    }

    override fun isAlwaysLeaf(): Boolean {
        return true
    }

    override fun handleDoubleClickOrEnter(tree: SimpleTree?, inputEvent: InputEvent?) {
        model.handleSelection()
    }

    override fun update(presentation: PresentationData) {
        presentation.setIcon(withIndicator(Icons.GITHUB_REPOSITORY_AVATAR, indicateOnline))
        presentation.presentableText = title
        presentation.locationString = subtitle
    }
}