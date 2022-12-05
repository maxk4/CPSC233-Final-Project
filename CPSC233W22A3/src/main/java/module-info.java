module mvh.cpsc233w22a3 {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;


    opens mvh.app to javafx.fxml;
    exports mvh.app;
}