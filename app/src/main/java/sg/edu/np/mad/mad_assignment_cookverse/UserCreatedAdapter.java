package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserCreatedAdapter extends RecyclerView.Adapter<UserCreatedViewHolder>{
    private List<Recipe> data;
    UserRecyclerViewInterface UserRecyclerViewInterface;
    private Recipe x;

    public UserCreatedAdapter(List<Recipe> input, UserRecyclerViewInterface UserRecyclerViewInterface){
        data=input;   //data consisting list of recipes
        this.UserRecyclerViewInterface = UserRecyclerViewInterface;   //calling recyclerview interface for onclick method
    }
    public UserCreatedViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.usercreatedrecipes,parent,false);
        return new UserCreatedViewHolder(item, UserRecyclerViewInterface);
    }
    public void onBindViewHolder(UserCreatedViewHolder holder,int position){
        Recipe r=data.get(position);
        holder.Usercreatedrecname.setText(r.getName()); //set recipe name for each cardview
        holder.likes.setText(String.valueOf(r.getNoOfLikes())); //set number of likes for each recipe in each cardview

    }
    public int getItemCount(){
        return data.size(); //number of recyclerview items

    }



}
