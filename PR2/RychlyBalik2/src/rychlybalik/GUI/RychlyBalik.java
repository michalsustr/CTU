package rychlybalik.GUI;


import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.awt.*;
import java.awt.Graphics2D;
import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import javax.swing.event.*;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import rychlybalik.Personalistika.*;
import rychlybalik.TypDopravy.*;
import rychlybalik.VozovyPark.*;
import rychlybalik.Zasilka.*;


/**
 *
 * @author Vojta Dokoupil VODOTECH 2011
 */
public class RychlyBalik extends JFrame {

    protected int index;
    protected SpolecneAtributy editovanyVuz;
    protected PraPracovnik editovanyPracovnik;
    protected int radek = -1;
    protected String text1 = "";
    protected String text2 = "";
    protected VozovyPark vozPark;
    protected Smena sezPrac;
    protected SeznamZasilek sezZas;

    // Deklarace grafických prvků
    private JPanel celkovyPanel;
    private JButton pridejPracovnika;
    private JTable seznamPracovniku;
    private JTable seznamVozu;
    private JTable seznamZasilek;
    private JButton pridejVuz;
    private JButton expediceZasilky;
    private JButton odeberZasilku;
    private JButton odeberVuz;
    private JButton editujVuz;
    private JButton odeberPracovnika;
    private JButton editujPracovnika;
    private JButton editujZasilku;
    private JButton vypocetCeny;
    private JPanel panelPracovniku;
    private JPanel panelExpedice;
    private JPanel panelVozu;
    private JTextField panelVoDoTech;
    private JScrollPane scrollerPracovnici;
    private JScrollPane scrollerZasilky;
    private JScrollPane scrollerVozy;
    private JSpinner hmotnostSpinner;
    private JSpinner vzdalenostSpinner;
    private JTextField nadpisHmotnost;
    private JTextField nadpisVzdalenost;
    private JTextPane vybraneExpedovani;
    private JTextPane vyslednaCena;
    private JLabel aktualniCas;
    // Konec deklarace grafických prvků

    public RychlyBalik(VozovyPark neco, Smena neco2, SeznamZasilek neco3) {
        this.vozPark = neco;
        this.sezPrac = neco2;
        this.sezZas = neco3;

       
        setSize(990, 505);
      
        setResizable(false);
        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //velikost okna
        setLocation(((d.width - getSize().width) / 2), ((d.height - getSize().height) / 2));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        

        //GRAFICKÉ KOMPONENTY - INICIALIZACE//
        aktualniCas=new JLabel();
        panelVoDoTech=new JTextField();
        celkovyPanel=new JPanel();
        celkovyPanel.setSize(990, 505);
        celkovyPanel.setBackground(new Color(145, 171, 210));
          setContentPane(celkovyPanel);

        //******DOPRAVA*******************************************************//
 this.timer();


        scrollerVozy = new JScrollPane();
        seznamVozu = new JTable();

        panelVozu = new JPanel();
        pridejVuz = new JButton();
        odeberVuz = new JButton();
        editujVuz = new JButton();

        //******PERSONALISTIKA************************************************//

        scrollerPracovnici = new JScrollPane();
        seznamPracovniku = new JTable();

        panelPracovniku = new JPanel();
        pridejPracovnika = new JButton();
        odeberPracovnika = new JButton();
        editujPracovnika = new JButton();

        scrollerZasilky = new JScrollPane();
        seznamZasilek = new JTable();

        //******EXPEDICE******************************************************//

        panelExpedice = new JPanel();
        vzdalenostSpinner = new JSpinner();
        hmotnostSpinner = new JSpinner();
        vybraneExpedovani = new JTextPane();
        vyslednaCena = new JTextPane();
        nadpisHmotnost = new JTextField();
        nadpisVzdalenost = new JTextField();
        editujZasilku = new JButton();
        vypocetCeny = new JButton();
        expediceZasilky = new JButton();
        odeberZasilku = new JButton();

        //KONEC INICIALIZACE//



        //***************PROCHÁZENÍ TABULKY VOZŮ****************//

        seznamVozu.setModel(vozPark);                                            //označování vybraného prostředku expedování
        scrollerVozy.setViewportView(seznamVozu);
        seznamVozu.setRowSelectionAllowed(true);

        ListSelectionModel model = seznamVozu.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                radek = seznamVozu.getSelectedRow();

                String text = "";

                for (int i = 0; i < seznamVozu.getColumnCount(); i++) {
                    text += seznamVozu.getValueAt(radek, i) + " ";
                }
                text1 = text;
                vybraneExpedovani.setText(" " + text + "\n " + text2);
                odeberVuz.setEnabled(true);
                editujVuz.setEnabled(true);

            }
        });

        //***************PROCHÁZENÍ TABULKY PRACOVNÍKŮ****************//

        seznamPracovniku.setModel(sezPrac);                                     //označování vybraného pracovníka expedování
        scrollerPracovnici.setViewportView(seznamPracovniku);
        seznamVozu.setRowSelectionAllowed(true);

        ListSelectionModel model2 = seznamPracovniku.getSelectionModel();
        model2.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int radek = seznamPracovniku.getSelectedRow();
                String text = "";

                for (int i = 0; i < seznamPracovniku.getColumnCount(); i++) {
                    text += seznamPracovniku.getValueAt(radek, i) + " ";
                }
                text2 = text;
                vybraneExpedovani.setText(" " + text1 + "\n " + text);
                odeberPracovnika.setEnabled(true);
                editujPracovnika.setEnabled(true);
            }
        });

        seznamZasilek.setModel(sezZas);
        seznamZasilek.setBackground(Color.YELLOW);
        seznamZasilek.setFont(new java.awt.Font("Tahoma", 1, 11));
        scrollerZasilky.setViewportView(seznamZasilek);

        panelVozu.setBorder(BorderFactory.createTitledBorder("Oddělení dopravy"));
        panelVozu.setBackground(new Color(145, 171, 210));
        panelVozu.setAlignmentX(50.0F);
        panelVozu.setAlignmentY(50.0F);
        panelVozu.setLayout(new AbsoluteLayout());

        pridejVuz.setText("Přidej prostředek");
        pridejVuz.setFont(new Font("Tahoma", 1, 11));

        pridejVuz.setBackground(new Color(242, 242, 242));
       
        pridejVuz.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                PridejProstredek(evt);
            }
        });
        panelVozu.add(pridejVuz, new AbsoluteConstraints(40, 30, 150, 30));

        odeberVuz.setText("Odeber prostředek");
        odeberVuz.setFont(new Font("Tahoma", 1, 11));
       
        odeberVuz.setBackground(new Color(242, 242, 242));
        odeberVuz.setEnabled(false);
        odeberVuz.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                OdeberVuzActionPerformed(evt);
            }
        });

        panelVozu.add(odeberVuz, new AbsoluteConstraints(40, 80, 150, 30));

        editujVuz.setText("Edituj Prostředek");
        editujVuz.setFont(new Font("Tahoma", 1, 11));
     
        editujVuz.setBackground(new Color(242, 242, 242));
        editujVuz.setEnabled(false);
        editujVuz.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                EditujProstredekActionPerformed(evt);
            }
        });
        panelVozu.add(editujVuz, new AbsoluteConstraints(40, 130, 150, 30));

        panelPracovniku.setBorder(BorderFactory.createTitledBorder("Personální oddělení"));
        
        panelPracovniku.setBackground(new Color(145, 171, 210));
        panelPracovniku.setLayout(new AbsoluteLayout());

        pridejPracovnika.setText("Přidej pracovníka");
        pridejPracovnika.setFont(new Font("Tahoma", 1, 11));
        
        pridejPracovnika.setBackground(new Color(242, 242, 242));
        pridejPracovnika.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                PridejPracovnikaActionPerformed(evt);
            }
        });
        panelPracovniku.add(pridejPracovnika, new AbsoluteConstraints(60, 30, 160, 30));

        odeberPracovnika.setText("Odeber pracovníka");
        odeberPracovnika.setFont(new Font("Tahoma", 1, 11));
        
        odeberPracovnika.setBackground(new Color(242, 242, 242));
        odeberPracovnika.setEnabled(false);

        odeberPracovnika.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                OdeberPracovnikaActionPerformed(evt);
            }
        });
        panelPracovniku.add(odeberPracovnika, new AbsoluteConstraints(60, 80, 160, 30));

        editujPracovnika.setText("Edituj pracovníka");
        editujPracovnika.setFont(new Font("Tahoma", 1, 11));
       
        editujPracovnika.setBackground(new Color(242, 242, 242));
        editujPracovnika.setEnabled(false);
        editujPracovnika.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                EditujPracovnikaActionPerformed(evt);
            }
        });
        panelPracovniku.add(editujPracovnika, new AbsoluteConstraints(60, 130, 160, 30));

        panelExpedice.setBorder(BorderFactory.createTitledBorder("Expediční oddělení"));

        panelExpedice.setBackground(new Color(145, 171, 210));
        panelExpedice.setLayout(new AbsoluteLayout());



        vzdalenostSpinner.setModel(new SpinnerNumberModel(1.00d, 0.00d, 4000.00d, 0.05d));
        vzdalenostSpinner.setBorder(null);//nastavování spinnerů
        vzdalenostSpinner.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelExpedice.add(vzdalenostSpinner, new AbsoluteConstraints(120, 82, 100, 40));        //spinner rychlosti.

        hmotnostSpinner.setModel(new SpinnerNumberModel(0.25d, 0.00d, 4000.00d, 0.05d));
        hmotnostSpinner.setBorder(null);//nastavování spinnerů
        hmotnostSpinner.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelExpedice.add(hmotnostSpinner, new AbsoluteConstraints(15, 82, 100, 40));
        

        
        vybraneExpedovani.setEditable(false);
        vybraneExpedovani.setFont(new java.awt.Font("Tahoma", 1, 11));
        vybraneExpedovani.setForeground(Color.blue);

       vyslednaCena.setEditable(false);
        vyslednaCena.setFont(new Font("Tahoma", 1, 11));
        vyslednaCena.setForeground(Color.blue);



        panelExpedice.add(vybraneExpedovani, new AbsoluteConstraints(15, 20, 180, 32));       //přidávání vybraného expedování
        panelExpedice.add(vyslednaCena, new AbsoluteConstraints(216, 20, 80, 32));
        nadpisHmotnost.setBackground(new Color(145, 171, 210));
        nadpisHmotnost.setHorizontalAlignment(JTextField.CENTER);
        nadpisHmotnost.setText("hmotnost [kg]");
        nadpisHmotnost.setBorder(null);

        panelExpedice.add(nadpisHmotnost, new AbsoluteConstraints(15, 60, 100, 20));

        nadpisVzdalenost.setBackground(new Color(145, 171, 210));
        nadpisVzdalenost.setHorizontalAlignment(JTextField.CENTER);
        nadpisVzdalenost.setText("vzdálenost [km]");
        nadpisVzdalenost.setBorder(null);

        panelExpedice.add(nadpisVzdalenost, new AbsoluteConstraints(120, 60, 100, 20));

        editujZasilku.setText("Edituj info");
        editujZasilku.setBackground(new Color(242, 242, 242));
        
        panelExpedice.add(editujZasilku, new AbsoluteConstraints(15, 230, 205, 30));

        vypocetCeny.setText("Výpočet ceny");
        
        vypocetCeny.setBackground(new Color(242, 242, 242));
        vypocetCeny.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                VypocetCenyActionPerformed(evt);
            }
        });
        panelExpedice.add(vypocetCeny, new AbsoluteConstraints(15, 140, 205, 30));

        expediceZasilky.setBackground(new Color(242, 242, 242));
        expediceZasilky.setFont(new Font("Tahoma", 1, 13));
        expediceZasilky.setForeground(Color.blue);
        expediceZasilky.setText("Expedice zásilky");
        expediceZasilky.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ExpediceZasilkyActionPerformed(evt);
            }
        });

        panelExpedice.add(expediceZasilky, new AbsoluteConstraints(250, 120, 160,50));


        aktualniCas.setText("Hodiny");
        panelExpedice.add(aktualniCas, new AbsoluteConstraints(305, 160, 160,50));

        odeberZasilku.setText("Odvolej expedování");
             odeberZasilku.setBackground(new Color(242, 242, 242));
        odeberZasilku.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
                OdeberZasilkuPerformed(evt);
            }


        });

        panelExpedice.add(odeberZasilku, new AbsoluteConstraints(15, 190, 205, 30));

        panelVoDoTech.setBackground(new Color(145, 171, 210));
        panelVoDoTech.setFont(new Font ("Tahoma", 1, 9));
        panelVoDoTech.setForeground(new Color(242, 242, 242));
        panelVoDoTech.setHorizontalAlignment(JTextField.CENTER);
        panelVoDoTech.setText("VoDoTech ©");
        panelVoDoTech.setBorder(null);

        panelExpedice.add(panelVoDoTech, new AbsoluteConstraints(350, 250, 70, 20));

        //--------------------------HLAVNÍ OKNO - STAVBA --------------------------//

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(

                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(10, 10, 10)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(scrollerVozy, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(scrollerPracovnici, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)
                        )
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(panelVozu, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(panelPracovniku, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)
                            )
                    )
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(scrollerZasilky, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelExpedice, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE)
                        )
                  
                )

                );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup().addGap(11, 11, 11)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                          .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                             .addComponent(scrollerPracovnici, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
                                             .addComponent(scrollerVozy, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                              .addComponent(panelPracovniku, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                                              .addComponent(panelVozu, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)))
                         .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollerZasilky, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelExpedice, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)))
                        
                                .addContainerGap()
                ));

        
    }

    public void setVozPark(VozovyPark vozPark) {
        this.vozPark = vozPark;
    }

    public VozovyPark getVozPark() {
        return vozPark;
    }

    public Smena getSezPrac() {
        return sezPrac;
    }

    public void setSezPrac(Smena sezPrac) {
        this.sezPrac = sezPrac;
    }

   

    private void EditujProstredekActionPerformed(ActionEvent evt) {
        if (seznamVozu.getSelectedRow() != -1 && seznamVozu.getSelectedRow() < vozPark.getPocetVozu()) {
            index = seznamVozu.getSelectedRow();
            editovanyVuz = vozPark.getSeznamVozu().get(index);

            VuzEdit okynko = new VuzEdit(this);
            okynko.setVisible(true);
            okynko.setTitle("EDITACE");
        }

    }

    public SpolecneAtributy getEditovanyVuz() {
        return editovanyVuz;
    }

    public int getIndex() {
        return index;
    }

    private void OdeberPracovnikaActionPerformed(java.awt.event.ActionEvent evt) {
        if (seznamPracovniku.getSelectedRow() != -1 && seznamPracovniku.getSelectedRow() < sezPrac.getPocetPracovniku()) {
            PotvrdOdeberPracovnika okno = new PotvrdOdeberPracovnika(this, seznamPracovniku.getSelectedRow());
            okno.setVisible(true);
        }
    }

    private void OdeberZasilkuPerformed(ActionEvent evt) {
                 if (seznamZasilek.getSelectedRow() != -1 && seznamZasilek.getSelectedRow() < sezZas.getPocetZasilek()) {
            PotvrdOdeberZasilku okno = new PotvrdOdeberZasilku(this, seznamZasilek.getSelectedRow());
            okno.setVisible(true);
        }
            }

  
    private void VypocetCenyActionPerformed(java.awt.event.ActionEvent evt) {
        double hmotnost = Double.valueOf(hmotnostSpinner.getModel().getValue().toString());
        index = seznamPracovniku.getSelectedRow();


    if((seznamPracovniku.getSelectedRow() == -1 || seznamPracovniku.getSelectedRow() > sezPrac.getPocetPracovniku())||(seznamVozu.getSelectedRow() == -1 || seznamVozu.getSelectedRow() > vozPark.getPocetVozu())){
    WarningVstup okynko1 = new WarningVstup(this, "Nevybral jsi vše potřebné\npro výpočet ceny!!");
            okynko1.setVisible(true);
    }    else

    {
            if (vozPark.getSeznamVozu().get(seznamVozu.getSelectedRow()).getNosnost() < hmotnost) {
            WarningVstup okynko1 = new WarningVstup(this, "Zadaný způsob dopravy\nnení schpen uvézt\nzásilku dané hmotnosti !!");
            okynko1.setVisible(true);

        } else {
            if (sezPrac.getSmena().get(index) instanceof Administrator == true) {
                WarningVstup okynko1 = new WarningVstup(this, "Administrativní pracovník\n nemůže dopravovat zásilku !!");
                okynko1.setVisible(true);

            } else {
                if (sezPrac.getMuzeRidit(index, vozPark.getSeznamVozu().get(seznamVozu.getSelectedRow())) == false) {
                    WarningVstup okynko1 = new WarningVstup(this, "Vybraný pracovník nemůže řídit\nvybraný prostředek !!");
                    okynko1.setVisible(true);

                } else {

                    double vzdalenost = Double.valueOf(vzdalenostSpinner.getModel().getValue().toString());
                    double cena = vozPark.getSeznamVozu().get(seznamVozu.getSelectedRow()).getCenaZaDoruceni(vzdalenost);
                    DecimalFormat df = new DecimalFormat("#.##");
                    vyslednaCena.setText("Cena: " + df.format(cena) + " Kč");


                }
            }
        }}
    }

    private void EditujPracovnikaActionPerformed(java.awt.event.ActionEvent evt) {
        if (seznamPracovniku.getSelectedRow() != -1 && seznamPracovniku.getSelectedRow() < sezPrac.getPocetPracovniku()) {
            index = seznamPracovniku.getSelectedRow();
            editovanyPracovnik = sezPrac.getSmena().get(index);

            PracovnikEdit okynko = new PracovnikEdit(this);
            okynko.setVisible(true);
            okynko.setTitle("EDITACE");

        }
    }

    public PraPracovnik getEditovanyPracovnik() {
        return editovanyPracovnik;
    }

    private void ExpediceZasilkyActionPerformed(java.awt.event.ActionEvent evt) {
        double hmotnost = Double.valueOf(hmotnostSpinner.getModel().getValue().toString());
        index = seznamPracovniku.getSelectedRow();

 if((seznamPracovniku.getSelectedRow() == -1 || seznamPracovniku.getSelectedRow() > sezPrac.getPocetPracovniku())||(seznamVozu.getSelectedRow() == -1 || seznamVozu.getSelectedRow() > vozPark.getPocetVozu())){
    WarningVstup okynko1 = new WarningVstup(this, "Nevybral jsi vše potřebné\npro expedici zásilky!!");
            okynko1.setVisible(true);
    }    else

    {

        if (vozPark.getSeznamVozu().get(seznamVozu.getSelectedRow()).getNosnost() < hmotnost) {
            WarningVstup okynko1 = new WarningVstup(this, "Zadaný způsob dopravy\nnení schpen uvézt\nzásilku dané hmotnosti !!");
            okynko1.setVisible(true);

        } else {
            if (sezPrac.getSmena().get(index) instanceof Administrator == true) {
                WarningVstup okynko1 = new WarningVstup(this, "Administrativní pracovník\n nemůže dopravovat zásilku !!");
                okynko1.setVisible(true);

            } else {
                if (sezPrac.getMuzeRidit(index, vozPark.getSeznamVozu().get(seznamVozu.getSelectedRow())) == false) {
                    WarningVstup okynko1 = new WarningVstup(this, "Vybraný pracovník nemůže řídit\nvybraný prostředek !!");
                    okynko1.setVisible(true);

                } else {
                    if (vozPark.getSeznamVozu().get(seznamVozu.getSelectedRow()).isExpeding()==true || sezPrac.getSmena().get(index).isExpeding()==true) {
                        WarningVstup okynko1 = new WarningVstup(this, "Pracovník nebo prostředek\n již expeduje!!");
                        okynko1.setVisible(true);


                    } else {

                        double vzdalenost = Double.valueOf(vzdalenostSpinner.getModel().getValue().toString());
                        Zasilka krabka = new Zasilka(hmotnost, vzdalenost);
                        krabka.setDopravator(sezPrac.getSmena().get(index));
                        krabka.setProstredek(vozPark.getSeznamVozu().get(seznamVozu.getSelectedRow()));
                        sezZas.pridejZasilku(krabka);
                        vozPark.getSeznamVozu().get(seznamVozu.getSelectedRow()).setExpeding(true);
                        sezPrac.getSmena().get(index).setExpeding(true);
                        refreshSeznamuZasilek();

                    }}
                }
            }
        }}

    private

     void OdeberVuzActionPerformed(java.awt.event.ActionEvent evt) {                     //odebírání vozu z Arraylistu.
        if (seznamVozu.getSelectedRow() != -1 && seznamVozu.getSelectedRow() < vozPark.getPocetVozu()) {
            PotvrdOdeberVuz okno = new PotvrdOdeberVuz(this, seznamVozu.getSelectedRow());
            okno.setVisible(true);
        }
    }

    private void PridejProstredek(java.awt.event.ActionEvent evt) {
        VuzPridani okynko = new VuzPridani(this);
        okynko.setVisible(true);
        okynko.setTitle("NOVÝ PROSTŘEDEK");
        

    }

    private void PridejPracovnikaActionPerformed(java.awt.event.ActionEvent evt) {
        PracovnikPridani okynko = new PracovnikPridani(this);
        okynko.setVisible(true);
        okynko.setTitle("NOVÝ PRACOVNÍK");
    }
    
    public int getRadek() {
        return radek;
    }
    // Konec

//public metody pro refresh vozového parku.
    public void refreshVozovehoParku() {
        seznamVozu.updateUI();
    }

    public void refreshSeznamuPracovniku() {
        seznamPracovniku.updateUI();
    }

    public void refreshSeznamuZasilek() {
        seznamZasilek.updateUI();
    }

    public JTable getSeznamVozu() {
        return seznamVozu;
    }

    public JButton getOdeberVuz() {
        return odeberVuz;
    }

    public void setOdeberVuz(JButton OdeberVuz) {
        this.odeberVuz = OdeberVuz;
    }

    public JButton getOdeberZasilku() {
        return odeberZasilku;
    }

    public void setOdeberZasilku(JButton OdeberZasilku) {
        this.odeberZasilku = OdeberZasilku;
    }

    public JButton getEditujProstredek() {
        return editujVuz;
    }

    public void setEditujProstredek(JButton EditujProstredek) {
        this.editujVuz = EditujProstredek;
    }

    public JButton getEditujPracovnika() {
        return editujPracovnika;
    }

    public void setEditujPracovnika(JButton EditujPracovnika) {
        this.editujPracovnika = EditujPracovnika;
    }

    public JButton getOdeberPracovnika() {
        return odeberPracovnika;
    }

    public void setOdeberPracovnika(JButton OdeberPracovnika) {
        this.odeberPracovnika = OdeberPracovnika;
    }

    public JTable getSeznamPracovniku() {
        return seznamPracovniku;
    }

    public void setSeznamPracovniku(JTable SeznamPracovniku) {
        this.seznamPracovniku = SeznamPracovniku;
    }


    public void timer(){
    Date Datum = new Date();
    Timer t = new Timer();
    final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    TimerTask refresh = new TimerTask() {

            @Override
            public void run() {
               Date dat =new Date();
               aktualniCas.setText(dateFormat.format(dat));
               refreshSeznamuZasilek();
               sezZas.refreshCasu();
            }
        };
        t.schedule(refresh, Datum,1000);
    }


    @Override
      public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        super.paint(g2);
        g2.drawRect(900, 250, 50, 50);
        File f=new File(/*"z:/3 Vojta/NetBeansProjects/RychlyBalik/build/classes/rychlybalik/GUI/balikobr.jpg");*/"c:/Documents and Settings/Vojta/Dokumenty/ČVUT/Programování přednášky/RychlyBalik/build/classes/rychlybalik/GUI/balikobr.jpg");
        ImageIcon i=new ImageIcon(f.toString());
        Image im=i.getImage();
        g2.drawImage(im, 860, 239,Color.red,null);
         }



}
