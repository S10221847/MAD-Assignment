package sg.edu.np.mad.mad_assignment_cookverse;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sg.edu.np.mad.mad_assignment_cookverse.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment implements UserRecyclerViewInterface, OnlineRecyclerViewInterface{
    public String TAG="Home page";
    OnlineRecipesAdapter oAdapter;
    UserCreatedAdapter uAdapter;
    UserRecyclerViewInterface userRecyclerViewInterface;
    OnlineRecyclerViewInterface onlineRecyclerViewInterface;


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
        DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ArrayList<Recipe> dataOriginal = new ArrayList<>();
        dataOriginal = dbHandler.listRecipe();

        //RECYCLER VIEW FOR ONLINE RECIPES
        RecyclerView orecyclerView=view.findViewById(R.id.onlinerecRecyclerView);   //instantiate recycler view for ONLINE RECIPES
        orecyclerView.setHasFixedSize(true);
        List<Recipe>oList=dbHandler.listOnlineRecipe();;

        oAdapter=new OnlineRecipesAdapter(oList,this );  //ONLINE RECIPE ADAPTER
        LinearLayoutManager oLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        orecyclerView.setLayoutManager(oLayoutManager);
        orecyclerView.setAdapter(oAdapter);

        //RECYCLER VIEW FOR USER CREATED RECIPES (user.id of recipes=null)
        RecyclerView urecyclerView=view.findViewById(R.id.usercreatedRecyclerView);
        urecyclerView.setHasFixedSize(true);
        List<Recipe>uList=dbHandler.listUserRecipe();

        uAdapter=new UserCreatedAdapter(uList,this);
        LinearLayoutManager uLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        urecyclerView.setLayoutManager(uLayoutManager);
        urecyclerView.setAdapter(uAdapter);

        return view;


    }
    public void onItemClick(int pos){
        Intent intent = new Intent(getActivity().getBaseContext(),
                RecipeActivity.class);
        DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
        long x = oAdapter.getItemId(pos);
        int i = (int) x;
        intent.putExtra("recipeName", dbHandler.listRecipe().get(i).getName());
        intent.putExtra("recipeDesc", dbHandler.listRecipe().get(i).getDescription());
        intent.putExtra("recipeSteps", dbHandler.listRecipe().get(i).getSteps());
        intent.putExtra("recipeIngred", dbHandler.listRecipe().get(i).getIngredients());

        getActivity().startActivity(intent);
    }

    public void onItemClick2(int pos) {
        Intent intent = new Intent(getActivity().getBaseContext(),
                RecipeActivity.class);
        DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
        long x = uAdapter.getItemId(pos);
        int i = (int) x;
        intent.putExtra("recipeName", dbHandler.listRecipe().get(i).getName());
        intent.putExtra("recipeDesc", dbHandler.listRecipe().get(i).getDescription());
        intent.putExtra("recipeSteps", dbHandler.listRecipe().get(i).getSteps());
        intent.putExtra("recipeIngred", dbHandler.listRecipe().get(i).getIngredients());

        getActivity().startActivity(intent);
    }
}