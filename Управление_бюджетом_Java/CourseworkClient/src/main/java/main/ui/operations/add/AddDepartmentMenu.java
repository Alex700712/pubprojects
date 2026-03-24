package main.ui.operations.add;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.connection.ClientSocket;
import main.connection.TCP.Request;
import main.connection.TCP.Response;
import main.entities.Department;
import main.entities.Post;

import static main.enums.RequestType.ADD_DEPARTMENT;
import static main.enums.RequestType.ADD_POST;

public class AddDepartmentMenu {

    @FXML
    private Button addButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField peopleCountField;

    @FXML
    void addDepartment(ActionEvent event) {
        try {
            Department department = new Department();
            department.setName(nameField.getText());
            department.setPeopleCount(Integer.parseInt(peopleCountField.getText()));
            Request request = new Request(ADD_DEPARTMENT, (new Gson()).toJson(department));
            ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
            ClientSocket.getInstance().getOut().flush();
            String answer = ClientSocket.getInstance().getInStream().readLine();
            Response response = new Gson().fromJson(answer, Response.class);
            addButton.getScene().getWindow().hide();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

