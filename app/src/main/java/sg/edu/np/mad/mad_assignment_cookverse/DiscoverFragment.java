package sg.edu.np.mad.mad_assignment_cookverse;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sg.edu.np.mad.mad_assignment_cookverse.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment implements RecyclerViewInterface{
    FragmentProfileBinding binding;
    DiscoverAdaptor dAdaptor;
    RecyclerViewInterface recyclerViewInterface;
    ArrayList<Recipe> dataOriginal;
    public String GLOBAL_PREF = "MyPrefs";
    public String DATABASE_VERSION = "MyDatabaseVersion";
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;
    //SearchView searchView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
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
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        //DBHandler
        sharedPreferences = this.getActivity().getSharedPreferences(GLOBAL_PREF, MODE_PRIVATE);
        int sharedDBVersion = sharedPreferences.getInt(DATABASE_VERSION, 2);
        dbHandler = new DBHandler(getActivity(), null, null, sharedDBVersion);
        dataOriginal = dbHandler.listAllRecipe();

        RecyclerViewInterface rvi = this;

        RecyclerView discoverRecyclerView = view.findViewById(R.id.discoverRecyclerView);
        SearchView searchView = view.findViewById(R.id.searchbar);

        dAdaptor = new DiscoverAdaptor(dataOriginal, rvi);
        LinearLayoutManager dLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        discoverRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, dLayoutManager.VERTICAL));
        discoverRecyclerView.setAdapter(dAdaptor);
        setHasOptionsMenu(true);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText, dataOriginal);
                return false;
            }
        });

        return view;
    }

    private void filter(String newText, ArrayList<Recipe> list) {
        ArrayList<Recipe> filteredList = new ArrayList<>();
        for(Recipe item:list){
            if(item.getName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        dAdaptor.filterList(filteredList);
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //filters the list in realtime, pass in user search text as newText
            @Override
            public boolean onQueryTextChange(String newText) {
                dAdaptor.getFilter().filter(newText);

                return false;
            }
        });
    }*/

    //pass values to RecipeActivity when itemView is clicked
    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(getActivity().getBaseContext(),
                RecipeActivity.class);
        String rid = dAdaptor.getRid(pos);

        intent.putExtra("recipeID", rid);
        getActivity().startActivity(intent);
    }
}