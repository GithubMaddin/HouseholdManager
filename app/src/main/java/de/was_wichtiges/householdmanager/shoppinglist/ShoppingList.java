
package de.was_wichtiges.householdmanager.shoppinglist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.was_wichtiges.householdmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M.Friedrich on 15.02.2017.
 */
public class ShoppingList extends AppCompatActivity {

    public static final String LOG_TAG = ShoppingList.class.getSimpleName();

    private ShoppingListDataSource dataSource;

    private ListView listShopping;

    /**
     * when activity is activated
     * - create new database object
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        listShopping = (ListView) findViewById(R.id.lst_shopping);

        ArrayList<String> data = new ArrayList<>();

        Log.d(LOG_TAG, "Create new database object");
        dataSource = new ShoppingListDataSource(this);
        dataSource.open();

        for (ShoppingListItem shoppingListItem : dataSource.getAllShoppingListItems()) {
            data.add(shoppingListItem.getName() + " " + shoppingListItem.getQuantity() + " " + shoppingListItem.getUnit());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, data);
        listShopping.setAdapter(adapter);
    }

    /**
     * If activity retuns from paused status
     * - open database
     */
    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "Open data source.");
        dataSource.open();


        ShoppingListItem shoppingListItem = dataSource.createShoppingMemo("Banane", 5);
        Log.d(LOG_TAG, "folgendermaßen sieht das neue Ding  aus " + shoppingListItem.toString());
        Log.d(LOG_TAG, "Es wurde der folgende Eintrag in die Datenbank geschrieben:");
        Log.d(LOG_TAG, "ID: " + shoppingListItem.getItemID() + ", Inhalt: " + shoppingListItem.toString());
        Log.d(LOG_TAG, "Und so die ganze Liste sieht das neue Ding  aus ");
        List<ShoppingListItem> abc = dataSource.getAllShoppingListItems();


        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");


    }

    /**
     * When activity is paused
     * - close database
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Close data source.");
        dataSource.close();
    }


}
