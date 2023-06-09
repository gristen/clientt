package com.example.clieeeent;

import com.example.clieeeent.controller.*;
import com.example.clieeeent.entity.UsersEntity;
import com.example.clieeeent.entity.flightsEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        appController controller = fxmlLoader.getController();
        stage.show();
    }

    public static boolean showPersonEditDialog(UsersEntity user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("editUser.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            editUserController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(user); // Передача объекта пользователя в контроллер

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean showFlightsEditDialog(flightsEntity fli) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("editFlights.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editads flights");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            editFlightsController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(fli);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean showUserAddDialog(UsersEntity userObj) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("addUser.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Студент");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            addUserController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRole(userObj);


            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean showFlightsAddDialog(flightsEntity fliObj) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("addFlights.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Студент");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            addFlightsController controller = loader.getController();
            controller.setDialogStage(dialogStage);
          //  controller.setSeats(fliObj);


            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
        launch();
    }
}