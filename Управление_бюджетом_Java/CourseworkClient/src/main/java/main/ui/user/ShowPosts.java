package main.ui.user;

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
import main.entities.Finance;
import main.entities.Post;
import main.ui.admin.AdminMainMenu;
import main.ui.operations.delete.DeleteWarning;
import main.ui.operations.update.UpdateMenu;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.*;

public class ShowPosts {

    @FXML
    private Button findPostButton;

    @FXML
    private TextField findPostField;

    @FXML
    private TableView<Post> postTable;

    @FXML
    private TableColumn<Post, Integer> postIdColumn;

    @FXML
    private TableColumn<Post, String> nameColumn;

    @FXML
    private TableColumn<Post, Integer> priorityColumn;

    @FXML
    private TableColumn<Post, Float> salaryColumn;

    @FXML
    private AnchorPane postsListPane;

    @FXML
    private ToolBar crudToolbar;

    @FXML
    void initialize()
    {
        postIdColumn.setCellValueFactory(new PropertyValueFactory<>("postId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        fillTable();
    }

    public void fillTable()
    {
        Type postListType = new TypeToken<ArrayList<Post>>() {}.getType();
        List<Post> posts = (new Gson().fromJson(new GetService<>(Post.class)
                .GetEntities(FIND_ALL_POSTS), postListType));
        ObservableList<Post> postList = FXCollections.observableArrayList();
        postList.addAll(posts);
        postTable.setItems(postList);
    }

    @FXML
    public void findPost()
    {
        if(!findPostField.getText().isEmpty())
        {
            try
            {
                ObservableList<Post> postList = FXCollections.observableArrayList();
                Post post = new GetService<>(Post.class)
                        .GetEntityById(FIND_POST, findPostField.getText());
                postList.add(post);
                postTable.setItems(postList);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else fillTable();
    }

    public void setVisibleToolbar()
    {
        crudToolbar.setVisible(true);
        crudToolbar.setDisable(false);
    }

    @FXML
    public void addPost() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/add/AddPostMenu.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Add Post");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }
    @FXML
    public void updatePost() {
        try {
            if (postTable.getSelectionModel().getSelectedItem() != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ShowPosts.class.getResource("/main/operations/update/UpdateMenu.fxml"));
                loader.load();
                UpdateMenu<Post> updateMenu = loader.getController();
                updateMenu.setEditEntity(postTable.getSelectionModel().getSelectedItem());
                updateMenu.setComboBoxData();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Update Post");
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

    @FXML
    public void deletePost()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminMainMenu.class.getResource("/main/operations/delete/DeleteWarning.fxml"));
            loader.load();
            DeleteWarning<Post> deleteWarning = loader.getController();
            deleteWarning.setDeleteEntity(postTable.getSelectionModel().getSelectedItem());
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Delete Post");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
    }

}
