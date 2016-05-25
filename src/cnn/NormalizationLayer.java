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
public class NormalizationLayer extends LayerInput {

    private int n;
    private int k;
    private double alpha;
    private double beta;
    private Vol S_cache;

    public NormalizationLayer(Options opt) {
//        super(vol, opt);
        super( opt);
        k = (int) opt.getOpt("k");
        n = (int) opt.getOpt("n");
        alpha = (double) opt.getOpt("alpha");
        beta = (double) opt.getOpt("beta");
        setOut_sx((int) opt.getOpt("in_sx"));
        setOut_sy((int) opt.getOpt("in_sy"));
        setOut_depth((int) opt.getOpt("in_depth"));
        setLayer_type("lrn");
        if (n % 2 == 0) {
            throw new IllegalStateException("Warning, n should be odd");
        }
    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        setIn_act(V);
        Vol A = V.cloneAndZero();
        S_cache = V.cloneAndZero();
        int n2 = Math.floorDiv(n, 2);
        for (int x = 0; x < V.getSx(); x++) {
            for (int y = 0; y < V.getSy(); y++) {
                for (int i = 0; i < V.getDepth(); i++) {
                    double ai = V.get(x, y, i);
                    double den = 0.0;
                    for (int j = Math.max(0, i - n2); j <= Math.min(i + n2, V.getDepth() - 1); j++) {
                        double aa = V.get(x, y, j);
                        den += aa * aa;
                    }
                    den *= alpha / n;
                    den += k;
                    S_cache.set(x, y, i, den);
                    den = Math.pow(den, beta);
                    A.set(x, y, i, ai / den);
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
        int n2 = Math.floorDiv(n, 2);
        for (int x = 0; x < V.getSx(); x++) {
            for (int y = 0; y < V.getSy(); y++) {
                for (int i = 0; i < V.getDepth(); i++) {
                    double chain_grad = getOut_act().get_grad(x, y, i);
                    double S = S_cache.get(x, y, i);
                    double SB = Math.pow(S, beta);
                    double SB2 = SB * SB;
                    for (int j = Math.max(0, i - n2); j <= Math.min(i + n2, V.getDepth() - 1); j++) {
                        double aj = V.get(x, y, j);
                        double g = -aj * beta * Math.pow(S, beta - 1) * alpha / n * 2 * aj;
                        if (j == i) {
                            g += SB;
                        }
                        g /= SB2;
                        g *= chain_grad;
                        V.add_grad(x, y, j, g);
                    }
                }
            }
        }
    }

    @Override
    public Options toJSON() {
        Options opt = super.toJSON();
        opt.put("k", k);
        opt.put("n", n);
        opt.put("alpha", alpha);
        opt.put("beta", beta);
        return opt;
    }

}
