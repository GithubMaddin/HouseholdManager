package de.was_wichtiges.householdmanager.shoppinglist;

/**
 * Created by M.Friedrich on 15.02.2017.
 */
public class ShoppingListItem {
    public enum Unit {
        KG,     //kilo
        G,      // gram
        PCS,    // pieces
        L,      // liter
        ML;     // milliliter
    }

    private long itemID;
    private String name;
    private int quantity;
    private Unit unit;
    private boolean checked;

    /**
     * Constructor
     * @param name: name of product
     * @param quantity: quantity
     * @param unit: unit
     * @param checked: status whether item is checked
     */
    public ShoppingListItem (String name, int quantity, Unit unit, boolean checked ){
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.checked = checked;
    }

    // =============================================================
    // == GET and SET Methods ======================================
    //==============================================================

    /**
     * Returns itemID
     * @return itemID
     */
    public long getItemID() {
        return itemID;
    }

    /**
     * Set itemID
     * @param itemID
     */
    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    /**
     * Returns name
     * @return name: namem of the Product
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param name: name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns quantity
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set Quantity
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns unit
     * @return returns unit;
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * Set unit
     * @param unit
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * Returns true if item is checked
     * @return isChecked: status whether item is checked.
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Set checked
     * @param checked
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
