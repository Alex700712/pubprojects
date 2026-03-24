package main.ui.сharts;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.connection.GetService;
import main.entities.Employee;
import main.entities.Post;
import main.enums.RequestType;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.FIND_ALL_EMPLOYEES;
import static main.enums.RequestType.FIND_ALL_POSTS;

public class PostsPieChart {

    @FXML
    private PieChart postPieChart;

    @FXML
    private Button reportButton;

    @FXML
    private Button readButton;

    @FXML
    private Label saveError;

    @FXML
    private Label readError;

    @FXML
    private Label nameError;

    @FXML
    private TextField nameField;

    public void initialize()
    {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        Type postListType = new TypeToken<ArrayList<Post>>() {}.getType();
        List<Post> posts = (new Gson().fromJson(new GetService<>(Post.class)
                .GetEntities(FIND_ALL_POSTS), postListType));

        Type employeeListType = new TypeToken<ArrayList<Employee>>() {}.getType();
        List<Employee> employees = (new Gson().fromJson(new GetService<>(Employee.class)
                .GetEntities(FIND_ALL_EMPLOYEES), employeeListType));
        for(Post post: posts)
        {
            int count = 0;
            for (Employee employee: employees)
            {
                if(employee.getPosts() != null && employee.getPosts().getPostId() == post.getPostId())
                    count++;
            }
            data.add(new PieChart.Data(post.getName(), count));
        }
        postPieChart.setData(data);
    }

    public void writeToFile() {
        if (!nameField.getText().equals("")) {
            nameError.setVisible(false);
            String filename = "Должности/" + nameField.getText() + ".txt";
            try (FileWriter out = new FileWriter(filename, false))
            {
                saveError.setVisible(false);
                Type postListType = new TypeToken<ArrayList<Post>>() {}.getType();
                List<Post> posts = (new Gson().fromJson(new GetService<>(Post.class)
                        .GetEntities(FIND_ALL_POSTS), postListType));

                Type employeeListType = new TypeToken<ArrayList<Employee>>() {}.getType();
                List<Employee> employees = (new Gson().fromJson(new GetService<>(Employee.class)
                        .GetEntities(FIND_ALL_EMPLOYEES), employeeListType));

                for(Post post: posts)
                {
                    int count = 0;
                    for (Employee employee: employees)
                    {
                        if(employee.getPosts().getPostId() == post.getPostId())
                            count++;
                    }
                    out.write(post.getName() + " : " + count + '\n');
                }
            } catch (Exception e) {
                saveError.setVisible(true);
                e.printStackTrace();
            }
        }
        else nameError.setVisible(true);
    }

    public void readFromFile()
    {
        if (!nameField.getText().equals("")) {
            nameError.setVisible(false);
            String filename = "Должности/" + nameField.getText() + ".txt";
            try (FileReader in = new FileReader(filename))
            {
                readError.setVisible(false);
                ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

                while (in.ready())
                {
                    String string = "";
                    char[] buffer = new char[100];
                    in.read(buffer);
                    string = String.valueOf(buffer);
                    string = string.trim();
                    String[] lines = string.split("\n");
                    for(String line : lines) {
                        String[] parts = line.split(" ");
                        int count = Integer.parseInt(parts[2]);
                        data.add(new PieChart.Data(parts[0], count));
                    }
                }
                postPieChart.setData(data);

            } catch (Exception e) {
                readError.setVisible(true);
                e.printStackTrace();
            }
        }
        else initialize();
    }
}
