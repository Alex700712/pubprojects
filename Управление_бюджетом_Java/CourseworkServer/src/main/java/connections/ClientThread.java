package connections;

import com.google.gson.Gson;
import connections.TCP.*;
import entities.*;
import enums.*;
import service.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientThread implements Runnable{
    private Socket clientSocket;
    private Request request;
    private Response response;
    private Gson gson;
    private BufferedReader in;
    private PrintWriter out;
    private UserService userService;
    private PostService postService;
    private DepartmentService departmentService;
    private FinanceService financeService;
    private EmployeeService employeeService;
    private ClientService clientService;
    private ServiceEntityService serviceEntityService;

    public ClientThread(Socket clientSocket) throws IOException {
        response = new Response();
        request = new Request();
        this.clientSocket = clientSocket;
        gson = new Gson();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
        this.userService = new UserService();
        this.postService = new PostService();
        this.departmentService = new DepartmentService();
        this.financeService = new FinanceService();
        this.employeeService = new EmployeeService();
        this.clientService = new ClientService();
        this.serviceEntityService = new ServiceEntityService();
    }

    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {
                String message = in.readLine();

                request = gson.fromJson(message, Request.class);
                switch (request.getRequestType()) {
                    case LOGIN: {
                        User loginUser = gson.fromJson(request.getRequestMessage(), User.class);
                        if (userService.findAllEntities().stream().anyMatch(x -> x.getLogin().toLowerCase().equals(loginUser.getLogin().toLowerCase()))
                                && userService.findAllEntities().stream().anyMatch(x -> x.getPassword().equals(loginUser.getPassword()))) {
                            User user = userService.findAllEntities().stream().filter(x -> x.getLogin().toLowerCase().equals(loginUser.getLogin().toLowerCase())).findFirst().get();
                            user = userService.findEntity(user.getUserId());
                            user.getEmployee().cutRelation();
                            response = new Response(ResponseStatus.OK, "Вход выполнен", gson.toJson(user));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Такого пользователя не существует или неправильный пароль!", "");
                        }
                        break;
                    }
                    case REGISTER_USER: {
                        User registeredUser = gson.fromJson(request.getRequestMessage(), User.class);
                        if (userService.findAllEntities().stream().noneMatch(
                                x -> x.getLogin().toLowerCase().equals(registeredUser.getLogin().toLowerCase()))) {
                            userService.saveEntity(registeredUser);
                            response = new Response(ResponseStatus.OK, "Пользователь успешно создан.", gson.toJson(registeredUser));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Такой пользователь уже существует!", "");
                        }
                        break;
                    }
                    case UPDATE_USER: {
                        User user = gson.fromJson(request.getRequestMessage(), User.class);
                        userService.updateEntity(user);
                        response = new Response(ResponseStatus.OK, "Пользователь успешно обновлён", "");
                        break;
                    }
                    case FIND_USER: {
                        Integer userId = gson.fromJson(request.getRequestMessage(), Integer.class);
                        User user = userService.findEntity(userId);
                        user.getEmployee().cutRelation();
                        if (user != null)
                            response = new Response(ResponseStatus.OK, "", gson.toJson(user));
                        else response = new Response(ResponseStatus.ERROR, "Пользователь не найден", null);
                        break;
                    }
                    case FIND_ALL_USERS: {
                        List<User> users = new ArrayList<User>();
                        users = userService.findAllEntities();
                        for(User user: users)
                            user.getEmployee().cutRelation();
                        response = new Response(ResponseStatus.OK, "", gson.toJson(users));
                        break;
                    }
                    case DELETE_USER: {
                        User user = gson.fromJson(request.getRequestMessage(), User.class);
                        userService.deleteEntity(user);
                        response = new Response(ResponseStatus.OK, "Пользователь успешно удалён", "");
                        break;
                    }
                    case ADD_POST: {
                        Post post = gson.fromJson(request.getRequestMessage(), Post.class);
                        postService.saveEntity(post);
                        response = new Response(ResponseStatus.OK, "Должность успешно добавлена", "");
                        break;
                    }
                    case UPDATE_POST: {
                        Post post = gson.fromJson(request.getRequestMessage(), Post.class);
                        postService.updateEntity(post);
                        response = new Response(ResponseStatus.OK, "Должность успешно обновлёна", "");
                        break;
                    }
                    case FIND_POST: {
                        int postId = gson.fromJson(request.getRequestMessage(), Integer.class);
                        Post post = postService.findEntity(postId);
                        if (post != null)
                            response = new Response(ResponseStatus.OK, "", gson.toJson(post));
                        else response = new Response(ResponseStatus.ERROR, "Должность не найдена", null);
                        break;
                    }
                    case FIND_ALL_POSTS: {
                        List<Post> posts = new ArrayList<Post>();
                        posts = postService.findAllEntities();
                        response = new Response(ResponseStatus.OK, "", gson.toJson(posts));
                        break;
                    }
                    case DELETE_POST: {
                        Post post = gson.fromJson(request.getRequestMessage(), Post.class);
                        postService.deleteEntity(post);
                        response = new Response(ResponseStatus.OK, "Должность успешно удалена", "");
                        break;
                    }
                    case ADD_DEPARTMENT: {
                        Department department = gson.fromJson(request.getRequestMessage(), Department.class);
                        departmentService.saveEntity(department);
                        response = new Response(ResponseStatus.OK, "Подразделение успешно добавлено", "");
                        break;
                    }
                    case UPDATE_DEPARTMENT: {
                        Department department = gson.fromJson(request.getRequestMessage(), Department.class);
                        departmentService.updateEntity(department);
                        response = new Response(ResponseStatus.OK, "Подразделение успешно обновлёно", "");
                        break;
                    }
                    case FIND_DEPARTMENT: {
                        int departmentId = gson.fromJson(request.getRequestMessage(), Integer.class);
                        Department department = departmentService.findEntity(departmentId);
                        if (department != null)
                            response = new Response(ResponseStatus.OK, "", gson.toJson(department));
                        else response = new Response(ResponseStatus.ERROR, "Подразделение не найдено", null);
                        break;
                    }
                    case FIND_ALL_DEPARTMENTS: {
                        List<Department> departments = new ArrayList<Department>();
                        departments = departmentService.findAllEntities();
                        response = new Response(ResponseStatus.OK, "", gson.toJson(departments));
                        break;
                    }
                    case DELETE_DEPARTMENT: {
                        Department department = gson.fromJson(request.getRequestMessage(), Department.class);
                        departmentService.deleteEntity(department);
                        response = new Response(ResponseStatus.OK, "Подразделение успешно удалено", "");
                        break;
                    }
                    case ADD_FINANCE: {
                        Finance finance = gson.fromJson(request.getRequestMessage(), Finance.class);
                        financeService.saveEntity(finance);
                        response = new Response(ResponseStatus.OK, "Операция успешно добавлена", "");
                        break;
                    }
                    case UPDATE_FINANCE: {
                        Finance finance = gson.fromJson(request.getRequestMessage(), Finance.class);
                        financeService.updateEntity(finance);
                        response = new Response(ResponseStatus.OK, "Операция успешно обновлена", "");
                        break;
                    }
                    case FIND_FINANCE: {
                        int financeId = gson.fromJson(request.getRequestMessage(), Integer.class);
                        Finance finance = financeService.findEntity(financeId);
                        if(finance != null)
                            response = new Response(ResponseStatus.OK, "", gson.toJson(finance));
                        else response = new Response(ResponseStatus.ERROR, "", null);
                        break;
                    }
                    case FIND_ALL_FINANCES: {
                        List<Finance> finances = new ArrayList<>();
                        finances = financeService.findAllEntities();
                        response = new Response(ResponseStatus.OK, "", gson.toJson(finances));
                        break;
                    }
                    case DELETE_FINANCE: {
                        Finance finance = gson.fromJson(request.getRequestMessage(), Finance.class);
                        finance.cutRelation();
                        financeService.deleteEntity(finance);
                        response = new Response(ResponseStatus.OK, "Операция успешно удалена", "");
                        break;
                    }
                    case ADD_EMPLOYEE: {
                        Employee employee = gson.fromJson(request.getRequestMessage(), Employee.class);
                        employee.getUser().findRelation();
                        employeeService.saveEntity(employee);
                        response = new Response(ResponseStatus.OK, "Сотрудник успешно добавлен", "");
                        break;
                    }
                    case UPDATE_EMPLOYEE: {
                        Employee employee = gson.fromJson(request.getRequestMessage(), Employee.class);
                        employeeService.updateEntity(employee);
                        response = new Response(ResponseStatus.OK, "Сотрудник успешно обновлён", "");
                        break;
                    }
                    case FIND_EMPLOYEE: {
                        int employeeId = gson.fromJson(request.getRequestMessage(), Integer.class);
                        Employee employee = employeeService.findEntity(employeeId);
                        if (employee != null) {
                            employee.getUser().cutRelation();
                            response = new Response(ResponseStatus.OK, "", gson.toJson(employee));
                        }
                        else response = new Response(ResponseStatus.ERROR, "Сотрудник не найден", null);
                        break;
                    }
                    case FIND_ALL_EMPLOYEES: {
                        List<Employee> employees = new ArrayList<Employee>();
                        employees = employeeService.findAllEntities();
                        for(Employee employee: employees)
                            employee.getUser().cutRelation();
                        response = new Response(ResponseStatus.OK, "", gson.toJson(employees));
                        break;
                    }
                    case DELETE_EMPLOYEE: {
                        Employee employee = gson.fromJson(request.getRequestMessage(), Employee.class);
                        User user = employee.getUser();
                        employee.cutRelation();
                        employee.setUser(user);
                        employeeService.deleteEntity(employee);
                        response = new Response(ResponseStatus.OK, "Сотрудник успешно удалена", "");
                        break;
                    }
                    case ADD_CLIENT: {
                        Client client = gson.fromJson(request.getRequestMessage(), Client.class);
                        clientService.saveEntity(client);
                        response = new Response(ResponseStatus.OK, "Клиент успешно добавлен", "");
                        break;
                    }
                    case UPDATE_CLIENT: {
                        Client client = gson.fromJson(request.getRequestMessage(), Client.class);
                        clientService.updateEntity(client);
                        response = new Response(ResponseStatus.OK, "Клиент успешно обновлён", "");
                        break;
                    }
                    case FIND_CLIENT: {
                        int clientId = gson.fromJson(request.getRequestMessage(), Integer.class);
                        Client client = clientService.findEntity(clientId);
                        if (client != null)
                            response = new Response(ResponseStatus.OK, "", gson.toJson(client));
                        else response = new Response(ResponseStatus.ERROR, "Клиент не найден", null);
                        break;
                    }
                    case FIND_ALL_CLIENTS: {
                        List<Client> clients = new ArrayList<Client>();
                        clients = clientService.findAllEntities();
                        response = new Response(ResponseStatus.OK, "", gson.toJson(clients));
                        break;
                    }
                    case DELETE_CLIENT: {
                        Client client = gson.fromJson(request.getRequestMessage(), Client.class);
                        client.cutRelations();
                        clientService.deleteEntity(client);
                        response = new Response(ResponseStatus.OK, "Клиент успешно удалён", "");
                        break;
                    }
                    case ADD_SERVICE: {
                        ServiceEntity serviceEntity = gson.fromJson(request.getRequestMessage(), ServiceEntity.class);
                        serviceEntityService.saveEntity(serviceEntity);
                        response = new Response(ResponseStatus.OK, "Услуга успешно добавлена", "");
                        break;
                    }
                    case UPDATE_SERVICE: {
                        ServiceEntity serviceEntity = gson.fromJson(request.getRequestMessage(), ServiceEntity.class);
                        serviceEntityService.updateEntity(serviceEntity);
                        response = new Response(ResponseStatus.OK, "Услуга успешно обновлена", "");
                        break;
                    }
                    case FIND_SERVICE: {
                        int serviceId = gson.fromJson(request.getRequestMessage(), Integer.class);
                        ServiceEntity serviceEntity = serviceEntityService.findEntity(serviceId);
                        if (serviceEntity != null)
                            response = new Response(ResponseStatus.OK, "", gson.toJson(serviceEntity));
                        else response = new Response(ResponseStatus.ERROR, "Услуга не найдена", null);
                        break;
                    }
                    case FIND_ALL_SERVICES: {
                        List<ServiceEntity> serviceEntities = new ArrayList<ServiceEntity>();
                        serviceEntities = serviceEntityService.findAllEntities();
                        response = new Response(ResponseStatus.OK, "", gson.toJson(serviceEntities));
                        break;
                    }
                    case DELETE_SERVICE: {
                        ServiceEntity serviceEntity = gson.fromJson(request.getRequestMessage(), ServiceEntity.class);
                        serviceEntityService.deleteEntity(serviceEntity);
                        response = new Response(ResponseStatus.OK, "Услуга успешно удалена", "");
                        break;
                    }

                }
                if(request.getRequestType().equals(RequestType.QUIT))
                    break;
                out.println(gson.toJson(response));
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Клиент " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " закрыл соединение.");
            try {
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
