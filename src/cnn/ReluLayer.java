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

    public ReluLayer( Options opt) {
        super(opt);
        setLayer_type("relu");
        setOut_depth((int) opt.get("in_depth"));
    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        setIn_act(V);
        Vol V2 = V.clone();
        int N = V.getW().length;
        double[] V2w = V2.getW();
        for (int i = 0; i < N; i++) {
            if (V2w[i] < 0) {
                V2w[i] = 0;
            }
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
            if (V2.getW(i) <= 0) {
                V.setDw(i, 0);
            } else {
                V.setDw(i, V2.getDw(i));
            }
        }
    }

}
