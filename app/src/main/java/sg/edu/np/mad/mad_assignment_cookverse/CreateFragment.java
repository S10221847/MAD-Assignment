package sg.edu.np.mad.mad_assignment_cookverse;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateFragment() {
        // Required empty public constructor
    }

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
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        mButtonChooseImage = view.findViewById(R.id.buttonImage);
        mButtonCreateRecipe = view.findViewById(R.id.buttonCreateRecipe);
        mImageView = view.findViewById(R.id.CreateImage);
        addIngred = view.findViewById(R.id.addIngredientsButton);
        addStep = view.findViewById(R.id.addStepsButton);
        addCuisi = view.findViewById(R.id.addCuisineButton);
        ingredList = view.findViewById(R.id.ingredList);
        stepList = view.findViewById(R.id.stepList);
        cuisiList = view.findViewById(R.id.cuisineList);

        //Intent receivingEnd = getActivity().getIntent();
        //String currentUsername = receivingEnd.getStringExtra("username");

        TextView name = view.findViewById(R.id.editRecipeName);
        TextView description = view.findViewById(R.id.editRecipeDescription);
        //TextView ingredients = view.findViewById(R.id.addIngredient);
        //TextView steps = view.findViewById(R.id.addSteps);
        TextView duration = view.findViewById(R.id.editRecipeDuration);
        //TextView cuisine = view.findViewById(R.id.addCuisine);
        TextView servings = view.findViewById(R.id.editRecipeServings);

        //Getting storage reference for images
        mStorageRef = FirebaseStorage.getInstance().getReference("images");

        //Button that calls openFileChooser method which allows for images to be chosen
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        //Button that calls openFileChooser method which allows for images to be chosen
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        //Button that processes user's input to create recipe object to be added to online and local database
        mButtonCreateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Instantiating recipe object and setting values from user's input
                Recipe recipe = new Recipe();
                recipe.setName(name.getText().toString());
                recipe.setDescription(description.getText().toString());
                try {
                    recipe.setDuration(Integer.parseInt(duration.getText().toString()));
                    recipe.setServings(Integer.parseInt(servings.getText().toString()));
                } catch(NumberFormatException e) {
                    System.out.println("Could not parse " + e);
                }
                recipe.setUid(LoginPage.mainUser.getName());
                recipe.setNooflikes(0);

                List<String> RecipeIngred = new ArrayList<>();
                for (int i = 0; i < ingredList.getChildCount(); i++){
                    try {
                        EditText et = (EditText) ingredList.getChildAt(i).findViewById(R.id.addIngredient);
                        Log.v("Main", et.getText().toString());
                        RecipeIngred.add(et.getText().toString());
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
                        Log.v("Main", et.getText().toString());
                        RecipeStep.add(et.getText().toString());
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
                        Log.v("Main", et.getText().toString());
                        RecipeCuisi.add(et.getText().toString());
                    }
                    catch(Exception e){
                        Log.v("Main", "fail");
                    }
                }
                if (!RecipeCuisi.get(0).equals("")) {
                    recipe.setCuisineList(RecipeCuisi);
                }

                //Checks to see if user has entered the minimum required inputs
                if (!recipe.getName().equals("") && mImageUri != null && !recipe.getIngredientsList().get(0).equals("")
                && !recipe.getStepsList().get(0).equals("")) {
                    //Calls create recipe method to send recipe object to database
                    createRecipe(recipe);
                    Intent myIntent = new Intent(getActivity(), MainFragment.class);
                    getActivity().startActivity(myIntent);
                }
                else{
                    Toast.makeText(getActivity(), "Please include all required fields and an image",Toast.LENGTH_SHORT).show();
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

        return view;
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
        ContentResolver cR = getActivity().getContentResolver();
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

    private void removeCuisiView(View view){
        cuisiList.removeView(view);
    }

    //Method that receives recipe object to be added to local database and firebase
    private void createRecipe(Recipe r){
        sharedPreferences = this.getActivity().getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        dbHandler = new DBHandler(getActivity(), null, null, sharedDBVersion);
        fbHandler = new FBHandler(dbHandler,getActivity());
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
                                    al.add(r.getRid());
                                    if (LoginPage.mainUser.getCreatedList() == null){
                                        LoginPage.mainUser.setCreatedList(al);
                                    }
                                    else{
                                        LoginPage.mainUser.getCreatedList().add(r.getRid());
                                    }

                                    //Adds recipe to firebase and local database and updates user object
                                    dbHandler.updateUser(LoginPage.mainUser);
                                    //dbHandler.addRecipe(r);
                                    fbHandler.addRecipe(r);

                                    Toast.makeText(getActivity(), "Recipe Created Successfully.", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "No image for this recipe",Toast.LENGTH_SHORT).show();
        }
    }
}