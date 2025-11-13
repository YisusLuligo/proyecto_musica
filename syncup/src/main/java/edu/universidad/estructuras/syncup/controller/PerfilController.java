package edu.universidad.estructuras.syncup.controller;


import edu.universidad.estructuras.syncup.app.SyncUpApp;
import edu.universidad.estructuras.syncup.model.Cancion;
import edu.universidad.estructuras.syncup.model.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;

public class PerfilController {
    @FXML private TextField tfNombre;
    @FXML private PasswordField pfPassword;
    @FXML private ComboBox<Cancion> cbCanciones;
    @FXML private ListView<Cancion> listFavoritos;
    @FXML private Label lblMsg;

    @FXML
    public void initialize() {
        Usuario u = SyncUpApp.usuarioActual;
        tfNombre.setText(u.getNombre());
        pfPassword.setText(u.getPassword());
        cbCanciones.setItems(FXCollections.observableArrayList(SyncUpApp.catalogoService.listar()));
        listFavoritos.setItems(FXCollections.observableArrayList(u.getFavoritos()));
    }

    @FXML
    public void onGuardar() {
        SyncUpApp.usuarioActual.setNombre(tfNombre.getText().trim());
        SyncUpApp.usuarioActual.setPassword(pfPassword.getText().trim());
        lblMsg.setText("Guardado");
    }

    @FXML
    public void onAgregarFav() {
        Cancion c = cbCanciones.getSelectionModel().getSelectedItem();
        if (c != null) {
            SyncUpApp.usuarioActual.agregarFavorito(c);
            listFavoritos.setItems(FXCollections.observableArrayList(SyncUpApp.usuarioActual.getFavoritos()));
        }
    }

    @FXML
    public void onExportarCSV() {
        try {
            File tmp = File.createTempFile("favoritos_", ".csv");
            SyncUpApp.exportService.exportarFavoritosCSV(SyncUpApp.usuarioActual, tmp);
            lblMsg.setText("Exportado: " + tmp.getAbsolutePath());
        } catch (Exception e) { lblMsg.setText("Error exportando"); }
    }
}
