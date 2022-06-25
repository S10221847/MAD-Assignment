package sg.edu.np.mad.mad_assignment_cookverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipeName");
        String recipeDescription = intent.getStringExtra("recipeDesc");
        String recipeSteps = intent.getStringExtra("recipeSteps");

        TextView rName = findViewById(R.id.rName);
        rName.setText(recipeName);

        TextView rSteps = findViewById(R.id.rSteps);
        rSteps.setText(recipeSteps);
    }
}