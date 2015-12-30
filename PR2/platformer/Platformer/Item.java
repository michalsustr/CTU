/*     */ package Platformer;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ 
/*     */ class Item
/*     */ {
/*     */   int n;
/* 311 */   boolean marked = false;
/* 312 */   boolean isActive = true;
/*     */   BufferedImage bi_active;
/*     */   BufferedImage bi_non_active;
/*     */ 
/*     */   Item(BufferedImage bi, BufferedImage bi2)
/*     */   {
/* 317 */     this.bi_active = bi;
/* 318 */     this.bi_non_active = bi2;
/*     */   }
/*     */ 
/*     */   public void setActive(boolean b) {
/* 322 */     this.isActive = b;
/*     */   }
/*     */ }

/* Location:           F:\Works\GrenadeHero\Platformer_2.jar
 * Qualified Name:     Platformer.Item
 * JD-Core Version:    0.6.0
 */