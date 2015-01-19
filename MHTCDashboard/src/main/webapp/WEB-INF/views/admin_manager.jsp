<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="admin_header.jsp" %>
<%@ include file="admin_navigation_bar.jsp" %>
        		
<div id="page-wrapper">
    <div class="row">
    	<div class="col-lg-12">
        	<h1 class="page-header">Administrator Management</h1>
        </div>
        <div class="col-lg-12">
	        <div id="admin-add-form">
	            <form method="POST" action="admin/account/create">
	                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                <table>
	                    <tr>
	                        <td>Username</td>
	                        <td>
	                            <input type="text" name="Username"> </td>
	                    </tr>
	                    <tr>
	                        <td>First name</td>
	                        <td>
	                            <input type="text" name="FirstName"> </td>
	                    </tr>
	                    <tr>
	                        <td>Last name</td>
	                        <td>
	                            <input type="text" name="LastName"> </td>
	                    </tr>
	                    <tr>
	                        <td>Email</td>
	                        <td>
	                            <input type="text" name="Email"> </td>
	                    </tr>
	                    <tr>
	                        <td>Password</td>
	                        <td>
	                            <input type="password" name="Password"> </td>
	                    </tr>
	                </table>
	                <input type="submit" value="Add Admin Account">
	            </form>
	        </div>
	
	        <hr>
	        <div id="admin-change-my-pwd">
	            <form method="POST" action="admin/account/change-password">
	                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                <table>
	                    <tr>
	                        <td>Current password</td>
	                        <td>
	                            <input type="password" name="oldPassword"> </td>
	                    </tr>
	                    <tr>
	                        <td>New Password</td>
	                        <td>
	                            <input type="password" name="newPassword"> </td>
	                    </tr>
	                    <tr>
	                        <td>Confirm New Password</td>
	                        <td>
	                            <input type="password" name="confirmPassword"> </td>
	                    </tr>
	                </table>
	                <input type="submit" value="Change my password">
	            </form>
	        </div>
	
	        <hr>
	        <div id="admin-reset-password">
	            <form method="POST" action="admin/account/reset-password">
	                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                <table>
	                    <tr>
	                        <td>Admin's Username</td>
	                        <td>
	                            <input type="text" name="resetUsername"> </td>
	                    </tr>
	                </table>
	                <input type="submit" value="Reset password for this account">
	            </form>
	        </div>
		</div>
    </div>
</div>
<!-- /#page-wrapper -->

<%@ include file="admin_footer.jsp" %>