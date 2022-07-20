package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DiscoverViewHolder extends RecyclerView.ViewHolder{
    TextView txt;
    TextView txt2;
    ImageView img;

    public DiscoverViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        txt = itemView.findViewById(R.id.recipeName);
        txt2 = itemView.findViewById(R.id.recipeDesc);
        img = itemView.findViewById(R.id.imageView2);

        //attach onClickListener for itemView
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerViewInterface != null){
                    //get position of itemView to pass in onItemClick
                    int pos = getAdapterPosition();

                    //make sure itemView position is valid
                    if(pos != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            }
        });
    }
}
