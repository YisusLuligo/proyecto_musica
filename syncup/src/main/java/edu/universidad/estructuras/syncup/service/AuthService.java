package edu.universidad.estructuras.syncup.service;


import edu.universidad.estructuras.syncup.model.Usuario;
import edu.universidad.estructuras.syncup.repository.UsuarioRepository;
import edu.universidad.estructuras.syncup.util.Validators;

import java.util.Optional;

public class AuthService {
    private final UsuarioRepository usuarios;

    public AuthService(UsuarioRepository usuarios) { this.usuarios = usuarios; }

    public boolean registrar(String username, String password, String nombre) {
        if (!Validators.validUsername(username) || !Validators.validPassword(password)) return false;
        if (usuarios.findByUsername(username).isPresent()) return false;
        usuarios.save(new Usuario(username, password, nombre));
        return true;
    }

    public Optional<Usuario> login(String username, String password) {
        return usuarios.findByUsername(username).filter(u -> u.getPassword().equals(password));
    }
}

