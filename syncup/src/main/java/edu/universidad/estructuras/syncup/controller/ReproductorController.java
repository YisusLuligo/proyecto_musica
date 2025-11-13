package edu.universidad.estructuras.syncup.controller;

import edu.universidad.estructuras.syncup.app.SyncUpApp;
import edu.universidad.estructuras.syncup.model.Cancion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


public class ReproductorController {
    @FXML private ComboBox<Cancion> cbCancion;
    @FXML private Pane mediaPane;
    @FXML private Label lblEstado;

    private MediaPlayer player;

    @FXML
    public void initialize() {
        cbCancion.setItems(FXCollections.observableArrayList(SyncUpApp.catalogoService.listar()));
    }

    @FXML
    public void onPlay() {
        Cancion c = cbCancion.getSelectionModel().getSelectedItem();
        if (c == null) { lblEstado.setText("Selecciona una canción"); return; }
        onStop();
        try {
            Media media = new Media(c.getStreamUrl());
            player = new MediaPlayer(media);
            MediaView view = new MediaView(player);
            view.fitWidthProperty().bind(mediaPane.widthProperty());
            view.fitHeightProperty().bind(mediaPane.heightProperty());
            mediaPane.getChildren().setAll(view);
            player.play();
            lblEstado.setText("Reproduciendo: " + c);
        } catch (Exception e) {
            lblEstado.setText("Error reproduciendo (URL inválida o codec)");
        }
    }

    @FXML
    public void onStop() {
        if (player != null) {
            player.stop();
            player.dispose();
            player = null;
            mediaPane.getChildren().clear();
        }
    }
}
