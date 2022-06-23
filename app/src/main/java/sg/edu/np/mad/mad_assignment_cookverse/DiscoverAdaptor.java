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

public class DiscoverAdaptor extends RecyclerView.Adapter<DiscoverViewHolder> implements Filterable {
    private List<Recipe> data;
    private List<Recipe> dataOriginal;
    private final RecyclerViewInterface recyclerViewInterface;

    public DiscoverAdaptor(List<Recipe> input, RecyclerViewInterface recyclerViewInterface) {
        this.data = input;
        //add this
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
        holder.txt2.setText(dish.getDescription());
        //holder.img.setImageResource(R.drawable.ic_launcher_foreground);
    }

    public int getItemCount(){ return data.size(); }


    //FILTERING
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Recipe> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(dataOriginal);
            }
            else{ //makes the search non case sensitive
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Recipe item : dataOriginal){
                    if(item.getName().toLowerCase().contains(filterPattern) || item.getDescription().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results.values != null) {
                data.clear();
                data.addAll((List)results.values);
                notifyDataSetChanged();
            }
            else{
                data.clear();
                notifyDataSetChanged();
            }
        }
    };
}
