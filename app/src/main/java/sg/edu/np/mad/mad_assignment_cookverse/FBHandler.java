package sg.edu.np.mad.mad_assignment_cookverse;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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

    public void retrieveFBRecipeData(){
        Query query = FirebaseDatabase.getInstance().getReference().child("Recipes");//.limitToFirst(50);
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

    public void addRecipe(Recipe r){
        String rid = "";
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rid = rootRef.child("Recipes").push().getKey();
        r.setRid(rid);
        DatabaseReference ref = rootRef.child("Recipes").child(rid);
        ref.setValue(r);
        updateVersion();

        LoginPage.mainUser.getCreatedList().add(rid);
        addUpdateUser(LoginPage.mainUser);
    }
    public void updateRecipe(Recipe r){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String rid = r.getRid();
        DatabaseReference ref = rootRef.child("Recipes").child(rid);
        ref.setValue(r);
        updateVersion();
    }

    public void addUpdateUser(User u){ //both adds and updates user data within firebase
        String username = u.getName();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Accounts").child(username);
        ref.setValue(u);
        updateVersion();
    }

    public void removeRecipe(Recipe r){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String rid = r.getRid();
        DatabaseReference ref = rootRef.child("Recipes").child(rid);
        ref.removeValue();
        updateVersion();
    }

    public void removeUser(User u){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String username = u.getName();
        DatabaseReference ref = rootRef.child("Accounts").child(username);
        ref.removeValue();
        updateVersion();
    }

    public void updateVersion(){
        sharedPreferences = context.getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        sharedDBVersion += 1;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DATABASE_VERSION, sharedDBVersion);
        editor.apply();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Database_Version");
        ref.setValue(sharedDBVersion);
    }

}
