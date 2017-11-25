package de.was_wichtiges.householdmanager.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.squareup.picasso.Picasso;
import de.was_wichtiges.householdmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfmarten on 25.11.17.
 */
public class RecommenderAdapter extends BaseAdapter implements Filterable {

    public List<ShoppingListItem> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RelativeLayout rl;
        ShoppingListItem item = (ShoppingListItem) getItem(i);
        rl = (RelativeLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shopping_list_search, viewGroup, false);
        ((TextView) rl.findViewById(R.id.txt_name)).setText(item.getName());
        Picasso.with(viewGroup.getContext()).load(item.getImagePath()).into((ImageView) rl.findViewById(R.id.img_main));
        return rl;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                int s = 7;
                if (charSequence != null)
                    s -= charSequence.length();
                ArrayList<ShoppingListItem> items = new ArrayList<>();
                if (charSequence != null)
                    items.add(new ShoppingListItem(charSequence.toString(), 1, "pcs", false));
                for (int i = 0; i < s; i++) {
                    items.add(new ShoppingListItem("Banane", 1, "kg", false));
                }
                results.values = items;
                results.count = items.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                items = (ArrayList<ShoppingListItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
