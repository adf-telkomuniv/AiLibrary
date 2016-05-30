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
public class LayerLoss extends LayerInput {

    int num_inputs;

    public LayerLoss(Options opt) {
        super(opt);
        int in_sx = (int) opt.getOpt("in_sx");
        int in_sy = (int) opt.getOpt("in_sy");
        int in_depth = (int) opt.getOpt("in_depth");
        num_inputs = in_sx * in_sy * in_depth;
        out_sx = (1);
        out_sy = (1);
        out_depth = num_inputs;
    }

}
