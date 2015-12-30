/*    */ package Platformer;
/*    */ 
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import javax.imageio.ImageIO;
/*    */ 
/*    */ class Digits
/*    */ {
/*    */   BufferedImage[] digits_sprite;
/* 14 */   int w = 20;
/* 15 */   int h = 30;
/*    */ 
/*    */   Digits() throws IOException {
/* 18 */     this.digits_sprite = new BufferedImage[10];
/* 19 */     for (int i = 0; i < this.digits_sprite.length; i++)
/* 20 */       this.digits_sprite[i] = ImageIO.read(getClass().getResource("/sprites/" + i + ".png"));
/*    */   }
/*    */ 
/*    */   protected void drawDigits(int d, Graphics2D g, int x, int y)
/*    */   {
/*    */     int a;
/* 27 */     if (d <= 0)
/* 28 */       a = 0;
/*    */     else {
/* 30 */       a = d;
/*    */     }
/*    */ 
/* 33 */     int q = qDigits(a);
/* 34 */     int i = 0;
/*    */     while (true) {
/* 36 */       g.drawImage(this.digits_sprite[(a % 10)], x + q * 15 - i * 15, y, null);
/* 37 */       if (a < 10) {
/*    */         break;
/*    */       }
/* 40 */       a /= 10;
/* 41 */       i++;
/*    */     }
/*    */   }
/*    */ 
/*    */   protected int qDigits(int num)
/*    */   {
/* 47 */     int len = 0;
/* 48 */     while (num >= 10) {
/* 49 */       num /= 10;
/* 50 */       len++;
/*    */     }
/* 52 */     return len;
/*    */   }
/*    */ }

/* Location:           F:\Works\GrenadeHero\Platformer_2.jar
 * Qualified Name:     Platformer.Digits
 * JD-Core Version:    0.6.0
 */