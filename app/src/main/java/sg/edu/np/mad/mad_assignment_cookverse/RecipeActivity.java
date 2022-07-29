package sg.edu.np.mad.mad_assignment_cookverse;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
        FBHandler fbHandler = new FBHandler(dbHandler);

        Recipe r = dbHandler.findRecipe(recipeID); //Find selected recipe
        TextView rName = findViewById(R.id.rName);
        ImageView rImage=findViewById(R.id.rImage);
        TextView duration=findViewById(R.id.duration);
        ImageView likestatus=findViewById(R.id.likestatus);
        ImageView back_button=findViewById(R.id.back_button);

        like_ornot=LoginPage.mainUser.getLikedList().contains(recipeID);
        //Check if user already liked post
        if(LoginPage.mainUser.getLikedList().contains(recipeID)){   //If user already liked post, heart is filled up
            likestatus.setImageURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.liked));
        }
        else{ //If user havent liked post
            likestatus.setImageURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.not_liked));
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
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.equals("home")){
                    Intent intent = new Intent();
                    setResult(321, intent);
                    finish();
                }
                else if (activity.equals("discover")){
                    Intent intent=new Intent();
                    setResult(456,intent);
                    finish();
                }



            }
        });
    }
}