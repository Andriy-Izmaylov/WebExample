package ua.org.oa.expertiza;

import ua.org.oa.expertiza.dao.NotFoundException;
import ua.org.oa.expertiza.dao.impl.MusicTypeDaoImpl;
import ua.org.oa.expertiza.dao.impl.RoleDaoImpl;
import ua.org.oa.expertiza.dao.impl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by expertiza on 16.11.2015.
 */
public class AddUserPageAdmin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MusicTypeDaoImpl musicTypes = new MusicTypeDaoImpl();
        Set<MusicType> setMusic = musicTypes.read();
        req.setAttribute("musicTypes", setMusic);


        RoleDaoImpl role = new RoleDaoImpl();
        Set<Role> setRoles = role.read();
        req.setAttribute("roles", setRoles);


        req.getRequestDispatcher("AddUserPageAdmin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("Password");
        String confirmPassword = req.getParameter("Confirm Password");
        String firstName = req.getParameter("First name");
        String lastName = req.getParameter("Last name");
        int age = Integer.parseInt(req.getParameter("Age"));
        String country = req.getParameter("Country");
        String street = req.getParameter("Street");
        int zipCode = Integer.parseInt(req.getParameter("ZipCode"));
        String roleSite = req.getParameter("role");
        String[] musics = req.getParameterValues("musicTypes");
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
            if (!login.isEmpty() && !password.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() &&
                    age != 0 && !country.isEmpty() && !street.isEmpty() && zipCode != 0 && !confirmPassword.isEmpty()) {
                UserDaoImpl userCheck = new UserDaoImpl();
                Set<User> userSet = userCheck.read();
                boolean flag = false;
                for (User userr : userSet) {
                    if (userr.getLogin().equals(login)) {
                        flag = true;
                    }
                }
                if (!flag && password.equals(confirmPassword)) {
                    User user = new User();
                    user.setLogin(login);
                    user.setPassword(confirmPassword);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setAge(age);
                    Address address = new Address();
                    address.setCountry(country);
                    address.setStreet(street);
                    address.setZipCode(zipCode);
                    user.setAddress(address);
                    user.setMusicTypes(set);
                    Role role = new Role();
                    role.setRoleName(roleSite);
                    user.setRole(role);
                    UserDaoImpl userImpl = new UserDaoImpl();
                    userImpl.create(user);
                    Set<User> setUsers = userImpl.read();
                    req.setAttribute("setUsers", setUsers);
                    req.getRequestDispatcher("AdminPage.jsp").forward(req, resp);

                } else {
                    String message = "incorrect password of something else";

                    req.setAttribute("message", message);
                    req.getRequestDispatcher("AddUserPageAdmin").forward(req, resp);
                }
            }
    }


}

