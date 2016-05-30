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
        if (this.net.getLayer(this.net.getLayers().size() - 1).layer_type.equals("regression")) {
            regression = true;
        } else {
            regression = false;
        }
        gsum = new ArrayList();
        xsum = new ArrayList();
    }

    public Options train(Vol x, double[] y) {
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
                    double[] params = (double[]) pglist.get(i).getOpt("params");
                    gsum.add(new double[params.length]);
                    if (method.equals("adam") || method.equals("adadelta")) {
                        xsum.add(new double[params.length]);
                    } else {
                        xsum.add(null);
                    }
                }
            }
            for (int i = 0; i < pglist.size(); i++) {
                Options pg = pglist.get(i);
                double[] p = (double[]) pg.getOpt("params");
                double[] g = (double[]) pg.getOpt("grads");

                double l2_decay_mul = (double) pg.getOpt("l2_decay_mul", 1.0);
                double l1_decay_mul = (double) pg.getOpt("l1_decay_mul", 1.0);
                double l2_decay = this.l2_decay * l2_decay_mul;
                double l1_decay = this.l1_decay * l1_decay_mul;

                int plen = p.length;
                for (int j = 0; j < plen; j++) {
                    l2_decay_loss += l2_decay * p[j] * p[j] / 2;
                    l1_decay_loss += l1_decay * Math.abs(p[j]);
                    double l1grad = l1_decay * (p[j] > 0 ? 1 : -1);
                    double l2grad = l2_decay * (p[j]);

                    double gij = (l2grad + l1grad + g[j]) / this.batch_size;

                    double[] gsumi = this.gsum.get(i);
                    double[] xsumi = this.xsum.get(i);

                    if (method.equals("adam")) {
                        gsumi[j] = gsumi[j] * beta1 + (1 - beta1) * gij;
                        xsumi[j] = xsumi[j] * beta2 + (1 - beta2) * gij * gij;
                        double biasCorr1 = gsumi[j] * (1 - Math.pow(beta1, k));
                        double biasCorr2 = xsumi[j] * (1 - Math.pow(beta2, k));
                        double dx = -learning_rate * biasCorr1 / (Math.sqrt(biasCorr2) + eps);
                        p[j] += dx;
                    } else if (method.equals("adagrad")) {
                        gsumi[j] = gsumi[j] * gij * gij;
                        double dx = -learning_rate / Math.sqrt(gsumi[j] + eps) * gij;
                        p[j] += dx;
                    } else if (method.equals("windowgrad")) {
                        gsumi[j] = ro * gsumi[j] + (1 - ro) * gij * gij;
                        double dx = -learning_rate / Math.sqrt(gsumi[j] + eps) * gij;
                        p[j] += dx;
                    } else if (method.equals("adadelta")) {
                        gsumi[j] = ro * gsumi[j] + (1 - ro) * gij * gij;
                        double dx = -Math.sqrt((xsumi[j] + eps) / (gsumi[j] + eps)) * gij;
                        xsumi[j] = ro * xsumi[j] + (1 - ro) * dx * dx;
                        p[j] += dx;
                    } else if (method.equals("nesterov")) {
                        double dx = gsumi[j];
                        gsumi[j] = gsumi[j] * momentum + learning_rate * gij;
                        dx = momentum * dx - (1.0 + momentum) * gsumi[j];
                        p[j] += dx;
                    } else if (momentum > 0.0) {
                        double dx = momentum * gsumi[j] - learning_rate * gij;
                        gsumi[j] = dx;
                        p[j] += dx;
                    } else {
                        p[j] += -learning_rate * gij;
                    }
                    g[j] = 0.0;
                }
            }
        }

        Options options = new Options();
        options.put("fwd_time", fwd_time);
        options.put("bwd_time", bwd_time);
        options.put("l2_decay_loss", l2_decay_loss);
        options.put("l1_decay_loss", l1_decay_loss);
        options.put("cost_loss", cost_loss);
        options.put("softmax_loss", cost_loss);
        options.put("loss", cost_loss + l1_decay_loss + l2_decay_loss);
        return options;
    }

//    public Options train(Vol x, int[] y) {
//        Date start = new Date();
//        net.forward(x, true);
//        Date end = new Date();
//        long fwd_time = end.getTime() - start.getTime();
//        System.out.println("forward time = " + (fwd_time / (1000 * 60 * 60)));
//
//        start = new Date();
//        double cost_loss = net.backward(y);
//        double l2_decay_loss = 0.0;
//        double l1_decay_loss = 0.0;
//        end = new Date();
//        long bwd_time = end.getTime() - start.getTime();
//        System.out.println("backward time = " + (bwd_time / (1000 * 60 * 60)));
//
//        //check if regression
//        if (regression && y.length <= 1) {
//            throw new IllegalStateException("y must be an array for regression type");
//        }
//
//        k++;
//        if (k % batch_size == 0) {
//            List<Options> pglist = net.getParamsAndGrads();
//            if (gsum.size() == 0 && (method.equals("sgd") || momentum > 0.0)) {
//                for (int i = 0; i < pglist.size(); i++) {
//                    double[] params = (double[]) pglist.getOpt(i).getOpt("params");
//                    gsum.add(new double[params.length]);
//                    if (method.equals("adam") || method.equals("adadelta")) {
//                        xsum.add(new double[params.length]);
//                    } else {
//                        xsum.add(null);
//                    }
//                }
//            }
//            for (int i = 0; i < pglist.size(); i++) {
//                Options pg = pglist.getOpt(i);
//                double[] p = (double[]) pg.getOpt("params");
//                double[] g = (double[]) pg.getOpt("grads");
//
//                double l2_decay_mul = (double) pg.getOpt("l2_decay_mul", 1.0);
//                double l1_decay_mul = (double) pg.getOpt("l1_decay_mul", 1.0);
//                double l2_decay = this.l2_decay * l2_decay_mul;
//                double l1_decay = this.l1_decay * l1_decay_mul;
//
//                int plen = p.length;
//                for (int j = 0; j < plen; j++) {
//                    l2_decay_loss += l2_decay * p[j] * p[j] / 2;
//                    l1_decay_loss += l1_decay * Math.abs(p[j]);
//                    double l1grad = l1_decay * (p[j] > 0 ? 1 : -1);
//                    double l2grad = l2_decay * (p[j]);
//
//                    double gij = (l2grad + l1grad + g[j]) / this.batch_size;
//
//                    double[] gsumi = this.gsum.getOpt(i);
//                    double[] xsumi = this.xsum.getOpt(i);
//
//                    if (method.equals("adam")) {
//                        gsumi[j] = gsumi[j] * beta1 + (1 - beta1) * gij;
//                        xsumi[j] = xsumi[j] * beta2 + (1 - beta2) * gij * gij;
//                        double biasCorr1 = gsumi[j] * (1 - Math.pow(beta1, k));
//                        double biasCorr2 = xsumi[j] * (1 - Math.pow(beta2, k));
//                        double dx = -learning_rate * biasCorr1 / (Math.sqrt(biasCorr2) + eps);
//                        p[j] += dx;
//                    } else if (method.equals("adagrad")) {
//                        gsumi[j] = gsumi[j] * gij * gij;
//                        double dx = -learning_rate / Math.sqrt(gsumi[j] + eps) * gij;
//                        p[j] += dx;
//                    } else if (method.equals("windowgrad")) {
//                        gsumi[j] = ro * gsumi[j] + (1 - ro) * gij * gij;
//                        double dx = -learning_rate / Math.sqrt(gsumi[j] + eps) * gij;
//                        p[j] += dx;
//                    } else if (method.equals("adadelta")) {
//                        gsumi[j] = ro * gsumi[j] + (1 - ro) * gij * gij;
//                        double dx = -Math.sqrt((xsumi[j] + eps) / (gsumi[j] + eps)) * gij;
//                        xsumi[j] = ro * xsumi[j] + (1 - ro) * dx * dx;
//                        p[j] += dx;
//                    } else if (method.equals("nesterov")) {
//                        double dx = gsumi[j];
//                        gsumi[j] = gsumi[j] * momentum + learning_rate * gij;
//                        dx = momentum * dx - (1.0 + momentum) * gsumi[j];
//                        p[j] += dx;
//                    } else {
//                        if (momentum > 0.0) {
//                            double dx = momentum * gsumi[j] - learning_rate * gij;
//                            gsumi[j] = dx;
//                            p[j] += dx;
//                        } else {
//                            p[j] += -learning_rate * gij;
//                        }
//                    }
//                    g[j] = 0.0;
//                }
//            }
//        }
//
//        Options options = new Options();
//        options.put("fwd_time", fwd_time);
//        options.put("bwd_time", bwd_time);
//        options.put("l2_decay_loss", l2_decay_loss);
//        options.put("l1_decay_loss", l1_decay_loss);
//        options.put("cost_loss", cost_loss);
//        options.put("softmax_loss", cost_loss);
//        options.put("loss", cost_loss + l1_decay_loss + l2_decay_loss);
//        return options;
//    }
}
