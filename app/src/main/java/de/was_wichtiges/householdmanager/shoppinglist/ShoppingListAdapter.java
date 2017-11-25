package de.was_wichtiges.householdmanager.shoppinglist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.was_wichtiges.householdmanager.R;

import java.util.List;

/**
 * Created by jfmarten on 25.11.17.
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    List<ShoppingListItem> items;

    public ShoppingListAdapter(List<ShoppingListItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShoppingListItem item = items.get(position);
        holder.name.setText(item.getName());
        holder.quantity.setText(item.getQuantity() + " " + item.getUnit());
        holder.image.setImageResource(R.mipmap.ic_launcher);
        holder.itemView.setTag(item);
        if (Math.random() > 0.5f) {
            holder.info.setImageResource(R.drawable.ic_info);
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(ShoppingListItem item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(ShoppingListItem item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(int i) {
        items.remove(i);
        notifyItemRemoved(i);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView quantity;
        ImageView info;

        ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img_main);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            quantity = (TextView) itemView.findViewById(R.id.txt_quantity);
            info = (ImageView) itemView.findViewById(R.id.img_info);
        }
    }

}
