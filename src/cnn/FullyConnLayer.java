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

    private int num_inputs;

    public FullyConnLayer( Options opt) {
        super( opt);
        setOut_depth((int) opt.getOpt(new String[]{"num_neurons", "filters"}));
        int in_depth = (int) opt.getOpt("in_depth");
        int in_sx = (int) opt.getOpt("in_sx");
        int in_sy = (int) opt.getOpt("in_sy");
        num_inputs = in_sx * in_sy * in_depth;
        setOut_sx(1);
        setOut_sy(1);
        setLayer_type("fc");
        setFilters(new Vol[getOut_depth()]);
        for (int i = 0; i < getOut_depth(); i++) {
            setFilters(i, new Vol(1, 1, num_inputs, 0));
        }
    }

    @Override
    public void backward() {
        Vol V = getIn_act();
        V.setDw(new double[V.getW().length]);
        for (int i = 0; i < getOut_depth(); i++) {
            Vol tfi = getFilters(i);
            double chain_grad = getOut_act().getDw(i);
            for (int d = 0; d < num_inputs; d++) {
                V.setDw(d, V.getDw(d) + (tfi.getW(d) * chain_grad));
                tfi.setDw(d, tfi.getDw(d) + (V.getW(d) * chain_grad));
            }
            getBiases().setDw(i, getBiases().getDw(i) + chain_grad);
        }
    }

    @Override
    public Vol forward(Vol V, boolean is_training) {
        setIn_act(V);
        Vol A = new Vol(1, 1, getOut_depth(), 0);
        double[] Vw = V.getW();
        for (int i = 0; i < getOut_depth(); i++) {
            double a = 0;
            double[] wi = getFilters(i).getW();
            for (int d = 0; d < num_inputs; d++) {
                a += Vw[d] * wi[d];
            }
            a += getBiases().getW(i);
            A.setW(i, a);
        }
        setOut_act(A);
        return getOut_act();
    }

    public int getNum_inputs() {
        return num_inputs;
    }

    public void setNum_inputs(int num_inputs) {
        this.num_inputs = num_inputs;
    }

    @Override
    public List<Options> getParamsAndGrads() {
        List<Options> response = new ArrayList();
        for (int i = 0; i < getOut_depth(); i++) {
            Options opt = new Options();
            opt.put("params", getFilters(i).getW());
            opt.put("grads", getFilters(i).getDw());
            opt.put("l1_decay_mul", getL1_decay_mul());
            opt.put("l2_decay_mul", getL2_decay_mul());
            response.add(opt);
        }
        Options opt = new Options();
        opt.put("params", biases.getW());
        opt.put("grads", biases.getDw());
        opt.put("l1_decay_mul", 0);
        opt.put("l2_decay_mul", 0);
        response.add(opt);
        return response;
    }
    
     @Override
    public Options toJSON() {
        Options opt = super.toJSON();
        opt.put("num_inputs", num_inputs);
        opt.put("l1_decay_mul", getL1_decay_mul());
        opt.put("l2_decay_mul", getL2_decay_mul());
        opt.put("filters", getFilters());
        //loop filters
        opt.put("biases", biases);
        return opt;
    }
}
