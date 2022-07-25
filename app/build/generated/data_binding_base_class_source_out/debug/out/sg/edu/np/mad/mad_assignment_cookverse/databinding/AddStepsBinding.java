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

public final class AddStepsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText addSteps;

  @NonNull
  public final ImageView deleteStep;

  private AddStepsBinding(@NonNull ConstraintLayout rootView, @NonNull EditText addSteps,
      @NonNull ImageView deleteStep) {
    this.rootView = rootView;
    this.addSteps = addSteps;
    this.deleteStep = deleteStep;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AddStepsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AddStepsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.add_steps, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AddStepsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addSteps;
      EditText addSteps = ViewBindings.findChildViewById(rootView, id);
      if (addSteps == null) {
        break missingId;
      }

      id = R.id.deleteStep;
      ImageView deleteStep = ViewBindings.findChildViewById(rootView, id);
      if (deleteStep == null) {
        break missingId;
      }

      return new AddStepsBinding((ConstraintLayout) rootView, addSteps, deleteStep);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
