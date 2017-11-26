package de.was_wichtiges.householdmanager.shoppinglist;

import java.util.List;

public class CategoryTree<E extends CategoryTree.Item<E>> {

    Node root;

    public CategoryTree() {

    }

    public void addItem(Item<E> newItem){
        if (root == null){
            root = new Node(null, Node.Type.CATEGORY, newItem );
        }
    };


    public interface Item<T> extends Comparable<T>{
        String getName();
    }


    private static class Node<E extends Item<E>>{
        private List<Node<E>> childrenNodes;
        private Node<E> parent;
        public enum Type{
            CATEGORY, ITEM;
        };
        Type type;
        E item;

        public Node(Node<E> parent, Type type, E item) {
            this.parent = parent;
            this.type = type;
            this.item = item;
        }
    }

}
