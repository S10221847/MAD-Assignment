package sg.edu.np.mad.mad_assignment_cookverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class LoginPage extends AppCompatActivity {
    public String TAG = "Main Activity";
    public String MY_USERNAME = "MyUsername";
    public String MY_PASSWORD = "MyPassword";
    DBHandler dbHandler = new DBHandler(this, null, null, 1);

    Field field;

    {
        try {
            field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

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

                if(isValidCredentials(etMyUserName.getText().toString(), etMyPassword.getText().toString())){
                    Intent myIntent = new Intent(LoginPage.this, MainFragment.class);
                    myIntent.putExtra("username", etMyUserName.getText().toString());
                    startActivity(myIntent);
                    Toast.makeText(LoginPage.this,"Valid",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginPage.this, "Invalid Login! Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean isValidCredentials(String username, String password){
        User userDBData = dbHandler.findUserByName(username);
        if(userDBData == null){
            Toast.makeText(LoginPage.this, "User Does not Exist!", Toast.LENGTH_SHORT);

        }
        else{
            if(userDBData.getName().equals(username) && userDBData.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}