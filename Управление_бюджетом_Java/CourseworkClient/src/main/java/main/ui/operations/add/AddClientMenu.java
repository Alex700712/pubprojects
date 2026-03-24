package main.ui.operations.add;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.connection.ClientSocket;
import main.connection.GetService;
import main.connection.TCP.Request;
import main.connection.TCP.Response;
import main.entities.*;

import static main.enums.RequestType.*;

public class AddClientMenu {

    @FXML
    private Button addButton;

    @FXML
    private CheckBox departmentCheckBox;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField fullNameField;

    @FXML
    private CheckBox serviceCheckBox;

    @FXML
    private TextField serviceField;

    @FXML
    void addClient() {
        try {
            Client client = new Client();

            client.setName(fullNameField.getText());
            if (departmentCheckBox.isSelected()) {
                client.setDepartment(new GetService<>(Department.class)
                        .GetEntityById(FIND_DEPARTMENT, departmentField.getText()));
            }
            if (serviceCheckBox.isSelected()) {
                client.setService(new GetService<>(ServiceEntity.class)
                        .GetEntityById(FIND_SERVICE, serviceField.getText()));
            }

            Request request = new Request();
            request.setRequestType(ADD_CLIENT);
            request.setRequestMessage(new Gson().toJson(client));
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

    @FXML
    void appendDepartment() { departmentField.setDisable(!departmentCheckBox.isSelected()); }

    @FXML
    void appendService() { serviceField.setDisable((!serviceCheckBox.isSelected())); }

}

