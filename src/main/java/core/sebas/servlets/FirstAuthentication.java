import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Authentication extends HttpServlet {

    private static final Logger log = Logger.getLogger(Authentication.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PropertyConfigurator.configure("log4j.properties");

        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        log.debug("Login user: " + username);
        
        boolean success = false;

        String query = "select * from user where username=? and password = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        Properties prop = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        if (inputStream != null) {
            prop.load(inputStream);
        }
        else {   
            log.error("property file config.properties not found in the classpath");
            throw new FileNotFoundException("property file config.properties not found in the classpath");
        }
        
        String userDB = prop.getProperty("userDB");
        String pwdDB = prop.getProperty("pwdDB");
        String hostDB = prop.getProperty("hostDB");
        String portDB = prop.getProperty("portDB");
        
        String connectionChain = "jdbc:mysql://" + hostDB + ":" + portDB + "/chess?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
        
        log.debug("Connecting to database with credentials: " + userDB + " and password: ********");
        log.debug("Database chain: " + connectionChain);
        try {
            conn = DriverManager.getConnection(connectionChain, userDB, pwdDB);
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();  

            List<String> list = new ArrayList<String>();							
            while (rs.next()) {
                // Login Successful if match is found
                success = true;

                list.add(rs.getString("username"));
                list.add(rs.getString("password"));
                session.setAttribute("user", list);
            }
        } catch (Exception e) {
            log.error("Error to database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                log.error("Error closing connection: " + e.getMessage());
            }
        }
        
        RequestDispatcher requestDispatcher = null;

        if (success) {
            requestDispatcher = request.getRequestDispatcher("/html/timer.jsp");
            requestDispatcher.forward(request, response);
        } else {
            log.error("Error in the connection to database chess");
            session.setAttribute("error", "Username or Password incorrect");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            requestDispatcher = request.getRequestDispatcher("/html/login.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
	
}
