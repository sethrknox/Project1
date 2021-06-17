package com.revature.daos;

import com.revature.beans.SPForm;

public interface SPFormDAO {

	public void addForm(SPForm spf);
	public SPForm getForm(Integer id);
}
