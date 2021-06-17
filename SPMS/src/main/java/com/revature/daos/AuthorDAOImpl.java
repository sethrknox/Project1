package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.beans.Author;
import com.revature.utils.JDBCConnection;

public class AuthorDAOImpl implements AuthorDAO {

	private Connection conn = JDBCConnection.getConnection();
	
	@Override
	public void addAuthor(Author a) {
		// TODO Auto-generated method stub
		String sql = "insert into project1.authors values (default, ?, ?, ?, ?, default) returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, a.getUsername());
			ps.setString(2, a.getPassword());
			ps.setString(3, a.getFirst());
			ps.setString(4, a.getLast());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				a.setId(rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Author getAuthor(String username, String password) {
		// TODO Auto-generated method stub
		String sql = "select * from project1.authors where username = ? and password = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Author a = new Author();
				a.setId(rs.getInt("id"));
				a.setUsername(username);
				a.setPassword(password);
				a.setFirst(rs.getString("first"));
				a.setLast(rs.getString("last"));
				a.setPoints(rs.getInt("points"));
				return a;
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
