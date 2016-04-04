/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.ArrayList;

/**
 *
 * @author dee
 */
public class Input {

    private Membership[] member;

    public Input(int l) {
        member = new Membership[l];
    }

    public void setLing(int i, Membership l) {
        member[i] = l;
    }

    public Membership getLine(int i) {
        return member[i];
    }

    public FuzzyOutput[] fuzzify(double a) {
        ArrayList<FuzzyOutput> out = new ArrayList();
        for (Membership l : member) {
            if (l.isInside(a)) {
                double fz = l.fuzzify(a);
                out.add(new FuzzyOutput(l.getLinguistic(), fz));
            }
        }
        return out.toArray(new FuzzyOutput[0]);
    }
}
