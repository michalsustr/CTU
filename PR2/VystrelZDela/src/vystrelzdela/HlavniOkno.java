package vystrelzdela;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Vystrel z dela tanku
 * @author Radek Malinsky
 */
public class HlavniOkno extends JApplet {

    /** tank */
    protected Tank tank;
    private JToolBar jToolBar;
    private JLabel autorLabel,
            uhelLabel, rychlostLabel,
            uhelHodnotaLabel, rychlostHodnotaLabel;
    private JPanel autorPanel, ovladaciPanel, sliderPanel;
    private JButton popisButton, strilejButton;
    private JSlider uhelSlider, rychlostSlider;

    /**
     * Konstruktor s inicializaci tanku
     * - volan pred init()
     */
    public HlavniOkno() {
        /* nastaveni pocatecnich vlastnosti tanku
         * - pozice, uhel dela, rychlost strely */
        tank = new Tank(new Point(50, 350), 10, 20);
    }

    /** Inicializace grafickych komponent */
    @Override
    public void init() {
        /* Graficka aplikace nesmi byt zpracovana hlavnim vlaknem
         * - je vytvoreno tzv. Event Dispatch Thread (EDT), ktere
         *   zpracovava frontu prekreslujicich se komponent */
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    initComponents();
                }
            });
        } catch (Exception ex) {
            System.err.println("Inicializace grafickych komponent nebyla korektne dokoncena.");
        }
    }

    @Override
    public void paint(Graphics g) {
        /* vykresleni rodice - tlacitka atd. */
        super.paint(g);
        /* vykresleni tanku */
        tank.kresli(g);
    }
    /** Obraz zastupujici zobrazeni pri prekreslovani pomoci dvojiteho bufferu */
    private Image doubleBuffer;

    /**
     * Prekreslovaci metoda vyuzivajici dvojiteho bufferu
     * - zamezi blikani obrazovky behem prekreslovani
     * @param g Graphics
     */
    @Override
    public void update(Graphics g) {
        Dimension size = getSize();
        /* vytvoreni obrazu */
        if (doubleBuffer == null
                || doubleBuffer.getWidth(this) != size.width
                || doubleBuffer.getHeight(this) != size.height) {
            doubleBuffer = createImage(size.width, size.height);
        }

        if (doubleBuffer != null) {
            /* kreslit do dvojiteho bufferu */
            Graphics g2 = doubleBuffer.getGraphics();
            paint(g2);
            g2.dispose();

            /* zkopirovat dvojity buffer na obrazovku */
            g.drawImage(doubleBuffer, 0, 0, null);
        } else {
            /* nebylo mozne vytvorit dvojity buffer */
            paint(g);
        }
    }

    /** Inicializace grafickych komponent - volana z init() */
    private void initComponents() {
        /* horni nastrojova lista */
        add(jToolBar = new JToolBar(), BorderLayout.NORTH);

        jToolBar.add(popisButton = new JButton("Na co se zaměřit"));
        popisButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                new PopisAplikaceOkno().setVisible(true);
            }
        });

        jToolBar.add(autorPanel = new JPanel(new BorderLayout()));
        autorPanel.add(autorLabel = new JLabel("© RM "), BorderLayout.EAST);
        autorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));

        /* dolni ovladaci panel tanku */
        add(ovladaciPanel = new JPanel(new BorderLayout()), BorderLayout.SOUTH);
        /* slider panel */
        ovladaciPanel.add(sliderPanel = new JPanel(new GridLayout(3, 2)), BorderLayout.CENTER);

        sliderPanel.add(uhelLabel = new JLabel("Nastavení úhlu děla:"));
        uhelLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        uhelLabel.setHorizontalAlignment(SwingConstants.CENTER);

        sliderPanel.add(rychlostLabel = new JLabel("Nastavení rychlosti střely:"));
        rychlostLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        rychlostLabel.setHorizontalAlignment(SwingConstants.CENTER);

        sliderPanel.add(uhelSlider = new JSlider(0, 90, (int) tank.getUhelDela()));
        uhelSlider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent evt) {
                int uhel = uhelSlider.getValue();
                uhelHodnotaLabel.setText("" + uhel);
                tank.setUhelDela(uhel);
                update(getGraphics());
            }
        });

        sliderPanel.add(rychlostSlider = new JSlider(0, 50, (int) tank.getRychlostStrely()));
        rychlostSlider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent evt) {
                int rychlost = rychlostSlider.getValue();
                rychlostHodnotaLabel.setText("" + rychlost);
                tank.setRychlostStrely(rychlost);
            }
        });

        sliderPanel.add(uhelHodnotaLabel = new JLabel("" + uhelSlider.getValue(), SwingConstants.CENTER));
        sliderPanel.add(rychlostHodnotaLabel = new JLabel("" + rychlostSlider.getValue(), SwingConstants.CENTER));
        /* tlacitko Strilej */
        ovladaciPanel.add(strilejButton = new JButton("Střílej"), BorderLayout.EAST);
        strilejButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                /* ziskani grafickeho kontextu pro strelu */
                tank.vystrel(getGraphics());
            }
        });
    }

    /**
     * Spusteni appletu jako aplikace
     * @param args String[]
     */
    public static void main(String[] args) {
        HlavniOkno applet = new HlavniOkno();
        JFrame okno = new JFrame("Sřílení z tanku");
        okno.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        okno.setSize(640, 480);
        okno.setContentPane(applet);
        /* zavolni metod init() a start() tak, jak by to udelal webovy prohlizec */
        applet.init();
        applet.start();
        /* umisteni okna do stredu obrazovky */
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        okno.setLocation((d.width - okno.getSize().width) / 2, (d.height - okno.getSize().height) / 2);
        okno.setVisible(true);
    }
}
