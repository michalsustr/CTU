/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rychlybalik;


import rychlybalik.GUI.RychlyBalik;

import rychlybalik.Personalistika.*;
import rychlybalik.TypDopravy.*;
import rychlybalik.VozovyPark.*;
import rychlybalik.Zasilka.*;

/**
 *
 * @author Vojta
 */
public class Main {

    public static double min(double prvni, double druha) {
        if (prvni > druha) {
            return druha;
        } else {
            return prvni;
        }
    }

    public static void main(String[] args) {

        VozovyPark BalikAuta=new VozovyPark();          //vytvoření konkrétní instance třídy VozovýPark.Obsahující tyto prostředky.
        BalikAuta.pridejVuz(new DodavkaVelka());
        BalikAuta.pridejVuz(new PickUp(2300));
        BalikAuta.pridejVuz(new PickUp(1500));
        BalikAuta.pridejVuz(new Kolo(3,35));
        BalikAuta.pridejVuz(new MHD(14));
        BalikAuta.pridejVuz(new MHD(6));

        Smena smena1=new Smena();
        
       smena1.pridejPracovnika(new Vykonator("Petr","Pavel",VycetOpravneni.DODAVKA));
        
        smena1.pridejPracovnika(new Vykonator("Jan","Novak",VycetOpravneni.KOLO));
       //smena1.pridejPracovnika(new Vykonator("Jan","Novak",VycetOpravneni.DODAVKA));
        smena1.pridejPracovnika(new Administrator("Honza","Novak"));
       smena1.pridejPracovnika(new Vykonator("Zdeněk","Svěrák",VycetOpravneni.PICK_UP));


       SeznamZasilek BalikZasilky=new SeznamZasilek();


       if(smena1.getSmena().get(0).getOpravneni().equals(BalikAuta.getSeznamVozu().get(0).getOpravneni())){System.out.println("Moze ridit");}


        System.out.println("Použitelné vozy:\n" +smena1.getObsluhovatelna(BalikAuta).toString());


        // TODO code application logic here
     /*   DodavkaVelka dodavka = new DodavkaVelka();
        PickUp auto1 = new PickUp(1500);
        PickUp auto2 = new PickUp(2300);
        Kolo cyklista = new Kolo(3, 35);
        MHD pesak1 = new MHD(14);
        MHD pesak2 = new MHD(6);

        Zasilka prvni = new Zasilka(11, 90);

        double hmotnost = prvni.getHmotnost();
        double vzdalenost = prvni.getVzdalenost();
        double cena = 0;


        if (hmotnost > 0) {
            if (hmotnost > 2300) {
                dodavka.getCenaZaDoruceni(vzdalenost);   //pro hmotnost od 0 - 4000
            } else {
                if (hmotnost > 1500) {
                    cena = min(dodavka.getCenaZaDoruceni(vzdalenost),
                            auto2.getCenaZaDoruceni(vzdalenost));
                } else {
                    if (hmotnost > 14) {
                        cena = min(min(dodavka.getCenaZaDoruceni(vzdalenost),
                                auto2.getCenaZaDoruceni(vzdalenost)),
                                auto1.getCenaZaDoruceni(vzdalenost));
                    } else {
                        if (hmotnost > 6) {
                            cena = min(min(min(dodavka.getCenaZaDoruceni(vzdalenost),
                                auto2.getCenaZaDoruceni(vzdalenost)),
                                auto1.getCenaZaDoruceni(vzdalenost)),
                                pesak1.getCenaZaDoruceni(vzdalenost));

                        } else {
                            if (hmotnost > 3) {
                                cena = min(min(min(min(dodavka.getCenaZaDoruceni(vzdalenost),
                                auto2.getCenaZaDoruceni(vzdalenost)),
                                auto1.getCenaZaDoruceni(vzdalenost)),
                                pesak1.getCenaZaDoruceni(vzdalenost)),
                                pesak2.getCenaZaDoruceni(vzdalenost));
                            } else {
                                cena = min(min(min(min(min(dodavka.getCenaZaDoruceni(vzdalenost),
                                auto2.getCenaZaDoruceni(vzdalenost)),
                                auto1.getCenaZaDoruceni(vzdalenost)),
                                pesak1.getCenaZaDoruceni(vzdalenost)),
                                pesak2.getCenaZaDoruceni(vzdalenost)),
                                cyklista.getCenaZaDoruceni(vzdalenost));
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("Nelze záporná hmotnost");

            
    }
      System.out.println("nejlevnejsi cena doruceni je:"+cena);
           System.out.println(dodavka.getCenaZaDoruceni(vzdalenost));
              System.out.println(                  auto2.getCenaZaDoruceni(vzdalenost));
              System.out.println(                  auto1.getCenaZaDoruceni(vzdalenost));
              System.out.println(                  pesak1.getCenaZaDoruceni(vzdalenost));
               System.out.println(                 pesak2.getCenaZaDoruceni(vzdalenost));

               System.out.println(                 cyklista.getCenaZaDoruceni(vzdalenost)); */

        System.out.println(BalikAuta.toString());
        
        System.out.println("pocet prostredku "+BalikAuta.getPocetVozu());

        System.out.println(BalikAuta.getNejlevnejsi(1500, 90));

       //HlavniOkno okno= new HlavniOkno(BalikAuta,smena1,BalikZasilky);
      // okno.setTitle("Rychlý Balík - logistika");
        //okno.setVisible(true);

       RychlyBalik okno= new RychlyBalik(BalikAuta,smena1,BalikZasilky);
       okno.setTitle("Rychlý Balík - logistika");
       okno.setVisible(true);

        //HlavniOkno2 okno2=new HlavniOkno2();
}



}
