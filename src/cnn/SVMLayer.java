/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

import java.util.Map;

/**
 *
 * @author dee
 */
public class SVMLayer extends LayerLoss {

    public SVMLayer(Options opt) {
        super(opt);
        setLayer_type("svm");

    }

//    public SVMLayer(Vol vol, int out_sx, int out_sy, int out_depth) {
//        super(vol, out_sx, out_sy, out_depth);
//        setLayer_type("svm");
//    }
    @Override
    public Vol forward(Vol V, boolean is_training) {
        setIn_act(V);
        setOut_act(V);
        return V;
    }

    @Override
    public double backward(double[] dy) {
        int y = (int) dy[0];
        Vol x = getIn_act();
        x.setDw(new double[x.getW().length]);
        double yscore = x.getW(y);
        double margin = 1;
        double loss = 0;
        for (int i = 0; i < getOut_depth(); i++) {
            if (y == i) {
                continue;
            }
            double ydiff = -yscore + x.getW(i) + margin;
            if (ydiff > 0) {
                x.setDw(i, x.getDw(i) + 1);
                x.setDw(y, x.getDw(y) - 1);
                loss += ydiff;
            }
        }
        return loss;
    }

    @Override
    public Options toJSON() {
        Options opt = super.toJSON();
        opt.put("num_inputs", getNum_inputs());
        return opt;
    }
}
