package servlet;

import dao.TechShopDAO;
import entities.User;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alexlopez
 */
public class Login extends HttpServlet {

    TechShopDAO techShopDAO = new TechShopDAO();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("pass");
        
        User user = new User(email, password);
        
        //Conect to data base
      
        
        try {
            if (techShopDAO.logInUser(user)) {
                User userSession = techShopDAO.allUserData(email);
                request.getSession(true).setAttribute("user", userSession);
                request.getRequestDispatcher("JSP/Home.jsp").forward(request, response);
            } else {
                request.setAttribute("Status", "Email or password incorrect");
                request.getRequestDispatcher("JSP/Status.jsp").forward(request, response);
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            request.setAttribute("Status", ex.getMessage());
            request.getRequestDispatcher("JSP/Status.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
