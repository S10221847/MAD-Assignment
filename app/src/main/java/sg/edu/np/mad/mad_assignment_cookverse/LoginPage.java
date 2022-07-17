package sg.edu.np.mad.mad_assignment_cookverse;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LoginPage extends AppCompatActivity {
    public String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        //addRecipe(r1);

        TextView newUser = findViewById(R.id.userSignup);
        newUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                Intent myNewCreate = new Intent(LoginPage.this, CreateUserPage.class);
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

                isValidCredentials(etMyUserName.getText().toString(), etMyPassword.getText().toString());
            }
        });

    }

    public void isValidCredentials(String username, String password){
        Query query = FirebaseDatabase.getInstance().getReference().child("Accounts").child(username);
        List<User> list = new ArrayList<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User userDBData = dataSnapshot.getValue(User.class);
                    Log.v(TAG, "Message Please");
                    if(userDBData.getName().equals(username) && userDBData.getPassword().equals(password)){
                        Intent myIntent = new Intent(LoginPage.this, MainFragment.class);
                        myIntent.putExtra("username", username);
                        myIntent.putExtra("image", userDBData.getUserImage());
                        myIntent.putExtra("bio", userDBData.getBio());
                        startActivity(myIntent);
                        Toast.makeText(LoginPage.this,"Valid",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LoginPage.this, "Invalid Login", Toast.LENGTH_SHORT);
                    }
                }
                else{
                    Toast.makeText(LoginPage.this, "No user by that name exists", Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        };
        query.addValueEventListener(eventListener);
    }

    public void addRecipe(Recipe r){
        String rid = "";
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rid = rootRef.child("Recipes").push().getKey();
        r.setRid(rid);
        DatabaseReference ref = rootRef.child("Recipes").child(rid);
        ref.setValue(r);
    }
    public void updateRecipe(Recipe r){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String rid = r.getRid();
        DatabaseReference ref = rootRef.child("Recipes").child(rid);
        ref.setValue(r);
    }

    public void addUpdateUser(User u){ //both adds and updates user data within firebase
        String username = u.getName();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Accounts").child(username);
        ref.setValue(u);
    }

    public void findUser(User u){
        String username = u.getName();
        Query query = FirebaseDatabase.getInstance().getReference().child("Accounts").child(username);
        List<User> list = new ArrayList<>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User u = dataSnapshot.getValue(User.class);
                }
                //ALL CODE THAT USES User object MUST BE HERE

                //ALL CODE THAT USES User object MUST BE HERE
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        };
        query.addValueEventListener(eventListener);
    }

    public void findRecipe(Recipe r){
        String rid = r.getRid();
        Query query = FirebaseDatabase.getInstance().getReference().child("Recipes").child(rid);
        List<User> list = new ArrayList<>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Recipe r = dataSnapshot.getValue(Recipe.class);
                }
                //ALL CODE THAT USES Recipe object MUST BE HERE

                //ALL CODE THAT USES Recipe object MUST BE HERE
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        };
        query.addValueEventListener(eventListener);
    }

    public void listRecipe(){
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
                //ALL CODE THAT USES Recipe list MUST BE HERE

                //ALL CODE THAT USES Recipe list MUST BE HERE
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        };
        query.addValueEventListener(eventListener);
    }

    public Recipe basicRecipeExample(){
        Recipe r1 = new Recipe();
        r1.setName("Creamy Pulled Pork Soup");
        r1.setDescription("This creamy soup can be made with leftover pulled pork. ");
        ArrayList<String> sList = new ArrayList<>();
        sList.add("Combine milk and chicken broth in a large pot. Add pulled pork and barbecue sauce and stir. Stir in chili powder, black pepper, oregano, and salt. Let simmer over low heat, stirring occasionally, for 45 minutes.");
        sList.add("Divide into 6 bowls and top with parsley.");
        r1.setStepsList(sList);
        ArrayList<String> iList = new ArrayList<>();
        iList.add("2 cups whole milk");
        iList.add("2 cups chicken broth");
        iList.add("1¾ cups cooked pulled pork");
        iList.add("2 tablespoons barbecue sauce");
        iList.add("2 teaspoons chili powder");
        iList.add("2 teaspoons ground black pepper");
        iList.add("1½ teaspoons dried oregano");
        iList.add("1 teaspoon salt");
        iList.add("2 tablespoons dried parsley");
        r1.setIngredientsList(iList);
        r1.setDuration(55);
        r1.setUid("test");
        ArrayList<String> cList = new ArrayList<>();
        cList.add("Italian");
        r1.setCuisineList(cList);
        r1.setNooflikes(0);
        r1.setServings(6);
        r1.setRecipeimage("https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fimages.media-allrecipes.com%2Fuserphotos%2F9146205.jpg&w=595&h=595&c=sc&poi=face&q=60");

        return r1;
        //then call addRecipe(r1) method to add to firebase
    }

    public void baseRecipe(){
        Recipe r1 = new Recipe();
        r1.setName("");
        r1.setDescription("");
        ArrayList<String> sList = new ArrayList<>();
        sList.add("");
        sList.add("");
        r1.setStepsList(sList);
        ArrayList<String> iList = new ArrayList<>();
        iList.add("");
        iList.add("");
        iList.add("");
        iList.add("");
        iList.add("");
        iList.add("");
        iList.add("");
        iList.add("");
        iList.add("");
        r1.setIngredientsList(iList);
        r1.setDuration(0);
        //r1.setUid("test");
        ArrayList<String> cList = new ArrayList<>();
        cList.add("");
        r1.setCuisineList(cList);
        r1.setNooflikes(0);
        r1.setServings(0);
        r1.setRecipeimage("");

    }
}