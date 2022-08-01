package sg.edu.np.mad.mad_assignment_cookverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListViewHolder>{
    List<Recipe> data;
    Context context;




    public ShoppingListAdapter(List<Recipe> input, Context context){
        data=input;
        this.context=context;

    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppinglist_layout,parent,false);
        return new ShoppingListViewHolder(item);
    }
    public void onBindViewHolder(ShoppingListViewHolder holder,int position){

        holder.rName.setText(data.get(position).getName());
        String servings_count=String.valueOf(data.get(position).getServings());
        servings_count=servings_count.concat(" Servings");
        holder.rServings.setText(servings_count);
        if(data.get(position).getRecipeimage() != null){
            new ImageLoadTask(data.get(position).getRecipeimage(), holder.rPic).execute();
        }

        for(int x=0;x<(data.get(position).getIngredientsList().size());x++){
            LayoutInflater inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.shopping_ingred,null);
            TextView shoppingIngred=(TextView)view.findViewById(R.id.shoppingIngred);
            String ingredient= data.get(position).getIngredientsList().get(x);
            shoppingIngred.setText(ingredient);
            holder.rIngred.addView(view);

        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}



