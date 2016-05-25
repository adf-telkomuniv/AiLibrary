/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn.testing;

import cnn.*;
import java.util.Random;

/**
 *
 * @author dee
 */
public class Testing {

    public double[][] data;
    public int[][] y;

    public static void main(String[] args) {
        Options[] defs = new Options[4];
        defs[0].put("type", "input");
        defs[0].put("out_sx", 1);
        defs[0].put("out_sy", 1);
        defs[0].put("out_depth", 1);

        defs[1].put("type", "fc");
        defs[1].put("num_neurons", 20);
        defs[1].put("activation", "relu");

        defs[2].put("type", "fc");
        defs[2].put("num_neurons", 20);
        defs[2].put("activation", "sigmoid");

        defs[3].put("type", "regression");
        defs[3].put("num_neurons", 1);

        Net net = new Net();
        net.makeLayers(defs);

        Options opt = new Options();
        opt.put("learning_rate", 0.01);
        opt.put("momentum", 0.0);
        opt.put("batch_size", 1);
        opt.put("l2_decay", 0.001);
        Trainers trainer = new Trainers(net, opt);
    }

    public void regen_data() {
        Random r = new Random();
        int N = 20;
        data = new double [N][1];
        y = new int[N][1];
        for (int i = 0; i < N; i++) {
            double x = r.nextDouble()*10-5;
            int y = x*Math.sin(x);
        }
    }

}
