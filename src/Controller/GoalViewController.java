/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Goals;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class GoalViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public TextField goalDateField;

    @FXML
    private Button cancelGoalButton;

    @FXML
    public Button saveGoalButton;

    @FXML
    public TextArea goalContentField;
    
    Scene previousScene;
    
    EntityManager manager;
    
    Goals goal;
    
    Integer goalId = 1;
    

    @FXML
    void cancelGoal(ActionEvent event) throws IOException {
        manager.getTransaction().begin();
        if (goal != null){
        goal.setDate(goal.getDate());
        goal.setContents(goal.getContents());
        manager.getTransaction().commit();
        }
        
        refreshProfileView(event);
    }

    @FXML
    void saveGoalButton(ActionEvent event) throws IOException {
          try {
            Goals existingGoal = manager.find(Goals.class, goal.getId());

            if (existingGoal != null) {
                manager.getTransaction().begin();


                existingGoal.setDate(goalDateField.getText());
                existingGoal.setContents(goalContentField.getText());

                manager.getTransaction().commit();
              }
           
        } catch (Exception ex) {
            
            Goals newGoal = new Goals();
            Query countEntriesQuery = manager.createNamedQuery("Goals.countEntries");
            goalId = ((Long) (countEntriesQuery.getSingleResult())).intValue();
            if (goalId == 0){ // since an primary key can not be 0
                goalId++;
            }

            Query findByIdQuery = manager.createNamedQuery("Goals.findById");
            findByIdQuery.setParameter("id", goalId);

            try {
                Goals doesExist = (Goals) findByIdQuery.getSingleResult();

                while (doesExist != null) {
                    goalId++;

                    findByIdQuery.setParameter("id", goalId);
                    doesExist = (Goals) findByIdQuery.getSingleResult();
                }
            } catch (NoResultException e) {
                System.out.println(e.getMessage());
            }
            
                newGoal.setId(goalId);
                newGoal.setDate(goalDateField.getText());
                newGoal.setContents(goalContentField.getText());
                manager.getTransaction().begin();

            if (newGoal.getId() != null) {

                manager.persist(newGoal);

                manager.getTransaction().commit();
            }
            
        } 
          refreshProfileView(event);
    }
    
    private void refreshProfileView(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserView.fxml"));

        Parent profileView = loader.load();

        Scene newScene = new Scene(profileView);
        UserViewController userControllerView = loader.getController();
        userControllerView.initialize();

        Stage stage = new Stage();
        stage.setScene(newScene);
               
        stage.show(); 

        Stage logView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        logView.close();
    }

    void setPreviousScene(Scene currentScene) {
        previousScene = currentScene; 
    }

    void initData(Goals selectedGoal) {
        goal = selectedGoal;
        goalDateField.setText(selectedGoal.getDate());
        goalContentField.setText(selectedGoal.getContents());
    }
    
    @FXML
    void initialize() {
        manager = (EntityManager) Persistence.createEntityManagerFactory("CalorieTrackerPU").createEntityManager();

        assert goalDateField != null : "fx:id=\"goalDateField\" was not injected: check your FXML file 'GoalView.fxml'.";
        assert cancelGoalButton != null : "fx:id=\"cancelGoalButton\" was not injected: check your FXML file 'GoalView.fxml'.";
        assert saveGoalButton != null : "fx:id=\"saveGoalButton\" was not injected: check your FXML file 'GoalView.fxml'.";
        assert goalContentField != null : "fx:id=\"goalContentField\" was not injected: check your FXML file 'GoalView.fxml'.";

    }
}

