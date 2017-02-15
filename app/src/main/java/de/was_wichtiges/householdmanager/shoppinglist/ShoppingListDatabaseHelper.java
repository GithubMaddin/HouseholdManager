package de.was_wichtiges.householdmanager.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by M.Friedrich on 15.02.2017.
 */
public class ShoppingListDatabaseHelper extends SQLiteOpenHelper {

    // identifies class name for log file
    private static final String LOG_TAG = ShoppingListDatabaseHelper.class.getSimpleName();

    // database name and version
    public static final String DB_NAME = "shopping_list.db";
    public static final int DB_VERSION = 1;

    // TABLE: shopping list item
    public static final String TABLE_SHOPPING_LIST = "shopping_list_items";
    public static final String COLUMN_ID = "itemID";
    public static final String COLUMN_PRODUCT = "product";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_CHECKED = "checked";

    // table attachement
        //TODO: create table attachements

    // SQL command to create TABLE: shopping list item
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_SHOPPING_LIST +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PRODUCT + " TEXT NOT NULL, " +
                    COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                    COLUMN_CHECKED + " BOOLEAN NOT NULL DEFAULT 0);";

    // SQL command to delete TABLE: shopping list item
    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST;

    /**
     * Constructor
     * @param context
     */
    public ShoppingListDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    /**
     * The onCreate Method is called in case the database does not exist yet.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "The table " + TABLE_SHOPPING_LIST + " was created with the following SQL-command: " + SQL_CREATE ) ;
            db.execSQL(SQL_CREATE);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "An error occured when creating the table " + TABLE_SHOPPING_LIST + ": " + ex.getMessage());
        }
    }

    /**
     * The onUpgrade methode is called in case the requested version of the database is higher
     * than the current installed database version. And Upgrade of the database is required.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion){
            case 2:
                try {
                    Log.d(LOG_TAG, "Update from version 1 to 2: Colmumn ***** was added to table ****");
                    //db.execSQL(SQL_DROP);
                    //onCreate(db);
                }
                catch (Exception ex){
                    Log.e(LOG_TAG, "An error occured when updating from version 1 to 2: "+ ex.getMessage());
                }
            case 3:
                break;
            default:
                Log.d(LOG_TAG, "No suitable update for database version" + newVersion + " available");
        }
        Log.d(LOG_TAG, "Update of the database from version " + oldVersion + " to version " + newVersion + " completed successfully");

    }
}
