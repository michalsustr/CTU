/*     */ package Platformer;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ class Protagonist extends Unit
/*     */ {
/*     */   public Protagonist(GamePanel gp, int bx, int by)
/*     */   {
/*  18 */     super(gp);
/*     */ 
/*  20 */     this.health = 100;
/*     */ 
/*  22 */     this.w = 32; this.h = 58;
/*     */ 
/*  24 */     setLocation(bx, by);
/*     */ 
/*  26 */     addPoint(11, 4);
/*  27 */     addPoint(17, 4);
/*  28 */     addPoint(6, 12);
/*  29 */     addPoint(22, 12);
/*  30 */     addPoint(5, 25);
/*  31 */     addPoint(25, 25);
/*  32 */     addPoint(4, 34);
/*  33 */     addPoint(27, 34);
/*  34 */     addPoint(4, 55);
/*  35 */     addPoint(27, 55);
/*     */   }
/*     */ 
/*     */   public BufferedImage getSprite()
/*     */   {
/*  41 */     if (this.speed_x > 0.0D) {
/*  42 */       this.course = 22;
/*  43 */     } else if (this.speed_x < 0.0D) {
/*  44 */       this.course = 44; } else {
/*  45 */       if (this.course == 22) {
/*  46 */         this.sprite_number = 0;
/*  47 */         return this.current_panel.protagonist_sprite[0];
/*  48 */       }if (this.course == 44) {
/*  49 */         this.sprite_number = 9;
/*  50 */         return this.current_panel.protagonist_sprite[9];
/*     */       }
/*  52 */       System.out.println("AUCH!!!!");
/*     */     }
/*     */ 
/*  56 */     if (this.speed_x > 0.0D) {
/*  57 */       if ((this.sprite_number > 0) && (this.sprite_number < 8)) {
/*  58 */         if (this.sprite_time > 35L) {
/*  59 */           this.sprite_number += 1;
/*  60 */           this.sprite_time = 0L;
/*     */         } else {
/*  62 */           this.sprite_time += this.time;
/*     */         }
/*     */       }
/*     */       else {
/*  66 */         this.sprite_number = 1;
/*     */       }
/*  68 */       return this.current_panel.protagonist_sprite[this.sprite_number];
/*  69 */     }if (this.speed_x < 0.0D) {
/*  70 */       if ((this.sprite_number > 9) && (this.sprite_number < 17)) {
/*  71 */         if (this.sprite_time > 35L) {
/*  72 */           this.sprite_number += 1;
/*  73 */           this.sprite_time = 0L;
/*     */         } else {
/*  75 */           this.sprite_time += this.time;
/*     */         }
/*     */       }
/*     */       else {
/*  79 */         this.sprite_number = 10;
/*     */       }
/*  81 */       return this.current_panel.protagonist_sprite[this.sprite_number];
/*     */     }
/*     */ 
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */   public void moving()
/*     */   {
/*  90 */     int s_pl_x = (int)Math.round(this.speed_x * this.time);
/*     */ 
/*  93 */     if (this.current_panel.movementIsPossible(this, this.x + s_pl_x, this.y)) {
/*  94 */       setLocation(this.x + s_pl_x, this.y);
/*     */     } else {
/*  96 */       int back_v_y = 1;
/*  97 */       while (back_v_y < 20)
/*     */       {
/*  99 */         if (this.current_panel.movementIsPossible(this, this.x + s_pl_x, this.y - back_v_y)) {
/* 100 */           setLocation(this.x + s_pl_x, this.y - 4);
/* 101 */           break;
/*     */         }
/* 103 */         back_v_y++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 113 */     if (this.current_panel.movementIsPossible(this, this.x, this.y + 1)) {
/* 114 */       if (this.gravity_jumper == 1) {
/* 115 */         this.speed_y += this.gravity * this.time / 2.0D;
/* 116 */         this.time_g += this.time;
/*     */       }
/* 119 */       else if (this.speed_y > 0.0D)
/*     */       {
/* 121 */         this.speed_y = 0.0D;
/*     */       }
/* 123 */       this.gravity_jumper = 1;
/*     */     }
/*     */     else
/*     */     {
/* 127 */       this.time_g = 0L;
/* 128 */       this.gravity_jumper = 0;
/*     */     }
/*     */ 
/* 132 */     int s_pl_y = (int)Math.round(this.speed_y * this.time);
/*     */ 
/* 135 */     while (!this.current_panel.movementIsPossible(this, this.x, this.y + s_pl_y))
/*     */     {
/* 139 */       if (this.speed_y == 0.0D) {
/*     */         break;
/*     */       }
/* 142 */       if (this.speed_y > 0.0D) {
/* 143 */         if (s_pl_y <= 1) {
/*     */           break;
/*     */         }
/* 146 */         s_pl_y--;
/*     */       }
/* 148 */       if (this.speed_y < 0.0D) {
/* 149 */         if (s_pl_y >= -1) {
/*     */           break;
/*     */         }
/* 152 */         s_pl_y++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 158 */     if (this.current_panel.movementIsPossible(this, this.x, this.y + s_pl_y)) {
/* 159 */       setLocation(this.x, this.y + s_pl_y);
/*     */     }
/*     */     else
/* 162 */       this.speed_y = 0.0D;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 171 */     this.time = this.current_panel.time;
/* 172 */     moving();
/*     */   }
/*     */ }

/* Location:           F:\Works\GrenadeHero\Platformer_2.jar
 * Qualified Name:     Platformer.Protagonist
 * JD-Core Version:    0.6.0
 */