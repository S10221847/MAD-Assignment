package sg.edu.np.mad.mad_assignment_cookverse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateUserPage extends AppCompatActivity {
    public String TAG = "Create User Page";
    public String MY_USERNAME = "MyUsername";
    public String MY_PASSWORD = "MyPassword";
    DBHandler dbHandler = new DBHandler(this,null,null,1);

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
                User userDBData = dbHandler.findUserByName(myCreateUsername.getText().toString());
                if (myCreateUsername.getText().toString().length() <= 20){
                    if (userDBData == null) {
                        User userDataDB = new User();
                        userDataDB.setName(myCreateUsername.getText().toString());
                        userDataDB.setPassword((myCreatePassword.getText().toString()));
                    /*userDataDB.setLikedList(null);
                    userDataDB.setCreatedList(null);*/
                        dbHandler.addUser(userDataDB);
                        Toast.makeText(CreateUserPage.this, "Account Created Successfully.", Toast.LENGTH_SHORT).show();

                        Intent myCreateIntent = new Intent(CreateUserPage.this, LoginPage.class);
                        startActivity(myCreateIntent);
                    }
                    else {
                        Toast.makeText(CreateUserPage.this, "User Already Exists! Try again!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(CreateUserPage.this, "Username must be within 20 characters!", Toast.LENGTH_SHORT).show();
                }
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

