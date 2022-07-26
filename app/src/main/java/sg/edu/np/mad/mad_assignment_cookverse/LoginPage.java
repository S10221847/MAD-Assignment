package sg.edu.np.mad.mad_assignment_cookverse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginPage extends AppCompatActivity {
    public String TAG = "Main Activity";
    public static User mainUser = new User();
    public String GLOBAL_PREF = "MyPrefs";
    public String DATABASE_VERSION = "MyDatabaseVersion";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        sharedPreferences = getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        sharedDBVersion += 1;
        DBHandler dbHandler = new DBHandler(this, null, null, sharedDBVersion);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DATABASE_VERSION, sharedDBVersion);
        editor.apply();
        FBHandler fbHandler = new FBHandler(dbHandler);
        fbHandler.retrieveFBUserData();
        fbHandler.retrieveFBRecipeData();

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
        //User userDBData = dbHandler.findUserByName(username);

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
                        /*myIntent.putExtra("username", username);
                        myIntent.putExtra("image", userDBData.getUserImage());*/
                        myIntent.putExtra("activity", "loginpage");
                        mainUser.setName(username);
                        mainUser.setBio(userDBData.getBio());
                        mainUser.setUserImage(userDBData.getUserImage());
                        mainUser.setPassword(userDBData.getPassword());
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
}