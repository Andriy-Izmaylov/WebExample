package ua.org.oa.expertiza;

import ua.org.oa.expertiza.dao.impl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by expertiza on 21.11.2015.
 */
public class ModeratorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDaoImpl usImpl = new UserDaoImpl();
        Set<User> setUsers =  usImpl.read();
        req.setAttribute("setUsers",setUsers);



        req.getRequestDispatcher("ModeratorPage.jsp").forward(req,resp);
    }
}