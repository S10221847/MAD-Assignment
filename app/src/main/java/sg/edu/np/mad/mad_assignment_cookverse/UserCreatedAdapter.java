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
    private final RecyclerViewInterface recyclerViewInterface;

    public UserCreatedAdapter(List<Recipe> input, RecyclerViewInterface recyclerViewInterface){
        this.data=input;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    public UserCreatedViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.usercreatedrecipes,parent,false);
        return new UserCreatedViewHolder(item, recyclerViewInterface);
    }
    public void onBindViewHolder(UserCreatedViewHolder holder,int position){
        Recipe r=data.get(position);
        holder.Usercreatedrecname.setText(r.getName());
        holder.likes.setText(String.valueOf(r.getNoOfLikes()));
    }
    public int getItemCount(){
        return data.size();
    }
}
