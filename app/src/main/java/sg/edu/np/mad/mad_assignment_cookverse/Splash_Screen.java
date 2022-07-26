package sg.edu.np.mad.mad_assignment_cookverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Splash_Screen extends AppCompatActivity {
    public String GLOBAL_PREF = "MyPrefs";
    public String DATABASE_VERSION = "MyDatabaseVersion";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Context context = this;

        sharedPreferences = getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        sharedDBVersion += 1;
        DBHandler dbHandler = new DBHandler(this, null, null, sharedDBVersion);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DATABASE_VERSION, sharedDBVersion);
        editor.apply();
        FBHandler fbHandler = new FBHandler(dbHandler);
        fbHandler.retrieveFBUserData();
        fbHandler.retrieveFBRecipeData(context);
    }
}