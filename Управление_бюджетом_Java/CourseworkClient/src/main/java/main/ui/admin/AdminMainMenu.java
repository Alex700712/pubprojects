package main.ui.admin;

import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.connection.ClientSocket;
import main.connection.TCP.Request;
import main.enums.RequestType;
import main.ui.UserAccount;
import main.ui.director.DirectorMainMenu;
import main.ui.user.ShowClients;
import main.ui.user.ShowService;

public class AdminMainMenu {
    private static Stage mainStage;

    private static AnchorPane accountPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button accountButton;

    @FXML
    private Button departmentButton;

    @FXML
    private Button employeeButton;

    @FXML
    private Button financeButton;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button postButton;

    @FXML
    private Button quitButton;

    private int currentUserId;

    @FXML
    void initialize()
    {
    }

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static Stage getStage() {
        return mainStage;
    }

    public void AccountPressed(ActionEvent actionEvent)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/UserAccountScene.fxml"));
            AnchorPane pane = loader.load();
            UserAccount account = loader.getController();
            account.setCurrentUserId(currentUserId);
            account.setInfo();
            mainPane.getChildren().add(pane);
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

    public void ShowEmployeesPressed(ActionEvent actionEvent)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/user/ShowEmployeesScene.fxml"));
            AnchorPane pane = loader.load();
            mainPane.getChildren().add(pane);
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

    public void ShowPostsPressed(ActionEvent actionEvent)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/user/ShowPostsScene.fxml"));
            AnchorPane pane = loader.load();
            mainPane.getChildren().add(pane);
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

    public void ShowDepartmentsPressed(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/user/ShowDepartmentsScene.fxml"));
            AnchorPane pane = loader.load();
            mainPane.getChildren().add(pane);
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

    public void FinancePressed(ActionEvent actionEvent)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/admin/AdminFinanceWindow.fxml"));
            AnchorPane pane = loader.load();
            mainPane.getChildren().add(pane);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ClientPressed()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DirectorMainMenu.class.getResource("/main/user/ShowClientsScene.fxml"));
            AnchorPane pane = loader.load();
            mainPane.getChildren().add(pane);
            ShowClients clientMenu = loader.getController();
            clientMenu.setVisibleToolbar();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ServicePressed()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DirectorMainMenu.class.getResource("/main/user/ShowServicesScene.fxml"));
            AnchorPane pane = loader.load();
            mainPane.getChildren().add(pane);
            ShowService serviceMenu = loader.getController();
            serviceMenu.setVisibleToolbar();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void QuitPressed(ActionEvent actionEvent)
    {
        {
            try {
                Request request = new Request(RequestType.QUIT, "");
                ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
                ClientSocket.getInstance().getOut().flush();

                quitButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(AdminMainMenu.class.getResource("/main/SignInWindow.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Budget Manager");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Button getAccountButton() {
        return accountButton;
    }

    public void setCurrentUserId(int id) { this.currentUserId = id; }

    public int getCurrentUserId() { return this.currentUserId; }

}

