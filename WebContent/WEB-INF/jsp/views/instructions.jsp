<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<div class="container">
	<div class="jumbotron">
	<h2>MavAppoint Intructions</h2>	 
	<p>
	<p>The application helps the users to add and manage appointments<br>
	  Users can provide feedback by clicking on the feedback link<br>
	  Users can report bug  by clicking on the report bugs link<br>
      The depatment link will redirect the user to the department list<br>
      Mymav link will redirect the users to UTA mymav<br>
      Student can book an appointment using Advising links<br>
	</p>
	</p>
	<button type="button" class="btn btn-secondary" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);">close</button>
	</div>
</div>
<%@include file="templates/footer.jsp" %>