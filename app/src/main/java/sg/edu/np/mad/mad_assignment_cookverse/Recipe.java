package sg.edu.np.mad.mad_assignment_cookverse;

import android.graphics.Bitmap;

import androidx.annotation.DrawableRes;

import java.util.List;

public class Recipe {
    private String name;
    private String description;
    private String ingredients;
    private String steps;
    private int duration; //in minutes
    private String rid;
    private String uid;
    private List<String> cuisineList;
    private int nooflikes;
    private String recipeimage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getCuisineList() {
        return cuisineList;
    }

    public void setCuisineList(List<String> cuisineList) {
        this.cuisineList = cuisineList;
    }

    public int getNooflikes() {
        return nooflikes;
    }

    public void setNooflikes(int nooflikes) {
        this.nooflikes = nooflikes;
    }

    public String getRecipeimage() {
        return recipeimage;
    }

    public void setRecipeimage(String recipeimage) {
        this.recipeimage = recipeimage;
    }
}
