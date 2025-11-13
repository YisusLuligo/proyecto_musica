package edu.universidad.estructuras.syncup.model;

import java.util.LinkedList;
import java.util.Objects;

/**
 * RF-013: username único, password, nombre, listaFavoritos (LinkedList<Cancion>)
 * RF-015: hashCode/equals basado en username.
 */
public class Usuario {
    private final String username;
    private String password;
    private String nombre;
    private final LinkedList<Cancion> favoritos = new LinkedList<>();

    public Usuario(String username, String password, String nombre) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.nombre = Objects.requireNonNull(nombre);
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = Objects.requireNonNull(password); }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = Objects.requireNonNull(nombre); }
    public LinkedList<Cancion> getFavoritos() { return favoritos; }

    public void agregarFavorito(Cancion c) { if (!favoritos.contains(c)) favoritos.add(c); }
    public void eliminarFavorito(Cancion c) { favoritos.remove(c); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return username.equals(usuario.username);
    }
    @Override public int hashCode() { return Objects.hash(username); }

    @Override public String toString() { return "Usuario{" + username + ", " + nombre + "}"; }
}
