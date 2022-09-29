/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import model.Country;
import model.CountryData;
import model.CountryDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;

/*
*
* αυτη η κλαση εγινε με βαση αυτη την ιστοσελιδα:
* http://zetcode.com/java/jfreechart/?fbclid=IwAR3SDN6HyVoVXsD8JxGUvntu65AY_435S0ctzWJlskIGP8Yt0h7A5AE3i14
* "A line chart with two series"
*/

public class LineChartEx2 extends JFrame {
    
    // Δημιουργία 2 Collections, ένα για κάθε Dataset
    private final XYSeriesCollection oilDataset = new XYSeriesCollection();
    private final XYSeriesCollection gdpDataset = new XYSeriesCollection();
    String title = new String();
    
    public void LineChartEx2(Country country) {
        // Τίτλος διαγράμματος
        title = "Economic Data for " + country.getName();
        // Δημιουργία των Dataset (δηλαδή της κάθε γραμμής του διαγράμματος)
        createDataset(country);
        initUI();
    }
    
    // Μέθοδος που δημιουργει το παράθυρο του διαγράμματος και καλει την μέθοδο createChart με όρισμα το dataset του Oil Consumption
    private void initUI() {

        XYDataset dataset = oilDataset;
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        
        add(chartPanel);

        pack();
        
        // Μορφοποίηση του παράθυρου
        //Τίτλος
        setTitle("Econometrics");
        // Εμφάνιση στο κέντρο της οθόνης
        setLocationRelativeTo(null);
        //Εικονίδιο - logo
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/visuals/icons8_profit_40px.png")));
        // Μέγεθος
        setSize(1200, 600);
    }
    
    // Μέθοδος που βάζει τα στοιχεία των Dataset στα αντίστοιχα Collections για την δημιουργία των γραμμών του διαγράμματος
    private void createDataset(Country country) {
        // Για το Oil Consumption
        XYSeries oilSeries = new XYSeries("Oil Consumption");
        for (CountryDataset countryDataset : country.getCountryDatasetCollection()) {
            if (countryDataset.getName().contains("Oil Consumption")) {
                for (CountryData countryData : countryDataset.getCountryDataCollection()) {
                    oilSeries.add(Integer.parseInt(countryData.getDataYear()), Double.parseDouble(countryData.getValue()));
                }
            }
        }
        oilDataset.addSeries(oilSeries);
        
        // Για το GDP
        XYSeries gdpSeries = new XYSeries("GDP");
        XYSeriesCollection gdpSeriesCollection = new XYSeriesCollection();
        for (CountryDataset countryDataset : country.getCountryDatasetCollection()) {
            if (countryDataset.getName().contains("GDP")) {
                for (CountryData countryData : countryDataset.getCountryDataCollection()) {
                    gdpSeries.add(Integer.parseInt(countryData.getDataYear()), Double.parseDouble(countryData.getValue()));
                }
            }
        }
        
        gdpDataset.addSeries(gdpSeries);
    }
    
    // Μέθοδος που δημιουργεί to διάγραμμα
    private JFreeChart createChart(final XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
        "XY Chart", // Title
        "Year", // x-axis Label
        "Oil Consumption", // y-axis Label
        dataset, // Dataset
        PlotOrientation.VERTICAL, // Plot Orientation
        true, // Show Legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
        );
        
        // Αντιπαραβολή των 2 dataset στο ίδιο παράθυρο
        XYPlot plot = chart.getXYPlot();
        
        // Δημιουργία δεξιάς στήλης
        final NumberAxis gdpAxis = new NumberAxis("GDP");
        final DecimalFormat gdpFormat = new DecimalFormat("#.#");
        gdpAxis.setNumberFormatOverride(gdpFormat);
        gdpAxis.setAutoRangeIncludesZero(false);
        plot.setRangeAxis(1, gdpAxis);
        plot.setDataset(1, gdpDataset);
        plot.mapDatasetToRangeAxis(1, 1);
        
        // Ορισμός του οριζόντιου άξονα
        final NumberAxis domainAxis = (NumberAxis)chart.getXYPlot().getDomainAxis();
        final DecimalFormat format = new DecimalFormat("####");
        domainAxis.setNumberFormatOverride(format);
        
        // Δημιουργία κατάλληλου renderer για την γραμμή Oil Consumption και μορφοποίηση της
        final StandardXYItemRenderer oilRenderer = new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES_AND_LINES);
        
        oilRenderer.setSeriesPaint(0, Color.MAGENTA);
        oilRenderer.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderer(oilRenderer);

        // Δημιουργία κατάλληλου renderer για την γραμμή GDP και μορφοποίηση της
        final XYLineAndShapeRenderer gdpRenderer = new XYLineAndShapeRenderer(true, true);

        gdpRenderer.setSeriesPaint(0, Color.GREEN);
        gdpRenderer.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderer(1, gdpRenderer);

        // Μορφοποίηση του background του διαγράμματος
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle(title,
                        new Font("Serif", Font.BOLD, 18)
                )
        );
        
        return chart;
    }
    
}
