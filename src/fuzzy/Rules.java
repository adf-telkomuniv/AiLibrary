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
public class Rules {

    private ArrayList<Rule> rules;

    public Rules() {
        rules = new ArrayList();
    }

    public void generate(String[][] input, String[] output) {
        int[] inp = new int[input.length];
        for (int i = 0; i < inp.length; i++) {
            inp[i] = input[i].length;
        }
        int max = 1;
        for (int i = 0; i < inp.length; i++) {
            max *= inp[i];
        }
        int ps = Math.round(max / output.length);
        int numOut = output.length - 1;
        int countOut = 0;
        for (int i = 0; i < inp.length; i++) {
            inp[i]--;
        }
        int[] inp2 = inp.clone();
        for (int i = 0; i < max; i++) {
            String s[] = new String[inp2.length];
            for (int j = 0; j < inp2.length; j++) {
                s[j] = input[j][inp2[j]];
            }
            mins(inp, 0, inp2);
            Rule r = new Rule(s, output[numOut]);
            rules.add(r);
            countOut++;
            if (countOut >= ps) {
                countOut = 0;
                if (numOut > 0) {
                    numOut--;
                }
            }
        }
//        rules.sort(null);
    }

    public void mins(int[] inp, int x, int[] inp2) {
        if (inp2[x] > 0) {
            inp2[x]--;
        } else {
            inp2[x] = inp[x];
            if (x < inp.length - 1) {
                mins(inp, x + 1, inp2);
            } else {

            }
        }
    }

    public void addRule(Rule r) {
        rules.add(r);
    }

    public void setRule(int i, Rule r) {
        rules.set(i, r);
    }

    public Rule getRule(int i) {
        return rules.get(i);
    }

    public void removeRule(int i) {
        rules.remove(i);
    }

    public void removeRule(Rule r) {
        rules.remove(r);
    }

    public void print() {
        rules.forEach(System.out::println);
    }

    public FuzzyValue checkRule(FuzzyValue[] input) {
        boolean ok;
        double val = input[0].getFuzzyValue();
        for (int i = 1; i < input.length; i++) {
            val = Math.min(val, input[i].getFuzzyValue());
        }
        for (Rule rule : rules) {
            ok = true;
            for (int j = 0; j < input.length; j++) {
                if (!input[j].getLinguistic().equals(rule.getInput(j))) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                FuzzyValue output = new FuzzyValue(rule.getOutputLing(), val);
                return output;
            }
        }
        return null;
    }

    @Override
    public String toString() {
//        rules.sort(null);
        String s = "";
        int i = 0;
        for (Rule rule : rules) {
            s = s + (i++) + " " + rule + "\n";
        }
        return s;
    }

}
