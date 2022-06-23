package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OnlineRecipesAdapter extends RecyclerView.Adapter<OnlineRecipesViewHolder>{
    ArrayList<Recipe> data;

    public OnlineRecipesAdapter(ArrayList<Recipe>input){
        data=input;
    }
    public OnlineRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.online_recipes,parent,false);
        return new OnlineRecipesViewHolder(item);
    }
    public void onBindViewHolder(OnlineRecipesViewHolder holder,int position){
        Recipe r=data.get(position);
        holder.recname.setText(r.getName());
        holder.likes.setText(String.valueOf(r.getNoOfLikes()));
    }
    public int getItemCount(){
        return data.size();
    }
}

