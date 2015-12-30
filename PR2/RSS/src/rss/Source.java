/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rss;

/**
 *
 * @author michal
 */
public class Source {
	private int id;
	private String name, url;

	Source(int id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return name;
	}


	
}
