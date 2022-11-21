import com.intellij.openapi.wm.ToolWindow
import fadyat.github.com.awesomeplugin.wrappers.RequestWrapper
import java.awt.BorderLayout
import javax.swing.JPanel

class RequestWindow(private val toolWindow: ToolWindow) {
    fun init() {
        val content = toolWindow.contentManager.factory.createContent(
            JPanel(BorderLayout()), "", false
        )
        val requestWrapper = RequestWrapper()
        content.component.add(requestWrapper.contentPane, BorderLayout.CENTER)
        toolWindow.contentManager.addContent(content)
    }
}