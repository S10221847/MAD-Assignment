package sg.edu.np.mad.mad_assignment_cookverse;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingListViewHolder extends RecyclerView.ViewHolder{
    ImageView rPic;
    TextView rName;
    TextView rServings;
    LinearLayout rIngred;
    public ShoppingListViewHolder(@NonNull View itemView){
        super(itemView);
        rPic=itemView.findViewById(R.id.shopping_pic);
        rName=itemView.findViewById(R.id.shopping_name);
        rServings=itemView.findViewById(R.id.shopping_servings);
        rIngred=itemView.findViewById(R.id.shoppinglinear);

        }
    }
