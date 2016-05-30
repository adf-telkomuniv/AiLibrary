/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author dee
 */
public class MagicNet {

    private Vol[] data;
    private double[] labels;
//    private int[] labels;
    private double train_ratio;
    private int num_folds;
    private int num_candidates;
    private int num_epochs;
    private int ensemble_size;

    private int batch_size_min;
    private int batch_size_max;
    private int l2_decay_min;
    private int l2_decay_max;
    private int learning_rate_min;
    private int learning_rate_max;
    private double momentum_min;
    private double momentum_max;
    private int neurons_min;
    private int neurons_max;

    private List<Options> folds;
    private List<Options> candidates;
    private List<Options> evaluated_candidates;
//    private int[][] unique_labels;
    private double[] unique_labels;

    private int iter = 0;
    private int foldix = 0;

//    public MagicNet(Vol[] data, int[][] labels, Options opt) {
    public MagicNet(Vol[] data, double[] labels, Options opt) {
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

        batch_size_min = (int) opt.getOpt("batch_size_min", 10);
        batch_size_max = (int) opt.getOpt("batch_size_max", 300);
        l2_decay_min = (int) opt.getOpt("l2_decay_min", -4);
        l2_decay_max = (int) opt.getOpt("l2_decay_max", 2);
        learning_rate_min = (int) opt.getOpt("learning_rate_min", -4);
        learning_rate_max = (int) opt.getOpt("learning_rate_max", 0);
        momentum_min = (double) opt.getOpt("momentum_min", 0.9);
        momentum_max = (double) opt.getOpt("momentum_max", 0.9);
        neurons_min = (int) opt.getOpt("neurons_min", 5);
        neurons_max = (int) opt.getOpt("neurons_max", 30);

        folds = new ArrayList();
        candidates = new ArrayList();
        evaluated_candidates = new ArrayList();
        unique_labels = Utils.arrUnique(labels);
        iter = 0;
        foldix = 0;

//        finish_fold_callback = null;
//        finish_batch_callback = null;
        if (data.length > 0) {
            sampleFolds();
            sampleCandidates();
        }

    }

    public final void sampleFolds() {
        int N = data.length;
        int num_train = (int) Math.floor(train_ratio * N);
        folds = new ArrayList();
        for (int i = 0; i < num_folds; i++) {
            int[] p = Utils.randperm(N);
            Options opt = new Options();
            opt.put("train_ix", Arrays.copyOfRange(p, 0, num_train));
            opt.put("test_ix", Arrays.copyOfRange(p, num_train, N));
            folds.add(opt);
        }
    }

    public final Options sampleCandidate() {
        int input_depth = data[0].getW().length;
        int num_classes = unique_labels.length;
        Random r = new Random();

        List<Options> layer_defs = new ArrayList();
        Options opt = new Options();
        opt.put("type", "input");
        opt.put("out_sx", 1);
        opt.put("out_sy", 1);
        opt.put("out_depth", input_depth);
        layer_defs.add(opt);
        double nl = Utils.weightedSample(new double[]{0, 1, 2, 3}, new double[]{0.1, 0.3, 0.3, 0.2});

        for (int q = 0; q < nl; q++) {
            int ni = Utils.randi(neurons_min, neurons_max);
            String[] act_title = {"tanh", "maxout", "relu"};
            int act = Utils.randi(0, 3);
            if (Utils.randf(0, 1) < 0.5) {
                double dp = r.nextDouble();
                opt = new Options();
                opt.put("type", "fc");
                opt.put("num_neurons", ni);
                opt.put("activation", act);
                opt.put("drop_prob", dp);
                layer_defs.add(opt);
            } else {
                opt.put("type", "fc");
                opt.put("num_neurons", ni);
                opt.put("activation", act);
                layer_defs.add(opt);
            }
        }

        opt.put("type", "softmax");
        opt.put("num_classes", num_classes);
        layer_defs.add(opt);
        Net net = new Net();
        net.makeLayers(layer_defs.toArray(new Options[layer_defs.size()]));

        int bs = Utils.randi(batch_size_min, batch_size_max);
        double l2 = Math.pow(10, Utils.randf(l2_decay_min, l2_decay_max));
        double lr = Math.pow(10, Utils.randf(learning_rate_min, learning_rate_max));
        double mom = Utils.randf(momentum_min, momentum_max);
        double tp = Utils.randf(0, 1);
        Options trainer_def = new Options();
        if (tp < 0.33) {
            trainer_def.put("method", "adadelta");
            trainer_def.put("batch_size", bs);
            trainer_def.put("l2_decay", l2);
        } else if (tp < 0.66) {
            trainer_def.put("method", "adagrad");
            trainer_def.put("learning_rate", lr);
            trainer_def.put("l2_decay", l2);
        } else {
            trainer_def.put("method", "sgd");
            trainer_def.put("learning_rate", lr);
            trainer_def.put("momentum", mom);
            trainer_def.put("batch_size", bs);
            trainer_def.put("l2_decay", l2);
        }
        Trainers trainer = new Trainers(net, opt);

        Options candidate = new Options();
        candidate.put("acc", new ArrayList());
        candidate.put("accv", 0);
        candidate.put("layer_defs", layer_defs);
        candidate.put("trainer_def", trainer_def);
        candidate.put("net", net);
        candidate.put("trainer", trainer);
        return candidate;
    }

    public void sampleCandidates() {
        candidates = new ArrayList();
        for (int i = 0; i < num_candidates; i++) {
            Options candidate = sampleCandidate();
            candidates.add(candidate);
        }
    }

    public void step() {
        iter++;
        Options fold = folds.get(foldix);
        int[] train_ix = (int[]) fold.getOpt("train_ix");
        int dataix = train_ix[Utils.randi(0, train_ix.length)];
        for (int k = 0; k < candidates.size(); k++) {
            Vol x = data[dataix];
            double[] l = {labels[dataix]};
            Options candidate = candidates.get(k);
            Trainers trainer = (Trainers) candidate.getOpt("trainer");
            trainer.train(x, l);
            candidate.put("trainer", trainer);
        }

        double lastiter = num_epochs * train_ix.length;
        if (iter >= lastiter) {
            double[] val_acc = evalValErrors();
            for (int k = 0; k < candidates.size(); k++) {
                Options c = candidates.get(k);
                List acc = (ArrayList) c.getOpt("acc");
                double accv = (double) c.getOpt("accv");
                acc.add(val_acc[k]);
                c.put("accv", accv + val_acc[k]);
            }
            iter = 0;
            foldix++;

            // finish fold callback
            if (foldix >= folds.size()) {
                for (int k = 0; k < candidates.size(); k++) {
                    evaluated_candidates.add(candidates.get(k));
                }
                evaluated_candidates.sort(new CandidateComparator());
                if (evaluated_candidates.size() > 3 * ensemble_size) {
                    evaluated_candidates.subList(0, 3 * ensemble_size);
                }
                // finish batch callback
                sampleCandidates();
                foldix = 0;
            } else {
                for (int k = 0; k < candidates.size(); k++) {
                    Options c = candidates.get(k);
                    Net net = new Net();
                    List<Options> layer_defs = (ArrayList) c.getOpt("layer_defs");
                    Options[] defs = layer_defs.toArray(new Options[layer_defs.size()]);
                    net.makeLayers(defs);
                    Options trainer_def = (Options) c.getOpt("trainer_def");
                    Trainers trainer = new Trainers(net, trainer_def);
                    c.put("net", net);
                    c.put("trainer", trainer);
                }
            }
        }
    }

    public double[] evalValErrors() {
        double[] vals = new double[candidates.size()];
        Options fold = folds.get(foldix);
        int[] test_ix = (int[]) fold.getOpt("test_ix");
        for (int k = 0; k < candidates.size(); k++) {
            Net net = (Net) candidates.get(k).getOpt("net");
            double v = 0.0;
            for (int q = 0; q < test_ix.length; q++) {
                Vol x = data[test_ix[q]];
                //                int[] l = labels[test_ix[q]];
                double l = labels[test_ix[q]];
                net.forward(x);
                double yhat = net.getPrediction();
                v += (yhat == l ? 1.0 : 0.0);
            }
            v /= test_ix.length;
            vals[k] = v;
        }
        return vals;
    }

    public Vol predictSoft(Vol data) {
        List<Options> eval_candidates = null;
        int nv = 0;
        if (evaluated_candidates.isEmpty()) {
            nv = candidates.size();
            eval_candidates = candidates;
        } else {
            nv = Math.min(ensemble_size, evaluated_candidates.size());
            eval_candidates = evaluated_candidates;
        }

        int n = 0;
        Vol xout = null;
        for (int j = 0; j < nv; j++) {
            Net net = (Net) eval_candidates.get(j).getOpt("net");
            Vol x = net.forward(data);
            if (j == 0) {
                xout = x;
                n = x.getW().length;
            } else {
                for (int d = 0; d < n; d++) {
                    double newd = xout.getW(d) + x.getW(d);
                    xout.setW(d, newd);
                }
            }
        }
        for (int d = 0; d < n; d++) {
            double newd = xout.getW(d) / nv;
            xout.setW(d, newd);
        }
        return xout;
    }

    public double predict(Vol data) {
        Vol xout = predictSoft(data);
        double predicted_label = -1;
        if (xout.getW().length != 0) {
            Options stats = Utils.maxmin(xout.getW());
            predicted_label = (int) stats.getOpt("maxi");
        }
        return predicted_label;
    }

    public Options toJSON() {
        double nv = Math.min(ensemble_size, evaluated_candidates.size());
        Options opt = new Options();
        List<Options> nets = new ArrayList();
        for (int i = 0; i < nv; i++) {
            Net net = (Net) evaluated_candidates.get(i).getOpt("net");
            nets.add(net.toJSON());
        }
        return opt;
    }

    private static class CandidateComparator implements Comparator<Options> {

        @Override
        public int compare(Options a, Options b) {
            double a_accv = (double) a.getOpt("accv");
            double[] a_acc = (double[]) a.getOpt("acc");
            double b_accv = (double) b.getOpt("accv");
            double[] b_acc = (double[]) b.getOpt("acc");
            return (a_accv / a_acc.length) > (b_accv / b_acc.length) ? -1 : 1;
        }
    }
}
