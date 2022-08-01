package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HealthyAdapter extends RecyclerView.Adapter<HealthyViewHolder>{
    List<Recipe> data;
    List<Recipe> dataOriginal;
    HealthyInterface healthyInterface;

    public HealthyAdapter(List<Recipe>input,HealthyInterface healthyInterface, List<Recipe> og){
        data=input;  //list containing all recipes
        dataOriginal = og;
        this.healthyInterface = healthyInterface; //calling recyclerviewinterface for itemonclick
    }
    public HealthyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.healthy_layout,parent,false);
        return new HealthyViewHolder(item,healthyInterface);
    }
    public void onBindViewHolder(HealthyViewHolder holder,int position){
        Recipe r=data.get(position);
        holder.recname.setText(r.getName()); //set recipe name for each cardview
        holder.likes.setText(String.valueOf(r.getNooflikes())); //set recipe number of likes for each cardview
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
