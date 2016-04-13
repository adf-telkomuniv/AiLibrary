/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dee
 */
public class Input {

//    protected Membership[] membership;
    protected List<Membership> membership;

//    public Input(int numLinguistic) {
//        membership = new Membership[numLinguistic];
//    }
    public Input() {
        membership = new ArrayList();
    }

    public void addMembership(Membership m) {
        membership.add(m);
    }

    public void addMembership(String linguistic, double[] point) {
        membership.add(new Membership(linguistic, point));
    }

    public void addMembership(String linguistic, double a, double b, double c) {
        membership.add(new Membership(linguistic, new double[]{a, b, c}));
    }

    public void addMembership(String linguistic, double a, double b, double c, double d) {
        membership.add(new Membership(linguistic, new double[]{a, b, c, d}));
    }

    public void addMembership(String linguistic, double[] point, int[] lineType) {
        membership.add(new Membership(linguistic, point, lineType));
    }

    public void setMembership(int numLinguistic, Membership m) {
//        membership[numLinguistic] = m;
        if (numLinguistic >= membership.size()) {
            membership.add(m);
        } else {
            membership.set(numLinguistic, m);
        }
    }

    public Membership getMembership(int i) {
//        return membership[i];
        return membership.get(i);
    }

    public FuzzyValue[] fuzzify(double a) {
        ArrayList<FuzzyValue> out = new ArrayList();
        for (Membership l : membership) {
            if (l.isInside(a)) {
                double fz = l.fuzzify(a);
                out.add(new FuzzyValue(l.getLinguistic(), fz));
            }
        }
        return out.toArray(new FuzzyValue[0]);
    }
}
