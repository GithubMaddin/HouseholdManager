package de.was_wichtiges.householdmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import de.was_wichtiges.householdmanager.net.ByteLoader;
import de.was_wichtiges.householdmanager.shoppinglist.ShoppingList;

import java.io.File;

/**
 * Overview Activity
 */
public class Overview extends AppCompatActivity {

    public static File shoppingListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        shoppingListItem = new File(getFilesDir(), "/img/shopping_list/");
        if (!shoppingListItem.exists())
            shoppingListItem.mkdirs();

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.lay_shopping_list);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShoppingList.class);
                startActivity(intent);
            }
        });

        ByteLoader.download("http://universal.was-wichtiges.de/img/" + ByteLoader.fileMD5("Banane"), shoppingListItem.getAbsolutePath() + "/" + ByteLoader.fileMD5("Banane"), true);
        ByteLoader.download("http://universal.was-wichtiges.de/img/" + ByteLoader.fileMD5("Apfel"), shoppingListItem.getAbsolutePath() + "/" + ByteLoader.fileMD5("Apfel"), true);
    }
}
