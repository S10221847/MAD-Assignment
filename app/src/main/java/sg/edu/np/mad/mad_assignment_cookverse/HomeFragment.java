package sg.edu.np.mad.mad_assignment_cookverse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import sg.edu.np.mad.mad_assignment_cookverse.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment implements RecyclerViewInterface,UserRecyclerViewInterface{
    public String TAG="Home page";
    OnlineRecipesAdapter oAdapter;
    UserCreatedAdapter uAdapter;
    RecyclerViewInterface RecyclerViewInterface;
    UserRecyclerViewInterface UserRecyclerViewInterface;
    List<Recipe> dataOriginal;
    List<Recipe>uList;


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
        //DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
        RecyclerViewInterface rvi = this;
        UserRecyclerViewInterface urvi = this;


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ArrayList<Recipe> dataOriginal = new ArrayList<>();
        //dataOriginal = dbHandler.listRecipe();

        //RECYCLER VIEW FOR ONLINE RECIPES
        RecyclerView orecyclerView=view.findViewById(R.id.onlinerecRecyclerView);   //instantiate recycler view for ONLINE RECIPES
        orecyclerView.setHasFixedSize(true);

        RecyclerView urecyclerView=view.findViewById(R.id.usercreatedRecyclerView);
        urecyclerView.setHasFixedSize(true);

        Query query = FirebaseDatabase.getInstance().getReference().child("Recipes");
        List<Recipe> list = new ArrayList<>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Recipe r = snapshot.getValue(Recipe.class);
                        list.add(r);
                    }
                }
                else{
                    Log.v(TAG, "not working");
                }
                List<Recipe>oList=new ArrayList<>();
                List<Recipe>uList=new ArrayList<>();

                for (Recipe r : list){
                    if (r.getUid() != null){
                        uList.add(r);
                    }
                    else{
                        oList.add(r);
                    }
                }

                oAdapter=new OnlineRecipesAdapter(oList, rvi);  //ONLINE RECIPE ADAPTER
                LinearLayoutManager oLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                orecyclerView.setLayoutManager(oLayoutManager);
                orecyclerView.setAdapter(oAdapter);

                uAdapter=new UserCreatedAdapter(uList,urvi);
                LinearLayoutManager uLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                urecyclerView.setLayoutManager(uLayoutManager);
                urecyclerView.setAdapter(uAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        };
        query.addValueEventListener(eventListener);

        //RECYCLER VIEW FOR USER CREATED RECIPES (user.id of recipes=null)
        /*RecyclerView urecyclerView=view.findViewById(R.id.usercreatedRecyclerView);
        urecyclerView.setHasFixedSize(true);
        List<Recipe>uList=dbHandler.listUserRecipe();

        uAdapter=new UserCreatedAdapter(uList,this);
        LinearLayoutManager uLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        urecyclerView.setLayoutManager(uLayoutManager);
        urecyclerView.setAdapter(uAdapter);*/

        return view;


    }
    public void onItemClick(int pos){
        Intent intent = new Intent(getActivity().getBaseContext(),
                RecipeActivity.class);

        intent.putExtra("recipePos", pos);
        getActivity().startActivity(intent);
    }

    public void onItemClick2(int pos) {
        Intent intent = new Intent(getActivity().getBaseContext(),
                RecipeActivity.class);

        intent.putExtra("recipePos", pos);
        getActivity().startActivity(intent);
    }

}