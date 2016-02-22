package ua.org.oa.expertiza;

import ua.org.oa.expertiza.dao.NotFoundException;
import ua.org.oa.expertiza.dao.impl.MusicTypeDaoImpl;
import ua.org.oa.expertiza.dao.impl.RoleDaoImpl;
import ua.org.oa.expertiza.dao.impl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by expertiza on 24.11.2015.
 */
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int userID = Integer.parseInt(req.getParameter("id"));
        UserDaoImpl userImpl = new UserDaoImpl();
        User user = null;
        try {
            user = userImpl.read(userID);

        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        req.setAttribute("user", user);


        MusicTypeDaoImpl musicTypes = new MusicTypeDaoImpl();
        Set<MusicType> setMusic = musicTypes.read();
        req.setAttribute("musicTypes", setMusic);


        RoleDaoImpl role = new RoleDaoImpl();
        Set<Role> setRoles = role.read();
        req.setAttribute("roles", setRoles);


        req.getRequestDispatcher("Update.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userID = Integer.parseInt(req.getParameter("id"));
        String login = req.getParameter("login");
        String password = req.getParameter("Password");
        String firstName = req.getParameter("First name");
        String lastName = req.getParameter("Last name");
        int age = Integer.parseInt(req.getParameter("Age"));
        String country = req.getParameter("Country");
        String street = req.getParameter("Street");
        int zipCode = Integer.parseInt(req.getParameter("ZipCode"));
        String roleSite = req.getParameter("role");
        String[] musics = req.getParameterValues("musicTypes");

        boolean updated = false;
        UserDaoImpl userImpl = new UserDaoImpl();


        if (!login.isEmpty() && !password.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() &&
                age != 0 && !country.isEmpty() && !street.isEmpty() && zipCode != 0 && musics != null) {


            User user = new User();
            user.setId(userID);
            user.setLogin(login);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAge(age);
            Address address = new Address();
            address.setCountry(country);
            address.setStreet(street);
            address.setZipCode(zipCode);
            user.setAddress(address);
            Role role = new Role();
            role.setRoleName(roleSite);
            user.setRole(role);
            Set<MusicType> set = new HashSet<>();
            MusicTypeDaoImpl musicImpl = new MusicTypeDaoImpl();
            for (String el : musics) {
                int id = Integer.parseInt(el);
                try {
                    set.add(musicImpl.read(id));
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }

            user.setMusicTypes(set);
            updated = userImpl.update(user);
        }
            if (updated) {
                Set<User> setUsers = userImpl.read();
                req.setAttribute("setUsers", setUsers);
                req.getRequestDispatcher("AdminPage.jsp").forward(req, resp);
            } else {
                String message = "Empty fields";
                User user = null;
                try {
                    user = userImpl.read(userID);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                req.setAttribute("user", user);
                req.setAttribute("errorMessage", message);
                MusicTypeDaoImpl musicTypes = new MusicTypeDaoImpl();
                Set<MusicType> setMusic = musicTypes.read();
                req.setAttribute("musicTypes", setMusic);
                RoleDaoImpl role = new RoleDaoImpl();
                Set<Role> setRoles = role.read();
                req.setAttribute("roles", setRoles);
                req.getRequestDispatcher("Update.jsp").forward(req, resp);

        }
    }
}


