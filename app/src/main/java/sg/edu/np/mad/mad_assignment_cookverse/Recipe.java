package sg.edu.np.mad.mad_assignment_cookverse;

import androidx.annotation.DrawableRes;

public class Recipe {
    private String name;
    private String description;
    private int recipeid;
    private int userid;
    @DrawableRes
    private int image; //represents drawable ID for image from res folder

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRecipeId() {
        return recipeid;
    }

    public void setRecipeId(int recipeid) {
        this.recipeid = recipeid;
    }

    public int getUserId() {
        return userid;
    }

    public void setUserId(int userid) {
        this.userid = userid;
    }

    @DrawableRes
    public int getImage() {return image;}

    public void setImage(@DrawableRes int image){this.image = image;}
}
