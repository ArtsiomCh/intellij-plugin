//package app.teamhub
//
//import com.intellij.openapi.project.Project
//import com.intellij.openapi.wm.ToolWindowAnchor
//import com.intellij.openapi.wm.ToolWindowManager
//import icons.Icons
//import icons.withBadge
//
//actual abstract class ChatWindow actual constructor(context: Context) {
//
//    private val project: Project = context.project
//
//    private val windowId = "Chat"
//
//    private val manager = ToolWindowManager.getInstance(project)
//
//    private val window = manager.registerToolWindow(
//            windowId,
//            true,
//            ToolWindowAnchor.BOTTOM,
//            project,
//            true,
//            true
//    )
//
//    internal val contentManager = window.contentManager
//
//    init {
//        window.icon = withBadge(Icons.CHAT_TOOL_WINDOW, 3)
//    }
//
//    protected actual var shown = window.isVisible
//
//    protected actual fun show() {
//        window.show(null)
//    }
//
//    protected actual fun close() {
//        manager.unregisterToolWindow(windowId)
//    }
//
//}