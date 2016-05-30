/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

/**
 *
 * @author dee
 */
public class RegressionLayer extends LayerLoss {

    public RegressionLayer(Options opt) {
        super(opt);
        layer_type = ("regression");
        out_depth = num_inputs;

    }

//    public RegressionLayer(Vol vol, int out_sx, int out_sy, int out_depth) {
//        super(vol, out_sx, out_sy, out_depth);
//        setLayer_type("regression");
//    }
    @Override
    public Vol forward(Vol V, boolean is_training) {
        in_act = (V);
        out_act = (V);
        return V;
    }

//    public double backward(int y) {
//        Vol x = getIn_act();
//        x.setDw(new double[x.w.length]);
//        double loss = 0;
//        double dy = x.getW(0) - y;
//        x.setDw(0, dy);
//        loss += 0.5 * dy * dy;
//        return loss;
//    }
//    public double backward(int i, double yi) {
//        Vol x = getIn_act();
//        x.setDw(new double[x.w.length]);
//        double loss = 0;
//        double dy = x.getW(i) - yi;
//        x.setDw(i, dy);
//        loss += 0.5 * dy * dy;
//        return loss;
//    }
    @Override
    public double backward(double[] y) {
        Vol x = in_act;
        x.dw = (new double[x.w.length]);
        double loss = 0.0;

        if (y.length == 1) {
            double dy = x.w[0] - y[0];
            x.dw[0] = dy;
            loss += 0.5 * dy * dy;
            return loss;
        } else {

            for (int i = 0; i < out_depth; i++) {
                double dy = x.w[i] - y[i];
                x.dw[i] = dy;
                loss += 0.5 * dy * dy;
            }
            return loss;
        }
    }

    @Override
    public Options toJSON() {
        Options opt = super.toJSON();
        opt.put("num_inputs", num_inputs);
        return opt;
    }
}
