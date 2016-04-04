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
public class FuzzyValue implements Comparable<FuzzyValue> {

    private String linguistic;
    private double fuzzyValue;

    public FuzzyValue(String linguistic, double fuzzyValue) {
        this.linguistic = linguistic;
        this.fuzzyValue = fuzzyValue;
    }

    public String getLinguistic() {
        return linguistic;
    }

    public double getFuzzyValue() {
        return fuzzyValue;
    }

    public void setLinguistic(String linguistic) {
        this.linguistic = linguistic;
    }

    public void setFuzzyValue(double fuzzyValue) {
        this.fuzzyValue = fuzzyValue;
    }

    @Override
    public int compareTo(FuzzyValue o) {
        return this.linguistic.compareTo(o.getLinguistic());
    }

}
