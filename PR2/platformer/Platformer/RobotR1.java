/*     */ package Platformer;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ class RobotR1 extends Unit
/*     */ {
/*     */   static final double speed_rob = 0.2D;
/*     */ 
/*     */   public RobotR1(GamePanel gp, int bx, int by)
/*     */     throws IOException
/*     */   {
/*  13 */     super(gp);
/*     */ 
/*  15 */     this.current_panel = gp;
/*     */ 
/*  17 */     this.health = 50;
/*     */ 
/*  19 */     this.w = 84;
/*  20 */     this.h = 80;
/*     */ 
/*  22 */     setLocation(bx, by);
/*     */ 
/*  24 */     addPoint(28, 13);
/*  25 */     addPoint(56, 13);
/*  26 */     addPoint(20, 32);
/*  27 */     addPoint(63, 32);
/*  28 */     addPoint(20, 79);
/*  29 */     addPoint(63, 79);
/*     */   }
/*     */ 
/*     */   public BufferedImage getSprite()
/*     */   {
/*  35 */     if (this.alive) {
/*  36 */       if (this.speed_x > 0.0D) {
/*  37 */         this.course = 22;
/*  38 */       } else if (this.speed_x < 0.0D) {
/*  39 */         this.course = 44; } else {
/*  40 */         if (this.course == 22) {
/*  41 */           this.sprite_number = 0;
/*  42 */           return this.current_panel.R1_sprites[0];
/*  43 */         }if (this.course == 44) {
/*  44 */           this.sprite_number = 4;
/*  45 */           return this.current_panel.R1_sprites[4];
/*     */         }
/*  47 */         System.out.println("AUCH!!!!");
/*     */       }
/*     */ 
/*  51 */       if (this.speed_x > 0.0D) {
/*  52 */         if ((this.sprite_number >= 0) && (this.sprite_number < 3)) {
/*  53 */           if (this.sprite_time > 20L) {
/*  54 */             this.sprite_number += 1;
/*  55 */             this.sprite_time = 0L;
/*     */           } else {
/*  57 */             this.sprite_time += this.time;
/*     */           }
/*     */         }
/*     */         else {
/*  61 */           this.sprite_number = 0;
/*     */         }
/*  63 */         return this.current_panel.R1_sprites[this.sprite_number];
/*  64 */       }if (this.speed_x < 0.0D) {
/*  65 */         if ((this.sprite_number >= 3) && (this.sprite_number < 7)) {
/*  66 */           if (this.sprite_time > 20L) {
/*  67 */             this.sprite_number += 1;
/*  68 */             this.sprite_time = 0L;
/*     */           } else {
/*  70 */             this.sprite_time += this.time;
/*     */           }
/*     */         }
/*     */         else {
/*  74 */           this.sprite_number = 4;
/*     */         }
/*  76 */         return this.current_panel.R1_sprites[this.sprite_number];
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  81 */       if (this.sprite_time > 30L) {
/*  82 */         if (this.death_frame < 4) {
/*  83 */           this.death_frame += 1;
/*     */         }
/*  85 */         this.sprite_time = 0L;
/*     */       }
/*     */       else
/*     */       {
/*  89 */         this.sprite_time += this.time;
/*     */       }
/*     */ 
/*  95 */       return this.current_panel.R1_sprites[(7 + this.death_frame)];
/*     */     }
/*     */ 
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   public void moving()
/*     */   {
/* 104 */     if (this.alive) {
/* 105 */       if (this.action) {
/* 106 */         if (this.current_panel.ourPlayer.getCentre().x > getCentre().x)
/* 107 */           this.speed_x = 0.2D;
/* 108 */         else if (this.current_panel.ourPlayer.getCentre().x < getCentre().x)
/* 109 */           this.speed_x = -0.2D;
/* 110 */         else if (this.current_panel.ourPlayer.getCentre().x == getCentre().x)
/* 111 */           this.speed_x = 0.0D;
/*     */       }
/* 113 */       else if (this.speed_x == 0.0D) {
/* 114 */         this.speed_x = 0.2D;
/*     */       }
/*     */ 
/* 117 */       int s_pl_x = (int)Math.round(this.speed_x * this.time);
/*     */ 
/* 121 */       if (this.current_panel.movementIsPossible(this, this.x + s_pl_x, this.y)) {
/* 122 */         if (this.current_panel.movementIsPossible(this, this.x + s_pl_x, this.y + 5))
/* 123 */           this.speed_x = (-this.speed_x);
/*     */         else {
/* 125 */           setLocation(this.x + s_pl_x, this.y);
/*     */         }
/*     */       }
/* 128 */       else if (this.current_panel.movementIsPossible(this, this.x + s_pl_x, this.y - 4))
/* 129 */         setLocation(this.x + s_pl_x, this.y - 4);
/*     */       else {
/* 131 */         this.speed_x = (-this.speed_x);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 138 */     if (this.current_panel.movementIsPossible(this, this.x, this.y + 1)) {
/* 139 */       if (this.gravity_jumper == 1) {
/* 140 */         this.speed_y += this.gravity * this.time / 2.0D;
/* 141 */         this.time_g += this.time;
/*     */       }
/* 144 */       else if (this.speed_y > 0.0D)
/*     */       {
/* 146 */         this.speed_y = 0.0D;
/*     */       }
/* 148 */       this.gravity_jumper = 1;
/*     */     }
/*     */     else
/*     */     {
/* 152 */       this.time_g = 0L;
/* 153 */       this.gravity_jumper = 0;
/*     */     }
/*     */ 
/* 157 */     int s_pl_y = (int)Math.round(this.speed_y * this.time);
/*     */ 
/* 160 */     while (!this.current_panel.movementIsPossible(this, this.x, this.y + s_pl_y))
/*     */     {
/* 164 */       if (this.speed_y == 0.0D) {
/*     */         break;
/*     */       }
/* 167 */       if (this.speed_y > 0.0D) {
/* 168 */         if (s_pl_y <= 1) {
/*     */           break;
/*     */         }
/* 171 */         s_pl_y--;
/*     */       }
/* 173 */       if (this.speed_y < 0.0D) {
/* 174 */         if (s_pl_y >= -1) {
/*     */           break;
/*     */         }
/* 177 */         s_pl_y++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 183 */     if (this.current_panel.movementIsPossible(this, this.x, this.y + s_pl_y)) {
/* 184 */       setLocation(this.x, this.y + s_pl_y);
/*     */     }
/*     */     else
/* 187 */       this.speed_y = 0.0D;
/*     */   }
/*     */ 
/*     */   public void death()
/*     */   {
/* 200 */     this.alive = false;
/*     */ 
/* 202 */     this.w = 35;
/* 203 */     this.h = 80;
/*     */ 
/* 206 */     this.points.clear();
/* 207 */     addPoint(5, 39);
/* 208 */     addPoint(7, 21);
/* 209 */     addPoint(36, 37);
/* 210 */     addPoint(42, 15);
/* 211 */     addPoint(82, 39);
/* 212 */     addPoint(72, 11);
/*     */   }
/*     */ 
/*     */   void fire()
/*     */   {
/* 218 */     this.current_panel.blusters.add(new BlusterLine(this.current_panel, this, this.current_panel.ourPlayer.getCentre().x, this.current_panel.ourPlayer.getCentre().y));
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 226 */     this.time = this.current_panel.time;
/*     */ 
/* 231 */     if (this.current_panel.isOnActiveZone(this)) {
/* 232 */       if (this.alive) {
/* 233 */         this.time_for_action = (int)(this.time_for_action + this.time);
/* 234 */         if (this.time_for_action > 500) {
/* 235 */           if (this.current_panel.isWithinSight(this, this.current_panel.ourPlayer)) {
/* 236 */             this.action = true;
/* 237 */             if (this.current_panel.isOnScreen(this))
/* 238 */               fire();
/*     */           }
/*     */           else {
/* 241 */             this.action = false;
/*     */           }
/* 243 */           this.time_for_action = 0;
/*     */         }
/*     */       }
/*     */ 
/* 247 */       moving();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Point getCentre()
/*     */   {
/* 254 */     return new Point(this.x + 42, this.y + 19);
/*     */   }
/*     */ }

/* Location:           F:\Works\GrenadeHero\Platformer_2.jar
 * Qualified Name:     Platformer.RobotR1
 * JD-Core Version:    0.6.0
 */