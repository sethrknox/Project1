package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.beans.Author;
import com.revature.beans.Editor;
import com.revature.beans.SPForm;
import com.revature.utils.JDBCConnection;

public class SPFormDAOImpl implements SPFormDAO {

	private Connection conn = JDBCConnection.getConnection();
	
	@Override
	public void addForm(SPForm spf) {
		// TODO Auto-generated method stub
		String sql = "insert into project1.spforms values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
				"?, default, default, default, ?, default"+
				", ?, default, ?, default, default, default) returning *";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, spf.getAuthor_first());
			ps.setString(2, spf.getAuthor_last());
			ps.setInt(3, spf.getAuthor_id());
			ps.setString(4, spf.getTitle());
			ps.setDate(5, spf.getEnd_date());
			ps.setString(6, spf.getStory_type().toLowerCase());
			ps.setString(7, spf.getGenre().toLowerCase());
			ps.setString(8, spf.getTag_line());
			ps.setString(9, spf.getDescription());
			ps.setString(10, spf.getDraft());
			
			ps.setInt(11, spf.getAe_id());
			ps.setInt(12, spf.getGe_id());
			ps.setInt(13, spf.getSe_id());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				spf.setId(rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public SPForm getForm(Integer id) {
		// TODO Auto-generated method stub
		String sql = "select * from ((project1.spforms spf left join project1.genres g on spf.genre = g.id)"+
					" left join project1.stories s on spf.story_type = s.id) where id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				SPForm spf = new SPForm();
				spf.setAuthor_id(rs.getInt("id"));
				spf.setAuthor_first(rs.getString("first"));
				spf.setAuthor_last(rs.getString("last"));
				spf.setTitle(rs.getString("title"));
				spf.setEnd_date(rs.getDate("end_date"));
				spf.setStory_type(rs.getString("type"));
				spf.setGenre(rs.getString("name"));
				spf.setTag_line(rs.getString("tag_line"));
				spf.setDescription(rs.getString("description"));
				spf.setDraft(rs.getString("draft"));
				spf.setStatus(rs.getString("status"));
				spf.setSubmit_date(rs.getDate("submit_date"));
				spf.setPriority(rs.getString("priority"));
				spf.setAe_id(rs.getInt("ae_id"));
				spf.setAe_approval(rs.getString("ae_approval"));
				spf.setGe_id(rs.getInt("ge_id"));
				spf.setGe_approval(rs.getString("ge_approval"));
				spf.setSe_id(rs.getInt("se_id"));
				spf.setSe_approval(rs.getString("se_approval"));
				spf.setDenial_reason(rs.getString("denial_reason"));
				spf.setSe_edit(rs.getBoolean("se_edit"));
				return spf;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
