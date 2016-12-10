<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="Courses for students">
    <meta name="author" content="Volodymyr Vasylentsev">
    <link rel="icon" href="favicon.ico">
    
    <title>Personal Account</title>
    
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/main.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <script type="text/javascript" src="js/jquery-2.2.2.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
  </head>
  
  <body>
  
    <div class="site-wrapper">

      <div class="site-wrapper-inner">

        <div class="cover-container">
        
          <!-- <div class="container"> -->
          
          <div class="masthead clearfix">
            <div class="inner">
              <div class="masthead-brand"></div>
                <div>
                           
                </div>
                <nav class="navbar">
                  <ul class="nav">
                    <div class="row">
                      <li>
                        <p class="navbar-text navbar-left">
                          Signed in as <b>${user.login}</b>, ${user.firstName} ${user.lastName}, ${user.department}.
                        </p>
                        <form class="navbar-form navbar-right" action="handler" method="post">
                          <button type="submit" class="btn btn-default col-xs-6 col-md-12" name="command" value="logout">Log out</button>
                        </form>
                      </li>
                    </div>
                  </ul>
                </nav>
             
            </div>
          </div>
          
          <div class="inner cover">

            <div class="row">
              <div class="col-xs-6 col-md-12">
                <h3>Subscribed courses</h3>
              </div>
              <div class="col-xs-6 col-md-12">
                <table class="table table-hover">
                  <tr>
                    <th class="text-center">Course ID</th>
                    <th class="text-left">Name</th>
                    <th class="text-left">Description</th>
                  </tr>
                  <c:forEach var="subscrcourse" items="${requestScope.subscrcourses}">
                    <tr>
                      <td class="text-center">${subscrcourse.id}</td>
                      <td class="text-left">${subscrcourse.name}</td>
                      <td class="text-left">${subscrcourse.description}</td>
                    </tr>
                  </c:forEach>
                </table>
              </div>
            </div>
            


            <form class="form" action="handler" method="post">
              <div class="row">
                <div class="form-group col-lg-3 col-lg-offset-3">
                  <input type="text" class="form-control" name="courseunsubscrid"
                    placeholder="Type course ID to unsubscribe">
                </div>
                
                <div class="form-group col-lg-2">
                  <button type="submit" class="btn btn-primary col-lg-12"
                    name="command" value="unsubscribe">Submit</button>
                </div>
              </div>
            </form>
            
            <hr></hr>
            
            <div class="row">
              <div class="col-xs-6 col-md-12">
                <h3>Available courses</h3>
              </div>
              <div class="col-xs-6 col-md-12">
                <table class="table table-hover">
                  <tr>
                    <th class="text-center">Course ID</th>
                    <th class="text-left">Name</th>
                    <th class="text-left">Description</th>
                  </tr>
                  <c:forEach var="course" items="${requestScope.courses}">
                    <tr>
                      <td class="text-center">${course.id}</td>
                      <td class="text-left">${course.name}</td>
                      <td class="text-left">${course.description}</td>
                    </tr>
                  </c:forEach>
                </table>
              </div>
            </div>
            
            <form class="form" action="handler" method="post">
              <div class="row">
                <div class="form-group col-lg-3 col-lg-offset-3">
                  <input type="text" class="form-control" name="coursesubscrid"
                    placeholder="Type course ID to subscribe">
                </div>
                
                <div class="form-group col-lg-2">
                  <button type="submit" class="btn btn-primary col-lg-12"
                    name="command" value="subscribe">Submit</button>
                </div>
              </div>
            </form>
            
          </div>
            
          <!-- </div> -->
          
          
        </div>
          
      </div>
    
    </div>
    
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/jquery.min.js"><\/script>')</script>
    <script src="js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
