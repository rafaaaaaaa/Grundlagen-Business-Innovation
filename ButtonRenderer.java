import javax.swing.JButton;
import java.awt.Component;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;

// Custom Renderer for the Button Column
public class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setText("Bestellen");
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String status = (String) table.getValueAt(row, 4); // Get the status column value
        setEnabled("Versandbereit".equals(status)); // Enable button only if status is "Versandbereit"
        return this;
    }
}