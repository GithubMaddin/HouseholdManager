package de.was_wichtiges.householdmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import de.was_wichtiges.householdmanager.net.ByteLoader;
import de.was_wichtiges.householdmanager.shoppinglist.RecommenderTree;
import de.was_wichtiges.householdmanager.shoppinglist.ShoppingList;
import de.was_wichtiges.householdmanager.shoppinglist.ShoppingListItem;


import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        ByteLoader.download("http://universal.was-wichtiges.de/img/" + ByteLoader.fileMD5("Banane"), shoppingListItem.getAbsolutePath() + "/" + ByteLoader.fileMD5("Banane")+".jpg", false);
        ByteLoader.download("http://universal.was-wichtiges.de/img/" + ByteLoader.fileMD5("Apfel"), shoppingListItem.getAbsolutePath() + "/" + ByteLoader.fileMD5("Apfel")+".jpg", false);


        RecommenderTree<ShoppingListItem> recTree = new RecommenderTree<>(5);
        /*recTree.addItem(new ShoppingListItem("aabb", 3, "kg", false,6));
        recTree.addItem(new ShoppingListItem("aacc", 3, "kg", false,5));
        recTree.addItem(new ShoppingListItem("aabba", 3, "kg", false,100));
        recTree.addItem(new ShoppingListItem("aabbe", 3, "kg", false,3));
        recTree.addItem(new ShoppingListItem("aa", 3, "kg", false,78));
*//*
        recTree.addItem(new ShoppingListItem("aaaa", 3, "kg", false, 201));
        recTree.addItem(new ShoppingListItem("aack", 3, "kg", false, 201));
        recTree.addItem(new ShoppingListItem("bbbb", 3, "kg", false, 202));
        recTree.addItem(new ShoppingListItem("cccc", 3, "kg", false, 203));
        recTree.addItem(new ShoppingListItem("dddd", 3, "kg", false, 204));
        recTree.addItem(new ShoppingListItem("eeee", 3, "kg", false, 205));
        recTree.addItem(new ShoppingListItem("ffff", 3, "kg", false, 206));

        recTree.addItem(new ShoppingListItem("aaaax", 3, "kg", false, 201));
        recTree.addItem(new ShoppingListItem("bbbbx", 3, "kg", false, 202));
        recTree.addItem(new ShoppingListItem("ccccx", 3, "kg", false, 203));
        recTree.addItem(new ShoppingListItem("ddddx", 3, "kg", false, 204));
        recTree.addItem(new ShoppingListItem("eeeex", 3, "kg", false, 205));
        recTree.addItem(new ShoppingListItem("ffffx", 3, "kg", false, 206));

        recTree.addItem(new ShoppingListItem("aaaay", 3, "kg", false, 201));
        recTree.addItem(new ShoppingListItem("bbbby", 3, "kg", false, 202));
        recTree.addItem(new ShoppingListItem("ccccy", 3, "kg", false, 203));
        recTree.addItem(new ShoppingListItem("ddddy", 3, "kg", false, 204));
        recTree.addItem(new ShoppingListItem("eeeey", 3, "kg", false, 205));
        recTree.addItem(new ShoppingListItem("ffffy", 3, "kg", false, 206));


        recTree.debug();


        Log.i("Listen", " ");
        List<ShoppingListItem> abc = recTree.searchRecommendedItems("");
        Collections.sort(abc, new Comparator<ShoppingListItem>() {
            @Override
            public int compare(ShoppingListItem shoppingListItem, ShoppingListItem t1) {
                return -shoppingListItem.compareTo(t1);
            }
        });
        for (ShoppingListItem curItem : abc) {
            Log.i("Listenitem", curItem.getName());
        }
        ;*/
        /*recTree.addItem(new ShoppingListItem("aa", 3, "kg", false));
        recTree.addItem(new ShoppingListItem("aabb", 3, "kg", false));
        recTree.addItem(new ShoppingListItem("aacc", 3, "kg", false));
        recTree.addItem(new ShoppingListItem("aabbdd", 3, "kg", false));
        recTree.addItem(new ShoppingListItem("aabbee", 3, "kg", false));
        recTree.addItem(new ShoppingListItem("ccasdfsadf", 3, "kg", false));
        recTree.addItem(new ShoppingListItem("aabbef", 3, "kg", false));
        */
        //recTree
        // recTree.addItem(new ShoppingListItem("Bananenbrot", 3, "kg", false));
        //recTree.addItem(new ShoppingListItem("Banane", 3, "kg", false));
        //  recTree.addItem(new ShoppingListItem("Bakterien", 3, "kg", false));

        //recTree.addItem(new ShoppingListItem("Apfel", 3, "kg", false));
        //recTree.addItem(new ShoppingListItem("Ban", 3, "kg", false));
        //recTree.addItem(new ShoppingListItem("Merle", 3, "kg", false));
        //recTree.addItem(new ShoppingListItem("Merle", 3, "kg", false));
        //recTree.addItem(new ShoppingListItem("Banane", 3, "kg", false));


    }

    ;
}

