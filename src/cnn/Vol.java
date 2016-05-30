/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

import java.util.Random;

/**
 *
 * @author dee
 */
public class Vol {

    private int sx, sy;
    private int depth;
    private double c;
    private double[] w, dw;
    private int n;

    /**
     *
     * @param sx
     * @param sy
     * @param depth
     * @param c default set 0
     */
    public Vol(int sx, int sy, int depth, double c) {
        System.out.println(sx + ", " + sy + ", " + depth + ", " + c);
        this.sx = sx;
        this.sy = sy;
        this.depth = depth;
        this.c = c;
        n = sx * sy * depth;
        w = new double[n];
        dw = new double[n];

        if (c == 0) {
            double scale = Math.sqrt(1 / (sx * sy * depth));
            Random r = new Random();
            for (int i = 0; i < w.length; i++) {
                w[i] = r.nextDouble() * scale;
            }
        } else {
            for (int i = 0; i < w.length; i++) {
                w[i] = c;

            }
        }
    }

    /**
     *
     * @param sx
     * @param c default = 0
     */
    public Vol(double[] sx, double c) {
        this.sx = 1;
        this.sy = 1;
        this.depth = sx.length;
        w = new double[depth];
        dw = new double[depth];
        for (int i = 0; i < depth; i++) {
            w[i] = sx[i];
        }
    }

    public Options toJSON() {
        Options opt = new Options();
        opt.put("sx", sx);
        opt.put("sy", sy);
        opt.put("depth", depth);
        opt.put("w", w);
        return opt;
    }

    public double get(int x, int y, int d) {
        int ix = ((sx * y) + x) * depth + d;
        return w[ix];
    }

    public void set(int x, int y, int d, double v) {
        int ix = ((sx * y) + x) * depth + d;
        w[ix] = v;
    }

    public void add(int x, int y, int d, double v) {
        int ix = ((sx * y) + x) * depth + d;
        w[ix] += v;
    }

    public double get_grad(int x, int y, int d) {
        int ix = ((sx * y) + x) * depth + d;
        return dw[ix];
    }

    public void set_grad(int x, int y, int d, double v) {
        int ix = ((sx * y) + x) * depth + d;
        dw[ix] = v;
    }

    public void add_grad(int x, int y, int d, double v) {
        int ix = ((sx * y) + x) * depth + d;
        dw[ix] += v;
    }

    public Vol cloneAndZero() {
        return new Vol(sx, sy, depth, 0);
    }

    public Vol clone() {
        Vol v = new Vol(sx, sy, depth, 0);
        int n = w.length;
        for (int i = 0; i < n; i++) {
            v.setW(i, this.w[i]);
        }
        return v;
    }

    public void addFrom(Vol v) {
        for (int i = 0; i < w.length; i++) {
            this.w[i] += v.getW(i);
        }
    }

    public void addFromScaled(Vol v, double a) {
        for (int i = 0; i < w.length; i++) {
            this.w[i] += v.getW(i) * a;
        }
    }

    public void setConst(double a) {
        for (int i = 0; i < w.length; i++) {
            w[i] = a;
        }
    }

    public double getW(int i) {
        return w[i];
    }

    public void setW(int i, double w) {
        this.w[i] = w;
    }

    public double getDw(int i) {
        return dw[i];
    }

    public void setDw(int i, double dw) {
        this.dw[i] = dw;
    }

    public int getSx() {
        return sx;
    }

    public void setSx(int sx) {
        this.sx = sx;
    }

    public int getSy() {
        return sy;
    }

    public void setSy(int sy) {
        this.sy = sy;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    double[] getW() {
        return w.clone();
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public double[] getDw() {
        return dw.clone();
    }

    public void setDw(double[] dw) {
        this.dw = dw;
    }

}
