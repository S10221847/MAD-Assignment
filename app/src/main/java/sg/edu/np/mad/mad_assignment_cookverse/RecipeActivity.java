package sg.edu.np.mad.mad_assignment_cookverse;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        int recipePos = intent.getIntExtra("recipePos",0);

        Query query = FirebaseDatabase.getInstance().getReference().child("Recipes");
        List<Recipe> list = new ArrayList<>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Recipe r = snapshot.getValue(Recipe.class);
                        list.add(r);
                    }
                }
                Recipe r = list.get(recipePos);
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
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        };
        query.addValueEventListener(eventListener);
    }
}