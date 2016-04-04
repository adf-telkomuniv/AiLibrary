/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.ArrayList;

/**
 *
 * @author ANDITYAARIFIANTO
 */
public class Rule implements Comparable<Rule> {

    private ArrayList<String> inputLing;
    private String outputLing;

    public Rule(String outputLing) {
        this.outputLing = outputLing;
        inputLing = new ArrayList();
    }

    public Rule(String[] input, String outputLing) {
        this.outputLing = outputLing;
        inputLing = new ArrayList();
        for (int i = 0; i < input.length; i++) {
            inputLing.add(input[i]);
        }
    }

    public String getOutputLing() {
        return outputLing;
    }

    public void setOutputLing(String outputLing) {
        this.outputLing = outputLing;
    }

    public void addInput(String linguistic) {
        this.inputLing.add(linguistic);
    }

    public void addInput(int i, String linguistic) {
        this.inputLing.add(i, linguistic);
    }

    public void setInput(int i, String liString) {
        inputLing.set(i, liString);
    }

    public void removeInput(int i) {
        inputLing.remove(i);
    }

    public String getInput(int i) {
        return inputLing.get(i);
    }

    public String toString() {
        String s = "if " + inputLing.get(0);
        for (int i = 1; i < inputLing.size(); i++) {
            s = s + " and " + inputLing.get(i);
        }
        s = s + " then " + outputLing;
        return s;
    }

    @Override
    public int compareTo(Rule r) {
        return -this.outputLing.compareTo(r.outputLing);
    }

}
