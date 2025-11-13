module edu.universidad.estructuras.syncup {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.base;


    exports edu.universidad.estructuras.syncup.app;
    exports edu.universidad.estructuras.syncup.controller;
    exports edu.universidad.estructuras.syncup.model;
    exports edu.universidad.estructuras.syncup.repository;
    exports edu.universidad.estructuras.syncup.service;
    exports edu.universidad.estructuras.syncup.util;
    exports edu.universidad.estructuras.syncup;

    opens edu.universidad.estructuras.syncup.controller to javafx.fxml;
    opens edu.universidad.estructuras.syncup.model to javafx.base;
    opens edu.universidad.estructuras.syncup.app to javafx.graphics;
}