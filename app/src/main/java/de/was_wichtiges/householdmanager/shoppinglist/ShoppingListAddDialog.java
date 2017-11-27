package de.was_wichtiges.householdmanager.shoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.*;
import com.squareup.picasso.Picasso;
import de.was_wichtiges.householdmanager.Overview;
import de.was_wichtiges.householdmanager.R;
import de.was_wichtiges.householdmanager.net.ByteLoader;

import java.io.File;

/**
 * Created by jfmarten on 25.11.17.
 */
public class ShoppingListAddDialog extends DialogFragment {

    ShoppingListAddDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ShoppingListAddDialogListener) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.dialog_shopping_list_add, null);
        final Spinner spinner = (Spinner) rl.findViewById(R.id.spn_unit);
        final TextView name = (TextView) rl.findViewById(R.id.txt_name);
        name.setText(bundle.getString("name"));
        final EditText quantity = (EditText) rl.findViewById(R.id.edt_quantity);
        quantity.setText(bundle.getInt("quantity") + "");
        ImageView image = (ImageView) rl.findViewById(R.id.img_main);
        if (new File("file://" + Overview.shoppingListItem.getAbsolutePath() + "/" + ByteLoader.fileMD5(bundle.getString("name")) + ".jpg").exists())
            Picasso.with(image.getContext()).load("file://" + Overview.shoppingListItem.getAbsolutePath() + "/" + ByteLoader.fileMD5(bundle.getString("name")) + ".jpg").into(image);
        else
            image.setImageResource(R.drawable.ic_broken_image_gray_24dp);
        builder.setTitle("Zur Liste hinzufügen");
        builder.setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                listener.onAdd(name.getText().toString(), Integer.parseInt(quantity.getText().toString()), spinner.getSelectedItem().toString());
            }
        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item);
        int current = 0, i = 0;
        for (ShoppingListItem.Unit unit : ShoppingListItem.Unit.values()) {
            adapter.add(unit.getShortName());
            if (bundle.getString("unit").equals(unit.getName()))
                current = i;
            i++;
        }
        spinner.setAdapter(adapter);
        spinner.setSelection(current);
        builder.setView(rl);
        return builder.create();
    }

    public interface ShoppingListAddDialogListener {
        void onAdd(String product, int quantity, String unit);
    }
}

