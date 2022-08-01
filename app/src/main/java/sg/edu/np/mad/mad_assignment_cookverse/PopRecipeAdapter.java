package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PopRecipeAdapter extends RecyclerView.Adapter<PopRecipeViewHolder>{
    private List<Recipe> data;
    List<Recipe> dataOriginal;
    PopRecyclerViewInterface PopRecyclerViewInterface;
    private Recipe x;

    public PopRecipeAdapter(List<Recipe> input, PopRecyclerViewInterface PopRecyclerViewInterface, List<Recipe> og){
        data=input;   //data consisting list of recipes
        dataOriginal = og;
        this.PopRecyclerViewInterface = PopRecyclerViewInterface;   //calling recyclerview interface for onclick method
    }
    public PopRecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.poprecipe_layout,parent,false);
        return new PopRecipeViewHolder(item, PopRecyclerViewInterface);
    }
    public void onBindViewHolder(PopRecipeViewHolder holder,int position){
        Recipe r=data.get(position);
        holder.Usercreatedrecname.setText(r.getName()); //set recipe name for each cardview
        holder.likes.setText(String.valueOf(r.getNooflikes())); //set number of likes for each recipe in each cardview
        new ImageLoadTask(r.getRecipeimage(), holder.pic).execute();

    }
    public int getItemCount(){
        return data.size(); //number of recyclerview items
    }

    public String getRid(int position) {

        String recipeid = data.get(position).getRid();

        return recipeid;
    }
}
