package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class OnlineRecipesViewHolder extends RecyclerView.ViewHolder{
    TextView recname;
    TextView likes;
    public OnlineRecipesViewHolder(View itemView){
        super(itemView);
        recname=itemView.findViewById(R.id.onlinerecipename);
        likes=itemView.findViewById(R.id.onlinelikes);


    }
}
