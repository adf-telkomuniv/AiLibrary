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
public class MagicNet {

    private double[] data;
    private double[] labels;
    private double train_ratio;
    private int num_folds;
    private int num_candidates;
    private int num_epochs;
    private int ensemble_size;

    public MagicNet(double[] data, double[] labels, Options opt) {
        if (opt == null) {
            opt = new Options();
        }
        this.data = data;
        this.labels = labels;
        train_ratio = (double) opt.getOpt("train_ratio", 0.7);

        num_folds = (int) opt.getOpt("num_folds", 10);
        num_candidates = (int) opt.getOpt("num_candidates", 50);

        num_epochs = (int) opt.getOpt("num_epochs", 50);
        ensemble_size = (int) opt.getOpt("ensemble_size", 10);
        
        
    }
}
