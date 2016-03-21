/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalstackplanning;

import java.io.IOException;

/**
 *
 * @author ANDITYAARIFIANTO
 */
public class GoalStackPlanning {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            GSP g = new GSP();
            g.start("current5.txt", "goal5.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
