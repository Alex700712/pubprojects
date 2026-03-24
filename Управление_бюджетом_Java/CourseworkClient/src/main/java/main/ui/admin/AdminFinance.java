package main.ui.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.connection.GetService;
import main.entities.Finance;
import main.ui.operations.delete.DeleteWarning;
import main.ui.operations.update.UpdateMenu;
import main.ui.сharts.FinanceLineChart;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static main.enums.RequestType.FIND_ALL_FINANCES;
import static main.enums.RequestType.FIND_FINANCE;

public class AdminFinance {

    @FXML
    private Button addFinanceButton;

    @FXML
    private TextField afterPeriod;

    @FXML
    private RadioButton allTimeExpense;

    @FXML
    private TextField beforePeriod;

    @FXML
    private Button deleteFinanceButton;

    @FXML
    private Button diagramButton;

    @FXML
    private TableView<Finance> financeTable;

    @FXML
    private Button findFinanceButton;

    @FXML
    private TextField findFinanceField;

    @FXML
    private RadioButton periodExpense;

    @FXML
    private Button updateFinanceButton;

    @FXML
    private Label dateReadLabel;

    @FXML
    private Label moneyLabel;

    private static String moneyLabelText;

    @FXML
    private RadioButton expenseDiagram;

    @FXML
    private RadioButton incomeDiagram;
    @FXML
    private TableColumn<Finance, Integer> financeId;
    @FXML
    private TableColumn<Finance, String> directionColumn;
    @FXML
    private TableColumn<Finance, Float> moneyColumn;
    @FXML
    private TableColumn<Finance, Date> dateColumn;
    @FXML
    private TableColumn<Finance, Integer> departmentIdColumn;
    @FXML
    private TableColumn<Finance, String> descriptionColumn;

    @FXML
    void initialize()
    {
        readMoney();
        //moneyLabel.setText(moneyLabelText);

        ToggleGroup toggleGroup = new ToggleGroup();
        allTimeExpense.setToggleGroup(toggleGroup);
        periodExpense.setToggleGroup(toggleGroup);

        ToggleGroup toggleGroup1 = new ToggleGroup();
        incomeDiagram.setToggleGroup(toggleGroup1);
        expenseDiagram.setToggleGroup(toggleGroup1);

        financeId.setCellValueFactory(new PropertyValueFactory<>("financeId"));
        directionColumn.setCellValueFactory(new PropertyValueFactory<>("direction"));
        moneyColumn.setCellValueFactory(new PropertyValueFactory<>("money"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("stringDate"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        departmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("departmentId"));

        fillTable();
    }

    @FXML
    void findFinance(ActionEvent event) {
        if(!findFinanceField.getText().isEmpty())
        {
            try
            {
                ObservableList<Finance> financeList = FXCollections.observableArrayList();
                Finance finance = new GetService<>(Finance.class)
                        .GetEntityById(FIND_FINANCE, findFinanceField.getText());
                financeList.add(finance);
                financeTable.setItems(financeList);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else {
            fillTable();
            moneyLabel.setText(moneyLabelText);
        }
    }

    @FXML
    void addFinance() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/add/AddFinanceMenu.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Add Finance");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

    @FXML
    void updateFinance(ActionEvent event) throws IOException {
        if(financeTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/update/UpdateMenu.fxml"));
            loader.load();
            UpdateMenu<Finance> updateMenu = loader.getController();
            updateMenu.setEditEntity(financeTable.getSelectionModel().getSelectedItem());
            updateMenu.setComboBoxData();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Update Finance");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void deleteFinance(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/delete/DeleteWarning.fxml"));
            loader.load();
            DeleteWarning<Finance> deleteWarning = loader.getController();
            deleteWarning.setDeleteEntity(financeTable.getSelectionModel().getSelectedItem());
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Delete Finance");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

    public void fillTable()
    {
        Type financeListType = new TypeToken<ArrayList<Finance>>() {}.getType();
        List<Finance> finances = (new Gson().fromJson(new GetService<>(Finance.class)
                .GetEntities(FIND_ALL_FINANCES), financeListType));
        ObservableList<Finance> financeList = FXCollections.observableArrayList();
        financeList.addAll(finances);
        financeTable.setItems(financeList);
    }

    @FXML
    void showFinanceDiagram(ActionEvent event) {
        try
        {
            if (incomeDiagram.isSelected())
                FinanceLineChart.setDirection("Доход");
            else FinanceLineChart.setDirection("Расход");
            if(periodExpense.isSelected()) {
                SimpleDateFormat format = new SimpleDateFormat();
                format.applyPattern("dd.MM.yyyy");
                FinanceLineChart.setPeriod(format.parse(beforePeriod.getText()),
                        format.parse(afterPeriod.getText()));
                dateReadLabel.setVisible(false);
            }
            else dateReadLabel.setVisible(false);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/charts/FinanceLineChart.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Finance diagram");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (ParseException parseException)
        {
            dateReadLabel.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeRadioButton()
    {
        if(allTimeExpense.isSelected())
        {
            beforePeriod.setText("");
            afterPeriod.setText("");
            beforePeriod.setDisable(true);
            afterPeriod.setDisable(true);
        }
        else
        {
            beforePeriod.setDisable(false);
            afterPeriod.setDisable(false);
        }
    }

    public static String getMoneyLabel() { return moneyLabelText; }

    public static void setMoneyLabel(String str) { moneyLabelText = str;}


    public void setMoneyText()
    {
        String filename = "Money.txt";
        try (FileWriter out = new FileWriter(filename, false))
        {
            out.write(moneyLabel.getText());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void readMoney()
    {
        String filename = "Money.txt";
        try (FileReader in = new FileReader(filename))
        {
            char[] buf = new char[20];
            in.read(buf);
            String temp = String.valueOf(buf);
            temp = temp.trim();
            moneyLabel.setText(temp);
            moneyLabelText = temp;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

