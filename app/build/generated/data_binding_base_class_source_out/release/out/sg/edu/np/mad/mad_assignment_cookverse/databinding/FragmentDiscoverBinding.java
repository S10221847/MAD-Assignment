// Generated by view binder compiler. Do not edit!
package sg.edu.np.mad.mad_assignment_cookverse.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sg.edu.np.mad.mad_assignment_cookverse.R;

public final class FragmentDiscoverBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final RecyclerView discoverRecyclerView;

  private FragmentDiscoverBinding(@NonNull FrameLayout rootView,
      @NonNull RecyclerView discoverRecyclerView) {
    this.rootView = rootView;
    this.discoverRecyclerView = discoverRecyclerView;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentDiscoverBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentDiscoverBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_discover, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentDiscoverBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.discoverRecyclerView;
      RecyclerView discoverRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (discoverRecyclerView == null) {
        break missingId;
      }

      return new FragmentDiscoverBinding((FrameLayout) rootView, discoverRecyclerView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}