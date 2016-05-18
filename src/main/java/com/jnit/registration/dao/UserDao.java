package com.jnit.registration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.jnit.registration.controller.advice.ResourceNotFoundException;
import com.jnit.registration.model.User;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		createTable();
	}

	private void createTable() {
		final String query = "create table if not exists  users(firstName text, lastName text, age integer, id integer primary key autoincrement)";
		jdbcTemplate.update(query);
	}

	public User persistUser(final User user) throws Exception {
		final String query = "insert into users(firstName, lastName, age) values(?,?,?);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getFirstName());
				ps.setString(2, user.getLastName());
				ps.setInt(3, user.getAge());

				return ps;
			}
		}, keyHolder);
		user.setId(keyHolder.getKey().longValue());
		return user;
	}

	public List<User> loadAllUsers() throws Exception {

		List<User> users = new ArrayList<User>();
		String query = "select * from users";
		users = jdbcTemplate.query(query, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setAge(rs.getInt("age"));
				return user;
			}
		});

		return users;
	}

	public User updateUser(User user) throws Exception {
		String query = "update users set firstName=?,lastName=?,age=? where id=?";
		int numRows = jdbcTemplate.update(query,
				new Object[] { user.getFirstName(), user.getLastName(), user.getAge(), user.getId() });
		if (numRows == 0) {
			throw new ResourceNotFoundException("User with id " + user.getId() + " not found");
		}
		return user;
	}

	public int deleteUser(Long id) throws Exception {
		String query = "delete from users where id=?";
		int numRows = jdbcTemplate.update(query, new Object[] { id });
		if (numRows == 0) {
			throw new ResourceNotFoundException("User with id " + id + " not found");
		}
		return numRows;

	}

}
