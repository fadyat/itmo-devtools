package fadyat.github.com.awesomeplugin.actions

import com.google.gson.GsonBuilder
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import fadyat.github.com.awesomeplugin.helpers.doRequest
import fadyat.github.com.awesomeplugin.wrappers.RequestWrapper
import java.io.IOException
import java.lang.IllegalArgumentException
import java.net.http.HttpResponse

class GigaRequestAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val requestWrapper = RequestWrapper()
        if (requestWrapper.showAndGet()) {
            lateinit var response: HttpResponse<String>
            try {
                response = doRequest(
                    requestWrapper.getMethod(),
                    requestWrapper.getUrl(),
                    requestWrapper.getHeaders(),
                    requestWrapper.getBody(),
                )
            } catch (ex: IOException) {
                displayErrorMessage(e.project, ex.message)
                return
            } catch (ex: InterruptedException) {
                displayErrorMessage(e.project, ex.message)
                return
            } catch (ex: IllegalArgumentException) {
                displayErrorMessage(e.project, ex.message)
                return
            }

            val gson = GsonBuilder().setPrettyPrinting().create()
            val prettyJson = gson.toJson(gson.fromJson(response.body(), Any::class.java))
            displaySuccessMessage(e.project, "Status code: ${response.statusCode()}\n\n$prettyJson")
        }
    }

    private fun displaySuccessMessage(project: Project?, message: String) {
        Messages.showMessageDialog(project, message, "Success", Messages.getInformationIcon())
    }

    private fun displayErrorMessage(project: Project?, message: String?) {
        Messages.showMessageDialog(project, message, "Error", Messages.getErrorIcon())
    }
}

