package com.revature.beans;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="spforms")
@Table(name="project1.spforms")
public class SPForm {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String author_first;
	private String author_last;
	@ManyToOne(targetEntity = Author.class)
	@JoinColumn(name="author_id", referencedColumnName = "id")
	private Author author_id;
	private String title;
	private Date end_date;
	@ManyToOne(targetEntity = Story.class)
	@JoinColumn(name="story_type", referencedColumnName = "type")
	private Story story_type;
	@ManyToOne(targetEntity = Genre.class)
	@JoinColumn(name="genre", referencedColumnName = "name")
	private Genre genre;
	private String tag_line;
	private String description;
	private String draft;
	// user input above
	private String status;
	private Date submit_date;
	private String priority;
	@ManyToOne(targetEntity = Editor.class)
	@JoinColumn(name="ae_id", referencedColumnName="id")
	private Editor ae_id;
	private String ae_approval;
	@ManyToOne(targetEntity = Editor.class)
	@JoinColumn(name="ge_id", referencedColumnName="id")
	private Editor ge_id;
	private String ge_approval;
	@ManyToOne(targetEntity = Editor.class)
	@JoinColumn(name="se_id", referencedColumnName="id")
	private Editor se_id;
	private String se_approval;
	private String denial_reason;
	private boolean se_edit;
	private String ae_draft;
	private String ge_draft;
	private String se_draft;
	
	public SPForm() {}
	
	
	
	public SPForm(Integer id, String author_first, String author_last, Author author_id, String title, Date end_date,
			Story story_type, Genre genre, String tag_line, String description, String draft, String status,
			Date submit_date, String priority, Editor ae_id, String ae_approval, Editor ge_id, String ge_approval,
			Editor se_id, String se_approval, String denial_reason, boolean se_edit, String ae_draft, String ge_draft,
			String se_draft) {
		super();
		this.id = id;
		this.author_first = author_first;
		this.author_last = author_last;
		this.author_id = author_id;
		this.title = title;
		this.end_date = end_date;
		this.story_type = story_type;
		this.genre = genre;
		this.tag_line = tag_line;
		this.description = description;
		this.draft = draft;
		this.status = status;
		this.submit_date = submit_date;
		this.priority = priority;
		this.ae_id = ae_id;
		this.ae_approval = ae_approval;
		this.ge_id = ge_id;
		this.ge_approval = ge_approval;
		this.se_id = se_id;
		this.se_approval = se_approval;
		this.denial_reason = denial_reason;
		this.se_edit = se_edit;
		this.ae_draft = ae_draft;
		this.ge_draft = ge_draft;
		this.se_draft = se_draft;
	}



	public SPForm(String first, String last, Author author_id,
			String title, Date end_date, Story story_type, Genre genre,
			String tag_line, String description) {
		this.author_first = first;
		this.author_last = last;
		this.author_id = author_id;
		this.title = title;
		this.end_date = end_date;
		this.story_type = story_type;
		this.genre = genre;
		this.tag_line = tag_line;
		this.description = description;
		this.draft = "";
		this.status = "on hold";
		this.submit_date = new Date(System.currentTimeMillis());
		this.priority = "low";
		this.ae_approval = "pending";
		this.ge_approval = "pending";
		this.se_approval = "pending";
		this.denial_reason = "";
		this.se_edit = false;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAuthor_first() {
		return author_first;
	}
	public void setAuthor_first(String author_first) {
		this.author_first = author_first;
	}
	public String getAuthor_last() {
		return author_last;
	}
	public void setAuthor_last(String author_last) {
		this.author_last = author_last;
	}
	public Author getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(Author author_id) {
		this.author_id = author_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public Story getStory_type() {
		return story_type;
	}
	public void setStory_type(Story story_type) {
		this.story_type = story_type;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public String getTag_line() {
		return tag_line;
	}
	public void setTag_line(String tag_line) {
		this.tag_line = tag_line;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDraft() {
		return draft;
	}
	public void setDraft(String draft) {
		this.draft = draft;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getSubmit_date() {
		return submit_date;
	}
	public void setSubmit_date(Date submit_date) {
		this.submit_date = submit_date;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Editor getAe_id() {
		return ae_id;
	}
	public void setAe_id(Editor ae_id) {
		this.ae_id = ae_id;
	}
	public String getAe_approval() {
		return ae_approval;
	}
	public void setAe_approval(String ae_approval) {
		this.ae_approval = ae_approval;
	}
	public Editor getGe_id() {
		return ge_id;
	}
	public void setGe_id(Editor ge_id) {
		this.ge_id = ge_id;
	}
	public String getGe_approval() {
		return ge_approval;
	}
	public void setGe_approval(String ge_approval) {
		this.ge_approval = ge_approval;
	}
	public Editor getSe_id() {
		return se_id;
	}
	public void setSe_id(Editor se_id) {
		this.se_id = se_id;
	}
	public String getSe_approval() {
		return se_approval;
	}
	public void setSe_approval(String se_approval) {
		this.se_approval = se_approval;
	}
	public String getDenial_reason() {
		return denial_reason;
	}
	public void setDenial_reason(String denial_reason) {
		this.denial_reason = denial_reason;
	}
	public boolean isSe_edit() {
		return se_edit;
	}
	public void setSe_edit(boolean se_edit) {
		this.se_edit = se_edit;
	}
	public String getAe_draft() {
		return ae_draft;
	}
	public void setAe_draft(String ae_draft) {
		this.ae_draft = ae_draft;
	}
	public String getGe_draft() {
		return ge_draft;
	}
	public void setGe_draft(String ge_draft) {
		this.ge_draft = ge_draft;
	}
	public String getSe_draft() {
		return se_draft;
	}
	public void setSe_draft(String se_draft) {
		this.se_draft = se_draft;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ae_approval == null) ? 0 : ae_approval.hashCode());
		result = prime * result + ((ae_draft == null) ? 0 : ae_draft.hashCode());
		result = prime * result + ((ae_id == null) ? 0 : ae_id.hashCode());
		result = prime * result + ((author_first == null) ? 0 : author_first.hashCode());
		result = prime * result + ((author_id == null) ? 0 : author_id.hashCode());
		result = prime * result + ((author_last == null) ? 0 : author_last.hashCode());
		result = prime * result + ((denial_reason == null) ? 0 : denial_reason.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((draft == null) ? 0 : draft.hashCode());
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((ge_approval == null) ? 0 : ge_approval.hashCode());
		result = prime * result + ((ge_draft == null) ? 0 : ge_draft.hashCode());
		result = prime * result + ((ge_id == null) ? 0 : ge_id.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((se_approval == null) ? 0 : se_approval.hashCode());
		result = prime * result + ((se_draft == null) ? 0 : se_draft.hashCode());
		result = prime * result + (se_edit ? 1231 : 1237);
		result = prime * result + ((se_id == null) ? 0 : se_id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((story_type == null) ? 0 : story_type.hashCode());
		result = prime * result + ((submit_date == null) ? 0 : submit_date.hashCode());
		result = prime * result + ((tag_line == null) ? 0 : tag_line.hashCode());
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
		SPForm other = (SPForm) obj;
		if (ae_approval == null) {
			if (other.ae_approval != null)
				return false;
		} else if (!ae_approval.equals(other.ae_approval))
			return false;
		if (ae_draft == null) {
			if (other.ae_draft != null)
				return false;
		} else if (!ae_draft.equals(other.ae_draft))
			return false;
		if (ae_id == null) {
			if (other.ae_id != null)
				return false;
		} else if (!ae_id.equals(other.ae_id))
			return false;
		if (author_first == null) {
			if (other.author_first != null)
				return false;
		} else if (!author_first.equals(other.author_first))
			return false;
		if (author_id == null) {
			if (other.author_id != null)
				return false;
		} else if (!author_id.equals(other.author_id))
			return false;
		if (author_last == null) {
			if (other.author_last != null)
				return false;
		} else if (!author_last.equals(other.author_last))
			return false;
		if (denial_reason == null) {
			if (other.denial_reason != null)
				return false;
		} else if (!denial_reason.equals(other.denial_reason))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (draft == null) {
			if (other.draft != null)
				return false;
		} else if (!draft.equals(other.draft))
			return false;
		if (end_date == null) {
			if (other.end_date != null)
				return false;
		} else if (!end_date.equals(other.end_date))
			return false;
		if (ge_approval == null) {
			if (other.ge_approval != null)
				return false;
		} else if (!ge_approval.equals(other.ge_approval))
			return false;
		if (ge_draft == null) {
			if (other.ge_draft != null)
				return false;
		} else if (!ge_draft.equals(other.ge_draft))
			return false;
		if (ge_id == null) {
			if (other.ge_id != null)
				return false;
		} else if (!ge_id.equals(other.ge_id))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (se_approval == null) {
			if (other.se_approval != null)
				return false;
		} else if (!se_approval.equals(other.se_approval))
			return false;
		if (se_draft == null) {
			if (other.se_draft != null)
				return false;
		} else if (!se_draft.equals(other.se_draft))
			return false;
		if (se_edit != other.se_edit)
			return false;
		if (se_id == null) {
			if (other.se_id != null)
				return false;
		} else if (!se_id.equals(other.se_id))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (story_type == null) {
			if (other.story_type != null)
				return false;
		} else if (!story_type.equals(other.story_type))
			return false;
		if (submit_date == null) {
			if (other.submit_date != null)
				return false;
		} else if (!submit_date.equals(other.submit_date))
			return false;
		if (tag_line == null) {
			if (other.tag_line != null)
				return false;
		} else if (!tag_line.equals(other.tag_line))
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
		return "SPForm [id=" + id + ", author_first=" + author_first + ", author_last=" + author_last + ", author_id="
				+ author_id + ", title=" + title + ", end_date=" + end_date + ", story_type=" + story_type + ", genre="
				+ genre + ", tag_line=" + tag_line + ", description=" + description + ", draft=" + draft + ", status="
				+ status + ", submit_date=" + submit_date + ", priority=" + priority + ", ae_id=" + ae_id
				+ ", ae_approval=" + ae_approval + ", ge_id=" + ge_id + ", ge_approval=" + ge_approval + ", se_id="
				+ se_id + ", se_approval=" + se_approval + ", denial_reason=" + denial_reason + ", se_edit=" + se_edit
				+ ", ae_draft=" + ae_draft + ", ge_draft=" + ge_draft + ", se_draft=" + se_draft + "]";
	}

	
	
}
