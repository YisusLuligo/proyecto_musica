package edu.universidad.estructuras.syncup.model;


import java.util.Objects;

/**
 * RF-016: id único, titulo, artista, genero, año, duración
 * RF-017: nodo en grafo de similitud
 * RF-018: hashCode/equals basado en id
 */
public class Cancion {
    private final String id;
    private String titulo;
    private String artista;
    private String genero;
    private int anio;
    private int duracionSeg; // duración en segundos
    private String streamUrl; // para reproducción

    public Cancion(String id, String titulo, String artista, String genero, int anio, int duracionSeg, String streamUrl) {
        this.id = Objects.requireNonNull(id);
        this.titulo = Objects.requireNonNull(titulo);
        this.artista = Objects.requireNonNull(artista);
        this.genero = Objects.requireNonNull(genero);
        this.anio = anio;
        this.duracionSeg = duracionSeg;
        this.streamUrl = streamUrl;
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public int getDuracionSeg() { return duracionSeg; }
    public void setDuracionSeg(int duracionSeg) { this.duracionSeg = duracionSeg; }
    public String getStreamUrl() { return streamUrl; }
    public void setStreamUrl(String streamUrl) { this.streamUrl = streamUrl; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cancion)) return false;
        Cancion cancion = (Cancion) o;
        return id.equals(cancion.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }

    @Override public String toString() { return titulo + " - " + artista; }
}
