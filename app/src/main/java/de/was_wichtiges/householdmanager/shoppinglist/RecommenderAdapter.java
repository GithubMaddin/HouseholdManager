package de.was_wichtiges.householdmanager.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.squareup.picasso.Picasso;
import de.was_wichtiges.householdmanager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfmarten on 25.11.17.
 */
public class RecommenderAdapter extends BaseAdapter implements Filterable {

    private RecommenderTree<ShoppingListItem> recommenderTree;

    public List<ShoppingListItem> items = new ArrayList<>();

    public RecommenderAdapter(RecommenderTree<ShoppingListItem> recommenderTree) {
        this.recommenderTree = recommenderTree;
    }

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
        if (new File(item.getImagePath()).exists())
            Picasso.with(viewGroup.getContext()).load(item.getImagePath()).into((ImageView) rl.findViewById(R.id.img_main));
        else
            ((ImageView) rl.findViewById(R.id.img_main)).setImageResource(R.drawable.ic_broken_image_gray_24dp);
        return rl;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                ArrayList<ShoppingListItem> items = new ArrayList<>();
                if (charSequence != null) {
                    items.add(new ShoppingListItem(charSequence.toString(), 1, "pcs", false));
                    items.addAll(recommenderTree.searchRecommendedItems(charSequence.toString().toLowerCase()));
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
