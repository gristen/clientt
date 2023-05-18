package com.example.clieeeent.controller;

import com.example.clieeeent.entity.UsersEntity;
import com.example.clieeeent.entity.flightsEntity;
import com.example.clieeeent.entity.roleEntity;
import com.example.clieeeent.utils.HTTPUtils;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class editFlightsController {
    public static String api = "http://localhost:2825/api/";
    static HTTPUtils http = new HTTPUtils();
    static Gson gson = new Gson();

    @FXML
    private TextField path;
    @FXML
    private TextField price;
    private Stage dialogStage;
    private flightsEntity fli;

    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setUser(flightsEntity fli) throws IOException {
        this.fli = fli;
        path.setText(fli.getPath());
        price.setText(fli.getPrice());
        // Установите значение id в поле user
        fli.setId_flights(fli.getId_flights());
    }
    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void addFlight() throws Exception {
        String pathValue = path.getText();
        String priceValue = price.getText();

        // Проверка наличия данных в обязательных полях
        if (pathValue.isEmpty() || priceValue.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Пустые поля");
            alert.setHeaderText("Пожалуйста, заполните все обязательные поля");
            alert.showAndWait();
            return; // Останавливаем выполнение метода, если есть пустые поля
        }

        // Проверка, что поле price является числом и не меньше 0
        double priceNumeric;
        try {
            priceNumeric = Double.parseDouble(priceValue);
            if (priceNumeric < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Некорректное значение");
            alert.setHeaderText("Поле 'Цена' должно быть положительным числом");
            alert.showAndWait();
            return; // Останавливаем выполнение метода, если некорректное значение поля price
        }

        // Дополнительные проверки, если необходимо
        // ...

        // Установка значений полей рейса
        fli.setPath(pathValue);
        fli.setPrice(priceValue);

        // Преобразование объекта рейса в JSON
        String flightJson = gson.toJson(fli);

        // Отправка запроса на сервер для обновления данных рейса
        String response = http.post(api + "flights/update", flightJson);

        // Обработка ответа сервера и выполнение необходимых действий

        okClicked = true;
        dialogStage.close();
        appController.getDataFlights();
    }
}
