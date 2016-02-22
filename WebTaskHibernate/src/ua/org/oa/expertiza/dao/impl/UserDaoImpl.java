package ua.org.oa.expertiza.dao.impl;

import ua.org.oa.expertiza.Address;
import ua.org.oa.expertiza.MusicType;
import ua.org.oa.expertiza.Role;
import ua.org.oa.expertiza.User;
import ua.org.oa.expertiza.dao.CRUD;
import ua.org.oa.expertiza.dao.NotFoundException;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by expertiza on 05.11.2015.
 */
public class UserDaoImpl implements CRUD<User> {
    private final static String CREATE_USER = "INSERT INTO User (login, password, First_Name, Last_Name, Age)"
            + " VALUES (?,?,?,?,?)";
    private final static String SET_ADDRESS_ID = "UPDATE User SET Address_ID=? WHERE id=?";
    private final static String SET_ROLE_ID = "UPDATE User SET roleID=? WHERE id=?";
    private final static String SET_MUSIC_ID_USER_ID = "INSERT INTO User_Music_Type (user_Id, music_type_Id)"
            + "VALUES(?,?)";
    private final static String READ_USERS = "SELECT id, login, password, First_Name, Last_Name, Age,"
            + " Adress_ID, Country, street, ZipCode, IDrole, Role_Name, Type_Name, Type_ID FROM User,"
            + " Adress, Music_Type, Role, User_Music_Type WHERE User.id=Adress.Adress_ID AND "
            + "User.roleID=Role.IDrole AND User.id=User_Music_Type.user_Id AND"
            + " User_Music_Type.music_type_Id=Music_Type.Type_ID;";



    private final static String READ_USER_BY_ID = "SELECT * FROM User WHERE id=?";
    private final static String GET_USER_ROLE = "SELECT IDrole, Role_Name FROM Role ,User WHERE " +
            " user.id = ? AND roleID=IDrole";
    private final static String GET_MUSIC_BY_USER_ID = "SELECT Type_ID, Type_Name FROM Music_Type, User,"
            + " User_Music_Type WHERE User.id=? AND user_Id=id AND music_type_Id=Type_ID;";

    private final static String UPDATE_USER = "UPDATE User SET login=?, password=?, First_Name=?, Last_Name=?, Age=?, roleID=? WHERE id=?";
    private final static String UPDATE_ADDRESS = "UPDATE Adress SET Country=?, street=?, ZipCode=? WHERE Adress_ID=?";
    private final static String DELETE_OLD_MUSIC_ID_USER_ID = "DELETE FROM User_Music_Type WHERE user_Id=?";
    private final static String INSERT_NEW_MUSIC_ID_USER_ID = "INSERT INTO User_Music_Type (user_Id, music_type_Id)"
            + "VALUES (?,?)";


    private final static String DELETE_USER = "DELETE FROM User WHERE id =?";
    private final static String DELETE_USER_MUSIC_TYPE = "DELETE FROM User_Music_Type WHERE user_Id =?";

    private AddressDaoImpl addressDao = new AddressDaoImpl();

    @Override
    public User create(User user) {

        Connection con = ConnectionDB.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setInt(5, user.getAge());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
                user.setId(id);
            }
            AddressDaoImpl addImpl = new AddressDaoImpl();
            Address address = new Address();
            address = user.getAddress();
            address.setUser(user);
            addImpl.create(address);
            PreparedStatement prStAdd = con.prepareStatement(SET_ADDRESS_ID);
            prStAdd.setInt(1, id);
            prStAdd.setInt(2, id);
            prStAdd.executeUpdate();
            RoleDaoImpl role = new RoleDaoImpl();
            Set<Role> setRole = new HashSet<>();
            setRole.addAll(role.read());
            for (Role roleSet : setRole) {
                if (roleSet.getRoleName().equals(user.getRole().getRoleName())) {
                    PreparedStatement prSt = con.prepareStatement(SET_ROLE_ID);
                    prSt.setInt(1, roleSet.getId());
                    prSt.setInt(2, id);
                    prSt.executeUpdate();
                    Role newRole = new Role();
                    newRole.setId(roleSet.getId());
                    newRole.setRoleName(roleSet.getRoleName());
                    user.setRole(newRole);
                }
            }
            Set<MusicType> setMusicUser = new HashSet<>();
            setMusicUser.addAll(user.getMusicTypes());
            Set<MusicType> setMusicBD = new HashSet<>();
            MusicTypeDaoImpl musicImpl = new MusicTypeDaoImpl();
            setMusicBD.addAll(musicImpl.read());
            for (MusicType userMusic : setMusicUser) {
                for (MusicType bdMusic : setMusicBD) {
                    if (userMusic.getTypeName().equals(bdMusic.getTypeName())) {
                        PreparedStatement prStat = con.prepareStatement(SET_MUSIC_ID_USER_ID);
                        prStat.setInt(1, id);
                        prStat.setInt(2, bdMusic.getId());
                        prStat.executeUpdate();
                        userMusic.setId(bdMusic.getId());
                        user.setMusicTypes(setMusicUser);

                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;

    }
    @Override
    public Set<User> read() {
        Set<User> set = new HashSet<>();
        Connection con = ConnectionDB.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement(READ_USERS);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                boolean flag = false;
                Iterator<User> iterator = set.iterator();
                while (iterator.hasNext()) {
                    User iterUser = iterator.next();
                    if (iterUser.getId() == res.getInt("id")) {
                        flag = true;
                        set.remove(iterUser);
                        User user = new User();
                        user.setId(iterUser.getId());
                        user.setLogin(iterUser.getLogin());
                        user.setPassword(iterUser.getPassword());
                        user.setFirstName(iterUser.getFirstName());
                        user.setLastName(iterUser.getLastName());
                        user.setAge(iterUser.getAge());
                        user.setAddress(iterUser.getAddress());
                        user.setRole(iterUser.getRole());
                        MusicType music = new MusicType();
                        Set<MusicType> setMusic = new HashSet<>();
                        setMusic.addAll(iterUser.getMusicTypes());
                        music.setTypeName(res.getString("Type_Name"));
                        music.setId(res.getInt("Type_ID"));
                        setMusic.add(music);
                        user.setMusicTypes(setMusic);
                        set.add(user);
                        break;
                    }

                }
                if (flag) {
                    continue;
                }

                User user = new User();
                Address address = new Address();
                Role role = new Role();
                MusicType music = new MusicType();
                Set<MusicType> setMusic = new HashSet<>();
                user.setId(res.getInt("id"));
                user.setLogin(res.getString("login"));
                user.setPassword(res.getString("password"));
                user.setFirstName(res.getString("First_Name"));
                user.setLastName(res.getString("Last_Name"));
                user.setAge(res.getInt("Age"));
                address.setAdressId(res.getInt("Adress_ID"));
                address.setCountry(res.getString("Country"));
                address.setStreet(res.getString("street"));
                address.setZipCode(res.getInt("ZipCode"));
                user.setAddress(address);
                role.setRoleName(res.getString("Role_Name"));
                role.setId(res.getInt("IDrole"));
                user.setRole(role);
                music.setTypeName(res.getString("Type_Name"));
                music.setId(res.getInt("Type_ID"));
                setMusic.add(music);
                user.setMusicTypes(setMusic);
                set.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return set;
    }
    @Override
    public User read(int id) throws NotFoundException {
        User user = new User();
        Set<MusicType> set = new HashSet<>();

        Connection con = ConnectionDB.getConnection();
        try {
            PreparedStatement pr = con.prepareStatement(READ_USER_BY_ID);
            pr.setInt(1, id);
            ResultSet res = pr.executeQuery();
            if (!res.next()) {
                throw new NotFoundException("No user with this ID");
            }

            user.setId(res.getInt("id"));
            user.setLogin(res.getString("login"));
            user.setPassword(res.getString("password"));
            user.setFirstName(res.getString("first_name"));
            user.setLastName(res.getString("last_name"));
            user.setAge(res.getInt("age"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AddressDaoImpl add = new AddressDaoImpl();
        Address address = new Address();
        address = add.read(id);
        user.setAddress(address);
        try {
            PreparedStatement prRol = con.prepareStatement(GET_USER_ROLE);
            prRol.setInt(1, id);
            ResultSet resultR = prRol.executeQuery();
            Role role = new Role();
            resultR.next();
            role.setId(resultR.getInt("IDrole"));
            role.setRoleName(resultR.getString("Role_Name"));
            user.setRole(role);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement prst = con.prepareStatement(GET_MUSIC_BY_USER_ID);
            prst.setInt(1, id);
            ResultSet result = prst.executeQuery();
            while (result.next()) {
                MusicType music = new MusicType();
                music.setId(result.getInt("Type_ID"));
                music.setTypeName(result.getString("Type_Name"));
                set.add(music);
            }

            user.setMusicTypes(set);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean update(User user) {
        Connection con = ConnectionDB.getConnection();
        boolean updated = false;
        {
            try {
                PreparedStatement ps = con.prepareStatement(UPDATE_USER);
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getFirstName());
                ps.setString(4, user.getLastName());
                ps.setInt(5, user.getAge());
                RoleDaoImpl role = new RoleDaoImpl();
                Set<Role> setRole = new HashSet<>();
                setRole.addAll(role.read());
                int roleId = 0;
                for (Role roleSet : setRole) {
                    if (roleSet.getRoleName().equals(user.getRole().getRoleName())) {
                        roleId = roleSet.getId();
                    }
                }
                ps.setInt(6, roleId);
                ps.setInt(7, user.getId());

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                PreparedStatement pst = con.prepareStatement(UPDATE_ADDRESS);
                Address address = user.getAddress();
                pst.setString(1, address.getCountry());
                pst.setString(2, address.getStreet());
                pst.setInt(3, address.getZipCode());
                pst.setInt(4, user.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                PreparedStatement prM = con.prepareStatement(DELETE_OLD_MUSIC_ID_USER_ID);
                prM.setInt(1, user.getId());
                prM.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            MusicTypeDaoImpl musicImpl = new MusicTypeDaoImpl();
            Set<MusicType> types = musicImpl.read();
            Set<MusicType> userTypes = user.getMusicTypes();

            try {
                PreparedStatement prMus = con.prepareStatement(INSERT_NEW_MUSIC_ID_USER_ID);
                for (MusicType t : types) {
                    for (MusicType mus : userTypes) {
                        if (t.getTypeName().equals(mus.getTypeName())) {
                            prMus.setInt(1, user.getId());
                            prMus.setInt(2, t.getId());
                            prMus.executeUpdate();
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            updated = true;
        }
        return updated;

    }

    @Override
    public boolean delete(int id) {

        boolean updated = false;
        Connection con = ConnectionDB.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(DELETE_USER_MUSIC_TYPE);
            ps.setInt(1, id);
            int i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement ps3 = con.prepareStatement(DELETE_USER);
            ps3.setInt(1, id);
            int j = ps3.executeUpdate();
            updated = j == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addressDao.delete(id);

        return updated;
    }
}
