/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bookreader;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author michal
 */
public class TextPanel extends JPanel {

	private JLabel labels[];
	private int maxLabels = 10;

	public TextPanel() {
		super();
		labels = new JLabel[maxLabels];
		for(int i = 0; i < maxLabels; i++) {
			labels[i] = new JLabel("abc");
			add(labels[i]);
		}
		
	}



	public void setText(String text) {
		
		String words[] = text.split(" ");
		for(int i = 0; i < maxLabels; i++) {
			if(i >= words.length) {
				break;
			}
			labels[i].setText(words[i]);
			labels[i].setSize(200,100);
			System.out.println(""+words[i]);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawString("abcd", 10, 10);

		g2d.drawString("aString", 100, 100);
		AffineTransform rot = new AffineTransform();
		rot.setToRotation(Math.PI / 4.0);
		AffineTransform scale = new AffineTransform();
		scale.setToScale(2, 2);
		scale.concatenate(rot);

		g2d.setTransform(scale);
		g2d.drawString("aString", 120, 20);
	}


	
}
