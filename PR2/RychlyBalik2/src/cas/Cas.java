/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import rychlybalik.GUI.RychlyBalik;
import rychlybalik.Zasilka.Zasilka;

/**
 *
 * @author vojta
 */
public class Cas implements Runnable {
protected String datum;
    public void run() {
      
        while (true) {

            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");//yyyy/MM/dd
            Date date = new Date();


            setDatum(dateFormat.format(date));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e)  {
            }
        }

    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }


}
