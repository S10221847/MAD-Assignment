package sg.edu.np.mad.mad_assignment_cookverse;

import android.graphics.Bitmap;

import java.util.List;

public class User {
    private String name;
    private String password;
    private String userImage;
    private String bio;
    private List<String> likedList;
    private List<String> createdList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getLikedList() {
        return likedList;
    }

    public void setLikedList(List<String> likedList) {
        this.likedList = likedList;
    }

    public List<String> getCreatedList() {
        return createdList;
    }

    public void setCreatedList(List<String> createdList) {
        this.createdList = createdList;
    }
}
