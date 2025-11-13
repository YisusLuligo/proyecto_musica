package edu.universidad.estructuras.syncup.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CancionRow {
    private final String id;
    private final StringProperty titulo = new SimpleStringProperty();
    private final StringProperty artista = new SimpleStringProperty();
    private final StringProperty album = new SimpleStringProperty();
    private final StringProperty genero = new SimpleStringProperty();
    private final StringProperty fecha = new SimpleStringProperty();
    private final StringProperty duracion = new SimpleStringProperty();

    public CancionRow(String id, String titulo, String artista, String album, String genero, String fecha, String duracion) {
        this.id = id; this.titulo.set(titulo); this.artista.set(artista); this.album.set(album);
        this.genero.set(genero); this.fecha.set(fecha); this.duracion.set(duracion);
    }
    public String getId() { return id; }
    public StringProperty tituloProperty() { return titulo; }
    public StringProperty artistaProperty() { return artista; }
    public StringProperty albumProperty() { return album; }
    public StringProperty generoProperty() { return genero; }
    public StringProperty fechaProperty() { return fecha; }
    public StringProperty duracionProperty() { return duracion; }
}