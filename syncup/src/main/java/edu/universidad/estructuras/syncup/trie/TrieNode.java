package edu.universidad.estructuras.syncup.trie;

import java.util.*;

public class TrieNode {
    Map<Character, TrieNode> hijos = new HashMap<>();
    boolean fin;
    String palabra; // opcional: guardar palabra completa
}