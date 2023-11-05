package cz.vse.basi02.adventura4it115.main;

import cz.vse.basi02.adventura4it115.Location;
import javafx.scene.control.ListCell;

public class ListCellLocation extends ListCell<Location> {

    @Override
    protected void updateItem(Location location, boolean b) {
        super.updateItem(location, b);
        if(b){
            setText(null);
        } else
        setText(location.getName());

    }
}
