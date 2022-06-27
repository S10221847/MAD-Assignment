package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class OnlineRecipesViewHolder extends RecyclerView.ViewHolder{
    TextView recname;
    TextView likes;
    public OnlineRecipesViewHolder(View itemView,RecyclerViewInterface RecyclerViewInterface){
        super(itemView);
        recname=itemView.findViewById(R.id.onlinerecipename); //find location of recipe name
        likes=itemView.findViewById(R.id.onlinelikes); //find location of recipe number of likes
        itemView.setOnClickListener(new View.OnClickListener() { //onclick listener for each cardview containing recipes
            @Override
            public void onClick(View view) {
                if(RecyclerViewInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        RecyclerViewInterface.onItemClick(pos);
                    }
                }
            }
        });


    }
}
