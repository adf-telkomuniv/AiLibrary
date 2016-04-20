/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dee
 */
public class ConvLayer extends LayerInput {

    private int in_depth;
    private int in_sx;
    private int in_sy;
    private int sx;
    private int sy;
    private int stride;
    private int pad;
    private double l1_decay_mul;
    private double l2_decay_mul;
    private double bias;
    private Vol[] filters;
    private Vol biases;

    public ConvLayer(Vol vol, Options opt) {
        super(vol, opt);
        setOut_depth((int) opt.get("filters"));
        sx = (int) opt.get("sx");
        in_depth = (int) opt.get("in_depth");
        in_sx = (int) opt.get("in_sx");
        in_sy = (int) opt.get("in_sy");

        sy = (int) opt.getOpt("sy", sx);
        stride = (int) opt.getOpt("stride", 1);
        pad = (int) opt.getOpt("pad", 0);
        l1_decay_mul = (double) opt.getOpt("l1_decay_mul", 0);
        l2_decay_mul = (double) opt.getOpt("l2_decay_mul", 1);
        setOut_sx(Math.floorDiv(in_sx + pad * 2 - sx, stride) + 1);
        setOut_sy(Math.floorDiv(in_sy + pad * 2 - sy, stride) + 1);
        setLayer_type("conv");
        bias = (double) opt.getOpt("bias_pref", 0);
        filters = new Vol[getOut_depth()];
        for (int i = 0; i < getOut_depth(); i++) {
            filters[i] = new Vol(sx, sy, in_depth, 0);
        }
        biases = new Vol(1, 1, getOut_depth(), bias);
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
        setIn_act(V);
        Vol A = new Vol(getOut_sx(), getOut_sy(), getOut_depth(), 0);

        int V_sx = V.getSx();
        int V_sy = V.getSy();

        for (int d = 0; d < getOut_depth(); d++) {
            Vol f = filters[d];
            int x = -pad;
            int y = -pad;
            for (int ay = 0; y < getOut_depth(); y += stride, ay++) {
                x = -pad;
                for (int ax = 0; ax < getOut_sx(); x += stride, ax++) {
                    double a = 0;
                    for (int fy = 0; fy < f.getSy(); fy++) {
                        int oy = y + fy;
                        for (int fx = 0; fx < f.getSx(); fx++) {
                            int ox = x + fx;
                            if (oy >= 0 && oy < V_sy && ox >= 0 && ox < V_sx) {
                                for (int fd = 0; fd < f.getDepth(); fd++) {
                                    int pos1 = ((f.getSx() * fy + fx) * f.getDepth() + fd);
                                    int pos2 = ((V_sx * oy) + ox) * V.getDepth() + fd;
                                    a += f.getW(pos1) * V.getW(pos2);
                                }
                            }
                        }
                    }
                    a += biases.getW(d);
                    A.set(ax, ay, d, a);
                }
            }
        }
        setOut_act(A);
        return A;
    }

    public void backward() {
        Vol V = getIn_act();
        V.setDw(new double[V.getW().length]);

        int V_sx = V.getSx();
        int V_sy = V.getSy();

        for (int d = 0; d < getOut_depth(); d++) {
            Vol f = filters[d];
            int x = -pad;
            int y = -pad;
            for (int ay = 0; ay < getOut_sy(); y += stride, ay++) {
                x = -pad;
                for (int ax = 0; ax < getOut_sx(); x += stride, ax++) {
                    double chain_grad = getOut_act().get_grad(ax, ay, d);
                    for (int fy = 0; fy < f.getSy(); fy++) {
                        int oy = y + fy;
                        for (int fx = 0; fx < f.getSx(); fx++) {
                            int ox = x + fx;
                            if (oy >= 0 && oy < V_sy && ox >= 0 && ox < V_sx) {
                                for (int fd = 0; fd < f.getDepth(); fd++) {
                                    int ix1 = ((V_sx * oy) + ox) * V.getDepth() + fd;
                                    int ix2 = ((f.getSx() * fy) + fx) * f.getDepth() + fd;
                                    f.setDw(ix2, f.getDw(ix2) + (V.getW(ix1) * chain_grad));
                                    V.setDw(ix1, V.getDw(ix1) + (f.getW(ix2) * chain_grad));
                                }
                            }
                        }
                    }
                    biases.setDw(d, chain_grad);
                }
            }
        }
    }

    @Override
    public List<Options> getParamsAndGrads() {
        List<Options> response = new ArrayList();
        for (int i = 0; i < getOut_depth(); i++) {
            Options opt = new Options();
            opt.put("filters", filters[i].getW());
            opt.put("grads", filters[i].getDw());
            opt.put("l1_decay_mul", l1_decay_mul);
            opt.put("l2_decay_mul", l2_decay_mul);
            response.add(opt);
        }
        Options opt = new Options();
        opt.put("filters", biases.getW());
        opt.put("grads", biases.getDw());
        opt.put("l1_decay_mul", 0);
        opt.put("l2_decay_mul", 0);
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