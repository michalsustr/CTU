/*    */ package Platformer;
/*    */ 
/*    */ import java.awt.Point;
/*    */ 
/*    */ class BlusterLine
/*    */   implements Runnable
/*    */ {
/*    */   int ax;
/*    */   int ay;
/*    */   int bx;
/*    */   int by;
/*    */   int vx;
/*    */   int vy;
/*    */   int s_x;
/*    */   int s_y;
/*    */   int target_x;
/*    */   int target_y;
/*    */   double dimension;
/*    */   double uv_x;
/*    */   double uv_y;
/* 17 */   int size = 20;
/* 18 */   int plus = 0;
/*    */   GamePanel current_panel;
/*    */   Unit current_unit;
/*    */   long time;
/* 22 */   boolean alive = true;
/*    */ 
/*    */   BlusterLine(GamePanel gp, Unit ue, int tx, int ty)
/*    */   {
/* 26 */     this.current_panel = gp;
/*    */ 
/* 28 */     this.current_unit = ue;
/*    */ 
/* 30 */     this.target_x = tx;
/* 31 */     this.target_y = ty;
/*    */ 
/* 33 */     this.s_x = this.current_unit.getCentre().x;
/* 34 */     this.s_y = this.current_unit.getCentre().y;
/*    */ 
/* 36 */     this.ax = this.s_x;
/* 37 */     this.ay = this.s_y;
/* 38 */     this.bx = this.s_x;
/* 39 */     this.by = this.s_y;
/*    */ 
/* 41 */     this.vx = (this.target_x - this.s_x);
/* 42 */     this.vy = (this.target_y - this.s_y);
/* 43 */     this.dimension = Math.sqrt(this.vx * this.vx + this.vy * this.vy);
/* 44 */     this.uv_x = (this.vx / this.dimension * 2.0D);
/* 45 */     this.uv_y = (this.vy / this.dimension * 2.0D);
/*    */   }
/*    */ 
/*    */   public void remove()
/*    */   {
/* 51 */     this.alive = false;
/*    */   }
/*    */ 
/*    */   public void moving()
/*    */   {
/* 57 */     if ((!this.current_panel.isOnActiveZone(this)) || (!this.current_panel.pixelIsEmpty(this.ax, this.ay))) {
/* 58 */       remove();
/*    */     }
/*    */ 
/* 62 */     if (this.current_panel.hitOfBluster(this.current_unit, this.ax, this.ay)) {
/* 63 */       remove();
/*    */     }
/*    */ 
/* 67 */     this.plus = (int)(this.plus + this.time);
/*    */ 
/* 69 */     if (this.plus < 21)
/* 70 */       this.size = this.plus;
/*    */     else {
/* 72 */       this.size = 20;
/*    */     }
/*    */ 
/* 75 */     this.bx = (this.s_x + (int)(this.uv_x * (this.plus - this.size)));
/* 76 */     this.by = (this.s_y + (int)(this.uv_y * (this.plus - this.size)));
/*    */ 
/* 79 */     this.ax = (this.s_x + (int)(this.uv_x * this.plus));
/* 80 */     this.ay = (this.s_y + (int)(this.uv_y * this.plus));
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/* 87 */     this.time = this.current_panel.time;
/* 88 */     moving();
/*    */   }
/*    */ }

/* Location:           F:\Works\GrenadeHero\Platformer_2.jar
 * Qualified Name:     Platformer.BlusterLine
 * JD-Core Version:    0.6.0
 */