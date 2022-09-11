/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package externalCommunication.parser;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 *   @ Ίνγκριντ Νικόλα - std095138@ac.eap.gr   
 *   @ Χρυσούλα Τζώτζη - std100289@ac.eap.gr
 *   @ Πασχάλης Εκλεμές - std094823@ac.eap.gr
 *   @ Τμήμα ΗΛΕ-44 2019-2020
 *   
 */
// Κλάση με ορίσματα τα Datasets του API (+ getters και setters)
public class RootJson {
    @SerializedName("dataset")
    @Expose
    private DatasetJson dataset;

    /**
     * @return the dataset
     */
    public DatasetJson getDataset() {
        return dataset;
    }

    /**
     * @param dataset the dataset to set
     */
    public void setDataset(DatasetJson dataset) {
        this.dataset = dataset;
    }
}
