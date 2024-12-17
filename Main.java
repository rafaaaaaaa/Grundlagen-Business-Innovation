import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Instantiate Fabrik
        Fabrik fabrik = new Fabrik();

        // Create JFrame
        JFrame frame = new JFrame("Aeki Manufacturing Portal");
        frame.setSize(800, 550);
        frame.setMinimumSize(new Dimension(800, 550));
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Top label: Professional title
        JLabel titleLabel = new JLabel("AEKI Manufacturing Portal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setPreferredSize(new Dimension(800, 50));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Input panel at the top with GridBagLayout for better alignment
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setPreferredSize(new Dimension(800, 150));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel label1 = new JLabel("Anzahl Standardtüren:");
        JTextField textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(150, 25));

        JLabel label2 = new JLabel("Anzahl Premiumtüren:");
        JTextField textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(150, 25));

        JButton orderButton = new JButton("Bestellen");
        orderButton.setPreferredSize(new Dimension(100, 30));

        // First row: Label 1 and TextField 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(label1, gbc);

        gbc.gridx = 1;
        inputPanel.add(textField1, gbc);

        // Second row: Label 2 and TextField 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(label2, gbc);

        gbc.gridx = 1;
        inputPanel.add(textField2, gbc);

        // Third row: Order Button centered
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(orderButton, gbc);

        // Wrapper panel to stick inputPanel to the top
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);

        // Table to display orders
        String[] columnNames = {"BestellungsNr", "Anzahl Standardtüren", "Anzahl Premiumtüren", "Lieferzeit", "Bestellstatus", "Fortschritt"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 5) { // Last column for progress bars
                    return new ProgressBarRenderer();
                } else {
                    return super.getCellRenderer(row, column);
                }
            }
        };
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(800, 250));

        // Label above the table
        JLabel tableLabel = new JLabel("Bestellungen", SwingConstants.LEFT);
        tableLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tableLabel.setPreferredSize(new Dimension(800, 30));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(tableLabel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Button action listener
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get inputs and parse them as integers
                    int standardTueren = Integer.parseInt(textField1.getText());
                    int premiumTueren = Integer.parseInt(textField2.getText());

                    // Add order to Fabrik
                    fabrik.bestellungAufgeben(standardTueren, premiumTueren);

                    // Populate table with updated bestellungen
                    updateTable(tableModel, fabrik);

                    // Clear the text fields
                    textField1.setText("");
                    textField2.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Bitte gültige Zahlen eingeben!", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Eingabefehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Timer to periodically update the table progress
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable(tableModel, fabrik);
            }
        });
        timer.start();

        // Add components to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);

        // Set frame visibility
        frame.setVisible(true);
    }

    private static void updateTable(DefaultTableModel tableModel, Fabrik fabrik) {
        tableModel.setRowCount(0);
        for (Bestellung bestellung : fabrik.gibBestellungen()) {
            String status;
            if (bestellung.gibAlleProdukteProduziert()) {
                status = "Versandbereit";
            } else if (bestellung.gibBestellteProdukte().stream().anyMatch(p -> p.aktuellerZustand() == 1)) {
                status = "In Produktion";
            } else {
                status = "Bestellt";
            }
            int totalProducts = bestellung.gibBestellteProdukte().size();
            int completedProducts = (int) bestellung.gibBestellteProdukte().stream()
                    .filter(p -> p.aktuellerZustand() == 2).count();
            int progress = totalProducts == 0 ? 0 : (completedProducts * 100) / totalProducts;
            String lieferzeitFormatted = String.format("%.2f Tage", bestellung.gibLieferzeit());
            tableModel.addRow(new Object[]{
                    bestellung.gibBestellungsNr(),
                    bestellung.gibAnzahlStandardTueren(),
                    bestellung.gibAnzahlPremiumTueren(),
                    lieferzeitFormatted,
                    status,
                    progress
            });
        }
    }

    // Renderer for the progress bar in the table
    static class ProgressBarRenderer extends JProgressBar implements TableCellRenderer {
        public ProgressBarRenderer() {
            setStringPainted(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            int progress = (int) value;
            setValue(progress);
            setString(progress + "%");
            return this;
        }
    }
}
