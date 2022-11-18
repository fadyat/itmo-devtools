package fadyat.github.com.awesomeplugin.comboboxes

import com.intellij.openapi.ui.ComboBox

class RequestMethodComboBox(methods: List<String>) : ComboBox<String>(methods.toTypedArray()) {
    init {
        this.isEditable = false
        this.selectedItem = methods[0]
    }
}