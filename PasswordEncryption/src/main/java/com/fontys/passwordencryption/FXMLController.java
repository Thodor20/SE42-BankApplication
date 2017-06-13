package com.fontys.passwordencryption;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
    
    private final Crypter crypter = new Crypter();
    private final FileControl fileControl = new FileControl();
    
    @FXML
    private Label laSecret;
    
    @FXML
    private PasswordField pfPassword;
    
    @FXML
    private TextField tfMessage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    private void encryptMessage_Click() {
        encryptMessage();
    }
    
    @FXML
    private void decryptMessage_Click() {
        laSecret.setText(decryptMessage());
    }
    
    private void encryptMessage() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());
        
        if (file != null) {
            byte[] encryptedMessage = crypter.encryptData(tfMessage.getText(),pfPassword.getText().toCharArray());
            fileControl.writeData(file.getPath(), encryptedMessage);
        }
        tfMessage.setText("");
        pfPassword.setText("");
        
    }
    
    private String decryptMessage(){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showOpenDialog(new Stage());
        
        if (file != null) {
            
           byte[] data = fileControl.readData(file);
           
           byte[] decrypted = crypter.decryptData(data, pfPassword.getText().toCharArray());
           pfPassword.setText("");
           
           return new String(decrypted);
           
        }
        
        return null;
    }
}
