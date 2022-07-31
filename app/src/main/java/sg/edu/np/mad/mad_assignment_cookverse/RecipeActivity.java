package sg.edu.np.mad.mad_assignment_cookverse;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    boolean showingSteps = false;
    List<Recipe> dataOriginal;
    public String GLOBAL_PREF = "MyPrefs";
    public String DATABASE_VERSION = "MyDatabaseVersion";
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;
    FBHandler fbHandler;
    boolean like_ornot;
    String activity;
    boolean shopping_ornot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        String recipeID = intent.getStringExtra("recipeID"); //Selected recipe id
        activity=intent.getStringExtra("activity"); //Activity it came from

        sharedPreferences = this.getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        dbHandler = new DBHandler(this, null, null, sharedDBVersion);
        FBHandler fbHandler = new FBHandler(dbHandler,this);

        Recipe r = dbHandler.findRecipe(recipeID); //Find selected recipe
        TextView rName = findViewById(R.id.rName);
        ImageView rImage=findViewById(R.id.rImage);
        TextView duration=findViewById(R.id.duration);
        ImageView likestatus=findViewById(R.id.likestatus);
        ImageView back_button=findViewById(R.id.back_button);
        ImageView shoppingList=findViewById(R.id.shoppingList);
        ImageView recipeEdit = findViewById(R.id.recipeEdit);
        Button about=findViewById(R.id.about);
        Dialog aboutDialog=new Dialog(RecipeActivity.this);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutDialog.setContentView(R.layout.about_recipe);


                ImageView rPic=aboutDialog.findViewById(R.id.rPic);
                new ImageLoadTask(r.getRecipeimage(), rPic).execute();
                TextView rNamee=aboutDialog.findViewById(R.id.rName_popup);
                rNamee.setText(r.getName());
                String rServingss=String.valueOf(r.getServings());
                rServingss=rServingss.concat(" Servings");
                TextView rServingsss=aboutDialog.findViewById(R.id.rServings);
                rServingsss.setText(rServingss);
                TextView rDesc=aboutDialog.findViewById(R.id.rDesc);
                rDesc.setText(r.getDescription());
                rDesc.setMovementMethod(new ScrollingMovementMethod());
                String cuisineR="Cuisine information missing";
                if(r.getCuisineList()!=null){
                    cuisineR=r.getCuisineList().get(0);
                    if(r.getCuisineList().size()>1){
                        for(int c=1;c<r.getCuisineList().size();c++){
                            cuisineR=cuisineR.concat(", ");
                            cuisineR=cuisineR.concat(r.getCuisineList().get(c));
                    }
                    //String cuisineR="hello";


                    }
                }

                TextView rCuisine=aboutDialog.findViewById(R.id.rCuisine);
                rCuisine.setText(cuisineR);
                String dietR="";
                if(r.isVegetarian()){
                    dietR=dietR.concat("Vegetarian, ");
                }
                if(r.isVegan()){
                    dietR=dietR.concat("Vegan, ");
                }
                if(r.isGlutenFree()){
                    dietR=dietR.concat("Gluten-Free, ");
                }
                if(r.isDairyFree()){
                    dietR=dietR.concat("Dairy-Free, ");
                }
                if(r.isHealthy()){
                    dietR=dietR.concat("Healthy, ");
                }
                if(dietR!=""){
                    dietR=dietR.substring(0,dietR.length()-2);
                }
                TextView rDiet=aboutDialog.findViewById(R.id.rDiet);
                rDiet.setText(dietR);
                TextView cheap2=aboutDialog.findViewById(R.id.rCheap2);
                ImageView cheap=aboutDialog.findViewById(R.id.rCheap);
                if(r.isCheap()==false){
                    cheap2.setVisibility(View.GONE);
                    cheap.setVisibility(View.GONE);
                }
                TextView popular2=aboutDialog.findViewById(R.id.rPopularr);
                ImageView popular=aboutDialog.findViewById(R.id.rPopular);
                if(r.isPopular()==false){
                    popular2.setVisibility(View.GONE);
                    popular.setVisibility(View.GONE);

                }
                aboutDialog.show();
            }

        });


        //Check if user already liked post
        if(LoginPage.mainUser.getLikedList().contains(recipeID)){   //If user already liked post, heart is filled up
            likestatus.setImageURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.liked));
        }
        else{ //If user havent liked post
            likestatus.setImageURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.not_liked));
        }

        //Check if user already added to shopping list
        if(LoginPage.mainUser.getShoppingList().contains(recipeID)){
            shoppingList.setImageURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.addedshopping));
        }
        else{
            shoppingList.setImageURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.add_tolist));
        }





        new ImageLoadTask(r.getRecipeimage(), rImage).execute();
        rName.setText(r.getName());
        String duration_String=String.valueOf(r.getDuration());
        duration_String=duration_String.concat(" minutes");

        duration.setText(duration_String);

        String recipeSteps = "";
        String recipeIngred = "";
        int step = 1;

        for(String i : r.getStepsList()){
            recipeSteps += "Step " + step + ": " + i + "\n" + "\n";
            step += 1;
        }

        for(String i : r.getIngredientsList()){
            recipeIngred += i + "\n";
        }

        TextView rSteps = findViewById(R.id.rSteps);
        rSteps.setText(recipeIngred);

        Button ingredStepsButton = findViewById(R.id.ingredStepsButton);

        if(showingSteps==true){
            ingredStepsButton.setText("Show Ingredients");
        }
        else{
            ingredStepsButton.setText("Show steps");
        }

        String finalRecipeIngred = recipeIngred;
        String finalRecipeSteps = recipeSteps;
        ingredStepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showingSteps == true) {
                    ingredStepsButton.setText("Show steps");
                    showingSteps = false;
                    rSteps.setText(finalRecipeIngred);
                } else {
                    ingredStepsButton.setText("Show ingredients");
                    showingSteps = true;
                    rSteps.setText(finalRecipeSteps);
                }
            }
        });
        if (LoginPage.mainUser.getCreatedList().contains(recipeID)){
            recipeEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(RecipeActivity.this, EditProfile.class);
                    startActivity(intent1);
                }
            });
        }
        else {
            recipeEdit.setVisibility(View.GONE);
        }
        likestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginPage.mainUser.getLikedList().contains(recipeID)){  //If recipe is liked and user clicks, it unlikes
                    (LoginPage.mainUser.getLikedList()).remove(recipeID);
                    r.setNooflikes((r.getNooflikes()-1));
                    likestatus.setImageURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.not_liked));

                }
                else{
                    (LoginPage.mainUser.getLikedList()).add(recipeID);
                    r.setNooflikes((r.getNooflikes()+1));
                    likestatus.setImageURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.liked));

                }
                dbHandler.updateUser(LoginPage.mainUser);
                fbHandler.addUpdateUser(LoginPage.mainUser);
                dbHandler.updateRecipe(r);
                fbHandler.updateRecipe(r);
            }
        });

        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginPage.mainUser.getShoppingList().contains(recipeID)){
                    (LoginPage.mainUser.getShoppingList()).remove(recipeID);
                    shoppingList.setImageURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.add_tolist));
                }
                else{
                    (LoginPage.mainUser.getShoppingList()).add(recipeID);
                    shoppingList.setImageURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.addedshopping));
                }
                dbHandler.updateUser(LoginPage.mainUser);
                fbHandler.addUpdateUser(LoginPage.mainUser);

            }
        });


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                if (activity.equals("home")){
                    setResult(321, intent);
                }
                else if (activity.equals("discover")){
                    setResult(456,intent);
                }
                else if (activity.equals("profile")){
                    setResult(123, intent);
                }
                finish();



            }
        });

    }
    private void showAboutDialog(Recipe r){
        Dialog aboutDialog=new Dialog(RecipeActivity.this);
        aboutDialog.setContentView(R.layout.about_recipe);


        ImageView rPic=aboutDialog.findViewById(R.id.rPic);
        new ImageLoadTask(r.getRecipeimage(), rPic).execute();
        TextView rName=aboutDialog.findViewById(R.id.rName);
        rName.setText(r.getName());
        String rServings=String.valueOf(r.getServings());
        rServings=rServings.concat(" Servings");
        TextView rServingss=aboutDialog.findViewById(R.id.rServings);
        rServingss.setText(rServings);
        TextView rDesc=aboutDialog.findViewById(R.id.rDesc);
        rDesc.setText(r.getDescription());
        String cuisineR=r.getCuisineList().get(0);
        for(int c=1;c<r.getCuisineList().size();c++){
            cuisineR=cuisineR.concat(",");
            cuisineR=cuisineR.concat(r.getCuisineList().get(c));

        }
        TextView rCuisine=aboutDialog.findViewById(R.id.rCuisine);
        rCuisine.setText(cuisineR);
        String dietR="";
        if(r.isVegetarian()){
            dietR=dietR.concat("Vegetarian, ");
        }
        if(r.isVegan()){
            dietR=dietR.concat("Vegan, ");
        }
        if(r.isGlutenFree()){
            dietR=dietR.concat("Gluten-Free, ");
        }
        if(r.isDairyFree()){
            dietR=dietR.concat("Dairy-Free, ");
        }
        if(r.isHealthy()){
            dietR=dietR.concat("Healthy, ");
        }
        if(dietR!=""){
            dietR=dietR.substring(0,dietR.length()-2);
        }
        TextView rDiet=aboutDialog.findViewById(R.id.rDiet);
        rDiet.setText(dietR);
        TextView cheap2=aboutDialog.findViewById(R.id.rCheap2);
        ImageView cheap=aboutDialog.findViewById(R.id.rCheap);
        if(r.isCheap()==false){
            cheap2.setVisibility(View.GONE);
            cheap.setVisibility(View.GONE);
        }
        TextView popular2=aboutDialog.findViewById(R.id.rPopularr);
        ImageView popular=aboutDialog.findViewById(R.id.rPopular);
        if(r.isPopular()==false){
            popular2.setVisibility(View.GONE);
            popular.setVisibility(View.GONE);

        }
        aboutDialog.show();

    }
}