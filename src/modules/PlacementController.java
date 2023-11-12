/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class PlacementController implements Initializable {

    @FXML
    private MaterialDesignIconView iconClose;
    @FXML
    private JFXButton btnSavePlacement;
    @FXML
    private JFXButton btnViewScheduled;
    @FXML
    private JFXTextField txtRegNo;
    @FXML
    private JFXTextField txtStudentNames;
    @FXML
    private JFXTextField txtSession;
    @FXML
    private JFXTextField txtCourse;
    @FXML
    private JFXTextField txtDepartment;
    @FXML
    private JFXTextField txtCompany;
    @FXML
    private JFXTextField txtPackage;
    @FXML
    private JFXDatePicker txtDate;
    @FXML
    private JFXButton btnSearch;
    private DbHandler handler;
    private Connection conn;

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
        btnSavePlacement.getScene().getWindow().hide();
    }

    @FXML
    private void savePlacement(ActionEvent event) throws SQLException {
        String insert = "INSERT INTO placements(reg_no,company_name,package,`date`) VALUES (?,?,?,?)";
        conn = handler.getConnection();
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setString(1, txtRegNo.getText());
        ps.setString(2, txtCompany.getText());
        ps.setString(3, txtPackage.getText());
        ps.setDate(4, java.sql.Date.valueOf(txtDate.getValue()));
        int successs = ps.executeUpdate();
        if (successs == 1) {
            System.out.println("Saved successfuly");
            // clear fields
            txtCompany.setText("");
            txtCourse.setText("");
            txtDate.setValue(null);
            txtDepartment.setText("");
            txtPackage.setText("");
            txtRegNo.setText("");
            txtSession.setText("");
            txtStudentNames.setText("");

        }

    }

    @FXML
    private void viewPlacements(ActionEvent event) throws IOException {
         //Dim stage
        Region veil = new Region();
        veil.setPrefSize(1100, 650);
        veil.setStyle("-fx-background-color:rgba(0,0,0,0.3)");
        btnSavePlacement.getScene().getWindow().hide();
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/tables/PLacementsTable.fxml"));
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
    private void searchStudent(ActionEvent event) throws SQLException {
        boolean exists = false;
        String search = "SELECT `names`,`session`,course FROM students WHERE  reg_no=?";
        conn = handler.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(search)) {
            ps.setString(1, txtRegNo.getText());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    exists = true;
                    txtStudentNames.setText(rs.getString(1));
                    txtCourse.setText(rs.getString(3));
                    txtSession.setText(rs.getString(2));
                    txtDepartment.setText("N/A");
                }

                if (!exists) {
                    txtStudentNames.setText("");
                    txtSession.setText("");
                    txtCourse.setText("");
                    txtDepartment.setText("");
                }

                conn.close();
            }
        }
    }

}
