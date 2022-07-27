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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "39");
        setContentView(R.layout.activity_edit_profile);
        mStorageRef = FirebaseStorage.getInstance().getReference("images");
        FloatingActionButton editPicButton = findViewById(R.id.editPicButton);
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editBio = (EditText) findViewById(R.id.editBio);
        imageView = findViewById(R.id.editPic);
        Button editCancel = findViewById(R.id.editCancel);
        Button editSave = findViewById(R.id.editSave);

        sharedPreferences = this.getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        dbHandler = new DBHandler(this, null, null, sharedDBVersion);
        fbHandler = new FBHandler(dbHandler);

        /*Intent receivingEnd = getIntent();
        String username = receivingEnd.getStringExtra("Username");
        String bio = receivingEnd.getStringExtra("Bio");
        String userImage = receivingEnd.getStringExtra("Pfp");*/

        editName.setText(LoginPage.mainUser.getName());
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

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(editName, editBio);
                finish();
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
    private void updateUser(EditText editName, EditText editBio){
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
                                    LoginPage.mainUser.setName(editName.getText().toString());
                                    LoginPage.mainUser.setBio(editBio.getText().toString());
                                    fbHandler.addUpdateUser(LoginPage.mainUser);
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
            LoginPage.mainUser.setName(editName.getText().toString());
            LoginPage.mainUser.setBio(editBio.getText().toString());
            fbHandler.addUpdateUser(LoginPage.mainUser);
        }
    }
}