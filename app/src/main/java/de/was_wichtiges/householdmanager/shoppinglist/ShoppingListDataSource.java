package de.was_wichtiges.householdmanager.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by M.Friedrich on 15.02.2017.
 */
public class ShoppingListDataSource {
    // identifies class name for log file
    private static final String LOG_TAG = ShoppingListDataSource.class.getSimpleName();

    // database and dbHelper object
    private SQLiteDatabase database;
    private ShoppingListDatabaseHelper dbHelper;

    private String[] columns = dbHelper.getAllColumnsTableShoppingList();

    /**
     * Constructor
     * @param context
     */
    public ShoppingListDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new ShoppingListDatabaseHelper(context);
    }


    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }
}
