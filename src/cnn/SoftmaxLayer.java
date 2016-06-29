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
public class SoftmaxLayer extends LayerLoss {

    private double[] es;

    public SoftmaxLayer(Options opt) {
        super(opt);
//        int in_sx = (int) opt.getOpt("in_sx");
//        int in_sy = (int) opt.getOpt("in_sy");
//        int in_depth = (int) opt.getOpt("in_depth");
//        num_inputs = in_sx * in_sy * in_depth;
        layer_type = ("softmax");
        out_depth = num_inputs;
    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        in_act = (V);
//        System.out.println("out_depth softmax = " + out_depth);
        Vol A = new Vol(1, 1, out_depth, 0.0);
        double[] as = V.w;
        double amax = V.w[0];
        for (int i = 1; i < out_depth; i++) {
            if (as[i] > amax) {
                amax = as[i];
            }
        }
        double[] es = new double[out_depth];
        double esum = 0.0;
        for (int i = 0; i < out_depth; i++) {
            double e = Math.exp(as[i] - amax);
            esum += e;
            es[i] = e;
        }
        for (int i = 0; i < out_depth; i++) {
            es[i] /= esum;
            A.w[i] = es[i];
        }
        this.es = es;
        out_act = (A);
        return out_act;
    }

    @Override
    public double backward(double[] dy) {
        int y = (int) dy[0];
        Vol x = in_act;
        x.dw = (new double[x.w.length]);
        for (int i = 0; i < out_depth; i++) {
            double indicator = (i == y ? 1.0 : 0.0);
            double mul = -(indicator - es[i]);
            x.dw[i] = mul;
        }
        return -Math.log(es[y]);
    }

    @Override
    public Options toJSON() {
        Options opt = super.toJSON();
        opt.put("num_inputs", num_inputs);
        return opt;
    }

}
