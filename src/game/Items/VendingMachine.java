package game.Items;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.Capability.ItemCapable;
import game.Capability.Status;

import javax.net.ssl.SSLContext;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class used to represent the vending machine from which the player can purchase items
 */
public class VendingMachine extends Item {
    private ArrayList<Item> vendingMachineItems = new ArrayList<Item>();
    private String name;
    private char displayChar;
    private ItemCapable itemCapable;


    /**
     * Constructor for VendingMachine
     */
    public VendingMachine() {
        super("vendingMachine", 'V', false);
        this.addCapability(itemCapable.VENDINGMACHINE);

    }
}




