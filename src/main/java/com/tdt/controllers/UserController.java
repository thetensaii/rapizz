package com.tdt.controllers;

import com.tdt.core.Error;
import com.tdt.entity.User;
import com.tdt.model.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserController {
    @FXML
    private TextField t_lastname;

    @FXML
    private TextField t_firstname;

    @FXML
    private TextField t_amount;

    @FXML
    private TextField p_password;

    @FXML
    private Button b_save;

    private int id;
    private User user;

    @FXML
    public void initialize() {
        System.out.println(id);
        user = new User();
    }

    @FXML
    public void buttonSaveClicked(ActionEvent e) {
        if (t_lastname.getText().length() < 3) {
            Error.showError("Champs invalide : ", "Le nom doit être d'au moins 3 caractères.");
            return;
        }

        if (t_firstname.getText().length() < 3) {
            Error.showError("Champs invalide : ", "Le prénom doit être d'au moins 3 caractères.");
            return;
        }
        float amount;
        try {
            amount = Float.parseFloat(t_amount.getText());
        } catch (Exception ex) {
            Error.showError("Ajout invalide ", "Veuillez donner un solde valide");
            return;
        }

        if (p_password.getText().length() == 0 && id == 0) {
            Error.showError("Champs invalide : ", "Veuillez saisir un mot de passe.");
            return;
        } else if (p_password.getText().length() != 0 && p_password.getText().length() < 3) {
            Error.showError("Champs invalide : ", "Le mot de passe doit être d'au moins 3 caractères.");
            return;
        }

        user.setLastname(t_lastname.getText());
        user.setFirstname(t_firstname.getText());
        user.setAmount(amount);

        if (p_password.getText().length() != 0) user.setPassword(p_password.getText());

        System.out.println(user.toString());
        if (e.getSource() == b_save) {
            UserManager.save(user);
            Stage stage = (Stage) b_save.getScene().getWindow();
            stage.close();
        }


    }

    public void setUserId(int id) {
        this.id = id;
        if (id != 0) {
            user = UserManager.getById(id);
            t_lastname.setText(user.getLastname());
            t_firstname.setText(user.getFirstname());
            t_amount.setText(user.getAmount() + "");
        }

    }

}
