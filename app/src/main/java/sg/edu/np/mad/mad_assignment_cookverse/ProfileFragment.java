package sg.edu.np.mad.mad_assignment_cookverse;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sg.edu.np.mad.mad_assignment_cookverse.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    public static ArrayList<Recipe> allRecipe = new ArrayList<>();
    public static ArrayList<Recipe> personalRecipe = new ArrayList<>();
    public static ArrayList<Recipe> likedRecipe = new ArrayList<>();
    public static ArrayList<String> personalStringRecipe = new ArrayList<>();
    public static ArrayList<String> likedStringRecipe = new ArrayList<>();
    public String GLOBAL_PREF = "MyPrefs";
    public String DATABASE_VERSION = "MyDatabaseVersion";
    DBHandler dbHandler;
    SharedPreferences sharedPreferences;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        //Setting first fragment to show personal recipe
        replaceFragment(new UserRecipeFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

        /*Intent receivingEnd = getActivity().getIntent();
        String currentUsername = receivingEnd.getStringExtra("username");
        String userpfp = receivingEnd.getStringExtra("image");
        String userbio = receivingEnd.getStringExtra("bio");*/
        TextView currentName =  rootView.findViewById(R.id.userName);
        TextView currentBio =  rootView.findViewById(R.id.bio);
        Button editProfile = rootView.findViewById(R.id.editProfile);
        TextView personalR = rootView.findViewById(R.id.personalReci);
        TextView likedR = rootView.findViewById(R.id.likedReci);
        /*User currentUser = new User();
        currentUser.setName(currentUsername);
        currentUser.setUserImage(userpfp);
        currentUser.setBio(userbio);*/

        //Find the current user among all the users in database
        /*Query query2 = FirebaseDatabase.getInstance().getReference().child("Recipes");
        ValueEventListener eventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Recipe r = snapshot.getValue(Recipe.class);
                        personalRecipe.add(r);
                    }
                }
                personalR.setText(String.valueOf(personalRecipe.size()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("Main", error.getMessage());
            }
        };
        query2.addValueEventListener(eventListener2);*/
        sharedPreferences = this.getActivity().getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        dbHandler = new DBHandler(getActivity(), null, null, sharedDBVersion);
        allRecipe = dbHandler.listAllRecipe();
        for (Recipe r:allRecipe) {//Need to edit if there's email verification
            if (LoginPage.mainUser.getCreatedList().contains(r.getRid()) &
                    !personalStringRecipe.contains(r.getRid())){
                personalStringRecipe.add(r.getRid());
                personalRecipe.add(r);
            }
            else if (LoginPage.mainUser.getLikedList().contains(r.getRid()) &
                    !likedStringRecipe.contains(r.getRid())){
                likedStringRecipe.add(r.getRid());
                likedRecipe.add(r);
            }
        }
        currentName.setText(LoginPage.mainUser.getName());
        currentBio.setText(LoginPage.mainUser.getBio());
        personalR.setText(String.valueOf(personalRecipe.size()));
        likedR.setText(String.valueOf(likedRecipe.size()));
        ImageView myImage =rootView.findViewById(R.id.ProfileImage);
        if (LoginPage.mainUser.getUserImage() != null){
            new ImageLoadTask(LoginPage.mainUser.getUserImage(), myImage).execute();
        }

        BottomNavigationView BNV =rootView.findViewById(R.id.bottomNavigationView2);
        //Switch among fragments when clicking them
        BNV.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.personal:
                    replaceFragment(new UserRecipeFragment());
                    break;
                case R.id.liked:
                    replaceFragment(new LikedRecipeFragment());
                    break;
            }
            return true;
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(getActivity().getBaseContext(), EditProfile.class);
                /*editIntent.putExtra("Username", LoginPage.mainUser.getName());
                editIntent.putExtra("Bio", LoginPage.mainUser.getBio());
                editIntent.putExtra("Pfp", LoginPage.mainUser.getUserImage());*/
                /*Add in password*/
                MainFragment.activityResultLauncher.launch(editIntent);
            }
        });
        return rootView;
    }

        //Method for showing fragments
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FRAGMENT_PLACEHOLDER, fragment);
        fragmentTransaction.commit();
    }

}