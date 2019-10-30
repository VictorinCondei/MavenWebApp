package net.javaMavenWebServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.tools.*;

/**
 * Servlet implementation class OkdPatternServlet
 */
public class OkdPatternServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OkdPatternServlet() {
        //super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		processRequest(request,response,1);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		processRequest(request,response,2);
	}
	protected void processRequest(HttpServletRequest request, HttpServletResponse response, int act) throws ServletException, IOException {
		String yourName = request.getParameter("yourName");
		PrintWriter writer = response.getWriter();
		SQLDBTools st=new SQLDBTools();
		if (st.SQLDBConnect() != null) {
			writer.println("<h3>DB Connection OK </h3>");
			if (act == 1) {
				yourName +="1: "+st.GetFromDB();
			}else {
				yourName +="2: "+st.PostToDB(yourName);
			}
		} else {
			writer.println("<h2>DB Connection ERROR "+st.GetState()+" </h2>");
		}
		
		writer.println("<h3>PatternServlet " + yourName + "</h3>");
                writer.println("<p><a href = './index.jsp'>Home ...</a>");
		writer.close();
	}

}
