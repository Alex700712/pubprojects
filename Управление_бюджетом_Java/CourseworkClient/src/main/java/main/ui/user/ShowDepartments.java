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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.connection.GetService;
import main.entities.Department;
import main.entities.Post;
import main.ui.admin.AdminMainMenu;
import main.ui.operations.delete.DeleteWarning;
import main.ui.operations.update.UpdateMenu;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.*;

public class ShowDepartments {

    @FXML
    private Button findDepartmentButton;

    @FXML
    private TextField findDepartmentField;

    @FXML
    private AnchorPane departmentListPane;

    @FXML
    private TableView<Department> departmentTable;

    @FXML
    private TableColumn<Department, Integer> departmentIdColumn;

    @FXML
    private TableColumn<Department, String> nameColumn;

    @FXML
    private TableColumn<Department, Integer> peopleCountColumn;

    @FXML
    private ToolBar crudToolbar;

    @FXML
    void initialize()
    {
        departmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        peopleCountColumn.setCellValueFactory(new PropertyValueFactory<>("peopleCount"));

        fillTable();
    }

    public void fillTable()
    {
        Type departmentListType = new TypeToken<ArrayList<Department>>() {}.getType();
        List<Department> departments = (new Gson().fromJson(new GetService<>(Department.class)
                .GetEntities(FIND_ALL_DEPARTMENTS), departmentListType));
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        departmentList.addAll(departments);
        departmentTable.setItems(departmentList);
    }

    @FXML
    void findDepartment() {
        if(!findDepartmentField.getText().isEmpty())
        {
            try
            {
                ObservableList<Department> departmentList = FXCollections.observableArrayList();
                Department department = new GetService<>(Department.class)
                        .GetEntityById(FIND_DEPARTMENT, findDepartmentField.getText());
                departmentList.add(department);
                departmentTable.setItems(departmentList);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else fillTable();
    }

    @FXML
    public void addDepartment()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/add/AddDepartmentMenu.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Add Department");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

    @FXML
    public void updateDepartment() {
        try {
            if (departmentTable.getSelectionModel().getSelectedItem() != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ShowPosts.class.getResource("/main/operations/update/UpdateMenu.fxml"));
                loader.load();
                UpdateMenu<Department> updateMenu = loader.getController();
                updateMenu.setEditEntity(departmentTable.getSelectionModel().getSelectedItem());
                updateMenu.setComboBoxData();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Update Department");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteDepartment()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/delete/DeleteWarning.fxml"));
            loader.load();
            DeleteWarning<Department> deleteWarning = loader.getController();
            deleteWarning.setDeleteEntity(departmentTable.getSelectionModel().getSelectedItem());
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Delete Department");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }


    public void setVisibleToolbar()
    {
        crudToolbar.setVisible(true);
        crudToolbar.setDisable(false);
    }

}
