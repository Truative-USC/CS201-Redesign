<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="parsing.StringConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% 
	if ((session.getId() == null) || (session.getAttribute(StringConstants.DATA) == null)){
		request.getRequestDispatcher("index.jsp").forward(request, response);
		return;
	}


	
	DataContainer data = (DataContainer) session.getAttribute(StringConstants.DATA);
	request.setAttribute("data", data);
	request.getSession().setAttribute("data", data);
	School school = data.getSchools().get(0);
	Department dept = school.getDepartments().get(0);
	Course course = (Course) session.getAttribute(StringConstants.COURSE);
	
	String location = (String) request.getAttribute(StringConstants.LOCATION);
	String [] hrefs = {"http://cs.usc.edu", StringConstants.HOME_JSP, course.getSyllabus().getUrl(), 
			StringConstants.LECTURES_JSP, StringConstants.ASSIGNMENTS_JSP, StringConstants.EXAMS_JSP};
	String [] labels = {dept.getPrefix()+" Department", dept.getPrefix()+" "+course.getNumber()+" Home", 
			"Syllabus", "Lectures", "Assignments", "Previous Exams"};
	String design = (String) session.getAttribute(StringConstants.DESIGN);
	String css = design.equals(StringConstants.DESIGN_1) ? "/css/design1.css" : "/css/design2.css";
%>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}<%=css%>" />
	<title><%= school.getName() %>: <%=dept.getPrefix() %> <%= course.getNumber() %> <%= course.getTerm() %> <%= course.getYear() %></title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
<% if (location.equals(StringConstants.ASSIGNMENTS_JSP)) { %>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/assignments.js"></script>
<body onload="return populateMaps(<%= course.getFinalProject().getDeliverables().size() %>, <%= course.getAssignments().size() %>)">

<%} else{ %>

<body>

<% } %>
	<table cellpadding="10" width="800" id="top_table">
		<tr>
			<!-- column for left side of page -->
			<td width="180" valign="top" align="right">
				<a href="http://www.usc.edu"><img src=<%= school.getImage() %> /></a><br />
				<br /> 
				<% 
				for (int i = 0; i<6; i++)
				{
	        		if (hrefs[i].equals(location))
	        		{
	        	%> 
        		<font size="+1"><%=labels[i]%></font><br />
				<br /> 
					<%
					} 
	        		else
	        		{ 
	        		%> 
	        	<font size="+1"><a href="<%= hrefs[i]%>"> <%= labels[i] %></a></font><br />
				<br /> 
				<%
					}
	        	} 
	        	%>
	        </td>
			<!-- center column to separate other two columns -->
			<td width="5"><br /></td>
			<!-- large column in center of page with dominant content -->
			<td width="615"><br />
				<p>
					<b><font size="+3"><%= dept.getPrefix() %> <%=course.getNumber() %></font></b><br />
					<b><i><font size="+1"><%= course.getTitle() %> - <%=course.getUnits() %> units</font></i></b><br /> 
					<b><i><font size="+1"><%= course.getTerm() %> <%= course.getYear() %></font></i></b><br />