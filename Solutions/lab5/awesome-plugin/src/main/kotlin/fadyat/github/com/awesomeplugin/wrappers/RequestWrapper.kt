package fadyat.github.com.awesomeplugin.wrappers

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.Label
import com.intellij.util.ui.GridBag
import fadyat.github.com.awesomeplugin.comboboxes.RequestMethodComboBox
import fadyat.github.com.awesomeplugin.helpers.HttpMethod
import java.awt.GridBagLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField


class RequestWrapper : DialogWrapper(true) {
    private val panel = JPanel(GridBagLayout())
    private val method = RequestMethodComboBox(HttpMethod.values().map { it.name })
    private val url = JTextField()

    init {
        init()
        title = "Request"
    }

    override fun createCenterPanel(): JComponent {
        val gb = GridBag().setDefaultInsets(0, 0, 0, 0)
            .setDefaultFill(GridBag.BOTH)
            .setDefaultAnchor(GridBag.WEST)
            .setDefaultWeightX(1.0)
            .setDefaultWeightY(1.0)

        return fillPanel(gb)
    }

    private fun fillPanel(gb: GridBag): JPanel {
        panel.add(Label("Request"), gb.nextLine().next())
        panel.add(method, gb.nextLine().next())
        panel.add(Label("URL"), gb.nextLine().next())
        panel.add(url, gb.nextLine().next())
        return panel
    }


    fun getMethod(): HttpMethod {
        return HttpMethod.valueOf(method.selectedItem?.toString()!!)
    }

    fun getUrl(): String {
        return url.text
    }
}