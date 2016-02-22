package ua.org.oa.expertiza.dao.impl;

import ua.org.oa.expertiza.Address;
import ua.org.oa.expertiza.dao.CRUD;
import ua.org.oa.expertiza.dao.NotFoundException;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by expertiza on 03.11.2015.
 */
public class AddressDaoImpl implements CRUD <Address> {

    private final static String CREATE_ADDRESS = "INSERT INTO Adress (Country,street,ZipCode) VALUES (?,?,?)";
    private final static String READ_ALL_ADDRESS = "SELECT * FROM Adress";
    private final static String READ_ADDRESS_ID = "SELECT * FROM Adress WHERE Adress_ID=?";
    private final static String UPDATE_ADDRESS = "UPDATE Adress SET Country=?, street=?, ZipCode=? WHERE Adress_ID =?";
    private final static String DELETE_ADDRESS = "DELETE FROM Adress WHERE Adress_ID =?";


    @Override
    public Address create(Address obj) {
        Connection con = ConnectionDB.getConnection();
        try {
            PreparedStatement pr = con.prepareStatement(CREATE_ADDRESS, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, obj.getCountry());
            pr.setString(2, obj.getStreet());
            pr.setInt(3,obj.getZipCode());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                obj.setAdressId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public Set<Address> read() {
        Connection con = ConnectionDB.getConnection();
        Set <Address> set = new HashSet<>();

        try {
            PreparedStatement pr = con.prepareStatement(READ_ALL_ADDRESS);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                int id = rs.getInt("Adress_ID");
                String country = rs.getString("Country");
                String street = rs.getString("street");
                int zipCode = rs.getInt("ZipCode");
                Address address = new Address();
                address.setAdressId(id);
                address.setCountry(country);
                address.setStreet(street);
                address.setZipCode(zipCode);
                set.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return set;
    }

    @Override
    public Address read(int id) throws NotFoundException {
        Connection con = ConnectionDB.getConnection();
        Address address = new Address();
        try {
            PreparedStatement pr = con.prepareStatement(READ_ADDRESS_ID);
            pr.setInt(1, id);
            ResultSet res = pr.executeQuery();
            if (!res.next()) {
                throw new NotFoundException("No user with this ID");
            }
            int idAddressHelper = res.getInt("Adress_ID");
            String country = res.getString("Country");
            String street = res.getString("street");
            int zipCode = res.getInt("zipCode");
            address.setAdressId(idAddressHelper);
            address.setCountry(country);
            address.setStreet(street);
            address.setZipCode(zipCode);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;

    }

    @Override
    public boolean update(Address obj) {
        Connection con = ConnectionDB.getConnection();
        boolean updated = false;
        try {
            PreparedStatement ps = con.prepareStatement(UPDATE_ADDRESS);
            ps.setString(1, obj.getCountry());
            ps.setString(2, obj.getStreet());
            ps.setInt(3, obj.getZipCode());
            ps.setInt(4, obj.getAdressId());
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
        boolean deleteFlag = false;
        Connection con = ConnectionDB.getConnection();
        try{
            PreparedStatement pr = con.prepareStatement(DELETE_ADDRESS);
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
