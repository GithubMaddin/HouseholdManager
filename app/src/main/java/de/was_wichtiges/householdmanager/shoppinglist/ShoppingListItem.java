package de.was_wichtiges.householdmanager.shoppinglist;

/**
 * Created by M.Friedrich on 15.02.2017.
 */
public class ShoppingListItem {
    public enum Unit {
        KG("kilogram", "kg"),     //kilo
        G("gram", "g"),      // gram
        PCS("pieces", "pcs"),    // pieces
        L("liter", "l"),      // liter
        ML("milliliter", "ml");     // milliliter

        private String name;
        private String shortName;

        Unit(String name, String shortName) {
            this.name = name;
            this.shortName = shortName;
        }

        @Override
        public String toString() {
            return name;
        }

        public static Unit getUnitFromString(String name) {
            for (Unit unit : Unit.values()) {
                if (unit.name.equals(name)) {
                    return unit;
                }
            }
            throw new RuntimeException("Cannot find unit '" + name + "'");
        }

        public String getName() {
            return name;
        }

        public String getShortName() {
            return shortName;
        }
    }

    private long itemID;
    private String name;
    private int quantity;
    private String unit;
    private boolean checked;

    private String changedBy;
    private int lastChange;

    /**
     * Constructor
     *
     * @param name:     name of product
     * @param quantity: quantity
     * @param checked:  status whether item is checked
     */
    public ShoppingListItem(String name, int quantity, String unit, boolean checked) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.checked = checked;

        // Unit.getUnitFromString(unit);
    }

    /**
     * to String
     *
     * @return Summary of shoppint list Item
     */
    @Override
    public String toString() {
        return "Item: " + name + "(" + quantity + " " + unit + "); item is checked?:" + checked;
    }

    // =============================================================
    // == GET and SET Methods ======================================
    //==============================================================

    /**
     * Returns itemID
     *
     * @return itemID
     */
    public long getItemID() {
        return itemID;
    }

    /**
     * Set itemID
     *
     * @param itemID
     */
    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    /**
     * Returns name
     *
     * @return name: namem of the Product
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     *
     * @param name: name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns quantity
     *
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set Quantity
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns unit
     *
     * @return returns unit;
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Set unit
     *
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Returns true if item is checked
     *
     * @return isChecked: status whether item is checked.
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Set checked
     *
     * @param checked
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
