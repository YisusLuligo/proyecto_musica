package edu.universidad.estructuras.syncup.app;

import edu.universidad.estructuras.syncup.grafo.GrafoDeSimilitud;
import edu.universidad.estructuras.syncup.model.Cancion;
import edu.universidad.estructuras.syncup.model.Usuario;
import edu.universidad.estructuras.syncup.repository.CancionRepository;
import edu.universidad.estructuras.syncup.repository.UsuarioRepository;
import edu.universidad.estructuras.syncup.service.*;
import edu.universidad.estructuras.syncup.social.GrafoSocial;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.*;

public class SyncUpApp extends Application {

    // Almacenamiento y servicios compartidos (simple DI)
    public static UsuarioRepository usuarioRepo = new UsuarioRepository();
    public static CancionRepository cancionRepo = new CancionRepository();
    public static GrafoDeSimilitud grafoSim = new GrafoDeSimilitud();
    public static GrafoSocial grafoSocial = new GrafoSocial();

    public static AuthService authService = new AuthService(usuarioRepo);
    public static CatalogoService catalogoService = new CatalogoService(cancionRepo);
    public static BusquedaService busquedaService = new BusquedaService(cancionRepo);
    public static RecomendacionService recomendacionService = new RecomendacionService(grafoSim);
    public static SocialService socialService = new SocialService(grafoSocial);
    public static ExportService exportService = new ExportService();
    public static ImportService importService = new ImportService(cancionRepo);

    public static Usuario usuarioActual; // contexto simple

    @Override
    public void start(Stage stage) throws Exception {
        seedData(); // datos de muestra y grafo

        Router.setPrimaryStage(stage);
        Router.goTo("view/login.fxml", "SyncUp - Iniciar sesión");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/syncup/icons/user.png")));
        stage.setMinWidth(960);
        stage.setMinHeight(600);
        stage.show();
    }

    private void seedData() {
        // Usuarios
        authService.registrar("ana", "123456", "Ana García");
        authService.registrar("carlos", "123456", "Carlos López");
        authService.registrar("maria", "123456", "María Rodríguez");
        authService.registrar("juan", "123456", "Juan Pérez");

        usuarioRepo.findByUsername("ana").ifPresent(u -> SyncUpApp.usuarioActual = u);

        // Canciones (con URLs ejemplo: usa URLs accesibles a mp3/stream)
        Cancion blinding = new Cancion("S1","Blinding Lights","The Weeknd","Pop",2019,200,
                "https://www2.cs.uic.edu/~i101/SoundFiles/BabyElephantWalk60.wav");
        Cancion levitating = new Cancion("S2","Levitating","Dua Lipa","Pop",2020,203,
                "https://www2.cs.uic.edu/~i101/SoundFiles/StarWars60.wav");
        Cancion good4u = new Cancion("S3","Good 4 U","Olivia Rodrigo","Pop Rock",2021,178,
                "https://www2.cs.uic.edu/~i101/SoundFiles/PinkPanther60.wav");
        Cancion stay = new Cancion("S4","Stay","The Kid LAROI, Justin Bieber","Pop",2021,141,
                "https://www2.cs.uic.edu/~i101/SoundFiles/CantinaBand60.wav");

        Arrays.asList(blinding, levitating, good4u, stay).forEach(catalogoService::agregarCancion);

        // Grafo de similitud (peso bajo = alta similitud)
        grafoSim.conectar(blinding, levitating, 0.2);
        grafoSim.conectar(blinding, good4u, 0.5);
        grafoSim.conectar(levitating, stay, 0.3);
        grafoSim.conectar(good4u, stay, 0.6);

        // Favoritos de Ana
        SyncUpApp.usuarioActual.agregarFavorito(blinding);
        SyncUpApp.usuarioActual.agregarFavorito(levitating);

        // Grafo social
        grafoSocial.agregarUsuario(usuarioRepo.findByUsername("ana").get());
        grafoSocial.agregarUsuario(usuarioRepo.findByUsername("carlos").get());
        grafoSocial.agregarUsuario(usuarioRepo.findByUsername("maria").get());
        grafoSocial.agregarUsuario(usuarioRepo.findByUsername("juan").get());

        socialService.seguir(usuarioRepo.findByUsername("ana").get(), usuarioRepo.findByUsername("carlos").get());
        socialService.seguir(usuarioRepo.findByUsername("ana").get(), usuarioRepo.findByUsername("maria").get());
        socialService.seguir(usuarioRepo.findByUsername("carlos").get(), usuarioRepo.findByUsername("juan").get());
    }

    @Override
    public void stop() throws Exception {
        busquedaService.shutdown();
        super.stop();
    }

    public static void main(String[] args) { launch(args); }
}

