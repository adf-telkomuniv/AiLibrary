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

    private double[] layers;

    public void makeLayers(Options[] defs) {
        Utils.asserts(defs.length >= 2, "Error, num layer < 2");
        Utils.asserts(defs[0].get("type").equals("input"), "first layer must be input");

    }

    public void desugar(Options[] defs) {
        List<Options> new_defs = new ArrayList();
        for (int i = 0; i < defs.length; i++) {
            Options def = defs[i];
            if (def.get("type").equals("softmax") || def.get("type").equals("svm")) {
                Options opt = new Options();
                opt.put("type", "fc");
                opt.put("num_neurons", def.get("num_classes"));
                new_defs.add(opt);
            }
            if (def.get("type").equals("regression") ) {
                Options opt = new Options();
                opt.put("type", "fc");
                opt.put("num_neurons", def.get("num_neurons"));
                new_defs.add(opt);
            }
        }

    }

}
