package de.was_wichtiges.householdmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import de.was_wichtiges.householdmanager.shoppinglist.ShoppingList;

/**
 * Overview Activity
 */
public class Overview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.lay_shopping_list);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShoppingList.class);
                startActivity(intent);
            }
        });
    }
}
