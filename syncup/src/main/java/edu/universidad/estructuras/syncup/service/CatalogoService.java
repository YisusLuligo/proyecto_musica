package edu.universidad.estructuras.syncup.service;



import edu.universidad.estructuras.syncup.model.Cancion;
import edu.universidad.estructuras.syncup.repository.CancionRepository;
import edu.universidad.estructuras.syncup.trie.Trie;

import java.util.*;

public class CatalogoService {
    private final CancionRepository repo;
    private final Trie trieTitulos = new Trie();

    public CatalogoService(CancionRepository repo) { this.repo = repo; }

    public void agregarCancion(Cancion c) {
        repo.save(c);
        trieTitulos.insertar(c.getTitulo());
    }

    public void actualizarCancion(Cancion c) {
        repo.save(c);
    }

    public void eliminarCancion(String id) { repo.delete(id); }

    public List<Cancion> listar() { return repo.findAll(); }

    public List<String> autocompletarTitulo(String prefijo) { return trieTitulos.autocompletar(prefijo); }
}
