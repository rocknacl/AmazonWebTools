package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;


public class DownloadSellerInformationDAO {
	private DownloadSellerInformationDAO(){
		
	}
	private static DownloadSellerInformationDAO onlyInstance;
	public static DownloadSellerInformationDAO getInstance(){
		if(onlyInstance==null){
			onlyInstance  = new DownloadSellerInformationDAO();
		}
		return onlyInstance;
	}
	
	public static void saveSellerNameAndID(Map<String, String> data, String category, String keyword,String country) throws SQLException {
		Connection conn = ConnectionPool.getConnectionPool().getConnection();
		String sql = "insert into contactseller_seller_feedback_stastics(seller_name,seller_id,category,keyword,is_fba,country) values(?,?,?,?,?,?) on duplicate key update is_fba = 1,category = concat(category,',',?),keyword=concat(keyword,',',?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		for (String name : data.keySet()) {
			pstmt.setString(1, name);
			pstmt.setString(2, data.get(name));
			pstmt.setString(3, category);
			pstmt.setString(4, keyword);
			pstmt.setInt(5, 1);
			pstmt.setString(6, country);
			pstmt.setString(7, category);
			pstmt.setString(8, keyword);
			pstmt.addBatch();
		}

		pstmt.executeBatch();
		conn.close();
	}
}
