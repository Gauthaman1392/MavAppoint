<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<div class="container">
	<div class="jumbotron">
	<p>Feedback</p>
	<form action="feedback" method="Post" onsubmit="return false;">
	<textarea name="feedbackField" id ="feedbackField" rows="10" cols="50"></textarea>
  		<br/>
  		<br/>  		
  		<input type="submit"  value="Submit" class="btn btn-primary" onclick="javascript:FormSubmit();"/>
  		<input type="button" value="Close" class="btn btn-secondary" onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);"/>
	</form>  
	<label id="result"><font color="#e67e22" size="4"></font></label>		 
	</div>
</div>
<script> function FormSubmit(){
									var feedback = document.getElementById("feedbackField").value;
									if(feedback.length==0){
									       alert("Feedback is required");
                                     return false;
									}
									var params = ('feedbackField='+feedback);
									var xmlhttp;
									xmlhttp = new XMLHttpRequest();
									xmlhttp.onreadystatechange=function(){
										if (xmlhttp.readyState==4){
											document.getElementById("result").innerHTML = xmlhttp.responseText;	
										}
									}
									xmlhttp.open("POST","feedback",true);
									xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
									xmlhttp.setRequestHeader("Content-length",params.length);
									xmlhttp.setRequestHeader("Connection","close");
									xmlhttp.send(params);
									document.getElementById("result").innerHTML = "Attempting to send feedback...";
								}
								</script>
<%@include file="templates/footer.jsp" %>