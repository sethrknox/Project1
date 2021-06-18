package com.revature.daos;

import java.util.List;

import com.revature.beans.SPForm;

public interface SPFormDAO extends GenericDAO<SPForm>{

	//public void addForm(SPForm spf);
	public List<SPForm> getForms(Integer id);
}
