<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<div class="container">
	<div class="jumbotron">
	<label><font color="#e67e22" size="5"> Add New Advisor </label>
			
	<form action="create_advisor" method="post" name="advisor_form" onsubmit="return false;">
		<div class="form-group">
		<label for="utaID"><font color="#e67e22" size="4">UTA ID</label><br>
		 			<input type="text" style="width:350px;" class="form-control" id="utaID"
		 				placeholder="100xxxxxxx">
					<label for="emailAddress"><font color="#e67e22" size="4">Email Address</label><br>
		 			<input type="text" style="width:350px;" class="form-control" id="emailAddress"
		 				placeholder="advisor@uta.edu">
					<label for="pname"><font color="#e67e22" size="4">Display Name</label><br>
		 			<input type="text" style="width:350px;" class="form-control" id="pname"
		 				placeholder="Dr. Advisor">			
					<label for="password"><font color="#e67e22" size="4">Password</label><br>
		 			<input type="password" style="width:350px;" class="form-control" id="password">
		 			<label for=""role""><font color="#e67e22" size="4">Role</label><br>
		 			<select style="width:350px;" class="form-control" name=role id="role">
						<option value="advisor">Advisor</option>
						<option value="lead advisor">Lead advisor</option>
					</select>
				</div>
				<input type="submit" value="submit" class="btn btn-primary" onclick="javascript:FormSubmit();">
	</form>			 	
	<label id="result"><font color="#e67e22" size="4"></font></label>
	</div>
	</div>
	<script> function FormSubmit(){
									var utaID = document.getElementById("utaID").value;
									var email = document.getElementById("emailAddress").value;
									var pname = document.getElementById("pname").value;
									var password = document.getElementById("password").value; 
									var role = document.getElementById("role").value;
									var params = ('utaID='+utaID+'&emailAddress='+email+'&pname='+pname+'&password='+password+'&role='+role);
									var xmlhttp;
									xmlhttp = new XMLHttpRequest();
									xmlhttp.onreadystatechange=function(){
										if (xmlhttp.readyState==4){
											document.getElementById("result").innerHTML = xmlhttp.responseText;	
											window.location.href = "view_advisors";
										}
									}
									xmlhttp.open("POST","add_advisor",true);
									xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
									xmlhttp.setRequestHeader("Content-length",params.length);
									xmlhttp.setRequestHeader("Connection","close");
									xmlhttp.send(params);
									document.getElementById("result").innerHTML = "Attempting to create new Advisor...";
								}
								</script>
<%@include file="templates/footer.jsp" %>