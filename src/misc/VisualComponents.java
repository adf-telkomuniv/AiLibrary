/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

/**
 *
 * @author dee
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VisualComponents extends JComponent {

    private static class Line {

        final double x1;
        final double y1;
        final double x2;
        final double y2;
        final Color color;

        public Line(double x1, double y1, double x2, double y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }

    private static class Dot {

        final double x1;
        final double y1;
        final Color color;

        public Dot(double x1, double y1, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.color = color;
        }
    }

    private final LinkedList<Dot> dots = new LinkedList<Dot>();

    public void addDot(double x1, double x2, Color color) {
        dots.add(new Dot(x1, x2, color));
        paintDot((Graphics2D) getGraphics());
    }

    public void addDot(double x1, double x2, Color color, int dim) {
        dots.add(new Dot(x1, x2, color));
        paintDot((Graphics2D) getGraphics(), dim);
    }

    public void clearDots() {
        depaintDot((Graphics2D) getGraphics());
        dots.clear();
//        repaint();
    }

    private final LinkedList<Line> lines = new LinkedList<Line>();

    public void addLine(double x1, double y1, double x2, double y2, Color color) {
        lines.add(new Line(x1, y1, x2, y2, color));
        paintLine((Graphics2D) getGraphics());
    }

    public void clearLines() {
        depaintLine((Graphics2D) getGraphics());
        lines.clear();
//        repaint();
    }

    protected void paintDot(Graphics2D g) {
        super.paintComponent(g);
        for (Dot dot : dots) {
            g.setColor(dot.color);
            Shape d = new Ellipse2D.Double(dot.x1, dot.y1, 15, 15);
            g.draw(d);
        }
    }

    protected void paintDot(Graphics2D g, int dim) {
        super.paintComponent(g);
        for (Dot dot : dots) {
            g.setColor(dot.color);
            Shape d = new Ellipse2D.Double(dot.x1, dot.y1, dim, dim);
            g.draw(d);
        }
    }

    protected void depaintDot(Graphics2D g) {
        super.paintComponent(g);
        for (Dot dot : dots) {
            g.setColor(new Color(240, 240, 240));
            Shape d = new Ellipse2D.Double(dot.x1, dot.y1, 15, 15);
            g.draw(d);
        }
    }

    protected void paintLine(Graphics2D g) {
        super.paintComponent(g);
        for (Line line : lines) {
            g.setColor(line.color);
            Shape l = new Line2D.Double(line.x1, line.y1, line.x2, line.y2);
            g.draw(l);
        }
    }

    protected void depaintLine(Graphics2D g) {
        super.paintComponent(g);
        for (Line line : lines) {
            g.setColor(new Color(240, 240, 240));
            Shape l = new Line2D.Double(line.x1, line.y1, line.x2, line.y2);
            g.draw(l);
        }
    }
//
//    public static void main(String[] args) {
//        JFrame testFrame = new JFrame();
//        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        final VisualComponents comp = new VisualComponents();
//        comp.setPreferredSize(new Dimension(320, 200));
//        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
//        JPanel buttonsPanel = new JPanel();
//        JButton newLineButton = new JButton("New Line");
//        JButton clearButton = new JButton("Clear");
//        buttonsPanel.add(newLineButton);
//        buttonsPanel.add(clearButton);
//        testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
//        newLineButton.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int x1 = (int) (Math.random() * 320);
//                int x2 = (int) (Math.random() * 320);
//                int y1 = (int) (Math.random() * 200);
//                int y2 = (int) (Math.random() * 200);
//                Color randomColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
//                comp.addLine(x1, y1, x2, y2, randomColor);
//                comp.addDot(x1, x2, randomColor);
//            }
//        });
//        clearButton.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                comp.clearLines();
//            }
//        });
//        testFrame.pack();
//        testFrame.setVisible(true);
//
//        Model m = new Model();
//        for (int i = 0; i < m.getNumData(); i++) {
////            System.out.println(m.data[i][0] + "-" + m.data[i][1]);
//            comp.addDot(m.getData()[i][0] * 100 + 100, m.getData()[i][1] * 100 + 100, m.getDataColor()[m.getLabels()[i]]);
//        }
//    }

}
