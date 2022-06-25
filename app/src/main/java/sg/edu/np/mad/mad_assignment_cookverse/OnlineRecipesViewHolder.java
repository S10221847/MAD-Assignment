package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class OnlineRecipesViewHolder extends RecyclerView.ViewHolder{
    TextView recname;
    TextView likes;
    public OnlineRecipesViewHolder(View itemView,RecyclerViewInterface recyclerViewInterface){
        super(itemView);
        recname=itemView.findViewById(R.id.onlinerecipename);
        likes=itemView.findViewById(R.id.onlinelikes);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerViewInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            }
        });


    }
}
