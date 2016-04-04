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
public class FuzzyOutput implements Comparable<FuzzyOutput> {

    private String linguistic;
    private double fuzzyValue;

    public FuzzyOutput(String linguistic, double fuzzyValue) {
        this.linguistic = linguistic;
        this.fuzzyValue = fuzzyValue;
    }

    public String getLinguistic() {
        return linguistic;
    }

    public double getFuzzyValue() {
        return fuzzyValue;
    }

    @Override
    public int compareTo(FuzzyOutput o) {
        return this.linguistic.compareTo(o.getLinguistic());
    }

}
