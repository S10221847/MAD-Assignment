package sg.edu.np.mad.mad_assignment_cookverse;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import sg.edu.np.mad.mad_assignment_cookverse.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //Setting first fragment to show personal recipe
        replaceFragment(new UserRecipeFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

        Intent receivingEnd = getActivity().getIntent();
        String currentUsername = receivingEnd.getStringExtra("username");
        String userpfp = receivingEnd.getStringExtra("image");
        String userbio = receivingEnd.getStringExtra("bio");
        TextView currentName =  rootView.findViewById(R.id.userName);
        TextView currentBio =  rootView.findViewById(R.id.bio);

        User currentUser = new User();
        currentUser.setName(currentUsername);
        currentUser.setUserImage(userpfp);
        currentUser.setBio(userbio);

        //Find the current user among all the users in database
        currentName.setText(currentUser.getName());
        currentBio.setText(currentUser.getBio());
        ImageView myImage =rootView.findViewById(R.id.ProfileImage);
        new ImageLoadTask(currentUser.getUserImage(), myImage).execute();

        BottomNavigationView BNV =rootView.findViewById(R.id.bottomNavigationView2);
        //Switch among fragments when clicking them
        BNV.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.personal:
                    replaceFragment(new UserRecipeFragment());
                    break;
                case R.id.liked:
                    replaceFragment(new LikedRecipeFragment());
                    break;
            }
            return true;
        });
        return rootView;
    }
    //Method for showing fragments
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FRAGMENT_PLACEHOLDER, fragment);
        fragmentTransaction.commit();
    }
}