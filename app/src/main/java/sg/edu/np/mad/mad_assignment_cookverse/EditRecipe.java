package sg.edu.np.mad.mad_assignment_cookverse;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EditRecipe extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonCreateRecipe;
    private ImageView mImageView;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private Button addIngred;
    private Button addStep;
    private Button addCuisi;
    private LinearLayout ingredList;
    private LinearLayout stepList;
    private LinearLayout cuisiList;
    private FBHandler fbHandler;
    public String GLOBAL_PREF = "MyPrefs";
    public String DATABASE_VERSION = "MyDatabaseVersion";
    public Recipe recipe;
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        sharedPreferences = this.getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        dbHandler = new DBHandler(this, null, null, sharedDBVersion);
        fbHandler = new FBHandler(dbHandler, this);
        mButtonChooseImage = findViewById(R.id.buttonImage2);
        mButtonCreateRecipe = findViewById(R.id.buttonCreateRecipe2);
        mImageView = findViewById(R.id.CreateImage2);
        addIngred = findViewById(R.id.addIngredientsButton);
        addStep = findViewById(R.id.addStepsButton);
        addCuisi = findViewById(R.id.addCuisineButton);
        ingredList = findViewById(R.id.ingredList2);
        stepList = findViewById(R.id.stepList2);
        cuisiList = findViewById(R.id.cuisineList2);
        ImageView backButton = findViewById(R.id.backArrowEditRecipe);
        TextView name = findViewById(R.id.editRecipeName2);
        TextView description = findViewById(R.id.editRecipeDescription2);
        TextView duration = findViewById(R.id.editRecipeDuration2);
        TextView servings = findViewById(R.id.editRecipeServings2);
        ImageView trash = findViewById(R.id.trashbin);
        mStorageRef = FirebaseStorage.getInstance().getReference("images");
        Intent getintent = getIntent();
        String recipeID = getintent.getStringExtra("rid");
        recipe = dbHandler.findRecipe(recipeID);
        name.setText(recipe.getName());
        new ImageLoadTask(recipe.getRecipeimage(), mImageView).execute();
        description.setText(recipe.getDescription());
        duration.setText(String.valueOf(recipe.getDuration()));
        servings.setText(String.valueOf(recipe.getServings()));
        startIngredientsView();
        startStepsView();
        startCuisiView();
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Delete this recipe?")
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LoginPage.mainUser.getCreatedList().remove(recipeID);
                                dbHandler.updateUser(LoginPage.mainUser);
                                dbHandler.deleteRecipe(recipe);
                                fbHandler.removeRecipe(recipe);
                                fbHandler.addUpdateUser(LoginPage.mainUser);
                                Intent intent = new Intent(EditRecipe.this, MainFragment.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Delete Recipe");
                alert.show();
            }
        });
        //if (recipe.getIngredientsList().)
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        /*Bitmap bm = doInBackground();
        mImageUri = getImageUri(this, bm);*/
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Button that processes user's input to create recipe object to be added to online and local database
        mButtonCreateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Instantiating recipe object and setting values from user's input
                recipe.setName(name.getText().toString());
                recipe.setDescription(description.getText().toString());
                try {
                    recipe.setDuration(Integer.parseInt(duration.getText().toString()));
                    recipe.setServings(Integer.parseInt(servings.getText().toString()));
                } catch(NumberFormatException e) {
                    System.out.println("Could not parse " + e);
                }
                recipe.setUid(LoginPage.mainUser.getName());

                List<String> RecipeIngred = new ArrayList<>();
                for (int i = 0; i < ingredList.getChildCount(); i++){
                    try {
                        EditText et = (EditText) ingredList.getChildAt(i).findViewById(R.id.addIngredient);
                        if (!et.getText().toString().equals("") || et.getText().toString().isEmpty()){
                            Log.v("Main", et.getText().toString());
                            RecipeIngred.add(et.getText().toString());
                        }
                    }
                    catch(Exception e){
                        Log.v("Main", "fail");
                    }
                }
                recipe.setIngredientsList(RecipeIngred);

                List<String> RecipeStep = new ArrayList<>();
                for (int i = 0; i < stepList.getChildCount(); i++){
                    try {
                        EditText et = (EditText) stepList.getChildAt(i).findViewById(R.id.addSteps);
                        if (!et.getText().toString().equals("") || et.getText().toString().isEmpty()){
                            Log.v("Main", et.getText().toString());
                            RecipeStep.add(et.getText().toString());
                        }
                    }
                    catch(Exception e){
                        Log.v("Main", "fail");
                    }
                }
                recipe.setStepsList(RecipeStep);

                List<String> RecipeCuisi = new ArrayList<>();
                for (int i = 0; i < cuisiList.getChildCount(); i++){
                    try {
                        EditText et = (EditText) cuisiList.getChildAt(i).findViewById(R.id.addCuisine);
                        if (!et.getText().toString().equals("") || et.getText().toString().isEmpty()){
                            Log.v("Main", et.getText().toString());
                            RecipeCuisi.add(et.getText().toString());
                        }
                    }
                    catch(Exception e){
                        Log.v("Main", "fail");
                    }
                }
                if (!RecipeCuisi.get(0).equals("")) {
                    recipe.setCuisineList(RecipeCuisi);
                }
                if (recipe.getIngredientsList().size() > 1 && recipe.getIngredientsList().get(0).equals("")){
                    recipe.getIngredientsList().remove(recipe.getIngredientsList().get(0));
                }
                if (recipe.getStepsList().size() >1 && recipe.getStepsList().get(0).equals("")){
                    recipe.getStepsList().remove(recipe.getStepsList().get(0));
                }
                //Checks to see if user has entered the minimum required inputs
                if (!recipe.getName().equals("") && !recipe.getIngredientsList().get(0).equals("")
                        && !recipe.getStepsList().get(0).equals("")) {
                    //Calls create recipe method to send recipe object to database
                    createRecipe(recipe);
                    Intent myIntent = new Intent(EditRecipe.this, MainFragment.class);
                    startActivity(myIntent);
                }
                else{
                    Toast.makeText(EditRecipe.this, "Please include all required fields and an image",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button that adds additional EditText/Layout to listview to allow user's to add more ingredients
        addIngred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIngredientView();
            }
        });

        //Button that adds additional EditText/Layout to listview to allow user's to add more steps
        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStepView();
            }
        });

        //Button that adds additional EditText/Layout to listview to allow user's to add more cuisines
        addCuisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCuisineView();
            }
        });
    }

    //Allows user's to choose image from their files
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
            mImageUri = data.getData();

            mImageView.setImageURI(mImageUri);
        }
    }

    //Gets image's file type as string
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    //Adds new ingredient view to list view to allow user's to add more ingredients
    private void addIngredientView(){
        final View ingredView = getLayoutInflater().inflate(R.layout.add_ingredients, null, false);
        Button delete = ingredView.findViewById(R.id.deleteIngredient); //button that deletes ingredient view
        Button add = ingredView.findViewById(R.id.addIngredientsButton); //button that adds ingredient view
        delete.setVisibility(View.VISIBLE);
        addIngred.setVisibility(View.INVISIBLE);

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                removeIngredView(ingredView);
                if (ingredList.getChildCount() == 1){
                    addIngred.setVisibility(View.VISIBLE);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addIngredientView();
            }
        });
        ingredList.addView(ingredView);
    }

    //Removes ingredient view from list view
    private void removeIngredView(View view){
        ingredList.removeView(view);
    }

    private void addStepView(){
        final View stepView = getLayoutInflater().inflate(R.layout.add_steps, null, false);
        Button delete = stepView.findViewById(R.id.deleteStep);
        Button add = stepView.findViewById(R.id.addStepsButton);
        delete.setVisibility(View.VISIBLE);
        addStep.setVisibility(View.INVISIBLE);

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                removeStepView(stepView);
                if (stepList.getChildCount() == 1){
                    addStep.setVisibility(View.VISIBLE);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addStepView();
            }
        });
        stepList.addView(stepView);
    }

    private void removeStepView(View view){
        stepList.removeView(view);
    }

    private void addCuisineView(){
        final View cuisiView = getLayoutInflater().inflate(R.layout.add_cuisine, null, false);
        Button delete = cuisiView.findViewById(R.id.deleteCuisine);
        Button add = cuisiView.findViewById(R.id.addCuisineButton);
        delete.setVisibility(View.VISIBLE);
        addCuisi.setVisibility(View.INVISIBLE);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                removeCuisiView(cuisiView);
                if (cuisiList.getChildCount() == 1){
                    addCuisi.setVisibility(View.VISIBLE);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addCuisineView();
            }
        });
        cuisiList.addView(cuisiView);
    }
    private void startCuisiView(){
        if (recipe.getCuisineList() == null){
        }
        else {
            for (String i : recipe.getCuisineList()) {
                final View cuisiView = getLayoutInflater().inflate(R.layout.add_cuisine, null, false);
                Button delete = cuisiView.findViewById(R.id.deleteCuisine);
                Button add = cuisiView.findViewById(R.id.addCuisineButton);
                TextView textView = cuisiView.findViewById(R.id.addCuisine);
                textView.setText(i);
                delete.setVisibility(View.VISIBLE);
                add.setVisibility(View.INVISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeCuisiView(cuisiView);
                        if (cuisiList.getChildCount() == 1){
                            addCuisi.setVisibility(View.VISIBLE);
                        }
                    }
                });
                cuisiList.addView(cuisiView);
            }
        }
    }
    private void startStepsView(){
        if (recipe.getStepsList() == null){
        }
        else {
            for (String i : recipe.getStepsList()) {
                final View stepView = getLayoutInflater().inflate(R.layout.add_steps, null, false);
                Button delete = stepView.findViewById(R.id.deleteStep);
                Button add = stepView.findViewById(R.id.addStepsButton);
                TextView textView = stepView.findViewById(R.id.addSteps);
                textView.setText(i);
                delete.setVisibility(View.VISIBLE);
                add.setVisibility(View.INVISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeStepView(stepView);
                        if (stepList.getChildCount() == 1){
                            addStep.setVisibility(View.VISIBLE);
                        }
                    }
                });
                stepList.addView(stepView);
            }
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public Bitmap doInBackground() {
        try {
            InputStream input = new URL(recipe.getRecipeimage()).openStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void startIngredientsView(){
        if (recipe.getIngredientsList() == null){
        }
        else {
            for (String i : recipe.getIngredientsList()) {
                final View ingredientsView = getLayoutInflater().inflate(R.layout.add_ingredients, null, false);
                Button delete = ingredientsView.findViewById(R.id.deleteIngredient);
                Button add = ingredientsView.findViewById(R.id.addIngredientsButton);
                TextView textView = ingredientsView.findViewById(R.id.addIngredient);
                textView.setText(i);
                delete.setVisibility(View.VISIBLE);
                add.setVisibility(View.INVISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeIngredView(ingredientsView);
                        if (ingredList.getChildCount() == 1){
                            addIngred.setVisibility(View.VISIBLE);
                        }
                    }
                });
                ingredList.addView(ingredientsView);
            }
        }
    }
    private void removeCuisiView(View view){
        cuisiList.removeView(view);
    }

    //Method that receives recipe object to be added to local database and firebase
    private void createRecipe(Recipe r){
        sharedPreferences = this.getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        dbHandler = new DBHandler(this, null, null, sharedDBVersion);
        fbHandler = new FBHandler(dbHandler,this);
        ArrayList<String> al = new ArrayList<>();

        //checking to see if there is an input image
        if (mImageUri != null){
            //setting image name and storage reference
            String fileName = System.currentTimeMillis() + "." + getFileExtension(mImageUri);
            StorageReference fileReference = mStorageRef.child(fileName);

            //uploading image to storage
            fileReference.putFile(mImageUri)
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
                                    r.setRecipeimage(uri.toString());
                                    Log.v("MAIN", uri.toString());
                                    //Adds recipe to firebase and local database and updates user object
                                    dbHandler.updateRecipe(r);
                                    fbHandler.updateRecipe(r);

                                    Toast.makeText(EditRecipe.this, "Recipe Created Successfully.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditRecipe.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            //mProgressBar.setProgress((int) progress);
                        }
                    });
        }
        else{
            dbHandler.updateRecipe(r);
            fbHandler.updateRecipe(r);
            Toast.makeText(EditRecipe.this, "No image for this recipe",Toast.LENGTH_SHORT).show();
        }
    }

}