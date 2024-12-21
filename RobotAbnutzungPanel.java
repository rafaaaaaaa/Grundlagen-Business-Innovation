import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;
import java.awt.*;

public class RobotAbnutzungPanel extends JPanel {

    private Fabrik fabrik;          // We need access to the Fabrik to get the Produktions_Manager
    private DefaultCategoryDataset dataset;
    private JFreeChart chart;

    public RobotAbnutzungPanel(Fabrik fabrik) {
        this.fabrik = fabrik;
        setLayout(new BorderLayout());

        // Create dataset and chart only once
        dataset = new DefaultCategoryDataset();
        // Create a vertical Bar Chart
        chart = ChartFactory.createBarChart(
                "Abnutzung der Roboter",   // Chart title
                "Roboter",                // Domain axis label
                "Abnutzung (%)",          // Range axis label
                dataset,
                PlotOrientation.VERTICAL,
                false,  // include legend
                false,  // tooltips
                false   // URLs
        );
        
        // Adjust the title font
        chart.getTitle().setFont(new Font("SansSerif", Font.PLAIN, 15));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        add(chartPanel, BorderLayout.CENTER);

        // Initialize the chart with current data
        refreshChartData();
    }

    /**
     * Rebuild the dataset from the current number of products produced by each robot,
     * converting that to a percentage of wear (Abnutzung).
     */
    public void refreshChartData() {
        if (fabrik == null || fabrik.gibProduktionsManager() == null) {
            return;
        }

        // Grab the four robots
        Holzbearbeitungs_Roboter holzRoboter = fabrik.gibProduktionsManager().gibHolzRoboter();
        Montage_Roboter montageRoboter       = fabrik.gibProduktionsManager().gibMontageRoboter();
        Lackier_Roboter lackierRoboter       = fabrik.gibProduktionsManager().gibLackierRoboter();
        Verpackungs_Roboter verpackungsRoboter = fabrik.gibProduktionsManager().gibVerpackungsRoboter();

        // Get how many products each has produced
        int holzCount        = holzRoboter.gibTotalProduzierteProdukte();
        int montageCount     = montageRoboter.gibTotalProduzierteProdukte();
        int lackierCount     = lackierRoboter.gibTotalProduzierteProdukte();
        int verpackungsCount = verpackungsRoboter.gibTotalProduzierteProdukte();

        // Clear the old data
        dataset.clear();

        // Each robot can produce up to 1000 products -> 100% worn out
        double abnutzungHolz        = Math.min(100.0, (holzCount / (float) holzRoboter.gibTotalProducableUnits()) * 100.0);
        double abnutzungMontage     = Math.min(100.0, (montageCount / (float) montageRoboter.gibTotalProducableUnits()) * 100.0);
        double abnutzungLackierung  = Math.min(100.0, (lackierCount / (float) lackierRoboter.gibTotalProducableUnits()) * 100.0);
        double abnutzungVerpackung  = Math.min(100.0, (verpackungsCount / (float) verpackungsRoboter.gibTotalProducableUnits()) * 100.0);

        // Add values to the dataset (series "Abnutzung", categories are the robot types)
        dataset.addValue(abnutzungHolz,       "Abnutzung", "Holzbearbeitung");
        dataset.addValue(abnutzungMontage,    "Abnutzung", "Montage");
        dataset.addValue(abnutzungLackierung, "Abnutzung", "Lackierung");
        dataset.addValue(abnutzungVerpackung, "Abnutzung", "Verpackung");
    }
}
