package com.sander.encryptpassword;

import java.io.File;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLController implements Initializable {

    private Crypter crypter;

    @FXML
    private Label laSecret;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfMessage;
    
    @FXML
    private Button btnEncrypt;
    
    @FXML
    private Button btnDecrypt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            crypter = new Crypter();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        btnEncrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                encryptMessage_Click();
            }
        });
        
        btnDecrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                decryptMessage_Click();
            }
        });
    }

    private void encryptMessage_Click() {
        if (!passwordCheck())
            return;
        
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            MessageData data = crypter.encryptData(tfMessage.getText().toCharArray(), pfPassword.getText());
            FileControl.writeData(file.getPath(), data);
        }

        tfMessage.setText(null);
        pfPassword.setText(null);
    }

    @FXML
    private void decryptMessage_Click() {
        if (!passwordCheck())
            return;
        
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            MessageData data = FileControl.readData(file);
            String message = crypter.decryptData(pfPassword.getText().toCharArray(), data);
            if (message != null) {
                laSecret.setText(message);
            } else {
                System.out.println("Failed descrypting message");
            }
        }
    }

    private boolean passwordCheck() {
        if (pfPassword.getText() == null || pfPassword.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Missing password!");
            alert.setContentText("Please provide a valid password.");

            alert.showAndWait();

            return false;
        }

        return true;
    }

}
