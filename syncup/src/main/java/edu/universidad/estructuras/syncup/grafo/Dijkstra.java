package edu.universidad.estructuras.syncup.grafo;

import edu.universidad.estructuras.syncup.model.Cancion;

import java.util.*;

public class Dijkstra {
    // RF-020: rutas de menor costo (mayor similitud -> menor peso)
    public static List<Cancion> shortestPath(GrafoDeSimilitud g, Cancion origen, Cancion destino) {
        Map<Cancion, Double> dist = new HashMap<>();
        Map<Cancion, Cancion> prev = new HashMap<>();
        Set<Cancion> visit = new HashSet<>();
        PriorityQueue<NodoDist> pq = new PriorityQueue<>();

        dist.put(origen, 0.0);
        pq.add(new NodoDist(origen, 0.0));

        while (!pq.isEmpty()) {
            Cancion u = pq.poll().c;
            if (visit.contains(u)) continue;
            visit.add(u);
            if (u.equals(destino)) break;

            Map<Cancion, Double> vecinos = g.getAdj().getOrDefault(u, Collections.emptyMap());
            for (Map.Entry<Cancion, Double> e : vecinos.entrySet()) {
                Cancion v = e.getKey();
                double peso = e.getValue();
                double alt = dist.getOrDefault(u, Double.POSITIVE_INFINITY) + peso;
                if (alt < dist.getOrDefault(v, Double.POSITIVE_INFINITY)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    pq.add(new NodoDist(v, alt));
                }
            }
        }
        // reconstruir ruta
        LinkedList<Cancion> ruta = new LinkedList<>();
        Cancion step = destino;
        if (!prev.containsKey(step) && !origen.equals(destino)) return Collections.emptyList();
        while (step != null) {
            ruta.addFirst(step);
            step = prev.get(step);
        }
        return ruta;
    }
}