package icons

import com.intellij.ui.Gray
import com.intellij.ui.JBColor
import com.intellij.ui.LayeredIcon
import com.intellij.util.IconUtil
import com.intellij.util.ui.JBUI
import javax.imageio.ImageIO
import javax.swing.*
import java.awt.*
import java.io.IOException
import java.net.URL

import com.intellij.execution.runners.ExecutionUtil.*
import com.intellij.util.IconUtil.*
import com.intellij.openapi.util.IconLoader.*

interface Icons {
    companion object {

        @JvmStatic
        val ICON_COLOR: Color = JBColor(Gray._110, Color(175, 177, 179))

        @JvmStatic
        val TEAM_TOOL_WINDOW = colorize(getIcon("/conference-13.png"), ICON_COLOR)
        @JvmStatic
        val CHAT_TOOL_WINDOW = colorize(getIcon("/chat-4-13.png"), ICON_COLOR)

        @JvmStatic
        val CHAT_TOOL_TAB = getIcon("/speech-bubble-13.png")
        @JvmStatic
        val TEAM_USER_AVATAR = getIcon("/user-2-16.png")
        @JvmStatic
        val GITHUB_REPOSITORY_AVATAR = getIcon("GitHub-Mark-16px.png")

    }
}

fun withBadge(base: Icon, badgeValue: Int): Icon {
    if (badgeValue == 0) return colorize(base, Icons.ICON_COLOR)
    val icon = LayeredIcon(2)
    icon.setIcon(base, 0)
    val badge = textToIcon(badgeValue.toString(), JLabel(), JBUI.scale(7f))
    icon.setIcon(badge, 1, SwingConstants.SOUTH_EAST)
    return icon
}

fun withIndicator(icon: Icon, online: Boolean): Icon {
    return if (!online) colorize(icon, Icons.ICON_COLOR) else getIndicator(icon, icon.getIconWidth(), icon.getIconHeight(), Color.GREEN)
}

fun toSize(icon: Icon, component: JComponent, size: Int): Icon {
    val iconSize = Math.max(icon.getIconWidth(), icon.getIconHeight())
    val sized = IconUtil.toSize(icon, iconSize, iconSize)
    val scaled = scale(sized, component, size.toFloat() / iconSize)
    return scaled
}

@Throws(IOException::class)
@JvmOverloads
fun fromUrl(url: String, component: JComponent, size: Int = 16): Icon {
    return toSize(ImageIcon(ImageIO.read(URL(url))), component, size)
}
