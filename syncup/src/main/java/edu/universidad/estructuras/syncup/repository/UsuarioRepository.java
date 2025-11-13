package edu.universidad.estructuras.syncup.repository;


import edu.universidad.estructuras.syncup.model.Usuario;

import java.util.*;

public class UsuarioRepository {
    // RF-014: indexado en HashMap<String, Usuario> con acceso O(1)
    private final Map<String, Usuario> usuarios = new HashMap<>();

    public Optional<Usuario> findByUsername(String username) {
        return Optional.ofNullable(usuarios.get(username));
    }

    public void save(Usuario u) { usuarios.put(u.getUsername(), u); }

    public void delete(String username) { usuarios.remove(username); }

    public List<Usuario> findAll() { return new ArrayList<>(usuarios.values()); }
}
