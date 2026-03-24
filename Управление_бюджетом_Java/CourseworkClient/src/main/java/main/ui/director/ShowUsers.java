package main.ui.director;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.connection.GetService;
import main.entities.Post;
import main.entities.User;
import main.ui.admin.AdminMainMenu;
import main.ui.operations.delete.DeleteWarning;
import main.ui.operations.update.UpdateMenu;
import main.ui.user.ShowPosts;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.*;

public class ShowUsers {

    @FXML
    private AnchorPane postsListPane;
    @FXML
    private Button findUserButton;

    @FXML
    private TextField findUserField;

    @FXML
    private ToolBar crudToolbar;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> userIdColumn;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> roleColumn;

    public void initialize()
    {
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        fillTable();
    }

    public void fillTable()
    {
        Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
        List<User> users = (new Gson().fromJson(new GetService<>(User.class)
                .GetEntities(FIND_ALL_USERS), userListType));
        for (User user: users)
            user.getEmployee().setUsers(user);
        ObservableList<User> userList = FXCollections.observableArrayList();
        userList.addAll(users);
        userTable.setItems(userList);
    }

    public void setVisibleToolbar()
    {
        crudToolbar.setVisible(true);
        crudToolbar.setDisable(false);
    }

    @FXML
    public void findUser()
    {
        if(!findUserField.getText().isEmpty())
        {
            try
            {
                ObservableList<User> userList = FXCollections.observableArrayList();
                User user = new GetService<>(User.class)
                        .GetEntityById(FIND_USER, findUserField.getText());
                user.getEmployee().setUsers(user);
                userList.add(user);
                userTable.setItems(userList);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else fillTable();
    }

    public void updateUser()
    {
        try {
            if (userTable.getSelectionModel().getSelectedItem() != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ShowPosts.class.getResource("/main/operations/update/UpdateMenu.fxml"));
                loader.load();
                UpdateMenu<User> updateMenu = loader.getController();
                updateMenu.setEditEntity(userTable.getSelectionModel().getSelectedItem());
                updateMenu.setComboBoxData();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Update User");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteUser() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/delete/DeleteWarning.fxml"));
            loader.load();
            DeleteWarning<User> deleteWarning = loader.getController();
            deleteWarning.setDeleteEntity(userTable.getSelectionModel().getSelectedItem());
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Delete User");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
