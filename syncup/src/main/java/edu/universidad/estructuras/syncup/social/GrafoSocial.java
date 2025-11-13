package edu.universidad.estructuras.syncup.social;



import edu.universidad.estructuras.syncup.model.Usuario;

import java.util.*;

public class GrafoSocial {
    private final Map<Usuario, Set<Usuario>> adj = new HashMap<>();

    public void agregarUsuario(Usuario u) { adj.putIfAbsent(u, new HashSet<>()); }

    public void conectar(Usuario a, Usuario b) {
        agregarUsuario(a); agregarUsuario(b);
        adj.get(a).add(b);
        adj.get(b).add(a);
    }

    public void desconectar(Usuario a, Usuario b) {
        adj.getOrDefault(a, Collections.emptySet()).remove(b);
        adj.getOrDefault(b, Collections.emptySet()).remove(a);
    }

    public Set<Usuario> amigosDeAmigos(Usuario origen, int maxNivel) {
        Set<Usuario> resultado = new HashSet<>();
        Set<Usuario> visit = new HashSet<>();
        Queue<Usuario> q = new LinkedList<>();
        Map<Usuario, Integer> nivel = new HashMap<>();

        q.add(origen); visit.add(origen); nivel.put(origen, 0);
        while (!q.isEmpty()) {
            Usuario u = q.poll();
            int n = nivel.get(u);
            for (Usuario v : adj.getOrDefault(u, Collections.emptySet())) {
                if (!visit.contains(v)) {
                    visit.add(v);
                    nivel.put(v, n + 1);
                    if (n + 1 <= maxNivel) {
                        resultado.add(v);
                        q.add(v);
                    }
                }
            }
        }
        // excluir amigos directos si quieres solo amigos de amigos:
        // resultado.removeAll(adj.getOrDefault(origen, Collections.emptySet()));
        // resultado.remove(origen);
        return resultado;
    }

    public Set<Usuario> getAmigos(Usuario u) { return adj.getOrDefault(u, Collections.emptySet()); }
}
