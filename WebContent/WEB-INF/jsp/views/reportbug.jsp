<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<div class="container">
	<div class="jumbotron">
	<p>Report bug</p>
	<form action="reportbug" method="Post" onsubmit="return false;">
	<textarea name="bugreportField" id ="bugreportField" rows="10" cols="50"></textarea>
  		<br/>
  		<br/>  		
  		<input type="submit" class="btn btn-primary"  value="Submit" onclick="javascript:FormSubmit();"/>
  		<input type="button" value="Close" class="btn btn-secondary" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);"/>
	</form>  
	<label id="result"><font color="#e67e22" size="4"></font></label>		 
	</div>
</div>
<script> function FormSubmit(){
									var bugreport = document.getElementById("bugreportField").value;
									if(bugreport.length==0){
									       alert("bugreport is required");
                                           return false;
									}
									var params = ('bugreportField='+bugreport);
									var xmlhttp;
									xmlhttp = new XMLHttpRequest();
									xmlhttp.onreadystatechange=function(){
										if (xmlhttp.readyState==4){
											document.getElementById("result").innerHTML = xmlhttp.responseText;	
										}
									}
									xmlhttp.open("POST","reportbug",true);
									xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
									xmlhttp.setRequestHeader("Content-length",params.length);
									xmlhttp.setRequestHeader("Connection","close");
									xmlhttp.send(params);
									document.getElementById("result").innerHTML = "Attempting to send bug report...";
								}
								</script>
<%@include file="templates/footer.jsp" %>