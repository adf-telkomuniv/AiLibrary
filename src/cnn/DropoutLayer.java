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

    private double drop_prob;
    private boolean[] dropped;

    public DropoutLayer(Vol vol, Options opt) {
        super(vol, opt);
        setLayer_type("dropout");
        drop_prob = (double) opt.getOpt("drop_prob", 0.5);
        dropped = new boolean[getOut_sx() * getOut_sy() * getOut_depth()];

    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        setIn_act(V);
        Vol V2 = V.clone();
        int N = V.getW().length;
        if (is_training) {
            Random r = new Random();
            for (int i = 0; i < N; i++) {
                if (r.nextDouble() < drop_prob) {
                    V2.setW(i, 0);
                    dropped[i] = true;
                } else {
                    dropped[i] = false;
                }
            }
        } else {
            for (int i = 0; i < N; i++) {
                V2.setW(i, V2.getW(i) * drop_prob);
            }
        }
        setOut_act(V2);
        return getOut_act();
    }

    @Override
    public void backward() {
        Vol V = getIn_act();
        Vol chain_grad = getOut_act();
        int N = V.getW().length;
        V.setDw(new double[N]);
        for (int i = 0; i < N; i++) {
            if (!(dropped[i])) {
                V.setDw(i, chain_grad.getDw(i));
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
