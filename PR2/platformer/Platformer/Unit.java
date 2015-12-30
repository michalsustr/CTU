/*    */ package Platformer;
/*    */ 
/*    */ import java.awt.Point;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ class Unit
/*    */ {
/*    */   ArrayList points;
/* 14 */   int x = 0;
/* 15 */   int y = 0;
/*    */   int w;
/*    */   int h;
/* 18 */   double speed_x = 0.0D;
/* 19 */   double speed_y = 0.0D;
/*    */   GamePanel current_panel;
/*    */   Thread th;
/*    */   int health;
/* 24 */   int death_frame = 0;
/*    */ 
/* 27 */   int sleep_time = 10;
/* 28 */   double gravity = 0.015D;
/* 29 */   int gravity_jumper = 1;
/*    */   long speed_current_time;
/*    */   long speed_last_time;
/*    */   long time;
/*    */   long time_g;
/*    */   int time_for_action;
/*    */   boolean action;
/* 36 */   boolean alive = true;
/* 37 */   int course = 22;
/*    */ 
/* 39 */   long sprite_time = 0L;
/* 40 */   int sprite_number = 1;
/*    */ 
/*    */   Unit(GamePanel gp)
/*    */   {
/* 46 */     this.points = new ArrayList();
/* 47 */     this.current_panel = gp;
/*    */   }
/*    */ 
/*    */   public void setLocation(int ax, int ay) {
/* 51 */     this.x = ax;
/* 52 */     this.y = ay;
/*    */   }
/*    */ 
/*    */   public void addPoint(int x, int y) {
/* 56 */     this.points.add(new Point(x, y));
/*    */   }
/*    */ 
/*    */   public void moving() {
/*    */   }
/*    */ 
/*    */   public BufferedImage getSprite() {
/* 63 */     return null;
/*    */   }
/*    */ 
/*    */   public void injure(int minus) {
/* 67 */     this.health -= minus;
/* 68 */     if (this.health <= 0)
/* 69 */       death();
/*    */   }
/*    */ 
/*    */   public void death()
/*    */   {
/* 74 */     this.alive = false;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Point getCentre() {
/* 82 */     return new Point(this.x + 15, this.y + 20);
/*    */   }
/*    */ }

/* Location:           F:\Works\GrenadeHero\Platformer_2.jar
 * Qualified Name:     Platformer.Unit
 * JD-Core Version:    0.6.0
 */