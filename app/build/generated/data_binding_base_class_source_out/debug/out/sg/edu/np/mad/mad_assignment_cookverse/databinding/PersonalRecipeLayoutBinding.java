// Generated by view binder compiler. Do not edit!
package sg.edu.np.mad.mad_assignment_cookverse.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sg.edu.np.mad.mad_assignment_cookverse.R;

public final class PersonalRecipeLayoutBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final ImageView personalRImage;

  @NonNull
  public final TextView personalRName;

  @NonNull
  public final ConstraintLayout personalRecipeLayout;

  private PersonalRecipeLayoutBinding(@NonNull ConstraintLayout rootView,
      @NonNull CardView cardView, @NonNull ImageView personalRImage,
      @NonNull TextView personalRName, @NonNull ConstraintLayout personalRecipeLayout) {
    this.rootView = rootView;
    this.cardView = cardView;
    this.personalRImage = personalRImage;
    this.personalRName = personalRName;
    this.personalRecipeLayout = personalRecipeLayout;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static PersonalRecipeLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static PersonalRecipeLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.personal_recipe_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static PersonalRecipeLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardView;
      CardView cardView = ViewBindings.findChildViewById(rootView, id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.personalRImage;
      ImageView personalRImage = ViewBindings.findChildViewById(rootView, id);
      if (personalRImage == null) {
        break missingId;
      }

      id = R.id.personalRName;
      TextView personalRName = ViewBindings.findChildViewById(rootView, id);
      if (personalRName == null) {
        break missingId;
      }

      ConstraintLayout personalRecipeLayout = (ConstraintLayout) rootView;

      return new PersonalRecipeLayoutBinding((ConstraintLayout) rootView, cardView, personalRImage,
          personalRName, personalRecipeLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
