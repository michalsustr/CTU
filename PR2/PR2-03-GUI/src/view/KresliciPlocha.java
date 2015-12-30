package view;

import model.Bod;
import model.Chaloupka;
import model.Ctverec;
import model.GrO;
import model.Obdelnik;
import model.Trojuhelnik;
import model.TypySrafovani;
import model.Usecka;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Miroslav Bal�k
 * Tato třída reprezentuje kreslící plochu. Základní metoda pro vykreslování je metoda paint,
která má jako parametr Graphics g. Ostatní metody umožňují dělat obsluhy jednotlivých událostí klávesnice
a myši
 */
public class KresliciPlocha extends JToggleButton {

    private GrO[] pole;

    public KresliciPlocha() {
        super();
        pole = new GrO[8];
        pole[0] = new Trojuhelnik(TypySrafovani.NIC, Color.RED, new Point(20, 30), new Point(40, 200), new Point(100, 100));
        pole[1] = new Ctverec(TypySrafovani.PLNE, Color.BLUE, new Point(50, 250), 60);
        pole[2] = new Bod(Color.MAGENTA, new Point(150, 100));
        pole[3] = new Usecka(new Color(0x964B00), new Point(100, 50), new Point(300, 100));
        pole[4] = new Chaloupka(TypySrafovani.NIC, Color.BLACK, new Point(200, 200), 100);
        pole[5] = new Obdelnik(TypySrafovani.SVISLEPRUHY, Color.GREEN, new Point(200,20), 80, 30);
        pole[6] = new Obdelnik(TypySrafovani.VODOROVNEPRUHY, Color.BLACK, new Point(300,60), 80, 20);
        pole[7] = new Obdelnik(TypySrafovani.NIC, Color.BLUE, new Point(350,150), 20, 50);
        setBorderPainted(false);//nechceme kreslit ohrani4en9
        setContentAreaFilled(false);//bez vyplne
        //       this.setBackground(new Color(0,0,255)); //nastaveni barvy pozadi na barvu modrou - slozky r,g,b
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        g.fillRect(0,0,500,500);
        for (GrO grO : pole) {
            grO.kresliSe(g);
        }
    }
}
