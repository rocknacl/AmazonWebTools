package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SendEmailDAO {
	private SendEmailDAO() {

	}

	private static SendEmailDAO onlyInstance;

	public static SendEmailDAO getSendEmailDAO() {
		if (onlyInstance == null) {
			onlyInstance = new SendEmailDAO();
		}
		return onlyInstance;
	}

	public synchronized String readASellerIDToGSendMessages(String taskCountry, String taskName) {
		Connection conn = null;
		String id = null;
		try {
			conn = ConnectionPool.getConnectionPool().getConnection();
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			// ResultSet rs = stmt.executeQuery("select s.seller_id from
			// contactseller_seller_feedback_stastics s where s.count_lifetime
			// between 1 and 1000 and s.country = '"
			// + task.getCountry().toString() + "' and s.send_assigned is null
			// and not exists (select 1 from email_send_record r where
			// r.seller_id= s.seller_id and r.task_name='"+task.getName()+"')
			// limit 1");

			ResultSet rs = stmt.executeQuery(
					"select s.seller_id from contactseller_seller_feedback_stastics s where s.count_year_1> 100 and positive_year_1_value>94  and s.country = '"
							+ taskCountry + "' and " + taskName + " is null order by count_lifetime desc limit 1");
			while (rs.next()) {
				id = rs.getString(1);
				System.out.println(rs.getString(1));
				Statement stmt1 = conn.createStatement();
				// stmt1.execute("update contactseller_seller_feedback_stastics
				// set send_assigned = 1 where seller_id = '"
				// + id+"'");

				stmt1.execute("update contactseller_seller_feedback_stastics set " + taskName
						+ " = 'assigned' where seller_id = '" + id + "'");
			}
			conn.commit();
			conn.setAutoCommit(true);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}

	}

	public void saveSendTime(String id, String taskName, String accountEmail, String hostName, String hostIP) {
		Connection conn = null;
		try {
			conn = ConnectionPool.getConnectionPool().getConnection();
			conn.setAutoCommit(false);
			// Connection conn =
			// ConnectionPool.getConnectionPool().getConnection();
			// PreparedStatement pstmt = conn.prepareStatement(
			// "update contactseller_seller_feedback_stastics set send_assigned
			// =
			// null where seller_id=?");

			PreparedStatement pstmt = conn.prepareStatement(
					"update contactseller_seller_feedback_stastics set " + taskName + " = 'Sent' where seller_id=?");
			pstmt.setString(1, id);
			pstmt.addBatch();
			PreparedStatement pstmt2 = conn.prepareStatement(
					"insert into email_send_record(account_email,seller_id,send_time,task_name,hostname,hostip) values(?,?,now(),?,?,?)");
			pstmt2.setString(1, accountEmail);
			pstmt2.setString(2, id);
			pstmt2.setString(3, taskName);
			pstmt2.setString(4, hostName);
			pstmt2.setString(5, hostIP);
			pstmt2.addBatch();

			pstmt.executeBatch();
			pstmt2.executeBatch();

			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}

	public void saveAccountBlockTime(String email) {
		Connection conn = null;
		try {
			conn = ConnectionPool.getConnectionPool().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"update contactseller_robot_account set block_time = now(),send_assigned = null where email=?");
			pstmt.setString(1, email);
			pstmt.addBatch();
			pstmt.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized String[] readAmazonAccount() {
		String[] result = null;
		Connection conn = null;
		try {
			conn = ConnectionPool.getConnectionPool().getConnection();
			String sql = "select email,password from contactseller_robot_account where (block_time is null or datediff(now(),block_time)>1) and send_assigned is null limit 1";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				result = new String[] { rs.getString("email"), rs.getString("password") };
				stmt.execute("update contactseller_robot_account set send_assigned = 1 where email = '"
						+ rs.getString("email") + "'");
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void saveRegisteredRobotAccount(String email, String password, String nation, String name, String hostIp) {
		Connection conn = null;
		try {
			conn = ConnectionPool.getConnectionPool().getConnection();
			String sql = "insert into robot_account(email,password,host_ip,nation) values(?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			pstmt.setString(3, hostIp);
			pstmt.setString(4, nation);
			pstmt.addBatch();
			pstmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
