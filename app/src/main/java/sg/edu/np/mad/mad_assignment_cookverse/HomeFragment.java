package sg.edu.np.mad.mad_assignment_cookverse;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment implements RecyclerViewInterface,UserRecyclerViewInterface,PopRecyclerViewInterface,VegetarianInterface,HealthyInterface {
    public String TAG = "Home page";
    OnlineRecipesAdapter oAdapter;
    UserCreatedAdapter uAdapter;
    PopRecipeAdapter pAdapter;
    VegetarianAdapter vAdapter;
    HealthyAdapter hAdapter;
    RecyclerViewInterface RecyclerViewInterface;
    UserRecyclerViewInterface UserRecyclerViewInterface;
    PopRecyclerViewInterface popRecyclerViewInterface;
    VegetarianInterface vegetarianInterface;
    HealthyInterface healthyInterface;
    List<Recipe> dataOriginal;
    public String GLOBAL_PREF = "MyPrefs";
    public String DATABASE_VERSION = "MyDatabaseVersion";
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;
    List<Recipe>rList=new ArrayList<>();
    public static List<Recipe>oList=new ArrayList<>();
    public static List<Recipe>uList=new ArrayList<>();
    public static List<Recipe>pList=new ArrayList<>();
    public static List<Recipe>vList=new ArrayList<>();
    public static List<Recipe>hList=new ArrayList<>();
    public static int seed = new Random().nextInt();;
    public static Boolean cameFromRecipe = false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    //Hello

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
        RecyclerViewInterface rvi = this;
        UserRecyclerViewInterface urvi = this;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Commented code below as it is to add random recipes from api, only uncomment when want to add more.

        /*RequestQueue queue = Volley.newRequestQueue(getActivity()); //Code to install random recipes from api into firebase
        String url = "https://api.spoonacular.com/recipes/random?apiKey=af813f5ed72840b8883afa9debd61d05&number=20";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Recipe r = new Recipe();
                        String recipeName; //
                        String recipeDesc; //
                        int duration; //
                        String recipeID; //
                        String userID; //
                        List<String> cuisineList = new ArrayList<String>(); //
                        List<String> ingredList = new ArrayList<String>(); //
                        List<String> stepsList = new ArrayList<String>(); //
                        int likes_count = 0; //
                        String recipeImage; //
                        int servings;
                        boolean vegetarian; //
                        boolean vegan; //
                        boolean glutenFree; //
                        boolean dairyFree; //
                        boolean healthy; //
                        boolean cheap; //
                        boolean popular; //
                        List<Recipe> recipeList = new ArrayList<Recipe>();
                        List<Recipe>rList=new ArrayList<>();

                        try {
                            JSONArray recipe = response.getJSONArray("recipes"); //get recipes array containing all the random recipes
                            for (int i = 0; i < recipe.length(); i++) {   //for loop through all the recipes in the recipes array
                                r=new Recipe();
                                JSONObject recipe_index = recipe.getJSONObject(i); //assign 1 recipe object to a variable
                                vegetarian = recipe_index.getBoolean("vegetarian"); //vegetarian
                                r.setVegetarian(vegetarian);

                                vegan = recipe_index.getBoolean("vegan"); //vegan
                                r.setVegan(vegan);

                                glutenFree = recipe_index.getBoolean("glutenFree"); //glutenFree
                                r.setGlutenFree(glutenFree);

                                dairyFree = recipe_index.getBoolean("dairyFree"); //dairyFree
                                r.setDairyFree(dairyFree);

                                healthy = recipe_index.getBoolean("veryHealthy"); //Healthy
                                r.setHealthy(healthy);

                                cheap = recipe_index.getBoolean("cheap"); //Cheap
                                r.setCheap(cheap);

                                popular = recipe_index.getBoolean("veryPopular"); //Popular
                                r.setPopular(popular);

                                recipeName = recipe_index.getString("title"); //Recipe title
                                r.setName(recipeName);

                                recipeDesc = recipe_index.getString("summary"); //Recipe Description
                                recipeDesc=recipeDesc.replaceAll("<b>","");
                                recipeDesc=recipeDesc.replaceAll("</b>","");
                                recipeDesc=recipeDesc.replaceAll("<a href=","");
                                recipeDesc=recipeDesc.replaceAll("</a>","");
                                r.setDescription(recipeDesc);

                                duration = recipe_index.getInt("readyInMinutes"); //Recipe Duration
                                r.setDuration(duration);

                                int recipeID_temp = recipe_index.getInt("id"); //Recipe ID
                                recipeID = String.valueOf(recipeID_temp);
                                r.setRid(recipeID);

                                userID = null; //User ID default null if found from the web api
                                r.setUid(userID);

                                JSONArray cuisineList_temp = recipe_index.getJSONArray("cuisines"); //Recipe cuisines list
                                cuisineList = new ArrayList<String>();
                                if (cuisineList_temp != null) {
                                    for (int x = 0; x < cuisineList_temp.length(); x++) {
                                        cuisineList.add(cuisineList_temp.getString(x));
                                    }

                                }
                                r.setCuisineList(cuisineList);

                                JSONArray ingredArray = recipe_index.getJSONArray("extendedIngredients"); //Get ingredients array
                                ingredList = new ArrayList<String>();
                                for (int a = 0; a < ingredArray.length(); a++) {
                                    JSONObject ingredient = ingredArray.getJSONObject(a); //Get respective ingredient details
                                    String ingredName = ingredient.getString("original"); //Get Name of ingredient
                                    ingredList.add(ingredName);

                                }
                                r.setIngredientsList(ingredList);

                                JSONArray stepsArray = recipe_index.getJSONArray("analyzedInstructions"); //Get steps array
                                stepsList = new ArrayList<String>();
                                JSONObject stepsObjectArray = stepsArray.getJSONObject(0); //Get steps object array
                                JSONArray steps = stepsObjectArray.getJSONArray("steps"); //get steps array in the object array
                                for (int s = 0; s < steps.length(); s++) {
                                    JSONObject temp_step = steps.getJSONObject(s);
                                    String actual_step = temp_step.getString("step");
                                    stepsList.add(actual_step);
                                }
                                r.setStepsList(stepsList);
                                likes_count = 0;
                                r.setNooflikes(likes_count);
                                recipeImage = recipe_index.getString("image");
                                r.setRecipeimage(recipeImage);
                                servings = recipe_index.getInt("servings");
                                r.setServings(servings);
                                //addRecipe(r);
                                String rid = "";
                                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                rid = rootRef.child("Recipes").push().getKey();
                                r.setRid(rid);
                                DatabaseReference ref = rootRef.child("Recipes").child(rid);
                                ref.setValue(r);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR OCCURED", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);*/


        //Call webapi to store online lists into firebase


        //DBHandler
        sharedPreferences = this.getActivity().getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        dbHandler = new DBHandler(getActivity(), null, null, sharedDBVersion);
        dataOriginal = dbHandler.listAllRecipe();

        //RECYCLER VIEW FOR ONLINE RECIPES
        RecyclerView orecyclerView = view.findViewById(R.id.onlinerecRecyclerView);   //instantiate recycler view for ONLINE RECIPES
        orecyclerView.setHasFixedSize(true);
        oList=dbHandler.listOnlineRecipe();
        //RECYCLER VIEW FOR USER CREATED RECIPES (user.id of recipes=null)
        RecyclerView urecyclerView=view.findViewById(R.id.usercreatedRecyclerView);
        urecyclerView.setHasFixedSize(true);
        uList=dbHandler.listUserRecipe();
        RecyclerView precyclerView=view.findViewById(R.id.poprecy);
        precyclerView.setHasFixedSize(true);
        RecyclerView vrecyclerView=view.findViewById(R.id.vrecyclerview);
        vrecyclerView.setHasFixedSize(true);

        RecyclerView hrecyclerView=view.findViewById(R.id.healthyrec);
        hrecyclerView.setHasFixedSize(true);
        for(Recipe h:dataOriginal){
            if(h.isHealthy()==true){
                hList.add(h);
                continue;
            }

        }
        for(Recipe x : dataOriginal){
            if(x.isPopular()==true){
                pList.add(x);
                continue;
            }

        }
        for(Recipe v: dataOriginal){
            if(v.isVegetarian()==true){
                vList.add(v);
                continue;
            }
        }


        if (cameFromRecipe == false){
            seed = new Random().nextInt();
            Collections.shuffle(oList, new Random(seed));
            Collections.shuffle(uList, new Random(seed));
            Collections.shuffle(pList, new Random(seed));
            Collections.shuffle(vList, new Random(seed));
            Collections.shuffle(hList, new Random(seed));
        }
        else{
            Collections.shuffle(oList, new Random(MainFragment.HomeSeed));
            Collections.shuffle(uList, new Random(MainFragment.HomeSeed));
            Collections.shuffle(pList, new Random(MainFragment.HomeSeed));
            Collections.shuffle(vList, new Random(MainFragment.HomeSeed));
            Collections.shuffle(hList, new Random(MainFragment.HomeSeed));
        }
        cameFromRecipe = false;
        oAdapter = new OnlineRecipesAdapter(oList, rvi, dataOriginal);  //ONLINE RECIPE ADAPTER
        LinearLayoutManager oLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        orecyclerView.setLayoutManager(oLayoutManager);
        orecyclerView.setAdapter(oAdapter);

        uAdapter=new UserCreatedAdapter(uList,this, dataOriginal);
        LinearLayoutManager uLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        urecyclerView.setLayoutManager(uLayoutManager);
        urecyclerView.setAdapter(uAdapter);

        pAdapter=new PopRecipeAdapter(pList,this,dataOriginal);
        LinearLayoutManager pLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        precyclerView.setLayoutManager(pLayoutManager);
        precyclerView.setAdapter(pAdapter);

        vAdapter=new VegetarianAdapter(vList,this,dataOriginal);
        LinearLayoutManager vLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        vrecyclerView.setLayoutManager(vLayoutManager);
        vrecyclerView.setAdapter(vAdapter);

        hAdapter=new HealthyAdapter(hList,this,dataOriginal);
        LinearLayoutManager hLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        hrecyclerView.setLayoutManager(hLayoutManager);
        hrecyclerView.setAdapter(hAdapter);



        /*Query query = FirebaseDatabase.getInstance().getReference().child("Recipes");
        List<Recipe> list = new ArrayList<>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Recipe r = snapshot.getValue(Recipe.class);
                        list.add(r);
                    }
                } else {
                    Log.v(TAG, "not working");
                }
                List<Recipe> oList = new ArrayList<>();
                List<Recipe> uList = new ArrayList<>();

                for (Recipe r : list) {
                    if (r.getUid() != null) {
                        uList.add(r);
                    } else {
                        oList.add(r);
                    }
                }
                Collections.shuffle(uList);
                Collections.shuffle(oList);

                oAdapter = new OnlineRecipesAdapter(oList, rvi, list);  //ONLINE RECIPE ADAPTER
                LinearLayoutManager oLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                orecyclerView.setLayoutManager(oLayoutManager);
                orecyclerView.setAdapter(oAdapter);

                uAdapter = new UserCreatedAdapter(uList, urvi, list);
                LinearLayoutManager uLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                urecyclerView.setLayoutManager(uLayoutManager);
                urecyclerView.setAdapter(uAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        };
        query.addValueEventListener(eventListener);*/

        ImageView refreshButton = view.findViewById(R.id.refreshImage);
        ProgressBar progressBar = view.findViewById(R.id.progressBar2);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to refresh the recipes? This may take awhile").setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Query query = FirebaseDatabase.getInstance().getReference().child("Database_Version");
                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int FBVersion = 1;
                                sharedPreferences = getActivity().getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
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
                                    DBHandler dbHandler = new DBHandler(getActivity(), null, null, sharedDBVersion);
                                    FBHandler fbHandler = new FBHandler(dbHandler,getActivity());
                                    fbHandler.retrieveFBUserData();
                                    fbHandler.refreshFBRecipeData();
                                    //progressBar.setVisibility(View.INVISIBLE);
                                }
                                else{
                                    Toast.makeText(getActivity(), "Recipes already up to date",Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.v("Main", error.getMessage());
                            }
                        };
                        query.addListenerForSingleValueEvent(eventListener);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog alert = builder.create();
                alert.setTitle("Refresh Recipes");
                alert.show();
            }
        });

        return view;


    }

    public void onItemClick(int pos) {
        Intent intent = new Intent(getActivity().getBaseContext(),
                RecipeActivity.class);
        String rid  = oAdapter.getRid(pos);
        MainFragment.HomeSeed = seed;
        intent.putExtra("recipeID", rid);
        intent.putExtra("activity","home");
        MainFragment.activityResultLauncher.launch(intent);
    }

    public void onItemClick2(int pos) {
        Intent intent = new Intent(getActivity().getBaseContext(),
                RecipeActivity.class);
        String rid = uAdapter.getRid(pos);
        MainFragment.HomeSeed = seed;
        intent.putExtra("recipeID", rid);
        intent.putExtra("activity","home");
        MainFragment.activityResultLauncher.launch(intent);

    }
    public void onItemClick3(int pos) {
        Intent intent = new Intent(getActivity().getBaseContext(),
                RecipeActivity.class);
        String rid = pAdapter.getRid(pos);
        MainFragment.HomeSeed = seed;
        intent.putExtra("recipeID", rid);
        intent.putExtra("activity", "home");
        MainFragment.activityResultLauncher.launch(intent);
    }
    public void onItemClick4(int pos) {
        Intent intent = new Intent(getActivity().getBaseContext(),
                RecipeActivity.class);
        String rid = vAdapter.getRid(pos);
        MainFragment.HomeSeed = seed;
        intent.putExtra("recipeID", rid);
        intent.putExtra("activity", "home");
        MainFragment.activityResultLauncher.launch(intent);
    }


    @Override
    public void onItemClick6(int pos) {
        Intent intent = new Intent(getActivity().getBaseContext(),
                RecipeActivity.class);
        String rid = hAdapter.getRid(pos);
        MainFragment.HomeSeed = seed;
        intent.putExtra("recipeID", rid);
        intent.putExtra("activity","home");
        MainFragment.activityResultLauncher.launch(intent);

    }
}

