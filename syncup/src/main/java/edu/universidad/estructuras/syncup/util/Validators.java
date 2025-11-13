package edu.universidad.estructuras.syncup.util;


public class Validators {
    public static boolean validUsername(String u) { return u != null && u.matches("[a-zA-Z0-9_]{3,20}"); }
    public static boolean validPassword(String p) { return p != null && p.length() >= 6; }
}
