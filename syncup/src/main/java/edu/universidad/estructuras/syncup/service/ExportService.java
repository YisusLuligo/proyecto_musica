package edu.universidad.estructuras.syncup.service;


import edu.universidad.estructuras.syncup.model.Cancion;
import edu.universidad.estructuras.syncup.model.Usuario;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ExportService {
    public File exportarFavoritosCSV(Usuario u, File destino) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destino), StandardCharsets.UTF_8))) {
            bw.write("id,titulo,artista,genero,anio,duracionSeg,url\n");
            for (Cancion c : u.getFavoritos()) {
                bw.write(String.format("%s,%s,%s,%s,%d,%d,%s\n",
                        c.getId(), escape(c.getTitulo()), escape(c.getArtista()), c.getGenero(), c.getAnio(), c.getDuracionSeg(), c.getStreamUrl()));
            }
        }
        return destino;
    }

    private String escape(String s) { return s.replace(",", ";"); }
}
