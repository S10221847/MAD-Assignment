package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DiscoverAdaptor extends RecyclerView.Adapter<DiscoverViewHolder>{
    //data arraylist will be filtered, dataOriginal arraylist stores the original values of data
    private ArrayList<Recipe> data;
    private ArrayList<Recipe> dataOriginal;
    private final RecyclerViewInterface recyclerViewInterface;

    public DiscoverAdaptor(ArrayList<Recipe> input, RecyclerViewInterface recyclerViewInterface) {
        this.data = input;
        //fill data original with same values as values in data
        dataOriginal = new ArrayList<>(input);
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public DiscoverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_layout, parent, false);
        return new DiscoverViewHolder(item, recyclerViewInterface);
    }

    public void onBindViewHolder(DiscoverViewHolder holder, int position){
        Recipe dish = data.get(position);

        holder.txt.setText(dish.getName());
        //holder.txt2.setText(dish.getDescription());
        new ImageLoadTask(dish.getRecipeimage(), holder.img).execute();
    }

    public int getItemCount(){ return data.size(); }

    @Override
    public long getItemId(int position) {

        int itemID;

        // orig will be null only if we haven't filtered yet:
        if (dataOriginal == null)
        {
            itemID = position;
        }
        else
        {
            itemID = dataOriginal.indexOf(data.get(position));
        }
        return itemID;
    }
    /*
    //FILTERING
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        //filter data arraylist
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Recipe> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(dataOriginal);
            }
            else{ // makes the search non case sensitive
                String filterPattern = constraint.toString().toLowerCase().trim();
                //if recipe name or description follows the filter pattern, add to filteredList
                for(Recipe item : dataOriginal){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        //display results on screen
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results.values != null) {
                data.clear();
                data.addAll((ArrayList)results.values);
                notifyDataSetChanged();
            }
            //show nothing if no results
            else{
                data.clear();
                notifyDataSetChanged();
            }
        }
    };*/
    public void filterList(ArrayList<Recipe> list){
        this.data = list;
        notifyDataSetChanged();
    }
}