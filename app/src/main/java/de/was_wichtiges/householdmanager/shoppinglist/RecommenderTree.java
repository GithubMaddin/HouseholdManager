package de.was_wichtiges.householdmanager.shoppinglist;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Maddin on 05.04.2017.
 */
public class RecommenderTree<E extends RecommenderTree.Item<E>> {
    Node<E> root;
    int numberRankedItems;

    public interface Item<T> extends Comparable<T> {
        String getName();
    }

    public RecommenderTree(int numberRankedItems) {
        root = new Node<E>(null, "");
        this.numberRankedItems =  numberRankedItems;
    }

    private void updateRankingList(Node<E> insertNode){
        while (insertNode.parent != null){
           Node<E> parentNode = insertNode.parent;
           // case ranking liste does not contain node and is not completely filled
           if (parentNode.recommendedChildren.size()<numberRankedItems){
               if (!parentNode.recommendedChildren.contains(insertNode.item)){
                   parentNode.recommendedChildren.add(insertNode.item);
               }
           }
           else { // list is filled
                if (Collections.min(parentNode.recommendedChildren).compareTo(insertNode.item) < 0) {
                    // rank is höher
                    parentNode.recommendedChildren.add(insertNode.item);
                    parentNode.recommendedChildren.remove(Collections.min(parentNode.recommendedChildren));
                }else {
                    break;
               }
            }

        }
    }

    public void addItem(E item) {
        String searchWord = item.getName().toLowerCase();
        checkNode(root, searchWord, item);
    }

    private void splitNode(Node<E> oldNode, E item, int splitCharPos, String remainingSearchWord){ // splitCharPos = index of last char of new guiding word
        // identifify new searchword
        String newGuidingCharcters = oldNode.guidingCharacters.substring(0, splitCharPos);
        // delete old node from parents list
        oldNode.parent.childrenNodes.remove(oldNode);
        // create new Guiding Node
        Node<E> newNode = new Node<E>(oldNode.parent, newGuidingCharcters);
        // add new node to parent list
        newNode.parent.childrenNodes.add(newNode);
        // change
        oldNode.guidingCharacters = oldNode.guidingCharacters.substring(splitCharPos);
        // change parent of old character to new node
        oldNode.parent = newNode;
        // add old node
        newNode.childrenNodes.add(oldNode);
        // add item to new Node
        if(remainingSearchWord == "") {
            Node<E> newItemNode = new Node<E>(newNode, item);
            newNode.childrenNodes.add(newItemNode);
        } else {
            Node<E> newSubGuidingCharacter = new Node<E>(newNode, remainingSearchWord);
            newNode.childrenNodes.add(newSubGuidingCharacter);
            Node<E> newItemNode = new Node<E>(newSubGuidingCharacter, item);
            newSubGuidingCharacter.childrenNodes.add(newItemNode);
        }

    }





    public boolean checkNode(Node<E> currentNode, String searchWord, E item) {

        if (currentNode.parent != null && (searchWord.charAt(0) != currentNode.guidingCharacters.charAt(0))){
            return false;
        } else {
            int length = Math.min(currentNode.guidingCharacters.length(), searchWord.length());
            for (int i = 0; i < length; i++) {
                if (searchWord.charAt(0) == currentNode.guidingCharacters.charAt(i)) { // same character
                    searchWord = searchWord.substring(1);
                } else { // different character
                    splitNode(currentNode, item, i , searchWord);
                    Log.i("Split","Wurde gesplittet 2");
                    return true;
                }
            }
            // special case: Identical word
            if (searchWord.length() == 0 && length == currentNode.guidingCharacters.length()) {
                currentNode.childrenNodes.add(new Node<E>(currentNode, item));
                return true;
            } // case: new shorter word
                else if (searchWord.length() == 0) {
                splitNode(currentNode, item, length, "");
                Log.i("Split","Wurde gesplittet");
                return true;

            }
            if (searchWord.length() > 0) {
                // Kinder aufrufen
                // check children and add if needed
                boolean newChildRequired = true;
                for (Node<E> nextNode : currentNode.childrenNodes){
                    if(nextNode.type== Node.Type.GUIDINGNODE){
                        if (checkNode(nextNode,searchWord,item)) {
                            newChildRequired = false;
                            break;
                        }
                    }
                }
                if (newChildRequired){
                    Node<E> newGuidanceNode = new Node<E>(root, searchWord);
                    newGuidanceNode.childrenNodes.add(new Node<E>(newGuidanceNode, item));
                    currentNode.childrenNodes.add(newGuidanceNode);
                    Log.i("Split","Neues Kind");
                }
                return true;
            }
        }
        return false;
    }




    /**
     * Node
     *
     * @param <E> item
     */
    private static class Node<E extends Item<E>>  {
        private List<Node<E>> childrenNodes;
        private Node<E> parent;
        public enum Type {
            GUIDINGNODE, ITEM;
        }

        Type type;
        String guidingCharacters;
        E item;


        private List<E> recommendedChildren;

        public Node(Node<E> parent, String guidingCharacters, E item) {
            this.parent = parent;
            if (guidingCharacters != null) {
                this.type = Type.GUIDINGNODE;
                this.guidingCharacters = guidingCharacters;
            }
            if (item != null) {
                this.type = Type.ITEM;
                this.item = item;
            }
            this.childrenNodes = new ArrayList<>();
            this.recommendedChildren = new ArrayList<>();
        }

        public Node(Node<E> parent, String guidingCharacters) {
            this(parent, guidingCharacters, null);
        }

        public Node(Node<E> parent, E item) {
            this(parent, null, item);
        }



        public void debug(String placeholder) {
            Log.i("Knoten:", placeholder + (type==Type.ITEM?"I: '" + item.getName() + "'" : "G: '" + guidingCharacters+"'"));
            for (Node<E> node : childrenNodes) {
                node.debug(placeholder + "  ");
            }


        }



    }

    public void debug() {
        root.debug("");
    }

}
