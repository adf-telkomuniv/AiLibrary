/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dee
 */
public class SoftmaxLayer extends LayerLoss {

    private double[] es;

    public SoftmaxLayer(Options opt) {
        super(opt);
        setLayer_type("softmax");
    }

//    public SoftmaxLayer(Vol vol, int out_sx, int out_sy, int out_depth) {
//        super(vol, out_sx, out_sy, out_depth);
//        setLayer_type("softmax");
//    }
    @Override
    public double backward(int[] dy) {
        int y = dy[0];
        Vol x = getIn_act();
        x.setDw(new double[x.getW().length]);
        for (int i = 0; i < getOut_depth(); i++) {
            double indicator = (i == y ? 1 : 0);
            double mul = -(indicator - es[i]);
            x.setDw(i, mul);
        }
        return -Math.log(es[y]);
    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        setIn_act(V);
        Vol A = new Vol(1, 1, getOut_depth(), 0);
        double[] as = V.getW();
        double amax = V.getW(0);
        for (int i = 0; i < getOut_depth(); i++) {
            if (as[i] > amax) {
                amax = as[i];
            }
        }
        double[] es = new double[getOut_depth()];
        double esum = 0;
        for (int i = 0; i < getOut_depth(); i++) {
            double e = Math.exp(as[i] - amax);
            esum += e;
            es[i] = e;
        }
        for (int i = 0; i < getOut_depth(); i++) {
            es[i] /= esum;
            A.setW(i, es[i]);
        }
        this.es = es;
        setOut_act(A);
        return getOut_act();
    }

    @Override
    public Options toJSON() {
        Options opt = super.toJSON();
        opt.put("num_inputs", getNum_inputs());
        return opt;
    }

}
