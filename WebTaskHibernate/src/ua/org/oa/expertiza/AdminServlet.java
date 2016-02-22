package ua.org.oa.expertiza;

import ua.org.oa.expertiza.dao.impl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Андрей on 21.11.2015.
 */
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDaoImpl userImpl = new UserDaoImpl();
        Set<User> setUsers = userImpl.read();
        req.setAttribute("setUsers", setUsers);

        req.getRequestDispatcher("AdminPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        UserDaoImpl userImpl = new UserDaoImpl();
        boolean deleted = userImpl.delete(id);
        if (deleted) {
            Set<User> setUsers = userImpl.read();
            req.setAttribute("setUsers", setUsers);
            req.getRequestDispatcher("AdminPage.jsp").forward(req, resp);
        }

    }
}
