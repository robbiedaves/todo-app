# todo-app - UNDER DEVELOPMENT

This is a simple tutorial on how to create a Java web todo app

##Todos for the application
* [DONE] Get js files to download using maven instead of stored
* [DONE] Test it works in JBoss Wildfly 
* [IN PROGRESS] Finish CRUD services
** There is a bug with the new and edit. One finds the css the other doesn't even though they are the same JSP page
    The only difference is one is triggered from the jsp and the other from the servlet. We need to understand how this is working
** Also, there seems to be an issue with the delete button. It works, then it doesn't. I think its the same issue as the new/edit. There is a context path issue here which we don't fully understand. We can delete once but then the servlet displays the screen again. I think on the second time display it doesn't work correctly and thus the delete doesn't work.

* Test it works deployed to heroku
* Write JUnit tests
* Test we can get this to work with Jetty
* Add DB
* See if we can use it with proper DB and H2 DB
* Add SOAP service and REST API service to view and maintain todos
* We need to test a proper GIT workflow, i.e. dev branch, merge, pull request and conflict resolution test.


###Step 1 - Create Project
Create a new project using the maven-archetype-webapp
and call it todo-app

###Step 2 - Update POM
Open the POM and add the tomcat dependency

Add the following to the POM
```XML
<plugins>
   <plugin>
      <groupId>org.apache.tomcat.maven</groupId>
      <artifactId>tomcat7-maven-plugin</artifactId>
      <version>2.2</version>
      <configuration>
          <path>/todo-app</path>
          <contextReloadable>true</contextReloadable>
      </configuration>
   </plugin>
</plugins>
```
This sets the context path to /todo-app to match the webapp name. 
We needed to do this because when we deployed into a server the URLs would not work as the web app defaulted name was todo-app.
We also set the contextReloadable parameter to true so java changes will be seen on recompile

Also edit the index.jsp and change the hello world text to Todo app, as follows
```XML
<jsp:forward page="/todos" />
```

#Step 3 - Start Tomcat server and Test

First test that the war file can be build using the following command:

```
mvn clean package
```

Then start the server and deploy the war using the following command:
```
mvn tomcat7:run
```

Now check the web app has been deployed using the following URL:

http://localhost:8080/todo/

The web page should display 'todo app'

stop the server by hitting ctrl c in the terminal.


#Step3.1

We can also add the wildfly plugin to do the same thing
```
<plugin>
    <groupId>org.wildfly.plugins</groupId>
    <artifactId>wildfly-maven-plugin</artifactId>
    <version>1.2.0.Alpha4</version>
</plugin>
```
And run it with 
```
mvn wildfly:run
```



#Step 4 - Setting up IDE to run
Note: These instructions are for Intellij
Click on the Run > Edit Configurations... menu option.

Hit the '+' button and select Maven.

In the Name field, enter 'Tomcat' and in the command line enter 'tomcat7:run'

Again, check that the web app starts



#Step 5 - Get Java Working (in development)
Add the following to the POM
```XML
      <dependency>
          <groupId>javax.servlet</groupId>
          <artifactId>javax.servlet-api</artifactId>
          <version>3.1.0</version>
          <scope>provided</scope>
      </dependency>
```

```XML
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>2.3.2</version>
        <inherited>true</inherited>
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
        </configuration>
      </plugin>
```

Add java directory to the main folder
add the following java class (com.robbiedaves.TodoServlet)


```java
package com.robbiedaves;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "TodoServlet",
        urlPatterns = {"/todos"}
)
public class TodoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type
        resp.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = resp.getWriter();
        out.println("<h1>" + "Hello This is the java Todo App!!!!!" + "</h1>");
    }
}
```

Now test the servlet is working at the following URL:

http://localhost:8080/todo/todos

#Step 6 - Write the Todo classes

Create a new Class called Todo.java
...

Create a new Class called TodoList
(This sets up dummy data)

Create a new Class called TodoService

Update TodoServlet with new methods


#Step 7 - Build the UI
We are going to build the UI with Bootstrap

There are two ways to start using Bootstrap on your own web site:
* Downloading Bootstrap from getbootstrap.com.
* Including Bootstrap from a CDN (Content Delivery Network).

For this tutorial we are going to download the precompiled and minified versions of Bootstrap CSS, JavaScript and fonts from getbootstrap.com and add them into our project.

Copy the directories js, fonts and css from the Bootstrap zip to the webapp folder.
(We should delete the non minified versions of the CSS and JS files, but I didn't!!!)

Create a new folder in the WEB-INF folder called jsp

Add a new file called list-todos.jsp in the new folder and copy the following into it:

```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
       <link rel="stylesheet" href="../css/bootstrap.min.css"/>
       <script src="../js/bootstrap.min.js"></script>
    </head>

    <body>
        <div class="container">
            <h2>Employees</h2>
            <!--Search Form -->
            <form action="/employee" method="get" id="seachEmployeeForm" role="form">
                <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
                <div class="form-group col-xs-5">
                    <input type="text" name="employeeName" id="employeeName" class="form-control" required="true" placeholder="Type the Name or Last Name of the employee"/>
                </div>
                <button type="submit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Search
                </button>
                <br></br>
                <br></br>
            </form>

            <!--Employees List-->
            <form action="/employee" method="post" id="employeeForm" role="form" >

                <c:choose>
                    <c:when test="${not empty employeeList}">
                        <table  class="table table-striped">
                            <thead>
                                <tr>
                                    <td>#</td>
                                    <td>Name</td>
                                    <td>Last name</td>
                                    <td>Birth date</td>
                                    <td>Role</td>
                                    <td>Department</td>
                                    <td>E-mail</td>
                                </tr>
                            </thead>
                            <c:forEach var="employee" items="${employeeList}">
                                <c:set var="classSucess" value=""/>
                                <c:if test ="${idEmployee == employee.id}">
                                    <c:set var="classSucess" value="info"/>
                                </c:if>
                                <tr class="${classSucess}">
                                    <td>${employee.id}</td>
                                    <td>${employee.name}</td>
                                    <td>${employee.lastName}</td>
                                    <td>${employee.birthDate}</td>
                                    <td>${employee.role}</td>
                                    <td>${employee.department}</td>
                                    <td>${employee.email}</td>

                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <br>
                        <div class="alert alert-info">
                            No people found matching your search criteria
                        </div>
                    </c:otherwise>
                </c:choose>
            </form>

        </div>
    </body>
</html>
```


Modify the index.jsp to forward to the servlet
```xml
<jsp:forward page="/employee" /> 
```



#To get JSP EL to work !!!!
I couldn't get the JSP EL to work
This site was the answer.
http://stackoverflow.com/questions/30080810/el-expressions-not-evaluated-in-jsp
The problem was the generated web.xml from maven!!!

Change the web.xml to
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.5"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
    <display-name>Archetype Created Web Application</display-name>
</web-app>
```

The reason why EL doesn't work unless you change the web.xml is as follows...

taken from http://stackoverflow.com/questions/30080810/el-expressions-not-evaluated-in-jsp

> When using JSTL 1.1 or newer, you need to assure that your web.xml is declared in such way that the webapp runs in at least Servlet 2.4 modus, 
otherwise EL expressions won't work in the webapp.
  
> When still having a Servlet 2.3 or older <!DOCTYPE> in web.xml, 
even though you already have a Servlet 2.4 or newer XSD, 
then it would still be forced to run in Servlet 2.3 or older modus, causing the EL expressions to fail.
  
> The technical reason is, EL was originally part of JSTL 1.0 and not available in Servlet 2.3 / JSP 1.2 and older. 
In JSTL 1.1, EL was removed from JSTL and integrated in JSP 2.0, which goes along with Servlet 2.4. 
So, if your web.xml is declared to run the webapp in Servlet 2.3 or older modus, 
then JSP would expect to find EL in JSTL library, but this would in turn fail if it's a newer JSTL version, 
lacking EL.
