package sg.edu.np.mad.mad_assignment_cookverse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView test=findViewById(R.id.test);
        test.setText("Testing repo to get A+ :D");
        TextView test2=findViewById(R.id.test2);
        test2.setText("Hello ash ayrton kok kai and minh");
    }
}