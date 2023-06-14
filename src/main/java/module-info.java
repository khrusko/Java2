module hr.algebra.khruskoj2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.naming;
    requires java.xml;


    opens hr.algebra.khruskoj2 to javafx.fxml;
    exports hr.algebra.khruskoj2;
    exports hr.algebra.khruskoj2.controller;
    opens hr.algebra.khruskoj2.controller to javafx.fxml;
    exports hr.algebra.khruskoj2.rmiserver;

}