package insurance;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/Register")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("Name");
        String date = request.getParameter("DateOfBirth");
        String gender = request.getParameter("Gender");
        String mobile = request.getParameter("MobileNo");
        String pass = request.getParameter("Password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/insurance", "root", "24sanjai2002");

            String sql = "INSERT INTO users (name, dateOfBirth, gender, mobileNo, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setDate(2, java.sql.Date.valueOf(date)); 
            ps.setString(3, gender);
            ps.setString(4, mobile);
            ps.setString(5, pass);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                out.println("<h3 style='color:green;'>Registration successful!</h3>");
                out.println("<a href=\"index.html\">Login here</a>");
            } else {
                out.println("<h3 style='color:red;'>Failed to register. Try again.</h3>");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
