package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

public class UserCreatedViewHolder extends RecyclerView.ViewHolder{
    TextView Usercreatedrecname;
    TextView likes;
    public UserCreatedViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface){
        super(itemView);
        Usercreatedrecname=itemView.findViewById(R.id.usercreatedname);
        likes=itemView.findViewById(R.id.usercreatedlikes);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            }
        });
    }
}
