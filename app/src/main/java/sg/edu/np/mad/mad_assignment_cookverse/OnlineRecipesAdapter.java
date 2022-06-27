package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OnlineRecipesAdapter extends RecyclerView.Adapter<OnlineRecipesViewHolder>{
    List<Recipe> data;
    List<Recipe> dataOriginal;
    OnlineRecyclerViewInterface onlineRecyclerViewInterface;

    public OnlineRecipesAdapter(List<Recipe>input,OnlineRecyclerViewInterface onlineRecyclerViewInterface){
        data=input;  //list containing all recipes
        dataOriginal = new ArrayList<>(input);
        this.onlineRecyclerViewInterface = onlineRecyclerViewInterface; //calling recyclerviewinterface for itemonclick
    }
    public OnlineRecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.online_recipes,parent,false);
        return new OnlineRecipesViewHolder(item,onlineRecyclerViewInterface);
    }
    public void onBindViewHolder(OnlineRecipesViewHolder holder,int position){
        Recipe r=data.get(position);
        holder.recname.setText(r.getName()); //set recipe name for each cardview
        holder.likes.setText(String.valueOf(r.getNoOfLikes())); //set recipe number of likes for each cardview
    }
    public int getItemCount(){

        return data.size(); //number of recyclerview items
    }
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
}

