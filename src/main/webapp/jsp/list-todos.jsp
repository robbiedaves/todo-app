<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
         <link rel="stylesheet" href="/todo-app/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>
         <script src="/todo-app/webjars/jquery/3.2.0/jquery.min.js"></script>
         <script src="/todo-app/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>

    <body>
        <div class="container">
            <h2>Todos</h2>
            
            <form action="/todo-app/todos" method="get" id="searchTodoForm" role="form">
                 <input type="hidden" id="searchAction" name="searchAction" value="searchByText">
                 <div class="form-group col-xs-5">
                    <input type="text" name="todoText" id="todoText" class="form-control" required="true" placeholder="Type the search text"/>
                 </div>
                 <button type="submit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Search
                 </button>
                 <br></br>
                 <br></br>
            </form>

            <!-- Alert Message -->
           <c:if test="${not empty message}">
               <div class="alert alert-success">
                    ${message}
               </div>
           </c:if>
            
           <form action="/todo-app/todos" method="post" id="todoForm" role="form">
            <input type="hidden" id="idTodo" name="idTodo">
                <input type="hidden" id="action" name="action">
                <c:choose>
                    <c:when test="${not empty todoList}">
                        <table  class="table table-striped">
                            <thead>
                                <tr>
                                    <td>#</td>
                                    <td>Title</td>
                                    <td>Description</td>
                                    <td>Raised By</td>
                                    <td>Completed</td>
                                </tr>
                            </thead>
                            <c:forEach var="todo" items="${todoList}">
                                <c:set var="classSuccess" value=""/>
                                <c:if test ="${idTodo == todo.id}">
                                    <c:set var="classSuccess" value="info"/>
                                </c:if>
                                <tr class="${classSuccess}">
                                    <td>
                                    <a href="/todo-app/todos?idTodo=${todo.id}&searchAction=searchById">${todo.id}</a>
                                    </td>
                                    <td>${todo.title}</td>
                                    <td>${todo.description}</td>
                                    <td>${todo.raisedBy}</td>
                                    <td>${todo.completed}</td>
                                    <td>                                                     
                                        <a href="#" id="remove" 
                                            onclick="document.getElementById('idTodo').value='${todo.id}';
                                                     document.getElementById('action').value='remove';
                                                     document.getElementById('todoForm').submit();"> 
                                            <span class="glyphicon glyphicon-trash"/>
                                        </a>                           
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <br>
                        <div class="alert alert-info">
                            No todos found matching your search criteria
                        </div>
                    </c:otherwise>
                </c:choose>
            </form>
            <form action ="jsp/new-todo.jsp">            
                <br></br>
                <button type="submit" class="btn btn-primary  btn-md">New todo</button> 
            </form>
        </div>
    </body>
</html>