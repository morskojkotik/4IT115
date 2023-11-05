package cz.vse.basi02.adventura4it115;

import cz.vse.basi02.adventura4it115.Item;
import cz.vse.basi02.adventura4it115.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro komplexní otestování třídy {@link Location} a {@link Item}.
 *
 * @author Ivan Bassov
 * @version @version ZS-2022-2023, 2023-01-11
 */
public class LocationItemTest
{
    @Test
    public void testExits()
    {
        Location l1 = new Location("hala");
        Location l2 = new Location("bufet");

        assertNull(l1.getExit(l1.getName()));
        assertNull(l1.getExit(l2.getName()));
        assertNull(l2.getExit(l1.getName()));

        assertFalse(l1.hasExit(l1.getName()));
        assertFalse(l1.hasExit(l2.getName()));
        assertFalse(l2.hasExit(l1.getName()));

        l1.addExit(l1);
        l1.addExit(l2);
        l2.addExit(l1);

        assertEquals(l1, l1.getExit(l1.getName()));
        assertEquals(l1, l2.getExit(l1.getName()));
        assertEquals(l2, l1.getExit(l2.getName()));
        assertNull(l1.getExit("učebna"));
        assertNull(l2.getExit("učebna"));

        assertTrue(l1.hasExit(l1.getName()));
        assertTrue(l1.hasExit(l2.getName()));
        assertTrue(l2.hasExit(l1.getName()));
        assertFalse(l1.hasExit("učebna"));
        assertFalse(l2.hasExit("učebna"));
    }
    
    @Test
    public void testItems()
    {
        Location l1 = new Location("hala");

        Item item1 = new Item("stul", true);
        Item item2 = new Item("rum", true);

        assertNull(l1.getItem(item1.getName()));
        assertNull(l1.getItem(item2.getName()));

        l1.addItem(item1);
        l1.addItem(item2);

        assertEquals(item1, l1.getItem(item1.getName()));
        assertEquals(item2, l1.getItem(item2.getName()));

        l1.removeItem(item1);
        l1.removeItem(item2);

        assertNull(l1.getItem(item1.getName()));
        assertNull(l1.getItem(item2.getName()));
        assertNull(l1.getItem("pc"));
    }
}
