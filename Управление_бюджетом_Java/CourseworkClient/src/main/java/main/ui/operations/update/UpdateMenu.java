package main.ui.operations.update;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.connection.ClientSocket;
import main.connection.GetService;
import main.connection.TCP.Request;
import main.connection.TCP.Response;
import main.entities.*;
import main.enums.RequestType;
import main.ui.admin.AdminFinance;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UpdateMenu<T> {

    @FXML
    private ComboBox<String> changeComboBox;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private RadioButton expenseRadio;

    @FXML
    private RadioButton incomeRadio;

    @FXML
    private TextField textField;

    @FXML
    private Button updateButton;

    @FXML
    private Label idLabel;

    @FXML
    private Label typeLabel;

    private T editEntity = null;

    private RequestType requestType;

    @FXML
    public void initialize() {
        ToggleGroup toggleGroup = new ToggleGroup();
        incomeRadio.setToggleGroup(toggleGroup);
        expenseRadio.setToggleGroup(toggleGroup);
    }

    @FXML
    public void updateEntity() {
        try {
            if (changeComboBox.getValue() != null) {
                Request request = new Request();
                request.setRequestType(requestType);
                switch (editEntity.getClass().getSimpleName()) {
                    case "User": {
                        updateUser(editEntity);
                        ((User)editEntity).getEmployee().setUsers(null);
                        break;
                    }
                    case "Post": {
                        updatePost(editEntity);
                        break;
                    }
                    case "Department": {
                        updateDepartment(editEntity);
                        break;
                    }
                    case "Finance": {
                        updateFinance(editEntity);
                        break;
                    }
                    case "Employee": {
                        updateEmployee(editEntity);
                        ((Employee)editEntity).getUsers().setEmployee(null);
                        break;
                    }
                    case "Client": {
                        updateClient(editEntity);
                        break;
                    }
                    case "ServiceEntity": {
                        updateServiceEntity(editEntity);
                        break;
                    }
                }

                request.setRequestMessage(new Gson().toJson(editEntity));
                ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
                ClientSocket.getInstance().getOut().flush();
                String answer = ClientSocket.getInstance().getInStream().readLine();
                Response response = new Gson().fromJson(answer, Response.class);

                updateButton.getScene().getWindow().hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(T entity) throws IOException {
        if (changeComboBox.getValue().equals("Логин"))
            ((User) editEntity).setLogin(textField.getText());
        else if (changeComboBox.getValue().equals("Роль")) {
            String role = textField.getText();
            if (role.equals("User") || role.equals("Admin") || role.equals("Director"))
                ((User) editEntity).setRole(textField.getText());
        }
    }

    public void updatePost(T entity) throws IOException {
        if (changeComboBox.getValue().equals("Название"))
            ((Post) editEntity).setName(textField.getText());
        else if (changeComboBox.getValue().equals("Приоритет")) {
            ((Post) editEntity).setPriority(Integer.parseInt(textField.getText()));
        }
        else if(changeComboBox.getValue().equals("Зарплата")) {
            ((Post) editEntity).setSalary(Integer.parseInt(textField.getText()));
        }
    }

    public void updateDepartment(T entity) throws IOException {
        if (changeComboBox.getValue().equals("Название"))
            ((Department) editEntity).setName(textField.getText());
        else if (changeComboBox.getValue().equals("Количество людей")) {
            ((Department) editEntity).setPeopleCount(Integer.parseInt(textField.getText()));
        }
    }

    public void updateFinance(T entity) throws IOException, ParseException {

        switch (changeComboBox.getValue()) {
            case "Направление":
                String prevDirection = ((Finance) editEntity).getDirection();
                if (incomeRadio.isSelected())
                    ((Finance) editEntity).setDirection("Доход");
                else ((Finance) editEntity).setDirection("Расход");

                if(!prevDirection.equals(((Finance) editEntity).getDirection()))
                {
                    String temp = AdminFinance.getMoneyLabel();
                    temp = temp.split(" ")[0];
                    int balance = 0;
                    if(((Finance) editEntity).getDirection().equals("Доход"))
                        balance = Integer.parseInt(temp) + 2 * ((Finance) editEntity).getMoney();
                    else
                        balance = Integer.parseInt(temp) - 2 * ((Finance) editEntity).getMoney();
                    AdminFinance.setMoneyLabel(balance + " BYN");
                    writeMoney();
                }
                break;
            case "Описание":
                ((Finance) editEntity).setDescription(descriptionArea.getText());
                break;
            case "Количество денег":
                int prevMoney = ((Finance) editEntity).getMoney();
                ((Finance) editEntity).setMoney(Integer.parseInt(textField.getText()));

                String temp = AdminFinance.getMoneyLabel();
                temp = temp.split(" ")[0];
                int balance = 0;
                if(((Finance) editEntity).getDirection().equals("Доход"))
                    balance = Integer.parseInt(temp) + ((Finance) editEntity).getMoney() - prevMoney;
                else
                    balance = Integer.parseInt(temp) - ((Finance) editEntity).getMoney() + prevMoney;
                AdminFinance.setMoneyLabel(balance + " BYN");
                writeMoney();
                break;
            case "Дата":
                SimpleDateFormat format = new SimpleDateFormat();
                format.applyPattern("dd.MM.yyyy");
                ((Finance) editEntity).setDate(format.parse(textField.getText()));
                break;
            case "Id Подразделения":
                if (!textField.getText().isEmpty() && Integer.parseInt(textField.getText()) > 0) {
                    ((Finance) editEntity).setDepartment(new GetService<Department>(Department.class).
                            GetEntityById(RequestType.FIND_DEPARTMENT, textField.getText()));
                } else if (textField.getText().isEmpty() || Integer.parseInt(textField.getText()) == -1)
                    ((Finance) editEntity).setDepartment(null);
                break;
        }
    }

    public void updateEmployee(T entity) throws IOException {

        switch (changeComboBox.getValue()) {
            case "ФИО":
                ((Employee) editEntity).setName(textField.getText());
                break;
            case "Id Должности":
                if (!textField.getText().isEmpty() && Integer.parseInt(textField.getText()) > 0) {
                    ((Employee) editEntity).setPosts((new GetService<>(Post.class).
                            GetEntityById(RequestType.FIND_POST, textField.getText())));
                } else if (textField.getText().isEmpty() || Integer.parseInt(textField.getText()) == -1)
                    ((Employee) editEntity).setPosts(null);
                break;
            case "Id Подразделения":
                if (!textField.getText().isEmpty() && Integer.parseInt(textField.getText()) > 0) {
                    ((Employee) editEntity).setDepartments(new GetService<>(Department.class).
                            GetEntityById(RequestType.FIND_DEPARTMENT, textField.getText()));
                } else if (textField.getText().isEmpty() || Integer.parseInt(textField.getText()) == -1)
                    ((Employee) editEntity).setDepartments(null);
                break;
        }
    }

    public void updateClient(T entity) throws IOException {

        switch (changeComboBox.getValue()) {
            case "ФИО":
                ((Client) editEntity).setName(textField.getText());
                break;
            case "Id Подразделения":
                if (!textField.getText().isEmpty() && Integer.parseInt(textField.getText()) > 0) {
                    ((Client) editEntity).setDepartment(new GetService<>(Department.class).
                            GetEntityById(RequestType.FIND_DEPARTMENT, textField.getText()));
                } else if (textField.getText().isEmpty() || Integer.parseInt(textField.getText()) == -1)
                    ((Client) editEntity).setDepartment(null);
                break;
            case "Id Услуги":
                if (!textField.getText().isEmpty() && Integer.parseInt(textField.getText()) > 0) {
                    ((Client) editEntity).setService((new GetService<>(ServiceEntity.class).
                            GetEntityById(RequestType.FIND_SERVICE, textField.getText())));
                } else if (textField.getText().isEmpty() || Integer.parseInt(textField.getText()) == -1)
                    ((Client) editEntity).setService(null);
                break;
        }
    }

    public void updateServiceEntity(T entity) throws IOException {
        if (changeComboBox.getValue().equals("Название"))
            ((ServiceEntity) editEntity).setName(textField.getText());
        else if (changeComboBox.getValue().equals("Стоимость")) {
            ((ServiceEntity) editEntity).setCost(Float.parseFloat(textField.getText()));
        }
    }

    @FXML
    public void changeField() {
        switch (changeComboBox.getValue()) {
            case "Направление": {
                incomeRadio.setVisible(true);
                expenseRadio.setVisible(true);
                textField.setVisible(false);
                descriptionArea.setVisible(false);
                break;
            }
            case "Описание": {
                incomeRadio.setVisible(false);
                expenseRadio.setVisible(false);
                textField.setVisible(false);
                descriptionArea.setVisible(true);
                break;
            }
            default: {
                incomeRadio.setVisible(false);
                expenseRadio.setVisible(false);
                textField.setVisible(true);
                descriptionArea.setVisible(false);
            }
        }
    }

    public void setEditEntity(T entity) {
        this.editEntity = entity;
        typeLabel.setText("Тип: " + entity.getClass().getSimpleName());
        switch (editEntity.getClass().getSimpleName()) {
            case "User": {
                requestType = RequestType.UPDATE_USER;
                idLabel.setText("id: " + ((User) entity).getUserId());
                break;
            }
            case "Post": {
                requestType = RequestType.UPDATE_POST;
                idLabel.setText("id: " + ((Post) entity).getPostId());
                break;
            }
            case "Department": {
                requestType = RequestType.UPDATE_DEPARTMENT;
                idLabel.setText("id: " + ((Department) entity).getDepartmentId());
                break;
            }
            case "Finance": {
                requestType = RequestType.UPDATE_FINANCE;
                idLabel.setText("id: " + ((Finance) entity).getFinanceId());
                break;
            }
            case "Employee": {
                requestType = RequestType.UPDATE_EMPLOYEE;
                idLabel.setText("id: " + ((Employee) entity).getEmployeeId());
                break;
            }
            case "Client": {
                requestType = RequestType.UPDATE_CLIENT;
                idLabel.setText("id: " + ((Client) entity).getClientId());
                break;
            }
            case "ServiceEntity": {
                requestType = RequestType.UPDATE_SERVICE;
                idLabel.setText("id: " + ((ServiceEntity) entity).getServiceId());
                break;
            }
        }
    }

    public void setComboBoxData() {
        ObservableList<String> changeList = FXCollections.observableArrayList();
        switch (editEntity.getClass().getSimpleName()) {
            case "User": {
                changeList.add("Логин");
                changeList.add("Роль");
                break;
            }
            case "Post": {
                changeList.add("Название");
                changeList.add("Приоритет");
                changeList.add("Зарплата");
                break;
            }
            case "Department": {
                changeList.add("Название");
                changeList.add("Количество людей");
                break;
            }
            case "Finance": {
                changeList.add("Направление");
                changeList.add("Количество денег");
                changeList.add("Дата");
                changeList.add("Описание");
                changeList.add("Id Подразделения");
                break;
            }
            case "Employee": {
                changeList.add("ФИО");
                changeList.add("Id Должности");
                changeList.add("Id Подразделения");
                break;
            }
            case "Client": {
                changeList.add("ФИО");
                changeList.add("Id Подразделения");
                changeList.add("Id Услуги");
                break;
            }
            case "ServiceEntity": {
                changeList.add("Название");
                changeList.add("Стоимость");
                break;
            }
        }

        this.changeComboBox.setItems(changeList);
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

