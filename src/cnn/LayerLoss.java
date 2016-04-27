/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

import java.util.Map;

/**
 *
 * @author dee
 */
public class LayerLoss extends LayerInput {

    private int num_inputs;

    public LayerLoss(Options opt) {
        super(opt);
        int in_sx = (int) opt.get("in_sx");
        int in_sy = (int) opt.get("in_sy");
        int in_depth = (int) opt.get("in_depth");
        num_inputs = in_sx * in_sy * in_depth;
        setOut_sx(1);
        setOut_sy(1);
    }

//    public LayerLoss(Vol vol, int out_sx, int out_sy, int out_depth) {
//        super(vol, out_sx, out_sy, out_depth);
//        num_inputs = out_sx * out_sy * out_depth;
//    }
    public int getNum_inputs() {
        return num_inputs;
    }

    public void setNum_inputs(int num_inputs) {
        this.num_inputs = num_inputs;
    }
}
