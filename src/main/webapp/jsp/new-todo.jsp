<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
         <link rel="stylesheet" href="/todo-app/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>
         <script src="/todo-app/webjars/jquery/3.2.0/jquery.min.js"></script>
         <script src="/todo-app/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <form action="/todo-app/todos" method="post" role="form" data-toggle="validator" >
                <c:if test ="${empty action}">                        	
                    <c:set var="action" value="add"/>
                </c:if>
                <input type="hidden" id="action" name="action" value="${action}">
                <input type="hidden" id="idTodo" name="idTodo" value="${todo.id}">
                <h2>Todo</h2>
                <div class="form-group col-xs-4">
                    <label for="title" class="control-label col-xs-4">Title:</label>
                    <input type="text" name="title" id="title" class="form-control" value="${todo.title}" required="true" />                                   

                    <label for="description" class="control-label col-xs-4">Description:</label>                   
                    <input type="text" name="description" id="description" class="form-control" value="${todo.description}" required="true"/> 

                    <label for="raisedBy" class="control-label col-xs-4">Raised By:</label>                    
                    <input type="text" name="raisedBy" id="raisedBy" class="form-control" value="${todo.raisedBy}" required="true"/> 

                    <label for="completed" class="control-label col-xs-4">Completed:</label>
                    <input type="text" name="completed" id="completed" class="form-control" value="${todo.completed}" required="true"/>

                    <br></br>
                    <button type="submit" class="btn btn-primary  btn-md">Accept</button> 
                </div>                                                      
            </form>
        </div>
    </body>
</html>