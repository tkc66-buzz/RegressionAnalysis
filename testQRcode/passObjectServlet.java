package testQRcode;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class passObjectServlet
 */
public class passObjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<User> tokenCart = new ArrayList<User>();

		User user = new User();
		user.setEmail("no.fef.1466.tkc@ezweb.ne.jp");
		user.setToken("20");
		user.setPoint("10");

		request.setAttribute("user", user);
		request.getRequestDispatcher("/qrCode.jsp").forward(request, response);
	}

}
