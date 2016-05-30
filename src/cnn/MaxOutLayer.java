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
public class MaxOutLayer extends LayerNonLinear {

    private int group_size;
    private int[] switches;

    public MaxOutLayer(Options opt) {
        super(opt);
        layer_type = ("maxout");
        group_size = (int) opt.getOpt("group_size", 2);
        out_depth = (Math.floorDiv((int) opt.getOpt("in_depth"), group_size));
        switches = new int[out_sx * out_sy * out_depth];
    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        in_act = (V);
        int N = out_depth;
        Vol V2 = new Vol(out_sx, out_sy, out_depth, 0);
        if (out_sx == 1 && out_sy == 1) {
            for (int i = 0; i < N; i++) {
                int ix = i * group_size;
                double a = V.w[ix];
                int ai = 0;
                for (int j = 0; j < group_size; j++) {
                    double a2 = V.w[ix + j];
                    if (a2 > a) {
                        a = a2;
                        ai = j;
                    }
                }
                V2.w[i] = a;
                switches[i] = ix + ai;
            }
        } else {
            int n = 0;
            for (int x = 0; x < V.sx; x++) {
                for (int y = 0; y < V.sy; y++) {
                    for (int i = 0; i < N; i++) {
                        int ix = i * group_size;
                        double a = V.get(x, y, ix);
                        int ai = 0;
                        for (int j = 0; j < group_size; j++) {
                            double a2 = V.get(x, y, ix + j);
                            if (a2 > a) {
                                a = a2;
                                ai = j;
                            }
                        }
                        V2.set(x, y, i, a);
                        switches[n] = ix + ai;
                        n++;
                    }
                }
            }
        }
        out_act = (V2);
        return out_act;
    }

    @Override
    public void backward() {
        Vol V = in_act;
        Vol V2 = out_act;
        int N = out_depth;
        V.dw = (new double[V.w.length]);
        if (out_sx == 1 && out_sy == 1) {
            for (int i = 0; i < N; i++) {
                double chain_grad = V2.dw[i];
                V.dw[switches[i]] = chain_grad;
            }
        } else {
            int n = 0;
            for (int x = 0; x < V2.sx; x++) {
                for (int y = 0; y < V2.sy; y++) {
                    for (int i = 0; i < N; i++) {
                        double chain_grad = V2.get_grad(x, y, i);
                        V.set_grad(x, y, switches[n], chain_grad);
                        n++;
                    }
                }
            }
        }
    }

    @Override
    public Options toJSON() {
        Options opt = super.toJSON();
        opt.put("group_size", group_size);
        return opt;
    }

}
