package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import dao.SellerFeedbackDao;
import models.SellerIDQueue;

/**
 * Servlet implementation class SellerFeedbackServlet
 */
@WebServlet("/SellerFeedbackServlet")
public class SellerFeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SellerFeedbackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String country = request.getParameter("country");
		List<String> sellerIDs = SellerIDQueue.getSellerIDs(country, 50);
		JSONArray array = new JSONArray(sellerIDs);
		response.getWriter().print(array.toString());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonStr = request.getParameter("sellerFeedbackInformation");
		try {
			JSONArray jarray = new JSONArray(jsonStr);
			SellerFeedbackDao.saveSellerFeedback(jarray);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
