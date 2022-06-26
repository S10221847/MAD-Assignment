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
    RecyclerViewInterface recyclerViewInterface;

    public OnlineRecipesAdapter(List<Recipe>input,RecyclerViewInterface recyclerViewInterface){
        data=input;
        /*for(int i=0;i<data.size();i++){
            if((data.get(i).getUserId())!=null){
                data.remove(i);
                continue;
            }
        }*/
        this.recyclerViewInterface = recyclerViewInterface;
    }
    public OnlineRecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.online_recipes,parent,false);
        return new OnlineRecipesViewHolder(item,recyclerViewInterface);
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

