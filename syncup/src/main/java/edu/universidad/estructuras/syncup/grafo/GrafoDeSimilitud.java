package edu.universidad.estructuras.syncup.grafo;

import edu.universidad.estructuras.syncup.model.Cancion;

import java.util.Map;

import java.util.*;

public class GrafoDeSimilitud {
    // No dirigido, ponderado (RF-019)
    private final Map<Cancion, Map<Cancion, Double>> adj = new HashMap<>();

    public void agregarNodo(Cancion c) { adj.putIfAbsent(c, new HashMap<>()); }

    public void conectar(Cancion a, Cancion b, double peso) {
        if (a.equals(b)) return;
        agregarNodo(a); agregarNodo(b);
        adj.get(a).put(b, peso);
        adj.get(b).put(a, peso);
    }

    public Map<Cancion, Map<Cancion, Double>> getAdj() { return adj; }
}
