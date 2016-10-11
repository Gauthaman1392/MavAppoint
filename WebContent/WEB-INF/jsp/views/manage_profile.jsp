<jsp:include page='<%=(String)request.getAttribute("includeHeader")%>' />
<div class="container">
	<div class="jumbotron">
	<p>Feedback</p>
	<form action="feedback" method="Post" onsubmit="return false;">
	<div class="row">
	<div class="col-md-4 col-lg-4">
		<div class="form-group">			
			 <label for="name">Name</label>
			 <input type="text" class="form-control" id="name" name="name" value="${name}">			 
			 
			 <label for="email">Email</label>
			 <input type="text" class="form-control" id="email" name="email" value="${email}">
		</div>
	</div>
	</div>
	<button type="submit" id="submit" class="btn btn-primary" onclick="javascript:FormSubmit();">Submit</button>
	</form>  
	<label id="result"><font color="#e67e22" size="4"></font></label>		 
	</div>
</div>
<script> function FormSubmit(){
									var name = document.getElementById("name").value;
									var email = document.getElementById("email").value;
									var params = ('name='+name+'&email='+email);
									var xmlhttp;
									xmlhttp = new XMLHttpRequest();
									xmlhttp.onreadystatechange=function(){
										if (xmlhttp.readyState==4){
											document.getElementById("result").innerHTML = xmlhttp.responseText;	
										}
									}
									xmlhttp.open("POST","manage_profile",true);
									xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
									xmlhttp.setRequestHeader("Content-length",params.length);
									xmlhttp.setRequestHeader("Connection","close");
									xmlhttp.send(params);
									document.getElementById("result").innerHTML = "Attempting to update profile...";
								}
								</script>
<%@include file="templates/footer.jsp" %>