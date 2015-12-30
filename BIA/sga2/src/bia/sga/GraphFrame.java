/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bia.sga;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JFrame;

/**
 *
 * @author michal
 */
class GraphFrame extends JFrame {

    GraphFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Line-segments evolution");
        setSize((int) SGA.rangeMaxX+20, (int) SGA.rangeMaxY+20);
        setLocation(new Point(50, 50));
        setVisible(true);
        setBackground(Color.white);
    }

    public void paint(Graphics g) {
        for (int i = 0; i < SGA.data.length; i++) {
           drawPoint(g, SGA.data[i][0], SGA.data[i][1]);
        }
    }

    private void drawPoint(Graphics g, double x, double y) {
        g.setColor(Color.red);
        g.drawLine((int) x-3, (int) y, (int) x+3, (int) y);
        g.drawLine((int) x+3, (int) y, (int) x, (int) y+3);
    }

    public void drawIndividual(Individual ind) {
        Graphics g = getGraphics();
        g.clearRect(0, 0, (int) SGA.rangeMaxX+20, (int) SGA.rangeMaxY+20);
        repaint();
        g.setColor(Color.blue);
        for (int i = 0; i < SGA.numSegments; i++) {
            int x1 = (int) ind.genes[4*i];
            int y1 = (int) ind.genes[4*i+1];
            int x2 = (int) ind.genes[4*i+2];
            int y2 = (int) ind.genes[4*i+3];

            g.drawLine(x1,y1,x2,y2);
        }

    }
}
