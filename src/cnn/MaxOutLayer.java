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

    public MaxOutLayer(Vol vol, Options opt) {
        super(vol, opt);
        setLayer_type("maxout");
        group_size = (int) opt.getOpt("group_size", 2);
        setOut_depth(Math.floorDiv((int) opt.get("in_depth"), group_size));
        switches = new int[getOut_sx() * getOut_sy() * getOut_depth()];
    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        setIn_act(V);
        int N = getOut_depth();
        Vol V2 = new Vol(getOut_sx(), getOut_sy(), getOut_depth(), 0);
        if (getOut_sx() == 1 && getOut_sy() == 1) {
            for (int i = 0; i < N; i++) {
                int ix = i * group_size;
                double a = V.getW(ix);
                int ai = 0;
                for (int j = 0; j < group_size; j++) {
                    double a2 = V.getW(ix + j);
                    if (a2 > a) {
                        a = a2;
                        ai = j;
                    }
                }
                V2.setW(i, a);
                switches[i] = ix + ai;
            }
        } else {
            int n = 0;
            for (int x = 0; x < V.getSx(); x++) {
                for (int y = 0; y < V.getSy(); y++) {
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
        setOut_act(V2);
        return getOut_act();
    }

    @Override
    public void backward() {
        Vol V = getIn_act();
        Vol V2 = getOut_act();
        int N = getOut_depth();
        V.setDw(new double[V.getW().length]);
        if (getOut_sx() == 1 && getOut_sy() == 1) {
            for (int i = 0; i < N; i++) {
                double chain_grad = V2.getDw(i);
                V.setDw(switches[i], chain_grad);
            }
        } else {
            int n = 0;
            for (int x = 0; x < V2.getSx(); x++) {
                for (int y = 0; y < V2.getSy(); y++) {
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
