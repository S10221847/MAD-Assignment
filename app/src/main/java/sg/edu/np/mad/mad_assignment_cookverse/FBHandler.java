package sg.edu.np.mad.mad_assignment_cookverse;

import android.content.Context;
import android.content.Intent;
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

    public FBHandler(DBHandler dbHandler){
        this.dbHandler = dbHandler;
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
        query.addValueEventListener(eventListener);
    }

    public void retrieveFBRecipeData(){
        Query query = FirebaseDatabase.getInstance().getReference().child("Recipes").limitToFirst(10);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Recipe r = snapshot.getValue(Recipe.class);
                        dbHandler.addRecipe(r);
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

    public void addRecipe(Recipe r){
        String rid = "";
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rid = rootRef.child("Recipes").push().getKey();
        r.setRid(rid);
        DatabaseReference ref = rootRef.child("Recipes").child(rid);
        ref.setValue(r);

        LoginPage.mainUser.getCreatedList().add(rid);
        addUpdateUser(LoginPage.mainUser);
    }
    public void updateRecipe(Recipe r){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String rid = r.getRid();
        DatabaseReference ref = rootRef.child("Recipes").child(rid);
        ref.setValue(r);
    }

    public void addUpdateUser(User u){ //both adds and updates user data within firebase
        String username = u.getName();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Accounts").child(username);
        ref.setValue(u);
    }

    //delete User and Recipe data methods
}
