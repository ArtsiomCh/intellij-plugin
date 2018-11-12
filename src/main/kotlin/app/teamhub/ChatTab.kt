//package app.teamhub
//
//import com.intellij.openapi.wm.ToolWindow.SHOW_CONTENT_ICON
//import com.intellij.ui.content.ContentManagerEvent
//import com.intellij.ui.content.ContentManagerListener
//import com.intellij.util.ui.components.BorderLayoutPanel
//import icons.Icons
//import icons.withBadge
//
//actual abstract class ChatTab actual constructor(title: String, list: MessageList, input: MessageInput, chatWindow: ChatWindow, context: Context): ContentManagerListener {
//
//    private val panel = BorderLayoutPanel().addToCenter(list.component).addToBottom(input.component)
//
//    private val content = chatWindow.contentManager.factory.createContent(panel, title, true)
//
//    init {
//        content.icon = withBadge(Icons.CHAT_TOOL_TAB, if (title == "nbransby/untitled") 3 else 0)
//        content.putUserData(SHOW_CONTENT_ICON, true)
//        chatWindow.contentManager.addContent(content)
//        @Suppress("LeakingThis")
//        chatWindow.contentManager.addContentManagerListener(this)
//    }
//
//    protected actual abstract fun handleTabClosed()
//
//    protected actual var shown = content.manager.isSelected(content)
//
//    protected actual fun show() {
//        content.manager.setSelectedContent(content)
//    }
//
//    protected actual open fun close() {
//        content.manager.removeContent(content, true)
//    }
//
//    override fun contentAdded(event: ContentManagerEvent) {
//    }
//
//    override fun contentRemoveQuery(event: ContentManagerEvent) {
//    }
//
//    override fun selectionChanged(event: ContentManagerEvent) {
//    }
//
//    override fun contentRemoved(event: ContentManagerEvent) {
//        if(event.content == content) {
//            content.manager.removeContentManagerListener(this)
//            handleTabClosed()
//        }
//    }
//
//}