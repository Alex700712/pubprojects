package main.ui;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.connection.ClientSocket;
import main.connection.TCP.Request;
import main.connection.TCP.Response;
import main.entities.User;
import main.enums.*;
import com.google.gson.Gson;
import main.ui.admin.AdminFinance;
import main.ui.admin.AdminMainMenu;
import main.ui.director.DirectorMainMenu;
import main.ui.user.UserMainMenu;

public class SignIn {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label appNameLabel;

    @FXML
    private Label connectionErrorLabel;

    @FXML
    private Label signInErrorLabel;

    @FXML
    private TextField loginSignInField;

    @FXML
    private PasswordField passwordSignInField;

    @FXML
    private Button signInButton;

    @FXML
    void initialize() {
    }

    @FXML
    public void keysPressed(KeyEvent keyEvent) {
        try {
            if (keyEvent.getCode() == KeyCode.getKeyCode("Enter"))
                enterPressed();
            else if (keyEvent.getCode() == KeyCode.getKeyCode("Down"))
                passwordSignInField.requestFocus();
            else if (keyEvent.getCode() == KeyCode.getKeyCode("Up"))
                loginSignInField.requestFocus();
        } catch (Exception e) {
            System.out.println("Ошибка - " + e);
        }
    }

    @FXML
    public void enterPressed() throws IOException {
        try {
            ClientSocket.getInstance().setConnection();
            if (ClientSocket.getInstance().getSocket() != null && ClientSocket.getInstance().getSocket().isConnected()) {
                connectionErrorLabel.setVisible(false);
                User user = new User();
                user.setLogin(loginSignInField.getText());
                user.setPassword(passwordSignInField.getText());
                System.out.println(user.getLogin());
                Request request = new Request();
                request.setRequestType(RequestType.LOGIN);
                request.setRequestMessage(new Gson().toJson(user));
                ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
                ClientSocket.getInstance().getOut().flush();
                String answer = ClientSocket.getInstance().getInStream().readLine();
                Response response = new Gson().fromJson(answer, Response.class);
                user = (new Gson().fromJson(response.getResponseData(), User.class));
                if (response.getResponseStatus() == ResponseStatus.OK) {
                    signInErrorLabel.setVisible(false);
                    signInButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    switch (user.getRole()) {
                        case "User":
                            loader.setLocation(SignIn.class.getResource("/main/user/UserMainMenuWindow.fxml"));
                            break;
                        case "Admin":
                            loader.setLocation(SignIn.class.getResource("/main/admin/AdminMainMenuWindow.fxml"));
                            break;
                        case "Director":
                            loader.setLocation(SignIn.class.getResource("/main/director/DirectorMainMenuWindow.fxml"));
                            break;
                    }
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setTitle("Budget Manager");
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    if(user.getRole().equals("User")) {
                        UserMainMenu menu = loader.getController();
                        menu.setCurrentUserId(user.getUserId());
                    }
                    else if (user.getRole().equals("Admin")) {
                        AdminMainMenu menu = loader.getController();
                        menu.setCurrentUserId(user.getUserId());
                    }
                    else {
                        DirectorMainMenu menu = loader.getController();
                        menu.setCurrentUserId(user.getUserId());
                    }
                    stage.show();
                } else signInErrorLabel.setVisible(true);
            } else {
                signInErrorLabel.setVisible(false);
                connectionErrorLabel.setVisible(true);
            }
        } catch (ConnectException connectException) {
            signInErrorLabel.setVisible(false);
            connectionErrorLabel.setVisible(true);
        }
    }
}
