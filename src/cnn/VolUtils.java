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
public class VolUtils {

    //utilities
    public Vol augment(Vol V, int crop, Options opt) {
        boolean fliplr = (boolean) opt.getOpt("fliplr", false);
        int dx = (int) opt.getOpt("dx", Utils.randi(0, V.getSx() - crop));
        int dy = (int) opt.getOpt("dy", Utils.randi(0, V.getSy() - crop));

        Vol W;
        if (crop != V.getSx() || dx != 0 || dy != 0) {
            W = new Vol(crop, crop, V.getDepth(), 0);
            for (int x = 0; x < crop; x++) {
                for (int y = 0; y < crop; y++) {
                    if (x + dx < 0 || x + dx >= V.getSx() || y + dy < 0 || y + dy >= V.getSy()) {
                        continue;
                    }
                    for (int d = 0; d < V.getDepth(); d++) {
                        W.set(x, y, d, V.get(x + dx, y + dy, d));
                    }
                }
            }
        } else {
            W = V;
        }

        if (fliplr) {
            Vol W2 = W.cloneAndZero();
            for (int x = 0; x < W.getSx(); x++) {
                for (int y = 0; y < W.getSy(); y++) {
                    for (int d = 0; d < W.getDepth(); d++) {
                        W2.set(x, y, d, W.get(W.getSx() - x - 1, y, d));
                    }
                }
            }
            W = W2;
        }
        return W;
    }

    public static Vol img_to_vol(int[][][] img, Options opt) {
        boolean convert_grayscale = (boolean) opt.getOpt("convert_grayscale", false);
        int W = img.length;
        int H = img[0].length;
        int D = img[0][0].length;
        double[] pv = new double[W * H * D];
        int p = 0;
        for (int d = 0; d < D; d++) {
            for (int w = 0; w < W; w++) {
                for (int h = 0; h < H; h++) {
                    pv[p++] = img[w][h][d] / 255.0 - 0.5;
                }
            }
        }
        Vol x = new Vol(W, H, 4, 0.0);
        x.setW(pv);
        if (convert_grayscale) {
            Vol x1 = new Vol(W, H, 1, 0.0);
            for (int i = 0; i < W; i++) {
                for (int j = 0; j < H; j++) {
                    x1.set(i, j, 0, x.get(i, j, 0));
                }
            }
            x = x1;
        }
        return x;
    }
}
