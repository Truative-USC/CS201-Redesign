<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<% request.setAttribute(StringConstants.LOCATION, StringConstants.EXAMS_JSP); %>
<%@include file="nav_bar.jsp"%>
<!--  the start tags here that are commented out are included in the nav_bar.jsp
<html>
<body>
	<table>
		<tr>
			<td> -->
				<p>
				<hr size="4" />
				</p>
				<table border="2" cellpadding="5" width="590">
					<tr>
						<th align="center">Semester</th>
						<th align="center">Written Exam #1</th>
						<th align="center">Programming Exam #1</th>
						<th>Written Exam #2</th>
						<th>Programming Exam #2</th>
					</tr>
				<%
            	for (Exam exam : course.getExams())
            	{
            	%>
            		<tr>
            			<td align="center"><%= exam.getSemester() %> <%= exam.getYear() %></td>
            		<%
            		for (Test test : exam.getOrderedTests())
            		{
            		%>
            			<td align="center">
            			<%
            			if (test != null)
            			{
							for (File file : test.getFiles())
							{ 
							%> 
							<a href="<%= file.getUrl() %>"><%= file.getTitle() %></a>
							<hr /> 
							<%
							}
            			}
						%>
						</td>
					<% 
					}
					%>
					</tr>
				<%
				} 
				%>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
