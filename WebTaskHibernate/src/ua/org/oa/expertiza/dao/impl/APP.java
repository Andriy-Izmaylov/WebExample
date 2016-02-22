package ua.org.oa.expertiza.dao.impl;

import ua.org.oa.expertiza.Address;
import ua.org.oa.expertiza.MusicType;
import ua.org.oa.expertiza.Role;
import ua.org.oa.expertiza.User;
import ua.org.oa.expertiza.dao.NotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by expertiza on 12.11.2015.
 */
public class APP {
    public static void main(String[] str) throws NotFoundException {
        User user = new User();
        //user.setId(2);

        user.setLogin("1");
        user.setPassword("1");
        user.setFirstName("1");
        user.setLastName("1");
        user.setAge(223);
        Role userRole = new Role();
        userRole.setRoleName("ADMIN");
        user.setRole(userRole);
        Address address = new Address();
        address.setCountry("bba");
        address.setStreet("bba");
        address.setZipCode(223);
        user.setAddress(address);

        MusicType type1 = new MusicType();
        type1.setTypeName("Classic");
        MusicType type2 = new MusicType();
        type2.setTypeName("Rock");
        MusicType type3 = new MusicType();
        type3.setTypeName("Alternative");

        Set<MusicType> musicTypes = new HashSet<>();
        musicTypes.add(type1);
        musicTypes.add(type2);
        musicTypes.add(type3);
        user.setMusicTypes(musicTypes);
        UserDaoImpl userImpl = new UserDaoImpl();



        System.out.println(userImpl.create(user));
//         System.out.println(userImpl.read());
//        System.out.println(userImpl.read(2));
//        System.out.println(userImpl.update(user));
      //  System.out.println(userImpl.delete(4));
    }
}
