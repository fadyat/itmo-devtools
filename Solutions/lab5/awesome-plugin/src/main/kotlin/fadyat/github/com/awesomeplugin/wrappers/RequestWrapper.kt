package fadyat.github.com.awesomeplugin.wrappers

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.Label
import com.intellij.util.ui.GridBag
import fadyat.github.com.awesomeplugin.comboboxes.RequestMethodComboBox
import fadyat.github.com.awesomeplugin.helpers.HttpMethod
import fadyat.github.com.awesomeplugin.tables.*
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField


class RequestWrapper : DialogWrapper(true) {
    private val panel = JPanel(GridBagLayout())
    private val method = RequestMethodComboBox(HttpMethod.values().map { it.name })
    private val url = JTextField()
    private val table = HeadersTable()
    private val body = JBTextArea()

    init {
        init()
        title = "Aboba :)"
        setOKButtonText("Send")

        body.margin = Insets(5, 5, 5, 5)

        panel.preferredSize = panel.preferredSize.apply { width = 600 }
        panel.preferredSize = panel.preferredSize.apply { height = 400 }
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
        panel.add(table, gb.nextLine().next())
        panel.add(Label("Body"), gb.nextLine().next())
        panel.add(body, gb.nextLine().next())
        return panel
    }


    fun getMethod(): HttpMethod {
        return HttpMethod.valueOf(method.selectedItem?.toString()!!)
    }

    fun getUrl(): String {
        return url.text
    }

    fun getHeaders(): Map<String, String> {
        val headers = mutableMapOf<String, String>()
        for (i in 0 until table.rowCount) {
            val (header, value) = table.getRowValues(i)
            if (header.isNotEmpty() && value.isNotEmpty()) {
                headers[header] = value
            }
        }
        return headers
    }

    fun getBody(): String {
        return body.text.trimIndent()
    }
}