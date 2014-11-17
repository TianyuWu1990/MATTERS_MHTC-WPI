<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin_header.jsp" %> 
<%@ include file="admin_navigation_bar.jsp" %> 

        <div id="page-wrapper">
        	<div class="row">
        		<div class="col-lg-12">
        			<h1 class="page-header">Reports</h1>
        		</div>
        	</div>
        	<div class="row">
        		<div class="col-lg-12">
                	<form method="POST" enctype="multipart/form-data" action="admin/upload/add/">
                		<select name="category">
                			<c:forEach items="${categories}" var="category">
                				<option value="${category.value}">${category.key}</option>
                			</c:forEach> 
                		</select>
						<input type="file" name="file" ><br /> 
						<input type="submit" value="Upload"> Press here to upload the file!		
    					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
                </div>
             </div>
        </div>
        
    </div>
    <!-- /#wrapper from navigation_bar.jsp -->
     

<%@ include file="admin_footer.jsp" %> 