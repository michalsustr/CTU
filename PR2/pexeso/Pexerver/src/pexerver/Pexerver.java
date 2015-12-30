package pexerver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mullins
 */
public class Pexerver {
	private static int numberOfCards = 100;
	private static int port = 4444;

    private Socket playerSocket;
    private ServerSocket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientMessage;
    private GameState gameState;

	private int playerCount;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////        INIT METHODS        ////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Pexerver() {
        gameState = new GameState(numberOfCards);
    }

    public static void main(String[] args) {
        Pexerver server = new Pexerver();
        server.init();

    }

    /**
     * Init method is called right after server start. Opens port, and accepts
     * first player. Then it redirects him to one or two players game, or
     * disconnects him;
     */
    public void init() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+port);
            System.exit(1);
        }

		Player player, playerFirst;
		String playerMessage;
		playerCount = 0;
		boolean waitingForPlayer = false;

        while (true) {
            try {
                player = waitForPlayer();
				playerCount++;

				if(playerCount == 1) {
					player.accept();
					playerMessage = player.read();

					if (playerMessage.equals(StatusMessage.CLIENT_AI)) {
						onePlayerGame(player);
						continue;
					} else if (playerMessage.equals(StatusMessage.CLIENT_PVP)) {
						waitingForPlayer = true;
						player.waitForOtherPlayer();
						playerFirst = player;
						continue;
					} else {
						player.invalidReply();
					}
				} else if (playerCount == 2 && waitingForPlayer) {
					player.accept();
					playerMessage = player.read();

					if (playerMessage.equals(StatusMessage.CLIENT_PVP)) {
						waitingForPlayer = false;
						twoPlayerGame(playerFirst, player);
						continue;
					} else {
						player.invalidReply();
					}
					
				}

				// in case of error
				player.serverBusy();
				disconnect(player);
				
            } catch (IOException ex) {
                System.err.println("Accept failed.");
                Logger.getLogger(Pexerver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                System.out.println("Program exception." + e.getMessage());
                Logger.getLogger(Pexerver.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

	private Player waitForPlayer() throws IOException {
		Socket socket = serverSocket.accept();
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		return new Player(socket, in, out);
	}

	private void disconnect(Player player) {
		player.disconnect();
		playerCount--;
	}



    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////        GAME METHODS        ////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Takes of the game for two players. Firstly waits for connection of second
     * player, then starts the game. In the end it gets the results.
     *
     * @throws SocketException
     */
    public void twoPlayerGame(Player player1, Player player2) throws SocketException {
        boolean finished = false, connected = false;


        out1.println(MSG.SERVER_START_GAME);
        out2.println(MSG.SERVER_START_GAME);
        System.out.println(MSG.SERVER_START_GAME);
        gameInit();
        out1.println(MSG.SERVER_GAMESTATE + gameState);
        out2.println(MSG.SERVER_GAMESTATE + gameState);
        System.out.println(MSG.SERVER_GAMESTATE + gameState);

        while (!finished) {
            finished = processInput(in1, out1, 1, player1);
            if (finished) {
                break;
            }
            finished = processInput(in2, out2, 2, player2);
            if (finished) {
                break;
            }
        }
        getResults();
    }

    /**
     * Initializes game state
     */
    public void gameInit() {

        gameState.clear();
        for (int i = 0; i < 100; i++) {
            gameState.add(i, new Card(i % 50, Owner.GAME));
        }
        Collections.shuffle(gameState);
    }

    /**
     * Resolves, whether client wants to continue the game or whether sends
     * gameState. Also processes input from client.
     *
     * @param in
     * @param out
     * @param player
     * @param socket
     */
    public boolean processInput(BufferedReader in, PrintWriter out, int player, Socket socket) throws SocketException {

        String input;
        int c1, c2;

        while (true) {
            try {
                out.println(player == 1 ? MSG.SERVER_ON_TURN_1 : MSG.SERVER_ON_TURN_2);
                input = in.readLine();
                input.trim();
                System.out.println(input);
                String[] result = input.split("::");
                if (result.length != 4) {
                    out.println(MSG.SERVER_INVALID_DATA);
                    System.out.println(MSG.SERVER_INVALID_DATA);
                    continue;
                }
                if (result[0].equals("CLIENT")) {
                    if (result[1].equals("Cards")) {
                        c1 = Integer.parseInt(result[2]);
                        c2 = Integer.parseInt(result[3]);
                        if (c1 < 100 && c1 >= 0 && c2 < 100 && c2 >= 0) {
                            turnCards(c1, c2, player);
                            if (player == 1) {
                                out2.println(MSG.SERVER_GAMESTATE + gameState);
                                out2.println(MSG.SERVER_TURNED + c1 + "::" + c2);
                                System.out.println(MSG.SERVER_GAMESTATE + gameState);
                                System.out.println(MSG.SERVER_TURNED + c1 + "::" + c2);
                            } else {
                                out1.println(MSG.SERVER_GAMESTATE + gameState);
                                out1.println(MSG.SERVER_TURNED + c1 + "::" + c2);
                                System.out.println(MSG.SERVER_GAMESTATE + gameState);
                                System.out.println(MSG.SERVER_TURNED + c1 + "::" + c2);
                            }
                            for (int i = 0; i < gameState.size(); i++) {
                                if (!gameState.get(i).getOwner().equals(Owner.GAME)) {
                                    return true;
                                }
                            }
                            return false;
                        } else {
                            out.println(MSG.SERVER_INVALID_DATA);
                            System.out.println(MSG.SERVER_INVALID_DATA);
                            continue;
                        }
                    } else if (result[1].equalsIgnoreCase("Disconnect")) {//client wants to disconnect
                        disconnect(socket, out, in);
                        return true;
                    } else {//some bullshit received
                        out.println(MSG.SERVER_INVALID_DATA);
                        System.out.println(MSG.SERVER_INVALID_DATA);
                        continue;
                    }
                } else {//not from client, disconnect
                    disconnect(socket, out, in);
                    return true;
                }
            } catch (IOException ex) {
                Logger.getLogger(Pexerver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Turns cards and checks whether they belong to the same group. If yes,
     * changes owner of this group.
     *
     * @param c1
     * @param c2
     * @param player
     */
    private void turnCards(int c1, int c2, int player) {

        if (gameState.get(c1).getGroup() == gameState.get(c2).getGroup()) {
            gameState.get(c1).setOwner(player == 1 ? Owner.PLAYER1 : Owner.PLAYER2);
            gameState.get(c2).setOwner(player == 1 ? Owner.PLAYER1 : Owner.PLAYER2);
        }
    }

    /**
     * Prints who is the winner to both clients.
     */
    public void getResults() {

        int p1 = 0, p2 = 0, blank = 0;

        for (int i = 0; i < gameState.size(); i++) {
            if (gameState.get(i).getOwner().equals(Owner.PLAYER1)) {
                p1++;
            } else if (gameState.get(i).getOwner().equals(Owner.PLAYER2)) {
                p2++;
            } else {
                blank++;
            }
        }
        if (p1 < p2) {
            out1.println(MSG.SERVER_WIN_2);
            out2.println(MSG.SERVER_WIN_2);
            System.out.println(MSG.SERVER_WIN_2);
        } else if (p1 > p2) {
            out1.println(MSG.SERVER_WIN_1);
            out2.println(MSG.SERVER_WIN_1);
            System.out.println(MSG.SERVER_WIN_1);
        } else if (((p1 + p2) <= blank) || (p1 == p2)) {
            out1.println(MSG.SERVER_WIN_TIE);
            out2.println(MSG.SERVER_WIN_TIE);
            System.out.println(MSG.SERVER_WIN_TIE);
        }
    }

    public ArrayList<Card> getGameState() {
        return gameState;
    }

    public void setGameState(ArrayList<Card> gameState) {
        this.gameState = gameState;
    }

    private void onePlayerGame() throws SocketException {

        boolean finished = false;
        ArtificialIntelligence ai = new ArtificialIntelligence();

        out1.println(MSG.SERVER_START_GAME);
        gameInit();
        out1.println(MSG.SERVER_GAMESTATE + gameState);
        while (!finished) {
            finished = processInput(in1, out1, 1, player1);
            if (finished) {
                break;
            }
            ai.play(gameState);
            int cards[] = ai.pickCards();
            turnCards(cards[0], cards[1], 2);
        }
        getResults();
    }
}
