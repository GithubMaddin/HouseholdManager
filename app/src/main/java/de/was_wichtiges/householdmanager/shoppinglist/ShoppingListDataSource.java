package de.was_wichtiges.householdmanager.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by M.Friedrich on 15.02.2017.
 */
public class ShoppingListDataSource {
    // identifies class name for log file
    private static final String LOG_TAG = ShoppingListDataSource.class.getSimpleName();

    // database and dbHelper object
    private SQLiteDatabase database;
    private ShoppingListDatabaseHelper dbHelper;

    private String[] columns = {
            ShoppingListDatabaseHelper.COLUMN_ID,
            ShoppingListDatabaseHelper.COLUMN_PRODUCT,
            ShoppingListDatabaseHelper.COLUMN_QUANTITY,
            ShoppingListDatabaseHelper.COLUMN_CHECKED
    };

    /**
     * Constructor
     * @param context
     */
    public ShoppingListDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new ShoppingListDatabaseHelper(context);
    }

    /**
     * Open Database
     */
    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    /**
     * Close Database
     */
    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    /**
     * insert new shopping List Item into database and returns shopping list item object
     * @param product
     * @param quantity
     * @return
     */
    public ShoppingListItem createShoppingMemo(String product, int quantity) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListDatabaseHelper.COLUMN_PRODUCT, product);
        values.put(ShoppingListDatabaseHelper.COLUMN_QUANTITY, quantity);

        long insertId = database.insert(ShoppingListDatabaseHelper.TABLE_SHOPPING_LIST, null, values);

        Log.d(LOG_TAG, "xxxxxxxxxx Eintrag in DB eingetragen xxxxxxxxx");

        Cursor cursor = database.query(ShoppingListDatabaseHelper.TABLE_SHOPPING_LIST,
                columns, ShoppingListDatabaseHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        ShoppingListItem shoppingMemo = cursorToShoppingMemo(cursor);
        cursor.close();

        return shoppingMemo;
    }

    /**
     * delets shoppingListItem from Database
     * @param shoppingListItem
     */
    public void deleteShoppingMemo(ShoppingListItem shoppingListItem) {
        long id = shoppingListItem.getItemID();

        database.delete(ShoppingListDatabaseHelper.TABLE_SHOPPING_LIST,
                ShoppingListDatabaseHelper.COLUMN_ID + "=" + id,
                null);

        Log.d(LOG_TAG, "Item deleted! ID: " + id + " content: " + shoppingListItem.toString());
    }

    /**
     * updates ShoppingListItem in database
     * @param id
     * @param newProduct
     * @param newQuantity
     * @param newChecked
     * @return
     */
    public ShoppingListItem updateShoppingMemo(long id, String newProduct, int newQuantity, boolean newChecked) {
        int intValueChecked = (newChecked)? 1 : 0;

        ContentValues values = new ContentValues();
        values.put(ShoppingListDatabaseHelper.COLUMN_PRODUCT, newProduct);
        values.put(ShoppingListDatabaseHelper.COLUMN_QUANTITY, newQuantity);
        values.put(ShoppingListDatabaseHelper.COLUMN_CHECKED, intValueChecked);

        database.update(ShoppingListDatabaseHelper.TABLE_SHOPPING_LIST,
                values,
                ShoppingListDatabaseHelper.COLUMN_ID + "=" + id,
                null);

        Cursor cursor = database.query(ShoppingListDatabaseHelper.TABLE_SHOPPING_LIST,
                columns, ShoppingListDatabaseHelper.COLUMN_ID + "=" + id,
                null, null, null, null);

        cursor.moveToFirst();
        ShoppingListItem shoppingListItem = cursorToShoppingMemo(cursor);
        cursor.close();

        return shoppingListItem;
    }


    /**
     * Converts cursor to ShoppingListItem
     * @param cursor
     * @return ShoppingListItem
     */

    private ShoppingListItem cursorToShoppingMemo(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(ShoppingListDatabaseHelper.COLUMN_ID);
        int idProduct = cursor.getColumnIndex(ShoppingListDatabaseHelper.COLUMN_PRODUCT);
        int idQuantity = cursor.getColumnIndex(ShoppingListDatabaseHelper.COLUMN_QUANTITY);
        int idChecked = cursor.getColumnIndex(ShoppingListDatabaseHelper.COLUMN_CHECKED);

        String product = cursor.getString(idProduct);
        int quantity = cursor.getInt(idQuantity);
        long id = cursor.getLong(idIndex);
        int intValueChecked = cursor.getInt(idChecked);

        boolean isChecked = (intValueChecked != 0);

        ShoppingListItem shoppingListItem = new ShoppingListItem(product, quantity, isChecked);

        return shoppingListItem;
    }

    /**
     * Returns a list of all ShoppingListItems from the database
     * @return
     */

    public List<ShoppingListItem> getAllShoppingListItems() {
        List<ShoppingListItem> shoppingListItemList = new ArrayList<>();

        Cursor cursor = database.query(ShoppingListDatabaseHelper.TABLE_SHOPPING_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        ShoppingListItem shoppingListItem;

        while(!cursor.isAfterLast()) {
            shoppingListItem = cursorToShoppingMemo(cursor);
            shoppingListItemList.add(shoppingListItem);
            Log.d(LOG_TAG, "ID: " + shoppingListItem.getItemID() + ", Inhalt: " + shoppingListItem.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return shoppingListItemList;
    }

}
