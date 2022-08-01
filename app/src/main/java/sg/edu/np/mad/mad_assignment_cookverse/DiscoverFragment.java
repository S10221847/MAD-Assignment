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

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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
    private Boolean hasConstraint = false;
    private Boolean constraintChanged = false;
    private String constraint = "";


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
        ArrayList<String> chosenFilters = new ArrayList<>();

        RecyclerViewInterface rvi = this;

        RecyclerView discoverRecyclerView = view.findViewById(R.id.discoverRecyclerView);
        SearchView searchView = view.findViewById(R.id.searchbar);
        ImageView filter = view.findViewById(R.id.discoverFilter);

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
                constraintChanged = true;
                constraint = newText;

                //check for text in searchbar
                if(newText.length() == 0) {
                    hasConstraint=false;
                }
                else{
                    hasConstraint=true;
                }

                //check for any filters applied
                if(chosenFilters.size()!=0){
                    //filterFunction(list, chosenFilters);
                    ArrayList<Recipe> filteredList1 = filterByFunction(dataOriginal, chosenFilters);
                    if(hasConstraint==true){
                        ArrayList<Recipe> filteredListFinal = filterBySearch(newText, filteredList1, chosenFilters);
                        dAdaptor.Filter(filteredListFinal, hasConstraint);
                    }
                    else{
                        dAdaptor.Filter(filteredList1, hasConstraint);
                    }
                }
                else {
                    ArrayList<Recipe> filteredList = filterBySearch(newText, dataOriginal, chosenFilters);
                    dAdaptor.Filter(filteredList, hasConstraint);
                }
                return false;
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog(dataOriginal, chosenFilters);
            }
        });

        return view;
    }

    private void showBottomSheetDialog(ArrayList<Recipe> recipes, ArrayList<String> chosenFilters) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        ArrayList<CheckBox> checkboxList = new ArrayList<>();

        CheckBox vegetarian = bottomSheetDialog.findViewById(R.id.isVegetarian);
        checkboxList.add(vegetarian);
        CheckBox vegan = bottomSheetDialog.findViewById(R.id.isVegan);
        checkboxList.add(vegan);
        CheckBox glutenFree = bottomSheetDialog.findViewById(R.id.isGlutenFree);
        checkboxList.add(glutenFree);
        CheckBox dairyFree = bottomSheetDialog.findViewById(R.id.isDairyFree);
        checkboxList.add(dairyFree);
        CheckBox healthy = bottomSheetDialog.findViewById(R.id.isHealthy);
        checkboxList.add(healthy);
        CheckBox popular = bottomSheetDialog.findViewById(R.id.isPopular);
        checkboxList.add(popular);
        CheckBox fast = bottomSheetDialog.findViewById(R.id.isFast);
        checkboxList.add(fast);
        Button applyFilter = bottomSheetDialog.findViewById(R.id.applyFilter);
        TextView clearFilter = bottomSheetDialog.findViewById(R.id.clearFilter);

        if(chosenFilters.size() != 0){
            for(CheckBox item:checkboxList){
                if(chosenFilters.contains(item.getText().toString())){
                    item.setChecked(true);
                }
            }
        }

        clearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenFilters.clear();
                ArrayList<Recipe> filteredList = filterBySearch(constraint, recipes, chosenFilters);
                dAdaptor.Filter(filteredList, hasConstraint);
                bottomSheetDialog.dismiss();
                Toast.makeText(getContext(), "Cleared filters", Toast.LENGTH_SHORT).show();
            }
        });

        //apply filter
        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintChanged=false;
                chosenFilters.clear();
                for (CheckBox c : checkboxList) {
                    if(c.isChecked()){
                        chosenFilters.add(c.getText().toString());
                    }
                }

                ArrayList<Recipe> filteredList1 = filterByFunction(dataOriginal, chosenFilters);
                ArrayList<Recipe> filteredListFinal = filterBySearch(constraint, filteredList1, chosenFilters);
                dAdaptor.functionFilterList(filteredListFinal, hasConstraint, constraintChanged);
                bottomSheetDialog.dismiss();

                if(chosenFilters.size()!=0){
                    Toast.makeText(getContext(), "Viewing " + chosenFilters.toString().replace("[", "").replace("]", "") + " recipes", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Viewing all recipes", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bottomSheetDialog.show();
    }

    private ArrayList<Recipe> filterBySearch(String newText, ArrayList<Recipe> list, ArrayList<String> chosenFilters) {
        ArrayList<Recipe> filteredList = new ArrayList<>();
        for(Recipe item:list){
            if(item.getName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        return filteredList;
    }

    private ArrayList<Recipe> filterByFunction (ArrayList<Recipe> list, ArrayList<String> chosenFilters) {
        ArrayList<Recipe> filteredList = new ArrayList<>();
        if(chosenFilters.size()==0){
            return list;
        }
        if(chosenFilters.contains("Vegetarian")){
            for(Recipe item:list){
                if(item.isVegetarian()==true && !filteredList.contains(item)){
                    filteredList.add(item);
                }
            }
        }
        if(chosenFilters.contains("Popular")){
            for(Recipe item:list){
                if(item.isPopular()==true && !filteredList.contains(item)){
                    filteredList.add(item);
                }
            }
        }
        if(chosenFilters.contains("Healthy")){
            for(Recipe item:list){
                if(item.isHealthy()==true && !filteredList.contains(item)){
                    filteredList.add(item);
                }
            }
        }
        if(chosenFilters.contains("Vegan")){
            for(Recipe item:list){
                if(item.isVegan()==true && !filteredList.contains(item)){
                    filteredList.add(item);
                }
            }
        }
        if(chosenFilters.contains("Gluten-free")){
            for(Recipe item:list){
                if(item.isGlutenFree()==true && !filteredList.contains(item)){
                    filteredList.add(item);
                }
            }
        }
        if(chosenFilters.contains("Dairy-free")){
            for(Recipe item:list){
                if(item.isDairyFree()==true && !filteredList.contains(item)){
                    filteredList.add(item);
                }
            }
        }
        if(chosenFilters.contains("Fast (less than 40mins)")){
            for(Recipe item:list){
                if(item.getDuration()<40 && !filteredList.contains(item)){
                    filteredList.add(item);
                }
            }
        }

        return filteredList;
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
        intent.putExtra("activity","discover");
        startActivity(intent);
    }
}