/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

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
        if (lineType[0] < 0 || lineType[1] > 2 || lineType[1] < 0 || lineType[1] > 2) {
            throw new IllegalStateException("part value = 0/1/2 ");
        }

        this.linguistic = linguistic;
        this.position = position.clone();
    }

    public double fuzzify(double input) {
        if (isInside(input)) {
            if (position[1] > input && input <= position[2]) {
                return 1;
            }
            if (input <= position[1]) {
                return partA(input);
            }
            if (position[2] > input) {
                return partB(input);
            }
        }
        return 0;
    }

    public double partA(double input) {
        if (lineType[0] == 0) {
            
        }
        if (lineType[0] == 1) {

        } else {

        }
        return 0;
    }

    public double partB(double input) {
        if (lineType[1] == 0) {

        }
        if (lineType[1] == 1) {

        } else {

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
