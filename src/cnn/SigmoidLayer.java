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
        setLayer_type("sigmoid");
        setOut_depth((int) opt.getOpt("in_depth"));
    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        setIn_act(V);
        Vol V2 = V.cloneAndZero();
        int N = V.getW().length;
        double[] V2w = V2.getW();
        double[] Vw = V.getW();
        for (int i = 0; i < N; i++) {
            V2w[i] = 1 / (1 + Math.exp(-Vw[i]));
        }
        setOut_act(V2);
        return getOut_act();
    }

    @Override
    public void backward() {
        Vol V = getIn_act();
        Vol V2 = getOut_act();
        int N = V.getW().length;
        V.setDw(new double[N]);
        for (int i = 0; i < N; i++) {
            double v2wi = V2.getW(i);
            V.setDw(i, v2wi * (1 - v2wi) * V2.getDw(i));
        }
    }

}
