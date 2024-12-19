import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private boolean clicked;
    private JTable table; // Reference to the JTable
    private Fabrik fabrik; // Reference to the Fabrik object
    private int row;

    public ButtonEditor(JCheckBox checkBox, JTable table, Fabrik fabrik) {
        super(checkBox);
        this.table = table; // Pass the table instance
        this.fabrik = fabrik; // Pass the Fabrik instance
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> performAction());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        String status = (String) table.getValueAt(row, 4); // Get the status column value
        button.setText("Bestellen");
        button.setEnabled("Versandbereit".equals(status)); // Enable button only if status is "Versandbereit"
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "Bestellung versenden";
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    private void performAction() { 
        int bestellungsNr = (int) table.getValueAt(row, 0); // Get BestellungsNr from the row
  // Process the Bestellung
            Bestellung bestellung = fabrik.bestellungVersenden(bestellungsNr); // Update the status of the Bestellung
            
           // Disable the button explicitly
            button.setEnabled(false);
      
    
}
}