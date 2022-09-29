/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package econometrica;

import java.sql.SQLException;
import graphicalUI.EconometricaGUI;
import java.io.IOException;

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
