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
public class FullyConnLayer extends LayerDotProducts {

    int num_inputs;
    int in_depth;
    int in_sx;
    int in_sy;

    public FullyConnLayer(Options opt) {
        super(opt);
        System.out.println("------------------------");
        System.out.println(opt);
//        int b1 = (int) opt.getOpt("num_neurons");
//        System.out.println("b1 = " + b1);
//        int b2 = (int) opt.getOpt("filters");
//        int a = (int) opt.getOpt(new String[]{"num_neurons", "filters"});
////        System.out.println("a = "+a);
//        System.out.println("b = " + b1 + " " + b2);
//        setOut_depth((int) opt.getOpt(new String[]{"num_neurons", "filters"}));

        if (opt.getOpt("num_neurons") != null) {
            out_depth = (int) opt.getOpt("num_neurons", 0);
        } else {
            out_depth = (int) opt.getOpt("filters", 0);
        }
        System.out.println("out = " + out_depth);

        in_depth = (int) opt.getOpt("in_depth");
        in_sx = (int) opt.getOpt("in_sx");
        in_sy = (int) opt.getOpt("in_sy");
        num_inputs = in_sx * in_sy * in_depth;
        out_sx = 1;
        out_sy = 1;
        layer_type = "fc";

        bias = (double) opt.getOpt("bias_pref", 0.0);
        System.out.println("bias fully = " + bias);

        filters = new Vol[out_depth];
        for (int i = 0; i < out_depth; i++) {
            filters[i] = new Vol(1, 1, num_inputs, 0);
        }
        biases = new Vol(1, 1, out_depth, bias);
    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        in_act = V;
        Vol A = new Vol(1, 1, out_depth, 0);
        double[] Vw = V.w;
        for (int i = 0; i < out_depth; i++) {
            double a = 0.0;
            double[] wi = filters[i].w;
            for (int d = 0; d < num_inputs; d++) {
                a += Vw[d] * wi[d];
            }
            a += biases.w[i];
            A.w[i] = a;
        }
        out_act = A;
        return out_act;
    }

    @Override
    public void backward() {
        Vol V = in_act;
        V.dw = new double[V.w.length];
        for (int i = 0; i < out_depth; i++) {
            Vol tfi = filters[i];
            double chain_grad = out_act.dw[i];
            for (int d = 0; d < num_inputs; d++) {
                V.dw[d] += tfi.w[d] * chain_grad;
                tfi.dw[d] += V.w[d] * chain_grad;
            }
            biases.dw[i] += chain_grad;
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
        opt.put("num_inputs", num_inputs);
        opt.put("l1_decay_mul", l1_decay_mul);
        opt.put("l2_decay_mul", l2_decay_mul);
        opt.put("filters", filters);
        //loop filters
        opt.put("biases", biases);
        return opt;
    }
}
