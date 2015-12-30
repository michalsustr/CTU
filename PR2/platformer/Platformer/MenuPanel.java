/*     */ package Platformer;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ class MenuPanel extends JPanel
/*     */   implements Runnable, MouseListener, MouseMotionListener, KeyListener
/*     */ {
/*     */   static final int NEW_GAME = 0;
/*     */   static final int RETRY_LEVEL = 1;
/*     */   static final int CONTROLS = 2;
/*     */   static final int ABOUT = 3;
/*     */   static final int EXIT = 4;
/*     */   static final int UP = 88;
/*     */   static final int DOWN = 99;
/*  34 */   int offset = 0;
/*  35 */   int offset_items = 0;
/*  36 */   int pos = 0;
/*  37 */   int number = 0;
/*     */   Thread th;
/*     */   GamePanel current_game;
/*     */   Item current;
/*     */   ArrayList list;
/*     */   Container c;
/*     */   Item[] items;
/*     */   BufferedImage bi_positions;
/*     */   BufferedImage bi_background;
/*     */   BufferedImage bi_controls;
/*     */   BufferedImage bi_about;
/*     */   BufferedImage[] bomb;
/*     */   Font font_version;
/*  50 */   int k = 32;
/*  51 */   int b = 0;
/*  52 */   boolean bit = false;
/*  53 */   float controls_a = 0.0F;
/*  54 */   float about_a = 0.0F;
/*  55 */   float klo = 0.0F;
/*     */ 
/*     */   MenuPanel(Container c) throws IOException
/*     */   {
/*  59 */     addMouseMotionListener(this);
/*  60 */     addMouseListener(this);
/*  61 */     addKeyListener(this);
/*  62 */     setFocusable(true);
/*  63 */     this.c = c;
/*  64 */     setBackground(Color.white);
/*  65 */     setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  66 */     setLayout(null);
/*  67 */     setVisible(true);
/*     */ 
/*  69 */     this.font_version = new Font("DialogInput", 0, 18);
/*     */ 
/*  71 */     this.th = new Thread(this);
/*  72 */     this.th.start();
/*     */ 
/*  75 */     this.bi_positions = ImageIO.read(getClass().getResource("/menu/positions.png"));
/*  76 */     this.bi_background = ImageIO.read(getClass().getResource("/menu/back_blue.png"));
/*  77 */     this.bi_controls = ImageIO.read(getClass().getResource("/menu/controls.png"));
/*  78 */     this.bi_about = ImageIO.read(getClass().getResource("/menu/about.png"));
/*     */ 
/*  80 */     this.bomb = new BufferedImage[5];
/*  81 */     for (int i = 0; i < 5; i++) {
/*  82 */       this.bomb[i] = ImageIO.read(getClass().getResource("/menu/bomb_" + i + ".png"));
/*     */     }
/*  84 */     this.items = new Item[5];
/*     */ 
/*  87 */     this.items[0] = new Item(this.bi_positions.getSubimage(0, 0, 125, 31), this.bi_positions.getSubimage(125, 0, 125, 31));
/*     */ 
/*  90 */     this.items[1] = new Item(this.bi_positions.getSubimage(0, 32, 125, 31), this.bi_positions.getSubimage(125, 32, 125, 31));
/*  91 */     this.items[1].isActive = false;
/*     */ 
/*  94 */     this.items[2] = new Item(this.bi_positions.getSubimage(0, 96, 125, 31), this.bi_positions.getSubimage(125, 96, 125, 31));
/*     */ 
/*  97 */     this.items[3] = new Item(this.bi_positions.getSubimage(0, 128, 125, 31), this.bi_positions.getSubimage(125, 128, 125, 31));
/*     */ 
/* 100 */     this.items[4] = new Item(this.bi_positions.getSubimage(0, 64, 125, 31), this.bi_positions.getSubimage(125, 64, 125, 31));
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 114 */     int s = 4;
/*     */     while (true)
/*     */     {
/* 117 */       if (this.number == 0) {
/* 118 */         if (this.offset_items > 0)
/*     */         {
/* 120 */           this.offset_items -= s;
/*     */         }
/*     */       }
/* 123 */       else if (this.number == this.items.length - 1) {
/* 124 */         if (this.offset_items < this.k * this.number)
/* 125 */           this.offset_items += s;
/*     */       }
/*     */       else {
/* 128 */         if (this.offset_items < this.k * this.number) {
/* 129 */           this.offset_items += s;
/*     */         }
/* 131 */         if (this.offset_items > this.k * this.number) {
/* 132 */           this.offset_items -= s;
/*     */         }
/*     */       }
/* 135 */       this.offset = this.offset_items;
/* 136 */       repaint();
/*     */       try {
/* 138 */         Thread.sleep(30L);
/*     */       } catch (InterruptedException ex) {
/* 140 */         Logger.getLogger(MenuPanel.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g)
/*     */   {
/* 152 */     g.drawImage(this.bi_background, 0, 0, null);
/*     */ 
/* 154 */     int xx = 40;
/* 155 */     int yy = 210;
/*     */ 
/* 157 */     g.setColor(new Color(250, 58, 58, 230));
/* 158 */     g.fillRect(xx, yy, 125, 31);
/*     */ 
/* 160 */     for (int i = 0; i < this.items.length; i++) {
/* 161 */       if (this.items[i].isActive)
/* 162 */         g.drawImage(this.items[i].bi_active, xx, yy + i * this.k - this.offset_items, null);
/*     */       else {
/* 164 */         g.drawImage(this.items[i].bi_non_active, xx, yy + i * this.k - this.offset_items, null);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 169 */     if (this.bit) {
/* 170 */       if (this.b < 4)
/* 171 */         this.b += 1;
/*     */       else {
/* 173 */         this.bit = false;
/*     */       }
/*     */     }
/* 176 */     else if (this.b > 0) {
/* 177 */       this.b -= 1;
/*     */     }
/*     */ 
/* 184 */     g.drawImage(this.bomb[this.b], 161, 7, null);
/*     */ 
/* 188 */     if (this.number == 2) {
/* 189 */       if (this.controls_a < 0.95F) {
/* 190 */         this.controls_a += 0.05F;
/*     */       }
/*     */     }
/* 193 */     else if (this.controls_a > 0.05F) {
/* 194 */       this.controls_a -= 0.05F;
/*     */     }
/*     */ 
/* 197 */     if (this.controls_a >= 0.05D) {
/* 198 */       g.drawImage(GameUtils.getTransparentImage(this.bi_controls, this.controls_a), 205, 115, this);
/*     */     }
/*     */ 
/* 202 */     if (this.number == 3) {
/* 203 */       if (this.about_a < 0.95F) {
/* 204 */         this.about_a += 0.05F;
/*     */       }
/*     */     }
/* 207 */     else if (this.about_a > 0.05F) {
/* 208 */       this.about_a -= 0.05F;
/*     */     }
/*     */ 
/* 211 */     if (this.about_a >= 0.05D) {
/* 212 */       g.drawImage(GameUtils.getTransparentImage(this.bi_about, this.about_a), 205, 115, this);
/* 213 */       g.setFont(this.font_version);
/* 214 */       g.setColor(new Color(0.0F, 0.0F, 0.0F, this.about_a));
/* 215 */       g.drawString("0.74", 387, 240);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void action(int c)
/*     */     throws FileNotFoundException, IOException
/*     */   {
/* 225 */     if (c == 0) {
/* 226 */       setVisible(false);
/* 227 */       if (this.current_game != null) {
/* 228 */         this.current_game.startNewGame();
/* 229 */         setVisible(false);
/* 230 */         this.current_game.setVisible(true);
/* 231 */         transferFocus();
/*     */       } else {
/* 233 */         this.current_game = new GamePanel(this);
/* 234 */         setVisible(false);
/* 235 */         this.c.add(this.current_game);
/* 236 */         transferFocus();
/*     */       }
/*     */     }
/* 239 */     if (c == 1) {
/* 240 */       setVisible(false);
/* 241 */       this.current_game.replayLevel();
/* 242 */       this.current_game.setVisible(true);
/* 243 */       transferFocus();
/*     */     }
/* 245 */     if (c == 4) {
/* 246 */       System.exit(0);
/*     */     }
/*     */ 
/* 250 */     if ((c != 2) || 
/* 252 */       (c == 3));
/*     */   }
/*     */ 
/*     */   public void mouseClicked(MouseEvent e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseEntered(MouseEvent e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseExited(MouseEvent e) {
/*     */   }
/*     */ 
/*     */   public void mouseDragged(MouseEvent e) {
/*     */   }
/*     */ 
/*     */   public void mouseMoved(MouseEvent e) {
/*     */   }
/*     */ 
/*     */   public void keyTyped(KeyEvent e) {
/*     */   }
/*     */ 
/*     */   public void keyPressed(KeyEvent e) {
/* 283 */     if ((e.getKeyCode() == 40) && (this.number < this.items.length - 1))
/*     */     {
/* 285 */       this.number += 1;
/* 286 */       this.bit = true;
/*     */     }
/* 288 */     if ((e.getKeyCode() == 38) && (this.number > 0))
/*     */     {
/* 290 */       this.number -= 1;
/* 291 */       this.bit = true;
/*     */     }
/* 293 */     if ((e.getKeyCode() == 10) && (this.items[this.number].isActive))
/*     */       try {
/* 295 */         action(this.number);
/*     */       } catch (FileNotFoundException ex) {
/* 297 */         Logger.getLogger(MenuPanel.class.getName()).log(Level.SEVERE, null, ex);
/*     */       } catch (IOException ex) {
/* 299 */         Logger.getLogger(MenuPanel.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void keyReleased(KeyEvent e)
/*     */   {
/*     */   }
/*     */ }

/* Location:           F:\Works\GrenadeHero\Platformer_2.jar
 * Qualified Name:     Platformer.MenuPanel
 * JD-Core Version:    0.6.0
 */