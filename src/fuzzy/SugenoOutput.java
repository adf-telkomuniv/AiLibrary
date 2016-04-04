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
public class SugenoOutput implements OutputModel {

    private double[] membership;
    private String[] linguistic;

    public SugenoOutput(int numLinguistic) {
        membership = new double[numLinguistic];
        linguistic = new String[numLinguistic];
    }

    public SugenoOutput(double[] membership, String[] linguistic) {
        if (membership.length != linguistic.length) {
            throw new IllegalStateException("size membership and lingustic must be the same");
        }
        this.membership = membership;
        this.linguistic = linguistic;
    }

    public void setMembership(int numLinguistic, double membership, String linguistic) {
        this.membership[numLinguistic] = membership;
        this.linguistic[numLinguistic] = linguistic;
    }

    public double getMembership(int i) {
        return membership[i];
    }

    public String getLinguistic(int i) {
        return linguistic[i];
    }

    @Override
    public double defuzzy(FuzzyValue[] output) {
        double result = 0;
        double dvd = 0;
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < linguistic.length; j++) {
                if (output[i].getLinguistic().equals(linguistic[j])) {
                    result += output[i].getFuzzyValue() * membership[j];
                }
            }
            dvd += output[i].getFuzzyValue();
        }
        return result / dvd;
    }

}
