package ua.org.oa.expertiza.dao.impl;

import ua.org.oa.expertiza.Role;
import ua.org.oa.expertiza.dao.CRUD;
import ua.org.oa.expertiza.dao.NotFoundException;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by expertiza on 03.11.2015.
 */

public class RoleDaoImpl implements CRUD <Role>{

    private final static String createRole = "INSERT INTO Role (Role_Name) VALUES (?)";
    private final static String readRolesAll = "SELECT * FROM Role";
    private final static String readRoleId = "SELECT * FROM Role WHERE IDrole=?";
    private final static String UpdateRole = "UPDATE Role SET Role_Name=? WHERE IDrole =?";
    private final static String deleteRole = "DELETE FROM Role WHERE IDrole =?";
    @Override
    public Role create(Role role) {

        Connection conRole = ConnectionDB.getConnection();
        try{
            PreparedStatement ps = conRole.prepareStatement(createRole, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, role.getRoleName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                role.setId(id);}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public Set<Role> read() {
        Set<Role> set = new HashSet<>();

        Connection con = ConnectionDB.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(readRolesAll);

            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("IDrole");
                String roleName = res.getString("role_name");
                Role newRole = new Role();
                newRole.setId(id);
                newRole.setRoleName(roleName);
                set.add(newRole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return set;
    }

    @Override
    public Role read(int id) throws NotFoundException {

        Connection con = ConnectionDB.getConnection();
        Role role = new Role();
        try {
            PreparedStatement pr = con.prepareStatement(readRoleId);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (!rs.next()){
                throw new NotFoundException("cannot find this");
            }
            role.setId(id);
            role.setRoleName(rs.getString("Role_Name"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public boolean update(Role obj) {
        Connection con = ConnectionDB.getConnection();
        boolean upd = false;
        try{
            PreparedStatement pr = con.prepareStatement(UpdateRole);
            pr.setString(1, obj.getRoleName());
            pr.setInt(2, obj.getId());
            int update = pr.executeUpdate();
            if(update == 1 ){
                upd = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(int id) {
        boolean deleteFlag = false;
        Connection con = ConnectionDB.getConnection();
        try{
            PreparedStatement pr = con.prepareStatement(deleteRole);
            pr.setInt(1, id);

            int i = pr.executeUpdate();
            if (i == 1) {
                deleteFlag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteFlag;
    }

}