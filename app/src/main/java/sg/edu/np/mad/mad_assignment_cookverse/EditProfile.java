package sg.edu.np.mad.mad_assignment_cookverse;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {
    private static Uri imageURI;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private String TAG = "EditProfile";
    private FBHandler fbHandler;
    public String GLOBAL_PREF = "MyPrefs";
    public String DATABASE_VERSION = "MyDatabaseVersion";
    public ArrayList<String> userNameString = new ArrayList<>();
    public EditText passwordAgain;
    public EditText password;
    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "39");
        setContentView(R.layout.activity_edit_profile);
        mStorageRef = FirebaseStorage.getInstance().getReference("images");
        FloatingActionButton editPicButton = findViewById(R.id.editPicButton);
        TextView email = findViewById(R.id.emailAddress);
        EditText editBio = (EditText) findViewById(R.id.editBio);
        imageView = findViewById(R.id.editPic);
        ImageView editCancel = findViewById(R.id.backArrowEditProfile);
        Button editSave = findViewById(R.id.editSave);
        password = (EditText) findViewById(R.id.editPasswordProfile);
        passwordAgain = (EditText) findViewById(R.id.editPasswordProfileAgain);
        EditText passwordLast = (EditText) findViewById(R.id.actualEditPasswordLast);

        sharedPreferences = this.getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        dbHandler = new DBHandler(this, null, null, sharedDBVersion);
        fbHandler = new FBHandler(dbHandler,this);
        String a = LoginPage.mainUser.getPassword();
        initFirebaseAuth();
        /*Intent receivingEnd = getIntent();
        String username = receivingEnd.getStringExtra("Username");
        String bio = receivingEnd.getStringExtra("Bio");
        String userImage = receivingEnd.getStringExtra("Pfp");*/

        email.setText(LoginPage.mainUser.getName());
        editBio.setText(LoginPage.mainUser.getBio());
        if (LoginPage.mainUser.getUserImage() != null){
            new ImageLoadTask(LoginPage.mainUser.getUserImage(), imageView).execute();
        }
        editPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        for (User i:dbHandler.listUser()) {
            if (!i.getName().equals(LoginPage.mainUser.getName())){
                userNameString.add(i.getName());
            }
        }

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!passwordAgain.getText().toString().equals("")){
                    if (!passwordAgain.getText().toString().equals(passwordLast.getText().toString())){
                        passwordLast.setError("New password does not match");
                        passwordLast.requestFocus();
                    }
                    else if (passwordAgain.getText().toString().length() < 6){
                        passwordAgain.setError("Password is too weak");
                        passwordAgain.requestFocus();
                    }
                    else if (!password.getText().toString().equals(LoginPage.mainUser.getPassword())){
                        password.setError("Old password does not match");
                        password.requestFocus();
                    }
                    else {
                        updateUser(editBio);
                        updateUserPass();
                        Intent intent = new Intent();
                        setResult(123, intent);
                        finish();
                    }
                }
                else{
                    updateUser(editBio);
                    Intent intent = new Intent();
                    setResult(123, intent);
                    finish();
                }
            }
        });

        editCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageURI = data.getData();

            imageView.setImageURI(imageURI);
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = EditProfile.this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void updateUser(EditText editBio){
        if (imageURI != null){
            String fileName = System.currentTimeMillis() + "." + getFileExtension(imageURI);
            StorageReference fileReference = mStorageRef.child(fileName);
            fileReference.putFile(imageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            /*Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgress.setProgress(0);
                                }
                            }, 5000);*/
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png' in uri
                                    LoginPage.mainUser.setUserImage(uri.toString());
                                    LoginPage.mainUser.setBio(editBio.getText().toString());
                                    fbHandler.addUpdateUser(LoginPage.mainUser);
                                    dbHandler.updateUser(LoginPage.mainUser);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    //mProgressBar.setProgress((int) progress);

                }
            });
        }
        else {
            LoginPage.mainUser.setBio(editBio.getText().toString());
            fbHandler.addUpdateUser(LoginPage.mainUser);
            dbHandler.updateUser(LoginPage.mainUser);
        }
    }
    public void updateUserPass(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        final String email = mUser.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email, password.getText().toString());
        mAuth.signInWithEmailAndPassword(email, password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mUser = mAuth.getCurrentUser();
                if (task.isSuccessful()){
                    mUser.updatePassword(passwordAgain.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        LoginPage.mainUser.setPassword(passwordAgain.getText().toString());
                                        fbHandler.addUpdateUser(LoginPage.mainUser);
                                        dbHandler.updateUser(LoginPage.mainUser);
                                        Log.d("EditProfile", "PW updated");
                                    }
                                    else{
                                        Log.d("EditProfile", "PW failed");
                                        Toast.makeText(EditProfile.this, "Fail to update password", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else{
                    Log.d("EditProfile", "Error auth");
                }
            }
        });
    }
    private void initFirebaseAuth() {
        //create firebase member ver
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

}