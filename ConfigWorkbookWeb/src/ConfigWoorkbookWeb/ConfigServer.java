package ConfigWoorkbookWeb;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ConfigServer
 */
@WebServlet("/ConfigServer")
public class ConfigServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfigServer() {
        super();
        // TODO Auto-generated constructor stub
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//request.setAttribute("status", "10");
		
		getServletContext().getRequestDispatcher("/NewFile.jsp").forward(request, response);
		//response.sendRedirect("NewFile.jsp");
		
		System.out.println(request.getParameter("username"));
		System.out.println(request.getParameter("password"));
		System.out.println(request.getParameter("endpoint"));
		//ConfigBookMain.main(new String[]{"saurabbh.gupta@gmail.com","salesforce4","https://login.salesforce.com/services/Soap/u/30.0"});
		//sampletry obj= new sampletry();
		//obj.fun();
		//request.setAttribute("status", "20");
		//response.sendRedirect("NewFile.jsp");
		//getServletContext().getRequestDispatcher("/NewFile.jsp").forward(request, response);
	}

}
