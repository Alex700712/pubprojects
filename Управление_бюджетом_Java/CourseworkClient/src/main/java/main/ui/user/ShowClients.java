package main.ui.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.connection.GetService;
import main.entities.Client;
import main.entities.Department;
import main.ui.admin.AdminMainMenu;
import main.ui.operations.delete.DeleteWarning;
import main.ui.operations.update.UpdateMenu;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.*;

public class ShowClients {

    @FXML
    private Button addDepartmentButton;

    @FXML
    private ToolBar crudToolbar;

    @FXML
    private Button deleteDepartmentButton;

    @FXML
    private AnchorPane clientListPane;

    @FXML
    private Button findClientButton;

    @FXML
    private TextField findClientField;

    @FXML
    private Button updateDepartmentButton;

    @FXML
    private TableView<Client> clientTable;
    @FXML
    private TableColumn<Client, Integer> clientIdColumn;
    @FXML
    private TableColumn<Client, String> nameColumn;
    @FXML
    private TableColumn<Client, Integer> departmentIdColumn;
    @FXML
    private TableColumn<Client, Integer> serviceIdColumn;

    @FXML
    public void initialize()
    {
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));

        fillTable();
    }

    @FXML
    void addClient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/add/AddClientMenu.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Add Client");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

    @FXML
    void updateClient(ActionEvent event) {
        try {
            if (clientTable.getSelectionModel().getSelectedItem() != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ShowPosts.class.getResource("/main/operations/update/UpdateMenu.fxml"));
                loader.load();
                UpdateMenu<Client> updateMenu = loader.getController();
                updateMenu.setEditEntity(clientTable.getSelectionModel().getSelectedItem());
                updateMenu.setComboBoxData();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Update Client");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteClient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/delete/DeleteWarning.fxml"));
            loader.load();
            DeleteWarning<Client> deleteWarning = loader.getController();
            deleteWarning.setDeleteEntity(clientTable.getSelectionModel().getSelectedItem());
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Delete Client");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

    public void fillTable()
    {
        Type clientListType = new TypeToken<ArrayList<Client>>() {}.getType();
        List<Client> clients = (new Gson().fromJson(new GetService<>(Client.class)
                .GetEntities(FIND_ALL_CLIENTS), clientListType));
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        clientList.addAll(clients);
        clientTable.setItems(clientList);
    }

    @FXML
    void findClient(ActionEvent event) {
        if(!findClientField.getText().isEmpty())
        {
            try
            {
                ObservableList<Client> clientList = FXCollections.observableArrayList();
                Client client = new GetService<>(Client.class)
                        .GetEntityById(FIND_CLIENT, findClientField.getText());
                clientList.add(client);
                clientTable.setItems(clientList);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else fillTable();
    }

    public void setVisibleToolbar()
    {
        crudToolbar.setVisible(true);
        crudToolbar.setDisable(false);
    }
}

