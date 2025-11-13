package edu.universidad.estructuras.syncup.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UsuarioRow {
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty estado = new SimpleStringProperty();
    private final StringProperty registro = new SimpleStringProperty();
    private final StringProperty ultima = new SimpleStringProperty();
    private final StringProperty playlists = new SimpleStringProperty();

    public UsuarioRow(String nombre, String email, String estado, String registro, String ultima, String playlists) {
        this.nombre.set(nombre); this.email.set(email); this.estado.set(estado);
        this.registro.set(registro); this.ultima.set(ultima); this.playlists.set(playlists);
    }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty emailProperty() { return email; }
    public StringProperty estadoProperty() { return estado; }
    public StringProperty registroProperty() { return registro; }
    public StringProperty ultimaProperty() { return ultima; }
    public StringProperty playlistsProperty() { return playlists; }
}
