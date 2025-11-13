package edu.universidad.estructuras.syncup.controller;

import edu.universidad.estructuras.syncup.app.Router;
import edu.universidad.estructuras.syncup.app.SyncUpApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistroController {
    @FXML private TextField tfUsuario, tfNombre;
    @FXML private PasswordField pfPassword;
    @FXML private Label lblMsg;

    @FXML
    public void onRegistrar() {
        boolean ok = SyncUpApp.authService.registrar(tfUsuario.getText().trim(),
                pfPassword.getText().trim(), tfNombre.getText().trim());
        lblMsg.setText(ok ? "Usuario creado. Inicia sesión." : "No se pudo crear (valida datos / usuario existente)");
    }

    @FXML
    public void volverLogin() {
        try { Router.goTo("login.fxml", "Iniciar sesión"); } catch (Exception e) { lblMsg.setText("Error"); }
    }
}

