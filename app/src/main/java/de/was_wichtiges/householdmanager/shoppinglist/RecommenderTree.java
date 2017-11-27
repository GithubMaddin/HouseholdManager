package de.was_wichtiges.householdmanager.shoppinglist;


import android.util.Log;

import java.util.ArrayList;
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



    /**
     * Function that updates ranking upwards
     * @param insertNode
     */
    private void updateRankingList(Node<E> insertNode){
        Node<E> currentNode = insertNode;
        while (currentNode.parent != null){
           Node<E> parentNode = currentNode.parent;
           // case ranking liste does not contain node and is not completely filled
           if (parentNode.recommendedChildren.size()<numberRankedItems){
               if (!parentNode.recommendedChildren.contains(insertNode.item)){
                   parentNode.recommendedChildren.add(insertNode.item);
               }
           }
           else { // list is filled
                if (Collections.min(parentNode.recommendedChildren).compareTo(insertNode.item) < 0) {
                    // rank is higher
                    parentNode.recommendedChildren.add(insertNode.item);
                    parentNode.recommendedChildren.remove(Collections.min(parentNode.recommendedChildren));
                }
            }
            currentNode = parentNode;
        }
    }

    public void addItem(E item) {
        String searchWord = item.getName().toLowerCase();
        updateRankingList(addNode(root, searchWord, item));
    }





/*
    private Node<E> splitNode(Node<E> oldNode, E item, int splitCharPos, String remainingSearchWord){ // splitCharPos = index of last char of new guiding word
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
            Log.i("Fall","1");
            Node<E> newItemNode = new Node<E>(newNode, item);
            newNode.childrenNodes.add(newItemNode);
            return newItemNode;
        } else {
            Log.i("Fall","2");
            Node<E> newSubGuidingNode = new Node<E>(newNode, remainingSearchWord);
            //newSubGuidingNode.recommendedChildren = oldNode.recommendedChildren;
            newNode.childrenNodes.add(newSubGuidingNode);
            //newNode.recommendedChildren = oldNode.recommendedChildren;
            Node<E> newItemNode = new Node<E>(newSubGuidingNode, item);
            newSubGuidingNode.childrenNodes.add(newItemNode);
            return newItemNode;
        }
    }
*/



    public void addNode(E item) {
        String searchWord = item.getName().toLowerCase();
        addNode(root, searchWord, item);
    }

    private Node<E> addNode(Node<E>currentNode , String searchword, E item){

        // identify item that is not the root node and does not match with the first letter
        if ((currentNode.guidingCharacters != null)
                && (currentNode.guidingCharacters.length() != 0)
                && (currentNode.guidingCharacters.charAt(0) != searchword.charAt(0))){
            return null;
        } else {
            // check if the remaining search word is the full or part of the current guidance node
            for (int i = 0; i < currentNode.guidingCharacters.length(); i++){
                if(searchword.length() == 0){
                    // split word --> case A
                    return splitNodePartialGuidingWord(currentNode,item,i);
                } else if (currentNode.guidingCharacters.charAt(i) == searchword.charAt(0)){
                    // get through node step by step and reduce search word
                    searchword = searchword.substring(1);
                } else {
                    // character differ => split word --> case
                    return splitNodeDifferingWord(currentNode, item, i, searchword);
                }
            }
            if (searchword.length() == 0) {
                // case searchword completle match current guiding node: add new item Node
                return addNewItemNode(currentNode, item);
                //TODO: identify weather item already exist
            }
        }
        // if there is a remaining part of the searchword check the children recursively:
        for (Node<E> nextNode : currentNode.childrenNodes) {
            if (nextNode.type == Node.Type.GUIDINGNODE) {
                Node<E> resultOfNode = addNode(nextNode, searchword, item);
                if (resultOfNode != null){
                    // only return result in case one of the children found a match
                    return resultOfNode;
                }
            }
        }
        // otherwise (if no matching item was found) return an empty list
        return addNewChildGuidingAndItemNode(currentNode, item, searchword);
    }

    /**
     * Help function - Adds sub-guidance node and item node
     * @param currentNode node the new node should be attached to
     * @param item item that should be added
     * @param remainingSearchWord remaining search word
     * @return
     */
    private Node<E> addNewChildGuidingAndItemNode(Node<E> currentNode, E item, String remainingSearchWord){
        Node<E> newGuidanceNode = new Node<E>(currentNode, remainingSearchWord);
        Node<E> newItemNode = new Node<E>(newGuidanceNode, item);
        newGuidanceNode.childrenNodes.add(newItemNode);
        currentNode.childrenNodes.add(newGuidanceNode);
        return newItemNode;
    }

    /**
     * Help function - Adds item node to current guidance node
     * @param currentNode node the new node should be attached to
     * @param item item that should be added
     * @return
     */
    private Node<E> addNewItemNode(Node<E> currentNode, E item){
        Node<E> newItemNode = new Node<E>(currentNode, item);
        currentNode.childrenNodes.add(newItemNode);
        return newItemNode;
    }




    private Node<E> splitNodeDifferingWord(Node<E> currentNode, E item, int splitCharIndex, String remainingSearchword){
        return addNewChildGuidingAndItemNode(splitNode(currentNode, splitCharIndex),item,remainingSearchword);
    }

    private Node<E> splitNodePartialGuidingWord(Node<E> currentNode, E item, int splitCharIndex){
        return addNewItemNode(splitNode(currentNode,splitCharIndex),item);
    }

    private Node<E> splitNode(Node<E> currentNode, int splitCharIndex){
        // create one new guiding node nearly identical to the current node
        // - first part of the old guiding word ending one character before the splitCarIndex
        Node<E> newParentGuidingNode = new Node<E>(currentNode.parent, currentNode.guidingCharacters.substring(0, splitCharIndex));
        // - same recommended children
        newParentGuidingNode.recommendedChildren = currentNode.recommendedChildren;
        // - add current node as new child of new node
        newParentGuidingNode.childrenNodes.add(currentNode);
        // return old and add new guiding node to parents
        newParentGuidingNode.parent.childrenNodes.add(newParentGuidingNode);
        newParentGuidingNode.parent.childrenNodes.remove(currentNode);

        // adapte old node
        // - change name last part of old guiding word
        currentNode.guidingCharacters = currentNode.guidingCharacters.substring(splitCharIndex);
        // - change parent to new guiding parent
        currentNode.parent = newParentGuidingNode;


        return newParentGuidingNode;
    }


    /****************************************************************************************/
/*
    public boolean checkNode(Node<E> currentNode, String searchWord, E item) {

        if (currentNode.parent != null && (searchWord.charAt(0) != currentNode.guidingCharacters.charAt(0))){
            return false;
        } else {
            int length = Math.min(currentNode.guidingCharacters.length(), searchWord.length());
            for (int i = 0; i < length; i++) {
                if (searchWord.charAt(0) == currentNode.guidingCharacters.charAt(i)) { // same character
                    searchWord = searchWord.substring(1);
                } else {
                    // different character
                    // split required
                    updateRankingList(splitNode(currentNode, item, i , searchWord));
                    Log.i("Split","Wurde gesplittet 2");
                    return true;
                }
            }
            // special case: Identical word
            if (searchWord.length() == 0 && length == currentNode.guidingCharacters.length()) {
                Node<E> newItemNode = new Node<E>(currentNode, item);
                currentNode.childrenNodes.add(newItemNode);
                updateRankingList(newItemNode);
                return true;
            } // case: new shorter word
                else if (searchWord.length() == 0) {
                updateRankingList(splitNode(currentNode, item, length, ""));
                Log.i("Split","Wurde gesplittet");
                return true;

            } else {
                // Seachword > 0 => check if any of the children is suitable => rekursive call
                boolean newChildRequired = true;
                for (Node<E> nextNode : currentNode.childrenNodes){
                    if(nextNode.type== Node.Type.GUIDINGNODE){
                        if (checkNode(nextNode,searchWord,item)) {
                            newChildRequired = false;
                        }
                    }
                }
                // if none of it is suitable => create and add new child node
                if (newChildRequired){
                    addNewChildGuidingAndItemNode(currentNode,item, searchWord);
                    return true;
                } else {
                    return true;
                }
            }
        }
    }
*/



    /**
     * Return list of recommended items for inserted search word
     * @param searchword search word
     * @return list of recommended items, if searchword not included returns empty list
     */
    public List<E> searchRecommendedItems(String searchword){
        return searchRecommendedItems(root, searchword);
    }

    /**
     *  Help function for recursive call of function
     * @param currentNode currend node
     * @param searchword searchword
     * @return list of recommended items, if searchword not included returns empty list
     */
    private List<E> searchRecommendedItems(Node<E>currentNode , String searchword){

        // identify item that is not the root node and does not match with the first letter
        if ((currentNode.guidingCharacters != null)
                && (currentNode.guidingCharacters.length() != 0)
                && (currentNode.guidingCharacters.charAt(0) != searchword.charAt(0))){
            return null;
        } else {
            // check if the remaining search word is the full or part of the current guidance node
            for (int i = 0; i < currentNode.guidingCharacters.length(); i++){
                if(searchword.length() == 0){
                    return currentNode.recommendedChildren;
                } else if (currentNode.guidingCharacters.charAt(i) == searchword.charAt(0)){
                    // get through node step by step and reduce search word
                    searchword = searchword.substring(1);
                }
            }
            if (searchword.length() == 0) {
                return currentNode.recommendedChildren;
            }
        }
        // if there is a remaining part of the searchword check the children recursively:
        for (Node<E> nextNode : currentNode.childrenNodes) {
            if (nextNode.type == Node.Type.GUIDINGNODE) {
                List<E> resultOfNode = searchRecommendedItems(nextNode, searchword);
                if (resultOfNode != null){
                    // only return result in case one of the children found a match
                    return resultOfNode;
                }
            }
        }
        // otherwise (if no matching item was found) return an empty list
        return new ArrayList<E>();
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
            String recommenderNodes = "(";
            if (this.recommendedChildren.size() != 0){
                for (Item<E> item: this.recommendedChildren){
                    recommenderNodes = recommenderNodes + item.getName() + ", ";
                }
            }

            recommenderNodes +=")";
            Log.i("Knoten:", placeholder + (type==Type.ITEM?"I: '" + item.getName() + "'" : "G: '" + guidingCharacters+"'" + recommenderNodes) + "parent:" + (parent!=null?parent.guidingCharacters: "null"));
            for (Node<E> node : childrenNodes) {
                node.debug(placeholder + "  ");
            }


        }



    }

    public void debug() {
        root.debug("");
    }

}
