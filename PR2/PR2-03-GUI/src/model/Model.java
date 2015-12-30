package model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miroslav Balík
 * Pro předmět programování 2 na FEL ČVUT
 */
public class Model {
   /*objekty budeme vkl8dat do kolekce*/
    private List<GrO> seznamObjektu = new ArrayList<GrO>();
    public void addGrO(GrO o){
        seznamObjektu.add(o);
    }
    public void kresliGrObjekty(Graphics g){
        for (GrO grO : seznamObjektu) {
            grO.kresliSe(g);
        }
    }
}
