package main.ui.сharts;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.connection.GetService;
import main.entities.Employee;
import main.entities.Finance;
import main.entities.Post;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

import static main.enums.RequestType.*;

public class FinanceLineChart {

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
    @FXML
    private LineChart<String, Number> incomeLineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private static Date before = null;
    private static Date after = null;

    private static String direction;

    @FXML
    void initialize()
    {
        yAxis.setLabel("BYN");
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.setName(direction);
        ObservableList<XYChart.Data<String, Number>> operation = FXCollections.observableArrayList();

        Type financeListType = new TypeToken<ArrayList<Finance>>() {}.getType();
        List<Finance> finances = (new Gson().fromJson(new GetService<Finance>(Finance.class)
                .GetEntities(FIND_ALL_FINANCES), financeListType));
        finances.sort(Comparator.comparing(Finance::getDate));
        if (before != null && after != null) {
            for (Finance finance : finances) {
                if (finance.getDirection().equals(direction)) {
                    if (!finance.getDate().before(before) && !finance.getDate().after(after)) {
                        operation.add(new XYChart.Data<>(finance.getStringDate(), finance.getMoney()));
                    }
                }
            }
        }

        else {
            for (Finance finance : finances) {
                if (finance.getDirection().equals(direction)) {
                    operation.add(new XYChart.Data<>(finance.getStringDate(), finance.getMoney()));
                }
            }
        }


        series.setData(operation);
        incomeLineChart.getData().add(series);
    }

    public static void setPeriod(Date beforePeriod, Date afterPeriod)
    {
        before = beforePeriod;
        after = afterPeriod;
    }

    public static Date getBefore() { return before; }

    public static Date getAfter() { return after; }

    public static void setDirection(String dir) { direction = dir; }

    public void writeToFile() {
        if (!nameField.getText().equals("")) {
            nameError.setVisible(false);
            String filename = "Финансы/" + nameField.getText() + ".txt";
            try (FileWriter out = new FileWriter(filename, false))
            {
                saveError.setVisible(false);

                Type financeListType = new TypeToken<ArrayList<Finance>>() {}.getType();
                List<Finance> finances = (new Gson().fromJson(new GetService<Finance>(Finance.class)
                        .GetEntities(FIND_ALL_FINANCES), financeListType));
                finances.sort(Comparator.comparing(Finance::getDate));
                out.write(direction);
                if(before != null && after != null)
                {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    out.write(format.format(before + "-" + after));
                }
                out.write('\n');

                ObservableList<XYChart.Series<String,Number>> seriesList =  incomeLineChart.getData();
                for(XYChart.Series<String,Number> series : seriesList)
                {
                    ObservableList<XYChart.Data<String,Number>> operations = series.getData();
                    for(XYChart.Data<String,Number> data : operations)
                        out.write(data.getXValue() + " " + data.getYValue() + '\n');
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
            String filename = "Финансы/" + nameField.getText() + ".txt";
            try (FileReader in = new FileReader(filename))
            {
                readError.setVisible(false);
                yAxis.setLabel("BYN");
                XYChart.Series<String,Number> series = new XYChart.Series<>();
                ObservableList<XYChart.Data<String, Number>> operation = FXCollections.observableArrayList();
                Date before_read = null;
                Date after_read = null;

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
                        if(parts[0].equals("Доход") || parts[0].equals("Расход"))
                        {
                            series.setName(parts[0]);
                            if(parts.length > 1)
                            {
                                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                                String[] date = parts[1].split("-");
                                before_read = format.parse(date[0]);
                                after_read = format.parse(date[1]);
                            }
                        }
                        else
                        {
                            operation.add(new XYChart.Data<>(parts[0], Integer.parseInt( parts[1])));
                        }
                    }
                }
                series.setData(operation);
                incomeLineChart.getData().clear();
                incomeLineChart.getData().add(series);

            } catch (Exception e) {
                readError.setVisible(true);
                e.printStackTrace();
            }
        }
        else {
            incomeLineChart.getData().clear();
            initialize();
        }
    }
}
