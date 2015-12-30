/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



/**
 *
 * @author michal
 */
class Post {

	private Integer id;
	private String title, link, description;
	private Date date;

	public Post(Integer id, String title, String link, String description, Date date) {
		this.id = id;
		this.title = title;
		this.link = link;
		this.description = description;
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public String getFormattedDate() {
		SimpleDateFormat formatovac = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("cs"));
		return formatovac.format(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



}
