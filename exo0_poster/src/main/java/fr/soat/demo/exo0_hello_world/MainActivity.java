package fr.soat.demo.exo0_hello_world;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Replace with right method coming from DataBindingUtils
        setContentView(R.layout.view_poster);

        // TODO Replace theses lines with the code giving the data to the binding
        TextView textView = (TextView) findViewById(R.id.textview);
        textView.setText("Poster");
    }
}
