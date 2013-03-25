
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.cmu.model.Skill"%>

<!DOCTYPE html>
<html>
<head>
<title>Friends Skills Finder</title>
</head>
<body>

	<h2>Friends Skills Details</h2>

	<ul>
		<li></li>
		<%
			List<Skill> skills = (ArrayList<Skill>) request.getAttribute("skills");
			for (int i = 0; i < skills.size(); i++) {
		%>

		<li><%=skills.get(i).getName() + " - "
						+ skills.get(i).getCount()%></li>
		<%
			}
		%>
	</ul>

	<form method="get" action="delete.do">
		<input type="submit" name="button" value="Logout" />
	</form>

</body>
</html>