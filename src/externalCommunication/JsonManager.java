/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package externalCommunication;

import com.google.gson.Gson;
import model.Country;
import java.io.IOException;
import externalCommunication.parser.RootJson;
import java.util.ArrayList;
import java.util.Collection;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 *   @ Ίνγκριντ Νικόλα - std095138@ac.eap.gr   
 *   @ Χρυσούλα Τζώτζη - std100289@ac.eap.gr
 *   @ Πασχάλης Εκλεμές - std094823@ac.eap.gr
 *   @ Τμήμα ΗΛΕ-44 2019-2020
 *   
 */
public class JsonManager {
    
    // Strings με τα επιμέρους τμήματα του URL
    static final String MAIN_URL = "https://www.quandl.com/api/v3/datasets/";
    public static final String GDP_MIDDLE_URL = "WWDI/";
    public static final String GDP_TAIL_URL = "_NY_GDP_MKTP_CN.json?api_key=";
    public static final String OIL_MIDDLE_URL = "BP/OIL_CONSUM_";
    public static final String OIL_TAIL_URL = ".json?api_key=";
    static final String API_KEY = "Ty_FBWyM1FE3umQbkFcd";
    static String urlToCall;
    static String gdpUrl;
    static String oilUrl;
    
    public static Collection GetJson(Country country) throws IOException {
        // Συναρμολόγηση των URL's και εισαγωγή αυτών σε λίστα
        String isoCode = country.getIsoCode();
        oilUrl = MAIN_URL + OIL_MIDDLE_URL + isoCode + OIL_TAIL_URL + API_KEY;
        gdpUrl = MAIN_URL + GDP_MIDDLE_URL + isoCode + GDP_TAIL_URL + API_KEY;
        
        String[] url = {oilUrl, gdpUrl};
        Collection<RootJson> rootJsonCollection = new ArrayList();
        
        // Κλήση κάθε URL που βρίσκεται στην λίστα
        for (String urlToCall : url) {
        OkHttpClient client = new OkHttpClient();
        
        //Κλήση API για OIL και GDP διαδοχικά  
        Request Request = new Request.Builder().url(urlToCall).build();
        
            try (Response response = client.newCall(Request).execute()) {
                // Αν το URL δεν ειναι κενό, το καταχωρεί σε ένα string
                if (response.isSuccessful() && response.body() != null) {
                    String responseString = response.body().string();

                    // Δημιουργία Gson αντικειμένου για την διαχείρηση του API
                    Gson gson = new Gson();
    
                    // Δημιουργία RootJson και κατ' επέκταση DatasetJson αντικειμένων με ορίσματα τα στοιχεία του API
                    RootJson jsonRoot = gson.fromJson(responseString, RootJson.class);
                    rootJsonCollection.add(jsonRoot);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rootJsonCollection;
    }

}
