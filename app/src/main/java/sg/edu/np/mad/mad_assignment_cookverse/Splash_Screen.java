package sg.edu.np.mad.mad_assignment_cookverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Splash_Screen extends AppCompatActivity {
    public String GLOBAL_PREF = "MyPrefs";
    public String DATABASE_VERSION = "MyDatabaseVersion";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Context context = this;
        ProgressBar progressBar = findViewById(R.id.progressBar);

        Query query = FirebaseDatabase.getInstance().getReference().child("Database_Version");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int FBVersion = 1;
                sharedPreferences = getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
                int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
                if (dataSnapshot.exists()) {
                    FBVersion = (int) dataSnapshot.getValue(Integer.class);
                }
                if (FBVersion != sharedDBVersion) {
                    progressBar.setVisibility(View.VISIBLE);
                    sharedDBVersion = FBVersion;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DATABASE_VERSION, sharedDBVersion);
                    editor.apply();
                    DBHandler dbHandler = new DBHandler(context, null, null, sharedDBVersion);
                    FBHandler fbHandler = new FBHandler(dbHandler,context);
                    fbHandler.retrieveFBUserData();
                    fbHandler.retrieveFBRecipeData();
                }
                else{
                    Intent myIntent = new Intent(context, LoginPage.class);
                    context.startActivity(myIntent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("Main", error.getMessage());
            }
        };
        query.addListenerForSingleValueEvent(eventListener);
    }
}