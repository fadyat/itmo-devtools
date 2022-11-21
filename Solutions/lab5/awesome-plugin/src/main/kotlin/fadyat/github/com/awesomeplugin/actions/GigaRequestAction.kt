package fadyat.github.com.awesomeplugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import fadyat.github.com.awesomeplugin.wrappers.RequestWrapper

class GigaRequestAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val requestWrapper = RequestWrapper()
        requestWrapper.show()
    }
}

