package de.was_wichtiges.householdmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import de.was_wichtiges.householdmanager.shoppinglist.ShoppingListItem;

public class Recommender extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommender);

        ImageButton erdbeere = (ImageButton) findViewById(R.id.btn_erdbeere);
        erdbeere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        ImageButton banane = (ImageButton) findViewById(R.id.btn_banane);
        banane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
