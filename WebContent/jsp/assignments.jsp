<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% request.setAttribute(StringConstants.LOCATION, StringConstants.ASSIGNMENTS_JSP); %>
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
				<div>
					Final Project Deliverables -- Sort by: 
					<input type="radio" name="sort_dels" value="<%= StringConstants.DUE %>" checked onchange="return sort('<%= StringConstants.DELIVERABLES %>', 'sort_dels', 'direction_dels')"> Due Date
					<input type="radio" name="sort_dels" value="<%= StringConstants.TITLE %>" onchange="return sort('<%= StringConstants.DELIVERABLES %>', 'sort_dels', 'direction_dels')"> Title
					<input type="radio" name="sort_dels" value="<%= StringConstants.GRADE %>" onchange="return sort('<%= StringConstants.DELIVERABLES %>', 'sort_dels', 'direction_dels')"> Grade Percentage<br>
					<input type="radio" name="direction_dels" value="<%= StringConstants.ASCENDING %>" checked onchange="return sort('<%= StringConstants.DELIVERABLES %>', 'sort_dels', 'direction_dels')"> Ascending Order
					<input type="radio" name="direction_dels" value="<%= StringConstants.DESCENDING %>" onchange="return sort('<%= StringConstants.DELIVERABLES %>', 'sort_dels', 'direction_dels')"> Descending Order<br>
					<p><hr size="4" /></p>
					Assignments -- Sort by: 
					<input type="radio" name="sort_as" value="<%= StringConstants.DUE %>" checked onchange="return sort('<%= StringConstants.ASSIGNMENTS %>', 'sort_as', 'direction_as')"> Due Date
					<input type="radio" name="sort_as" value="<%= StringConstants.ASSIGNED %>" onchange="return sort('<%= StringConstants.ASSIGNMENTS %>', 'sort_as', 'direction_as')"> Assigned Date
					<input type="radio" name="sort_as" value="<%= StringConstants.GRADE %>" onchange="return sort('<%= StringConstants.ASSIGNMENTS %>', 'sort_as', 'direction_as')"> Grade Percentage<br>
					<input type="radio" name="direction_as" value="<%= StringConstants.ASCENDING %>" checked onchange="return sort('<%= StringConstants.ASSIGNMENTS %>', 'sort_as', 'direction_as')"> Ascending Order
					<input type="radio" name="direction_as" value="<%= StringConstants.DESCENDING %>" onchange="return sort('<%= StringConstants.ASSIGNMENTS %>', 'sort_as', 'direction_as')"> Descending Order<br>
	            </div>
				<p>
				<hr size="4" />
				</p>
				<b>Assignments</b>
				<table border="2" cellpadding="5" width="890">
					<tr>
						<th align="center">Homework #</th>
						<th align="center" width="100">Assigned</th>
						<th align="center" width="100">Due</th>
						<th>Assignment</th>
						<th>% Grade</th>
						<th>Grading Criteria</th>
						<th>Solution</th>
					</tr>
					<%
				    for (int i = 0; i<course.getAssignments().size(); i++)
				    {
				    	Assignment assignment = course.getAssignments().get(i);
					%>
					<tr id="a<%=i%>" title="<%=assignment.getNumber()%>">
						<td align="center"><%= assignment.getNumber() %></td>
						<td align="center"><%= assignment.getAssignedDate() %></td>
						<td align="center"><%= assignment.getDueDate() %></td>
						<td align="center">
						<% 
						if (assignment.getTitle() != null)
			            { 
			            %> 
			            <a href="<%= assignment.getUrl() %>"><%= assignment.getTitle() %></a>
						<hr /> 
						<%  	
			            } 
			            if (assignment.getFiles() != null)
			            { 
			            	for (File file : assignment.getFiles())
			            	{ 
			            %> 
			            <a href="<%= file.getUrl() %>"><%= file.getTitle() %></a>
						<hr /> 
						<%
			               	} 
			           	 }
			           	 %>
						</td>
						<td align="center"><%= assignment.getGradePercentage() %></td>
						<td align="center">
						<% 
						if (assignment.getGradingCriteriaFiles() != null)
						{ 
						 	for (File file : assignment.getGradingCriteriaFiles())
						 	{ 
						%> 	  
						<a href="<%= file.getUrl() %>"><%= file.getTitle() %></a>
						<hr /> 
						<%
							} 
						}
						%>
						</td>
						<td align="center">
						<% 
						if (assignment.getSolutionFiles() != null)
			            { 
			            	for (File file : assignment.getSolutionFiles())
			            	{ 
			           	%> 
			           <a href="<%= file.getUrl() %>"><%= file.getTitle() %></a>
						<hr /> 
						<%
							} 
			            }
			            %>
						</td>
					</tr>
					<%
					}
				    Assignment finalProject = course.getFinalProject();
				       
			       	if (finalProject != null)
			       	{ 
			   	    %>
					<tr id="fp">
						<td align="center"><%= finalProject.getNumber() %></td>
						<td align="center"><%= finalProject.getAssignedDate() %></td>
						<td align="center" colspan="3">
							<table border="1" cellpadding="5">
								<tr>
									<td colspan="3" align="center"><a
										href="<%= finalProject.getUrl() %>"><%= finalProject.getTitle() %></a>
										<hr /> 
						<% 
						for (File file : finalProject.getFiles()) 
						{
						%> 
				        				<a href="<%= file.getUrl() %>"><%= file.getTitle() %></a>
				
						<% 
						} 
						%>
									</td>
								</tr>
						<% 
						List<Deliverable> dels = finalProject.getDeliverables();
						for (int i = 0; i<dels.size(); i++)
				        { 
							Deliverable del = dels.get(i);
				        %>
								<tr id="d<%=i%>" title="<%= del.getNumber() %>">
									<td><%= del.getDueDate() %></td>
									<td><%= del.getTitle() %> 
							<% 
							if (del.getFiles() != null) 
				            {
				            %> 		<br /> 
				            	<% 
				            	for (File file : del.getFiles())
				                { 
				                %> 
				                	<a href="<%= file.getUrl() %>"><%= file.getTitle() %></a>
										<hr /> 
							<%
								} 
				            }
				            %>
				            		</td>
									<td><%= del.getGradePercentage() %></td>
								</tr>
						<%
						} 
						%>
							</table>
						</td>
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
