/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import classifier.LinearClassifier;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author dee
 */
public class SimLinearClassifier extends javax.swing.JFrame {

    /**
     * Creates new form SimLinearClassifier
     */
    final VisualComponents comp;
    private LinearClassifier model;
    HashMap<String, Component> cp;

    public SimLinearClassifier() {
        initComponents();
        model = new LinearClassifier();
        comp = new VisualComponents();
        comp.setBorder(new EtchedBorder(2));
        comp.setPreferredSize(new Dimension(model.getWidth(), model.getHeight()));
        getContentPane().add(comp, BorderLayout.CENTER);
        init();
        model.calculateScore(0);
        visualize();
        visualizeData();
        pack();
    }

    public void drawData() {
        for (int i = 0; i < model.getNumData(); i++) {
//            System.out.println(model.getData(i, 0) + "-" + model.getData(i, 1));
            comp.addDot(
                    model.getData(i, 0) * model.getSs() + model.getWidth() / 2,
                    model.getData(i, 1) * model.getSs() + model.getHeight() / 2,
                    model.getDataColor(model.getLabels(i)));
        }
    }

    public void drawLines() {
        int j = 0;
//        System.out.println("m = "+model.getWeight().length);
        for (int i = 0; i < model.getWeight().length; i += 2) {
//            System.out.println("i = " + i + " " + model.getWeight().length);
            int[] xs = {-5, 5, 0};
            double[] ys = {0, 0, 0};
            double[] w = {model.getWeight(i), model.getWeight(i + 1)};
            double b = model.getBias(j);
//            System.out.println(w[0] + " " + w[1] + " " + b + " " + j);
            ys[0] = (-b - w[0] * xs[0]) / w[1];
            ys[1] = (-b - w[0] * xs[1]) / w[1];
            ys[2] = (-b - w[0] * xs[2]) / w[1];
            double x1 = xs[0] * model.getSs() + model.getWidth() / 2;
            double x2 = xs[1] * model.getSs() + model.getWidth() / 2;
            double y1 = ys[0] * model.getSs() + model.getHeight() / 2;
            double y2 = ys[1] * model.getSs() + model.getHeight() / 2;
//            System.out.println(x1 + " " + x2 + " " + y1 + " " + y2);
            comp.addLine(x1, y1, x2, y2, model.getDataColor(j));
//            System.out.println("j = "+j);
            j++;
        }
    }

    public void getData() {
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                String s = "w" + i + j;
                JSpinner c = (JSpinner) cp.get(s);
                model.setWeight(k++, (double) c.getValue());
            }
        }
        k = 0;
        for (int i = 0; i < 3; i++) {
            String s = "b" + i;
            JSpinner c = (JSpinner) cp.get(s);
            model.setBias(k++, (double) c.getValue());

        }
        k = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 9; j++) {
                String s = "x" + i + j;
                JSpinner c = (JSpinner) cp.get(s);
                model.setData(i, j, (double) c.getValue());
            }
        }
    }

    public void visualize() {
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                String s = "w" + i + j;
                JSpinner c = (JSpinner) cp.get(s);
                c.setValue(model.getWeight(k++));
            }
        }
        k = 0;
        for (int i = 0; i < 3; i++) {
            String s = "b" + i;
            JSpinner c = (JSpinner) cp.get(s);
            c.setValue(model.getBias(k++));

        }
        k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                String s = "s" + i + j;
                JTextField c = (JTextField) cp.get(s);
                DecimalFormat df = new DecimalFormat("0.00");
                c.setText(df.format(model.getScores(j, i)));
            }
        }
        k = 0;
        for (int i = 0; i < 9; i++) {
            String s = "l" + i;
            JTextField c = (JTextField) cp.get(s);
            DecimalFormat df = new DecimalFormat("0.00");
            c.setText(df.format(model.getLoss(i)));

        }
    }

    public void visualizeData() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 9; j++) {
                String s = "x" + i + j;
                JSpinner c = (JSpinner) cp.get(s);
                c.setValue(model.getData(j, i));
            }
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
        buttonsPanel2 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        weight = new javax.swing.JPanel();
        w01 = new javax.swing.JSpinner();
        w11 = new javax.swing.JSpinner();
        w10 = new javax.swing.JSpinner();
        w20 = new javax.swing.JSpinner();
        w21 = new javax.swing.JSpinner();
        w00 = new javax.swing.JSpinner();
        process = new javax.swing.JPanel();
        y0 = new javax.swing.JTextField();
        x00 = new javax.swing.JSpinner();
        s00 = new javax.swing.JTextField();
        x10 = new javax.swing.JSpinner();
        s10 = new javax.swing.JTextField();
        s20 = new javax.swing.JTextField();
        l0 = new javax.swing.JTextField();
        s13 = new javax.swing.JTextField();
        l3 = new javax.swing.JTextField();
        y3 = new javax.swing.JTextField();
        x03 = new javax.swing.JSpinner();
        s23 = new javax.swing.JTextField();
        x13 = new javax.swing.JSpinner();
        s03 = new javax.swing.JTextField();
        s16 = new javax.swing.JTextField();
        y6 = new javax.swing.JTextField();
        l6 = new javax.swing.JTextField();
        x06 = new javax.swing.JSpinner();
        s26 = new javax.swing.JTextField();
        s06 = new javax.swing.JTextField();
        x16 = new javax.swing.JSpinner();
        y1 = new javax.swing.JTextField();
        s01 = new javax.swing.JTextField();
        s11 = new javax.swing.JTextField();
        s21 = new javax.swing.JTextField();
        x01 = new javax.swing.JSpinner();
        l1 = new javax.swing.JTextField();
        x11 = new javax.swing.JSpinner();
        l2 = new javax.swing.JTextField();
        x12 = new javax.swing.JSpinner();
        y2 = new javax.swing.JTextField();
        x02 = new javax.swing.JSpinner();
        s02 = new javax.swing.JTextField();
        s22 = new javax.swing.JTextField();
        s12 = new javax.swing.JTextField();
        s14 = new javax.swing.JTextField();
        l4 = new javax.swing.JTextField();
        y4 = new javax.swing.JTextField();
        x04 = new javax.swing.JSpinner();
        s24 = new javax.swing.JTextField();
        x14 = new javax.swing.JSpinner();
        s04 = new javax.swing.JTextField();
        x15 = new javax.swing.JSpinner();
        s05 = new javax.swing.JTextField();
        s15 = new javax.swing.JTextField();
        x05 = new javax.swing.JSpinner();
        l5 = new javax.swing.JTextField();
        y5 = new javax.swing.JTextField();
        s25 = new javax.swing.JTextField();
        s17 = new javax.swing.JTextField();
        y7 = new javax.swing.JTextField();
        l7 = new javax.swing.JTextField();
        x07 = new javax.swing.JSpinner();
        s27 = new javax.swing.JTextField();
        s07 = new javax.swing.JTextField();
        x17 = new javax.swing.JSpinner();
        x18 = new javax.swing.JSpinner();
        s18 = new javax.swing.JTextField();
        l8 = new javax.swing.JTextField();
        s08 = new javax.swing.JTextField();
        s28 = new javax.swing.JTextField();
        x08 = new javax.swing.JSpinner();
        y8 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        bias = new javax.swing.JPanel();
        b1 = new javax.swing.JSpinner();
        b2 = new javax.swing.JSpinner();
        b0 = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        btnStep = new javax.swing.JButton();
        btnInitiate = new javax.swing.JButton();
        btnRun = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnRandom = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lossFunction = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        lr = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        minError = new javax.swing.JFormattedTextField();
        maxIter = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        weight.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "weight", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        weight.setName("weight"); // NOI18N

        w01.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        w01.setDoubleBuffered(true);

        w11.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        w11.setDoubleBuffered(true);

        w10.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        w10.setDoubleBuffered(true);

        w20.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        w20.setDoubleBuffered(true);

        w21.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        w21.setDoubleBuffered(true);

        w00.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        w00.setDoubleBuffered(true);

        javax.swing.GroupLayout weightLayout = new javax.swing.GroupLayout(weight);
        weight.setLayout(weightLayout);
        weightLayout.setHorizontalGroup(
            weightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(weightLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(weightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(weightLayout.createSequentialGroup()
                        .addComponent(w00, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(w01, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(weightLayout.createSequentialGroup()
                        .addComponent(w10, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(w11, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(weightLayout.createSequentialGroup()
                        .addComponent(w20, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(w21, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        weightLayout.setVerticalGroup(
            weightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(weightLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(weightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(w00, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(w01, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(weightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(w10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(w11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(weightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(w20, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(w21, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        process.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        y0.setEditable(false);
        y0.setBackground(new java.awt.Color(102, 204, 255));
        y0.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        y0.setText("0");

        x00.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x00.setDoubleBuffered(true);

        s00.setEditable(false);
        s00.setBackground(new java.awt.Color(102, 204, 255));
        s00.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x10.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x10.setDoubleBuffered(true);

        s10.setEditable(false);
        s10.setBackground(new java.awt.Color(204, 255, 204));
        s10.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s20.setEditable(false);
        s20.setBackground(new java.awt.Color(255, 204, 204));
        s20.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        l0.setEditable(false);
        l0.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s13.setEditable(false);
        s13.setBackground(new java.awt.Color(102, 255, 102));
        s13.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        l3.setEditable(false);
        l3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        y3.setEditable(false);
        y3.setBackground(new java.awt.Color(102, 255, 102));
        y3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        y3.setText("1");

        x03.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x03.setDoubleBuffered(true);

        s23.setEditable(false);
        s23.setBackground(new java.awt.Color(255, 204, 204));
        s23.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x13.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x13.setDoubleBuffered(true);

        s03.setEditable(false);
        s03.setBackground(new java.awt.Color(204, 255, 255));
        s03.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s16.setEditable(false);
        s16.setBackground(new java.awt.Color(204, 255, 204));
        s16.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        y6.setEditable(false);
        y6.setBackground(new java.awt.Color(255, 102, 102));
        y6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        y6.setText("2");

        l6.setEditable(false);
        l6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x06.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x06.setDoubleBuffered(true);

        s26.setEditable(false);
        s26.setBackground(new java.awt.Color(255, 102, 102));
        s26.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s06.setEditable(false);
        s06.setBackground(new java.awt.Color(204, 255, 255));
        s06.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x16.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x16.setDoubleBuffered(true);

        y1.setEditable(false);
        y1.setBackground(new java.awt.Color(102, 204, 255));
        y1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        y1.setText("0");

        s01.setEditable(false);
        s01.setBackground(new java.awt.Color(102, 204, 255));
        s01.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s11.setEditable(false);
        s11.setBackground(new java.awt.Color(204, 255, 204));
        s11.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s21.setEditable(false);
        s21.setBackground(new java.awt.Color(255, 204, 204));
        s21.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x01.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x01.setDoubleBuffered(true);

        l1.setEditable(false);
        l1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x11.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x11.setDoubleBuffered(true);

        l2.setEditable(false);
        l2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x12.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x12.setDoubleBuffered(true);

        y2.setEditable(false);
        y2.setBackground(new java.awt.Color(102, 204, 255));
        y2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        y2.setText("0");

        x02.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x02.setDoubleBuffered(true);

        s02.setEditable(false);
        s02.setBackground(new java.awt.Color(102, 204, 255));
        s02.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s22.setEditable(false);
        s22.setBackground(new java.awt.Color(255, 204, 204));
        s22.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s12.setEditable(false);
        s12.setBackground(new java.awt.Color(204, 255, 204));
        s12.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s14.setEditable(false);
        s14.setBackground(new java.awt.Color(102, 255, 102));
        s14.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        l4.setEditable(false);
        l4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        y4.setEditable(false);
        y4.setBackground(new java.awt.Color(102, 255, 102));
        y4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        y4.setText("1");

        x04.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x04.setDoubleBuffered(true);

        s24.setEditable(false);
        s24.setBackground(new java.awt.Color(255, 204, 204));
        s24.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x14.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x14.setDoubleBuffered(true);

        s04.setEditable(false);
        s04.setBackground(new java.awt.Color(204, 255, 255));
        s04.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x15.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x15.setDoubleBuffered(true);

        s05.setEditable(false);
        s05.setBackground(new java.awt.Color(204, 255, 255));
        s05.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s15.setEditable(false);
        s15.setBackground(new java.awt.Color(102, 255, 102));
        s15.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x05.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x05.setDoubleBuffered(true);

        l5.setEditable(false);
        l5.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        y5.setEditable(false);
        y5.setBackground(new java.awt.Color(102, 255, 102));
        y5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        y5.setText("1");

        s25.setEditable(false);
        s25.setBackground(new java.awt.Color(255, 204, 204));
        s25.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s17.setEditable(false);
        s17.setBackground(new java.awt.Color(204, 255, 204));
        s17.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        y7.setEditable(false);
        y7.setBackground(new java.awt.Color(255, 102, 102));
        y7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        y7.setText("2");

        l7.setEditable(false);
        l7.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x07.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x07.setDoubleBuffered(true);

        s27.setEditable(false);
        s27.setBackground(new java.awt.Color(255, 102, 102));
        s27.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s07.setEditable(false);
        s07.setBackground(new java.awt.Color(204, 255, 255));
        s07.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x17.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x17.setDoubleBuffered(true);

        x18.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x18.setDoubleBuffered(true);

        s18.setEditable(false);
        s18.setBackground(new java.awt.Color(204, 255, 204));
        s18.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        l8.setEditable(false);
        l8.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s08.setEditable(false);
        s08.setBackground(new java.awt.Color(204, 255, 255));
        s08.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        s28.setEditable(false);
        s28.setBackground(new java.awt.Color(255, 102, 102));
        s28.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        x08.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        x08.setDoubleBuffered(true);

        y8.setEditable(false);
        y8.setBackground(new java.awt.Color(255, 102, 102));
        y8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        y8.setText("2");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("x[0]");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("x[1]");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("y");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("s[0]");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("s[1]");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("s[2]");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("loss");

        javax.swing.GroupLayout processLayout = new javax.swing.GroupLayout(process);
        process.setLayout(processLayout);
        processLayout.setHorizontalGroup(
            processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(processLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x04, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x00, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(x05, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x07, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x03, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x06, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x08, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x01, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x02, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(x14, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x15, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x10)
                    .addComponent(x13, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x17, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x18, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x16, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x11, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x12, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(y4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(y0)
                    .addComponent(y5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(y7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(y3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(y6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(y8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(y1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(y2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s04, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s00)
                    .addComponent(s05, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s03, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s07, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s08, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s06, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s01, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s02, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(s12, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(s14, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(s15, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(s10)
                    .addComponent(s17, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(s13, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(s16, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(s18, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(s11, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s22, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s24, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s20)
                    .addComponent(s25, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s23, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s27, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s28, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s26, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s21, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l0, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        processLayout.setVerticalGroup(
            processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(processLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(x00, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(s00)
                    .addComponent(s10)
                    .addComponent(s20)
                    .addComponent(l0)
                    .addComponent(x10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(y0))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(x01, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(x11)
                    .addComponent(y1)
                    .addComponent(s01)
                    .addComponent(s11)
                    .addComponent(s21)
                    .addComponent(l1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(x02, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(x12)
                    .addComponent(y2)
                    .addComponent(s02)
                    .addComponent(s12)
                    .addComponent(s22)
                    .addComponent(l2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(x03, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(x13)
                    .addComponent(y3)
                    .addComponent(s03)
                    .addComponent(s13)
                    .addComponent(s23)
                    .addComponent(l3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(x04, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(x14)
                    .addComponent(y4)
                    .addComponent(s04)
                    .addComponent(s14)
                    .addComponent(s24)
                    .addComponent(l4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(x05, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(x15)
                    .addComponent(y5)
                    .addComponent(s05)
                    .addComponent(s15)
                    .addComponent(s25)
                    .addComponent(l5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(x06, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(x16)
                    .addComponent(y6)
                    .addComponent(s06)
                    .addComponent(s16)
                    .addComponent(s26)
                    .addComponent(l6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(x07, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(x17)
                    .addComponent(y7)
                    .addComponent(s07)
                    .addComponent(s17)
                    .addComponent(s27)
                    .addComponent(l7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(x08, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(x18)
                    .addComponent(y8)
                    .addComponent(s08)
                    .addComponent(s18)
                    .addComponent(s28)
                    .addComponent(l8))
                .addContainerGap())
        );

        bias.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "bias", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        b1.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        b1.setDoubleBuffered(true);

        b2.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        b2.setDoubleBuffered(true);

        b0.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.1d)));
        b0.setDoubleBuffered(true);

        javax.swing.GroupLayout biasLayout = new javax.swing.GroupLayout(bias);
        bias.setLayout(biasLayout);
        biasLayout.setHorizontalGroup(
            biasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(biasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(biasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b0, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        biasLayout.setVerticalGroup(
            biasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(biasLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(b0, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInitiate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRandom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
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

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "parameters"));

        jLabel2.setText("loss function");

        lossFunction.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Softmax", "Weston Watkins '99", "One vs. All", "Structured SVM" }));

        jLabel3.setText("min error");

        lr.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.1d), null, null, Double.valueOf(0.01d)));
        lr.setDoubleBuffered(true);

        jLabel5.setText("learning rate");

        jLabel11.setText("max iteration");

        minError.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.000000#"))));
        minError.setText("0.000001");

        maxIter.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        maxIter.setText("1000");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lossFunction, 0, 1, Short.MAX_VALUE)
                    .addComponent(minError)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lr, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(maxIter))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lossFunction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(minError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(maxIter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(weight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(process, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(weight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(process, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout buttonsPanel2Layout = new javax.swing.GroupLayout(buttonsPanel2);
        buttonsPanel2.setLayout(buttonsPanel2Layout);
        buttonsPanel2Layout.setHorizontalGroup(
            buttonsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonsPanel2Layout.setVerticalGroup(
            buttonsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonsPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(buttonsPanel2, java.awt.BorderLayout.LINE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStepActionPerformed
        // TODO add your handling code here:
        double loss = model.calculateScore(lossFunction.getSelectedIndex());
        model.step((double) lr.getValue());
        comp.clearLines();
        drawLines();
        drawData();
        visualize();
    }//GEN-LAST:event_btnStepActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        comp.clearLines();
        comp.clearDots();
        model = new LinearClassifier();
        lossFunction.setSelectedIndex(0);
        lr.setValue(0.1);
        minError.setText("0.000001");
        model.calculateScore(0);
        visualize();
        visualizeData();

    }//GEN-LAST:event_btnResetActionPerformed

    private void btnInitiateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInitiateActionPerformed
        // TODO add your handling code here:
        comp.clearLines();
        comp.clearDots();
        for (int i = 0; i < 9; i++) {
            JTextField c = (JTextField) cp.get("l" + i);
            c.setBackground(new Color(240, 240, 240));
        }
        getData();
        drawData();
        drawLines();
        visualize();
    }//GEN-LAST:event_btnInitiateActionPerformed

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        // TODO add your handling code here:
        double temploss = 0;
        int iter = 0;
        double loss = 0;
        for (int i = 0; i < Double.parseDouble(maxIter.getText()); i++) {
            loss = model.calculateScore(0);
            if (Math.abs(loss - temploss) < Double.parseDouble(minError.getText())) {
                break;
            } else {
                temploss = loss;
            }
            iter = i + 1;

            model.step((double) lr.getValue());
            comp.clearLines();
            drawLines();
            visualize();
        }
        drawData();

        for (int i = 0; i < 9; i++) {
            double max = (Math.max(Math.max(model.getScores(i, 0), model.getScores(i, 1)), model.getScores(i, 2)));
            JTextField c = (JTextField) cp.get("l" + i);
            if (max == model.getScores(i, model.getLabels(i))) {
                c.setBackground(Color.green.brighter());
            } else {
                c.setBackground(new Color(240, 240, 240));
            }
        }
        DecimalFormat df = new DecimalFormat("0.00#");
        JOptionPane.showMessageDialog(this, "Process stopped at iteration " + iter + ", and average loss of " + df.format(loss));

    }//GEN-LAST:event_btnRunActionPerformed

    private void btnRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandomActionPerformed
        // TODO add your handling code here:
        model.randomWeight();
        model.randomBias();
        visualizeData();
        visualize();
    }//GEN-LAST:event_btnRandomActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        SimLinearClassifier v = new SimLinearClassifier();
        v.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner b0;
    private javax.swing.JSpinner b1;
    private javax.swing.JSpinner b2;
    private javax.swing.JPanel bias;
    private javax.swing.JButton btnInitiate;
    private javax.swing.JButton btnRandom;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnRun;
    private javax.swing.JButton btnStep;
    private javax.swing.JPanel buttonsPanel2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField l0;
    private javax.swing.JTextField l1;
    private javax.swing.JTextField l2;
    private javax.swing.JTextField l3;
    private javax.swing.JTextField l4;
    private javax.swing.JTextField l5;
    private javax.swing.JTextField l6;
    private javax.swing.JTextField l7;
    private javax.swing.JTextField l8;
    private javax.swing.JComboBox lossFunction;
    private javax.swing.JSpinner lr;
    private javax.swing.JFormattedTextField maxIter;
    private javax.swing.JFormattedTextField minError;
    private javax.swing.JPanel process;
    private javax.swing.JTextField s00;
    private javax.swing.JTextField s01;
    private javax.swing.JTextField s02;
    private javax.swing.JTextField s03;
    private javax.swing.JTextField s04;
    private javax.swing.JTextField s05;
    private javax.swing.JTextField s06;
    private javax.swing.JTextField s07;
    private javax.swing.JTextField s08;
    private javax.swing.JTextField s10;
    private javax.swing.JTextField s11;
    private javax.swing.JTextField s12;
    private javax.swing.JTextField s13;
    private javax.swing.JTextField s14;
    private javax.swing.JTextField s15;
    private javax.swing.JTextField s16;
    private javax.swing.JTextField s17;
    private javax.swing.JTextField s18;
    private javax.swing.JTextField s20;
    private javax.swing.JTextField s21;
    private javax.swing.JTextField s22;
    private javax.swing.JTextField s23;
    private javax.swing.JTextField s24;
    private javax.swing.JTextField s25;
    private javax.swing.JTextField s26;
    private javax.swing.JTextField s27;
    private javax.swing.JTextField s28;
    private javax.swing.JSpinner w00;
    private javax.swing.JSpinner w01;
    private javax.swing.JSpinner w10;
    private javax.swing.JSpinner w11;
    private javax.swing.JSpinner w20;
    private javax.swing.JSpinner w21;
    private javax.swing.JPanel weight;
    private javax.swing.JSpinner x00;
    private javax.swing.JSpinner x01;
    private javax.swing.JSpinner x02;
    private javax.swing.JSpinner x03;
    private javax.swing.JSpinner x04;
    private javax.swing.JSpinner x05;
    private javax.swing.JSpinner x06;
    private javax.swing.JSpinner x07;
    private javax.swing.JSpinner x08;
    private javax.swing.JSpinner x10;
    private javax.swing.JSpinner x11;
    private javax.swing.JSpinner x12;
    private javax.swing.JSpinner x13;
    private javax.swing.JSpinner x14;
    private javax.swing.JSpinner x15;
    private javax.swing.JSpinner x16;
    private javax.swing.JSpinner x17;
    private javax.swing.JSpinner x18;
    private javax.swing.JTextField y0;
    private javax.swing.JTextField y1;
    private javax.swing.JTextField y2;
    private javax.swing.JTextField y3;
    private javax.swing.JTextField y4;
    private javax.swing.JTextField y5;
    private javax.swing.JTextField y6;
    private javax.swing.JTextField y7;
    private javax.swing.JTextField y8;
    // End of variables declaration//GEN-END:variables
    public void init() {
        x00.setName("x00");
        x01.setName("x01");
        x02.setName("x02");
        x03.setName("x03");
        x04.setName("x04");
        x05.setName("x05");
        x06.setName("x06");
        x07.setName("x07");
        x08.setName("x08");
        x10.setName("x10");
        x11.setName("x11");
        x12.setName("x12");
        x13.setName("x13");
        x14.setName("x14");
        x15.setName("x15");
        x16.setName("x16");
        x17.setName("x17");
        x18.setName("x18");
        y0.setName("y0");
        y1.setName("y1");
        y2.setName("y2");
        y3.setName("y3");
        y4.setName("y4");
        y5.setName("y5");
        y6.setName("y6");
        y7.setName("y7");
        y8.setName("y8");
        s00.setName("s00");
        s01.setName("s01");
        s02.setName("s02");
        s03.setName("s03");
        s04.setName("s04");
        s05.setName("s05");
        s06.setName("s06");
        s07.setName("s07");
        s08.setName("s08");
        s10.setName("s10");
        s11.setName("s11");
        s12.setName("s12");
        s13.setName("s13");
        s14.setName("s14");
        s15.setName("s15");
        s16.setName("s16");
        s17.setName("s17");
        s18.setName("s18");
        s20.setName("s20");
        s21.setName("s21");
        s22.setName("s22");
        s23.setName("s23");
        s24.setName("s24");
        s25.setName("s25");
        s26.setName("s26");
        s27.setName("s27");
        s28.setName("s28");
        l0.setName("l0");
        l1.setName("l1");
        l2.setName("l2");
        l3.setName("l3");
        l4.setName("l4");
        l5.setName("l5");
        l6.setName("l6");
        l7.setName("l7");
        l8.setName("l8");
        w00.setName("w00");
        w01.setName("w01");
        w10.setName("w10");
        w11.setName("w11");
        w20.setName("w20");
        w21.setName("w21");
        b0.setName("b0");
        b1.setName("b1");
        b2.setName("b2");
        Component[] cm = process.getComponents();
        cp = new HashMap();
        for (Component c : cm) {
            cp.put(c.getName(), c);
        }
        cm = weight.getComponents();
        for (Component c : cm) {
            cp.put(c.getName(), c);
        }
        cm = bias.getComponents();
        for (Component c : cm) {
            cp.put(c.getName(), c);
        }

        ((JSpinner.DefaultEditor) w00.getEditor()).getTextField().setBackground(new Color(204, 255, 255));
        ((JSpinner.DefaultEditor) w01.getEditor()).getTextField().setBackground(new Color(204, 255, 255));
        ((JSpinner.DefaultEditor) b0.getEditor()).getTextField().setBackground(new Color(204, 255, 255));
        ((JSpinner.DefaultEditor) w10.getEditor()).getTextField().setBackground(new Color(204, 255, 204));
        ((JSpinner.DefaultEditor) w11.getEditor()).getTextField().setBackground(new Color(204, 255, 204));
        ((JSpinner.DefaultEditor) b1.getEditor()).getTextField().setBackground(new Color(204, 255, 204));
        ((JSpinner.DefaultEditor) w20.getEditor()).getTextField().setBackground(new Color(255, 204, 204));
        ((JSpinner.DefaultEditor) w21.getEditor()).getTextField().setBackground(new Color(255, 204, 204));
        ((JSpinner.DefaultEditor) b2.getEditor()).getTextField().setBackground(new Color(255, 204, 204));
    }

}
