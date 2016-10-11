<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<div class="container">
	<form class="navbar-form navbar-right" role="search" action="search" method="post">
		<div class="form-group">
			<input type="text" name="query" class="form-control" placeholder="Search">
		</div>
		<button type="submit" class="btn btn-default">Submit</button>
	</form>
	<div class="jumbotron">
		<h1>Maverick Advising</h1>
	</div>
</div>

<%@include file="templates/footer.jsp"%>