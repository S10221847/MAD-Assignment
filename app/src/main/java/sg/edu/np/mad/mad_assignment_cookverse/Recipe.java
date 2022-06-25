package sg.edu.np.mad.mad_assignment_cookverse;

import androidx.annotation.DrawableRes;

import java.util.List;

public class Recipe {
    private String name;
    private String description;
    private String ingredients;
    private String steps;
    private int recipeid;
    private int userid;
    /*private List<String> cuisineList;
    private List<String> ingredientList;*/
    private int nooflikes;
    /*@DrawableRes
    private int image; //represents drawable ID for image from res folder*/

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

    /*@DrawableRes
    public int getImage() {return image;}

    public void setImage(@DrawableRes int image){this.image = image;}

    public List<String> getCuisineList() { return cuisineList; }

    public void setCuisineList(List<String> cuisineList) { this.cuisineList = cuisineList; }

    public List<String> getIngredientList() { return ingredientList; }

    public void setIngredientList(List<String> ingredientList) { this.ingredientList = ingredientList; }*/

    public int getNoOfLikes() { return nooflikes; }

    public void setNoOfLikes(int nooflikes) { this.nooflikes = nooflikes; }

    public String getIngredients() { return ingredients; }

    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getSteps() { return steps; }

    public void setSteps(String steps) { this.steps = steps; }
}
