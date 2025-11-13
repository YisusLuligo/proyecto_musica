package edu.universidad.estructuras.syncup.controller;

import edu.universidad.estructuras.syncup.app.SyncUpApp;
import edu.universidad.estructuras.syncup.model.Cancion;
import edu.universidad.estructuras.syncup.service.BusquedaService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class BibliotecaController {
    @FXML private TextField tfArtista, tfGenero, tfAnio;
    @FXML private ComboBox<BusquedaService.Logica> cbLogica;
    @FXML private TableView<Cancion> tablaResultados;
    @FXML private Label lblInfo;

    @FXML
    public void initialize() {
        cbLogica.setItems(FXCollections.observableArrayList(BusquedaService.Logica.AND, BusquedaService.Logica.OR));
        cbLogica.getSelectionModel().select(BusquedaService.Logica.AND);
        tablaResultados.setItems(FXCollections.observableArrayList(SyncUpApp.catalogoService.listar()));
    }

    @FXML
    public void onBuscar() {
        try {
            Optional<String> artista = opt(tfArtista.getText());
            Optional<String> genero  = opt(tfGenero.getText());
            Optional<Integer> anio   = optInt(tfAnio.getText());
            List<Cancion> res = SyncUpApp.busquedaService.buscarAvanzado(artista, genero, anio, cbLogica.getValue());
            tablaResultados.setItems(FXCollections.observableArrayList(res));
            lblInfo.setText("Resultados: " + res.size());
        } catch (InterruptedException | ExecutionException e) {
            lblInfo.setText("Error en búsqueda");
        }
    }

    @FXML
    public void onRadio() {
        Cancion sel = tablaResultados.getSelectionModel().getSelectedItem();
        if (sel == null) { lblInfo.setText("Selecciona una canción"); return; }
        Queue<Cancion> cola = SyncUpApp.recomendacionService.iniciarRadio(sel, 5);
        Alert a = new Alert(Alert.AlertType.INFORMATION, String.join("\n", cola.stream().map(Cancion::toString).toList()), ButtonType.OK);
        a.setHeaderText("Radio generada"); a.showAndWait();
    }

    private Optional<String> opt(String s) { s = s == null ? "" : s.trim(); return s.isEmpty() ? Optional.empty() : Optional.of(s); }
    private Optional<Integer> optInt(String s) { try { return s.isBlank() ? Optional.empty() : Optional.of(Integer.parseInt(s.trim())); } catch (Exception e){ return Optional.empty(); } }
}
