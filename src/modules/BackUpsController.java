/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class BackUpsController implements Initializable {

    @FXML
    private MaterialDesignIconView iconClose;
    @FXML
    private JFXButton btnBackUp;
    @FXML
    private JFXButton btnRestore;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void closeStage(MouseEvent event) {
        btnRestore.getScene().getWindow().hide();
    }

    @FXML
    private void backUpDB(ActionEvent event) {
    }

    @FXML
    private void restoreDB(ActionEvent event) {
    }
    
}
