package main.ui.operations.delete;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.connection.ClientSocket;
import main.connection.GetService;
import main.connection.TCP.Request;
import main.connection.TCP.Response;
import main.enums.RequestType;
import main.entities.*;
import main.ui.admin.AdminFinance;
import main.ui.operations.update.UpdateMenu;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.*;

public class DeleteWarning<T> {

    public T entity;
    @FXML
    private Button cancelButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label idLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label deleteEmployeeLabel;

    @FXML
    private Label deleteUserLabel;

    private RequestType requestType;

    @FXML
    public void initialize()
    {

    }

    @FXML
    public void backToMenu() { cancelButton.getScene().getWindow().hide(); }

    @FXML
    public void deleteEntity() {
        try {
            if (entity.getClass().equals(User.class))
                ((User) entity).getEmployee().setUsers(null);
//            deleteEmployee((User)entity);
            if (entity.getClass().equals(Employee.class))
                ((Employee) entity).getUsers().setEmployee(null);
            Request request = new Request();
            request.setRequestType(requestType);
            request.setRequestMessage(new Gson().toJson(entity));
            ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
            ClientSocket.getInstance().getOut().flush();
            String answer = ClientSocket.getInstance().getInStream().readLine();
            Response response = new Gson().fromJson(answer, Response.class);

            if(entity.getClass().equals((Finance.class)))
            {
                String temp = AdminFinance.getMoneyLabel();
                temp = temp.split(" ")[0];
                int balance = 0;
                if(((Finance) entity).getDirection().equals("Доход"))
                    balance = Integer.parseInt(temp) - ((Finance) entity).getMoney();
                else
                    balance = Integer.parseInt(temp) + ((Finance) entity).getMoney();
                AdminFinance.setMoneyLabel(balance + " BYN");
                writeMoney();
            }
            backToMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDeleteEntity(T entity) throws IOException {
        this.entity = entity;
        typeLabel.setText("Тип: " + entity.getClass().getSimpleName());
        switch (entity.getClass().getSimpleName())
        {
            case "User":
            {
                deleteUserLabel.setVisible(true);
                requestType = RequestType.DELETE_USER;
                idLabel.setText("id: " + ((User) entity).getUserId());
                break;
            }
            case "Post":
            {
                requestType = RequestType.DELETE_POST;
                idLabel.setText("id: " + ((Post) entity).getPostId());
                Type employeeListType = new TypeToken<ArrayList<Employee>>() {}.getType();
                List<Employee> employees = (new Gson().fromJson(new GetService<>(Employee.class)
                        .GetEntities(FIND_ALL_EMPLOYEES), employeeListType));
                for (Employee employee : employees)
                    if (employee.getPosts() != null && employee.getPosts().getPostId() == ((Post) entity).getPostId()) {
                        employee.setPosts(null);
                        Request request = new Request();
                        request.setRequestType(UPDATE_EMPLOYEE);
                        request.setRequestMessage(new Gson().toJson(employee));
                        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
                        ClientSocket.getInstance().getOut().flush();
                        String answer = ClientSocket.getInstance().getInStream().readLine();
                        Response response = new Gson().fromJson(answer, Response.class);
                    }
                break;
            }
            case "Department": {
                requestType = RequestType.DELETE_DEPARTMENT;
                idLabel.setText("id: " + ((Department) entity).getDepartmentId());
                Type clientListType = new TypeToken<ArrayList<Client>>() {}.getType();
                List<Client> clients = (new Gson().fromJson(new GetService<>(Client.class)
                        .GetEntities(FIND_ALL_CLIENTS), clientListType));
                for (Client client : clients)
                    if (client.getService() != null && client.getService().getServiceId()
                            == ((Department) entity).getDepartmentId())
                    {
                        client.setService(null);
                        Request request = new Request();
                        request.setRequestType(UPDATE_CLIENT);
                        request.setRequestMessage(new Gson().toJson(client));
                        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
                        ClientSocket.getInstance().getOut().flush();
                        String answer = ClientSocket.getInstance().getInStream().readLine();
                        Response response = new Gson().fromJson(answer, Response.class);
                    }

                Type employeeListType = new TypeToken<ArrayList<Employee>>() {}.getType();
                List<Employee> employees = (new Gson().fromJson(new GetService<>(Employee.class)
                        .GetEntities(FIND_ALL_EMPLOYEES), employeeListType));
                for (Employee employee : employees)
                    if (employee.getDepartments() != null && employee.getDepartments().getDepartmentId()
                            == ((Department) entity).getDepartmentId())
                    {
                        employee.setDepartments(null);
                        Request request = new Request();
                        request.setRequestType(UPDATE_EMPLOYEE);
                        request.setRequestMessage(new Gson().toJson(employee));
                        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
                        ClientSocket.getInstance().getOut().flush();
                        String answer = ClientSocket.getInstance().getInStream().readLine();
                        Response response = new Gson().fromJson(answer, Response.class);
                    }

                Type financeListType = new TypeToken<ArrayList<Finance>>() {}.getType();
                List<Finance> financeList = (new Gson().fromJson(new GetService<>(Finance.class)
                        .GetEntities(FIND_ALL_FINANCES), financeListType));
                for (Finance finance : financeList)
                    if (finance.getDepartment() != null && finance.getDepartment().getDepartmentId()
                            == ((Department) entity).getDepartmentId())
                    {
                        finance.setDepartment(null);
                        Request request = new Request();
                        request.setRequestType(UPDATE_FINANCE);
                        request.setRequestMessage(new Gson().toJson(finance));
                        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
                        ClientSocket.getInstance().getOut().flush();
                        String answer = ClientSocket.getInstance().getInStream().readLine();
                        Response response = new Gson().fromJson(answer, Response.class);
                    }
                break;
            }
            case "Finance":
            {
                requestType = RequestType.DELETE_FINANCE;
                idLabel.setText("id: " + ((Finance) entity).getFinanceId());
                break;
            }
            case "Employee":
            {
                deleteEmployeeLabel.setVisible(true);
                requestType = RequestType.DELETE_EMPLOYEE;
                idLabel.setText("id: " + ((Employee) entity).getEmployeeId());
                break;
            }
            case "Client":
            {
                requestType = RequestType.DELETE_CLIENT;
                idLabel.setText("id: " + ((Client) entity).getClientId());
                break;
            }
            case "ServiceEntity":
            {
                Type clientListType = new TypeToken<ArrayList<Client>>() {}.getType();
                List<Client> clients = (new Gson().fromJson(new GetService<>(Client.class)
                        .GetEntities(FIND_ALL_CLIENTS), clientListType));
                for(Client client: clients)
                    if(client.getService() != null && client.getService().getServiceId()
                            == ((ServiceEntity)entity).getServiceId())
                    {
                        client.setService(null);
                        Request request = new Request();
                        request.setRequestType(UPDATE_CLIENT);
                        request.setRequestMessage(new Gson().toJson(client));
                        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
                        ClientSocket.getInstance().getOut().flush();
                        String answer = ClientSocket.getInstance().getInStream().readLine();
                        Response response = new Gson().fromJson(answer, Response.class);
                    }

                requestType = RequestType.DELETE_SERVICE;
                idLabel.setText("id: " + ((ServiceEntity) entity).getServiceId());
                break;
            }
        }
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
