/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rss;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author michal
 */
public class Main {
	private static Connection conn;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
				try {
					new MainWindow().setVisible(true);
				} catch (SQLException ex) {
					System.err.println("sql error");
				}
            }
		});
    }


	public static Connection getConnection() throws SQLException {
		if(conn == null) {
			String url = "jdbc:postgresql://localhost/rss";
			conn = DriverManager.getConnection(url, "postgres", "lit2turi");
		}
		return conn;
	}



}
