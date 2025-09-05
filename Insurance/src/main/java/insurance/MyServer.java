package insurance;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/MyServer")
public class MyServer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mobileNo = request.getParameter("MobileNo");
        String password = request.getParameter("pass");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/insurance", "root", "24sanjai2002");

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE mobileNo=? AND password=?");
            ps.setString(1, mobileNo);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("userMobile", mobileNo);

                if ("9080660401".equals(mobileNo) && "2410".equals(password)) {
                    RequestDispatcher rd = request.getRequestDispatcher("Admin.html");
                    rd.forward(request, response);
                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("User.html");
                    rd.forward(request, response);
                }
            } else {
                out.print("<h3 style='color:red'>Login failed! Invalid MobileNo or Password.</h3>");
                RequestDispatcher rd = request.getRequestDispatcher("index.html");
                rd.include(request, response);
            }

            con.close();

        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
