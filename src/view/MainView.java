package view;

import controller.RolController;
import controller.UsuarioController;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Usuario;
import model.Rol;

/**
 *
 * @author mafeg
 */
public class MainView extends Application {

    private UsuarioController usuarioController = new UsuarioController();
    private RolController rolController = new RolController();

    private Stage primaryStage;
    private TextField userNameField, txtPassword;
    private PasswordField passwordField;

    private Image verIcon;
    private Image ocultarIcon;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 350, 200);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        Label userLabel = new Label("Username:");
        gridPane.add(userLabel, 0, 0);
        userNameField = new TextField();
        gridPane.add(userNameField, 1, 0);

        Label passwordLabel = new Label("Contraseña:");
        gridPane.add(passwordLabel, 0, 1);
        passwordField = new PasswordField();
        gridPane.add(passwordField, 1, 1);
        txtPassword = new TextField();
        gridPane.add(txtPassword, 1, 1);
        txtPassword.setVisible(false);

        verIcon = new Image("file:images/ver.png");
        ocultarIcon = new Image("file:images/ocultar.png");
        ImageView iconView = new ImageView(verIcon);
        iconView.setFitWidth(20);
        iconView.setFitHeight(20);
        Button passwordButton = new Button();
        passwordButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        passwordButton.setGraphic(iconView);
        gridPane.add(passwordButton, 2, 1);

        //Agrega la accion al boton para alternar entre mostrar u ocultar la contrasena
        passwordButton.setOnAction((e) -> ocultarMostrar(passwordField, txtPassword, passwordButton, iconView));

        Button loginButton = new Button("Iniciar Sesión");
        loginButton.setOnAction(e -> {
            if (userNameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                this.mostrarAlerta("Debe diligenciar los datos");
            } else {
                validarUsuario(userNameField.getText(), passwordField.getText());
            }
        });
        gridPane.add(loginButton, 1, 4);

        this.primaryStage.setTitle("Gestion de Usuarios - Login");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private void validarUsuario(String username, String password) {
        List<Rol> roles = this.rolController.listarRoles();
        Usuario usuario = usuarioController.autenticarUsuario(username, password);
        if (usuario == null) {
            this.mostrarAlerta("Usuario no autorizado");
        } else {
            primaryStage.hide();
            mostrarConfirmacion("Ingreso Exitoso", "Hola, " + usuario.getNombre(), "Ha iniciado sesión correctamente");
            if (usuario.getIdRol() == 1) {
                vistaAdministrador(usuario);
            } else {
                vistaUsuario(usuario);
            }
        }
    }

    private void vistaAdministrador(Usuario usuario) {
        Stage gestionUsuario = new Stage();
        GridPane gridPanel = new GridPane();
        Scene scene = new Scene(gridPanel, 800, 600);
        gestionUsuario.setTitle("Gestion de Usuarios - Administador");
        gestionUsuario.setScene(scene);
        gestionUsuario.show();
    }

    private void vistaUsuario(Usuario usuario) {
        Stage stageUsuario = new Stage();
        GridPane usuarioPanel = new GridPane();
        usuarioPanel.setAlignment(Pos.CENTER);
        usuarioPanel.setPadding(new Insets(10, 10, 10, 10));
        usuarioPanel.setVgap(8);
        usuarioPanel.setHgap(10);

        Label nombreLabel = new Label("Nombre:");
        nombreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        usuarioPanel.add(nombreLabel, 0, 0);
        Label nombre = new Label(usuario.getNombre());
        usuarioPanel.add(nombre, 1, 0);

        Label apellidoLabel = new Label("Apellido:");
        apellidoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        usuarioPanel.add(apellidoLabel, 0, 1);
        Label apellido = new Label(usuario.getApellido());
        usuarioPanel.add(apellido, 1, 1);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        usuarioPanel.add(usernameLabel, 0, 2);
        Label username = new Label(usuario.getUsername());
        usuarioPanel.add(username, 1, 2);

        Label rolLabel = new Label("Rol:");
        rolLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        usuarioPanel.add(rolLabel, 0, 3);
        Label rol = new Label(usuario.getNombreRol());
        usuarioPanel.add(rol, 1, 3);

        Button editButton = new Button("Editar");
        usuarioPanel.add(editButton, 0, 5);
        Button logoutButton = new Button("Cerrar Sesión");
        usuarioPanel.add(logoutButton, 1, 5);

        Scene scene = new Scene(usuarioPanel, 350, 200);
        stageUsuario.setTitle("Usuario");
        stageUsuario.setScene(scene);
        stageUsuario.show();

        editButton.setOnAction(e -> vistaForm(usuario, stageUsuario));
        logoutButton.setOnAction(e -> logout(stageUsuario));

    }

    private void vistaForm(Usuario usuario, Stage stageSecundario) {
        stageSecundario.hide();
        boolean registro = usuario == null;
        Stage stageForm = new Stage();
        GridPane formPanel = new GridPane();
        formPanel.setPadding(new Insets(10, 10, 10, 10));
        formPanel.setVgap(8);
        formPanel.setHgap(10);

        Label nombreLabel = new Label("Nombre:");
        formPanel.add(nombreLabel, 0, 0);
        TextField nombreTextField = new TextField();
        formPanel.add(nombreTextField, 1, 0);

        Label apellidoLabel = new Label("Apellido:");
        formPanel.add(apellidoLabel, 0, 1);
        TextField apellidoTextField = new TextField();
        formPanel.add(apellidoTextField, 1, 1);

        Label passwordLabel = new Label("Contraseña:");
        formPanel.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        formPanel.add(passwordField, 1, 2);
        TextField passwordTextField = new TextField();
        formPanel.add(passwordTextField, 1, 2);
        passwordTextField.setVisible(false);

        Label confirmarLabel = new Label("Confirmar Contraseña:");
        formPanel.add(confirmarLabel, 0, 3);
        PasswordField confirmarField = new PasswordField();
        formPanel.add(confirmarField, 1, 3);
        TextField confirmarTextField = new TextField();
        formPanel.add(confirmarTextField, 1, 3);
        confirmarTextField.setVisible(false);

        ImageView passwordView = new ImageView(verIcon);
        passwordView.setFitWidth(20);
        passwordView.setFitHeight(20);
        ImageView confirmarView = new ImageView(verIcon);
        confirmarView.setFitWidth(20);
        confirmarView.setFitHeight(20);

        Button passwordButton = new Button();
        passwordButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        passwordButton.setGraphic(passwordView);
        formPanel.add(passwordButton, 2, 2);
        Button confirmarButton = new Button();
        confirmarButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        confirmarButton.setGraphic(confirmarView);
        formPanel.add(confirmarButton, 2, 3);

        passwordButton.setOnAction(e -> ocultarMostrar(passwordField, passwordTextField, passwordButton, passwordView));
        confirmarButton.setOnAction(e -> ocultarMostrar(confirmarField, confirmarTextField, confirmarButton, confirmarView));

        Button accionButton = new Button("");

        if (registro) {
            accionButton.setText("Guardar");
        } else {
            passwordLabel.setText("Nueva Contraseña");
            accionButton.setText("Editar");
            nombreTextField.setText(usuario.getNombre());
            apellidoTextField.setText(usuario.getApellido());
        }
        formPanel.add(accionButton, 1, 5);

        Scene scene = new Scene(formPanel, 350, 200);
        stageForm.setTitle("Usuario");
        stageForm.setScene(scene);
        stageForm.show();
    }

    private void ocultarMostrar(PasswordField passwordField, TextField passwordTextField, Button passwordButton, ImageView iconView) {
        if (passwordField.isVisible()) {
            iconView.setImage(ocultarIcon);
            passwordTextField.setText(passwordField.getText());
        } else {
            iconView.setImage(verIcon);
            passwordField.setText(passwordTextField.getText());
        }
        passwordButton.setGraphic(iconView);
        passwordField.setVisible(!passwordField.isVisible());
        passwordTextField.setVisible(!passwordTextField.isVisible());
    }

    private void logout(Stage stageUser) {
        stageUser.close();
        userNameField.setText("");
        passwordField.setText("");
        txtPassword.setText("");
        primaryStage.show();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarConfirmacion(String titulo, String cabecera, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
