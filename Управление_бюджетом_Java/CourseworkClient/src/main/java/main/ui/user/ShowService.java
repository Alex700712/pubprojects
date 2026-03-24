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
import main.entities.ServiceEntity;
import main.ui.admin.AdminMainMenu;
import main.ui.operations.delete.DeleteWarning;
import main.ui.operations.update.UpdateMenu;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.*;

public class ShowService {

    @FXML
    private Button addServiceButton;

    @FXML
    private ToolBar crudToolbar;

    @FXML
    private Button deleteServiceButton;

    @FXML
    private AnchorPane serviceListPane;

    @FXML
    private Button findServiceButton;

    @FXML
    private TextField findServiceField;

    @FXML
    private Button updateServiceButton;

    @FXML
    private TableView<ServiceEntity> serviceTable;
    @FXML
    private TableColumn<ServiceEntity, Integer> serviceIdColumn;
    @FXML
    private TableColumn<ServiceEntity, String> nameColumn;
    @FXML
    private TableColumn<ServiceEntity, Float> costColumn;

    public void initialize()
    {
        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        fillTable();
    }

    public void fillTable()
    {
        Type serviceListType = new TypeToken<ArrayList<ServiceEntity>>() {}.getType();
        List<ServiceEntity> services = (new Gson().fromJson(new GetService<>(ServiceEntity.class)
                .GetEntities(FIND_ALL_SERVICES), serviceListType));
        ObservableList<ServiceEntity> serviceList = FXCollections.observableArrayList();
        serviceList.addAll(services);
        serviceTable.setItems(serviceList);
    }

    @FXML
    void addService() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/add/AddServiceMenu.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Add Service");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

    @FXML
    void updateService() {
        try {
            if (serviceTable.getSelectionModel().getSelectedItem() != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ShowPosts.class.getResource("/main/operations/update/UpdateMenu.fxml"));
                loader.load();
                UpdateMenu<ServiceEntity> updateMenu = loader.getController();
                updateMenu.setEditEntity(serviceTable.getSelectionModel().getSelectedItem());
                updateMenu.setComboBoxData();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Update Service");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteService() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/delete/DeleteWarning.fxml"));
            loader.load();
            DeleteWarning<ServiceEntity> deleteWarning = loader.getController();
            deleteWarning.setDeleteEntity(serviceTable.getSelectionModel().getSelectedItem());
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Delete Service");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

    @FXML
    void findService() {
        if(!findServiceField.getText().isEmpty())
        {
            try
            {
                ObservableList<ServiceEntity> serviceList = FXCollections.observableArrayList();
                ServiceEntity service = new GetService<>(ServiceEntity.class)
                        .GetEntityById(FIND_SERVICE, findServiceField.getText());
                serviceList.add(service);
                serviceTable.setItems(serviceList);
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