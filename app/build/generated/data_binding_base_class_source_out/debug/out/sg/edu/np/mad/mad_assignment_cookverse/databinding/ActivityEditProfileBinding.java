// Generated by view binder compiler. Do not edit!
package sg.edu.np.mad.mad_assignment_cookverse.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import sg.edu.np.mad.mad_assignment_cookverse.R;

public final class ActivityEditProfileBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final ImageView backArrowEditProfile;

  @NonNull
  public final EditText editBio;

  @NonNull
  public final EditText editName;

  @NonNull
  public final EditText editPasswordProfile;

  @NonNull
  public final EditText editPasswordProfileAgain;

  @NonNull
  public final ShapeableImageView editPic;

  @NonNull
  public final FloatingActionButton editPicButton;

  @NonNull
  public final Button editSave;

  @NonNull
  public final TextView textView10;

  @NonNull
  public final TextView textView11;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textView5;

  @NonNull
  public final TextView textView6;

  private ActivityEditProfileBinding(@NonNull ScrollView rootView,
      @NonNull ImageView backArrowEditProfile, @NonNull EditText editBio,
      @NonNull EditText editName, @NonNull EditText editPasswordProfile,
      @NonNull EditText editPasswordProfileAgain, @NonNull ShapeableImageView editPic,
      @NonNull FloatingActionButton editPicButton, @NonNull Button editSave,
      @NonNull TextView textView10, @NonNull TextView textView11, @NonNull TextView textView3,
      @NonNull TextView textView5, @NonNull TextView textView6) {
    this.rootView = rootView;
    this.backArrowEditProfile = backArrowEditProfile;
    this.editBio = editBio;
    this.editName = editName;
    this.editPasswordProfile = editPasswordProfile;
    this.editPasswordProfileAgain = editPasswordProfileAgain;
    this.editPic = editPic;
    this.editPicButton = editPicButton;
    this.editSave = editSave;
    this.textView10 = textView10;
    this.textView11 = textView11;
    this.textView3 = textView3;
    this.textView5 = textView5;
    this.textView6 = textView6;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityEditProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityEditProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_edit_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityEditProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backArrowEditProfile;
      ImageView backArrowEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (backArrowEditProfile == null) {
        break missingId;
      }

      id = R.id.editBio;
      EditText editBio = ViewBindings.findChildViewById(rootView, id);
      if (editBio == null) {
        break missingId;
      }

      id = R.id.editName;
      EditText editName = ViewBindings.findChildViewById(rootView, id);
      if (editName == null) {
        break missingId;
      }

      id = R.id.editPasswordProfile;
      EditText editPasswordProfile = ViewBindings.findChildViewById(rootView, id);
      if (editPasswordProfile == null) {
        break missingId;
      }

      id = R.id.editPasswordProfileAgain;
      EditText editPasswordProfileAgain = ViewBindings.findChildViewById(rootView, id);
      if (editPasswordProfileAgain == null) {
        break missingId;
      }

      id = R.id.editPic;
      ShapeableImageView editPic = ViewBindings.findChildViewById(rootView, id);
      if (editPic == null) {
        break missingId;
      }

      id = R.id.editPicButton;
      FloatingActionButton editPicButton = ViewBindings.findChildViewById(rootView, id);
      if (editPicButton == null) {
        break missingId;
      }

      id = R.id.editSave;
      Button editSave = ViewBindings.findChildViewById(rootView, id);
      if (editSave == null) {
        break missingId;
      }

      id = R.id.textView10;
      TextView textView10 = ViewBindings.findChildViewById(rootView, id);
      if (textView10 == null) {
        break missingId;
      }

      id = R.id.textView11;
      TextView textView11 = ViewBindings.findChildViewById(rootView, id);
      if (textView11 == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = ViewBindings.findChildViewById(rootView, id);
      if (textView5 == null) {
        break missingId;
      }

      id = R.id.textView6;
      TextView textView6 = ViewBindings.findChildViewById(rootView, id);
      if (textView6 == null) {
        break missingId;
      }

      return new ActivityEditProfileBinding((ScrollView) rootView, backArrowEditProfile, editBio,
          editName, editPasswordProfile, editPasswordProfileAgain, editPic, editPicButton, editSave,
          textView10, textView11, textView3, textView5, textView6);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
