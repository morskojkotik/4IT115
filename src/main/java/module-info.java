module cz.vse.basi02.adventura4it115 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;


    opens cz.vse.basi02.adventura4it115.main to javafx.fxml;
    exports cz.vse.basi02.adventura4it115.main;
}