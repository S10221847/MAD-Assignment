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
    RecyclerViewInterface RecyclerViewInterface;

    public OnlineRecipesAdapter(List<Recipe>input,RecyclerViewInterface RecyclerViewInterface){
        data=input;  //list containing all recipes
        dataOriginal = new ArrayList<>(input);
        this.RecyclerViewInterface = RecyclerViewInterface; //calling recyclerviewinterface for itemonclick
    }
    public OnlineRecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.online_recipes,parent,false);
        return new OnlineRecipesViewHolder(item,RecyclerViewInterface);
    }
    public void onBindViewHolder(OnlineRecipesViewHolder holder,int position){
        Recipe r=data.get(position);
        holder.recname.setText(r.getName()); //set recipe name for each cardview
        holder.likes.setText(String.valueOf(r.getNoOfLikes())); //set recipe number of likes for each cardview
    }
    public int getItemCount(){

        return data.size(); //number of recyclerview items
    }

}

