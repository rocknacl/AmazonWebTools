package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.SendEmailDAO;
import models.Operation;

/**
 * Servlet implementation class SendEmailServlet
 */
@WebServlet("/SendEmailServlet")
public class SendEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendEmailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String operation = (String) request.getParameter("OP");
		if (operation.equals(Operation.GetAmazonAccount.toString())) {

			String[] account = SendEmailDAO.getSendEmailDAO().readAmazonAccount();
			if (account != null)
				response.getWriter().append(account[0] + " " + account[1]);

		}
		if (operation.equals(Operation.GetAmazonSellerID.toString())) {
			String country = (String) request.getParameter("Country");
			String taskName = (String) request.getParameter("TaskName");
			System.out.println(country + " || " + taskName);
			if (country == null || taskName == null)
				return;

			String sellerID = SendEmailDAO.getSendEmailDAO().readASellerIDToGSendMessages(country, taskName);
			response.getWriter().append(sellerID);

		}
		if (operation.equals(Operation.SaveSendEmailResult.toString())) {
			String email = (String) request.getParameter("AccountEmail");
			String sellerID = (String) request.getParameter("SellerID");
			String taskName = (String) request.getParameter("TaskName");
			String hostIP = (String) request.getRemoteAddr();

			SendEmailDAO.getSendEmailDAO().saveSendTime(sellerID, taskName, email, null, hostIP);
			response.getWriter().append("success");
		}
		if (operation.equals(Operation.SaveAccountBlockTime.toString())) {

			String email = (String) request.getParameter("AccountEmail");
			SendEmailDAO.getSendEmailDAO().saveAccountBlockTime(email);
			response.getWriter().append("success");
			return;
		}
		if (operation.equals(Operation.SaveAccountRegistered.toString())) {
			String email = (String) request.getParameter("email");
			String password = (String) request.getParameter("password");
			String name = (String) request.getParameter("name");
			String hostIP = (String) request.getRemoteAddr();
			String nation = (String) request.getParameter("nation");
			SendEmailDAO.getSendEmailDAO().saveRegisteredRobotAccount(email, password, nation, name, hostIP);
			response.getWriter().append("success");
			return;
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
