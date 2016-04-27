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

    private double l1_decay_mul;
    private double l2_decay_mul;
    private double bias;
    private Vol[] filters;
    Vol biases;

//    public LayerDotProducts(Vol vol, Options opt) {
    public LayerDotProducts(Options opt) {
//        super(vol, opt);
        super(opt);
        l1_decay_mul = (double) opt.getOpt("l1_decay_mul", 0);
        l2_decay_mul = (double) opt.getOpt("l2_decay_mul", 1);
        bias = (double) opt.getOpt("bias_pref", 0);
        biases = new Vol(1, 1, getOut_depth(), getBias());

    }

    public double getL1_decay_mul() {
        return l1_decay_mul;
    }

    public void setL1_decay_mul(double l1_decay_mul) {
        this.l1_decay_mul = l1_decay_mul;
    }

    public double getL2_decay_mul() {
        return l2_decay_mul;
    }

    public void setL2_decay_mul(double l2_decay_mul) {
        this.l2_decay_mul = l2_decay_mul;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public Vol[] getFilters() {
        return filters;
    }

    public Vol getFilters(int i) {
        return filters[i];
    }

    public void setFilters(Vol[] filters) {
        this.filters = filters;
    }

    public void setFilters(int i, Vol filter) {
        filters[i] = filter;
    }

    public Vol getBiases() {
        return biases;
    }

    public void setBiases(Vol biases) {
        this.biases = biases;
    }

}
