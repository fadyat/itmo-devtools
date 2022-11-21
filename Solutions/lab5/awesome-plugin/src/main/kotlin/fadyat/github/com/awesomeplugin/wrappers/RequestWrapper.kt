package fadyat.github.com.awesomeplugin.wrappers

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.Label
import com.intellij.util.ui.GridBag
import fadyat.github.com.awesomeplugin.comboboxes.RequestMethodComboBox
import fadyat.github.com.awesomeplugin.helpers.HttpMethod
import fadyat.github.com.awesomeplugin.helpers.doRequest
import fadyat.github.com.awesomeplugin.tables.*
import java.awt.GridBagLayout
import java.awt.Insets
import java.io.IOException
import java.lang.IllegalArgumentException
import java.net.http.HttpResponse
import javax.swing.*


class RequestWrapper : DialogWrapper(true) {
    private val panel = JPanel(GridBagLayout())
    private val method = RequestMethodComboBox(HttpMethod.values().map { it.name })
    private val url = JTextField()
    private val headers = HeadersTable()
    private val requestBody = JBTextArea()
    private val statusCode = JTextField()
    private val responseBody = JTextPane()
    private val scrollableResponsePane = JBScrollPane(responseBody)

    init {
        init()
        title = "Request-Ik"
        setOKButtonText("Send")
        requestBody.margin = Insets(5, 5, 5, 5)
        panel.preferredSize = panel.preferredSize.apply { width = 600 }
        panel.preferredSize = panel.preferredSize.apply { height = 400 }
        scrollableResponsePane.preferredSize = scrollableResponsePane.preferredSize.apply { height = 100 }
        responseBody.isEditable = false
        statusCode.isEditable = false
        cancelAction.isEnabled = false
    }

    override fun createCenterPanel(): JComponent {
        val gb = GridBag().setDefaultInsets(0, 0, 0, 0)
            .setDefaultFill(GridBag.BOTH)
            .setDefaultAnchor(GridBag.WEST)
            .setDefaultWeightX(10.0)
            .setDefaultWeightY(10.0)

        return fillPanel(gb)
    }

    private fun fillPanel(gb: GridBag): JPanel {
        panel.add(Label("Request"), gb.nextLine().next())
        panel.add(method, gb.nextLine().next())
        panel.add(Label("URL"), gb.nextLine().next())
        panel.add(url, gb.nextLine().next())
        panel.add(Label("Headers"), gb.nextLine().next())
        panel.add(headers, gb.nextLine().next())
        panel.add(Label("Body"), gb.nextLine().next())
        panel.add(requestBody, gb.nextLine().next())
        panel.add(Label("Status code"), gb.nextLine().next())
        panel.add(statusCode, gb.nextLine().next())
        panel.add(Label("Response"), gb.nextLine().next())
        panel.add(scrollableResponsePane, gb.nextLine().next())
        return panel
    }


    private fun getMethod(): HttpMethod {
        return HttpMethod.valueOf(method.selectedItem?.toString()!!)
    }

    private fun getUrl(): String {
        return url.text
    }

    private fun getHeaders(): Map<String, String> {
        val headers = mutableMapOf<String, String>()
        for (i in 0 until this.headers.rowCount) {
            val (header, value) = this.headers.getRowValues(i)
            if (header.isNotEmpty() && value.isNotEmpty()) {
                headers[header] = value
            }
        }
        return headers
    }

    private fun getBody(): String {
        return requestBody.text.trimIndent()
    }

    override fun doOKAction() {
        lateinit var response: HttpResponse<String>
        try {
            response = doRequest(getMethod(), getUrl(), getHeaders(), getBody())
        } catch (ex: IOException) {
            responseBody.text = ex.message
            return
        } catch (ex: InterruptedException) {
            responseBody.text = ex.message
            return
        } catch (ex: IllegalArgumentException) {
            responseBody.text = ex.message
            return
        }

        responseBody.text = response.body()
        statusCode.text = response.statusCode().toString()
    }
}