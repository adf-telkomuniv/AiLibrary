/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

/**
 *
 * @author dee
 */
public class SugenoOutput implements CrispOutput {

    private double[] membership;

    public SugenoOutput(int numLinguistic) {
        membership = new double[numLinguistic];
    }

    public void setMembership(int numLinguistic, double d) {
        membership[numLinguistic] = d;
    }

    public double getMembership(int i) {
        return membership[i];
    }

    @Override
    public double defuzzy(FuzzyOutput[] output) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
