/**
 * Created by Jeremy on 08/07/2018.
 */

package strathmore.edu.strathevents;

/**
 * Created by Belal on 9/5/2017.
 */


//this is very simple class and it only contains the user attributes, a constructor and the getters
// you can easily do this by right click -> generate -> constructor and getters
public class User {

    private String username, email, password;
    private  Integer id;

    public User(Integer id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public User(Integer id,String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public User(String email, String password) {

        this.email = email;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }


}
