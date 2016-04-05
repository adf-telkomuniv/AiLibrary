/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dee
 */
public class MamdaniOutput extends Input implements OutputModel {

    private final int numPoint;

    public MamdaniOutput(int numLinguistic, int numPoint) {
        super(numLinguistic);
        this.numPoint = numPoint;
    }

    @Override
    public double defuzzy(FuzzyValue[] fuzzyOutput) {
        Map<Integer, Double> outputMap = new HashMap();
        for (int i = 0; i < membership.length; i++) {
            for (FuzzyValue output : fuzzyOutput) {
                if (membership[i].getLinguistic().equals(output.getLinguistic())) {
                    outputMap.put(i, output.getFuzzyValue());
                    break;
                }
            }
        }

        Integer[] outputLinguistic = outputMap.keySet().toArray(new Integer[0]);
        double min = membership[outputLinguistic[0]].getPosition()[1];
        double max = membership[outputLinguistic[outputLinguistic.length - 1]].getPosition()[2];
        for (Integer linguistic : outputLinguistic) {
            min = Math.min(min, membership[linguistic].getPosition()[1]);
            max = Math.max(max, membership[linguistic].getPosition()[2]);
        }
        double step = (max - min) / numPoint;
        double divider = 0;
        for (int i = 0; i < outputLinguistic.length; i++) {
            divider += (fuzzyOutput[i].getFuzzyValue() * numPoint / outputLinguistic.length);
        }
        double result = 0;
        double pos = min;

        for (int i = 0; i < numPoint; i++) {
            for (Integer linguistic : outputLinguistic) {
                if (membership[linguistic].isInside(pos)) {
                    result += pos * outputMap.get(linguistic);
                    break;
                }
            }
            pos += step;
        }

        return result / divider;
    }

}
