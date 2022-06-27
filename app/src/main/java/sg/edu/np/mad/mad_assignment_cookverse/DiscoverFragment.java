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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment implements RecyclerViewInterface{
    FragmentProfileBinding binding;
    DiscoverAdaptor dAdaptor;
    RecyclerViewInterface recyclerViewInterface;
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

        DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
        RecyclerView discoverRecyclerView = view.findViewById(R.id.discoverRecyclerView);
        dAdaptor = new DiscoverAdaptor(dbHandler.listRecipe(), this);
        LinearLayoutManager dLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        discoverRecyclerView.setLayoutManager(dLayoutManager);
        discoverRecyclerView.setAdapter(dAdaptor);
        setHasOptionsMenu(true);

        return view;
    }


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
    }

    //pass values to RecipeActivity when itemView is clicked
    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(getActivity().getBaseContext(),
                RecipeActivity.class);
        DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
        long x = dAdaptor.getItemId(pos);
        int i = (int) x;
        intent.putExtra("recipeName", dbHandler.listRecipe().get(i).getName());
        intent.putExtra("recipeDesc", dbHandler.listRecipe().get(i).getDescription());
        intent.putExtra("recipeSteps", dbHandler.listRecipe().get(i).getSteps());
        intent.putExtra("recipeIngred", dbHandler.listRecipe().get(i).getIngredients());

        getActivity().startActivity(intent);
    }
}