package sg.edu.np.mad.mad_assignment_cookverse;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    boolean showingSteps = true;
    List<Recipe> dataOriginal;
    public String GLOBAL_PREF = "MyPrefs";
    public String DATABASE_VERSION = "MyDatabaseVersion";
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        String recipeID = intent.getStringExtra("recipeID");

        sharedPreferences = this.getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        dbHandler = new DBHandler(this, null, null, sharedDBVersion);

        Recipe r = dbHandler.findRecipe(recipeID);
        TextView rName = findViewById(R.id.rName);
        rName.setText(r.getName());

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
        rSteps.setText(recipeSteps);

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
    }
}