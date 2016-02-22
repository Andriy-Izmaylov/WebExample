package ua.org.oa.expertiza;

import java.util.Set;

/**
 * Created by expertiza on 03.11.2015.
 */
public class MusicType {
    private int id;
    private String typeName;
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "MusicType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
