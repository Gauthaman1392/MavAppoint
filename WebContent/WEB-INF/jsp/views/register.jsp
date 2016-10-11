<%@include file="templates/header.jsp"%>

<div class="container">
	<form role="form" action="#" method="post">
		<div class="row">
			<div class="col-md-4 col-lg-4">
				<div class="form-group">
					<label for="userid" style="color: #ffffff;">User ID</label> <input
						type="text" class="form-control" name="userId"
						placeholder="100xxxxxxx or 600xxxxxxx"> <label for="name"
						style="color: #ffffff;">Name</label> <input type="text"
						class="form-control" name="name" placeholder="firstname lastname">

					<label for="emailAddress" style="color: #ffffff;">Email
						Address</label> <input type="text" class="form-control"
						name="emailAddress" placeholder="firstname.lastname@mavs.uta.edu">

				</div>
			</div>
		</div>
		<button type="submit" id="submit" class="btn btn-primary">Submit</button>
	</form>
</div>

<%@include file="templates/footer.jsp"%>