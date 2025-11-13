package edu.universidad.estructuras.syncup.controller;

import edu.universidad.estructuras.syncup.app.Router;
import edu.universidad.estructuras.syncup.app.SyncUpApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML private TextField tfUsuario;
    @FXML private PasswordField pfPassword;
    @FXML private Label lblError;

    @FXML
    public void onLogin() {
        String u = tfUsuario.getText().trim();
        String p = pfPassword.getText().trim();
        SyncUpApp.authService.login(u, p).ifPresentOrElse(usuario -> {
            SyncUpApp.usuarioActual = usuario;
            try { Router.goTo("view/admin.fxml", "MusicApp Admin - Panel"); }
            catch (Exception e) { lblError.setText("Error cargando panel"); }
        }, () -> lblError.setText("Usuario/Contraseña inválidos"));
    }

    @FXML
    public void goRegistro() {
        try { Router.goTo("view/registro.fxml", "Registro"); }
        catch (Exception e) { lblError.setText("Error cargando registro"); }
    }
}
