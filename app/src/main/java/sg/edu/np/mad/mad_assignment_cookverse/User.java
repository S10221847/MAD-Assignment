package sg.edu.np.mad.mad_assignment_cookverse;

public class User {
    private String username;
    private String password;
    private int id; //username can arguably be used a primary key also

    public String getName() {
        return username;
    }

    public void setName(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
