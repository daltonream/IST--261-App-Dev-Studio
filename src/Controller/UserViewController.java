package Controller;

import Model.Goals;
import Model.UserModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class UserViewController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public TextField userInfo;
    
    @FXML
    private Button quitButton;
    
    @FXML
    private Button createButton;

    @FXML
    private Button tableEditButton;

    @FXML
    private Button tableDeleteButton;
    
    @FXML
    private Button viewButton;

    
    @FXML
    private TableView<Goals> goal;

    @FXML
    private TableColumn<Goals, String> goalDate;

    @FXML
    private TableColumn<Goals, String> goalContents;

    private ObservableList<Goals> goals;
    
    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField height;

    @FXML
    private TextField weight;

    @FXML
    private TextField age;
    
    EntityManager manager;
    

    
    
    @FXML
    void quitButton(ActionEvent event) {
           Platform.exit();
    }
    
    @FXML
    void tableDeleteButton(ActionEvent event) {
        Goals selectedGoal = goal.getSelectionModel().getSelectedItem();
        int id = selectedGoal.getId();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete this goal?");
        alert.setContentText("Are you sure you want to delete this goal entry?");

        Optional<ButtonType> result = alert.showAndWait();
       
        if (result.get() == ButtonType.OK) {
               Goals log = goalQuery(id);
               delete(log);
               initialize();
        }
    }

    @FXML
    void tableEditButton(ActionEvent event) {
        Goals selectedGoal = goal.getSelectionModel().getSelectedItem();
        
        
        if(selectedGoal != null){
            Goals info = goalQuery(selectedGoal.getId());
            System.out.println(info);

            try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GoalView.fxml"));
               Parent createGoalView = loader.load();

               Scene createGoalScene = new Scene(createGoalView);

               GoalViewController controller = loader.getController();
               controller.initData(info);

               Scene currentScene = ((Node) event.getSource()).getScene();
               controller.setPreviousScene(currentScene);

               Stage stage = (Stage) currentScene.getWindow();
               stage.setScene(createGoalScene);
               stage.show();
           } catch (Exception ex) {
               System.out.println(ex.getMessage());
           }

       } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Please Select a Goal");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }
    
    @FXML
    void createButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GoalView.fxml"));
            Parent createGoalView = loader.load();

            Scene createGoalScene = new Scene(createGoalView);

            GoalViewController controller = loader.getController();
            
            Scene currentScene = ((Node) event.getSource()).getScene();
            controller.setPreviousScene(currentScene);

            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(createGoalScene);
            stage.show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    public void refreshTable() {
       // Integer userId = Integer.parseInt(idBox.getText());
       String email = "daltonream@gmail.com"; //dalton.getEmail();
        List<Goals> info = readByUserEmail(email);
        setTableData(info);
    }
    
    /**
     * 
     * @param logList 
     */
    private void setTableData(List<Goals> logList) {
        goals = FXCollections.observableArrayList();
        
        logList.forEach(s -> {
            goals.add(s);   
        });
        goal.setItems(goals);
        goal.refresh();
    }
    private List<Goals> readByUserEmail(String email){
        
        Query query = manager.createNamedQuery("Goals.findAll");
        List<Goals> otherGoal = query.getResultList();
        return otherGoal;
    }
    
    private Goals goalQuery(int id) {
        Query query = manager.createNamedQuery("Goals.findById");
        query.setParameter("id", id);
        Goals tempGoal = (Goals) query.getSingleResult();
        return tempGoal;
    }
    
    private void delete(Goals log) {
            manager.getTransaction().begin();
            manager.remove(log);
            manager.getTransaction().commit();
        try {

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
        @FXML
    void viewButton(ActionEvent event) {
        Goals selectedGoal = goal.getSelectionModel().getSelectedItem();
        
        
        if(selectedGoal != null){
            Goals info = goalQuery(selectedGoal.getId());


            try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GoalView.fxml"));
               Parent createGoalView = loader.load();

               Scene createGoalScene = new Scene(createGoalView);

               GoalViewController controller = loader.getController();
               controller.goalDateField.setEditable(false);
               controller.goalContentField.setEditable(false);
               controller.saveGoalButton.setVisible(false);
               controller.initData(info);

               Scene currentScene = ((Node) event.getSource()).getScene();
               controller.setPreviousScene(currentScene);

               Stage stage = (Stage) currentScene.getWindow();
               stage.setScene(createGoalScene);
               stage.show();
           } catch (Exception ex) {
               System.out.println(ex.getMessage());
           }

       } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Please Select a Goal");
            alert.setContentText(null);
            alert.showAndWait();
        }


    }
    
   
    @FXML
    public void initialize() {
        manager = (EntityManager) Persistence.createEntityManagerFactory("CalorieTrackerPU").createEntityManager();
       
        refreshTable();

        goalDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        goalContents.setCellValueFactory(new PropertyValueFactory<>("contents"));
        goal.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        
        Query query = manager.createNamedQuery("UserModel.findAll");
        UserModel user = (UserModel) query.getSingleResult();
        name.setText(user.getFirstName() + " " + user.getLastName());
        email.setText(user.getEmail());
        height.setText(user.getHeight());
        weight.setText(user.getWeight() + "");
        age.setText(user.getAge() + "");

         
        assert quitButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'UserView.fxml'.";
        assert goal != null : "fx:id=\"goal\" was not injected: check your FXML file 'UserView.fxml'.";
        assert goalDate != null : "fx:id=\"goalDate\" was not injected: check your FXML file 'UserView.fxml'.";
        assert goalContents != null : "fx:id=\"goalContents\" was not injected: check your FXML file 'UserView.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'UserView.fxml'.";
        assert tableEditButton != null : "fx:id=\"tableEditButton\" was not injected: check your FXML file 'UserView.fxml'.";
        assert tableDeleteButton != null : "fx:id=\"tableDeleteButton\" was not injected: check your FXML file 'UserView.fxml'.";
        assert viewButton != null : "fx:id=\"viewButton\" was not injected: check your FXML file 'UserView.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'UserView.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'UserView.fxml'.";
        assert height != null : "fx:id=\"height\" was not injected: check your FXML file 'UserView.fxml'.";
        assert weight != null : "fx:id=\"weight\" was not injected: check your FXML file 'UserView.fxml'.";
        assert age != null : "fx:id=\"age\" was not injected: check your FXML file 'UserView.fxml'.";

    }
}
