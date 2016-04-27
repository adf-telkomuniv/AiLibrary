/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dee
 */
public class LayerInput {

//    private Vol vol;
    private int out_sx, out_sy;
    private int out_depth;
    private String layer_type;
    private Vol in_act, out_act;

//    public LayerInput(Vol vol, Options opt) {
    public LayerInput(Options opt) {
//        this.vol = vol;
        out_depth = (int) opt.getOpt(new String[]{"out_depth", "depth"}, 0);
        out_sx = (int) opt.getOpt(new String[]{"out_sx", "sx", "width"}, 1);
        out_sy = (int) opt.getOpt(new String[]{"out_sy", "sy", "height"}, 1);
        layer_type = "input";
    }

//    public LayerInput(Vol vol, int out_sx, int out_sy, int out_depth) {
//        this.vol = vol;
//
//        out_sx = (out_sx < 1 ? 1 : out_sx);
//        out_sy = (out_sy < 1 ? 1 : out_sy);
//        out_depth = (out_depth < 0 ? 0 : out_depth);
//        layer_type = "input";
//    }
    public Vol forward(Vol V, boolean is_training) {
        in_act = out_act = V;
        return out_act;
    }

    public double backward(int y) {
        return 0;
    }

    public double backward(int[] y) {
        return 0;
    }

    public void backward() {

    }

    public List<Options> getParamsAndGrads() {
        return null;
    }

    public Options toJSON() {
        Options opt = new Options();
        opt.put("out_depth", out_depth);
        opt.put("out_sx", out_sx);
        opt.put("out_sy", out_sy);
        opt.put("layer_type", layer_type);
        return opt;
    }

    public Vol getIn_act() {
        return in_act;
    }

    public void setIn_act(Vol in_act) {
        this.in_act = in_act;
    }

    public Vol getOut_act() {
        return out_act;
    }

    public void setOut_act(Vol out_act) {
        this.out_act = out_act;
    }

//    public Vol getVol() {
//        return vol;
//    }
//
//    public void setVol(Vol vol) {
//        this.vol = vol;
//    }
    public int getOut_sx() {
        return out_sx;
    }

    public void setOut_sx(int out_sx) {
        this.out_sx = out_sx;
    }

    public int getOut_sy() {
        return out_sy;
    }

    public void setOut_sy(int out_sy) {
        this.out_sy = out_sy;
    }

    public int getOut_depth() {
        return out_depth;
    }

    public void setOut_depth(int out_depth) {
        this.out_depth = out_depth;
    }

    public String getLayer_type() {
        return layer_type;
    }

    public void setLayer_type(String layer_type) {
        this.layer_type = layer_type;
    }

}
