package com.epam.testapp.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * News bean
 */

@Entity
@Table(name = "NEWS")
@NamedQueries({
		@NamedQuery(name = "News.delete", query = "DELETE FROM News n where n.id in (:idList)"),
		@NamedQuery(name = "News.list", query = "FROM News n ORDER BY n.editionDate DESC"), })
public class News implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String brief;
	private String content;
	private Date editionDate;

	public News() {
		editionDate = new Date();
	}

	public News(int id, String title, String brief, String content) {
		setId(id);
		setTitle(title);
		setBrief(brief);
		setContent(content);
		setEditionDate(Calendar.getInstance().getTime());
	}

	// @Generated(GenerationTime.INSERT)
	@SequenceGenerator(name = "sequenceGenerator", sequenceName = "NEWS_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@Id
	@Column(name = "news_id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "title")
	@Size(max = 100)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "brief")
	@Size(max = 500)
	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	@Column(name = "content")
	@Size(max = 2000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "edition_date")
	public Date getEditionDate() {
		return editionDate;
	}

	public void setEditionDate(Date editionDate) {
		this.editionDate = editionDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brief == null) ? 0 : brief.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((editionDate == null) ? 0 : editionDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		if (brief == null) {
			if (other.brief != null)
				return false;
		} else if (!brief.equals(other.brief))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (editionDate == null) {
			if (other.editionDate != null)
				return false;
		} else if (!editionDate.equals(other.editionDate))
			return false;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", title=" + title
				+ ", brief=" + brief + ", content=" + content
				+ ", editionDate=" + editionDate + "]";
	}

}
