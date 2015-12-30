/*     */ package Platformer;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ class Bomb
/*     */ {
/*     */   ArrayList points;
/*  16 */   int x = 0;
/*  17 */   int y = 0;
/*  18 */   double speed_x = 0.0D;
/*  19 */   double speed_y = 0.0D;
/*     */   GamePanel current_panel;
/*  21 */   int sleep_time = 10;
/*  22 */   double gravity = 0.01D;
/*  23 */   int gravity_jumper = 1;
/*     */   long time;
/*     */   long time_live;
/*     */   long time_g;
/*     */   long time_color;
/*  28 */   boolean alive = true;
/*     */   Color current_color;
/*     */ 
/*     */   Bomb(GamePanel gp)
/*     */   {
/*  32 */     this.points = new ArrayList();
/*  33 */     this.current_panel = gp;
/*  34 */     this.current_color = Color.BLACK;
/*     */ 
/*  37 */     addPoint(6, 0);
/*  38 */     addPoint(6, 12);
/*  39 */     addPoint(0, 6);
/*  40 */     addPoint(12, 6);
/*     */ 
/*  46 */     setLocation(this.current_panel.ourPlayer.x + 15, this.current_panel.ourPlayer.y + 20);
/*     */ 
/*  48 */     if (this.current_panel.ourPlayer.course == 22)
/*  49 */       this.speed_x = (1.0D + this.current_panel.ourPlayer.speed_x);
/*  50 */     else if (this.current_panel.ourPlayer.course == 44) {
/*  51 */       this.speed_x = (-1.0D + this.current_panel.ourPlayer.speed_x);
/*     */     }
/*  53 */     this.speed_y = (-1.1D + this.current_panel.ourPlayer.speed_y);
/*     */   }
/*     */ 
/*     */   public void setLocation(int ax, int ay)
/*     */   {
/*  60 */     this.x = ax;
/*  61 */     this.y = ay;
/*     */   }
/*     */ 
/*     */   public void addPoint(int x, int y) {
/*  65 */     this.points.add(new Point(x, y));
/*     */   }
/*     */ 
/*     */   public void moving()
/*     */   {
/*  70 */     int s_pl_x = (int)Math.round(this.speed_x * this.time);
/*     */ 
/*  73 */     if (this.current_panel.movementIsPossible(this, this.x + s_pl_x, this.y))
/*  74 */       setLocation(this.x + s_pl_x, this.y);
/*  75 */     else if (this.speed_x > 0.0D)
/*     */     {
/*  77 */       this.speed_x = (-this.speed_x / 1.5D);
/*  78 */     } else if (this.speed_x < 0.0D) {
/*  79 */       this.speed_x = (-this.speed_x / 1.5D);
/*     */     }
/*     */ 
/*  84 */     if (this.current_panel.movementIsPossible(this, this.x, this.y + 1))
/*     */     {
/*  86 */       if (this.gravity_jumper == 1) {
/*  87 */         this.speed_y += this.gravity * this.time / 2.0D;
/*  88 */         this.time_g += this.time;
/*     */       }
/*  91 */       else if (this.speed_y > 0.0D)
/*     */       {
/*  93 */         this.speed_y = 0.0D;
/*     */       }
/*  95 */       this.gravity_jumper = 1;
/*     */     }
/*     */     else {
/*  98 */       this.speed_x *= 0.9D;
/*  99 */       this.time_g = 0L;
/* 100 */       this.gravity_jumper = 0;
/*     */     }
/*     */ 
/* 105 */     int s_pl_y = (int)Math.round(this.speed_y * this.time);
/*     */ 
/* 108 */     while (!this.current_panel.movementIsPossible(this, this.x, this.y + s_pl_y))
/*     */     {
/* 112 */       if (this.speed_y == 0.0D) {
/*     */         break;
/*     */       }
/* 115 */       if (this.speed_y > 0.0D) {
/* 116 */         if (s_pl_y <= 1) {
/*     */           break;
/*     */         }
/* 119 */         s_pl_y--;
/*     */       }
/* 121 */       if (this.speed_y < 0.0D) {
/* 122 */         if (s_pl_y >= -1) {
/*     */           break;
/*     */         }
/* 125 */         s_pl_y++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 131 */     if (this.current_panel.movementIsPossible(this, this.x, this.y + s_pl_y)) {
/* 132 */       setLocation(this.x, this.y + s_pl_y);
/*     */     }
/*     */     else
/* 135 */       this.speed_y = 0.0D;
/*     */   }
/*     */ 
/*     */   public void boom()
/*     */   {
/* 143 */     this.current_panel.explosion(this.x, this.y, 80);
/*     */ 
/* 147 */     this.alive = false;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 152 */     this.time = this.current_panel.time;
/*     */ 
/* 154 */     this.time_live += this.time;
/*     */ 
/* 156 */     this.time_color += this.time;
/*     */ 
/* 158 */     if (this.current_color.equals(Color.BLACK)) {
/* 159 */       if (this.time_color >= 150L) {
/* 160 */         this.current_color = Color.RED;
/* 161 */         this.time_color = 0L;
/*     */       }
/* 163 */     } else if (this.time_color >= 70L) {
/* 164 */       this.current_color = Color.BLACK;
/* 165 */       this.time_color = 0L;
/*     */     }
/*     */ 
/* 169 */     if (this.time_live > 1000L) {
/* 170 */       boom();
/*     */     }
/*     */ 
/* 173 */     moving();
/*     */   }
/*     */ }

/* Location:           F:\Works\GrenadeHero\Platformer_2.jar
 * Qualified Name:     Platformer.Bomb
 * JD-Core Version:    0.6.0
 */