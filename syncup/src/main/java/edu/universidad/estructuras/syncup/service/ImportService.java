package edu.universidad.estructuras.syncup.service;


import edu.universidad.estructuras.syncup.model.Cancion;
import edu.universidad.estructuras.syncup.repository.CancionRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ImportService {
    private final CancionRepository repo;

    public ImportService(CancionRepository repo) { this.repo = repo; }

    // Formato esperado por línea: id|titulo|artista|genero|anio|duracionSeg|url
    public int importarDesdeArchivoPlano(File archivo) throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length < 7) continue;
                Cancion c = new Cancion(
                        parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(),
                        Integer.parseInt(parts[4].trim()), Integer.parseInt(parts[5].trim()), parts[6].trim()
                );
                repo.save(c);
                count++;
            }
        }
        return count;
    }
}
