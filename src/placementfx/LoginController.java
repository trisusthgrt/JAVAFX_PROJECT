/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package placementfx;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class LoginController implements Initializable {

    @FXML
    private JFXButton btnLogin;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void doLogin(ActionEvent event) throws IOException {
        btnLogin.getScene().getWindow().hide();
        Parent root=FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage mainStage=new Stage();
        Scene scene=new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }
    
}
