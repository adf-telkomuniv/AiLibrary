/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author ANDITYAARIFIANTO
 */
public class FileIO {

    public static ArrayList<String> readFileString(String filename) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader bf = new BufferedReader(fr);
        ArrayList<String> temp = new ArrayList<>();
        String ln;
        while ((ln = bf.readLine()) != null) {
            temp.add(ln.toLowerCase());
        }
        bf.close();
        return temp;
    }

    public static double[][] readFileDouble(String filename) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader bf = new BufferedReader(fr);
        ArrayList<String> temp = new ArrayList<>();
        String ln;
        while ((ln = bf.readLine()) != null) {
            temp.add(ln.toLowerCase());
        }
        bf.close();
        double[][] out = new double[temp.size()][temp.get(0).split(" ").length];
        for (int i = 0; i < temp.size(); i++) {
            String[] db = temp.get(i).split(" ");
            for (int j = 0; j < db.length; j++) {
                out[i][j] = Double.parseDouble(db[j]);
            }
        }
        return out;
    }
}
