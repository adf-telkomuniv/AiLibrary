/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import classifier.LinearClassifier;
import cnn.Net;
import cnn.Options;
import cnn.Trainers;
import cnn.Vol;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author dee
 */
public class Ai1DGui2 extends javax.swing.JFrame {

    /**
     * Creates new form AiLcGui
     */
    VisualComponents comp;
    HashMap<String, Component> cp;
    public double[][] data;
    public double[][] labels;
    int ss = 40;
    int height = 400;
    int width = 400;
    int N = 20;
    Trainers trainer;
    Net net;
    int maxiter = 5000;
    Vol netx = new Vol(1, 1, 1, 0);

    public Ai1DGui2() {
        initComponents();
        comp = new VisualComponents();
        comp.setBorder(new EtchedBorder(2));
        comp.setPreferredSize(new Dimension(height, width));
        getContentPane().add(comp, BorderLayout.CENTER);
        comp.clearLines();
        comp.clearDots();
        regen_data(N);
        pack();
    }

    public void drawData() {
        for (int i = 0; i < data.length; i++) {
            comp.addDot(
                    data[i][0] * ss + width * 0.5,
                    labels[i][0] * ss + height * 0.5,
                    Color.RED, 5
            );
        }
    }

    public void regen_data(int N) {
        Random r = new Random();
        data = new double[N][1];
        labels = new double[N][1];
        for (int i = 0; i < N; i++) {
            double x = r.nextDouble() * 10 - 5;
            data[i][0] = x + r.nextDouble();
            labels[i][0] = x * Math.sin(x);
        }
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i][0] + " " + labels[i][0]);

        }
    }

    public void drawResult() {
        comp.clearLines();

        Vol netx = new Vol(1, 1, 1, 0);
        double x1, y1, x2 = 0, y2 = 0;
        for (double x = 0.0; x <= width; x += 1) {
            netx.w[0] = (x - width / 2) / ss;
            Vol a = net.forward(netx);
            double y = a.w[0];
            if (x == 0) {
                x1 = x2 = x;
                y1 = y2 = y * ss + height / 2;
            } else {
                x1 = x2;
                y1 = y2;
                x2 = x;
                y2 = y * ss + height / 2;
            }
            comp.addLine(x1, y1, x2, y2, Color.BLUE);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        mainPanel = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        operation = new javax.swing.JPanel();
        btnStep = new javax.swing.JButton();
        btnInitiate = new javax.swing.JButton();
        btnRun = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnRandom = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menu.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        operation.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnStep.setText("Step");
        btnStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStepActionPerformed(evt);
            }
        });

        btnInitiate.setText("Initiate");
        btnInitiate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInitiateActionPerformed(evt);
            }
        });

        btnRun.setText("Run");
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnRandom.setText("Random");
        btnRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout operationLayout = new javax.swing.GroupLayout(operation);
        operation.setLayout(operationLayout);
        operationLayout.setHorizontalGroup(
            operationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(operationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(operationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInitiate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRandom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        operationLayout.setVerticalGroup(
            operationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(operationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnInitiate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRun)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStep)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRandom)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(operation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(operation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(185, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(mainPanel, java.awt.BorderLayout.LINE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStepActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnStepActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        comp.clearDots();
        comp.clearLines();
        init();
        comp.addLine(0, height / 2, width, height / 2, Color.BLACK);
        comp.addLine(width / 2, 0, width / 2, height, Color.BLACK);
        drawData();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnInitiateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInitiateActionPerformed
        // TODO add your handling code here:
        comp.clearDots();
        comp.clearLines();
        init();
        comp.addLine(0, height / 2, width, height / 2, Color.BLACK);
        comp.addLine(width / 2, 0, width / 2, height, Color.BLACK);
        drawData();

    }//GEN-LAST:event_btnInitiateActionPerformed

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        // TODO add your handling code here:
        int max_iter = maxiter;
        double avloss = 0.0;
        for (int iters = 0; iters < max_iter; iters++) {
            if (iters % 100 == 0) {
                System.out.println("iters = " + iters);
            }
            for (int ix = 0; ix < N; ix++) {
                netx.w = data[ix];
                Options stats = trainer.train(netx, labels[ix]);
                double loss = (double) stats.getOpt("loss");
                avloss += loss;
            }
        }
        avloss /= N * max_iter;
        System.out.println("avloss = " + avloss);
        comp.addLine(0, height / 2, width, height / 2, Color.BLACK);
        comp.addLine(width / 2, 0, width / 2, height, Color.BLACK);
//        drawData();
        drawResult();
    }//GEN-LAST:event_btnRunActionPerformed

    private void btnRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandomActionPerformed
        // TODO add your handling code here:
        comp.clearLines();
        comp.clearDots();
        regen_data(N);
        comp.addLine(0, height / 2, width, height / 2, Color.BLACK);
        comp.addLine(width / 2, 0, width / 2, height, Color.BLACK);
        comp.repaint();
    }//GEN-LAST:event_btnRandomActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInitiate;
    private javax.swing.JButton btnRandom;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnRun;
    private javax.swing.JButton btnStep;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel operation;
    // End of variables declaration//GEN-END:variables

    public void init() {
        Options[] defs = new Options[3];
        defs[0] = new Options();
        defs[0].put("type", "input");
        defs[0].put("out_sx", 1);
        defs[0].put("out_sy", 1);
        defs[0].put("out_depth", 1);

        defs[1] = new Options();
        defs[1].put("type", "fc");
        defs[1].put("num_neurons", 30);
        defs[1].put("activation", "sigmoid");

//        defs[2] = new Options();
//        defs[2].put("type", "fc");
//        defs[2].put("num_neurons", 4);
//        defs[2].put("activation", "sigmoid");

        defs[2] = new Options();
        defs[2].put("type", "regression");
        defs[2].put("num_neurons", 1);

        net = new Net();
        net.makeLayers(defs);

        Options opt = new Options();
        opt.put("learning_rate", 0.01);
        opt.put("momentum", 0.0);
        opt.put("batch_size", 1);
        opt.put("l2_decay", 0.001);
        trainer = new Trainers(net, opt);
    }

    public static void main(String[] args) {
        new Ai1DGui2().setVisible(true);
    }
}
