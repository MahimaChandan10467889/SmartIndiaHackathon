package viit.com.hackathon_2k19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Faq extends AppCompatActivity {

    TextView q1,a1,q2,a2,q3,a3,q4,a4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        q1 = findViewById(R.id.q1);
        a1 = findViewById(R.id.a1);
        q2 = findViewById(R.id.q2);
        a2 = findViewById(R.id.a2);
        q3 = findViewById(R.id.q3);
        a3 = findViewById(R.id.a3);
        q4 = findViewById(R.id.q4);
        a4 = findViewById(R.id.a4);


        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a1.setVisibility(View.VISIBLE);

            }
        });
        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a2.setVisibility(View.VISIBLE);

            }
        });
        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a3.setVisibility(View.VISIBLE);

            }
        });
        q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a4.setVisibility(View.VISIBLE);

            }
        });

    }

}
