package com.revature.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="requests")
@Table(name="project1.requests")
public class EditorRequest {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(targetEntity = SPForm.class)
	@JoinColumn(name="form_id", referencedColumnName = "id")
	private SPForm form_id;
	@ManyToOne(targetEntity = Editor.class)
	@JoinColumn(name="req_id", referencedColumnName = "id")
	private Editor req_id;
	private String receiver;
	private String request;
	private String response;
	
	public EditorRequest() {}

	public EditorRequest(Integer id, SPForm form_id, Editor req_id, String receiver, String request, String response) {
		super();
		this.id = id;
		this.form_id = form_id;
		this.req_id = req_id;
		this.receiver = receiver;
		this.request = request;
		this.response = response;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SPForm getForm_id() {
		return form_id;
	}

	public void setForm_id(SPForm form_id) {
		this.form_id = form_id;
	}

	public Editor getReq_id() {
		return req_id;
	}

	public void setReq_id(Editor req_id) {
		this.req_id = req_id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((form_id == null) ? 0 : form_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
		result = prime * result + ((req_id == null) ? 0 : req_id.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result + ((response == null) ? 0 : response.hashCode());
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
		EditorRequest other = (EditorRequest) obj;
		if (form_id == null) {
			if (other.form_id != null)
				return false;
		} else if (!form_id.equals(other.form_id))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (receiver == null) {
			if (other.receiver != null)
				return false;
		} else if (!receiver.equals(other.receiver))
			return false;
		if (req_id == null) {
			if (other.req_id != null)
				return false;
		} else if (!req_id.equals(other.req_id))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EditorRequest [id=" + id + ", form_id=" + form_id + ", req_id=" + req_id + ", receiver=" + receiver
				+ ", request=" + request + ", response=" + response + "]";
	}

	
	
	
	
}
