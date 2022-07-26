package sg.edu.np.mad.mad_assignment_cookverse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    public String MY_USERNAME = "MyUsername";
    public String MY_PASSWORD = "MyPassword";
    //DBHandler dbHandler = new DBHandler(this,null,null,1);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creater_user_page);

        EditText myCreateUsername = findViewById(R.id.editUsername);
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
                                /*userDataDB.setUserImage("https://upload.wikimedia.org/wikipedia/commons/a/ac/Default_pfp.jpg");*/
                                userDataDB.setBio("Your own bio");

                                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference ref = rootRef.child("Accounts").child(username);
                                ref.setValue(userDataDB);

                                Toast.makeText(CreateUserPage.this, "Account Created Successfully.", Toast.LENGTH_SHORT).show();

                                Intent myCreateIntent = new Intent(CreateUserPage.this, LoginPage.class);
                                startActivity(myCreateIntent);
                            }
                            else {
                                Toast.makeText(CreateUserPage.this, "Username must be within 20 characters! and password more than 1", Toast.LENGTH_SHORT).show();
                            }
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
}

