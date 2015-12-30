/*    */ package Platformer;
/*    */ 
/*    */ import java.awt.AlphaComposite;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.image.BufferedImage;
/*    */ 
/*    */ class GameUtils
/*    */ {
/*    */   protected static BufferedImage getTransparentImage(BufferedImage src, float alpha)
/*    */   {
/* 19 */     BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), 2);
/*    */ 
/* 21 */     Graphics2D g2 = dest.createGraphics();
/* 22 */     AlphaComposite ac = AlphaComposite.getInstance(3, alpha);
/*    */ 
/* 25 */     g2.setComposite(ac);
/* 26 */     g2.drawImage(src, null, 0, 0);
/* 27 */     g2.dispose();
/* 28 */     return dest;
/*    */   }
/*    */ 
/*    */   protected static BufferedImage getBlackToRedImage(BufferedImage src, float p)
/*    */   {
/* 34 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\Works\GrenadeHero\Platformer_2.jar
 * Qualified Name:     Platformer.GameUtils
 * JD-Core Version:    0.6.0
 */