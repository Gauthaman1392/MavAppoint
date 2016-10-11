<%@page import="uta.mav.appoint.beans.Advisor"%>
<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<div class="container">
	<div class="jumbotron">
		<form class="navbar-form navbar-right" role="search" action="search"
			method="post">
			<div class="form-group">
				<input type="text" name="query" class="form-control" placeholder="Search">
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
		<%@  page import="uta.mav.appoint.beans.Advisor"%>
		<%
			Advisor advisor = (Advisor) request.getAttribute("advisor");
			if (advisor.getName() == null) {
		%>
		<h3>Nothing found</h3>
		<%
			} else {
		%>
		<h5 id="name">
			Name:
			<%=advisor.getName()%></h5>
			<h5 id="email">
				Email:
				<%=advisor.getEmail()%></h5>
			<h5 id="role">
				Role:
				<%=advisor.getRole()%></h5>
			<%
				}
			%>
		
	</div>
</div>
<%@include file="templates/footer.jsp"%>
