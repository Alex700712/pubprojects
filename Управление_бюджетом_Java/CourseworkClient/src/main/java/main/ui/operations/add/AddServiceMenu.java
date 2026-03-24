package main.ui.operations.add;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.connection.ClientSocket;
import main.connection.TCP.Request;
import main.connection.TCP.Response;
import main.entities.ServiceEntity;
import main.enums.RequestType;

public class AddServiceMenu {

    @FXML
    private Button addButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField costField;

    @FXML
    void addService() {
        try {
            ServiceEntity service = new ServiceEntity();
            service.setName(nameField.getText());
            service.setCost(Integer.parseInt(costField.getText()));
            Request request = new Request(RequestType.ADD_SERVICE, (new Gson()).toJson(service));
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


