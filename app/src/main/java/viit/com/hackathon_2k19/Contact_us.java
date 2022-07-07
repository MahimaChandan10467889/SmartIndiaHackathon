package viit.com.hackathon_2k19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Contact_us extends AppCompatActivity {

    TextView n1,i1,n2,i2,n3,i3,n4,i4,n5,i5,n6,i6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        n1=findViewById(R.id.n1);
        i1=findViewById(R.id.i1);
        n2=findViewById(R.id.n2);
        i2=findViewById(R.id.i2);
        n3=findViewById(R.id.n3);
        i3=findViewById(R.id.i3);
        n4=findViewById(R.id.n4);
        i4=findViewById(R.id.i4);
        n5=findViewById(R.id.n5);
        i5=findViewById(R.id.i5);
        n6=findViewById(R.id.n6);
        i6=findViewById(R.id.i6);


        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1.setVisibility(View.VISIBLE);
            }
        });

        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.setVisibility(View.VISIBLE);
            }
        });

        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i3.setVisibility(View.VISIBLE);
            }
        });

        n4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i4.setVisibility(View.VISIBLE);
            }
        });

        n5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i5.setVisibility(View.VISIBLE);
            }
        });

        n6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i6.setVisibility(View.VISIBLE);
            }
        });
    }
}
