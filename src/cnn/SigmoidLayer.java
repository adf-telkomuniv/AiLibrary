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
public class SigmoidLayer extends LayerNonLinear {

    public SigmoidLayer(Options opt) {
        super(opt);
        layer_type = ("sigmoid");
        out_depth = ((int) opt.getOpt("in_depth"));
    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        in_act = V;
        Vol V2 = V.cloneAndZero();
        int N = V.w.length;
        double[] V2w = V2.w;
        double[] Vw = V.w;
        for (int i = 0; i < N; i++) {
            V2w[i] = 1 / (1 + Math.exp(-Vw[i]));
        }
        out_act = (V2);
        return out_act;
    }

    @Override
    public void backward() {
        Vol V = in_act;
        Vol V2 = out_act;
        int N = V.w.length;
        V.dw = (new double[N]);
        for (int i = 0; i < N; i++) {
            double v2wi = V2.w[i];
            V.dw[i] = v2wi * (1 - v2wi) * V2.dw[i];
        }
    }

}
