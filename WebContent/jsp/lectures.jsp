<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lectures.js"></script>
<% request.setAttribute(StringConstants.LOCATION, StringConstants.LECTURES_JSP); %>
<%@include file="nav_bar.jsp"%>
<% 
	Schedule schedule = course.getSchedule(); 
%>
<!--  the start tags here that are commented out are included in the nav_bar.jsp
<html>
<body>
	<table>
		<tr>
			<td> -->
				<% 
        		for (int i = 0; i<schedule.getTextbooks().size(); i++)
        		{
      	  			Textbook text = schedule.getTextbooks().get(i);
        		%>
				<p>
				<hr size="4" />
				</p> <b>Chapter references are from <%= text.getAuthor() %> <u><%= text.getTitle() %>
				</u>, <%= text.getPublisher() %>, <%= text.getYear() %>. ISBN <%= text.getIsbn() %></b>
				<%
				} 
				%>
				<p>
				<hr size="4" />
				<div>
          			<input id ="topic_search" type="text">
          			<input type="submit" id="search_button" value="Search Topics"  onclick="return highlight('topic_search', '<%= StringConstants.LECTURE %>')">
          			<input type="submit" id="clear_button" value="Clear Search" onclick="return clearHighlight('<%= StringConstants.LECTURE %>')"><br>
          			<p><hr size="4" /></p>
          			<input type="radio" id="<%= StringConstants.SHOW_LECTURES %>" name="display" onchange="return filter('<%= StringConstants.SHOW_LECTURES %>')" > Show Lectures
  		  			<input type="radio" id="<%= StringConstants.SHOW_DUE %>" name="display"  onchange="return filter('<%= StringConstants.SHOW_DUE %>')" > Show Due Dates
  		  			<input type="radio" id="<%= StringConstants.SHOW_ALL %>" name="display"  onchange="return filter('<%= StringConstants.SHOW_ALL %>')" checked > Show All<br>
          		</div>
          		<p>
				<hr size="4" />
				</p>
				</p> <b>Lectures</b>
				<table border="2" cellpadding="5" width="790">
					<tr>
						<th align="center">Week #</th>
						<th align="center">Lab</th>
						<th align="center">Lecture #</th>
						<th align="center">Day</th>
						<th align="center" width="100">Date</th>
						<th align="center">Lecture Topic</th>
						<th align="center">Chapter</th>
						<th>Program</th>
					</tr>
					<%
					int id = 0;
            		for (Week week : schedule.getWeeks())
            		{
            		%>
					<tr>
						<td class="week" title="<%=week.getLectures().size()%>,<%=week.getNumDueDates()%>" align="center" rowspan="<%= week.getRowSize() %>"><%= week.getWeek() %></td>
						<td class="lab" title="<%=week.getLectures().size()%>,<%=week.getNumDueDates()%>" align="center" rowspan="<%= week.getRowSize() %>">
						<%
              			for (Lab lab : week.getLabs())
              			{
              			%> 
              				<a href="<%= lab.getUrl() %>"><%= lab.getTitle() %></a>
							<hr /> 
						<%
						}
						for (int i = 0; i<week.getLectures().size(); i++){
							
							if (i != 0)
							{
						%>
					<tr >
							<%
							}
						
             				Lecture l1 = week.getLectures().get(i);   
            				%>
						<td class="lecture" align="center" rowspan="1"><%= l1.getNumber() %></td>
						<td class="lecture" align="center" rowspan="1"><%= l1.getDay() %></td>
						<td class="lecture" align="center" rowspan="1"><%= l1.getDate() %></td>
						<td class="lecture" align="center" rowspan="1">
							<% 
							for (Topic topic : l1.getTopics())
							{
	              			%> 
              				<a class = "<%= week.getWeek() %>,<%= l1.getNumber() %>,<%=topic.getNumber() %>" href="<%= topic.getUrl() %>"><%= topic.getTitle() %></a>
							<hr /> 
							<%
							} 
							%>
						</td>
						<td class="lecture" align="center" rowspan="1"><%= l1.getAllChapters() %></td>
						<td class="lecture" align="center" rowspan="1">
							<% 
							for (Program program : l1.getAllPrograms())
							{
	            	  			if (program.getFiles() != null){
	            	  				
	            	  				for (File file : program.getFiles()){
	              			%> 
              				<a href="<%= file.getUrl() %>"><%= file.getTitle() %></a><br />
								<%
									}
	            	  			} 
	            	  			%> 
            	  			<br /> 
	            	  		<%
	            	  		} 
	            	  		%>
						</td>
					</tr>
							<%
							List<GeneralAssignment> assigns = week.getMappedDueDates().get(l1.getNumber());
							if (assigns != null)
							{
								for (GeneralAssignment a : assigns)
								{
									String title = (a instanceof Assignment ? "ASSIGNMENT "+a.getNumber() : a.getTitle());
							%>
					<tr class="due_date">
						<td align="center" class="topic_col"></td>
						<td align="center"><%= l1.getDay() %></td>
						<td align="center"><%= a.getDueDate() %></td>
						<td align="center" colspan="3"><font size="+1" color="red"><b><%= title %> DUE</b></font></td>
					</tr>
					<%
								}
							}
						}
					}
					%>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
