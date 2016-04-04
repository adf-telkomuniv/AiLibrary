/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ANDITYAARIFIANTO
 */
public class Fuzzy {

    private ArrayList<Input> inputCrips;
    private Rules rules;
    private CrispOutput output;

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

    public void setOutput(CrispOutput output) {
        this.output = output;
    }

    public FuzzyOutput[][] fuzzyfy(double[] a) {
        if (a.length != inputCrips.size()) {
            throw new IllegalStateException("wrong input size");
        }
        FuzzyOutput[][] output = new FuzzyOutput[a.length][];
        for (int i = 0; i < a.length; i++) {
            FuzzyOutput[] out = inputCrips.get(i).fuzzify(a[i]);
            output[i] = out;
        }
        return output;
    }

    public FuzzyOutput[] inference(FuzzyOutput[][] fuzzyOutput) {
        ArrayList<FuzzyOutput> out = new ArrayList();
        fuzzyOutput = generate(fuzzyOutput);
        for (int i = 0; i < fuzzyOutput.length; i++) {
            FuzzyOutput o = rules.checkRule(fuzzyOutput[i]);
            boolean found = false;
            for (int j = 0; j < out.size(); j++) {
                if (out.get(i).getLinguistic().equals(o.getLinguistic())) {
                    found = true;
                    out.get(i).setFuzzyValue(Math.max(out.get(i).getFuzzyValue(), o.getFuzzyValue()));
                    break;
                }
            }
            if (!found) {
                out.add(o);
            }
        }
        out.sort(null);
        return out.toArray(new FuzzyOutput[0]);
    }

    public FuzzyOutput[][] generate(FuzzyOutput[][] input) {
        int[] inp = new int[input.length];
        for (int i = 0; i < inp.length; i++) {
            inp[i] = input[i].length;
        }
        int max = 1;
        for (int i = 0; i < inp.length; i++) {
            max *= inp[i];
        }
        FuzzyOutput[][] output = new FuzzyOutput[max][];
        for (int i = 0; i < inp.length; i++) {
            inp[i]--;
        }
        int[] inp2 = inp.clone();
        for (int i = 0; i < max; i++) {
            FuzzyOutput s[] = new FuzzyOutput[inp2.length];
            for (int j = 0; j < inp2.length; j++) {
                s[j] = input[j][inp2[j]];
            }
            mins(inp, inp2.length - 1, inp2);
            output[i] = s;
        }
        return output;
    }

    public void mins(int[] inp, int x, int[] inp2) {
        if (inp2[x] > 0) {
            inp2[x]--;
        } else {
            inp2[x] = inp[x];
            if (x > 0) {
                mins(inp, x - 1, inp2);
            } else {

            }
        }
    }

}
