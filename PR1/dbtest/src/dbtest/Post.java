/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbtest;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author michal
 */
@Entity
@Table(name = "posts")
@NamedQueries({
	@NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p"),
	@NamedQuery(name = "Post.findById", query = "SELECT p FROM Post p WHERE p.id = :id"),
	@NamedQuery(name = "Post.findByTitle", query = "SELECT p FROM Post p WHERE p.title = :title"),
	@NamedQuery(name = "Post.findByLink", query = "SELECT p FROM Post p WHERE p.link = :link"),
	@NamedQuery(name = "Post.findByDescription", query = "SELECT p FROM Post p WHERE p.description = :description"),
	@NamedQuery(name = "Post.findByPubdate", query = "SELECT p FROM Post p WHERE p.pubdate = :pubdate")})
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	@Basic(optional = false)
    @Column(name = "title")
	private String title;
	@Basic(optional = false)
    @Column(name = "link")
	private String link;
	@Basic(optional = false)
    @Column(name = "description")
	private String description;
	@Basic(optional = false)
    @Column(name = "pubdate")
    @Temporal(TemporalType.TIMESTAMP)
	private Date pubdate;
	@JoinColumn(name = "source_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Source sources;

	public Post() {
	}

	public Post(Integer id) {
		this.id = id;
	}

	public Post(Integer id, String title, String link, String description, Date pubdate) {
		this.id = id;
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubdate = pubdate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPubdate() {
		return pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	public Source getSources() {
		return sources;
	}

	public void setSources(Source sources) {
		this.sources = sources;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Post)) {
			return false;
		}
		Post other = (Post) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "dbtest.Post[id=" + id + "]";
	}

}
