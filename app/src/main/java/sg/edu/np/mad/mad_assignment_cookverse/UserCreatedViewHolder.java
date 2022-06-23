package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class UserCreatedViewHolder extends RecyclerView.ViewHolder{
    TextView Usercreatedrecname;
    TextView likes;
    public UserCreatedViewHolder(View itemView){
        super(itemView);
        Usercreatedrecname=itemView.findViewById(R.id.usercreatedname);
        likes=itemView.findViewById(R.id.usercreatedlikes);


    }
}
