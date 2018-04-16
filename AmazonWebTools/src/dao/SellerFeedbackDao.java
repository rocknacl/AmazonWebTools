package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import models.SellerFeedbackInformation;

public class SellerFeedbackDao {

	public static List<String> getSellerIDs(String country, int limit) {
		List<String> sellerIds = new ArrayList<String>();
		Connection conn = null;
		String query = "select seller_id from contactseller_seller_feedback_stastics where positive_month_1 is null  and country = '"
				+ country + "' limit " + limit;
		try {
			conn = ConnectionPool.getConnectionPool().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				sellerIds.add(rs.getString("seller_id"));
			}
			return sellerIds;

		} catch (SQLException e) {
			e.printStackTrace();
			return sellerIds;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void saveSellerFeedback(List<SellerFeedbackInformation> infos) {
		Connection conn = null;
		try {
			conn = ConnectionPool.getConnectionPool().getConnection();
			String sql = "update contactseller_seller_feedback_stastics set positive_month_1 = ?, positive_month_3= ?, positive_year_1= ?, positive_lifetime= ?, neutral_month_1= ?, neutral_month_3= ?, neutral_year_1= ?, neutral_lifetime= ?, negative_month_1= ?, negative_month_3= ?, negative_year_1= ?, negative_lifetime= ?, count_month_1= ?, count_month_3= ?, count_year_1= ?, count_lifetime= ? where seller_id = ? and country = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (SellerFeedbackInformation sfi : infos) {
				pstmt.setString(1, sfi.getPositive_month_1());
				pstmt.setString(2, sfi.getPositive_month_3());
				pstmt.setString(3, sfi.getPositive_year_1());
				pstmt.setString(4, sfi.getPositive_lifetime());

				pstmt.setString(5, sfi.getNeutral_month_1());
				pstmt.setString(6, sfi.getNeutral_month_3());
				pstmt.setString(7, sfi.getNeutral_year_1());
				pstmt.setString(8, sfi.getNeutral_lifetime());

				pstmt.setString(9, sfi.getNegative_month_1());
				pstmt.setString(10, sfi.getNegative_month_3());
				pstmt.setString(11, sfi.getNegative_year_1());
				pstmt.setString(12, sfi.getNegative_lifetime());

				pstmt.setInt(13, sfi.getCount_month_1());
				pstmt.setInt(14, sfi.getCount_month_3());
				pstmt.setInt(15, sfi.getCount_year_1());
				pstmt.setInt(16, sfi.getCount_lifetime());

				pstmt.setString(17, sfi.getSellerID());
				pstmt.setString(18, sfi.getCountry());

				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void saveSellerFeedback(JSONArray array) {
		Connection conn = null;
		try {
			conn = ConnectionPool.getConnectionPool().getConnection();
			String sql = "update contactseller_seller_feedback_stastics set positive_month_1 = ?, positive_month_3= ?, positive_year_1= ?, positive_lifetime= ?, neutral_month_1= ?, neutral_month_3= ?, neutral_year_1= ?, neutral_lifetime= ?, negative_month_1= ?, negative_month_3= ?, negative_year_1= ?, negative_lifetime= ?, count_month_1= ?, count_month_3= ?, count_year_1= ?, count_lifetime= ? where seller_id = ? and country = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);

				pstmt.setString(1, obj.getString("positive_month_1"));
				pstmt.setString(2, obj.getString("positive_month_3"));
				pstmt.setString(3, obj.getString("positive_year_1"));
				pstmt.setString(4, obj.getString("positive_lifetime"));

				pstmt.setString(5, obj.getString("neutral_month_1"));
				pstmt.setString(6, obj.getString("neutral_month_3"));
				pstmt.setString(7, obj.getString("neutral_year_1"));
				pstmt.setString(8, obj.getString("neutral_lifetime"));

				pstmt.setString(9, obj.getString("negative_month_1"));
				pstmt.setString(10, obj.getString("negative_month_3"));
				pstmt.setString(11, obj.getString("negative_year_1"));
				pstmt.setString(12, obj.getString("negative_lifetime"));

				pstmt.setInt(13, obj.getInt("count_month_1"));
				pstmt.setInt(14, obj.getInt("count_month_3"));
				pstmt.setInt(15, obj.getInt("count_year_1"));
				pstmt.setInt(16, obj.getInt("count_lifetime"));

				pstmt.setString(17, obj.getString("sellerID"));
				pstmt.setString(18, obj.getString("country"));

				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
