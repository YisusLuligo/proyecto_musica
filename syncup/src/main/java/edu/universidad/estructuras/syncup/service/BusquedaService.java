package edu.universidad.estructuras.syncup.service;




import edu.universidad.estructuras.syncup.model.Cancion;
import edu.universidad.estructuras.syncup.repository.CancionRepository;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Predicate;

public class BusquedaService {
    private final CancionRepository repo;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public BusquedaService(CancionRepository repo) { this.repo = repo; }

    public enum Logica { AND, OR }

    public List<Cancion> buscarAvanzado(Optional<String> artista, Optional<String> genero, Optional<Integer> anio, Logica logica)
            throws InterruptedException, ExecutionException {

        List<Cancion> all = repo.findAll();

        Callable<List<Cancion>> fArtista = () -> filter(all, c -> artista.map(a -> c.getArtista().equalsIgnoreCase(a)).orElse(true));
        Callable<List<Cancion>> fGenero  = () -> filter(all, c -> genero.map(g -> c.getGenero().equalsIgnoreCase(g)).orElse(true));
        Callable<List<Cancion>> fAnio    = () -> filter(all, c -> anio.map(y -> c.getAnio() == y).orElse(true));

        Future<List<Cancion>> futA = executor.submit(fArtista);
        Future<List<Cancion>> futG = executor.submit(fGenero);
        Future<List<Cancion>> futY = executor.submit(fAnio);

        List<Cancion> la = futA.get();
        List<Cancion> lg = futG.get();
        List<Cancion> ly = futY.get();

        Set<Cancion> res = new HashSet<>();
        if (logica == Logica.OR) {
            res.addAll(la); res.addAll(lg); res.addAll(ly);
        } else {
            // AND: intersección
            Set<Cancion> sa = new HashSet<>(la);
            sa.retainAll(lg);
            sa.retainAll(ly);
            res.addAll(sa);
        }
        return new ArrayList<>(res);
    }

    private List<Cancion> filter(List<Cancion> list, Predicate<Cancion> p) {
        List<Cancion> out = new ArrayList<>();
        for (Cancion c : list) if (p.test(c)) out.add(c);
        return out;
    }

    public void shutdown() { executor.shutdown(); }
}
