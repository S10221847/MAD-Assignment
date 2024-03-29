// Generated by view binder compiler. Do not edit!
package sg.edu.np.mad.mad_assignment_cookverse.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sg.edu.np.mad.mad_assignment_cookverse.R;

public final class AddIngredientsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText addIngredient;

  @NonNull
  public final Button addIngredientsButton;

  @NonNull
  public final Button deleteIngredient;

  private AddIngredientsBinding(@NonNull ConstraintLayout rootView, @NonNull EditText addIngredient,
      @NonNull Button addIngredientsButton, @NonNull Button deleteIngredient) {
    this.rootView = rootView;
    this.addIngredient = addIngredient;
    this.addIngredientsButton = addIngredientsButton;
    this.deleteIngredient = deleteIngredient;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AddIngredientsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AddIngredientsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.add_ingredients, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AddIngredientsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addIngredient;
      EditText addIngredient = ViewBindings.findChildViewById(rootView, id);
      if (addIngredient == null) {
        break missingId;
      }

      id = R.id.addIngredientsButton;
      Button addIngredientsButton = ViewBindings.findChildViewById(rootView, id);
      if (addIngredientsButton == null) {
        break missingId;
      }

      id = R.id.deleteIngredient;
      Button deleteIngredient = ViewBindings.findChildViewById(rootView, id);
      if (deleteIngredient == null) {
        break missingId;
      }

      return new AddIngredientsBinding((ConstraintLayout) rootView, addIngredient,
          addIngredientsButton, deleteIngredient);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
