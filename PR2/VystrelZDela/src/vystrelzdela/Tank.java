package vystrelzdela;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Trida reprezentujici tank
 * @author Radek Malinsky
 */
public class Tank {

    /** pozice tanku v okne */
    protected Point pozice;
    /** pocatecni uhel dela v radianech a pocatecni rychlost strely */
    protected double uhelDela, rychlostStrely;
    /** poloha konce dela - pozice od ktere poleti strela */
    private double konecDelaX, konecDelaY;

    /**
     * Vytvoreni noveho tanku
     * @param pozice x, y souradnice
     * @param uhelDela pocatecni uhel dela
     * @param rychlostStrely pocatecni rychlost strely
     */
    public Tank(Point pozice, double uhelDela, double rychlostStrely) {
        this.pozice = pozice;
        /* aktualizuj polohu dela v radianech = pozice od ktere poleti strela */
        aktualizujPolohuDela(this.uhelDela = uhelDela * Math.PI / 180);
        this.rychlostStrely = rychlostStrely;
    }

    /**
     * Vrati pocatecni rychlost strely
     * @return rychlost strely
     */
    public double getRychlostStrely() {
        return rychlostStrely;
    }

    /**
     * Nastavi pocatecni rychlost strely
     * @param rychlostStrely rychlost strely
     */
    public void setRychlostStrely(double rychlostStrely) {
        this.rychlostStrely = rychlostStrely;
    }

    /**
     * Vrati pocatecni uhel dela
     * @return uhel dela
     */
    public double getUhelDela() {
        return uhelDela * 180 / Math.PI;
    }

    /**
     * Nastavi pocatecni uhel dela
     * @param uhelDela uhel dela
     */
    public void setUhelDela(double uhelDela) {
        /* aktualizuj polohu dela v radianech radiany */
        aktualizujPolohuDela(this.uhelDela = uhelDela * Math.PI / 180);
    }

    /**
     * Aktualizace polohy konce dela po zneme uhlu dela = pozice od ktere poleti strela
     * @param uhelDela uhel dela v radianech
     */
    private void aktualizujPolohuDela(double uhelDela) {
        this.konecDelaX = pozice.x + 40 + 60 * Math.cos(uhelDela);
        this.konecDelaY = pozice.y - 22 - 60 * Math.sin(uhelDela);
    }

    /**
     * Vykresleni tanku
     * @param g graficky kontext
     */
    public void kresli(Graphics g) {
        /* potomek Graphics - pridava nove vlastnosti */
        Graphics2D g2 = (Graphics2D) g;

        int velikostKola = 20;
        /* kola */
        for (int i = 0; i < 4; i++) {
            g2.drawOval(pozice.x + i * velikostKola, pozice.y, velikostKola, velikostKola);
        }
        /* telo tanku */
        g2.drawOval(pozice.x - 10, pozice.y - 10, 100, 25);
        /* strilna */
        g2.drawOval(pozice.x + 25, pozice.y - 25, 30, 20);

        /* uhel otoceni v radianech a souradnice bodu, kolem ktereho dojde k otoceni */
        g2.rotate(-uhelDela, pozice.x + 40, pozice.y - 22);
        /* delo */
        g2.drawRect(pozice.x + 40, pozice.y - 22, 60, 10);
    }

    /**
     * Vystrel z dela
     * @param g graficky kontext, na ktery bude strela vykreslena
     * je ziskan z kontejneru JApplet
     */
    public void vystrel(Graphics g) {
        /* kazda strela je reprezentovana jednim vlaknem */
        Thread t = new Thread(new Strela(g));
        t.start(); /* spusti metodu run() tridy Strela */

    }

    /**
     * Trida reprezentujici strelu vystrelenou z dela tanku
     * - vnitrni trida => vidi promenne a metody nadrazene tridy Tank
     *                 => nelze vytvorit Strelu dokud nebude existovat Tank
     */
    class Strela implements Runnable {

        /** ziskany graficky kontext */
        private Graphics g;

        /**
         * Konstruktor tridy Strela
         * @param g graficky kontext komponenty, na kterou se bude strela kreslit
         */
        public Strela(Graphics g) {
            this.g = g;
        }

        /** Metoda kterou provede vlakno po spusteni */
        @SuppressWarnings("SleepWhileHoldingLock")
        public void run() {
            double puvodniX = konecDelaX, puvodniY = konecDelaY,
                    aktualniX = konecDelaX, aktualniY = konecDelaY,
                    t = 0; /* cas */

            while (aktualniY <= pozice.y) {
                /* x = x0 + v0 * t * cos(alpha) */
                aktualniX = puvodniX + rychlostStrely * t * Math.cos(uhelDela);
                /* y = y0 + v0 * t * sin(alpha) + 1/2 * g * t^2 */
                aktualniY = puvodniY - rychlostStrely * t * Math.sin(uhelDela) + 0.5 * 9.81 * t * t;

                kresliSrelu((int) puvodniX, (int) puvodniY, (int) aktualniX, (int) aktualniY);

                puvodniX = aktualniX;
                puvodniY = aktualniY;
                /* rychlost zmeny casu = pocet kroku strely */
                t += 0.1;

                try {
                    /* zpomaleni vykreslovani */
                    Thread.sleep(50L);
                } catch (InterruptedException ex) {
                    /* pokud se vlakno probudi, strela bude velmi rychla */
                }
            }
        }

        /**
         * Vykresleni strely
         * @param puvodniX puvodni pozice x-souradnice
         * @param puvodniY puvodni pozice y-souradnice
         * @param aktualniX aktualni pozice x-souradnice
         * @param aktualniY aktualni pozice y-souradnice
         */
        public void kresliSrelu(int puvodniX, int puvodniY, int aktualniX, int aktualniY) {
            /* potomek Graphics - pridava nove vlastnosti */
            Graphics2D g2 = (Graphics2D) g;
            /* prekresleni puvodni strely */
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillOval(puvodniX, puvodniY, 10, 10);
            /* vykresleni nove strely */
            g2.setColor(Color.BLACK);
            g2.fillOval(aktualniX, aktualniY, 10, 10);
        }
    }
}
