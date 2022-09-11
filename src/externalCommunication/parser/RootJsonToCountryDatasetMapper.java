/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package externalCommunication.parser;

import java.util.ArrayList;
import java.util.Collection;
import model.Country;
import model.CountryData;
import model.CountryDataset;

/**
 *
 *   @ Ίνγκριντ Νικόλα - std095138@ac.eap.gr   
 *   @ Χρυσούλα Τζώτζη - std100289@ac.eap.gr
 *   @ Πασχάλης Εκλεμές - std094823@ac.eap.gr
 *   @ Τμήμα ΗΛΕ-44 2019-2020
 *   
 */
//κλάση για την μετατροπή των JsonObject σε CountryDataset(s) και CountryData(s)
public final class RootJsonToCountryDatasetMapper {

    public static Country Map(Collection<RootJson> rootJsonCollection, Country country) {
        
        ArrayList<CountryDataset> datasetArray = new ArrayList<>();


        for (RootJson jsonObject : rootJsonCollection) {
            CountryDataset returnedCountryDataset = new CountryDataset();
            returnedCountryDataset.setCountryCode(country);
            //Μετατροπή της ημερομηνίας μετρήσεων για να εμφανίζεται μόνο το έτος
            String startYear = jsonObject.getDataset().getStartDate().toString();
            startYear = startYear.substring(0, startYear.length() - 6);
            returnedCountryDataset.setStartYear(startYear);
            returnedCountryDataset.setName(jsonObject.getDataset().getName());
            //Μετατροπή της ημερομηνίας μετρήσεων για να εμφανίζεται μόνο το έτος
            String endYear = jsonObject.getDataset().getEndDate().toString();
            endYear = endYear.substring(0, endYear.length() - 6);
            returnedCountryDataset.setEndYear(endYear);
            returnedCountryDataset.setDescription(jsonObject.getDataset().getDescription());
            
            ArrayList<CountryData> dataArray = new ArrayList<>();
            
            for (int i = 0; i < jsonObject.getDataset().getData().size(); i++) {
                CountryData returnedCountryData = new CountryData();
                ArrayList nestedArray = (ArrayList) jsonObject.getDataset().getData().get(i);
                //Μετατροπή της ημερομηνίας μετρήσεων για να εμφανίζεται μόνο το έτος
                String dataYear = nestedArray.get(0).toString();
                dataYear = dataYear.substring(0, dataYear.length() - 6);
                returnedCountryData.setDataYear(dataYear);
                returnedCountryData.setValue(nestedArray.get(1).toString());
                
                dataArray.add(i, returnedCountryData);
                
            }
            returnedCountryDataset.setCountryDataCollection(dataArray);
            datasetArray.add(returnedCountryDataset);
            
        }
        country.setCountryDatasetCollection(datasetArray);
        
        return country;
    }
}
