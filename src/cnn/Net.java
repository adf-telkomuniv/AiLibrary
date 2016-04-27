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
public class Net {

    private List<LayerInput> layers;

    public Net(Options opt) {
        layers = new ArrayList();
    }

    public void makeLayers(Options[] defs) {
        Utils.asserts(defs.length >= 2, "Error, num layer < 2");
        Utils.asserts(defs[0].get("type").equals("input"), "first layer must be input");
        defs = desugar(defs);

        layers = new ArrayList();
        for (int i = 0; i < defs.length; i++) {
            Options def = defs[i];
            if (i > 0) {
                LayerInput prev = layers.get(i - 1);
                def.put("in_sx", prev.getOut_sx());
                def.put("in_sy", prev.getOut_sy());
                def.put("in_depth", prev.getOut_depth());
            }
            String type = (String) def.get("type");
            switch (type) {
                case "fc":
                    layers.add(new FullyConnLayer(def));
                    break;
                case "lrn":
                    layers.add(new NormalizationLayer(def));
                    break;
                case "dropout":
                    layers.add(new DropoutLayer(def));
                    break;
                case "input":
                    layers.add(new LayerInput(def));
                    break;
                case "softmax":
                    layers.add(new SoftmaxLayer(def));
                    break;
                case "regression":
                    layers.add(new Regressionlayer(def));
                    break;
                case "conv":
                    layers.add(new ConvLayer(def));
                    break;
                case "pool":
                    layers.add(new PoolLayer(def));
                    break;
                case "relu":
                    layers.add(new ReluLayer(def));
                    break;
                case "sigmoid":
                    layers.add(new SigmoidLayer(def));
                    break;
                case "tanh":
                    layers.add(new TanhLayer(def));
                    break;
                case "maxout":
                    layers.add(new MaxOutLayer(def));
                    break;
                case "svm":
                    layers.add(new SVMLayer(def));
                    break;

                default:
                    throw new AssertionError("unrecognize type " + type);
            }
        }
    }

    // forward process
    public Vol forward(Vol V, boolean is_training) {
        Vol act = layers.get(0).forward(V, is_training);
        for (int i = 1; i < layers.size(); i++) {
            act = layers.get(i).forward(act, is_training);
        }
        return act;
    }

    public double getCostLoss(Vol v, int[] y) {
        this.forward(v, false);
        int N = layers.size();
        double loss = layers.get(N - 1).backward(y);
        return loss;
    }

//    public double backward(int y) {
//        int N = layers.size();
//        double loss = layers.get(N - 1).backward(y);
//        for (int i = N - 2; i >= 0; i--) {
//            layers.get(i).backward();
//        }
//        return loss;
//    }

    public double backward(int[] y) {
        int N = layers.size();
        double loss = layers.get(N - 1).backward(y);
        for (int i = N - 2; i >= 0; i--) {
            layers.get(i).backward();
        }
        return loss;
    }

    public List<Options> getParamsAndGrads() {
        List<Options> response = new ArrayList();
        for (LayerInput layer : layers) {
            List<Options> layer_responses = layer.getParamsAndGrads();
            for (Options layer_response : layer_responses) {
                response.add(layer_response);
            }
        }
        return response;
    }

    public int getPrediction() {
        LayerInput S = layers.get(layers.size() - 1);
        Utils.asserts(S.getLayer_type().equals("softmax"), "use softmax for getPrediction function");
        double[] p = S.getOut_act().getW();
        double maxv = p[0];
        int maxi = 0;
        for (int i = 1; i < p.length; i++) {
            if (p[i] > maxv) {
                maxv = p[i];
                maxi = i;
            }
        }
        return maxi;
    }

    public Options toJSON() {
        Options opt = new Options();
        Options[] layers = new Options[this.layers.size()];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = this.layers.get(i).toJSON();
        }
        opt.put("layers", layers);
        return opt;
    }

    public Options[] desugar(Options[] defs) {
        List<Options> new_defs = new ArrayList();
        for (int i = 0; i < defs.length; i++) {
            Options def = defs[i];
            if (def.get("type").equals("softmax") || def.get("type").equals("svm")) {
                Options opt = new Options();
                opt.put("type", "fc");
                opt.put("num_neurons", def.get("num_classes"));
                new_defs.add(opt);
            }
            if (def.get("type").equals("regression")) {
                Options opt = new Options();
                opt.put("type", "fc");
                opt.put("num_neurons", def.get("num_neurons"));
                new_defs.add(opt);
            }

            if ((def.get("type").equals("fc") || def.get("type").equals("conv"))
                    && !def.find("bias_pref")) {
                def.put("bias_pref", 0.0);
                if (def.find("activation") && ((String) def.get("activation")).equals("relu")) {
                    def.put("bias_pref", 0.1);
                }
            }

            new_defs.add(def);
            if (def.find("activation")) {
                Options opt;
                String activation = (String) def.get("activation");
                if (activation.equals("relu")) {
                    opt = new Options();
                    opt.put("type", "relu");
                    new_defs.add(opt);
                } else if (activation.equals("sigmoid")) {
                    opt = new Options();
                    opt.put("type", "sigmoid");
                    new_defs.add(opt);
                } else if (activation.equals("tanh")) {
                    opt = new Options();
                    opt.put("type", "tanh");
                    new_defs.add(opt);
                } else if (activation.equals("maxout")) {
                    opt = new Options();
                    int gs = (int) def.getOpt("group_size", 2);
                    opt.put("type", "maxout");
                    opt.put("group_size", gs);
                    new_defs.add(opt);
                } else {
                    throw new IllegalStateException("unsupported " + activation);
                }
                if (def.find("drop_prob") && def.get("type").equals("dropout")) {
                    opt = new Options();
                    opt.put("type", "dropout");
                    opt.put("drop_prob", (double) def.get("drop_prob"));
                }
            }
        }
        return new_defs.toArray(new Options[new_defs.size()]);
    }

    public List<LayerInput> getLayers() {
        return layers;
    }

    public void setLayers(List<LayerInput> layers) {
        this.layers = layers;
    }

    public LayerInput getLayer(int i) {
        return layers.get(i);
    }

    public void setLayer(int i, LayerInput layer) {
        layers.set(i, layer);
    }

    public void addLayer(LayerInput layer) {
        layers.add(layer);
    }

}
