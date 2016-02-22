package ua.org.oa.expertiza;

import ua.org.oa.expertiza.dao.impl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * Created by expertiza on 15.11.2015.
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("Login.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        UserDaoImpl userDao = new UserDaoImpl();
        Set<User> existUser = userDao.read();
        Role role = new Role();
        String name = null;
        String lastName = null;

        boolean isEquals = false;
        for (User user : existUser) {
            if (user.getLogin().equals(login) && user.getPassword().equals(pass)){
                isEquals = true;
                role = user.getRole();
                name = user.getFirstName();
                lastName = user.getLastName();

            }
        }
        if(isEquals){
            if (role.getRoleName().equals("ADMIN")){
                HttpSession se = req.getSession(true);
                se.setAttribute("login",login);
                Set<User> setUsers = userDao.read();
                req.setAttribute("setUsers", setUsers);
                req.getRequestDispatcher("AdminPage.jsp").forward(req,resp);

            }else if(role.getRoleName().equals("MODERATOR")){
                HttpSession se = req.getSession(true);
                se.setAttribute("login",login);
                Set<User> setUsers = userDao.read();
                req.setAttribute("setUsers", setUsers);
                req.getRequestDispatcher("ModeratorPage.jsp").forward(req,resp);
            }else{
                HttpSession se = req.getSession(true);
                se.setAttribute("login",login);
                se.setAttribute("name", name);
                se.setAttribute("lastName", lastName);
                req.getRequestDispatcher("Welcome.jsp").forward(req,resp);
            }
        }else {
            String message = "incorrect login or password";
            req.setAttribute("errorMessage", message);
            req.getRequestDispatcher("Login.jsp").forward(req,resp);
        }
    }
}