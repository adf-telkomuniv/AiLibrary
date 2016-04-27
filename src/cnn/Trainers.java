/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dee
 */
public class Trainers {

    private Net net;
    private double learning_rate;
    private double l1_decay;
    private double l2_decay;
    private int batch_size;
    private String method;
    private double momentum;
    private double ro;
    private double eps = 1e-8;
    private double beta1;
    private double beta2;
    private int k;
    private List<double[]> gsum;
    private List<double[]> xsum;
    private boolean regression;

    public Trainers(Net net, Options opt) {
        this.net = net;
        learning_rate = (double) opt.getOpt("learning_rate", 0.01);
        l1_decay = (double) opt.getOpt("l1_decay", 0.0);
        l2_decay = (double) opt.getOpt("l2_decay", 0.0);
        batch_size = (int) opt.getOpt("batch_size", 1);
        method = (String) opt.getOpt("method", "sgd");

        momentum = (double) opt.getOpt("momentum", 0.9);
        ro = (double) opt.getOpt("ro", 0.95);
        eps = (double) opt.getOpt("eps", 0.95);
        beta1 = (double) opt.getOpt("beta1", 0.95);
        beta2 = (double) opt.getOpt("beta2", 0.95);

        k = 0;
        if (this.net.getLayer(this.net.getLayers().size() - 1).getLayer_type().equals("regression")) {
            regression = true;
        } else {
            regression = false;
        }
        gsum = new ArrayList();
        xsum = new ArrayList();
    }

    public Options train(Vol x, int[] y) {
        Date start = new Date();
        net.forward(x, true);
        Date end = new Date();
        long fwd_time = end.getTime() - start.getTime();
        System.out.println("forward time = " + (fwd_time / (1000 * 60 * 60)));

        start = new Date();
        double cost_loss = net.backward(y);
        double l2_decay_loss = 0.0;
        double l1_decay_loss = 0.0;
        end = new Date();
        long bwd_time = end.getTime() - start.getTime();
        System.out.println("backward time = " + (bwd_time / (1000 * 60 * 60)));

        //check if regression
        if (regression && y.length <= 1) {
            throw new IllegalStateException("y must be an array for regression type");
        }

        k++;
        if (k % batch_size == 0) {
            List<Options> pglist = net.getParamsAndGrads();
            if (gsum.size() == 0 && (method.equals("sgd") || momentum > 0.0)) {
                for (int i = 0; i < pglist.size(); i++) {
                    Options[] layers = (Options[]) pglist.get(i).get("layers");
                    gsum.add(new double[layers.length]);
                    if (method.equals("adam") || method.equals("adadelta")) {
                        xsum.add(new double[layers.length]);
                    } else {
                        xsum.add(null);
                    }
                }
            }
            //s
        }

//        return null;
    }

}
