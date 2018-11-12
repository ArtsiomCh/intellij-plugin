//package app.teamhub
//
//import com.intellij.ui.components.JBList
//
//actual abstract class MessageList actual constructor(context: Context)  {
//
//    actual abstract val messages: List<MessageItem>
//
//    internal val component = JBList<MessageItem>()
//
//    protected actual fun reload() {
//        component.model = JBList.createDefaultListModel(messages)
//    }
//
//}