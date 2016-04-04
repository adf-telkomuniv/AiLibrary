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
public class Fuzzy {

    private ArrayList<Input> inputCrips;
    private Rules rules;

    public Fuzzy() {
        inputCrips = new ArrayList();
        rules = null;
    }

    public Fuzzy(Rules rules) {
        inputCrips = new ArrayList();
        this.rules = rules;
    }

    public void addInput(Input input) {
        inputCrips.add(input);
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }

}
