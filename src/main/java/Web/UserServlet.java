package Web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import DAO.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;

@WebServlet("/")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        doGet(rq, rs);
    }

    protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        String action = rq.getServletPath();

        try {
            switch (action) {
                case "/new":
                    // showNewForm(rq, rs);
                    break;

                case "/insert":
                    // insertUser(rq, rs);
                    break;

                case "/delete":
                    // deleteUser(rq, rs);
                    break;

                case "/edit":
                    // showEditForm(rq, rs);
                    break;

                case "/update":
                    // updateUser(rq, rs);
                    break;

                default:
                    listUser(rq, rs);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listUser(HttpServletRequest rq, HttpServletResponse rs)
            throws SQLException, IOException, ServletException {
        List<User> users = userDAO.getUsers();

        rq.setAttribute("users", users);
        RequestDispatcher dispatcher = rq.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(rq, rs);
    }
}
