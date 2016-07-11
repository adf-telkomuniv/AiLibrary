/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn.testing;

import cnn.Net;
import cnn.Options;
import cnn.Trainers;
import cnn.Vol;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ANDITYAARIFIANTO
 */
public class TestingCifar3 {

    public static void main(String[] args) {
        try {
            byte[] b = Files.readAllBytes(Paths.get("O:\\cifar-10-batches-bin\\data_batch_1.bin"));

            double[][] data = new double[10000][3072];
            double[] labels = new double[10000];

            System.out.println(b.length);
            for (int i = 0; i < 10000; i++) {
                labels[i] = b[i * 3073];
                byte[] b2 = Arrays.copyOfRange(b, i * 3073 + 1, i * 3073 + 3073);
                for (int j = 0; j < b2.length; j++) {
                    data[i][j] = b2[j];
                }
//                System.out.println(data[i][5]);
            }

            Options[] defs = new Options[4];
            defs[0] = new Options();
            defs[0].put("type", "input");
            defs[0].put("out_sx", 3072);
            defs[0].put("out_sy", 1);
            defs[0].put("out_depth", 1);

            defs[1] = new Options();
            defs[1].put("type", "fc");
            defs[1].put("num_neurons", 50);
            defs[1].put("activation", "relu");

            defs[2] = new Options();
            defs[2].put("type", "fc");
            defs[2].put("num_neurons", 50);
            defs[2].put("activation", "relu");

            defs[3] = new Options();
            defs[3].put("type", "svm");
            defs[3].put("num_neurons", 10);

            Net net = new Net();
            net.makeLayers(defs);

            Options opt = new Options();
            opt.put("learning_rate", 0.00001);
            opt.put("momentum", 0.0);
            opt.put("batch_size", 1);
            opt.put("l2_decay", 0.001);
            Trainers trainer = new Trainers(net, opt);

            int N = 20;
            int max_iter = 200;
            Vol netx = new Vol(3072, 1, 1, 0);
            double avloss = 0.0;
            for (int iters = 0; iters < max_iter; iters++) {
                System.out.println("iter=" + iters);
                for (int ix = 0; ix < N; ix++) {
                    netx.w = data[ix].clone();
                    Options stats = trainer.train(netx, labels);
                    double loss = (double) stats.getOpt("loss");
                    System.out.println("loss = " + loss);
                    avloss += loss;
                }
            }
            avloss /= N * max_iter;
            System.out.println("avloss = " + avloss);

            double[] y = new double[N];
            for (int ix = 0; ix < N; ix++) {
                netx.w = data[ix].clone();
                Vol v = net.forward(netx);
//                for (int i = 0; i < 10; i++) {
//                    System.out.print(" "+v.w[i]);
//                }
//                System.out.println("");
                for (int i = 0; i < 10; i++) {
                    if (v.w[(int) y[ix]] < v.w[i]) {
                        y[ix] = i;
                    }
                }
                System.out.println(v.w[(int) y[ix]]);
            }

            for (int i = 0; i < 10; i++) {
                System.out.println(y[i]);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
