package sg.edu.np.mad.mad_assignment_cookverse;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    //member variables

    Button myLoginButton;
    TextView newUser;
    EditText etMyUserName;
    EditText etMyPassword;

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private FBHandler fbHandler;
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        sharedPreferences = this.getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        dbHandler = new DBHandler(this, null, null, sharedDBVersion);
        fbHandler = new FBHandler(dbHandler, this);

        //init member variables
        newUser = findViewById(R.id.userSignup);
        myLoginButton = findViewById(R.id.myLoginButton);
        etMyUserName = findViewById(R.id.editmyEmailAdd);
        etMyPassword = findViewById(R.id.editmyPassword);

        progressDialog = new ProgressDialog(this);
        initFirebaseAuth();

        newUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myNewCreate = new Intent(LoginPage.this, CreateUserPage.class);
                startActivity(myNewCreate);
                return false;
            }
        });
        myLoginButton.setOnClickListener(view -> {
            //Perform auth/ Login method
            PerformLogin();
        });
    }

    private void initFirebaseAuth() {
        //create firebase member ver
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private void PerformLogin() {
        String email = etMyUserName.getText().toString();
        String password = etMyPassword.getText().toString();

        //if user haven't added email in editText
        if (email.isEmpty()) {
            etMyUserName.setError("Type Email");
            etMyUserName.requestFocus();
        }
        //if user didn't added password in editText
        else if (password.isEmpty()) {
            etMyPassword.setError("Type Email");
            etMyPassword.requestFocus();
        } else {

            progressDialog.setMessage("Please wait ,Login...");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //if email and password matched by auth method then check verification of user
                    if (task.isSuccessful()) {
                        mUser = mAuth.getCurrentUser();
                        //if user already verified then open main page/home page
                        if (mUser.isEmailVerified()) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginPage.this, "Login Completed", Toast.LENGTH_SHORT).show();
                            String emailNew = email.replaceAll("\\p{Punct}+", "");
                            mainUser = dbHandler.findUser(emailNew);
                            mainUser.setPassword(password);
                            List<String> liked_List =new ArrayList<>();
                            List<String> shop_List =new ArrayList<>();
                            List<String> created_List =new ArrayList<>();
                            if(mainUser.getLikedList() == null){
                                mainUser.setLikedList(liked_List);
                            }

                            if(mainUser.getShoppingList() == null){
                                mainUser.setShoppingList(shop_List);
                            }
                            if(mainUser.getCreatedList() == null){
                                mainUser.setCreatedList(created_List);
                            }
                            sendUserToNewPage();
                        } else {
                            //if email not verified
                            mUser.sendEmailVerification();
                            progressDialog.dismiss();
                            Toast.makeText(LoginPage.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        }
    }

    private void sendUserToNewPage() {
        Intent myIntent = new Intent(LoginPage.this, MainFragment.class);
        startActivity(myIntent);
        finish();
    }
}






        /*TextView newUser = findViewById(R.id.userSignup);
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
        //myIntent.putExtra("activity", "loginpage");
                        /*mainUser.setName(username);
                        mainUser.setBio(userDBData.getBio());
                        mainUser.setUserImage(userDBData.getUserImage());
                        mainUser.setPassword(userDBData.getPassword());
                        mainUser.setLikedList(userDBData.getLikedList());
                        mainUser.setCreatedList(userDBData.getCreatedList());
                        mainUser.setShoppingList(userDBData.getShoppingList());
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
        query.addListenerForSingleValueEvent(eventListener);
    }*/

