/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package externalCommunication;

import model.Country;
import model.CountryDataset;
import javax.persistence.EntityManager;
import java.awt.HeadlessException;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import model.CountryData;
import model.CountryDataJpaController;
import model.CountryDatasetJpaController;
import model.CountryJpaController;
import model.exceptions.NonexistentEntityException;

/**
 *
 * @ Ίνγκριντ Νικόλα - std095138@ac.eap.gr
 * @ Χρυσούλα Τζώτζη - std100289@ac.eap.gr
 * @ Πασχάλης Εκλεμές - std094823@ac.eap.gr
 * @ Τμήμα ΗΛΕ-44 2019-2020
 *
 */
public final class DBController {

    // Δήλωση static για να χρησιμοποιηθεί ο ίδιος entity manager καθ' όλη τη διάρκεια της εκτέλεσης
    protected static EntityManager em;
    private static final String PERSISTENCE_UNIT_NAME = "econometricaPU";
    private static EntityManagerFactory emf;
    private static CountryJpaController countryEntity = new CountryJpaController(emf);
    private static CountryDatasetJpaController countryDatasetEntity = new CountryDatasetJpaController(emf);
    private static CountryDataJpaController countryDataEntity = new CountryDataJpaController(emf);

    public static EntityManager getEm() {
        return em;
    }

    public static void connect() {
        if (emf == null) {
            try {
                // Δημιουργία Entity Manager
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                // Σύνδεση με την Βάση Δεδομένων 
                em = emf.createEntityManager();
            } catch (Exception e) {
                // Σε αντίθετη περίπτωση, εμφανιζει μήνυμα λάθους
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Oops, something went wrong! :( \nThe connection with the database failed", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Μέθοδος που ελέγχει αν η επιλεγμένη χώρα υπάρχει στην DB και επιστρέφει μία boolean μεταβλητή
    public static boolean dbFinder(Country country) {
        connect();
        Boolean flag = false;
        String iso = "";
        
        countryEntity = new CountryJpaController(emf);
        // Ψάχνει στον πίνακα COUNTRY αν υπάρχει εγγραφή με το ίδιο isoCode
        for (Country requestedCountry : countryEntity.findCountryEntities()) {
            countryEntity.findCountry(iso);
            if (country.getIsoCode().equals(requestedCountry.getIsoCode())) {
                // Αν το βρεί, επιστρέφει true
                flag = true;
            }
        }

        return flag;
    }

    // Μέθοδος που αποθηκεύει στην DB μία χώρα και τα δεδομένα της
    public static boolean dbSaver(Country country) throws Exception {
        Boolean flag = false;

        // Αποθηκεύει την χώρα στον πίνακα COUNTRY
        connect();
        countryEntity = new CountryJpaController(emf);
        Country countryDB = new Country();
        countryDB.setIsoCode(country.getIsoCode());
        countryDB.setName(country.getName());

        countryEntity.create(countryDB);
        // Για κάθε dataset, δημιουργεί αντικείμενα CountryDataset και τα αποθηκεύει στον πίνακα COUNTRY_DATASET
        for (CountryDataset countryDataset : country.getCountryDatasetCollection()) {
            connect();
            countryDatasetEntity = new CountryDatasetJpaController(emf);
            CountryDataset countryDatasetDB = new CountryDataset();
            countryDatasetDB.setCountryCode(country);
            countryDatasetDB.setStartYear(countryDataset.getStartYear());
            countryDatasetDB.setName(countryDataset.getName());
            countryDatasetDB.setEndYear(countryDataset.getEndYear());
            countryDatasetDB.setDescription(countryDataset.getDescription());
            countryDatasetEntity.create(countryDatasetDB);
            // Για κάθε data μέσα στο dataset, δημιουργεί αντικείμενα CountryData και τα αποθηκεύει στον πίνακα COUNTRY_DATA
            for (CountryData countryData : countryDataset.getCountryDataCollection()) {
                connect();
                countryDataEntity = new CountryDataJpaController(emf);
                CountryData countryDataDB = new CountryData();
                countryDataDB.setDataYear(countryData.getDataYear());
                countryDataDB.setValue(countryData.getValue());
                countryDataDB.setDataset(countryDatasetDB);
                countryDataEntity.create(countryDataDB);
            }
        }

        flag = true;

        return flag;
    }

    // Μέθοδος που ανακαλεί τα δεδομένα απο την DB (καλείται μόνο εφόσον η dbFinder επιστρέψει true)
    public static Country dbRetriever(Country country) {
        
        connect();
        countryDatasetEntity = new CountryDatasetJpaController(emf);
        CountryDataset countryDataset = new CountryDataset();

        ArrayList dbDatasets = new ArrayList();
        // Όλα τα dataset που αντιστοιχούν στην χώρα, τα εισάγει σε μία λίστα
        for (CountryDataset dbCountryDataset : countryDatasetEntity.findCountryDatasetEntities()) {
            if (dbCountryDataset.getCountryCode().equals(country)) {
                dbDatasets.add(dbCountryDataset);
            }
        }
        // Εισάγει την λίστα με τα datasets στην χώρα
        country.setCountryDatasetCollection(dbDatasets);

        return country;
    }

    // Μέθοδος που διαγράφει όλα τα στοιχεία των πινάκων
    public static void clrTable() {
        connect();
        
        try {
            // Επειδή υπάρχουν primary και secondary keys, η διαγραφή των πινάκων γίνεται μόνο με την συγκεκριμένη σειρά
            // Διαγραφή των στοιχείων του πίνακα COUNTRY_DATA
            countryDataEntity = new CountryDataJpaController(emf);
            for (CountryData countryData : countryDataEntity.findCountryDataEntities()) {
                countryDataEntity.destroy(countryData.getId());
            }
            // Διαγραφή των στοιχείων του πίνακα COUNTRY_DATASET
            countryDatasetEntity = new CountryDatasetJpaController(emf);
            for (CountryDataset countryDataset : countryDatasetEntity.findCountryDatasetEntities()) {
                countryDatasetEntity.destroy(countryDataset.getDatasetId());
            }
            // Διαγραφή των στοιχείων του πίνακα COUNTRY
            countryEntity = new CountryJpaController(emf);
            for (Country country : countryEntity.findCountryEntities()) {
                countryEntity.destroy(country.getIsoCode());
            }
            //Ενημερωτικό μύνημα
            JOptionPane.showMessageDialog(null, "Τα δεδομένα διαγράφηκαν επιτυχώς", "Διαγραφή δεδομένων", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException | NonexistentEntityException e) {
            // Σε περίπτωση λάθους εμφανίζει σχετικό μήνυμα
            JOptionPane.showMessageDialog(null, "Oops, something went wrong! :( \nThe database may not be empty", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

}
