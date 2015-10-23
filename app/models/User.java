package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;

import play.data.format.*;
import play.data.validation.*;

@Entity
public class User extends Model {
    @Id
    @Constraints.Min(10)
    public Long id;

    @Constraints.Required
    public String name;

    public String password;

    public boolean admin;

    public static Finder<Long, User> find = new Finder<Long,User>(
        Long.class, User.class
    );

    public static void create(User user){
        user.save();
    }
    public static User authenticate(String name, String password){
        return find.where().eq("name", name).eq("password", password).findUnique();
    }
}
