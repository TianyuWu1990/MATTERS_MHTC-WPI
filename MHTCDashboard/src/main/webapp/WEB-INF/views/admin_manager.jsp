<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="admin_header.jsp" %>
<%@ include file="admin_navigation_bar.jsp" %>
        		
<div id="page-wrapper">
    <div class="row">
    	<div class="col-lg-12">
        	<h1 class="page-header">Administrator Management</h1>
        </div>
    </div>
    <div class="row">
    	<div class="col-lg-12">
    		<p>The <b>Administrator Management</b> page allows and admin to perform account maintenance, such as resetting a user's password or
    		changing their own, as well as the ability to create a new admin account. 
    		</p>
    	</div>
    </div>
    <div class="row">
        <div class="col-lg-4">
        	<div class="panel panel-primary">
        		<div class="panel-heading">
        			Create a new administator account
        		</div>
        		<div class="panel-body">
			        <div id="admin-add-form">
			            <form method="POST" action="admin/account/create">
			                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			                <div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
									<input type="text" class="form-control" name="Username"
										placeholder="Username">
								</div>
							</div>
							
			                <div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-envelope fa-fw"></i></span> <input type="text"
										class="form-control" name="Email" placeholder="Email">
								</div>
			               </div>
			               
			                <div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
									<input type="password" class="form-control" name="Password"
										placeholder="Password">
								</div>
							</div>
			                
			                <div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
									<input type="password" class="form-control"
										placeholder="Password confirm">
								</div>
							</div>
							
			                <div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-info fa-fw"></i></span>
									<input type="text" class="form-control" name="FirstName"
										placeholder="First Name">
								</div>
							</div>
							
			                <div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-info fa-fw"></i></span>
									<input type="text" class="form-control" name="LastName"
										placeholder="Last Name">
								</div>
							</div>
							<button type="submit" class="btn btn-success">Add Admin Account</button>
			            </form>
			        </div>
			     </div>
			 </div>
	
	        <hr>
	        
	       <div class="panel panel-primary">
        		<div class="panel-heading">
        			Change your password
        		</div>
        		<div class="panel-body">
	        
			        <div id="admin-change-my-pwd">
			            <form method="POST" action="admin/account/change-password">
			                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			                <div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
									<input type="password" class="form-control" name="oldPassword"
										placeholder="Current Password">
								</div>
							</div>
							
			                <div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
									<input type="text" class="form-control" name="newPassword"
										placeholder="New Password">
								</div>
							</div>
							
			                <div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
									<input type="text" class="form-control" name="confirmPassword"
										placeholder="Confirm New Password">
								</div>
							</div>
							<button type="submit" class="btn btn-success">Change my password</button>
			            </form>
			        </div>
			     </div>
			</div>
	
	        <hr>
	        
	       <div class="panel panel-primary">
        		<div class="panel-heading">
        			Reset a user's password
        		</div>
        		<div class="panel-body">
	        
			        <div id="admin-reset-password">
			            <form method="POST" action="admin/account/reset-password">
			                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			                <div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
									<input type="text" class="form-control" name="resetUsername"
										placeholder="Username">
								</div>
							</div>
							<button type="submit" class="btn btn-success">Reset password for this user</button>
			            </form>
			        </div>
			  	</div>
			</div>
			
		</div>
    </div>
</div>
<!-- /#page-wrapper -->

<%@ include file="admin_footer.jsp" %>