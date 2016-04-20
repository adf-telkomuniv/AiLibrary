/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsp;

import java.io.IOException;

/**
 *
 * @author dee
 */
public class NewClass {
     public static void main(String[] args) {
        try {
            // TODO code application logic here
            GSP g = new GSP();
            g.start("current2.txt", "goal2.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
