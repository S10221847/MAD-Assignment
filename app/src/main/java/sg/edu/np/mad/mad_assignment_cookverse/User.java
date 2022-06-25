package sg.edu.np.mad.mad_assignment_cookverse;

import android.graphics.Bitmap;

import java.util.List;

public class User {
    private String username;
    private String password;
    private int id; //username can arguably be used a primary key also
    /*private List<String> likedList;
    private List<String> createdList;*/
    private Bitmap userImage;

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

    /*public List<String> getLikedList() { return likedList; }

    public void setLikedList(List<String> likedList) { this.likedList = likedList; }

    public List<String> getCreatedList() { return createdList; }

    public void setCreatedList(List<String> createdList) { this.createdList = createdList; }*/

    public Bitmap getUserImage() { return userImage; }

    public void setUserImage(Bitmap userImage) { this.userImage = userImage; }
}
