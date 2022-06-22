package sg.edu.np.mad.mad_assignment_cookverse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class createUserPage extends AppCompatActivity {
    public String TAG = "Create User Page";
    public String MY_USERNAME = "MyUsername";
    public String MY_PASSWORD = "MyPassword";
    DBHandler dbHandler = new DBHandler(this,null,null,1);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creater_user_page);

        EditText myCreateUsername = findViewById(R.id.editUsername);
        EditText myCreatePassword = findViewById(R.id.editPassword);

        Button myButtonCreate = findViewById(R.id.buttonCreate);
        myButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User userDBData = dbHandler.findUserByName(myCreateUsername.getText().toString());
                if (userDBData == null) {
                    User userDataDB = new User();
                    userDataDB.setName(myCreateUsername.getText().toString());
                    userDataDB.setPassword((myCreatePassword.getText().toString()));
                    /*userDataDB.setLikedList(null);
                    userDataDB.setCreatedList(null);*/
                    dbHandler.addUser(userDataDB);
                    Toast.makeText(createUserPage.this, "Account Created Successfully.", Toast.LENGTH_SHORT).show();

                    Intent myCreateIntent = new Intent(createUserPage.this, MainActivity.class);
                    startActivity(myCreateIntent);
                }
                else {
                    Toast.makeText(createUserPage.this, "User Already Exists! Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button myButtonCancel = findViewById(R.id.buttonCancel);
        myButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(createUserPage.this,"Creation Cancelled",Toast.LENGTH_SHORT).show();
                Intent myCancelIntent = new Intent(createUserPage.this,MainActivity.class);
                startActivity(myCancelIntent);
            }
        });

    }
}

