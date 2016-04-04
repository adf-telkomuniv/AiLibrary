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

    private Membership[] membership;

    public Input(int numLinguistic) {
        membership = new Membership[numLinguistic];
    }

    public void setMembership(int numLinguistic, Membership m) {
        membership[numLinguistic] = m;
    }

    public Membership getMembership(int i) {
        return membership[i];
    }

    public FuzzyOutput[] fuzzify(double a) {
        ArrayList<FuzzyOutput> out = new ArrayList();
        for (Membership l : membership) {
            if (l.isInside(a)) {
                double fz = l.fuzzify(a);
                out.add(new FuzzyOutput(l.getLinguistic(), fz));
            }
        }
        return out.toArray(new FuzzyOutput[0]);
    }
}
