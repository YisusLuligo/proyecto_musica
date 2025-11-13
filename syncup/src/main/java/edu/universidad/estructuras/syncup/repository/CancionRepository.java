package edu.universidad.estructuras.syncup.repository;


import edu.universidad.estructuras.syncup.model.Cancion;

import java.util.*;

public class CancionRepository {
    private final Map<String, Cancion> canciones = new HashMap<>();

    public void save(Cancion c) { canciones.put(c.getId(), c); }
    public Optional<Cancion> findById(String id) { return Optional.ofNullable(canciones.get(id)); }
    public void delete(String id) { canciones.remove(id); }
    public List<Cancion> findAll() { return new ArrayList<>(canciones.values()); }

    public List<Cancion> buscarPorTituloExacto(String titulo) {
        List<Cancion> res = new ArrayList<>();
        for (Cancion c : canciones.values()) {
            if (c.getTitulo().equalsIgnoreCase(titulo)) res.add(c);
        }
        return res;
    }
}

