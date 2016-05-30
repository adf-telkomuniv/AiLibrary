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
public class LayerDotProducts extends LayerInput {

    double l1_decay_mul;
    double l2_decay_mul;
    double bias;
    Vol[] filters;
    Vol biases;

    public LayerDotProducts(Options opt) {
        super(opt);
        l1_decay_mul = (double) opt.getOpt("l1_decay_mul", 0.0);
        l2_decay_mul = (double) opt.getOpt("l2_decay_mul", 1.0);
        bias = (double) opt.getOpt("bias_pref", 0.0);
        System.out.println("bias = " + bias);

    }


}
