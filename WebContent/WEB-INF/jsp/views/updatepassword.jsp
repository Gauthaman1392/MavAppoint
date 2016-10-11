<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<script>
	$(window)
			.load(
					function() {
						$('#newPassword')
								.keyup(
										function(e) {
											var strongRegex = new RegExp(
													"^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{6,})");
											var enoughRegex = new RegExp(
													"(?=.{6,}).*", "g");
											if (false == enoughRegex.test($(
													this).val())) {
												$('#passwordStrength')
														.html(
																'Minimum 6 Characters is necessary');
												$('#passwordStrength').css(
														'color', 'red');
											} else if (strongRegex.test($(this)
													.val())) {
												$('#passwordStrength').className = 'ok';
												$('#passwordStrength').html(
														'Strong!');
												$('#passwordStrength').css(
														'color', 'green');
												$('#submit').prop('disabled',
														false);
											} else {
												$('#passwordStrength').className = 'error';
												$('#passwordStrength').html(
														'Weak!');
												$('#passwordStrength').css(
														'color', 'red');
												$('#submit').prop('disabled',
														true);
											}
											return true;
										});
					});

	function FormSubmit() {
		var currentPassword = document.getElementById("currentPassword").value;
		var newPassword = document.getElementById("newPassword").value;
		var params = ('currentPassword=' + currentPassword + '&newPassword=' + newPassword);
		var xmlhttp;
		xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				if (xmlhttp.responseText == "0") {
					document.getElementById("result").innerHTML = "Please check your current password";
				} else {
					window.location.href = "index";
				}

			}
		}
		xmlhttp.open("POST", "updatepassword", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.setRequestHeader("Content-length", params.length);
		xmlhttp.setRequestHeader("Connection", "close");
		xmlhttp.send(params);
		document.getElementById("result").innerHTML = "Attempting to change password...";
	}
</script>

<div class="container">
	<div class="row">
		<p id="result" style="color: #FFFFFF;"></p>
		<form role="form" action="#" method="post" onsubmit="return false;">
			<div class="row">
				<div class="col-md-4 col-lg-4">
					<div class="form-group">
						<label for="currentPassword" style="color: #ffffff;">Current
							Password</label> 
							<input type="password" class="form-control"
							id="currentPassword" name="currentPassword"> 
							<label for="newPassword" style="color: #ffffff;">New Password<span
							id="passwordStrength" style="color: #ffffff;"></span></label> 
							<input
							type="password" class="form-control" id="newPassword"
							name="newPassword">
					</div>
				</div>
			</div>
			<button type="submit" id="submit" disabled class="btn btn-primary"
				onclick="javascript:FormSubmit();">Submit</button>
		</form>
	</div>
</div>

<%@include file="templates/footer.jsp"%>