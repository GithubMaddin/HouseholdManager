package de.was_wichtiges.householdmanager.shoppinglist;

import java.util.ArrayList;

/**
 * Created by Maddin on 05.04.2017.
 */
public class RecommenderNode {
    private String name;
    private ArrayList<RecommenderNode> childrenNodes;
    public enum Type{
        CATEGORY, ITEM;
    };
}
