<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
           <link rel="stylesheet" href="../todo-app/css/bootstrap.min.css"/>
           <script src="../todo-app/js/jquery-3.2.1.js"></script>
           <script src="../todo-app/js/bootstrap.min.js"></script>
    </head>

    <body>
        <div class="container">
            <h2>Todos</h2>
            <!-- Search Form -->
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

            <!--Todo List-->
            <form action="/todo-app/todos" method="post" id="todoForm" role="form" >

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
                                    <td>${todo.id}</td>
                                    <td>${todo.title}</td>
                                    <td>${todo.description}</td>
                                    <td>${todo.raisedBy}</td>
                                    <td>${todo.completed}</td>
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
        </div>
    </body>
</html>