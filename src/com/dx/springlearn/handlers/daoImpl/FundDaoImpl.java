package com.dx.springlearn.handlers.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.dx.springlearn.handlers.dao.FundDao;
import com.dx.springlearn.handlers.utils.JDBCUtils;

@Repository
public class FundDaoImpl implements FundDao {

	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;

	// 根据user_id查询支付密码
	@Override
	public String findPayPass(Integer id) {
		String payPass = "0";
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "select payPass from fund where user_id = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			while (rs.next()) {
				payPass = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.colseResource(conn, st, rs);
		}
		return payPass;
	}

	@Override
	public String account(Integer id, String bankCard) {
		String status = "0";
		try {
			Connection conn = JDBCUtils.getConnection();
			String sql = "insert into fund (user_id, bankCard) values (?,?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			st.setString(2, bankCard);
			st.executeQuery();
			status = "1";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.colseResource(conn, st, null);
		}
		return status;
	}

}
