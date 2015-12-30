package peclient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Mullins
 */
public class Peclient extends JFrame implements ActionListener {

    private String me = null;
    private final JMenuBar jMenuBar;
    private final JMenu menu;
    private final JPanel panel;
    private JLabel statusBar;
    private final JMenuItem connect;
    private final JMenuItem disconnect;
    private Socket server = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private int player;
    private ArrayList<Card> gameState;
    private ArrayList<JButton> buttons;
    private String[] turned = null;
    private static final int SHOWCARDS = 5000;
    private int[] cardsPicked;
    private int counter;
    private String dir;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////        INIT METHODS        ////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Sets the GUI including actionlisteners
     */
    public Peclient() {

        super("Pexeso po síti");
        setSize(700, 700);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        Container con = getContentPane();

        jMenuBar = new JMenuBar();
        menu = new JMenu("Hra");
        connect = new JMenuItem("Připojit k serveru");
        disconnect = new JMenuItem("Odpojit od serveru");

        con.add(jMenuBar, BorderLayout.NORTH);
        jMenuBar.add(menu);
        menu.add(connect);
        connect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (init()) {
                    statusBar = new JLabel(" Connected to server.");
                    initGame();
                    play();
                } else {
                    disconnect();
                }
            }
        });
        menu.add(disconnect);
        disconnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                disconnect();
            }
        });
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Určitě chcete ukončit aplikaci?",
                        "Konec aplikace",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    disconnect();
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });
        panel = new JPanel(new GridLayout(10, 10, 7, 5), rootPaneCheckingEnabled);
        panel.setBorder(new EmptyBorder(7, 5, 5, 0));
        buttons = new ArrayList<JButton>(100);
        
        dir = System.getProperty("user.dir");
        for (int i = 0; i < 100; i++) {
            buttons.add(new JButton(new ImageIcon(dir + "/img/pex/foto.jpg")));
            buttons.get(i).setName("" + i);
            buttons.get(i).addActionListener(this);
            panel.add(buttons.get(i)).setEnabled(false);
        }
        con.add(panel, BorderLayout.CENTER);

        statusBar = new JLabel(" Client up. Not connected to server.");
        statusBar.setBorder(new BevelBorder(1));
        statusBar.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
        con.add(statusBar, BorderLayout.SOUTH);

        try {
            DisplayMode current = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
            setLocation((current.getWidth() - getWidth()) / 2, (current.getHeight() - getHeight()) / 2);
        } catch (Exception e) {
            Logger.getLogger(Peclient.class.getName()).log(Level.FINE, null, e);
        }
        
        cardsPicked = new int[2];
    }

    public static void main(String[] args) {
        (new Peclient()).setVisible(true);
    }

    /**
     * Connects to server and resolves, which player this is;
     *
     * @return
     */
    private boolean init() {

        String serverMessage;
        boolean ret = false;
        gameState = new ArrayList<Card>(100);

        try {
            server = new Socket("localhost", 4444);
            out = new PrintWriter(server.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            if (((serverMessage = in.readLine())).contains(MSG.SERVER_CONN_ACCEPT)) {
                player = Integer.parseInt(serverMessage.split("::")[3]);
            }
            System.out.println(serverMessage);
            statusBar.setText(" " + serverMessage);
            if (player == 1 || player == 2) {
                ret = true;
            }
        } catch (UnknownHostException e) {
            Logger.getLogger(Peclient.class.getName()).log(Level.SEVERE, null, e);
            System.err.println("Don't know about host: localhost.");
            JOptionPane.showMessageDialog(null, "Nepodařilo se navázat spojení se serverem.", "Chyba spojení.", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            Logger.getLogger(Peclient.class.getName()).log(Level.SEVERE, null, e);
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            JOptionPane.showMessageDialog(null, "Nepodařilo se navázat spojení se serverem.", "Chyba spojení.", JOptionPane.ERROR_MESSAGE);
        }
        return ret;
    }

    /**
     * Disconnects from server and informs it with a message.
     *
     */
    private void disconnect() {
        if (out != null || in != null || server != null) {
            try {
                out.println(MSG.CLIENT_DISCONNECT);
                System.out.println(MSG.CLIENT_DISCONNECT);
                out.close();
                in.close();
                server.close();
                statusBar = new JLabel(" Disconnected from server.");
            } catch (IOException ex) {
                Logger.getLogger(Peclient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////        GAME METHODS        ////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     *
     * @param serverMessage
     * @return <b>true</b> if disconnected from server.
     */
    public boolean parseInput(String serverMessage) {

        boolean ret = false;
        String[] gs = null;
        serverMessage.trim();
        String[] res = serverMessage.split("::");

        if (serverMessage.equals(MSG.SERVER_CONN_ACCEPT)) {
            me = res[3];
            statusBar.setText(serverMessage);
        } else if ((serverMessage.equals(MSG.SERVER_WIN_1) && me.equals("1")) || (serverMessage.equals(MSG.SERVER_WIN_2) && me.equals("2"))) {
            JOptionPane.showMessageDialog(null, "You have won the game. Disconnecting from server.", "Winner", JOptionPane.INFORMATION_MESSAGE);
            statusBar.setText(serverMessage);
            disconnect();
        } else if ((serverMessage.equals(MSG.SERVER_WIN_1) && !me.equals("1")) || (serverMessage.equals(MSG.SERVER_WIN_2) && !me.equals("2"))) {
            JOptionPane.showMessageDialog(null, "You have lost the game. Disconnecting from server.", "Loser", JOptionPane.INFORMATION_MESSAGE);
            statusBar.setText(serverMessage);
            ret = true;
            disconnect();
        } else if (serverMessage.equals(MSG.SERVER_WIN_TIE)) {
            JOptionPane.showMessageDialog(null, "No-one had enough cards to win. Disconnecting from server.", "Tie", JOptionPane.INFORMATION_MESSAGE);
            statusBar.setText(serverMessage);
            ret = true;
            disconnect();
        } else if (serverMessage.equals(MSG.SERVER_INVALID_DATA)) {
            statusBar.setText(serverMessage);
            sendCards();
        } else if (serverMessage.contains(MSG.SERVER_GAMESTATE)) {
            statusBar.setText(MSG.SERVER_GAMESTATE);
            res[2] = res[2].substring(1, res[2].length() - 1);
            System.out.println(res[2]);
            gs = res[2].split(",");
            System.out.println(gs);
            gameState.clear();
            int gr;
            for (int i = 0; i < 100; i++) {
                gs[i] = gs[i].trim();
                System.out.println(gs);
                gr = Integer.parseInt(gs[i].split(":")[0]);
                gameState.add(i, new Card(gr, Owner.valueOf(gs[i].split(":")[1])));
            }
        } else if (serverMessage.equals(MSG.SERVER_START_GAME)) {
            statusBar.setText(serverMessage);
        } else if (serverMessage.contains(MSG.SERVER_TURNED)) {
            statusBar.setText(MSG.SERVER_TURNED);
            turnCards(Integer.parseInt(res[2]), Integer.parseInt(res[3]));
        } else if (serverMessage.contains(MSG.SERVER_ON_TURN)) {
            statusBar.setText(serverMessage);
                pickCards();
        } else if (serverMessage.equals(MSG.SERVER_WAITING_P2)) {
            statusBar.setText(serverMessage);
        } else if (serverMessage.equals(MSG.SERVER_START_AI)) {
            statusBar.setText(serverMessage);
        } else if (serverMessage.equals(MSG.SERVER_INVALID_REPLY)) {
            statusBar.setText(serverMessage);
            ret = true;
            disconnect();
        }
        return ret;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();
        if (src instanceof JButton) {
            counter++;
            if (counter > 2) {
                panel.setEnabled(false);
                sendCards();
                return;
            }
            JButton b = (JButton) src;
            String name = b.getName().trim();
            b.setIcon(new ImageIcon(dir + "/img/pex" + name + ".png"));
            if (counter == 1){
                cardsPicked[0]=Integer.parseInt(name);
            } else {
                cardsPicked[1]=Integer.parseInt(name);
            }
            panel.updateUI();
        }
    }

    /**
     * Resolves, whether player wnats to play against someone else or against
     * computer.
     *
     */
    private void initGame() {

        if (player == 1) {
            String[] sel = new String[]{"S člověkem", "S počítačem"};
            Object op = JOptionPane.showInputDialog(null, "Připojen. S kým chceš hrát?", "Server",
                    JOptionPane.QUESTION_MESSAGE, null, sel, 0);
            if (op.equals(sel[0])) {
                out.println(MSG.CLIENT_PVP);
                System.out.println(MSG.CLIENT_PVP);
            } else if (op.equals(sel[1])) {
                out.println(MSG.CLIENT_AI);
                System.out.println(MSG.CLIENT_AI);
            } else {
                disconnect();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Připojen, páruji s čekajícím hráčem.", "Server", JOptionPane.INFORMATION_MESSAGE);
            out.println(MSG.CLIENT_READY);
            System.out.println(MSG.CLIENT_READY);
        }
    }

    /**
     * Game sequence of player.
     *
     */
    private void play() {

        String message = null;
        boolean finished = false;

        while (!finished) {
            try {
                if ((message = in.readLine()) != null) {
                    finished = parseInput(message);
                }
            } catch (IOException ex) {
                Logger.getLogger(Peclient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Sends picked cards to the server.
     *
     */
    private void sendCards() {

        out.println(MSG.CLIENT_SEND_CARDS + cardsPicked[0] + "::" + cardsPicked[1]);
    }

    /**
     * Enables picking of cards temporarily to let player choose two of them.
     *
     */
    private void pickCards() {
        
        cardsPicked[0] = -1;
        cardsPicked[1] = -1;
        panel.setEnabled(true);
        counter = 0;
    }

    /**
     * Shows the cards the other player turned.
     *
     * @param c1
     * @param c2
     */
    private void turnCards(int c1, int c2) {
        buttons.get(c1).setIcon(new ImageIcon(dir + "/img/pex" + c1 + ".png"));
        buttons.get(c1).setEnabled(true);
        buttons.get(c2).setIcon(new ImageIcon(dir + "/img/pex" + c2 + ".png"));
        buttons.get(c2).setEnabled(true);
        try {
            Thread.sleep(SHOWCARDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(Peclient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
            buttons.get(c1).setIcon(new ImageIcon(dir + "/img/pex/foto.jpg"));
            buttons.get(c1).setEnabled(false);
            buttons.get(c2).setIcon(new ImageIcon(dir + "/img/pex/foto.jpg"));
            buttons.get(c2).setEnabled(false);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    class MSG {

        public static final String CLIENT_DISCONNECT = "CLIENT::Disconnect";
        public static final String CLIENT_READY = "CLIENT::Ready";
        public static final String CLIENT_AI = "CLIENT::AI";
        public static final String CLIENT_PVP = "CLIENT::PVP";
        public static final String CLIENT_SEND_CARDS = "CLIENT::Cards::";
        public static final String SERVER_WIN_1 = "SERVER::Winner::Player::1";
        public static final String SERVER_WIN_2 = "SERVER::Winner::Player::2";
        public static final String SERVER_WIN_TIE = "SERVER::Winner::No-one";
        public static final String SERVER_INVALID_DATA = "SERVER::Invalid data received::Re-send.";
        public static final String SERVER_GAMESTATE = "SERVER::Gamestate::";
        public static final String SERVER_START_GAME = "SERVER::Game starts.";
        public static final String SERVER_TURNED = "SERVER::Turned::";
        public static final String SERVER_ON_TURN = "SERVER::Player on turn::";
        public static final String SERVER_CONN_ACCEPT = "SERVER::Connection accepted::You are Player::";
        public static final String SERVER_WAITING_P2 = "SERVER::Waiting for Player::2";
        public static final String SERVER_START_AI = "SERVER::Starting::AI";
        public static final String SERVER_INVALID_REPLY = "SERVER::Invalid reply::Disconnecting.";
    }
}
