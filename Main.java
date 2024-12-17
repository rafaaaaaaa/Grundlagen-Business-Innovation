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
        frame.setSize(800, 700);
        frame.setMinimumSize(new Dimension(800, 700));
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
                } else if (column == 4) { // Status column
                    return new StatusRenderer();
                } else {
                    return super.getCellRenderer(row, column);
                }
            }
        };
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(800, 400)); // Increased minimum height

        JLabel tableLabel = new JLabel("Bestellungen", SwingConstants.LEFT);
        tableLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(tableLabel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Lagerbestand table
        JLabel lagerLabel = new JLabel("Lagerbestand", SwingConstants.LEFT);
        lagerLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        String[] lagerColumns = {"Produktart", "Lagerbestand"};
        DefaultTableModel lagerModel = new DefaultTableModel(lagerColumns, 0);
        JTable lagerTable = new JTable(lagerModel) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 1) {
                    return new ProgressBarRenderer();
                } else {
                    return super.getCellRenderer(row, column);
                }
            }
        };
        JScrollPane lagerScrollPane = new JScrollPane(lagerTable);
        lagerScrollPane.setPreferredSize(new Dimension(800, 150));

        JPanel lagerPanel = new JPanel(new BorderLayout());
        lagerPanel.add(lagerLabel, BorderLayout.NORTH);
        lagerPanel.add(lagerScrollPane, BorderLayout.CENTER);

        // Nachbestellen Button and Progress Bar
        JButton refillButton = new JButton("Lager Nachbestellen");
        refillButton.setPreferredSize(new Dimension(200, 30));
        JProgressBar refillProgressBar = new JProgressBar(0, 100);
        refillProgressBar.setStringPainted(true);

        refillButton.addActionListener(e -> {
    refillProgressBar.setValue(0);

    // Parallel auszuführender Thread für Lagerauffüllung
    Thread lagerThread = new Thread(() -> fabrik.gibLager().lagerAuffuellen());

    // SwingWorker für die ProgressBar-Aktualisierung
    SwingWorker<Void, Integer> worker = new SwingWorker<>() {
        @Override
        protected Void doInBackground() throws Exception {
            lagerThread.start(); // Starte den Lagerauffüllungs-Thread
            for (int i = 1; i <= 100; i++) {
                Thread.sleep(480); // Simuliert 48 Sekunden Nachbestellung
                publish(i);
            }
            lagerThread.join(); // Warte, bis der Lagerauffüllungs-Thread abgeschlossen ist
            return null;
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            refillProgressBar.setValue(chunks.get(chunks.size() - 1)); // Update ProgressBar
        }

        @Override
        protected void done() {
            SwingUtilities.invokeLater(() -> {
                updateLagerbestand(lagerModel, fabrik); // Aktualisiere Lagerbestand
                refillProgressBar.setValue(0); // Reset Fortschrittsbalken
            });
        }
    };
    worker.execute();
});



        JPanel refillPanel = new JPanel();
        refillPanel.add(refillButton);
        refillPanel.add(refillProgressBar);
        lagerPanel.add(refillPanel, BorderLayout.SOUTH);

        // Button action listener
        orderButton.addActionListener(e -> {
            try {
                int standardTueren = Integer.parseInt(textField1.getText());
                int premiumTueren = Integer.parseInt(textField2.getText());

                fabrik.bestellungAufgeben(standardTueren, premiumTueren);
                updateTable(tableModel, fabrik);
                updateLagerbestand(lagerModel, fabrik);

                textField1.setText("");
                textField2.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Bitte gültige Zahlen eingeben!", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        Timer timer = new Timer(1000, e -> {
            updateTable(tableModel, fabrik);
            updateLagerbestand(lagerModel, fabrik);
        });
        timer.start();

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(lagerPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void updateTable(DefaultTableModel tableModel, Fabrik fabrik) {
        tableModel.setRowCount(0);
        for (Bestellung bestellung : fabrik.gibBestellungen()) {
            String status = bestellung.gibAlleProdukteProduziert() ? "Versandbereit" :
                    bestellung.gibBestellteProdukte().stream().anyMatch(p -> p.aktuellerZustand() == 1) ? "In Produktion" : "Bestellt";
            int progress = (int) (bestellung.gibBestellteProdukte().stream().filter(p -> p.aktuellerZustand() == 2).count() * 100.0 / bestellung.gibBestellteProdukte().size());
            tableModel.addRow(new Object[]{
                    bestellung.gibBestellungsNr(),
                    bestellung.gibAnzahlStandardTueren(),
                    bestellung.gibAnzahlPremiumTueren(),
                    String.format("%.2f Tage", bestellung.gibLieferzeit()),
                    status,
                    progress
            });
        }
    }

    private static void updateLagerbestand(DefaultTableModel lagerModel, Fabrik fabrik) {
        lagerModel.setRowCount(0);
        Map<String, Integer> lagerBestand = fabrik.gibLager().gibLagerBestand();
        for (Map.Entry<String, Integer> entry : lagerBestand.entrySet()) {
            int maxBestand = switch (entry.getKey()) {
                case "Holzeinheiten", "Farbeinheiten", "Kartoneinheiten" -> 1000;
                case "Schrauben" -> 5000;
                case "Glaseinheiten" -> 100;
                default -> 1000;
            };
            int progress = (int) (entry.getValue() * 100.0 / maxBestand);
            lagerModel.addRow(new Object[]{entry.getKey(), progress});
        }
    }

    static class ProgressBarRenderer extends JProgressBar implements TableCellRenderer {
        public ProgressBarRenderer() {
            setStringPainted(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setValue((int) value);
            setString(value + "%");
            return this;
        }
    }

    static class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if ("Versandbereit".equals(value)) {
                c.setForeground(new Color(0, 100, 0));
                c.setFont(c.getFont().deriveFont(Font.BOLD));
            } else {
                c.setForeground(Color.BLACK);
                c.setFont(c.getFont().deriveFont(Font.PLAIN));
            }
            return c;
        }
    }
}