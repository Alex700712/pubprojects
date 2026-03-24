package main.ui.operations.add;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.connection.ClientSocket;
import main.connection.GetService;
import main.connection.TCP.Request;
import main.connection.TCP.Response;
import main.entities.Department;
import main.entities.Finance;
import main.entities.Post;

import java.text.SimpleDateFormat;

import static main.enums.RequestType.*;

public class AddPostMenu {

    @FXML
    private Button addButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priorityField;

    @FXML
    private TextField salaryField;

    @FXML
    void addPost() {
        try {
            Post post = new Post();
            post.setName(nameField.getText());
            post.setPriority(Integer.parseInt(priorityField.getText()));
            post.setSalary(Float.parseFloat(salaryField.getText()));
            Request request = new Request(ADD_POST, (new Gson()).toJson(post));
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
