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
public class SVMLayer extends LayerLoss {

    public SVMLayer(Options opt) {
        super(opt);
        layer_type = ("svm");
        out_depth = num_inputs;

    }

//    public SVMLayer(Vol vol, int out_sx, int out_sy, int out_depth) {
//        super(vol, out_sx, out_sy, out_depth);
//        layer_type=("svm");
//    }
    @Override
    public Vol forward(Vol V, boolean is_training) {
        in_act = V;
        out_act = V;
        return V;
    }

    @Override
    public double backward(double[] dy) {
        int y = (int) dy[0];
        Vol x = in_act;
        x.dw = (new double[x.w.length]);
        double yscore = x.w[y];
        double margin = 1.0;
        double loss = 0.0;
        for (int i = 0; i < out_depth; i++) {
            if (y == i) {
                continue;
            }
            double ydiff = -yscore + x.w[i] + margin;
            if (ydiff > 0) {
                x.dw[i] += 1;
                x.dw[y] -= 1;
                loss += ydiff;
            }
        }
        return loss;
    }

    @Override
    public Options toJSON() {
        Options opt = super.toJSON();
        opt.put("num_inputs", num_inputs);
        return opt;
    }
}
