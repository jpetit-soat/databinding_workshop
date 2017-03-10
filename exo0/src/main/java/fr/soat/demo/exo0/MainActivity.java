package fr.soat.demo.exo0;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_poster);

        TextView textView = (TextView) findViewById(R.id.textview);
        textView.setText("Poster");
    }
}
