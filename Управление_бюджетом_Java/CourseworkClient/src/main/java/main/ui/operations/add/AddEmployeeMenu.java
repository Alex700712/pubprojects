package main.ui.operations.add;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.connection.ClientSocket;
import main.connection.GetService;
import main.connection.TCP.Request;
import main.connection.TCP.Response;
import main.entities.*;

import java.text.SimpleDateFormat;

import static main.enums.RequestType.*;

public class AddEmployeeMenu {

    @FXML
    private PasswordField acceptPasswordField;

    @FXML
    private Button addButton;

    @FXML
    private CheckBox departmentCheckBox;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox postCheckBox;

    @FXML
    private TextField postField;

    @FXML
    private ComboBox<String> roleCheckBox;

    @FXML
    public void initialize()
    {
        ObservableList<String> roleList = FXCollections.observableArrayList();
        roleList.add("User");
        roleList.add("Admin");
        roleList.add("Director");
        roleCheckBox.setItems(roleList);
    }

    @FXML
    void addEmployee() {
        try {
            Employee employee = new Employee();
            User user = new User();

            user.setLogin(loginField.getText());
            if(passwordField.getText().equals(acceptPasswordField.getText()))
                user.setPassword(passwordField.getText());
            user.setRole(roleCheckBox.getSelectionModel().getSelectedItem());

            employee.setName(fullNameField.getText());
            employee.setUsers(user);
            if (postCheckBox.isSelected()) {
                employee.setPosts(new GetService<>(Post.class)
                        .GetEntityById(FIND_POST, postField.getText()));
            }
            if (departmentCheckBox.isSelected()) {
                employee.setDepartments(new GetService<>(Department.class)
                        .GetEntityById(FIND_DEPARTMENT, departmentField.getText()));
            }

            Request request = new Request();
            request.setRequestType(ADD_EMPLOYEE);
            request.setRequestMessage(new Gson().toJson(employee));
            ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
            ClientSocket.getInstance().getOut().flush();
            String answer = ClientSocket.getInstance().getInStream().readLine();
            Response response1 = new Gson().fromJson(answer, Response.class);

            addButton.getScene().getWindow().hide();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void appendDepartment() {
        departmentField.setDisable(!departmentCheckBox.isSelected());
    }

    @FXML
    void appendPost() {
        postField.setDisable(!postCheckBox.isSelected());
    }

}
