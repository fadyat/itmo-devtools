package fadyat.github.com.awesomeplugin.tables

import com.intellij.ui.table.JBTable
import javax.swing.JTable

class HeaderTable() {
    private val table = JBTable()

    fun getTable(): JTable {
        return table
    }
}
