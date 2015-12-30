/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbtest;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author michal
 */
@Entity
@Table(name = "sources")
@NamedQueries({
	@NamedQuery(name = "Sources.findAll", query = "SELECT s FROM Sources s"),
	@NamedQuery(name = "Sources.findById", query = "SELECT s FROM Sources s WHERE s.id = :id"),
	@NamedQuery(name = "Sources.findByName", query = "SELECT s FROM Sources s WHERE s.name = :name"),
	@NamedQuery(name = "Sources.findByUrl", query = "SELECT s FROM Sources s WHERE s.url = :url")})
public class Source implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	@Basic(optional = false)
    @Column(name = "name")
	private String name;
	@Basic(optional = false)
    @Column(name = "url")
	private String url;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sources")
	private Collection<Post> postCollection;

	public Source() {
	}

	public static Source createSource(Integer id, String name, String url) {
        Source source = new Source(id,name,url);
        return source;
  }

	public Source(Integer id) {
		this.id = id;
	}

	public Source(Integer id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Collection<Post> getPostCollection() {
		return postCollection;
	}

	public void setPostCollection(Collection<Post> postCollection) {
		this.postCollection = postCollection;
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
		if (!(object instanceof Source)) {
			return false;
		}
		Source other = (Source) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "dbtest.Sources[id=" + id + "]";
	}

}
