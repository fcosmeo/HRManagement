package pe.edu.utp.dataservlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet(name = "ConnectedServlet",urlPatterns = "/connected")
public class ConnectedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType( "text/html; charset=iso-8859-1" );
        PrintWriter out = null;
        out = response.getWriter();
        Integer tipo = Integer.parseInt(request.getParameter("tipo"));


        Connection con;
        Statement stmt;
        PreparedStatement psInsertar;

        if(tipo==2){
            //JDBC DataSource Connection Test
            try {
                InitialContext ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("jdbc/MySQLDataSource");
                con = ds.getConnection();
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select employee_id, first_name, last_name, department_name\n" +
                        "from employees a ,departments d,jobs  j\n" +
                        "where  a.department_id=d.department_id and a.job_id=j.job_id");

                if(rs != null){
                    out.println("<p>Lista de Empleados</p>");
                    out.println("<table border='1'>");
                    out.println("<tr>");
                    out.println("<td>Id</td>");
                    out.println("<td>First Name</td>");
                    out.println("<td>Last Name</td>");
                    out.println("<td>Department Name</td>");
                    out.println("</tr>");

                    while(rs.next()){

                        out.println("<tr>");
                        out.println("<td >"+rs.getString("employee_id")+"</td>");
                        out.println("<td >"+rs.getString("first_name")+"</td>");
                        out.println("<td >"+rs.getString("last_name")+"</td>");
                        out.println("<td >"+rs.getString("department_name")+"</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");

                }else{
                    out.println("Not lucky this time");
                }
            }catch(NamingException e){
                e.printStackTrace();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }else{
            //JDBC DataSource Connection Test
            try {
                InitialContext ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("jdbc/MySQLDataSource");
                con = ds.getConnection();
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select (select count(*) from employees) as cant_emp,(select count(*) from Departments) as cant_dep,(select count(*) from Jobs ) as cant_jobs;");

                if(rs != null){
                    out.println("<p>Indicadores</p>");
                    out.println("<table border='1'>");
                    out.println("<tr>");
                    out.println("<td>Cantidad de Empleados</td>");
                    out.println("<td>Cantidad de Departamentos</td>");
                    out.println("<td>Cantidad de Puestos</td>");
                    out.println("</tr>");

                    while(rs.next()){

                        out.println("<tr>");
                        out.println("<td >"+rs.getString("cant_emp")+"</td>");
                        out.println("<td >"+rs.getString("cant_dep")+"</td>");
                        out.println("<td >"+rs.getString("cant_jobs")+"</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");

                }else{
                    out.println("Not lucky this time");
                }
            }catch(NamingException e){
                e.printStackTrace();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }


    }
}

