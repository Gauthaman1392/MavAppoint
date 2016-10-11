<jsp:include page='<%=(String)request.getAttribute("includeHeader")%>' />
<div class="container">
	<div class="jumbotron">
	<p>Settings</p>
	<form action="feedback" method="Post" onsubmit="return false;">
	<div class="row">
	<div class="col-md-4 col-lg-4">
		<div class="form-group">			
			 <label for="name">Password Expiry</label>
			 <input type="text" class="form-control" id="expiry" name="expiry" value="${expiry}<%= " hrs"%>">			 			
		</div>
	</div>
	</div>
	<button type="submit" id="submit" class="btn btn-primary" onclick="javascript:FormSubmit();">Submit</button>
	</form>  
	<label id="result"><font color="#e67e22" size="4"></font></label>		 
	</div>
</div>
<script> function FormSubmit(){
									var expiry = document.getElementById("expiry").value;									
									var params = ('expiry='+expiry);
									var xmlhttp;
									xmlhttp = new XMLHttpRequest();
									xmlhttp.onreadystatechange=function(){
										if (xmlhttp.readyState==4){
											document.getElementById("result").innerHTML = xmlhttp.responseText;	
										}
									}
									xmlhttp.open("POST","manage_settings",true);
									xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
									xmlhttp.setRequestHeader("Content-length",params.length);
									xmlhttp.setRequestHeader("Connection","close");
									xmlhttp.send(params);
									document.getElementById("result").innerHTML = "Attempting to update settings...";
								}
								</script>
<%@include file="templates/footer.jsp" %>