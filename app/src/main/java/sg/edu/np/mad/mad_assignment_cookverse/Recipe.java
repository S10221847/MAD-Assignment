package sg.edu.np.mad.mad_assignment_cookverse;

import android.graphics.Bitmap;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;
    private String description;
    private int duration; //in minutes
    private String rid;
    private String uid;
    private List<String> cuisineList = new ArrayList<>();
    private List<String> ingredientsList = new ArrayList<>();
    private List<String> stepsList = new ArrayList<>();
    private int nooflikes;
    private String recipeimage;
    private int servings;

    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private boolean healthy;
    private boolean cheap;
    private boolean popular;




    public boolean isVegetarian(){return vegetarian;}
    public void setVegetarian(boolean b){this.vegetarian=b;}

    public boolean isVegan(){return vegan;}
    public void setVegan(boolean b){this.vegan=b;}

    public boolean isGlutenFree(){return glutenFree;}
    public void setGlutenFree(boolean b){this.glutenFree=b;}

    public boolean isDairyFree(){return dairyFree;}
    public void setDairyFree(boolean b){this.dairyFree=b;}

    public boolean isHealthy(){return healthy;}
    public void setHealthy(boolean b){this.healthy=b;}

    public boolean isCheap(){return cheap;}
    public void setCheap(boolean b){this.cheap=b;}

    public boolean isPopular(){return popular;}
    public void setPopular(boolean b){this.popular=b;}


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getDuration() { return duration; }

    public void setDuration(int duration) { this.duration = duration; }

    public String getRid() { return rid; }

    public void setRid(String rid) { this.rid = rid; }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

    public List<String> getCuisineList() { return cuisineList; }

    public void setCuisineList(List<String> cuisineList) { this.cuisineList = cuisineList; }

    public int getNooflikes() { return nooflikes; }

    public void setNooflikes(int nooflikes) { this.nooflikes = nooflikes; }

    public String getRecipeimage() { return recipeimage; }

    public void setRecipeimage(String recipeimage) { this.recipeimage = recipeimage; }

    public List<String> getIngredientsList() { return ingredientsList; }

    public void setIngredientsList(List<String> ingredientsList) { this.ingredientsList = ingredientsList; }

    public List<String> getStepsList() { return stepsList; }

    public void setStepsList(List<String> stepsList) { this.stepsList = stepsList; }

    public int getServings() { return servings; }

    public void setServings(int servings) { this.servings = servings; }
}
