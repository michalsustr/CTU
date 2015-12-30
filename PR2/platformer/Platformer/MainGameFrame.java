/*    */ package Platformer;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Toolkit;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import javax.swing.JFrame;
/*    */ 
/*    */ public class MainGameFrame extends JFrame
/*    */ {
/*    */   public MainGameFrame()
/*    */     throws FileNotFoundException, IOException
/*    */   {
/* 21 */     super("Grenade Hero 0.74");
/* 22 */     setSize(640, 508);
/* 23 */     setResizable(false);
/*    */ 
/* 26 */     setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/icon_64.png")));
/*    */ 
/* 28 */     setLocationRelativeTo(this);
/* 29 */     setDefaultCloseOperation(3);
/* 30 */     Container c = new Container();
/*    */ 
/* 32 */     c = getContentPane();
/*    */ 
/* 40 */     MenuPanel m = new MenuPanel(c);
/* 41 */     c.add(m);
/*    */ 
/* 44 */     setVisible(true);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) throws FileNotFoundException, IOException
/*    */   {
/* 49 */     MainGameFrame game = new MainGameFrame();
/*    */   }
/*    */ }

/* Location:           F:\Works\GrenadeHero\Platformer_2.jar
 * Qualified Name:     Platformer.MainGameFrame
 * JD-Core Version:    0.6.0
 */