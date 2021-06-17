package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.beans.Editor;
import com.revature.utils.JDBCConnection;

public class EditorDAOImpl implements EditorDAO {

	private Connection conn = JDBCConnection.getConnection();
	
	@Override
	public void addEditor(Editor e) {
		// TODO Auto-generated method stub
		String sql = "insert into project1.editors values (default, ?, ?, ?, ?, ?) returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, e.getUsername());
			ps.setString(2, e.getPassword());
			ps.setString(3, e.getFirst());
			ps.setString(4, e.getLast());
			ps.setString(5, e.getType());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				e.setId(rs.getInt("id"));
			}
		} catch (SQLException x) {
			// TODO Auto-generated catch block
			x.printStackTrace();
		}
	}
	
	@Override
	public Editor getEditor(String username, String password) {
		// TODO Auto-generated method stub
		String sql = "select * from project1.editors where username = ? and password = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Editor e = new Editor();
				e.setId(rs.getInt("id"));
				e.setUsername(username);
				e.setPassword(password);
				e.setFirst(rs.getString("first"));
				e.setLast(rs.getString("last"));
				e.setType(rs.getString("type"));
				return e;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean usernameExists(String username) {
		// TODO Auto-generated method stub
		String sql = "(select username from project1.authors) union (select username from project1.editors)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				if (rs.getString("username").equals(username)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
