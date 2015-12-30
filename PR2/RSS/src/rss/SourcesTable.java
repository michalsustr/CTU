/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rss;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author michal
 */
class SourcesTable extends JTable {


	private Integer source = null;

	public SourcesTable() {
		super();
		setModel(new SourcesTableModel());
	}

	public void setSource(Integer src) throws SQLException {
		this.source = src;

		// load sources
		Connection conn = Main.getConnection();

		Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM posts "
			+ ((source != null) ? "WHERE source_id = "+source : "")
			+ " ORDER BY pubdate DESC"
		);

		ArrayList<Post> posts = ((SourcesTableModel) getModel()).getPosts();
		posts.clear();

        while (rs.next()) {
			posts.add( new Post(
				rs.getInt("id"),
				rs.getString("title"),
				rs.getString("link"),
				rs.getString("description"),
				rs.getDate("pubdate")
			));
        }

		updateUI();

		// select the first item
		getSelectionModel().setSelectionInterval(0,0);
	}

	protected static class SourcesTableModel extends AbstractTableModel {

		public ArrayList<Post> posts;
		private String[] columns = {"Title", "Published"};

		public SourcesTableModel() {
			posts = new ArrayList<Post>();
		}

		public ArrayList<Post> getPosts() {
			return posts;
		}

		public int getRowCount() {
			return posts.size();
		}

		public int getColumnCount() {
			return this.columns.length;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			Post row = posts.get(rowIndex);
			switch(columnIndex) {
				case 0: // name
					return row.getTitle();
				case 1: // payload
					return row.getFormattedDate();
				default:
					return "";
			}
		}

		@Override
		public String getColumnName(int column) {
			return this.columns[ column ];
		}
	}

}
