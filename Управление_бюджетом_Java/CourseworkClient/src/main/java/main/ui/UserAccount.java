package main.ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.connection.ClientSocket;
import main.connection.GetService;
import main.connection.TCP.Request;
import main.connection.TCP.Response;
import main.entities.Employee;
import main.entities.User;
import main.enums.RequestType;
import main.enums.ResponseStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserAccount {

    @FXML
    private AnchorPane accountPane;

    @FXML
    private Button changePasswordButton;

    @FXML
    private TextField changePasswordField;

    @FXML
    private Label changeSuccess;

    @FXML
    private Label changeErrorLength;

    @FXML
    private Label changeError;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label employeeIdLabel;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Label postLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label userIdLabel;

    @FXML
    private int currentUserId;

    @FXML
    void initialize(){

    }

    public AnchorPane getAccountPane() {
        return accountPane;
    }

    public void setInfo() throws IOException {
        User user = new GetService<>(User.class).GetEntityById(RequestType.FIND_USER, String.valueOf(currentUserId));
        userIdLabel.setText("ID : " + currentUserId);
        loginLabel.setText("Логин : " + user.getLogin());
        roleLabel.setText("Роль : " + user.getRole());
        Type employeeListType = new TypeToken<ArrayList<Employee>>() {}.getType();
        List<Employee> employees = (new Gson().fromJson(new GetService<>(Employee.class).
                GetEntities(RequestType.FIND_ALL_EMPLOYEES), employeeListType));
        for (Employee employee: employees) {
            if(employee.getUsers().getUserId() == user.getUserId())
            {
                employeeIdLabel.setText("ID : " + employee.getEmployeeId());
                fullNameLabel.setText("ФИО : " + employee.getName());

                if(employee.getDepartments() != null)
                    departmentLabel.setText("Подразделение : " + employee.getDepartments().getName());
                else departmentLabel.setText("Подразделение : -");

                if(employee.getPosts() != null)
                    postLabel.setText("Должность : " + employee.getPosts().getName());
                else postLabel.setText("Должность : -");
                break;
            }
        }
    }

    public void ChangePasswordPressed(ActionEvent actionEvent) throws IOException
    {
        if(changePasswordField.getText().isEmpty() || changePasswordField.getText().length() < 4)
        {
            changeSuccess.setVisible(false);
            changeError.setVisible(false);
            changeErrorLength.setVisible(true);
        }
        else
        {
            changeErrorLength.setVisible(false);
            User user = new GetService<>(User.class).GetEntityById(RequestType.FIND_USER, String.valueOf(currentUserId));
            user.setPassword(changePasswordField.getText());

            Request request = new Request();
            Response response = new Response();
            request.setRequestType(RequestType.UPDATE_USER);
            request.setRequestMessage(new Gson().toJson(user));
            ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
            ClientSocket.getInstance().getOut().flush();
            String answer = ClientSocket.getInstance().getInStream().readLine();
            response = new Gson().fromJson(answer, Response.class);
            if(response.getResponseStatus() == ResponseStatus.OK)
                changeSuccess.setVisible(true);
            else changeError.setVisible(true);
        }
    }

    public void setCurrentUserId(int id) { this.currentUserId = id; }

    public int getCurrentUserId() { return this.currentUserId; }

}

