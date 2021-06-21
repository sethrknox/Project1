package com.revature.services;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.revature.beans.Author;
import com.revature.beans.Editor;
import com.revature.beans.SPForm;
import com.revature.daos.AuthorDAOImpl;
import com.revature.daos.EditorDAO;
import com.revature.daos.EditorDAOImpl;
import com.revature.daos.SPFormDAO;
import com.revature.daos.SPFormDAOImpl;

public class SPFormServiceImpl implements SPFormService {

	private SPFormDAO sdao = new SPFormDAOImpl();
	private AuthorDAOImpl adao = new AuthorDAOImpl();
	private EditorDAO edao = new EditorDAOImpl();
	
	@Override
	public void createForm(SPForm spf, Integer author_id) {
		// TODO Auto-generated method stub
		Author a = adao.getById(author_id);
		spf.setAuthor_id(a);
		Date d = new Date(System.currentTimeMillis());
		spf.setSubmit_date(d);
		spf.setPriority("low");
		spf.setAe_approval("pending");
		spf.setGe_approval("pending");
		spf.setSe_approval("pending");
		spf.setAe_draft("pending");
		spf.setGe_draft("pending");
		spf.setSe_draft("pending");
		Integer points = a.getPoints();
		Integer cost = 0;
		String type = spf.getStory_type().getType();
		if ("novel".equals(type.toLowerCase())) {
			cost = 50;
		} else if ("novella".equals(type)) {
			cost = 25;
		} else if ("short story".equals(type)) {
			cost = 20;
			spf.setAe_draft("approved");
		} else if ("article".equals(type)) {
			cost = 10;
			spf.setAe_draft("approved");
			spf.setGe_draft("approved");
		}
		if (points < cost) {
			spf.setStatus("on hold");
		} else {
			spf.setStatus("pending");
			adao.updatePoints(author_id, cost, points);
		}
		String genre = spf.getGenre().getName();
		boolean hasAssistants = sdao.hasAssistants(genre);
		if (!hasAssistants) {
			spf.setAe_approval("approved");
		}
		sdao.add(spf);
	}

	@Override
	public void resubmit(Integer form_id, Integer author_id) {
		// TODO Auto-generated method stub
		Author a = adao.getById(author_id);
		SPForm spf = sdao.getById(form_id);
		Integer points = a.getPoints();
		Integer cost = 0;
		String type = spf.getStory_type().getType();
		if ("novel".equals(type)) {
			cost = 50;
		} else if ("novella".equals(type)) {
			cost = 25;
		} else if ("short story".equals(type)) {
			cost = 20;
		} else if ("article".equals(type)) {
			cost = 10;
		}
		spf.setStatus("pending");
		Date d = new Date(System.currentTimeMillis());
		spf.setSubmit_date(d);
		adao.updatePoints(author_id, cost, points);
		sdao.resubmit(form_id);
	}

	@Override
	public List<SPForm> getEditorForms(Integer id, String type) {
		// TODO Auto-generated method stub
		List<SPForm> forms = sdao.getEditorForms(id, type);
		formPriorityUpdate(forms);
		return forms;
	}

	@Override
	public List<SPForm> getForms(Integer id) {
		// TODO Auto-generated method stub
		List<SPForm> forms = sdao.getForms(id);
		formPriorityUpdate(forms);
		return forms;
	}
	
	private void formPriorityUpdate(List<SPForm> forms) {
		if (forms == null) {
			return;
		}
		Date d = new Date(System.currentTimeMillis());
		LocalDate curr_date = d.toLocalDate();
		boolean highPriority = false;
		for (SPForm s : forms) {
			highPriority = false;
			LocalDate submit_date = s.getSubmit_date().toLocalDate();
			Integer submit_year = submit_date.getYear();
			Integer curr_year = curr_date.getYear();
			Integer submit_day = submit_date.getDayOfYear();
			Integer curr_day = curr_date.getDayOfYear();
			if (submit_year < curr_year - 1) {
				highPriority = true;
			} else if (submit_year == curr_year - 1) {
				if ((365 - submit_day) + curr_day > 7) {
					highPriority = true;
				}
			} else if (submit_year == curr_year) {
				if (submit_day < curr_day - 7) {
					highPriority = true;
				}
			}
			if (highPriority) {
				s.setPriority("high");
				sdao.update(s);
			}
		}
	}

	@Override
	public void approve(Integer form_id, Integer editor_id, String type) {
		// TODO Auto-generated method stub
		SPForm spf = sdao.getById(form_id);
		switch(type) {
		case "assistant": {
			spf.setAe_approval("approved");
			Editor e = edao.getById(editor_id);
			spf.setAe_id(e);
			break;
		}
		case "general": {
			spf.setGe_approval("approved");
			Editor e = edao.getById(editor_id);
			spf.setGe_id(e);
			break;
		}
		case "senior": {
			spf.setSe_approval("approved");
			Editor e = edao.getById(editor_id);
			spf.setSe_id(e);
			break;
		}
		default: {
			break;
		}
		}
		sdao.update(spf);
	}

	@Override
	public void deny(Integer form_id, String reason, Integer editor_id, String type) {
		// TODO Auto-generated method stub
		SPForm spf = sdao.getById(form_id);
		Author a = spf.getAuthor_id();
		spf.setStatus("denied");
		spf.setDenial_reason(reason);
		Integer points = a.getPoints();
		Integer cost = 0;
		String story_type = spf.getStory_type().getType();
		if ("novel".equals(story_type)) {
			cost = 50;
		} else if ("novella".equals(story_type)) {
			cost = 25;
		} else if ("short story".equals(story_type)) {
			cost = 20;
		} else if ("article".equals(story_type)) {
			cost = 10;
		}
		adao.updatePoints(a.getId(), -cost, points);
		switch(type) {
		case "assistant": {
			spf.setAe_approval("denied");
			Editor e = edao.getById(editor_id);
			spf.setAe_id(e);
			break;
		}
		case "general": {
			spf.setGe_approval("denied");
			Editor e = edao.getById(editor_id);
			spf.setGe_id(e);
			break;
		}
		case "senior": {
			spf.setSe_approval("denied");
			Editor e = edao.getById(editor_id);
			spf.setSe_id(e);
			break;
		}
		default: {
			break;
		}
		}
		sdao.update(spf);
	}

	@Override
	public void submitDraft(Integer draft_id, InputStream inputStream) {
		// TODO Auto-generated method stub
		sdao.submitDraft(draft_id, inputStream);
	}
}
