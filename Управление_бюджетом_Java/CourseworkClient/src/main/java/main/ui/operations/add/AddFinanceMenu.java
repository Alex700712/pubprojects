package main.ui.operations.add;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.connection.ClientSocket;
import main.connection.GetService;
import main.connection.TCP.Request;
import main.connection.TCP.Response;
import main.entities.Department;
import main.entities.Finance;
import main.ui.SignIn;
import main.ui.admin.AdminFinance;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import static main.enums.RequestType.*;
import static main.enums.ResponseStatus.*;

public class AddFinanceMenu {

    private AdminFinance financeEdit = new AdminFinance();
    @FXML
    private Button addButton;
    @FXML
    private TextField dateField;

    @FXML
    private TextField departmentField;

    @FXML
    private CheckBox departmentCheckBox;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private RadioButton expenseRadio;

    @FXML
    private RadioButton incomeRadio;

    @FXML
    private TextField moneyField;

    @FXML
    void initialize() {
        ToggleGroup toggleGroup = new ToggleGroup();
        incomeRadio.setToggleGroup(toggleGroup);
        expenseRadio.setToggleGroup(toggleGroup);
    }

    @FXML
    void addFinance(ActionEvent event) {
        try {
            Finance finance = new Finance();
            finance.setMoney(Integer.parseInt(moneyField.getText()));
            finance.setDate(new SimpleDateFormat(("dd.MM.yyyy")).parse(dateField.getText()));
            if(incomeRadio.isSelected())
                finance.setDirection("Доход");
            else finance.setDirection("Расход");
            if(!descriptionArea.getText().equals(""))
                finance.setDescription(descriptionArea.getText());
            if (departmentCheckBox.isSelected()) {
                finance.setDepartment(new GetService<Department>(Department.class)
                        .GetEntityById(FIND_DEPARTMENT, departmentField.getText()));
            }
            Request request = new Request(ADD_FINANCE, (new Gson()).toJson(finance));
            ClientSocket.getInstance().getOut().println((new Gson()).toJson(request));
            ClientSocket.getInstance().getOut().flush();
            String answer = ClientSocket.getInstance().getInStream().readLine();
            Response response = new Gson().fromJson(answer, Response.class);


            String temp = AdminFinance.getMoneyLabel();
            temp = temp.split(" ")[0];
            int balance = 0;
            if(incomeRadio.isSelected())
                balance = Integer.parseInt(temp) + Integer.parseInt(moneyField.getText());
            else
                balance = Integer.parseInt(temp) - Integer.parseInt(moneyField.getText());
            AdminFinance.setMoneyLabel(balance + " BYN");
            writeMoney();

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

    public void writeMoney()
    {
        String filename = "Money.txt";
        try (FileWriter out = new FileWriter(filename, false))
        {
            out.write(AdminFinance.getMoneyLabel());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
