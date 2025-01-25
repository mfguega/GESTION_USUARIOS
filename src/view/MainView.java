package view;

import controller.RolController;
import controller.UsuarioController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    
    private TableView<Usuario> tablaUsuarios;
    private ObservableList<Usuario> dataUsuario;

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
        Stage gestionAdministrador = new Stage();

        tablaUsuarios = new TableView<>();
        actualizarTabla();

        TableColumn<Usuario, String> nombreCol = new TableColumn<>("Nombre");
        nombreCol.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        TableColumn<Usuario, String> apellidoCol = new TableColumn<>("Apellido");
        apellidoCol.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());

        TableColumn<Usuario, String> rolCol = new TableColumn<>("Rol");
        rolCol.setCellValueFactory(cellData -> cellData.getValue().nombreRolProperty());

        TableColumn<Usuario, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());

        TableColumn<Usuario, Void> editarCol = new TableColumn<>("Editar");
        TableColumn<Usuario, Void> eliminarCol = new TableColumn<>("Eliminar");

        editarCol.setCellFactory(col -> new TableCell<>() {
            private final Button editarBtn = new Button("Editar");

            {
                editarBtn.setOnAction(e -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    vistaForm(usuario, gestionAdministrador);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editarBtn);
                }
            }
        });

        eliminarCol.setCellFactory(col -> new TableCell<>() {
            private final Button eliminarBtn = new Button("Eliminar");

            {
                eliminarBtn.setOnAction(e -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacion.setTitle("Confirmación de Eliminación");
                    confirmacion.setHeaderText("¿Estás seguro de que deseas eliminar a este usuario?");
                    confirmacion.setContentText("Username: " + usuario.getNombreRol());

                    confirmacion.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            usuarioController.eliminarUsuario(usuario.getId());
                            actualizarTabla();
                            mostrarConfirmacion("Eliminación exitosa", "Operación realizada", "El usuario fue eliminado con éxito.");
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(eliminarBtn);
                }
            }
        });

        tablaUsuarios.getColumns().addAll(nombreCol, apellidoCol, usernameCol, rolCol, editarCol, eliminarCol);
        Button nuevo = new Button("Nuevo");
        nuevo.setOnAction(e -> vistaForm(null, gestionAdministrador));
        Button logout = new Button("Cerrar Sesión");
        logout.setOnAction(e -> logout(gestionAdministrador));
        HBox hbox = new HBox(20);
        hbox.getChildren().addAll(nuevo, logout);
        hbox.setAlignment(Pos.TOP_RIGHT);
        VBox admin = new VBox(20);
        admin.getChildren().addAll(hbox, tablaUsuarios);
        admin.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(admin, 508, 480);
        gestionAdministrador.setTitle("Gestion de Usuarios - Administador");
        gestionAdministrador.setScene(scene);
        gestionAdministrador.show();
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
        formPanel.add(passwordLabel, 0, registro ? 3 : 2);
        PasswordField passwordField = new PasswordField();
        formPanel.add(passwordField, 1, registro ? 3 : 2);
        TextField passwordTextField = new TextField();
        formPanel.add(passwordTextField, 1, registro ? 3 : 2);
        passwordTextField.setVisible(false);

        Label confirmarLabel = new Label("Confirmar Contraseña:");
        formPanel.add(confirmarLabel, 0, registro ? 4 : 3);
        PasswordField confirmarField = new PasswordField();
        formPanel.add(confirmarField, 1, registro ? 4 : 3);
        TextField confirmarTextField = new TextField();
        formPanel.add(confirmarTextField, 1, registro ? 4 : 3);
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
        formPanel.add(passwordButton, 2, registro ? 3 : 2);
        Button confirmarButton = new Button();
        confirmarButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        confirmarButton.setGraphic(confirmarView);
        formPanel.add(confirmarButton, 2, registro ? 4 : 3);

        passwordButton.setOnAction(e -> ocultarMostrar(passwordField, passwordTextField, passwordButton, passwordView));
        confirmarButton.setOnAction(e -> ocultarMostrar(confirmarField, confirmarTextField, confirmarButton, confirmarView));

        Button accionButton = new Button("");

        if (registro) {
            accionButton.setText("Guardar");
            ObservableList<Rol> items = FXCollections.observableArrayList(rolController.listarRoles());
            ComboBox<Rol> roles = new ComboBox<>(items);
            roles.setCellFactory(lv -> new ListCell<Rol>() {
                @Override
                protected void updateItem(Rol item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item != null ? item.getNombreRol() : "");
                }
            });
            roles.setOnAction(e -> {
                Rol rol = roles.getValue();
                System.out.print(rol.getIdRol());
            });
            Label rolLabel = new Label("Rol:");
            formPanel.add(rolLabel, 0, 2);
            formPanel.add(roles, 1, 2);
        } else {
            nombreTextField.setText(usuario.getNombre());
            apellidoTextField.setText(usuario.getApellido());
            passwordLabel.setText("Nueva Contraseña");
            accionButton.setText("Editar");
            accionButton.setOnAction(e -> {
                Boolean validacion = true;
                if (nombreTextField.getText().isEmpty() || apellidoTextField.getText().isEmpty() || !passwordField.getText().isEmpty() || !confirmarField.getText().isEmpty()) {
                    if (nombreTextField.getText().isEmpty() || apellidoTextField.getText().isEmpty()) {
                        validacion = false;
                        this.mostrarAlerta("Debe diligenciar los datos");
                    } else if (!passwordField.getText().isEmpty() || !confirmarField.getText().isEmpty()) {
                        if (!passwordField.getText().equals(confirmarField.getText())) {
                            validacion = false;
                            this.mostrarAlerta("Contraseñas no coinciden");
                        }
                    } else {
                        validacion = true;
                    }
                }
                if (validacion) {
                    String contrasena = "";
                    if (passwordField.getText().isEmpty()) {
                        contrasena = usuario.getPassword();
                    } else {
                        contrasena = passwordField.getText();
                    }
                    usuarioController.actualizarUsuario(
                            nombreTextField.getText(),
                            apellidoTextField.getText(),
                            usuario.getUsername(),
                            contrasena,
                            usuario.getIdRol(),
                            usuario.getNombreRol(),
                            usuario.getId()
                    );
                    mostrarConfirmacion("Actualización exitosa", "Usuario " + usuario.getUsername(), "Ha sido actualizado exitosamente");
                    stageForm.close();
                    Usuario usuarionuevo = usuarioController.autenticarUsuario(usuario.getUsername(), contrasena);
                    vistaUsuario(usuarionuevo);
                }
            });
        }
        Button accionVolver = new Button("");
        accionVolver.setText("Volver");
        accionVolver.setOnAction(e -> {
            stageForm.close();
            stageSecundario.show();
        });
        HBox buttonBox = new HBox(25);
        buttonBox.getChildren().addAll(accionButton, accionVolver);
        formPanel.add(buttonBox, 1, 5);

        stageForm.setOnCloseRequest(event -> {
            stageSecundario.show();
            stageForm.close();
        });

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

    private void actualizarTabla() {
        dataUsuario = FXCollections.observableArrayList(usuarioController.listarUsuarios());
        tablaUsuarios.setItems(dataUsuario);
    }
}
