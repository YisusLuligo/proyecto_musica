package edu.universidad.estructuras.syncup.service;



import edu.universidad.estructuras.syncup.grafo.Dijkstra;
import edu.universidad.estructuras.syncup.grafo.GrafoDeSimilitud;
import edu.universidad.estructuras.syncup.model.Cancion;
import edu.universidad.estructuras.syncup.model.Usuario;

import java.util.*;

public class RecomendacionService {
    private final GrafoDeSimilitud grafo;

    public RecomendacionService(GrafoDeSimilitud grafo) { this.grafo = grafo; }

    // Descubrimiento semanal: tomar favoritos del usuario y expandir a vecinos de menor peso.
    public List<Cancion> descubrimientoSemanal(Usuario u, int maxPorFavorito) {
        Set<Cancion> reco = new LinkedHashSet<>();
        for (Cancion fav : u.getFavoritos()) {
            Map<Cancion, Double> vecinos = grafo.getAdj().getOrDefault(fav, Collections.emptyMap());
            vecinos.entrySet().stream()
                    .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                    .limit(maxPorFavorito)
                    .forEach(e -> reco.add(e.getKey()));
        }
        // Evitar repetir favoritos
        reco.removeAll(u.getFavoritos());
        return new ArrayList<>(reco);
    }

    // Radio: desde una canción, generar cola de reproducción por ruta más similar (Dijkstra)
    public Queue<Cancion> iniciarRadio(Cancion origen, int maxSaltos) {
        Queue<Cancion> cola = new LinkedList<>();
        // estratégia simple: elegir el vecino más similar y continuar
        Cancion actual = origen;
        Set<Cancion> visit = new HashSet<>();
        visit.add(actual);

        for (int i = 0; i < maxSaltos; i++) {
            Map<Cancion, Double> vecinos = grafo.getAdj().getOrDefault(actual, Collections.emptyMap());
            Optional<Map.Entry<Cancion, Double>> next = vecinos.entrySet().stream()
                    .filter(e -> !visit.contains(e.getKey()))
                    .min(Comparator.comparingDouble(Map.Entry::getValue));
            if (next.isPresent()) {
                Cancion c = next.get().getKey();
                cola.add(c);
                visit.add(c);
                actual = c;
            } else break;
        }
        return cola;
    }

    // Ruta recomendada entre dos canciones usando Dijkstra
    public List<Cancion> rutaMasSimilar(Cancion origen, Cancion destino) {
        return Dijkstra.shortestPath(grafo, origen, destino);
    }
}
