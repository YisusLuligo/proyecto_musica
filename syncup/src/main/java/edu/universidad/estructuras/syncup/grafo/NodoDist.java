package edu.universidad.estructuras.syncup.grafo;

import edu.universidad.estructuras.syncup.model.Cancion;

class NodoDist implements Comparable<NodoDist> {
    Cancion c; double dist;
    NodoDist(Cancion c, double dist) { this.c = c; this.dist = dist; }
    public int compareTo(NodoDist o) { return Double.compare(this.dist, o.dist); }
}