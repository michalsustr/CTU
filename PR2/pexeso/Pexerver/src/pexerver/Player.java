/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pexerver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michal
 */
public class Player {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	Player(Socket socket, BufferedReader in, PrintWriter out) {
		this.socket = socket;
		this.in = in;
		this.out = out;
	}

	void write(String message) {
		out.println(message);
	}

	String read() {
		try {
			return in.readLine();
		} catch (IOException ex) {
			Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	void accept() {
		write(StatusMessage.SERVER_CONN_ACCEPT);
	}

	void invalidReply() {
		write(StatusMessage.SERVER_INVALID_REPLY);
	}

	void disconnect() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch(IOException e) {
		}
	}

	void waitForOtherPlayer() {
		write(StatusMessage.SERVER_WAITING_P2);
	}

	void serverBusy() {
		write(StatusMessage.SERVER_BUSY);
	}

}
