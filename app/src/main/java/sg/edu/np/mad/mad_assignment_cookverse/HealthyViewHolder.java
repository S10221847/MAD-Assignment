package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class HealthyViewHolder extends RecyclerView.ViewHolder{
    TextView recname;
    TextView likes;
    ImageView pic;
    public HealthyViewHolder(View itemView, HealthyInterface healthyInterface){
        super(itemView);
        recname=itemView.findViewById(R.id.onlinerecipename); //find location of recipe name
        likes=itemView.findViewById(R.id.onlinelikes); //find location of recipe number of likes
        pic=itemView.findViewById(R.id.imageView4);
        itemView.setOnClickListener(new View.OnClickListener() { //onclick listener for each cardview containing recipes
            @Override
            public void onClick(View view) {
                if(healthyInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        healthyInterface.onItemClick6(pos);
                    }
                }
            }
        });


    }
}
