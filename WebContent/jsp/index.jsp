<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="parsing.StringConstants"%>
<html>
<body>
	<form action="${pageContext.request.contextPath}/ChooseFile"
		method="post" enctype="multipart/form-data">
		
		<div>
			SQL USERNAME <input type="text" name="username">
		</div>
		<div>
			SQL PASSWORD <input type="text" name="password">
		</div>
		<div>
			SQL IP ADDRESS <input type="text" name=ipaddress>
		</div>
		<div>
			Choose SSL or not <input type="checkbox" name="ssl">
		</div>
		<div>
			<label>Please choose a JSON file</label> <br /> <input accept=".json"
				type="file" id="file" name="file">
		</div>
		
		<div>
			<input type="submit" value="Upload File" />
		</div>
		<div>
			<input type="radio" name="<%= StringConstants.DESIGN %>" value="<%= StringConstants.DESIGN_1 %>" checked> Design 1<br>
  			<input type="radio" name="<%= StringConstants.DESIGN %>" value="<%= StringConstants.DESIGN_2 %>"> Design 2<br>
 		</div>
	</form>
</body>
</html>