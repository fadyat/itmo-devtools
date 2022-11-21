package fadyat.github.com.awesomeplugin.windows

import RequestWindow
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import fadyat.github.com.awesomeplugin.wrappers.RequestWrapper
import java.awt.BorderLayout
import javax.swing.JPanel

class RequestWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val window = RequestWindow(toolWindow)
        window.init()
    }
}



