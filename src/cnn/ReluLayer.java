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
public class ReluLayer extends LayerNonLinear {

    public ReluLayer(Options opt) {
        super(opt);
        layer_type = ("relu");
        out_depth = ((int) opt.getOpt("in_depth"));
    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        in_act = V;
        Vol V2 = V.clone();
        int N = V.w.length;
        double[] V2w = V2.w;
        for (int i = 0; i < N; i++) {
            if (V2w[i] < 0) {
                V2w[i] = 0;
            }
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
            if (V2.w[i] <= 0) {
                V.dw[i] = 0;
            } else {
                V.dw[i] = V2.dw[i];
            }
        }
    }

}
