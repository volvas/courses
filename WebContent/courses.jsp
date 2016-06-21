<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Available Courses</title>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
		<script type="text/javascript" src="js/jquery-2.2.2.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.js"></script>
    </head>
    <body>
    	<div class="container">
			<div class="jumbotron">
				<h1>Your courses</h1>
			</div>
			
			<div class="row">
				<div class="col-xs-6 col-md-8">
					<h3>Subscribed courses</h3>
				</div>
				<div class="col-xs-6 col-md-8">
					 <table class="table table-hover">
			            <tr>
			                <th>Course_id</th>
			                <th>Name</th>
			                <th>Description</th>
			            </tr>
			            <c:forEach var="subscrcourses" items="${requestScope.subscrcourses}">
			                <tr>
			                    <td>${course.id}</td>
			                    <td>${course.name}</td>
			                    <td>${course.description}</td>
			                </tr>
			            </c:forEach>
			        </table>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6 col-md-8">
					<h3>Available courses</h3>
				</div>
				<div class="col-xs-6 col-md-8">
					 <table class="table table-hover">
			            <tr>
			                <th>Course_id</th>
			                <th>Name</th>
			                <th>Description</th>
			            </tr>
			            <c:forEach var="course" items="${requestScope.courses}">
			                <tr>
			                    <td>${course.id}</td>
			                    <td>${course.name}</td>
			                    <td>${course.description}</td>
			                </tr>
			            </c:forEach>
			        </table>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6 col-md-4">
					<h4>Select the course (type the course ID):</h4>
					<form action="handler" method="post">
						<div class="form-group">
							<input type="text" class="form-control" name="coursenumber" placeholder="1...">
						</div>
						<button type="submit" class="btn btn-primary col-xs-4 col-md-3" name="ok" value="courseselect">Submit</button>
					</form>
				</div>
			</div>
		</div>
    </body>
</html>
