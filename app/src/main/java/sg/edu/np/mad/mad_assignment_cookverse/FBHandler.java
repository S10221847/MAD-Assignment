package sg.edu.np.mad.mad_assignment_cookverse;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FBHandler {
    private String TAG = "FBHandler";
    private DBHandler dbHandler;
    private Context context;
    public String GLOBAL_PREF = "MyPrefs";
    public String DATABASE_VERSION = "MyDatabaseVersion";
    SharedPreferences sharedPreferences;

    public FBHandler(DBHandler dbHandler, Context context){
        this.dbHandler = dbHandler;
        this.context = context;
    }

    //Retrieves accounts from firebase and adds them to local database
    public void retrieveFBUserData(){
        Query query = FirebaseDatabase.getInstance().getReference().child("Accounts");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User u = snapshot.getValue(User.class);
                        dbHandler.addUser(u);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        };
        query.addListenerForSingleValueEvent(eventListener);
    }

    //Retrieves recipes from firebase and adds them to local database with intent to LoginPage, used on app_launch in splash screen
    public void retrieveFBRecipeData(){
        Query query = FirebaseDatabase.getInstance().getReference().child("Recipes");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Recipe r = snapshot.getValue(Recipe.class);
                        dbHandler.addRecipe(r);
                    }
                }
                Intent myIntent = new Intent(context, LoginPage.class);
                context.startActivity(myIntent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        };
        query.addListenerForSingleValueEvent(eventListener);
    }

    //Retrieves recipes from firebase and adds them to local database with intent to MainFragment
    public void refreshFBRecipeData(){
        Query query = FirebaseDatabase.getInstance().getReference().child("Recipes");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Recipe r = snapshot.getValue(Recipe.class);
                        dbHandler.addRecipe(r);
                    }
                }
                Intent myIntent = new Intent(context, MainFragment.class);
                context.startActivity(myIntent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        };
        query.addListenerForSingleValueEvent(eventListener);
    }

    //Adds recipe to Firebase and local database whilst updating User's CreatedList, used in CreateFragment
    public void addRecipe(Recipe r){
        String rid = "";
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rid = rootRef.child("Recipes").push().getKey();
        r.setRid(rid);
        DatabaseReference ref = rootRef.child("Recipes").child(rid);
        ref.setValue(r);
        dbHandler.addRecipe(r);

        LoginPage.mainUser.getCreatedList().add(rid);
        addUpdateUser(LoginPage.mainUser);
    }

    //Updates recipe in Firebase with given recipe ID
    public void updateRecipe(Recipe r){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String rid = r.getRid();
        DatabaseReference ref = rootRef.child("Recipes").child(rid);
        ref.setValue(r);
        updateVersion();
    }

    //Both adds and updates user data within Firebase
    public void addUpdateUser(User u){
        String username = u.getName();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Accounts").child(username);
        ref.setValue(u);
        updateVersion();
    }

    //Removes given recipe from Firebase
    public void removeRecipe(Recipe r){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String rid = r.getRid();
        DatabaseReference ref = rootRef.child("Recipes").child(rid);
        ref.removeValue();
        updateVersion();
    }

    //Removes given user from Firebase
    public void removeUser(User u){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String username = u.getName();
        DatabaseReference ref = rootRef.child("Accounts").child(username);
        ref.removeValue();
        updateVersion();
    }

    //Called to update Database Version within database for next launch/refresh
    public void updateVersion(){
        sharedPreferences = context.getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        sharedDBVersion += 1;
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putInt(DATABASE_VERSION, sharedDBVersion);
        //editor.apply();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Database_Version");
        ref.setValue(sharedDBVersion);
    }

}
