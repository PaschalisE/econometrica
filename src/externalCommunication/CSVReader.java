/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package externalCommunication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @ Ίνγκριντ Νικόλα - std095138@ac.eap.gr
 * @ Χρυσούλα Τζώτζη - std100289@ac.eap.gr
 * @ Πασχάλης Εκλεμές - std094823@ac.eap.gr
 * @ Τμήμα ΗΛΕ-44 2019-2020
 *
 */
public class CSVReader {

    private static Map<String, String> countryCodeAndName = new LinkedHashMap<String, String>();

    public CSVReader() {
        // Όνομα αρχείου
        String fileName = "iso-countries.csv";
        File file = new File(fileName);

        try {
            // Διαβάζει το αρχείο
            Scanner inputStream = new Scanner(file);
            // Χωρίζει τα δαδομάνα του αρχείου αν βρει semicolon (;) ή νέα γραμή (\n)
            inputStream.useDelimiter("[;\n]");
            while (inputStream.hasNext()) {
                String country = inputStream.next();
                String alpha2 = inputStream.next();
                String alpha3 = inputStream.next();
                String number = inputStream.next();
                // Από αυτά κρατάει μόνο το όνομα της χώρας και το iso code (alpha3)
                // και τα καταχωρεί σε ένα map
                countryCodeAndName.put(country, alpha3);
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            // Αν δεν βρεθεί το αρχείο, βγαζει μήνυμα λάθους
            JOptionPane.showMessageDialog(null, "Που είναι το αρχείο, οεο;", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @return the countryCodeAndName
     */
    public static Map<String, String> getCountryCodeAndName() {
        return countryCodeAndName;
    }

    /**
     * @param aCountryCodeAndName the countryCodeAndName to set
     */
    public static void setCountryCodeAndName(Map<String, String> aCountryCodeAndName) {
        countryCodeAndName = aCountryCodeAndName;
    }

}
