package com.dx.springlearn.handlers.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.dx.springlearn.handlers.dao.UserDao;
import com.dx.springlearn.handlers.entity.User;
import com.dx.springlearn.handlers.utils.JDBCUtils;
@Repository
public class UserDaoImpl implements UserDao{
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        //注册用户
	@Override
	public void insert(User user) {
		// TODO Auto-generated method stub
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "insert into user (username,password,phone)values(?,?,?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, user.getUsername());
			st.setString(2, user.getPassword());
			st.setString(3, user.getPhone());			
		    st.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.colseResource(conn, st, null);
		}
	}
    
}
