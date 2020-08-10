package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import objects.DataContainer;
import objects.Exam;
import objects.GeneralObject;
import parsing.Parser;
import parsing.StringConstants;
import parsing.connect;
import parsing.fillData;

/**
 * Servlet implementation class ChooseFile
 */
@WebServlet("/ChooseFile")
@MultipartConfig
public class ChooseFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		String ipAddress = request.getParameter("ipaddress");
		String ssl = request.getParameter("ssl");
		System.out.println("Ssl box" + ssl);
		 DataContainer newData = null;

		if(ssl == null) {
			ssl = "false";
		}
		else {
			ssl = "true";
		}
		 connect con = new connect(userName, passWord, ipAddress, ssl);
		 Connection conn = con.getConnection();
		 java.sql.Statement rsStatement = null;
		try {
			rsStatement = conn.createStatement();
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		 ResultSet rs;
		 boolean empty = true;

		try {
			rs = rsStatement.executeQuery("select * from assignments");
			while(rs.next()) {
				 empty = false;
		} 
		}catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		 
		 if(empty) {
			 Part filePart = request.getPart(StringConstants.FILE); 
			 InputStreamReader fileContent = new InputStreamReader(filePart.getInputStream());
			 BufferedReader br = new BufferedReader(fileContent);
			 DataContainer data = new Parser(br).getData();
			 System.out.println(userName);
			 System.out.println(passWord);
			 System.out.println(ipAddress);
			 br.close();
			 fileContent.close();
			 data.organize();
			 try {
		 connect.writeJavaObject(data);
			 System.out.println("i came  into here");
			} catch (Exception e) {
				// TODO Auto-generated catch block

			}
			 fillData fill = new fillData();
			 newData = fill.fillObjects();
			 newData.organize();
		 }
		 else {
			 fillData fill = new fillData();
			  newData = fill.fillObjects();
			 newData.organize();
		 }
		 
		

		    
		 

			
		 
		 request.getSession().setAttribute(StringConstants.DESIGN, (String)request.getParameter(StringConstants.DESIGN));
		 request.getSession().setAttribute(StringConstants.DATA, newData);
		 request.getSession().setAttribute(StringConstants.COURSE, newData.getSchools().get(0).getDepartments().get(0).getCourses().get(0));
		 request.getSession().setMaxInactiveInterval(600);

		 		 response.sendRedirect("jsp/"+StringConstants.HOME_JSP);
	}

}
