package sg.edu.np.mad.mad_assignment_cookverse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateUserPage extends AppCompatActivity {
    public String TAG = "Create User Page";

    EditText myCreateUsername;
    EditText myCreatePassword;
    Button myButtonCreate;
    Button myButtonCancel;

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private FBHandler fbHandler;
    public String GLOBAL_PREF = "MyPrefs";
    public String DATABASE_VERSION = "MyDatabaseVersion";
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creater_user_page);

        sharedPreferences = this.getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        dbHandler = new DBHandler(this, null, null, sharedDBVersion);
        fbHandler = new FBHandler(dbHandler,this);

        progressDialog = new ProgressDialog(this);

        myCreateUsername = findViewById(R.id.editUsername);
        myCreatePassword = findViewById(R.id.editPassword);
        //For validating creating account
        myButtonCreate = findViewById(R.id.buttonCreate);

        //For clicking cancel button
        myButtonCancel = findViewById(R.id.buttonCancel);
        myButtonCancel.setOnClickListener(view -> CancelAuth());

        initFirebaseAuth();


        myButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuthentication();
            }
        });

    }

    private void initFirebaseAuth() {
        //create firebase member variables
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private void PerformAuthentication() {
        String email = myCreateUsername.getText().toString();
        String password = myCreatePassword.getText().toString();

        //if user didn't added email in editText
        if (email.isEmpty()) {
            myCreateUsername.setError("Type Email");
            myCreateUsername.requestFocus();
        }
        //if user didn't added password in editText
        else if (password.isEmpty()) {
            myCreatePassword.setError("Type Email");
            myCreatePassword.requestFocus();
        } else {

            progressDialog.setMessage("Please wait ,Login...");
            progressDialog.show();

            //perform authentication by firebase authentication method
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //if auth successful send verification link
                        mUser = mAuth.getCurrentUser();
                        User userDataDB = new User();
                        String emailNew = email.replaceAll("\\p{Punct}+", "");
                        userDataDB.setName(emailNew);
                        userDataDB.setPassword(password);
                        userDataDB.setBio("Your own bio");
                        fbHandler.addUpdateUser(userDataDB);
                        dbHandler.addUser(userDataDB);

                        //send verification
                        mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //if link successfully sent
                                    progressDialog.dismiss();
                                    Toast.makeText(CreateUserPage.this, "Please Check your email and verify Link", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(CreateUserPage.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        //show error if authentication not successful
                        progressDialog.dismiss();
                        Toast.makeText(CreateUserPage.this, "" + task.getException().getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }

    }

    private void CancelAuth() {
        Toast.makeText(CreateUserPage.this, "Creation Cancelled", Toast.LENGTH_SHORT).show();
        Intent myCancelIntent = new Intent(CreateUserPage.this, LoginPage.class);
        startActivity(myCancelIntent);
    }
}

        /*EditText myCreateUsername = findViewById(R.id.editUsername);
        EditText myCreatePassword = findViewById(R.id.editPassword);
        //For validating creating account
        Button myButtonCreate = findViewById(R.id.buttonCreate);
        myButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = myCreateUsername.getText().toString();

                Query query = FirebaseDatabase.getInstance().getReference().child("Accounts").child(username);//.orderByChild("name").equalTo(username);
                List<User> list = new ArrayList<>();
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User u = dataSnapshot.getValue(User.class);
                            Log.v(TAG, "Message Please");
                            Toast.makeText(CreateUserPage.this, "User Already Exists! Try again!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if (myCreateUsername.getText().toString().length() <= 20 && myCreatePassword.getText().toString().length() > 0 && myCreateUsername.getText().toString().length() > 0){
                                User userDataDB = new User();
                                userDataDB.setName(myCreateUsername.getText().toString());
                                userDataDB.setPassword((myCreatePassword.getText().toString()));
                                /*userDataDB.setUserImage("https://upload.wikimedia.org/wikipedia/commons/a/ac/Default_pfp.jpg");
                                userDataDB.setBio("Your own bio");

                                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference ref = rootRef.child("Accounts").child(username);
                                ref.setValue(userDataDB);

                                Toast.makeText(CreateUserPage.this, "Account Created Successfully.", Toast.LENGTH_SHORT).show();

                                Intent myCreateIntent = new Intent(CreateUserPage.this, LoginPage.class);
                                startActivity(myCreateIntent);
                            }
                            else {
                                Toast.makeText(CreateUserPage.this, "Username must be within 20 characters! and password more than 1", Toast.LENGTH_SHORT).show();*/
                           /* }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.v(TAG, error.getMessage());
                    }
                };
                query.addValueEventListener(eventListener);
            }
        });
        //For clicking cancel button
        Button myButtonCancel = findViewById(R.id.buttonCancel);
        myButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreateUserPage.this,"Creation Cancelled",Toast.LENGTH_SHORT).show();
                Intent myCancelIntent = new Intent(CreateUserPage.this, LoginPage.class);
                startActivity(myCancelIntent);
            }
        });

    }
} */

