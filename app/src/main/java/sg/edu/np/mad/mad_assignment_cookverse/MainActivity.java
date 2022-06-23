package sg.edu.np.mad.mad_assignment_cookverse;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public String TAG = "Main Activity";
    public String MY_USERNAME = "MyUsername";
    public String MY_PASSWORD = "MyPassword";
    DBHandler dbHandler = new DBHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        addVersionRecipes(); //only for pre-existing database

        TextView newUser = findViewById(R.id.userSignup);
        newUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                Intent myNewCreate = new Intent(MainActivity.this, createUserPage.class);
                startActivity(myNewCreate);
                return false;
            }
        });

        Button myLoginButton = findViewById(R.id.myLoginButton);
        myLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etMyUserName = findViewById(R.id.editmyEmailAdd);
                EditText etMyPassword = findViewById(R.id.editmyPassword);

                if(isValidCredentials(etMyUserName.getText().toString(), etMyPassword.getText().toString())){
                    Intent myIntent = new Intent(MainActivity.this, MainFragment.class);
                    startActivity(myIntent);
                    Toast.makeText(MainActivity.this,"Valid",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Invalid Login! Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean isValidCredentials(String username, String password){
        User userDBData = dbHandler.findUserByName(username);
        if(userDBData == null){
            Toast.makeText(MainActivity.this, "User Does not Exist!", Toast.LENGTH_SHORT);

        }
        else{
            if(userDBData.getName().equals(username) && userDBData.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public void addVersionRecipes(){
        Recipe recipeData = dbHandler.findRecipe(2); //corresponds with next recipeId not in database
        User userData = dbHandler.findUserById(1);

        if(userData != null){
            if(recipeData == null){
                recipeData = new Recipe();
                recipeData.setName("Avocado Deviled Eggs"); //recipeId is 2, won't run after 2nd launch
                recipeData.setNoOfLikes(0);
                dbHandler.addRecipe(recipeData);

                recipeData.setName("Spicy Sweet Glazed Salmon");
                dbHandler.addRecipe(recipeData);

                recipeData.setName("Good Old Fashioned Pancakes");
                dbHandler.addRecipe(recipeData);

                recipeData.setName("Rhubarb Muffin");
                dbHandler.addRecipe(recipeData);
            }
        }
    }


    public void createInitialRecipe() {

        Recipe recipeData = new Recipe(); //1st Recipe, should automatically be assigned incremented primary key
        recipeData.setName("Farro Salad with Asparagus and Parmesan");
        recipeData.setNoOfLikes(0);
        /*recipeData.setDescription("A light and delicious way to get your whole grains. Perfect salad for picnics, baby or bridal showers or just because!\n" +
                "prep: 20 mins \n" +
                "\n" +
                "cook: 55 mins \n" +
                "\n" +
                "additional: 12 hrs \n" +
                "\n" +
                "total: 13 hrs 15 mins \n" +
                "\n" +
                "Servings: 12 \n" +
                "\n" +
                "Yield: 12 servings \n" +
                "\n" +
                "Ingredients: 2 cups Farro, ¾ pound fresh asparagus trimmed, 1 cup red and yellow cherry tomatoes halved, ¾ cup chopped walnuts, ¾ cup dried cranberries, ½ cup chopped fresh parsley, 1/3 cup chopped fresh chives, ¼ cup balsamic vinaigrette or to taste, 1 cup shaved Parmesan cheese divided\n" +
                "\n" +
                "\n" +
                "Step 1: Soak farro in a large bowl of water for at least 12 hours. Drain.\n" +
                "Step 2: Fill a large pot with lightly salted water and bring to a rolling boil over high heat. Once the water is boiling, stir in the drained farro, and return to a boil. Reduce heat to medium, then cook the farro uncovered, stirring occasionally for 20 minutes. Reduce heat to low, cover, and continue simmering until tender, about 30 more minutes. Drain and allow to cool.\n" +
                "Step 3: Bring a large pot of lightly salted water to a boil. Add the asparagus, and cook uncovered until tender, about 3 minutes. Drain in a colander, then immediately immerse in ice water for several minutes until cold to stop the cooking process. Once the asparagus is cold, drain well, and chop. Set aside.\n" +
                "Step 4: Place farro, asparagus, tomatoes, walnuts, cranberries, parsley, and chives in a large bowl. Drizzle the balsamic vinaigrette over and sprinkle about 3/4 cups Parmesan cheese, then toss. Top with the remaining 1/4 cup of Parmesan cheese. Serve at room temperature.\n");*/
        /*ArrayList<String> iList = new ArrayList<String>(Arrays.asList("Farro", "Asparagus", "Red and Yellow Cherry Tomatoes", "Walnuts", "Cranberries", "Parsley", "Chives", "Balsamic Vinaigrette", "Parmesan Cheese"));
        recipeData.setIngredientList(iList);
        ArrayList<String> cList = new ArrayList<String>(Arrays.asList("Greek", "Italian"));
        recipeData.setCuisineList(cList);*/
        //recipeData.setUserId(); only use when recipe is created by User, otherwise should automatically be null
        dbHandler.addRecipe(recipeData);
    }
}