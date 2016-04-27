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
public class Regressionlayer extends LayerLoss {

    public Regressionlayer(Options opt) {
        super(opt);
        setLayer_type("regression");
    }

//    public Regressionlayer(Vol vol, int out_sx, int out_sy, int out_depth) {
//        super(vol, out_sx, out_sy, out_depth);
//        setLayer_type("regression");
//    }
    @Override
    public Vol forward(Vol V, boolean is_training) {
        setIn_act(V);
        setOut_act(V);
        return V;
    }

//    public double backward(int y) {
//        Vol x = getIn_act();
//        x.setDw(new double[x.getW().length]);
//        double loss = 0;
//        double dy = x.getW(0) - y;
//        x.setDw(0, dy);
//        loss += 0.5 * dy * dy;
//        return loss;
//    }
//    public double backward(int i, double yi) {
//        Vol x = getIn_act();
//        x.setDw(new double[x.getW().length]);
//        double loss = 0;
//        double dy = x.getW(i) - yi;
//        x.setDw(i, dy);
//        loss += 0.5 * dy * dy;
//        return loss;
//    }
    @Override
    public double backward(int[] y) {
        if (y.length == 1) {
            Vol x = getIn_act();
            x.setDw(new double[x.getW().length]);
            double loss = 0;
            double dy = x.getW(0) - y[0];
            x.setDw(0, dy);
            loss += 0.5 * dy * dy;
            return loss;
        } else {
            Vol x = getIn_act();
            x.setDw(new double[x.getW().length]);
            double loss = 0;
            for (int i = 0; i < getOut_depth(); i++) {
                double dy = x.getW(i) - y[i];
                x.setDw(i, dy);
                loss += 0.5 * dy * dy;
            }
            return loss;
        }
    }

    @Override
    public Options toJSON() {
        Options opt = super.toJSON();
        opt.put("num_inputs", getNum_inputs());
        return opt;
    }
}
