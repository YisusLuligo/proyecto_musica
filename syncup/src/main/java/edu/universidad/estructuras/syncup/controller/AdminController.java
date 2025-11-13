package edu.universidad.estructuras.syncup.controller;


import edu.universidad.estructuras.syncup.app.Router;
import edu.universidad.estructuras.syncup.app.SyncUpApp;
import edu.universidad.estructuras.syncup.model.Cancion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class AdminController {
    @FXML private Label lblCountCanciones, lblCountUsuarios;
    @FXML private TableView<CancionRow> tablaCanciones;
    @FXML private TableColumn<CancionRow, String> colTitulo, colArtista, colAlbum, colGenero, colFecha, colDuracion;
    @FXML private TableColumn<CancionRow, String> colAcciones;
    @FXML private TextField tfBuscarTitulo;

    @FXML private TableView<UsuarioRow> tablaUsuarios;
    @FXML private TableColumn<UsuarioRow, String> colUserNombre, colUserEmail, colUserEstado, colUserRegistro, colUserUltima, colUserPlaylists, colUserAcciones;

    private final ObservableList<CancionRow> cancionesData = FXCollections.observableArrayList();
    private final ObservableList<UsuarioRow> usuariosData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cargarCanciones();
        cargarUsuarios();
    }

    private void cargarCanciones() {
        cancionesData.clear();
        for (Cancion c : SyncUpApp.catalogoService.listar()) {
            cancionesData.add(new CancionRow(c.getId(), c.getTitulo(), c.getArtista(), "-", c.getGenero(), c.getAnio()+"", formatDur(c.getDuracionSeg())));
        }
        tablaCanciones.setItems(cancionesData);
        colTitulo.setCellValueFactory(d -> d.getValue().tituloProperty());
        colArtista.setCellValueFactory(d -> d.getValue().artistaProperty());
        colAlbum.setCellValueFactory(d -> d.getValue().albumProperty());
        colGenero.setCellValueFactory(d -> d.getValue().generoProperty());
        colFecha.setCellValueFactory(d -> d.getValue().fechaProperty());
        colDuracion.setCellValueFactory(d -> d.getValue().duracionProperty());
        colAcciones.setCellFactory(col -> new AccionesCellCancion());
        lblCountCanciones.setText(cancionesData.size() + " Canciones");
    }

    private void cargarUsuarios() {
        usuariosData.clear();
        // Datos de ejemplo
        usuariosData.add(new UsuarioRow("Ana García","ana@email.com","Activo","2023-01-15","2024-11-09","12"));
        usuariosData.add(new UsuarioRow("Carlos López","carlos@email.com","Activo","2023-03-22","2024-11-08","8"));
        usuariosData.add(new UsuarioRow("María Rodríguez","maria@email.com","Inactivo","2023-06-10","2024-10-15","15"));
        usuariosData.add(new UsuarioRow("Juan Pérez","juan@email.com","Activo","2023-08-05","2024-11-09","6"));
        tablaUsuarios.setItems(usuariosData);
        colUserNombre.setCellValueFactory(d -> d.getValue().nombreProperty());
        colUserEmail.setCellValueFactory(d -> d.getValue().emailProperty());
        colUserEstado.setCellValueFactory(d -> d.getValue().estadoProperty());
        colUserRegistro.setCellValueFactory(d -> d.getValue().registroProperty());
        colUserUltima.setCellValueFactory(d -> d.getValue().ultimaProperty());
        colUserPlaylists.setCellValueFactory(d -> d.getValue().playlistsProperty());
        colUserAcciones.setCellFactory(col -> new AccionesCellUsuario());
        lblCountUsuarios.setText(usuariosData.size() + " Usuarios");
    }

    @FXML
    public void volverInicio() {
        try { Router.goTo("view/login.fxml", "Iniciar sesión"); } catch (Exception ignored) {}
    }

    @FXML
    public void abrirBiblioteca() {
        try { Router.goTo("view/biblioteca.fxml", "Biblioteca"); } catch (Exception ignored) {}
    }

    @FXML
    public void mostrarModalAgregarCancion() {
        Stage modal = buildModal("Agregar Nueva Canción");
        VBox root = new VBox(10); root.setPadding(new Insets(12));

        TextField tfTitulo = new TextField(); tfTitulo.setPromptText("Título");
        TextField tfArtista = new TextField(); tfArtista.setPromptText("Artista");
        TextField tfAlbum = new TextField(); tfAlbum.setPromptText("Álbum");
        TextField tfDuracion = new TextField(); tfDuracion.setPromptText("Duración (seg)");
        TextField tfGenero = new TextField(); tfGenero.setPromptText("Género");
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        TextField tfUrl = new TextField(); tfUrl.setPromptText("URL de streaming");
        Button btnAgregar = new Button("Agregar"); btnAgregar.getStyleClass().add("btn-primary");

        btnAgregar.setOnAction(e -> {
            String id = "S" + (new Random().nextInt(10000));
            int durSeg = Integer.parseInt(tfDuracion.getText().trim());
            Cancion c = new Cancion(id, tfTitulo.getText().trim(), tfArtista.getText().trim(), tfGenero.getText().trim(),
                    dpFecha.getValue().getYear(), durSeg, tfUrl.getText().trim());
            SyncUpApp.catalogoService.agregarCancion(c);
            cargarCanciones();
            modal.close();
        });

        root.getChildren().addAll(tfTitulo, tfArtista, tfAlbum, tfDuracion, tfGenero, dpFecha, tfUrl, btnAgregar);
        modal.setScene(new Scene(root));
        modal.showAndWait();
    }

    @FXML
    public void mostrarModalAgregarUsuario() {
        Stage modal = buildModal("Agregar Nuevo Usuario");
        VBox root = new VBox(10); root.setPadding(new Insets(12));
        TextField tfNombre = new TextField(); tfNombre.setPromptText("Nombre");
        TextField tfEmail = new TextField(); tfEmail.setPromptText("Email");
        ComboBox<String> cbEstado = new ComboBox<>(FXCollections.observableArrayList("Activo","Inactivo")); cbEstado.getSelectionModel().select(0);
        TextField tfUsername = new TextField(); tfUsername.setPromptText("Username");
        PasswordField pf = new PasswordField(); pf.setPromptText("Contraseña");
        Button btnAgregar = new Button("Agregar"); btnAgregar.getStyleClass().add("btn-primary");
        btnAgregar.setOnAction(e -> {
            boolean ok = SyncUpApp.authService.registrar(tfUsername.getText().trim(), pf.getText().trim(), tfNombre.getText().trim());
            if (ok) { cargarUsuarios(); modal.close(); }
        });
        root.getChildren().addAll(tfNombre, tfEmail, cbEstado, tfUsername, pf, btnAgregar);
        modal.setScene(new Scene(root)); modal.showAndWait();
    }

    @FXML
    public void onAutocompletar() {
        List<String> sugerencias = SyncUpApp.catalogoService.autocompletarTitulo(tfBuscarTitulo.getText().trim());
        Alert a = new Alert(Alert.AlertType.INFORMATION, String.join("\n", sugerencias), ButtonType.OK);
        a.setHeaderText("Sugerencias de título"); a.showAndWait();
    }

    private Stage buildModal(String title) {
        Stage modal = new Stage();
        modal.initOwner(tablaCanciones.getScene().getWindow());
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle(title);
        modal.setMinWidth(420);
        modal.setMinHeight(300);
        return modal;
    }

    private String formatDur(int s) {
        int m = s / 60; int r = s % 60;
        return String.format("%d:%02d", m, r);
    }

    // Celdas de acciones para canciones
    private class AccionesCellCancion extends TableCell<CancionRow, String> {
        private final HBox box = new HBox(8);
        private final Button edit = new Button("Editar");
        private final Button del = new Button("Eliminar");
        private final Button play = new Button("Reproducir");

        AccionesCellCancion() {
            edit.getStyleClass().add("btn-secondary");
            del.getStyleClass().add("btn-danger");
            play.getStyleClass().add("btn-primary");
            box.getChildren().addAll(edit, del, play);

            edit.setOnAction(e -> {
                CancionRow row = getTableView().getItems().get(getIndex());
                SyncUpApp.cancionRepo.findById(row.getId()).ifPresent(c -> {
                    c.setTitulo(c.getTitulo()+" (Edit)");
                    SyncUpApp.catalogoService.actualizarCancion(c);
                    cargarCanciones();
                });
            });
            del.setOnAction(e -> {
                CancionRow row = getTableView().getItems().get(getIndex());
                SyncUpApp.catalogoService.eliminarCancion(row.getId());
                cargarCanciones();
            });
            play.setOnAction(e -> {
                try { Router.goTo("view/reproductor.fxml", "Reproductor"); }
                catch (Exception ignored) {}
            });
        }
        @Override protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : box);
        }
    }

    // Celdas de acciones para usuarios
    private class AccionesCellUsuario extends TableCell<UsuarioRow, String> {
        private final HBox box = new HBox(8);
        private final Button manage = new Button("Editar");
        private final Button delete = new Button("Eliminar");
        AccionesCellUsuario() {
            manage.getStyleClass().add("btn-secondary");
            delete.getStyleClass().add("btn-danger");
            box.getChildren().addAll(manage, delete);

            manage.setOnAction(e -> {
                // Aquí abrir perfil.fxml si deseas editar datos reales
                try { Router.goTo("view/perfil.fxml", "Perfil"); } catch (Exception ignored) {}
            });
            delete.setOnAction(e -> {
                UsuarioRow row = getTableView().getItems().get(getIndex());
                // eliminar del repositorio si existiera el username (no mostrado en la tabla)
                cargarUsuarios();
            });
        }
        @Override protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : box);
        }
    }

    @FXML
    public void onCargarArchivo() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Selecciona archivo plano de canciones");
        File f = chooser.showOpenDialog(tablaCanciones.getScene().getWindow());
        if (f != null) {
            try {
                int count = SyncUpApp.importService.importarDesdeArchivoPlano(f);
                cargarCanciones();
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Se cargaron " + count + " canciones", ButtonType.OK);
                a.showAndWait();
            } catch (Exception e) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Error cargando archivo", ButtonType.OK);
                a.showAndWait();
            }
        }
    }

    @FXML
    public void onRutaCanciones() {
        List<CancionRow> seleccion = tablaCanciones.getSelectionModel().getSelectedItems();
        if (seleccion.size() == 2) {
            var origen = SyncUpApp.cancionRepo.findById(seleccion.get(0).getId()).get();
            var destino = SyncUpApp.cancionRepo.findById(seleccion.get(1).getId()).get();
            var ruta = SyncUpApp.recomendacionService.rutaMasSimilar(origen, destino);
            Alert a = new Alert(Alert.AlertType.INFORMATION, String.join("\n", ruta.stream().map(Cancion::toString).toList()), ButtonType.OK);
            a.setHeaderText("Ruta más similar"); a.showAndWait();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Selecciona exactamente dos canciones", ButtonType.OK);
            a.showAndWait();
        }
    }

}
