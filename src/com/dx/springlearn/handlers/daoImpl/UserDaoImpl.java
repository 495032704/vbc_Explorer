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
		private User user = new User();
		private String status = "0";
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
    
    //注册用户
	@Override
	public void insert(User user) {
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "insert into user (phoneNumber,password,inviteCode,registerTime,inviter)values(?,?,?,?,?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, user.getPhoneNumber());
			st.setString(2, user.getPassword());
			st.setString(3, user.getInviteCode());
			st.setString(4, user.getRegisterTime());
			st.setString(5, user.getInviter());
		    st.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.colseResource(conn, st, null);
		}
	}
	
	//查询手机号
	@Override
	public String findPhone(String pnone) {
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "select user_id from user where phoneNumber = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, pnone);
		    rs = st.executeQuery();
		    while(rs.next()){
		    	status = "1";
		    }
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.colseResource(conn, st, rs);
		}
		return status;
	}
	
	//登录验证
	public String findUserIsNull(String phoneNumber, String password) {
		int id=0;
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "select user_id from user where phoneNumber = ? and password = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, phoneNumber);
			st.setString(2, password);
		    rs = st.executeQuery();
		    while(rs.next()){
		    	id = rs.getInt(1);
		    }
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.colseResource(conn, st, rs);
		}
		if(id==0) {
			return "0";
		}
		return "1";
	}
	
	//查询邀请码
	@Override
	public String findInvite(String invite) {
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "select user_id from user where inviteCode = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, invite);
		    rs = st.executeQuery();
		    while(rs.next()){
		    	status = "1";
		    }
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.colseResource(conn, st, rs);
		}
		return status;
	}

	//查询密码
	@Override
	public String findPassByPhone(String phoneNumber) {
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "select password from user where phoneNumber = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, phoneNumber);
		    rs = st.executeQuery();
		    while(rs.next()){
		    	status = rs.getString("password");
		    }
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.colseResource(conn, st, rs);
		}
		return status;
	}

	//查询用户所有信息
	@Override
	public User findUserinformation(String phoneNumber) {
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "select user_id, phoneNumber, password, gender, inviteCode, statusCode, registerTime, "
					+ "lastLoginTime, realName, idCard, level, inviter from user where phoneNumber = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, phoneNumber);
		    rs = st.executeQuery();
		    while (rs.next()) {
		    	user.setUserId(rs.getInt("user_id"));
		    	user.setPhoneNumber(rs.getString("phoneNumber"));
		    	user.setPassword(rs.getString("password"));
		    	user.setGender(rs.getString("gender"));
		    	user.setInviteCode(rs.getString("inviteCode"));
		    	user.setStatusCode(rs.getString("statusCode"));
		    	user.setRegisterTime(rs.getString("registerTime"));
		    	user.setLastLoginTime(rs.getString("lastLoginTime"));
		    	user.setRealName(rs.getString("realName"));
		    	user.setIdCard(rs.getString("idCard"));
		    	user.setLevel(rs.getString("level"));
		    	user.setInviter(rs.getString("inviter"));
		    }
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.colseResource(conn, st, rs);
		}
		return user;
	}

	//修改手机号
	@Override
	public String updatePhone(String newPhone, Integer id) {
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "update user set phoneNumber = ? where user_id = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, newPhone);
			st.setInt(2, id);
		    st.executeUpdate();
		    status = "1";
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.colseResource(conn, st, null);
		}
		return status;
	}

	//修改登录密码
	@Override
	public String updatePassword(String newPassword, Integer id) {
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "update user set password = ? where user_id = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, newPassword);
			st.setInt(2, id);
		    st.executeUpdate();
		    status = "1";
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.colseResource(conn, st, null);
		}
		return status;
	}

	//修改支付密码
	@Override
	public String updatePayPass(String newPayPass, Integer id) {
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "update fund set newPayPass = ? where user_id = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, newPayPass);
			st.setInt(2, id);
		    st.executeUpdate();
		    status = "1";
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.colseResource(conn, st, null);
		}
		return status;
	}

	//认证|修改真实身份
	@Override
	public String updateRealId(String realName, String idCard, Integer id) {
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "update user set realName = ?, idCard = ? where user_id = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, realName);
			st.setString(2, idCard);
			st.setInt(3, id);
		    int executeUpdate = st.executeUpdate();
		    if(executeUpdate==1) {
		    	status = "1";
		    }
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.colseResource(conn, st, null);
		}
		return status;
	}

}
