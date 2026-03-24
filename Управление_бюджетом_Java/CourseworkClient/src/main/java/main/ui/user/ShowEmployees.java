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
import main.entities.Employee;
import main.ui.admin.AdminMainMenu;
import main.ui.operations.delete.DeleteWarning;
import main.ui.operations.update.UpdateMenu;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.*;

public class ShowEmployees {

    @FXML
    private AnchorPane accountListPane;

    @FXML
    private Button findEmployeeButton;

    @FXML
    private TextField findEmployeeField;

    @FXML
    private Button postDiagramButton;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, Integer> employeeIdColumn;
    @FXML
    private TableColumn<Employee, String> fullNameColumn;
    @FXML
    private TableColumn<Employee, Integer> userIdColumn;
    @FXML
    private TableColumn<Employee, Integer> postIdColumn;
    @FXML
    private TableColumn<Employee, Integer> departmentIdColumn;

    @FXML
    private ToolBar crudToolbar;

    public void initialize()
    {
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        postIdColumn.setCellValueFactory(new PropertyValueFactory<>("postId"));
        departmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("departmentId"));

        fillTable();
    }

    @FXML
    void findEmployee() {
        if(!findEmployeeField.getText().isEmpty())
        {
            try
            {
                ObservableList<Employee> employeeList = FXCollections.observableArrayList();
                Employee employee = new GetService<>(Employee.class)
                        .GetEntityById(FIND_EMPLOYEE, findEmployeeField.getText());
                if(employee != null)
                    employee.getUsers().setEmployee(employee);
                employeeList.add(employee);
                employeeTable.setItems(employeeList);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else fillTable();
    }

    public void fillTable()
    {
        Type employeeListType = new TypeToken<ArrayList<Employee>>() {}.getType();
        List<Employee> employees = (new Gson().fromJson(new GetService<>(Employee.class)
                .GetEntities(FIND_ALL_EMPLOYEES), employeeListType));
        for(Employee employee: employees)
            employee.getUsers().setEmployee(employee);
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        employeeList.addAll(employees);
        employeeTable.setItems(employeeList);
    }

    public void showPostDiagram() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AdminMainMenu.class.getResource("/main/charts/PostPieChart.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Post diagram");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void addEmployee() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/add/AddEmployeeMenu.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Add Employee");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

    @FXML
    void updateEmployee(ActionEvent event) throws IOException {
        if(employeeTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/update/UpdateMenu.fxml"));
            loader.load();
            UpdateMenu<Employee> updateMenu = loader.getController();
            updateMenu.setEditEntity(employeeTable.getSelectionModel().getSelectedItem());
            updateMenu.setComboBoxData();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Update Employee");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void deleteEmployee(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/delete/DeleteWarning.fxml"));
            loader.load();
            DeleteWarning<Employee> deleteWarning = loader.getController();
            deleteWarning.setDeleteEntity(employeeTable.getSelectionModel().getSelectedItem());
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Delete Employee");
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
