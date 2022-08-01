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
    public ShoppingListViewHolder(View itemView,shoppingInterface SHOPPINGINTERFACE){
        super(itemView);
        rPic=itemView.findViewById(R.id.shopping_pic);
        rName=itemView.findViewById(R.id.shopping_name);
        rServings=itemView.findViewById(R.id.shopping_servings);
        rIngred=itemView.findViewById(R.id.shoppinglinear);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SHOPPINGINTERFACE!=null){
                    int pos=getAdapterPosition();

                    if(pos!=RecyclerView.NO_POSITION){
                        SHOPPINGINTERFACE.onItemClick5(pos);
                    }
                }
            }
        });

        };


    }
