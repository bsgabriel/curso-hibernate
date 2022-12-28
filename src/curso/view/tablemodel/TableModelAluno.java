package curso.view.tablemodel;

import javax.swing.table.DefaultTableModel;

public class TableModelAluno extends DefaultTableModel {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
