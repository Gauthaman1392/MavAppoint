<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<style>
.custab {
	border: 1px solid #ccc;
	padding: 5px;
	margin: 5% 0;
	box-shadow: 3px 3px 2px #ccc;
	transition: 0.5s;
	background-color: #e67e22;
}

.custab:hover {
	box-shadow: 3px 3px 0px transparent;
	transition: 0.5s;
}
</style>


<div class="container">
	<div class="btn-group">
		<form action="appointments" method="post" name="cancel">
		<input type="button" class="btn btn-secondary" value="Add new advisor" onclick="window.location='add_advisor'">
			<input type=hidden name=cancel_button id="cancel_button"> <input
				type=hidden name=edit_button id="edit_button">
			<div class="row col-md-16  custyle">
				<table class="table table-striped custab">
					<thead>
						<tr>
							<th>ID</th>
							<th>Name</th>
							<th>Email</th>
							<th>Role</th>
							<th class="text-center">Action</th>
						</tr>
					</thead>
					<%@ page import="java.util.ArrayList"%>
					<%@ page import="uta.mav.appoint.beans.Advisor"%>
					<!-- begin processing appointments  -->
					<%
						ArrayList<Advisor> array = (ArrayList<Advisor>) session.getAttribute("advisors");
						if (array != null) {
					%>
					<%
						for (int i = 0; i < array.size(); i++) {
					%>
					<tr>
						<td><%=array.get(i).getId()%></td>
						<td><%=array.get(i).getName()%></td>
						<td><%=array.get(i).getEmail()%></td>
						<td><%=array.get(i).getRole()%></td>
						<td class="text-center"><button type="button" class="btn btn-secondary" id="button <%=i%>" onclick="button<%=i%>()">Edit</button></td>
						<td class="text-center"><button type="button" class="btn btn-secondary" id="button_ <%=i%>" onclick="button_<%=i%>()">Delete</button></td>
					</tr>
					<script> function button<%=i%>(){
						document.getElementById("originalId").value = "<%=array.get(i).getId()%>";
							document.getElementById("advisorId").value = "<%=array.get(i).getId()%>"; 
						document.getElementById("name").value = "<%=array.get(i).getName()%>"; 
						document.getElementById("email").value = "<%=array.get(i).getEmail()%>";
						document.getElementById("role").value = "<%=array.get(i).getRole()%>"; 										
						$('#editAdvisorModal').modal();
					}
					</script>
					<script> function button_<%=i%>(){
						DeleteAdvisor(<%=array.get(i).getId()%>, <%=i%>);
								}
					</script>
					</div>
					<%
						}
						}
					%>
					<!-- end processing advisors -->
				</table>
		</form>
	</div>
</div>
<form name=editAdvisor action="update_advisor" onsubmit="return false;"
	method="post">
	<div class="modal fade" id="editAdvisorModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id=editAdvisorTypeLabel">Update
						Advisor</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name=originalId id="originalId"> <b>ID:</b><input
						type="text" class="form-control" name=advisorId id="advisorId"><br>
					<b>Name: </b><input type="text" class="form-control" name=name
						id="name"><br> <b>Email: </b><input type="text"
						class="form-control" name=email id="email"><br> <b>Role:
					</b><select class="form-control" name=role id="role">
						<option value="advisor">Advisor</option>
						<option value="lead advisor">Lead advisor</option>
					</select><br>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<input type="submit" id="submitButton" class="btn btn-primary"
						value="Submit" onclick="javascript:UpdateAdvisor();">
				</div>
			</div>
		</div>
	</div>
</form>

<script>
function UpdateAdvisor(){
	validate2();
	var originalId = document.getElementById("originalId").value;  
	var advisorId = document.getElementById("advisorId").value; 
	var name = document.getElementById("name").value; 
	var email = document.getElementById("email").value;
	var role = document.getElementById("role").value; 					
	var params = ('originalId='+originalId+'&advisorId='+advisorId+'&name='+name+'&email='+email+'&role='+role);
	var xmlhttp;
	xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4){
			if(xmlhttp.responseText == "done"){
				$('#editAdvisorModal').modal('hide');		
				document.getElementById("submitButton").disabled  = false;
				document.getElementById("submitButton").value = "submit";
				location.reload();
			}
			else{
				
				document.getElementById("submitButton").disabled  = false;
				document.getElementById("submitButton").value = "submit";
			}				
		}
	}
	xmlhttp.open("POST","update_advisor",true);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.setRequestHeader("Content-length",params.length);
	xmlhttp.setRequestHeader("Connection","close");
	xmlhttp.send(params);
	document.getElementById("submitButton").disabled  = true;
	document.getElementById("submitButton").value = "submitting...";
}

function DeleteAdvisor(id, i){				
	validate();
	var params = ('id='+id);
	var button = "button_ "+i;
	var xmlhttp;
	xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4){
			if(xmlhttp.responseText == "done"){		
				document.getElementById(button).disabled  = true;
				document.getElementById(button).value = "Deleted";
				location.reload();
			}
			else{
				document.getElementById(button).disabled  = false;
				document.getElementById(button).value = "Delete";
			}				
		}
	}
	xmlhttp.open("POST","delete_advisor",true);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.setRequestHeader("Content-length",params.length);
	xmlhttp.setRequestHeader("Connection","close");
	xmlhttp.send(params);
	document.getElementById(button).disabled  = true;
	document.getElementById(button).value = "Deleting...";
}
</script>

<script>
function validate(){
		return confirm('Are you sure you want to delete this advisor?');	
	}
	
function validate2(){
		if (document.getElementById("advisorId").value == ""){
			alert("Advisor ID is required.");
			return false;
		}
		if (document.getElementById("name").value == ""){
			alert("Advisor name is required.");
			return false;
		}
		if (document.getElementById("email").value == ""){
			alert("Advisor email is required.");
			return false;
		}
		
}
</script>
<%@include file="templates/footer.jsp"%>