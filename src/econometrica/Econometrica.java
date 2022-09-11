/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package econometrica;

import java.sql.SQLException;
import graphicalUI.EconometricaGUI;
import java.io.IOException;

/**
 *
 *   @ Ίνγκριντ Νικόλα - std095138@ac.eap.gr   
 *   @ Χρυσούλα Τζώτζη - std100289@ac.eap.gr
 *   @ Πασχάλης Εκλεμές - std094823@ac.eap.gr
 *   @ Τμήμα ΗΛΕ-44 2019-2020
 *   
 */
public class Econometrica {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws SQLException, IOException {
        //άνοιγμα GUI
        EconometricaGUI gui = new EconometricaGUI();
        gui.setVisible(true);
    }
}
