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
                    showNewForm(rq, rs);
                    break;

                case "/insert":
                    insertUser(rq, rs);
                    break;

                case "/delete":
                    deleteUser(rq, rs);
                    break;

                case "/edit":
                    showEditForm(rq, rs);
                    break;

                case "/update":
                    updateUser(rq, rs);
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

    public void updateUser(HttpServletRequest rq, HttpServletResponse rs)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(rq.getParameter("id"));
        String name = rq.getParameter("name");
        int age = Integer.parseInt(rq.getParameter("age"));
        String school = rq.getParameter("school");
        String code = rq.getParameter("code");

        User book = new User(id, name, age, school, code);
        userDAO.updateUser(book);
        rs.sendRedirect("list");
    }

    public void deleteUser(HttpServletRequest rq, HttpServletResponse rs)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(rq.getParameter("id"));
        userDAO.deleteUser(id);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.selectUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String school = request.getParameter("school");
        String code = request.getParameter("code");
        User newUser = new User(name, age, school, code);
        userDAO.insertUser(newUser);
        response.sendRedirect("list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }
}
