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
public class PoolLayer extends LayerInput {

    private int sx;
    private int sy;
    private int in_depth;
    private int in_sx;
    private int in_sy;
    private int stride;
    private int pad;
    private int[] switchx, switchy;

    public PoolLayer(Vol vol, Options opt) {
        super(vol, opt);
        sx = (int) opt.get("sx");
        in_sx = (int) opt.get("in_sx");
        in_sy = (int) opt.get("in_sy");
        in_depth = (int) opt.get("in_depth");
        sy = (int) opt.getOpt("sy", sx);
        stride = (int) opt.getOpt("stride", 2);
        pad = (int) opt.getOpt("pad", 2);

        setOut_depth(in_depth);
        setOut_sx(Math.floorDiv(in_sx + pad * 2 - sx, stride + 1));
        setOut_sy(Math.floorDiv(in_sy + pad * 2 - sy, stride + 1));
        setLayer_type("pool");
        switchx = new int[getOut_sx() * getOut_sy() * getOut_depth()];
        switchy = new int[getOut_sx() * getOut_sy() * getOut_depth()];

    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        setIn_act(V);
        Vol A = new Vol(getOut_sx(), getOut_sy(), getOut_depth(), 0);
        int n = 0;
        for (int d = 0; d < getOut_depth(); d++) {
            int x = -pad;
            int y = -pad;
            for (int ax = 0; ax < getOut_sx(); x += stride, ax++) {
                y = -pad;
                for (int ay = 0; ay < getOut_sy(); y += stride, ay++) {
                    double a = Integer.MIN_VALUE;
                    int winx = -1;
                    int winy = -1;
                    for (int fx = 0; fx < sx; fx++) {
                        for (int fy = 0; fy < sy; fy++) {
                            int oy = y + fy;
                            int ox = x + fx;
                            if (oy >= 0 && oy < V.getSy() && ox >= 0 && ox < V.getSx()) {
                                double v = V.get(ox, oy, d);
                                if (v > a) {
                                    a = v;
                                    winx = ox;
                                    winy = oy;
                                }
                            }
                        }
                    }
                    switchx[n] = winx;
                    switchy[n] = winy;
                    n++;
                    A.set(ax, ay, d, a);
                }
            }
        }
        setOut_act(A);
        return getOut_act();
    }

    @Override
    public void backward() {
        Vol V = getIn_act();
        V.setDw(new double[V.getW().length]);
        Vol A = getOut_act();
        int n = 0;
        for (int d = 0; d < getOut_depth(); d++) {
            int x = -pad;
            int y = -pad;
            for (int ax = 0; ax < getOut_sx(); x += stride, ax++) {
                y = -pad;
                for (int ay = 0; ay < getOut_sy(); y += stride, ay++) {
                    double chain_grad = getOut_act().get_grad(ax, ay, d);
                    V.add_grad(switchx[n], switchy[n], d, chain_grad);
                    n++;
                }
            }
        }
    }

    @Override
    public Options toJSON() {
        Options opt = super.toJSON();
        opt.put("sx", sx);
        opt.put("sy", sy);
        opt.put("stride", stride);
        opt.put("in_depth", in_depth);
        opt.put("pad", pad);
        return opt;
    }

}
