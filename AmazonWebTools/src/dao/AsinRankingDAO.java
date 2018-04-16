package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AsinRankingDAO {
	final static String url = "jdbc:mysql://112.74.206.141:3306/oss_stone?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false";
	final static String usr = "stone";
	final static String password = "stone";
	public static ArrayList<String> readASINFromDB() throws SQLException {
		Connection conn = null;
		ArrayList<String> tuples = new ArrayList<String>();
		try {
//			conn = ConnectionPool.getConnectionPool().getConnection(url,usr,password);
			conn = ConnectionPool.getConnectionPool().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(
					"SELECT distinct c.asin,k.Country FROM oss_stone.seo_pool_keyword k,oss_stone.commodity c where k.Status  in ('Normal','Searching') and k.Merchant_SKU = c.Merchant_SKU and not exists (select * from oss_stone.asin_ranking r where r.asin = c.asin and datediff(now(),r.insert_date)<1)");
			while (result.next()) {
				String asin = result.getString("asin");
				String country = result.getString("Country");
				String tuple = asin + "," + country;
				tuples.add(tuple);
			}
			return tuples;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (conn != null) {
				conn.close();
			}

		}
	}

	public static String saveRankingInformation(String asin, String rank) throws SQLException {
		Connection conn = null;
		try {
			conn = ConnectionPool.getConnectionPool().getConnection(url,usr,password);
			PreparedStatement stmt = conn
					.prepareStatement("replace into oss_stone.asin_ranking(insert_date,asin,rank_information) values(now(),?,?)");
			stmt.setString(1, asin);
			stmt.setString(2, rank);
			stmt.addBatch();
			stmt.executeBatch();
			return "success";
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

}
