<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Admin Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js" ></script>
        
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
        <script src="js/libs/jquery-treeview/tree.jquery.js"></script>
        
        <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/ui-lightness/jquery-ui.min.css"/>
        <link rel="stylesheet" href="js/libs/jquery-treeview/jqtree.css"/>
        <link rel="stylesheet" href="css/admin_css.css"/>
        <script src="js/functions.js"></script>
    </head>
    <body>
        <div id="top-div" class="outer-div"><label class="title">Administrator's Page</label>
        <a href="<c:url value="j_spring_security_logout"/>" class="ui-button" style="float:right"> Logout</a></div>
        <div id="mainDiv" class="outer-div">
            <div id="toolbar-div">
                <a id="addNewCategBtn" href="#" class="ui-button">Add New Category</a>
                <a id="editCategBtn" href="#" class="ui-button">Edit Category</a>
                |
                <a id="addNewMetricBtn" href="#" class="ui-button">Add New Metric</a>
                <div id="btnDiv">
                	<form method="POST" enctype="multipart/form-data" action="admin/upload/add">
						<input type="file" name="file" ><br /> 
						<!--<input type="text" name="name" class="hidden">!-->
						<input type="submit" value="Upload"> Press here to upload the file!
                    	<!--<a id="uploadFileBtn" href="#" class="ui-button">Upload File</a>
                    	<a id="deleteFileBtn" href="#" class="ui-button">Delete File</a>
                    	<a id="updateFileBtn" href="#" class="ui-button">Update File</a>-->
					</form>
                </div>
            </div>
            <hr>
            <a href="#" class="editCategCLS">Edit</a>
            <label id="selectedItem"></label>

            <div id="metrics_div"></div>
        </div>
        <div id="categDiv" class="outer-div"><h4>Categories</h4>
            <div id="tview-div">
            </div>
        </div>
        <div id="err_msg" class="msg ui-state-error ui-corner-all"></div>
        <div id="info_msg" class="msg ui-state-highlight ui-corner-all"></div>

        <!-- Add new category dialog -->
        <div id="addCategPopup" class="popup">
            Insert at: <label id="selectedCateg"></label>
            <br><br>
            <table>
                <tr>
                    <td>Name:</td><td><input type="text" id="newCategName"/></td>
                </tr>
                <tr>
                    <td>Source:</td><td><input type="text" id="newSourceName"/></td>
                </tr>
            </table>
        </div>

        <!-- Edit category dialog -->
        <div id="editCategPopup" class="popup">
            Selected Category:<label id="e_selectedCateg"></label>
            <br><br>
            <table>
                <tr>
                    <td>Name:</td><td><input type="text" id="editCategName"/></td>
                </tr>
                <tr>
                    <td>Source:</td><td><input type="text" id="editSourceName"/></td>
                </tr>
                <tr>
                    <td>Visible</td><td><input type="checkbox" id="editCategVisible"></td>
                </tr>
            </table>
        </div>

        <!-- Add metric dialog -->
        <div id="addMetricPopup" class="popup">
            Selected Category:<label id="m_selectedCateg"></label>
            <br><br>
            <table>
                <tr>
                    <td>Name:</td><td><input type="text" id="newMetricName"/></td>
                </tr>
                <tr>
                    <td>Is Calculated:</td><td><input type="checkbox" id="newMetricCalc"></td>
                </tr>
                <tr>
                    <td>Data Type:</td>
                    <td>
                        <select id="dataTypes">
                            <option value="numeric">Numeric</option>
                            <option value="currecy">Currency ($)</option>
                            <option value="percentage">Percentage (%)</option>
                        </select>
                    </td>
                </tr>
            </table>
        </div>
        <!-- Edit metric dialog -->
        <div id="editMetricPopup" class="popup">
            Selected Metric:<label id="m_selectedMetric"></label>
            <br><br>
            <table>
                <tr>
                    <td>Name:</td><td><input type="text" id="editMetricName"/></td>
                </tr>
                <tr>
                    <td>Is Calculated:</td><td><input type="checkbox" id="editMetricCalc"></td>
                </tr>
                <tr>
                    <td>Data Type:</td>
                    <td>
                        <select id="e_dataTypes">
                            <option value="numeric">Numeric</option>
                            <option value="currecy">Currency ($)</option>
                            <option value="percentage">Percentage (%)</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Visible:</td><td><input type="checkbox" id="editMetricVisible"></td>
                </tr>
            </table>
        </div>
    </body>
</html>
