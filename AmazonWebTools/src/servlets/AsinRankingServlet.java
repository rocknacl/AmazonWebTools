package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AsinRankingDAO;
import models.Operation;

/**
 * Servlet implementation class AsinRankingServlet
 */
@WebServlet("/AsinRankingServlet")
public class AsinRankingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AsinRankingServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String operation = (String) request.getParameter("OP");
		System.out.println(operation);
		if (operation.equals(Operation.GetSEOAsin.toString())) {
			try {
				ArrayList<String> asinCountry = AsinRankingDAO.readASINFromDB();
				String result = null;
				for (String s : asinCountry) {
					if (result == null) {
						result = s;
					} else {
						result = result + "\t" + s;
					}
				}
				response.getWriter().append(result);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (operation.equals(Operation.SaveAsinRanking.toString())) {
			String asin = request.getParameter("asin");
			String rank = request.getParameter("rank");
			System.out.println(asin+":"+rank);
			try {
				String result = AsinRankingDAO.saveRankingInformation(asin, rank);
				response.getWriter().append(result);
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().append(e.getMessage());
			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
