/*      */ package Platformer;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Point;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.Random;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.imageio.ImageIO;
/*      */ import javax.swing.JPanel;
/*      */ 
/*      */ class GamePanel extends JPanel
/*      */   implements KeyListener, Runnable
/*      */ {
/*      */   static final String CURRENT_VERSION = "0.74";
/*      */   static final int UP = 11;
/*      */   static final int RIGHT = 22;
/*      */   static final int DOWN = 33;
/*      */   static final int LEFT = 44;
/*      */   static final int WIDTH_OF_MAP = 2784;
/*      */   static final int HEIGHT_OF_MAP = 1152;
/*      */   static final int Q_LEVELS = 5;
/*   33 */   int latency = 10;
/*   34 */   int H = 480;
/*   35 */   int W = 640;
/*      */ 
/*   37 */   int current_level = 0;
/*   38 */   int q_bomb = 0;
/*      */   int frames;
/*      */   long t0;
/*      */   long t1;
/*      */   int fps;
/*      */   BufferedImage bi_init;
/*      */   BufferedImage bi_out;
/*      */   Graphics2D graph_init;
/*      */   Graphics2D graph_out;
/*      */   BufferedImage[] protagonist_sprite;
/*      */   BufferedImage merge_sprite;
/*      */   BufferedImage texture_kenny;
/*      */   BufferedImage sprite_droid;
/*      */   BufferedImage sprite_info;
/*      */   BufferedImage sprite_you_dead;
/*      */   BufferedImage[] R1_sprites;
/*      */   BufferedImage sprite_level;
/*      */   BufferedImage sprite_health;
/*      */   BufferedImage sprite_bomb;
/*      */   Graphics2D graph_i;
/*      */   Protagonist ourPlayer;
/*      */   MenuPanel menu_panel;
/*      */   Polygon pol;
/*      */   ArrayList<Unit> units;
/*      */   Bomb bomb_thrown;
/*      */   ArrayList blusters;
/*      */   BufferedImage[] boom_sprites;
/*      */   BufferedImage[] portal_sprites;
/*      */   BasicStroke bluster_pen_out;
/*      */   BasicStroke bluster_pen_in;
/*      */   Thread thread_panel;
/*      */   Random rnd;
/*      */   long speed_current_time;
/*      */   long speed_last_time;
/*      */   long time;
/*      */   long portal_time;
/*   86 */   double speed = 0.4D;
/*   87 */   int offset_x = 0;
/*   88 */   int offset_y = 0;
/*   89 */   int sh_x = 0;
/*   90 */   int sh_y = 0;
/*      */   Rectangle finish;
/*      */   int portal_number;
/*   93 */   int death_color = 0;
/*   94 */   int hit_color = 0;
/*   95 */   boolean runBoom = false;
/*      */   int boom_x;
/*      */   int boom_y;
/*   96 */   int boom_i = 0;
/*   97 */   int info_offset = -5;
/*      */   Digits dig;
/*      */ 
/*      */   public GamePanel(MenuPanel mp)
/*      */     throws FileNotFoundException, IOException
/*      */   {
/*  102 */     this.menu_panel = mp;
/*  103 */     this.thread_panel = new Thread(this);
/*      */ 
/*  105 */     setDoubleBuffered(false);
/*  106 */     addKeyListener(this);
/*  107 */     setFocusable(true);
/*      */ 
/*  111 */     init();
/*  112 */     startNewGame();
/*      */ 
/*  114 */     this.thread_panel.setPriority(10);
/*      */ 
/*  116 */     this.speed_last_time = System.currentTimeMillis();
/*      */ 
/*  118 */     this.thread_panel.start();
/*      */   }
/*      */ 
/*      */   public void run()
/*      */   {
/*      */     while (true) {
/*  124 */       this.speed_current_time = System.currentTimeMillis();
/*  125 */       this.time = (this.speed_current_time - this.speed_last_time);
/*      */ 
/*  128 */       if (this.time > 60L) {
/*  129 */         this.time = 10L;
/*      */       }
/*  131 */       trackingScreen();
/*      */ 
/*  133 */       this.bi_out.getRaster().setDataElements(0, 0, this.bi_init.getSubimage(this.offset_x, this.offset_y, this.W, this.H).getRaster());
/*      */ 
/*  135 */       runNDrawProtagonist();
/*  136 */       runNDrawUnits();
/*  137 */       runNDrawBomb();
/*  138 */       runNDrawBlusters();
/*  139 */       explosionAnimation();
/*      */ 
/*  141 */       drawInfo2();
/*      */ 
/*  143 */       drawFinish2();
/*      */ 
/*  149 */       if (isOnFinish() == true) {
/*      */         try {
/*  151 */           toNextLevel();
/*      */         } catch (IOException ex) {
/*  153 */           Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  158 */       if (this.hit_color > 0) {
/*  159 */         this.graph_out.setColor(new Color(246, 100, 100, 40));
/*  160 */         this.graph_out.fillRect(0, 0, this.W, this.H);
/*  161 */         this.hit_color -= 1;
/*      */       }
/*      */ 
/*  164 */       if (this.ourPlayer.health <= 0)
/*      */       {
/*  166 */         this.graph_out.setColor(new Color(255, 255, 255, this.death_color));
/*  167 */         this.graph_out.fillRect(0, 0, this.W, this.H);
/*  168 */         if (this.death_color <= 252) {
/*  169 */           this.death_color += 3;
/*      */         } else {
/*  171 */           this.death_color = 0;
/*      */           try
/*      */           {
/*  174 */             replayLevel();
/*      */           } catch (IOException ex) {
/*      */           }
/*      */         }
/*      */       }
/*  179 */       if (this.death_color > 50) {
/*  180 */         this.graph_out.drawImage(this.sprite_you_dead, this.W / 2 - 100, this.H / 2 - 25, null);
/*      */       }
/*      */ 
/*  184 */       repaint();
/*  185 */       this.speed_last_time = this.speed_current_time;
/*      */       try
/*      */       {
/*  189 */         Thread.sleep(10L);
/*      */       }
/*      */       catch (InterruptedException ex) {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void runNDrawProtagonist() {
/*  197 */     this.ourPlayer.run();
/*  198 */     this.graph_out.drawImage(this.ourPlayer.getSprite(), this.ourPlayer.x - this.offset_x, this.ourPlayer.y - this.offset_y, null);
/*      */   }
/*      */ 
/*      */   public void runNDrawUnits() {
/*  202 */     Iterator iter_units = this.units.iterator();
/*  203 */     while (iter_units.hasNext()) {
/*  204 */       Unit temp = (Unit)iter_units.next();
/*      */ 
/*  207 */       temp.run();
/*  208 */       if (isOnScreen(temp))
/*  209 */         this.graph_out.drawImage(temp.getSprite(), temp.x - this.offset_x, temp.y - this.offset_y, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void runNDrawBomb()
/*      */   {
/*  220 */     if (this.bomb_thrown != null)
/*  221 */       if (!this.bomb_thrown.alive) {
/*  222 */         this.bomb_thrown = null;
/*      */       } else {
/*  224 */         this.bomb_thrown.run();
/*  225 */         this.graph_out.setColor(this.bomb_thrown.current_color);
/*  226 */         this.graph_out.fillOval(this.bomb_thrown.x - this.offset_x, this.bomb_thrown.y - this.offset_y, 12, 12);
/*      */       }
/*      */   }
/*      */ 
/*      */   public void runNDrawBlusters()
/*      */   {
/*  232 */     Iterator iter_blusters = this.blusters.iterator();
/*  233 */     while (iter_blusters.hasNext())
/*      */     {
/*  235 */       BlusterLine temp = (BlusterLine)iter_blusters.next();
/*      */ 
/*  237 */       if (temp.alive)
/*      */       {
/*  239 */         temp.run();
/*      */ 
/*  241 */         this.graph_out.setStroke(this.bluster_pen_out);
/*      */ 
/*  243 */         this.graph_out.setColor(Color.cyan);
/*  244 */         this.graph_out.drawLine(temp.ax - this.offset_x, temp.ay - this.offset_y, temp.bx - this.offset_x, temp.by - this.offset_y);
/*  245 */         this.graph_out.setStroke(this.bluster_pen_in);
/*      */ 
/*  247 */         this.graph_out.setColor(new Color(123, 181, 246));
/*  248 */         this.graph_out.drawLine(temp.ax - this.offset_x, temp.ay - this.offset_y, temp.bx - this.offset_x, temp.by - this.offset_y);
/*      */       }
/*      */       else {
/*  251 */         iter_blusters.remove();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void explosionAnimation()
/*      */   {
/*  259 */     if (this.runBoom)
/*  260 */       if (this.boom_i < this.boom_sprites.length) {
/*  261 */         this.graph_out.drawImage(this.boom_sprites[this.boom_i], this.boom_x - this.offset_x, this.boom_y - this.offset_y, null);
/*  262 */         this.boom_i += 1;
/*      */       } else {
/*  264 */         this.runBoom = false;
/*  265 */         this.boom_i = 0;
/*      */       }
/*      */   }
/*      */ 
/*      */   public void drawInfo2()
/*      */   {
/*  271 */     this.graph_out.drawImage(this.sprite_info, this.W / 2 - this.sprite_info.getRaster().getWidth() / 2, 0, null);
/*  272 */     this.dig.drawDigits(this.ourPlayer.health, this.graph_out, 265 + this.info_offset, 2);
/*  273 */     this.dig.drawDigits(this.q_bomb, this.graph_out, 352 + this.info_offset, 2);
/*      */   }
/*      */ 
/*      */   public void drawFinish2() {
/*  277 */     if ((this.finish.x - 5 + 110 > this.offset_x) && (this.finish.x - 5 < this.offset_x + this.W) && (this.finish.y - 5 + 110 > this.offset_y) && (this.finish.y - 5 < this.offset_y + this.H))
/*      */     {
/*  281 */       if (this.portal_time > 55L)
/*      */       {
/*  283 */         int temp_portal_number = this.portal_number;
/*      */ 
/*  286 */         while (this.portal_number == temp_portal_number)
/*      */         {
/*  289 */           this.portal_number = this.rnd.nextInt(8);
/*      */         }
/*      */ 
/*  293 */         this.portal_time = 0L;
/*      */       } else {
/*  295 */         this.portal_time += this.time;
/*      */       }
/*      */ 
/*  298 */       this.graph_out.drawImage(this.portal_sprites[this.portal_number], this.finish.x - 5 - this.offset_x, this.finish.y - 5 - this.offset_y, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void trackingScreen()
/*      */   {
/*  305 */     if (this.ourPlayer.speed_x != 0.0D) {
/*  306 */       if (this.ourPlayer.x < this.offset_x + 200)
/*  307 */         offset(44);
/*  308 */       else if (this.ourPlayer.x > this.offset_x + 440) {
/*  309 */         offset(22);
/*      */       }
/*      */     }
/*  312 */     else if (this.ourPlayer.x < this.offset_x + 150)
/*  313 */       offset(44, 32);
/*  314 */     else if (this.ourPlayer.x > this.offset_x + 490) {
/*  315 */       offset(22, 32);
/*      */     }
/*      */ 
/*  320 */     if (this.ourPlayer.speed_y != 0.0D) {
/*  321 */       if (this.ourPlayer.y < this.offset_y + 150)
/*  322 */         offset(11);
/*  323 */       else if (this.ourPlayer.y > this.offset_y + 330) {
/*  324 */         offset(33);
/*      */       }
/*      */     }
/*  327 */     else if (this.ourPlayer.y < this.offset_y + 100)
/*  328 */       offset(11, 32);
/*  329 */     else if (this.ourPlayer.y > this.offset_y + 380)
/*  330 */       offset(33, 32);
/*      */   }
/*      */ 
/*      */   public void toCreateLevel(int i)
/*      */     throws IOException
/*      */   {
/*  338 */     toCleanLevel();
/*  339 */     toLoadMap(i);
/*  340 */     toCreateUnits(i);
/*      */   }
/*      */ 
/*      */   public void startNewGame() throws IOException {
/*  344 */     this.current_level = 0;
/*  345 */     toCreateLevel(0);
/*      */   }
/*      */ 
/*      */   public void replayLevel() throws IOException {
/*  349 */     toCreateLevel(this.current_level);
/*      */   }
/*      */ 
/*      */   public void toNextLevel() throws IOException {
/*  353 */     if (this.current_level + 1 < 5) {
/*  354 */       this.current_level += 1;
/*  355 */       toCreateLevel(this.current_level);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void toCleanLevel()
/*      */   {
/*  363 */     Iterator iter_units = this.units.iterator();
/*  364 */     while (iter_units.hasNext())
/*      */     {
/*  366 */       Unit temp = (Unit)iter_units.next();
/*  367 */       iter_units.remove();
/*      */     }
/*      */     try
/*      */     {
/*  371 */       Iterator iter_blusters = this.blusters.iterator();
/*  372 */       while (iter_blusters.hasNext())
/*      */       {
/*  374 */         BlusterLine temp = (BlusterLine)iter_units.next();
/*  375 */         iter_blusters.remove();
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*  381 */     this.bomb_thrown = null;
/*      */   }
/*      */ 
/*      */   public void toCreateUnits(int i)
/*      */     throws IOException
/*      */   {
/*  388 */     switch (i)
/*      */     {
/*      */     case 0:
/*  392 */       this.ourPlayer = new Protagonist(this, 102, 106);
/*      */ 
/*  394 */       this.units.add(new RobotR1(this, 837, 212));
/*  395 */       this.units.add(new RobotR1(this, 276, 748));
/*  396 */       this.units.add(new RobotR1(this, 635, 793));
/*  397 */       this.units.add(new RobotR1(this, 1336, 566));
/*      */ 
/*  399 */       this.units.add(new RobotR1(this, 1811, 338));
/*  400 */       this.units.add(new RobotR1(this, 2350, 170));
/*  401 */       this.units.add(new RobotR1(this, 2463, 405));
/*  402 */       break;
/*      */     case 1:
/*  407 */       this.ourPlayer = new Protagonist(this, 102, 106);
/*      */ 
/*  409 */       this.units.add(new RobotR1(this, 868, 128));
/*  410 */       this.units.add(new RobotR1(this, 1537, 209));
/*  411 */       this.units.add(new RobotR1(this, 2513, 512));
/*  412 */       this.units.add(new RobotR1(this, 1197, 668));
/*  413 */       this.units.add(new RobotR1(this, 232, 586));
/*  414 */       this.units.add(new RobotR1(this, 199, 766));
/*  415 */       this.units.add(new RobotR1(this, 519, 1001));
/*  416 */       this.units.add(new RobotR1(this, 1794, 791));
/*  417 */       this.units.add(new RobotR1(this, 1929, 794));
/*  418 */       this.units.add(new RobotR1(this, 1742, 992));
/*  419 */       this.units.add(new RobotR1(this, 2529, 902));
/*  420 */       break;
/*      */     case 2:
/*  423 */       this.ourPlayer = new Protagonist(this, 2453, 154);
/*      */ 
/*  425 */       this.units.add(new RobotR1(this, 2213, 533));
/*      */ 
/*  427 */       this.units.add(new RobotR1(this, 1539, 272));
/*  428 */       this.units.add(new RobotR1(this, 1182, 310));
/*  429 */       this.units.add(new RobotR1(this, 1213, 586));
/*  430 */       this.units.add(new RobotR1(this, 1635, 825));
/*  431 */       this.units.add(new RobotR1(this, 1438, 982));
/*  432 */       this.units.add(new RobotR1(this, 711, 809));
/*  433 */       this.units.add(new RobotR1(this, 408, 280));
/*  434 */       this.units.add(new RobotR1(this, 223, 171));
/*  435 */       break;
/*      */     case 3:
/*  438 */       this.ourPlayer = new Protagonist(this, 102, 106);
/*      */ 
/*  440 */       this.units.add(new RobotR1(this, 470, 479));
/*  441 */       this.units.add(new RobotR1(this, 850, 693));
/*  442 */       this.units.add(new RobotR1(this, 609, 944));
/*  443 */       this.units.add(new RobotR1(this, 1108, 962));
/*  444 */       this.units.add(new RobotR1(this, 1896, 789));
/*  445 */       this.units.add(new RobotR1(this, 1673, 531));
/*  446 */       this.units.add(new RobotR1(this, 2626, 268));
/*  447 */       this.units.add(new RobotR1(this, 1995, 324));
/*      */ 
/*  449 */       break;
/*      */     case 4:
/*  452 */       this.ourPlayer = new Protagonist(this, 148, 760);
/*      */ 
/*  454 */       this.units.add(new RobotR1(this, 519, 977));
/*  455 */       this.units.add(new RobotR1(this, 830, 755));
/*  456 */       this.units.add(new RobotR1(this, 670, 730));
/*  457 */       this.units.add(new RobotR1(this, 402, 551));
/*  458 */       this.units.add(new RobotR1(this, 611, 402));
/*  459 */       this.units.add(new RobotR1(this, 1124, 282));
/*  460 */       this.units.add(new RobotR1(this, 1435, 583));
/*  461 */       this.units.add(new RobotR1(this, 1344, 246));
/*  462 */       this.units.add(new RobotR1(this, 2280, 341));
/*  463 */       this.units.add(new RobotR1(this, 2144, 823));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void toLoadMap(int i)
/*      */     throws IOException
/*      */   {
/*  471 */     switch (i)
/*      */     {
/*      */     case 0:
/*  474 */       setBackground(new Color(207, 213, 201));
/*  475 */       this.bi_init.setData(ImageIO.read(getClass().getResource("/maps/level_0_2.png")).getRaster());
/*  476 */       this.finish = new Rectangle(2644, 559, 100, 100);
/*  477 */       this.q_bomb = 20;
/*  478 */       break;
/*      */     case 1:
/*  481 */       setBackground(Color.WHITE);
/*  482 */       this.bi_init.setData(ImageIO.read(getClass().getResource("/maps/level_1_2.png")).getRaster());
/*  483 */       this.finish = new Rectangle(2623, 871, 100, 100);
/*  484 */       this.q_bomb = 20;
/*  485 */       break;
/*      */     case 2:
/*  488 */       setBackground(Color.WHITE);
/*  489 */       this.bi_init.setData(ImageIO.read(getClass().getResource("/maps/level_2_2.png")).getRaster());
/*  490 */       this.finish = new Rectangle(146, 1027, 100, 100);
/*  491 */       this.q_bomb = 20;
/*  492 */       break;
/*      */     case 3:
/*  495 */       setBackground(Color.WHITE);
/*  496 */       this.bi_init.setData(ImageIO.read(getClass().getResource("/maps/level_3_2.png")).getRaster());
/*  497 */       this.finish = new Rectangle(1068, 135, 100, 100);
/*  498 */       this.q_bomb = 20;
/*  499 */       break;
/*      */     case 4:
/*  502 */       setBackground(Color.WHITE);
/*  503 */       this.bi_init.setData(ImageIO.read(getClass().getResource("/maps/level_4_2.png")).getRaster());
/*  504 */       this.finish = new Rectangle(1856, 944, 100, 100);
/*  505 */       this.q_bomb = 20;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void init()
/*      */     throws IOException
/*      */   {
/*  514 */     this.rnd = new Random();
/*  515 */     this.finish = new Rectangle(0, 0, 0, 0);
/*      */ 
/*  521 */     this.bluster_pen_out = new BasicStroke(7.0F, 1, 0);
/*  522 */     this.bluster_pen_in = new BasicStroke(4.0F, 1, 0);
/*      */ 
/*  524 */     this.blusters = new ArrayList();
/*  525 */     this.units = new ArrayList();
/*      */ 
/*  529 */     this.bi_out = new BufferedImage(this.W, this.H, 2);
/*      */ 
/*  532 */     this.graph_out = this.bi_out.createGraphics();
/*  533 */     this.graph_out.setColor(Color.black);
/*      */ 
/*  536 */     this.bi_init = new BufferedImage(2784, 1152, 2);
/*  537 */     this.graph_init = this.bi_init.createGraphics();
/*      */ 
/*  539 */     this.sprite_droid = ImageIO.read(getClass().getResource("/sprites/droid.png"));
/*      */ 
/*  541 */     this.sprite_you_dead = ImageIO.read(getClass().getResource("/sprites/you_dead.png"));
/*      */ 
/*  543 */     this.sprite_info = ImageIO.read(getClass().getResource("/sprites/i.png"));
/*      */ 
/*  546 */     this.dig = new Digits();
/*      */ 
/*  548 */     this.sprite_health = ImageIO.read(getClass().getResource("/sprites/health.png"));
/*  549 */     this.sprite_bomb = ImageIO.read(getClass().getResource("/sprites/bomb.png"));
/*  550 */     this.sprite_level = ImageIO.read(getClass().getResource("/sprites/level.png"));
/*      */ 
/*  554 */     this.boom_sprites = new BufferedImage[9];
/*  555 */     for (int i = 0; i < this.boom_sprites.length; i++) {
/*  556 */       this.boom_sprites[i] = ImageIO.read(getClass().getResource("/boom/" + i + ".png"));
/*      */     }
/*      */ 
/*  560 */     this.portal_sprites = new BufferedImage[8];
/*  561 */     for (int i = 0; i < this.portal_sprites.length; i++) {
/*  562 */       this.portal_sprites[i] = ImageIO.read(getClass().getResource("/portal/" + i + ".png"));
/*      */     }
/*      */ 
/*  567 */     this.merge_sprite = ImageIO.read(getClass().getResource("/protagonist/protagonist.png"));
/*  568 */     this.protagonist_sprite = new BufferedImage[18];
/*  569 */     for (int p = 0; p < 18; p++) {
/*  570 */       this.protagonist_sprite[p] = this.merge_sprite.getSubimage(p * 32, 0, 32, 58);
/*      */     }
/*      */ 
/*  575 */     this.R1_sprites = new BufferedImage[12];
/*  576 */     for (int i = 0; i < this.R1_sprites.length; i++)
/*  577 */       this.R1_sprites[i] = ImageIO.read(getClass().getResource("/R1/" + i + ".png"));
/*      */   }
/*      */ 
/*      */   public boolean isOnFinish()
/*      */   {
/*  591 */     return (this.ourPlayer.getCentre().x > this.finish.x) && (this.ourPlayer.getCentre().x < this.finish.x + this.finish.width) && (this.ourPlayer.getCentre().y > this.finish.y) && (this.ourPlayer.getCentre().y < this.finish.y + this.finish.height);
/*      */   }
/*      */ 
/*      */   public boolean isWithinSight(Unit u, Unit pl)
/*      */   {
/*  597 */     int x0 = u.getCentre().x;
/*  598 */     int y0 = u.getCentre().y;
/*      */ 
/*  600 */     int x1 = pl.getCentre().x;
/*  601 */     int y1 = pl.getCentre().y;
/*      */ 
/*  604 */     int dy = y1 - y0;
/*  605 */     int dx = x1 - x0;
/*      */     int stepy;
/*  608 */     if (dy < 0) {
/*  609 */       dy = -dy;
/*  610 */       stepy = -1;
/*      */     } else {
/*  612 */       stepy = 1;
/*      */     }
/*      */     int stepx;
/*  614 */     if (dx < 0) {
/*  615 */       dx = -dx;
/*  616 */       stepx = -1;
/*      */     } else {
/*  618 */       stepx = 1;
/*      */     }
/*  620 */     dy <<= 1;
/*  621 */     dx <<= 1;
/*      */ 
/*  623 */     if (!pixelIsEmpty(x0, y0)) {
/*  624 */       return false;
/*      */     }
/*  626 */     if (dx > dy) {
/*  627 */       int fraction = dy - (dx >> 1);
/*  628 */       while (x0 != x1) {
/*  629 */         if (fraction >= 0) {
/*  630 */           y0 += stepy;
/*  631 */           fraction -= dx;
/*      */         }
/*  633 */         x0 += stepx;
/*  634 */         fraction += dy;
/*  635 */         if (!pixelIsEmpty(x0, y0))
/*  636 */           return false;
/*      */       }
/*      */     }
/*      */     else {
/*  640 */       int fraction = dx - (dy >> 1);
/*  641 */       while (y0 != y1) {
/*  642 */         if (fraction >= 0) {
/*  643 */           x0 += stepx;
/*  644 */           fraction -= dy;
/*      */         }
/*  646 */         y0 += stepy;
/*  647 */         fraction += dx;
/*  648 */         if (!pixelIsEmpty(x0, y0)) {
/*  649 */           return false;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  654 */     return true;
/*      */   }
/*      */ 
/*      */   public void hitOfBomb(int hx, int hy)
/*      */   {
/*  665 */     Iterator iter_units = this.units.iterator();
/*  666 */     while (iter_units.hasNext()) {
/*  667 */       Unit temp = (Unit)(Unit)iter_units.next();
/*  668 */       if ((isOnActiveZone(temp)) && 
/*  669 */         (hx > temp.x) && (hx < temp.x + temp.w) && (hy > temp.y) && (hy < temp.y + temp.h))
/*      */       {
/*  672 */         temp.injure(1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean hitOfBluster(Unit u, int hx, int hy)
/*      */   {
/*  683 */     if ((hx > this.ourPlayer.x) && (hx < this.ourPlayer.x + this.ourPlayer.w) && (hy > this.ourPlayer.y) && (hy < this.ourPlayer.y + this.ourPlayer.h))
/*      */     {
/*  685 */       int t = this.rnd.nextInt(5) + 2;
/*  686 */       this.hit_color = t;
/*  687 */       this.ourPlayer.injure(t);
/*  688 */       return true;
/*      */     }
/*      */ 
/*  691 */     Iterator iter_units = this.units.iterator();
/*  692 */     while (iter_units.hasNext()) {
/*  693 */       Unit temp = (Unit)(Unit)iter_units.next();
/*  694 */       if ((isOnActiveZone(temp)) && 
/*  695 */         (hx > temp.x) && (hx < temp.x + temp.w) && (hy > temp.y) && (hy < temp.y + temp.h) && (!temp.equals(u)))
/*      */       {
/*  698 */         temp.injure(this.rnd.nextInt(5) + 2);
/*  699 */         return true;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  704 */     return false;
/*      */   }
/*      */ 
/*      */   public void explosion(int xc, int yc, int r)
/*      */   {
/*  711 */     this.boom_x = (xc - 90);
/*  712 */     this.boom_y = (yc - 90);
/*  713 */     this.runBoom = true;
/*      */ 
/*  715 */     for (int y = yc - r; y <= yc + r; y++) {
/*  716 */       int x1 = (int)Math.round(xc + Math.sqrt(r * r - (y - yc) * (y - yc)));
/*  717 */       int x2 = (int)Math.round(xc - Math.sqrt(r * r - (y - yc) * (y - yc)));
/*  718 */       for (int i = x2; i <= x1; i++)
/*      */       {
/*      */         try
/*      */         {
/*  722 */           int argb = this.bi_init.getColorModel().getRGB(this.bi_init.getRaster().getDataElements(i, y, null));
/*  723 */           Color color = new Color(argb, true);
/*  724 */           if (color.getAlpha() > 200) {
/*  725 */             Color n = new Color(color.getRed(), color.getGreen(), color.getBlue(), 30);
/*      */ 
/*  727 */             this.bi_init.setRGB(i, y, n.getRGB());
/*      */           }
/*  729 */           hitOfBomb(i, y);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean movementIsPossible(Unit u, int x, int y)
/*      */   {
/*      */     try
/*      */     {
/*  743 */       for (int i = 0; i < u.points.size(); i++) {
/*  744 */         Point temp = (Point)u.points.get(i);
/*  745 */         if (!pixelIsEmpty(temp.x + x, temp.y + y))
/*  746 */           return false;
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  750 */       return false;
/*      */     }
/*  752 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean movementIsPossible(Bomb u, int x, int y)
/*      */   {
/*      */     try {
/*  758 */       for (int i = 0; i < u.points.size(); i++) {
/*  759 */         Point temp = (Point)u.points.get(i);
/*  760 */         if (!pixelIsEmpty(temp.x + x, temp.y + y))
/*  761 */           return false;
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  765 */       return false;
/*      */     }
/*  767 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean pixelIsEmpty(int x, int y)
/*      */   {
/*      */     try {
/*  773 */       int argb = this.bi_init.getColorModel().getRGB(this.bi_init.getRaster().getDataElements(x, y, null));
/*  774 */       Color color = new Color(argb, true);
/*  775 */       if (color.getAlpha() > 200) {
/*  776 */         return false;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  791 */       return false;
/*      */     }
/*  793 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean isOnScreen(Unit unit)
/*      */   {
/*  801 */     return (unit.x >= this.offset_x - 100) && (unit.x <= this.offset_x + this.W + 10) && (unit.y >= this.offset_y - 100) && (unit.y <= this.offset_y + this.H + 10);
/*      */   }
/*      */ 
/*      */   public boolean isOnActiveZone(Unit unit)
/*      */   {
/*  811 */     return (unit.x >= this.offset_x - this.W / 2) && (unit.x <= this.offset_x + this.W + this.W / 2) && (unit.y >= this.offset_y - this.H / 2) && (unit.y <= this.offset_y + this.H + this.H / 2);
/*      */   }
/*      */ 
/*      */   public boolean isOnActiveZone(BlusterLine bluster)
/*      */   {
/*  823 */     return (bluster.ax >= this.offset_x - this.W / 2) && (bluster.ax <= this.offset_x + this.W + this.W / 2) && (bluster.ay >= this.offset_y - this.H / 2) && (bluster.ay <= this.offset_y + this.H + this.H / 2);
/*      */   }
/*      */ 
/*      */   public void offset(int to)
/*      */   {
/*  831 */     int sh = 4;
/*  832 */     int s_pl_x = (int)Math.round(this.ourPlayer.speed_x * this.time);
/*  833 */     int s_pl_y = (int)Math.round(this.ourPlayer.speed_y * this.time);
/*      */ 
/*  835 */     if (to == 22) {
/*  836 */       int s = this.offset_x + s_pl_x;
/*  837 */       if ((s + this.W <= this.bi_init.getWidth()) && (s_pl_x > 0)) {
/*  838 */         this.offset_x = s;
/*      */       }
/*      */     }
/*  841 */     if (to == 33) {
/*  842 */       int s = this.offset_y + s_pl_y;
/*  843 */       if ((s + this.H <= this.bi_init.getHeight()) && (s_pl_y > 0)) {
/*  844 */         this.offset_y = s;
/*      */       }
/*      */     }
/*  847 */     if (to == 44) {
/*  848 */       int s = this.offset_x + s_pl_x;
/*  849 */       if ((s >= 0) && (s_pl_x < 0)) {
/*  850 */         this.offset_x = s;
/*      */       }
/*      */     }
/*      */ 
/*  854 */     if (to == 11) {
/*  855 */       int s = this.offset_y + s_pl_y;
/*  856 */       if ((s >= 0) && (s_pl_y < 0))
/*  857 */         this.offset_y = s;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void offset(int to, int b)
/*      */   {
/*  864 */     int sh = b;
/*  865 */     if (to == 22) {
/*  866 */       int s = this.offset_x + sh;
/*  867 */       if (s + this.W <= this.bi_init.getWidth()) {
/*  868 */         this.offset_x = s;
/*      */       }
/*      */     }
/*  871 */     if (to == 33) {
/*  872 */       int s = this.offset_y + sh;
/*  873 */       if (s + this.H <= this.bi_init.getHeight()) {
/*  874 */         this.offset_y = s;
/*      */       }
/*      */     }
/*  877 */     if (to == 44) {
/*  878 */       int s = this.offset_x - sh;
/*  879 */       if (s >= 0) {
/*  880 */         this.offset_x = s;
/*      */       }
/*      */     }
/*      */ 
/*  884 */     if (to == 11)
/*      */     {
/*  886 */       int s = this.offset_y - sh;
/*  887 */       if (s >= 0)
/*  888 */         this.offset_y = s;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void paintComponent(Graphics g)
/*      */   {
/*  895 */     super.paintComponent(g);
/*  896 */     Graphics2D draw = (Graphics2D)g;
/*      */ 
/*  900 */     if (this.runBoom) {
/*  901 */       if (this.boom_i % 2 == 0) {
/*  902 */         this.sh_x = 0;
/*  903 */         this.sh_y = 0;
/*  904 */         this.sh_x += 10 + this.rnd.nextInt(10);
/*  905 */         this.sh_y -= 10 + this.rnd.nextInt(10);
/*      */       } else {
/*  907 */         this.sh_x = 0;
/*  908 */         this.sh_y = 0;
/*  909 */         this.sh_x -= 10 + this.rnd.nextInt(10);
/*  910 */         this.sh_y += 10 + this.rnd.nextInt(10);
/*      */       }
/*      */     } else {
/*  913 */       this.sh_x = 0;
/*  914 */       this.sh_y = 0;
/*      */     }
/*      */ 
/*  917 */     draw.drawImage(this.bi_out, this.sh_x, this.sh_y, null);
/*      */ 
/*  921 */     this.frames += 1;
/*  922 */     this.t1 = System.currentTimeMillis();
/*  923 */     if (this.t1 - this.t0 >= 1000L) {
/*  924 */       this.fps = this.frames;
/*  925 */       this.t0 = this.t1;
/*  926 */       this.frames = 0;
/*      */     }
/*  928 */     g.setColor(Color.red);
/*  929 */     g.drawString("fps: " + this.fps, 10, getHeight() - 10);
/*      */   }
/*      */ 
/*      */   public void keyTyped(KeyEvent e)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void keyPressed(KeyEvent e)
/*      */   {
/*  940 */     if (e.getKeyCode() == 37) {
/*  941 */       this.ourPlayer.speed_x = (-this.speed);
/*      */     }
/*  943 */     if (e.getKeyCode() == 39)
/*      */     {
/*  945 */       this.ourPlayer.speed_x = this.speed;
/*      */     }
/*      */ 
/*  948 */     if (e.getKeyCode() == 27)
/*      */     {
/*  950 */       setVisible(false);
/*  951 */       this.menu_panel.setVisible(true);
/*  952 */       this.menu_panel.items[1].setActive(true);
/*  953 */       transferFocus();
/*      */     }
/*      */ 
/*  959 */     if ((e.getKeyCode() == 32) && (!movementIsPossible(this.ourPlayer, this.ourPlayer.x, this.ourPlayer.y + 5))) {
/*  960 */       double d = -1.1D;
/*      */ 
/*  962 */       this.ourPlayer.speed_y = d;
/*      */     }
/*      */ 
/*  970 */     if (e.getKeyCode() == 66)
/*  971 */       if (this.bomb_thrown == null)
/*      */       {
/*  973 */         if (this.q_bomb > 0) {
/*  974 */           this.bomb_thrown = new Bomb(this);
/*  975 */           this.q_bomb -= 1;
/*      */         }
/*      */       }
/*      */       else
/*  979 */         this.bomb_thrown.boom();
/*      */   }
/*      */ 
/*      */   public void keyReleased(KeyEvent e)
/*      */   {
/* 1017 */     if ((e.getKeyCode() == 37) && 
/* 1018 */       (this.ourPlayer.speed_x == -this.speed)) {
/* 1019 */       this.ourPlayer.speed_x = 0.0D;
/*      */     }
/*      */ 
/* 1023 */     if ((e.getKeyCode() == 39) && 
/* 1024 */       (this.ourPlayer.speed_x == this.speed))
/* 1025 */       this.ourPlayer.speed_x = 0.0D;
/*      */   }
/*      */ }

/* Location:           F:\Works\GrenadeHero\Platformer_2.jar
 * Qualified Name:     Platformer.GamePanel
 * JD-Core Version:    0.6.0
 */