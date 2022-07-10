package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

public class UserCreatedViewHolder extends RecyclerView.ViewHolder{
    TextView Usercreatedrecname;
    TextView likes;
    ImageView pic;
    public UserCreatedViewHolder(@NonNull View itemView, UserRecyclerViewInterface UserRecyclerViewInterface){
        super(itemView);
        Usercreatedrecname=itemView.findViewById(R.id.usercreatedname); //find location of recipe name
        likes=itemView.findViewById(R.id.usercreatedlikes); //find location of recipe number of likes
        pic=itemView.findViewById(R.id.imageView3);
        itemView.setOnClickListener(new View.OnClickListener() {  //onclick listener for each cardview containing recipe
            @Override
            public void onClick(View view) {
                if (UserRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        UserRecyclerViewInterface.onItemClick2(pos);
                    }
                }
            }
        });
    }
}