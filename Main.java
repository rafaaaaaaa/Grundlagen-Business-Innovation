import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Fabrik instanziieren
        Fabrik fabrik = new Fabrik();

        // JFrame erstellen
        JFrame frame = new JFrame("Aeki Manufacturing Portal");
        frame.setSize(800, 700);
        frame.setMinimumSize(new Dimension(800, 700));
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Oberes Label: Professioneller Titel
        JLabel titleLabel = new JLabel("AEKI Manufacturing Portal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setPreferredSize(new Dimension(800, 50));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Eingabe-Panel oben mit GridBagLayout für bessere Ausrichtung
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setPreferredSize(new Dimension(800, 150));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        JLabel label1 = new JLabel("Anzahl Standardtüren:");
        JTextField textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(150, 25));
        textField1.setText("0"); // Standardwert
        
        JLabel label2 = new JLabel("Anzahl Premiumtüren:");
        JTextField textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(150, 25));
        textField2.setText("0"); // Standardwert
        
        JButton orderButton = new JButton("Bestellen");
        orderButton.setPreferredSize(new Dimension(100, 30));
        
        // Erste Zeile: Label 1 und TextField 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(label1, gbc);
        
        gbc.gridx = 1;
        inputPanel.add(textField1, gbc);
        
        // Zweite Zeile: Label 2 und TextField 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(label2, gbc);
        
        gbc.gridx = 1;
        inputPanel.add(textField2, gbc);
        
        // Dritte Zeile: Bestell-Button zentriert
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(orderButton, gbc);

        // Wrapper-Panel, um inputPanel oben zu halten
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);

        // Tabelle zur Anzeige von Bestellungen
        String[] columnNames = {"BestellungsNr", "Anzahl Standardtüren", "Anzahl Premiumtüren", "Lieferzeit", "Bestellstatus", "Fortschritt", ""};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Nur die Button-Spalte ist editierbar
            }
        };
        JTable table = new JTable(tableModel) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 5) { // Fortschrittsspalte
                    return new ProgressBarRenderer();
                } else if (column == 4) { // Status-Spalte
                    return new StatusRenderer();
                } else if (column == 6) { // Button-Spalte
                    return new ButtonRenderer();
                } else {
                    return super.getCellRenderer(row, column);
                }
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 6) { // Button-Spalte
                    return new ButtonEditor(new JCheckBox(), this, fabrik); // Fabrik-Instanz hier übergeben
                } else {
                    return super.getCellEditor(row, column);
                }
            }
        };
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(800, 400)); // Erhöhte Mindesthöhe

        JLabel tableLabel = new JLabel("Bestellungen", SwingConstants.LEFT);
        tableLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(tableLabel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        
        // Panel für die Robot-Nutzungs-Pie-Chart
        RobotAbnutzungPanel robotAbnutzungPanel = new RobotAbnutzungPanel(fabrik);
        
        // Für das Layout könntest du es z.B. in einen neuen Tab oder an die Seite setzen
        // Zur Vereinfachung wird es hier einfach auf der EAST-Seite platziert:
        frame.add(robotAbnutzungPanel, BorderLayout.EAST);
        

        // Lagerbestandstabelle
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

        // Nachbestellen-Button und Fortschrittsbalken
        JButton refillButton = new JButton("Lager Nachbestellen");
        refillButton.setPreferredSize(new Dimension(200, 30));
        JProgressBar refillProgressBar = new JProgressBar(0, 100);
        refillProgressBar.setStringPainted(true);

        refillButton.addActionListener(e -> {
            refillProgressBar.setValue(0);

            // Parallel auszuführender Thread für Lagerauffüllung
            Thread lagerThread = new Thread(() -> fabrik.gibLager().lagerAuffuellen());

            // SwingWorker für die Fortschrittsbalken-Aktualisierung
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
                    refillProgressBar.setValue(chunks.get(chunks.size() - 1)); // Fortschrittsbalken aktualisieren
                }

                @Override
                protected void done() {
                    SwingUtilities.invokeLater(() -> {
                        updateLagerbestand(lagerModel, fabrik); // Lagerbestand aktualisieren
                        refillProgressBar.setValue(0); // Fortschrittsbalken zurücksetzen
                    });
                }
            };
            worker.execute();
        });

        JPanel refillPanel = new JPanel();
        refillPanel.add(refillButton);
        refillPanel.add(refillProgressBar);
        lagerPanel.add(refillPanel, BorderLayout.SOUTH);

        // ActionListener für den Bestell-Button
        orderButton.addActionListener(e -> {
            try {
                int standardTueren = Integer.parseInt(textField1.getText());
                int premiumTueren = Integer.parseInt(textField2.getText());

                // Bestellung in die Fabrik hinzufügen
                fabrik.bestellungAufgeben(standardTueren, premiumTueren);

                // Tabelle und Lagerbestand aktualisieren
                updateTable(tableModel, fabrik);
                updateLagerbestand(lagerModel, fabrik);

                // Eingabefelder zurücksetzen
                textField1.setText("0");
                textField2.setText("0");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Bitte gültige Zahlen eingeben!", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Timer zur regelmäßigen Aktualisierung
        Timer timer = new Timer(500, e -> {
            updateTable(tableModel, fabrik);
            updateLagerbestand(lagerModel, fabrik);
            robotAbnutzungPanel.refreshChartData();
            robotAbnutzungPanel.revalidate();
            robotAbnutzungPanel.repaint();
        });
        timer.start();

        // Panels zum JFrame hinzufügen
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(lagerPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Methode zur Aktualisierung der Bestelltabelle
    private static void updateTable(DefaultTableModel tableModel, Fabrik fabrik) {
        tableModel.setRowCount(0); // Tabelle leeren

        for (Bestellung bestellung : fabrik.gibBestellungen()) {
            // Bestellstatus basierend auf den Produktbedingungen bestimmen
            String status = bestellung.gibBestellteProdukte().stream().allMatch(p -> p.aktuellerZustand() == 3) ? "Ausgeliefert" :
                            bestellung.gibAlleProdukteProduziert() ? "Versandbereit" :
                            bestellung.gibBestellteProdukte().stream().anyMatch(p -> p.aktuellerZustand() == 1) ? "In Produktion" : "Bestellt";

            // Fortschrittsprozentsatz berechnen
            int progress = (int) (bestellung.gibBestellteProdukte().stream()
                    .filter(p -> p.aktuellerZustand() == 2 || p.aktuellerZustand() == 3)
                    .count() * 100.0 / bestellung.gibBestellteProdukte().size());

            // Eine Zeile zum Tabellenmodell hinzufügen
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

    // Methode zur Aktualisierung des Lagerbestands
    private static void updateLagerbestand(DefaultTableModel lagerModel, Fabrik fabrik) {
        lagerModel.setRowCount(0);
        Map<String, Integer> lagerBestand = fabrik.gibLager().gibLagerBestand();
        for (Map.Entry<String, Integer> entry : lagerBestand.entrySet()) {
            int currentStock = entry.getValue(); // Aktuellen Lagerbestand abrufen
            int maxBestand = switch (entry.getKey()) {
                case "Holzeinheiten", "Farbeinheiten", "Kartoneinheiten" -> 1000;
                case "Schrauben" -> 5000;
                case "Glaseinheiten" -> 100;
                default -> 1000;
            };

            int progress = (int) (currentStock * 100.0 / maxBestand); // Fortschrittsprozentsatz berechnen

            // PRODUKTART MIT BESTAND HINZUFÜGEN
            String produktartWithStock = entry.getKey() + " (" + currentStock + "/" + maxBestand + ")";

            // Zeile zum Tabellenmodell mit aktualisierter Produktart hinzufügen
            lagerModel.addRow(new Object[]{produktartWithStock, progress});
        }
    }

    // Renderer für Fortschrittsbalken in der Tabelle
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

    // Renderer für den Bestellstatus in der Tabelle
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
