package ua.org.oa.expertiza.dao.impl;

import ua.org.oa.expertiza.MusicType;
import ua.org.oa.expertiza.dao.CRUD;
import ua.org.oa.expertiza.dao.NotFoundException;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by expertiza on 03.11.2015.
 */
public class MusicTypeDaoImpl implements CRUD <MusicType>{
    private final static String CreateMusicType = "INSERT INTO Music_Type (Type_Name) VALUES (?)";
    private final static String ReadAllMusicTypes = "SELECT * FROM Music_Type";
    private final static String ReadMusicTypeID = "SELECT * FROM Music_Type WHERE Type_ID=?";
    private final static String UpdateMusicType = "UPDATE Music_Type SET Type_Name=? WHERE Type_ID =?";
    private final static String DeleteMusicTypes = "DELETE FROM Music_Type WHERE Type_ID =?";



    @Override
    public MusicType create(MusicType obj) {
        Connection con = ConnectionDB.getConnection();
        try {
            PreparedStatement pr = con.prepareStatement(CreateMusicType, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, obj.getTypeName());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                obj.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public Set<MusicType> read() {
        Connection con = ConnectionDB.getConnection();
        Set<MusicType> set = new HashSet<>();
        try {
            PreparedStatement pr = con.prepareStatement(ReadAllMusicTypes);
            ResultSet res = pr.executeQuery();
            while (res.next()) {
                MusicType mt = new MusicType();
                mt.setId(res.getInt("Type_ID"));
                mt.setTypeName(res.getString("type_name"));
                set.add(mt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return set;
    }

    @Override
    public MusicType read(int id) throws NotFoundException {
        Connection con = ConnectionDB.getConnection();
        MusicType mt = new MusicType();
        try {
            PreparedStatement pr = con.prepareStatement(ReadMusicTypeID);
            pr.setInt(1, id);
            ResultSet res = pr.executeQuery();
            if (!res.next()) {
                throw new NotFoundException("No user with this ID");
            }
            mt.setTypeName(res.getString("type_name"));
            mt.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mt;
    }

    @Override
    public boolean update(MusicType obj) {
        Connection con = ConnectionDB.getConnection();
        boolean updated = false;
        try {
            PreparedStatement ps = con.prepareStatement(UpdateMusicType);
            ps.setString(1, obj.getTypeName());
            ps.setInt(2, obj.getId());
            int update = ps.executeUpdate();
            if (update == 1) {
                updated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @Override
    public boolean delete(int id) {
        Connection con = ConnectionDB.getConnection();
        boolean deleted = false;
        try {
            PreparedStatement ps = con.prepareStatement(DeleteMusicTypes);
            ps.setInt(1, id);
            int i = ps.executeUpdate();
            if (i == 1) {
                deleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }
}
