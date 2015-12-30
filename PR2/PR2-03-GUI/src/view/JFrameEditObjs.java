package view;

import java.awt.Color;
import javax.swing.*;



public class JFrameEditObjs extends JFrame{
        JTextField jTextField1 = new JTextField();
        JLabel jLabel1 = new JLabel("Název objektu");
        JButton jButtonOK = new JButton("O.K.");
        JButton jButtonZrus = new JButton("Zrušit");
        JRadioButton jRadioButton1 = new JRadioButton();
        JRadioButton jRadioButton2 = new JRadioButton();
        JRadioButton jRadioButton3 = new JRadioButton();

    public JFrameEditObjs() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(jTextField1);
        jTextField1.setBounds(130, 20, 59, 20);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 20, 80, 20);
        getContentPane().add(jButtonOK);
        jButtonOK.setBounds(200, 120, 70, 23);
        getContentPane().add(jButtonZrus);
        jButtonZrus.setBounds(120, 120, 70, 23);
        jRadioButton1.setBackground(Color.RED);
        getContentPane().add(jRadioButton1);
        jRadioButton1.setBounds(10, 60, 50, 23);
        jRadioButton2.setBackground(new java.awt.Color(0,200, 0));
        getContentPane().add(jRadioButton2);
        jRadioButton2.setBounds(70, 60, 50, 23);
        jRadioButton3.setBackground(new java.awt.Color(0, 0,150));
        getContentPane().add(jRadioButton3);
        jRadioButton3.setBounds(130, 60, 50, 23);
        ButtonGroup bg = new ButtonGroup();
        bg.add(jRadioButton1);bg.add(jRadioButton2);bg.add(jRadioButton3);
        jRadioButton1.setSelected(true);//implicitní barva
        this.setSize(300,200);
    }
}
