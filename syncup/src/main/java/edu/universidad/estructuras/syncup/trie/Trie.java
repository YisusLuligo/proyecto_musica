package edu.universidad.estructuras.syncup.trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trie {
    private final TrieNode raiz = new TrieNode();

    public void insertar(String palabra) {
        TrieNode nodo = raiz;
        for (char ch : palabra.toLowerCase().toCharArray()) {
            nodo.hijos.putIfAbsent(ch, new TrieNode());
            nodo = nodo.hijos.get(ch);
        }
        nodo.fin = true;
        nodo.palabra = palabra;
    }

    public List<String> autocompletar(String prefijo) {
        List<String> res = new ArrayList<>();
        TrieNode nodo = raiz;
        for (char ch : prefijo.toLowerCase().toCharArray()) {
            nodo = nodo.hijos.get(ch);
            if (nodo == null) return res;
        }
        dfs(nodo, res);
        return res;
    }

    private void dfs(TrieNode nodo, List<String> res) {
        if (nodo.fin && nodo.palabra != null) res.add(nodo.palabra);
        for (Map.Entry<Character, TrieNode> e : nodo.hijos.entrySet()) {
            dfs(e.getValue(), res);
        }
    }
}
