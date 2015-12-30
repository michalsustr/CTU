/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package udalosti;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author michal
 */
public class Citac extends JFrame {

	protected JButton tlacitko;
	protected JLabel popisek;
	protected int hodnota;
    /**
     * @param args the command line arguments
     */
    public Citac() {
        setSize(100,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d.width-getSize().width)/2, (d.height-getSize().height)/2);

		

		popisek = new JLabel(""+hodnota);
		popisek.setHorizontalAlignment((int) CENTER_ALIGNMENT);

		tlacitko = new JButton("Stlač mě");
		tlacitko.addActionListener(new Posluchac1(this));

		BorderLayout bl = new BorderLayout();
		setLayout(bl);


		add(tlacitko, BorderLayout.SOUTH);
		add(popisek, BorderLayout.CENTER);

		//pack();

    }

	public void setHodnotaCitace(int hodnota) {
		this.hodnota = hodnota;
		popisek.setText(""+this.hodnota);
	}

	public int getHodnota() {
		return this.hodnota;
	}

	public static void main(String[] args) {
		new Citac().setVisible(true);
	}



}
