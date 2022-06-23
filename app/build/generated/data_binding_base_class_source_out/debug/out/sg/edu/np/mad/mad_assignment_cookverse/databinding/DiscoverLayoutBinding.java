// Generated by view binder compiler. Do not edit!
package sg.edu.np.mad.mad_assignment_cookverse.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sg.edu.np.mad.mad_assignment_cookverse.R;

public final class DiscoverLayoutBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView recipeDesc;

  @NonNull
  public final TextView recipeName;

  private DiscoverLayoutBinding(@NonNull LinearLayout rootView, @NonNull TextView recipeDesc,
      @NonNull TextView recipeName) {
    this.rootView = rootView;
    this.recipeDesc = recipeDesc;
    this.recipeName = recipeName;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DiscoverLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DiscoverLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.discover_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DiscoverLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.recipeDesc;
      TextView recipeDesc = ViewBindings.findChildViewById(rootView, id);
      if (recipeDesc == null) {
        break missingId;
      }

      id = R.id.recipeName;
      TextView recipeName = ViewBindings.findChildViewById(rootView, id);
      if (recipeName == null) {
        break missingId;
      }

      return new DiscoverLayoutBinding((LinearLayout) rootView, recipeDesc, recipeName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
