/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rss;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.ListDataListener;

/**
 *
 * @author michal
 */
class SourcesComboBox extends JComboBox {
	private final SourcesTable sourceTable;

	public SourcesComboBox(SourcesTable sourceTable) {
		super();

		this.sourceTable = sourceTable;

		try {
			loadSources();
			sourceTable.setSource(null);
		} catch (SQLException ex) {
			Logger.getLogger(SourcesComboBox.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void loadSources() throws SQLException {
		// load sources
		Connection conn;

		conn = Main.getConnection();

		Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM sources");

		ArrayList<Object> items = new ArrayList<Object>();
		items.add("- All -");
        while (rs.next()) {
			items.add(new Source(rs.getInt("id"), rs.getString("name"), rs.getString("url")));
        }

		setModel(new DefaultComboBoxModel(items.toArray()));

        st.close();
	}
}
