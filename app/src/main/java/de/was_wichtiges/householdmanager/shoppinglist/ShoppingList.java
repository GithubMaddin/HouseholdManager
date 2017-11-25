
package de.was_wichtiges.householdmanager.shoppinglist;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import de.was_wichtiges.householdmanager.R;

/**
 * Created by M.Friedrich on 15.02.2017.
 */
public class ShoppingList extends AppCompatActivity implements ShoppingListAddDialog.ShoppingListAddDialogListener {

    public static final String LOG_TAG = ShoppingList.class.getSimpleName();

    private ShoppingListDataSource dataSource;

    private ShoppingListAdapter shoppingListAdapter;
    private RecommenderAdapter recommenderAdapter;

    private RecyclerView listShopping;
    private AutoCompleteTextView editSearch;

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

        listShopping = (RecyclerView) findViewById(R.id.lst_shopping);
        editSearch = (AutoCompleteTextView) findViewById(R.id.edt_search);

        Log.d(LOG_TAG, "Create new database object");
        dataSource = new ShoppingListDataSource(this);
        dataSource.open();

        shoppingListAdapter = new ShoppingListAdapter(dataSource.getAllShoppingListItems());
        listShopping.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listShopping.setAdapter(shoppingListAdapter);
        listShopping.setItemAnimator(new DefaultItemAnimator());
        listShopping.setHasFixedSize(true);

        ItemTouchHelper.Callback listShoppingCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                dataSource.deleteShoppingMemo(shoppingListAdapter.items.get(viewHolder.getAdapterPosition()));
                shoppingListAdapter.remove(viewHolder.getAdapterPosition());
            }
        };
        new ItemTouchHelper(listShoppingCallback).attachToRecyclerView(listShopping);


        recommenderAdapter = new RecommenderAdapter();
        editSearch.setAdapter(recommenderAdapter);
        editSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showAddDialog((ShoppingListItem) adapterView.getItemAtPosition(i));
                editSearch.setText("");
            }
        });
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == EditorInfo.IME_ACTION_GO) {
                    showAddDialog(new ShoppingListItem(editSearch.getText().toString(), 1, "pcs", false));
                }
                return false;
            }
        });
    }

    public void showAddDialog(ShoppingListItem item) {
        DialogFragment dialogFragment = new ShoppingListAddDialog();
        Bundle bundle = new Bundle();
        bundle.putString("name", item.getName());
        bundle.putInt("quantity", item.getQuantity());
        bundle.putString("unit", item.getUnit());
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), "ShoppingListAddDialog");
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


    @Override
    public void onAdd(String product, int quantity, String unit) {
        ShoppingListItem item = dataSource.createShoppingMemo(product, quantity, unit);
        shoppingListAdapter.add(item, 0);
    }
}
