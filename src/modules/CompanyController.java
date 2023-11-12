/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import placementfx.DbHandler;
import placementfx.MainController;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class CompanyController implements Initializable {

    @FXML
    private MaterialDesignIconView iconClose;
    @FXML
    private JFXButton btnViewTrainingCompanies;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtContact;
    @FXML
    private JFXTextField txtWebsite;
    @FXML
    private JFXButton btnSaveTrainingCompany;
    private Connection conn;
    private DbHandler handler;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = new DbHandler();

    }

    @FXML
    private void closeStage(MouseEvent event) {
        iconClose.getScene().getWindow().hide();
    }

    @FXML
    private void viewTraingCompanies(ActionEvent event) throws IOException {
        //Dim stage
        Region veil = new Region();
        veil.setPrefSize(1100, 650);
        veil.setStyle("-fx-background-color:rgba(0,0,0,0.3)");
        btnSaveTrainingCompany.getScene().getWindow().hide();
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/tables/HiringsTable.fxml"));
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (MainController.mainRootPane != null) {
                    MainController.mainRootPane.getChildren().add(veil);
                }
            } else if (MainController.mainRootPane.getChildren().contains(veil)) {
                MainController.mainRootPane.getChildren().remove(veil);
            }

        });

        newStage.show();
    }

    @FXML
    private void saveTraining(ActionEvent event) throws SQLException {
        String query = "INSERT INTO  hiring_companies(`name`,address,contact,website) "
                + "VALUES (?,?,?,?)";
        conn = handler.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, txtName.getText().toUpperCase());
        ps.setString(2, txtAddress.getText());
        ps.setString(3, txtContact.getText());
        ps.setString(4, txtWebsite.getText().toLowerCase());
        if (ps.executeUpdate() == 1) {
            System.out.println("Saved successfully");
            txtAddress.setText("");
            txtContact.setText("");
            txtName.setText("");
            txtWebsite.setText("");
        }
    }

}
