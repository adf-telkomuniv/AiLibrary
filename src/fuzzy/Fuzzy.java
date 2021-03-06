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

    private ArrayList<Input> inputSystem;
    private Rules rules;
    private OutputModel outputModel;

    public Fuzzy() {
        inputSystem = new ArrayList();
        rules = null;
    }

    public Fuzzy(Rules rules) {
        inputSystem = new ArrayList();
        this.rules = rules;
    }

    public void addInput(Input input) {
        inputSystem.add(input);
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }

    public void setOutput(OutputModel outputModel) {
        this.outputModel = outputModel;
    }

    public FuzzyValue[][] fuzzyfy(double[] crispInput) {
        if (crispInput.length != inputSystem.size()) {
            throw new IllegalStateException("wrong input size");
        }
        FuzzyValue[][] fuzzyInput = new FuzzyValue[crispInput.length][];
        for (int i = 0; i < crispInput.length; i++) {
            FuzzyValue[] out = inputSystem.get(i).fuzzify(crispInput[i]);
            fuzzyInput[i] = out;
        }
        return fuzzyInput;
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

    public FuzzyValue[][] generate(FuzzyValue[][] input) {
        int[] inp = new int[input.length];
        for (int i = 0; i < inp.length; i++) {
            inp[i] = input[i].length;
        }
        int max = 1;
        for (int i = 0; i < inp.length; i++) {
            max *= inp[i];
        }
        FuzzyValue[][] fuzzyOutput = new FuzzyValue[max][];
        for (int i = 0; i < inp.length; i++) {
            inp[i]--;
        }
        int[] inp2 = inp.clone();
        for (int i = 0; i < max; i++) {
            FuzzyValue s[] = new FuzzyValue[inp2.length];
            for (int j = 0; j < inp2.length; j++) {
                s[j] = input[j][inp2[j]];
            }
            mins(inp, inp2.length - 1, inp2);
            fuzzyOutput[i] = s;
        }
        return fuzzyOutput;
    }

    public FuzzyValue[] inference(FuzzyValue[][] fuzzyInput, Rules rules) {
        ArrayList<FuzzyValue> fuzzyOutput = new ArrayList();
        fuzzyInput = generate(fuzzyInput);
        for (FuzzyValue[] input : fuzzyInput) {
            FuzzyValue fuzzyInference = rules.checkRule(input);
            boolean found = false;
            for (FuzzyValue output : fuzzyOutput) {
                if (output.getLinguistic().equals(fuzzyInference.getLinguistic())) {
                    found = true;
                    output.setFuzzyValue(Math.max(output.getFuzzyValue(), fuzzyInference.getFuzzyValue()));
                    break;
                }
            }
            if (!found) {
                fuzzyOutput.add(fuzzyInference);
            }
        }
        fuzzyOutput.sort(null);
        return fuzzyOutput.toArray(new FuzzyValue[0]);
    }

    public double defuzzify(FuzzyValue[] fuzzyOutput, OutputModel outputModel) {
        if (outputModel instanceof MamdaniOutput) {
            MamdaniOutput mamdani = (MamdaniOutput) outputModel;
            return mamdani.defuzzy(fuzzyOutput);
        } else if (outputModel instanceof SugenoOutput) {
            SugenoOutput sugeno = (SugenoOutput) outputModel;
            return sugeno.defuzzy(fuzzyOutput);
        }
        return 0;
    }

    public double processFuzzy(double[] crispInput) {
        FuzzyValue[][] fuzzyInput = fuzzyfy(crispInput);
        FuzzyValue[] fuzzyOutput = inference(fuzzyInput, rules);
        double crispOutput = defuzzify(fuzzyOutput, outputModel);
        return crispOutput;
    }
}
