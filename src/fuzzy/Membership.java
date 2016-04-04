/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.Arrays;

/**
 *
 * @author dee
 */
public class Membership {

    private double position[] = new double[4];
    private String linguistic;
    private int lineType[];

    public Membership(String ling) {
        this.linguistic = ling;
        lineType = new int[]{0, 0};
    }

    public Membership(String linguistic, double[] position, int[] lineType) {
        if (position.length != 4) {
            throw new IllegalStateException("wrong array position length");
        }
        if (lineType.length != 2) {
            throw new IllegalStateException("wrong array lineType length");
        }
        Arrays.sort(position);
        if (position[0] < 0 || position[3] > 1) {
            throw new IllegalStateException("range position = [0,1]");
        }
        if (lineType[0] < 0 || lineType[1] > 1 || lineType[1] < 0 || lineType[1] > 1) {
            throw new IllegalStateException("part value = 0/1 ");
        }

        this.linguistic = linguistic;
        this.position = position.clone();
    }

    public double fuzzify(double input) {
        if (isInside(input)) {
            if (position[1] < input && input <= position[2]) {
                return 1;
            }
            if (position[0] <= input && input <= position[1]) {
                return partA(input);
            }
            if (position[2] < input && input <= position[3]) {
                return partB(input);
            }
        }
        return 0;
    }

    public double partA(double input) {
        if (lineType[0] == 0) {
            return (input - position[0]) / (position[1] - position[0]);
        }
        if (lineType[0] == 1) {
            double b = (position[0] + position[1]) / 2;
            if (input < b && input >= position[0]) {
                return 2 * Math.pow((input - position[0]) / (position[1] - position[0]), 2);
            }
            if (input >= b && input <= position[1]) {
                return 1 - 2 * Math.pow((input - position[1]) / (position[1] - position[0]), 2);
            }
        }
        return 0;
    }

    public double partB(double input) {
        if (lineType[1] == 0) {
            return -(input - position[3]) / (position[2] - position[3]);
        }
        if (lineType[1] == 1) {
            double b = (position[2] + position[3]) / 2;
            if (input < b && input >= position[2]) {
                return 1 - 2 * Math.pow((input - position[2]) / (position[3] - position[2]), 2);
            }
            if (input >= b && input <= position[3]) {
                return 2 * Math.pow((input - position[3]) / (position[3] - position[2]), 2);
            }
        }
        return 0;
    }

    public boolean isInside(double input) {
        return (input >= position[0] && input <= position[3]);
    }

    public String getLinguistic() {
        return linguistic;
    }

}
