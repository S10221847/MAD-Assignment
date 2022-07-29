package sg.edu.np.mad.mad_assignment_cookverse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//Child fragment of profile fragment
public class UserRecipeFragment extends Fragment {
    PersonalRecipeAdapter adapter;
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

    public UserRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserRecipeFragment newInstance(String param1, String param2) {
        UserRecipeFragment fragment = new UserRecipeFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_recipe, container, false);
        // data to populate the RecyclerView with
        PersonalRecipeAdapter.ItemClickListener itemClickListener = this::onItemClick;
        RecyclerView recyclerView = view.findViewById(R.id.rvPersonal);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        adapter = new PersonalRecipeAdapter(getActivity(), ProfileFragment.personalRecipe);
        adapter.setClickListener(itemClickListener);
        recyclerView.setAdapter(adapter);
        /*Query query = FirebaseDatabase.getInstance().getReference().child("Recipes");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Recipe r = snapshot.getValue(Recipe.class);
                        rList.add(r);
                    }
                }
                RecyclerView recyclerView = view.findViewById(R.id.rvNumbers);
                int numberOfColumns = 3;
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
                adapter = new PersonalRecipeAdapter(getActivity(), rList);
                adapter.setClickListener(itemClickListener);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("Main", error.getMessage());
            }
        };
        query.addValueEventListener(eventListener);*/
        // set up the RecyclerView
        return view;
    }


    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity().getBaseContext(),
                RecipeActivity.class);
        String rid  = ProfileFragment.personalRecipe.get(position).getRid();

        intent.putExtra("recipeID", rid);
        intent.putExtra("activity","profile");
        MainFragment.activityResultLauncher.launch(intent);
    }

}