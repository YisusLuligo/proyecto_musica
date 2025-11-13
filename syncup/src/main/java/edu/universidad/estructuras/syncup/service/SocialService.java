package edu.universidad.estructuras.syncup.service;


import edu.universidad.estructuras.syncup.model.Usuario;
import edu.universidad.estructuras.syncup.social.GrafoSocial;

import java.util.*;

public class SocialService {
    private final GrafoSocial grafo;

    public SocialService(GrafoSocial grafo) { this.grafo = grafo; }

    public void seguir(Usuario a, Usuario b) { grafo.conectar(a, b); }
    public void dejarDeSeguir(Usuario a, Usuario b) { grafo.desconectar(a, b); }
    public Set<Usuario> amigos(Usuario u) { return grafo.getAmigos(u); }

    public Set<Usuario> sugerencias(Usuario u) {
        // “amigos de amigos” hasta nivel 2
        Set<Usuario> aoa = grafo.amigosDeAmigos(u, 2);
        // eliminar amigos directos y al propio usuario
        aoa.removeAll(grafo.getAmigos(u));
        aoa.remove(u);
        return aoa;
    }
}
