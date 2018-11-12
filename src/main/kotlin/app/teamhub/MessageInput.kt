//package app.teamhub
//
//import com.intellij.openapi.actionSystem.AnAction
//import com.intellij.openapi.actionSystem.AnActionEvent
//import com.intellij.openapi.fileTypes.FileTypes
//import com.intellij.ui.EditorTextField
//import java.awt.event.ActionEvent
//import java.awt.event.ActionListener
//import java.awt.event.KeyEvent
//
//actual abstract class MessageInput actual constructor(placeholder: String, context: Context) : ActionListener {
//
//    internal val component = EditorTextField("", context.project, FileTypes.PLAIN_TEXT)
//
//    init {
//        component.setPlaceholder(placeholder)
//        component.ensureWillComputePreferredSize()
//        val action = object : AnAction() {
//            override fun actionPerformed(e: AnActionEvent) {
//                handleEnter(e.modifiers != 0)
//            }
//        }
//        action.registerCustomShortcutSet(KeyEvent.VK_ENTER, 0, component)
//    }
//
//    override fun actionPerformed(e: ActionEvent?) {
//    }
//
//    protected actual abstract fun handleEnter(isModifierKeyDown: Boolean): Boolean
//
//    protected actual var text: String
//        get() = component.text
//        set(value) { component.text = value }
//
//}