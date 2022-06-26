package sg.edu.np.mad.mad_assignment_cookverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecipeActivity extends AppCompatActivity {
    boolean showingSteps = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipeName");
        String recipeDescription = intent.getStringExtra("recipeDesc");
        String recipeSteps = intent.getStringExtra("recipeSteps");
        String recipeIngred = intent.getStringExtra("recipeIngred");

        TextView rName = findViewById(R.id.rName);
        rName.setText(recipeName);
        TextView rSteps = findViewById(R.id.rSteps);
        rSteps.setText(recipeSteps);

        Button ingredStepsButton = findViewById(R.id.ingredStepsButton);

        if(showingSteps==true){
            ingredStepsButton.setText("Show Steps");
        }
        else{
            ingredStepsButton.setText("Show ingredients");
        }

        ingredStepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showingSteps == true) {
                    ingredStepsButton.setText("Show steps");
                    showingSteps = false;
                    rSteps.setText(recipeIngred);
                } else {
                    ingredStepsButton.setText("Show ingredients");
                    showingSteps = true;
                    rSteps.setText(recipeSteps);
                }
            }
        });
    }
}