// Generated by view binder compiler. Do not edit!
package sg.edu.np.mad.mad_assignment_cookverse.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.imageview.ShapeableImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sg.edu.np.mad.mad_assignment_cookverse.R;

public final class ShoppinglistLayoutBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout constraintLayout;

  @NonNull
  public final TextView shoppingName;

  @NonNull
  public final ShapeableImageView shoppingPic;

  @NonNull
  public final TextView shoppingServings;

  @NonNull
  public final LinearLayout shoppinglinear;

  private ShoppinglistLayoutBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout constraintLayout, @NonNull TextView shoppingName,
      @NonNull ShapeableImageView shoppingPic, @NonNull TextView shoppingServings,
      @NonNull LinearLayout shoppinglinear) {
    this.rootView = rootView;
    this.constraintLayout = constraintLayout;
    this.shoppingName = shoppingName;
    this.shoppingPic = shoppingPic;
    this.shoppingServings = shoppingServings;
    this.shoppinglinear = shoppinglinear;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ShoppinglistLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ShoppinglistLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.shoppinglist_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ShoppinglistLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.constraintLayout;
      ConstraintLayout constraintLayout = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout == null) {
        break missingId;
      }

      id = R.id.shopping_name;
      TextView shoppingName = ViewBindings.findChildViewById(rootView, id);
      if (shoppingName == null) {
        break missingId;
      }

      id = R.id.shopping_pic;
      ShapeableImageView shoppingPic = ViewBindings.findChildViewById(rootView, id);
      if (shoppingPic == null) {
        break missingId;
      }

      id = R.id.shopping_servings;
      TextView shoppingServings = ViewBindings.findChildViewById(rootView, id);
      if (shoppingServings == null) {
        break missingId;
      }

      id = R.id.shoppinglinear;
      LinearLayout shoppinglinear = ViewBindings.findChildViewById(rootView, id);
      if (shoppinglinear == null) {
        break missingId;
      }

      return new ShoppinglistLayoutBinding((ConstraintLayout) rootView, constraintLayout,
          shoppingName, shoppingPic, shoppingServings, shoppinglinear);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}