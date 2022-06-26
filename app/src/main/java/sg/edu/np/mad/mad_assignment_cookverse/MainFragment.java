package sg.edu.np.mad.mad_assignment_cookverse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import sg.edu.np.mad.mad_assignment_cookverse.databinding.FragmentMainBinding;
import sg.edu.np.mad.mad_assignment_cookverse.ProfileFragment;

public class MainFragment extends AppCompatActivity {
    FragmentMainBinding binding;
    DBHandler dbHandler = new DBHandler(this, null, null, 1);
    Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String currentUsername = getIntent().getStringExtra("username");
        bundle.putString("username", currentUsername);
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle);
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.discover:
                    replaceFragment(new DiscoverFragment());
                    break;
                case R.id.create:
                    replaceFragment(new CreateFragment());
                    break;
                case R.id.profile:
                    replaceFragment(profileFragment);
                    break;
            }

            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}