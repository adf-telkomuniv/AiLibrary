/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

import java.util.Random;

/**
 *
 * @author dee
 */
public class DropoutLayer extends LayerInput {

    double drop_prob;
    boolean[] dropped;

    public DropoutLayer(Options opt) {
        super(opt);
        out_sx = (int) opt.getOpt("in_sx");
        out_sy = (int) opt.getOpt("in_sy");
        out_depth = (int) opt.getOpt("in_depth");
        layer_type = "dropout";
        drop_prob = (double) opt.getOpt("drop_prob", 0.5);
        dropped = new boolean[out_sx * out_sy * out_depth];

    }

    /**
     *
     * @param V
     * @param is_training default false
     * @return
     */
    @Override
    public Vol forward(Vol V, boolean is_training) {
        in_act = V;
        Vol V2 = V.clone();
        int N = V.w.length;
        if (is_training) {
            Random r = new Random();
            for (int i = 0; i < N; i++) {
                if (r.nextDouble() < drop_prob) {
                    V2.w[i] = 0;
                    dropped[i] = true;
                } else {
                    dropped[i] = false;
                }
            }
        } else {
            for (int i = 0; i < N; i++) {
                V2.w[i] *= drop_prob;
            }
        }
        out_act = V2;
        return out_act;
    }

    @Override
    public void backward() {
        Vol V = in_act;
        Vol chain_grad = out_act;
        int N = V.w.length;
        V.dw = new double[N];
        for (int i = 0; i < N; i++) {
            if (!(dropped[i])) {
                V.dw[i] = chain_grad.dw[i];
            }
        }
    }

    @Override
    public Options toJSON() {
        Options opt = super.toJSON();
        opt.put("drop_prob", drop_prob);
        return opt;
    }

}
