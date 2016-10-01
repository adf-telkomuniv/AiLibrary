/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dee
 */
public class ConvLayer extends LayerDotProducts {

    int in_depth;
    int in_sx;
    int in_sy;
    int sx;
    int sy;
    int stride;
    int pad;

    public ConvLayer(Options opt) {
        super(opt);
        out_depth = (int) opt.getOpt("filters");
        sx = (int) opt.getOpt("sx");
        in_depth = (int) opt.getOpt("in_depth");
        in_sx = (int) opt.getOpt("in_sx");
        in_sy = (int) opt.getOpt("in_sy");

        sy = (int) opt.getOpt("sy", sx);
        stride = (int) opt.getOpt("stride", 1);
        pad = (int) opt.getOpt("pad", 0);

        out_sx = Math.floorDiv(in_sx + pad * 2 - sx, stride) + 1;
        out_sy = Math.floorDiv(in_sy + pad * 2 - sy, stride) + 1;
        bias = (double) opt.getOpt("bias_pref", 0.0);
        filters = new Vol[out_depth];
        for (int i = 0; i < out_depth; i++) {
            filters[i] = new Vol(sx, sy, in_depth, bias);
        }
        biases = new Vol(1, 1, out_depth, bias);
        layer_type = "conv";

//        super.setLayer_type("conv");
//        super.setOut_depth((int) opt.getOpt("filters"));
//        super.setOut_sx(out_sx);
//        super.setOut_sy(out_sy);
//        super.setFilters(filters);
//        super.setBiases(biases);
    }

//    public ConvLayer(Vol vol, int out_sx, int out_sy, int out_depth, int in_depth,
//            int in_sx, int in_sy, int sx, int sy, int stride, int pad,
//            double l1_decay_mul, double l2_decay_mul, double bias) {
//        super(vol, out_sx, out_sy, out_depth);
//        this.in_depth = in_depth;
//        this.in_sx = in_sx;
//        this.in_sy = in_sy;
//        this.sx = sx;
//        this.sy = sy;
//        this.stride = stride;
//        this.pad = pad;
//        this.l1_decay_mul = l1_decay_mul;
//        this.l2_decay_mul = l2_decay_mul;
//        setOut_sx(Math.floorDiv(in_sx + pad * 2 - sx, stride) + 1);
//        setOut_sy(Math.floorDiv(in_sy + pad * 2 - sy, stride) + 1);
//        setLayer_type("conv");
//        this.bias = bias;
//        filters = new Vol[out_depth];
//        for (int i = 0; i < out_depth; i++) {
//            filters[i] = new Vol(sx, sy, in_depth, 0);
//        }
//        biases = new Vol(1, 1, out_depth, bias);
//    }
    @Override
    public Vol forward(Vol V, boolean is_training) {
        this.in_act = V;
        Vol A = new Vol(this.out_sx, this.out_sy, this.out_depth, 0);

        int V_sx = V.sx;
        int V_sy = V.sy;
        int xy_stride = this.stride;

        for (int d = 0; d < this.out_depth; d++) {
            Vol f = this.filters[d];
            int x;
            int y = -this.pad;
            for (int ay = 0; y < out_sy; y += xy_stride, ay++) {
                x = -this.pad;
                for (int ax = 0; ax < out_sx; x += xy_stride, ax++) {
                    double a = 0.0;
                    for (int fy = 0; fy < f.sy; fy++) {
                        int oy = y + fy;
                        for (int fx = 0; fx < f.sx; fx++) {
                            int ox = x + fx;
                            if (oy >= 0 && oy < V_sy && ox >= 0 && ox < V_sx) {
                                for (int fd = 0; fd < f.depth; fd++) {
                                    int pos1 = ((f.sx * fy) + fx) * f.depth + fd;
                                    int pos2 = ((V_sx * oy) + ox) * V.depth + fd;
                                    a += f.w[pos1] * V.w[pos2];
                                }
                            }
                        }
                    }
                    a += biases.w[d];
                    A.set(ax, ay, d, a);
                }
            }
        }
        out_act = A;
        return A;
    }

    @Override
    public void backward() {
        Vol V = this.in_act;
        V.dw = new double[V.w.length];

        int V_sx = V.sx;
        int V_sy = V.sy;
        int xy_stride = this.stride;

        for (int d = 0; d < this.out_depth; d++) {
            Vol f = filters[d];
            int x;
            int y = -this.pad;
            for (int ay = 0; ay < this.out_sy; y += xy_stride, ay++) {
                x = -this.pad;
                for (int ax = 0; ax < this.out_sx; x += xy_stride, ax++) {
                    double chain_grad = this.out_act.get_grad(ax, ay, d);
                    for (int fy = 0; fy < f.sy; fy++) {
                        int oy = y + fy;
                        for (int fx = 0; fx < f.sx; fx++) {
                            int ox = x + fx;
                            if (oy >= 0 && oy < V_sy && ox >= 0 && ox < V_sx) {
                                for (int fd = 0; fd < f.depth; fd++) {
                                    int ix1 = ((V_sx * oy) + ox) * V.depth + fd;
                                    int ix2 = ((f.sx * fy) + fx) * f.depth + fd;
                                    f.dw[ix2] += V.w[ix1] * chain_grad;
                                    V.dw[ix1] += f.w[ix2] * chain_grad;
                                }
                            }
                        }
                    }
                    this.biases.dw[d] += chain_grad;
                }
            }
        }
    }

    @Override
    public List<Options> getParamsAndGrads() {
        List<Options> response = new ArrayList();
        for (int i = 0; i < out_depth; i++) {
            Options opt = new Options();
            opt.put("params", filters[i].w);
            opt.put("grads", filters[i].dw);
            opt.put("l1_decay_mul", l1_decay_mul);
            opt.put("l2_decay_mul", l2_decay_mul);
            response.add(opt);
        }
        Options opt = new Options();
        opt.put("params", biases.w);
        opt.put("grads", biases.dw);
        opt.put("l1_decay_mul", 0.0);
        opt.put("l2_decay_mul", 0.0);
        response.add(opt);
        return response;
    }

    @Override
    public Options toJSON() {
        Options opt = super.toJSON();
        opt.put("sx", sx);
        opt.put("sy", sy);
        opt.put("stride", stride);
        opt.put("in_depth", in_depth);
        opt.put("l1_decay_mul", l1_decay_mul);
        opt.put("l2_decay_mul", l2_decay_mul);
        opt.put("pad", pad);
        opt.put("filters", filters);
        //loop filters
        opt.put("biases", biases);
        return opt;
    }

}
