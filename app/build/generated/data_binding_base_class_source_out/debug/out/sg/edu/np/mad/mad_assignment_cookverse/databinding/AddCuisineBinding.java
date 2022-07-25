// Generated by view binder compiler. Do not edit!
package sg.edu.np.mad.mad_assignment_cookverse.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sg.edu.np.mad.mad_assignment_cookverse.R;

public final class AddCuisineBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText addCuisine;

  @NonNull
  public final ImageView deleteCuisine;

  private AddCuisineBinding(@NonNull ConstraintLayout rootView, @NonNull EditText addCuisine,
      @NonNull ImageView deleteCuisine) {
    this.rootView = rootView;
    this.addCuisine = addCuisine;
    this.deleteCuisine = deleteCuisine;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AddCuisineBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AddCuisineBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.add_cuisine, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AddCuisineBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addCuisine;
      EditText addCuisine = ViewBindings.findChildViewById(rootView, id);
      if (addCuisine == null) {
        break missingId;
      }

      id = R.id.deleteCuisine;
      ImageView deleteCuisine = ViewBindings.findChildViewById(rootView, id);
      if (deleteCuisine == null) {
        break missingId;
      }

      return new AddCuisineBinding((ConstraintLayout) rootView, addCuisine, deleteCuisine);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
