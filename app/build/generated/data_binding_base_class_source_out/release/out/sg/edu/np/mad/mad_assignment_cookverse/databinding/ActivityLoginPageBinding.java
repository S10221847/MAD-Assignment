// Generated by view binder compiler. Do not edit!
package sg.edu.np.mad.mad_assignment_cookverse.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sg.edu.np.mad.mad_assignment_cookverse.R;

public final class ActivityLoginPageBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout coordinatorLayout;

  @NonNull
  public final EditText editmyEmailAdd;

  @NonNull
  public final EditText editmyPassword;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final Button myLoginButton;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView userSignup;

  private ActivityLoginPageBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout coordinatorLayout, @NonNull EditText editmyEmailAdd,
      @NonNull EditText editmyPassword, @NonNull ImageView imageView, @NonNull Button myLoginButton,
      @NonNull TextView textView, @NonNull TextView userSignup) {
    this.rootView = rootView;
    this.coordinatorLayout = coordinatorLayout;
    this.editmyEmailAdd = editmyEmailAdd;
    this.editmyPassword = editmyPassword;
    this.imageView = imageView;
    this.myLoginButton = myLoginButton;
    this.textView = textView;
    this.userSignup = userSignup;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginPageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login_page, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginPageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout coordinatorLayout = (ConstraintLayout) rootView;

      id = R.id.editmyEmailAdd;
      EditText editmyEmailAdd = ViewBindings.findChildViewById(rootView, id);
      if (editmyEmailAdd == null) {
        break missingId;
      }

      id = R.id.editmyPassword;
      EditText editmyPassword = ViewBindings.findChildViewById(rootView, id);
      if (editmyPassword == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.myLoginButton;
      Button myLoginButton = ViewBindings.findChildViewById(rootView, id);
      if (myLoginButton == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.userSignup;
      TextView userSignup = ViewBindings.findChildViewById(rootView, id);
      if (userSignup == null) {
        break missingId;
      }

      return new ActivityLoginPageBinding((ConstraintLayout) rootView, coordinatorLayout,
          editmyEmailAdd, editmyPassword, imageView, myLoginButton, textView, userSignup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
