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
public class PoolLayer extends LayerInput {

    private int sx;
    private int sy;
    private int in_depth;
    private int in_sx;
    private int in_sy;
    private int stride;
    private int pad;
    private int[] switchx, switchy;

    public PoolLayer(Options opt) {
        super(opt);
        sx = (int) opt.getOpt("sx");
        in_sx = (int) opt.getOpt("in_sx");
        in_sy = (int) opt.getOpt("in_sy");
        in_depth = (int) opt.getOpt("in_depth");
        sy = (int) opt.getOpt("sy", sx);
        stride = (int) opt.getOpt("stride", 2);
        pad = (int) opt.getOpt("pad", 2);

        out_depth = (in_depth);
        out_sx = (Math.floorDiv(in_sx + pad * 2 - sx, stride + 1));
        out_sy = (Math.floorDiv(in_sy + pad * 2 - sy, stride + 1));
        layer_type = ("pool");
        switchx = new int[out_sx * out_sy * out_depth];
        switchy = new int[out_sx * out_sy * out_depth];

    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        in_act = (V);
        Vol A = new Vol(out_sx, out_sy, out_depth, 0);
        int n = 0;
        for (int d = 0; d < out_depth; d++) {
            int x = -pad;
            int y = -pad;
            for (int ax = 0; ax < out_sx; x += stride, ax++) {
                y = -pad;
                for (int ay = 0; ay < out_sy; y += stride, ay++) {
                    double a = Integer.MIN_VALUE;
                    int winx = -1;
                    int winy = -1;
                    for (int fx = 0; fx < sx; fx++) {
                        for (int fy = 0; fy < sy; fy++) {
                            int oy = y + fy;
                            int ox = x + fx;
                            if (oy >= 0 && oy < V.sy && ox >= 0 && ox < V.sx) {
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
        out_act = (A);
        return out_act;
    }

    @Override
    public void backward() {
        Vol V = in_act;
        V.dw = (new double[V.w.length]);
        Vol A = out_act;
        int n = 0;
        for (int d = 0; d < out_depth; d++) {
            int x = -pad;
            int y = -pad;
            for (int ax = 0; ax < out_sx; x += stride, ax++) {
                y = -pad;
                for (int ay = 0; ay < out_sy; y += stride, ay++) {
                    double chain_grad = out_act.get_grad(ax, ay, d);
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
