package fadyat.github.com.awesomeplugin.tables

import com.intellij.ui.table.JBTable
import javax.swing.table.DefaultTableModel


class HeadersTable : JBTable() {
    init {
        val model = DefaultTableModel()
        model.addColumn("Key")
        model.addColumn("Value")
        model.addRow(arrayOf("", ""))
        this.model = model



        model.addTableModelListener {
            if (this.isLastRowNotEmpty()) {
                model.addRow(arrayOf("", ""))
            } else if (this.isTwoLastRowsAreEmpty()) {
                model.removeRow(model.rowCount - 1)
            }
        }
    }

    private fun isLastRowNotEmpty(): Boolean {
        val model = this.model as DefaultTableModel

        val (header, value) = getRowValues(model.rowCount - 1)
        return header.isNotEmpty() || value.isNotEmpty()
    }

    private fun isTwoLastRowsAreEmpty(): Boolean {
        val model = this.model as DefaultTableModel

        if (model.rowCount < 2) {
            return false
        }

        for (i in 0..1) {
            val (header, value) = getRowValues(model.rowCount - 1 - i)
            if (header.isNotEmpty() || value.isNotEmpty()) {
                return false
            }
        }

        return true
    }

    fun getRowValues(row: Int): Pair<String, String> {
        val model = this.model as DefaultTableModel

        return model.getValueAt(row, 0).toString() to model.getValueAt(row, 1).toString()
    }
}

