package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VegetarianAdapter extends RecyclerView.Adapter<VegetarianViewHolder>{
    private List<Recipe> data;
    List<Recipe> dataOriginal;
    VegetarianInterface VegetarianInterface;
    private Recipe x;

    public VegetarianAdapter(List<Recipe> input, VegetarianInterface VegetarianInterface, List<Recipe> og){
        data=input;   //data consisting list of recipes
        dataOriginal = og;
        this.VegetarianInterface = VegetarianInterface;   //calling recyclerview interface for onclick method
    }
    public VegetarianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.vegetarianrecycler,parent,false);
        return new VegetarianViewHolder(item, VegetarianInterface);
    }
    public void onBindViewHolder(VegetarianViewHolder holder,int position){
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

