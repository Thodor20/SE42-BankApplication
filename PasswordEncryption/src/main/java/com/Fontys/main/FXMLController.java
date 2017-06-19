package com.Fontys.main;

import com.Fontys.util.FileControl;
import java.io.File;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            crypter = new Crypter();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void encryptMessage_Click() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            EncryptionData data = crypter.encryptData(pfPassword.getText(), tfMessage.getText());
            FileControl.writeData("C:\\Users\\thomv\\Desktop\\file.txt", data);
        }

        tfMessage.setText("");
        pfPassword.setText("");
    }

    @FXML
    private void decryptMessage_Click() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            EncryptionData data = FileControl.readData("C:\\Users\\thomv\\Desktop\\file.txt");
            String message = crypter.decryptData(data);
            if (message != null) {
                laSecret.setText(message);
            } else {
                System.out.println("Failed descrypting message");
            }
        }
    }

}
