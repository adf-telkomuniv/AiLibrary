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
public class LayerInput {

    int out_sx, out_sy;
    int out_depth;
    String layer_type;
    Vol in_act, out_act;

    public LayerInput(Options opt) {
        if (opt.find("out_depth")) {
            out_depth = (int) opt.getOpt("out_depth", 0);
        } else {
            out_depth = (int) opt.getOpt("depth", 0);
        }
        System.out.println("out_depth = " + out_depth);

        if (opt.find("out_sx")) {
            out_sx = (int) opt.getOpt("out_sx", 1);
        } else if (opt.find("sx")) {
            out_sx = (int) opt.getOpt("sx", 1);
        } else {
            out_sx = (int) opt.getOpt("width", 1);
        }

        if (opt.find("out_sy")) {
            out_sy = (int) opt.getOpt("out_sy", 1);
        } else if (opt.find("sy")) {
            out_sy = (int) opt.getOpt("sy", 1);
        } else {
            out_sy = (int) opt.getOpt("height", 1);
        }

        layer_type = "input";
    }

    /**
     *
     * @param V
     * @param is_training default false
     * @return
     */
    public Vol forward(Vol V, boolean is_training) {
        in_act = out_act = V;
        return out_act;
    }

    public double backward(double y) {
        return 0;
    }

    public double backward(double[] y) {
        return 0;
    }

    public void backward() {

    }

    public List<Options> getParamsAndGrads() {
        return new ArrayList();
    }

    public Options toJSON() {
        Options opt = new Options();
        opt.put("out_depth", out_depth);
        opt.put("out_sx", out_sx);
        opt.put("out_sy", out_sy);
        opt.put("layer_type", layer_type);
        return opt;
    }

}
