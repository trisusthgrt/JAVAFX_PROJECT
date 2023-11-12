/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class StudentController implements Initializable {

    @FXML
    private JFXButton btnSaveStudent;
    @FXML
    private JFXButton btnViewAll;
    @FXML
    private MaterialDesignIconView closeIcon;
    @FXML
    private JFXComboBox<String> comboCourse;
    @FXML
    private JFXTextField txtxSession;
    @FXML
    private JFXTextField txtContact;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtMother;
    @FXML
    private JFXTextField txtFather;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXDatePicker dtDob;
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
        setCoursesToCombo();
    }

    @FXML
    private void closeStage(MouseEvent event) {
        btnViewAll.getScene().getWindow().hide();
    }

    private void setCoursesToCombo() {
        try {
            String query = "SELECT course_name FROM courses";
            conn = handler.getConnection();
            try (Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {
                    comboCourse.getItems().addAll(rs.getString(1));
                }

            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void saveStudent(ActionEvent event) throws SQLException {
        String query = "INSERT INTO students(`names`,dob,`session`,"
                + "course,father_name,mother_name,contact,address)"
                + " VALUES (?,?,?,?,?,?,?,?)";
        conn = handler.getConnection();
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, txtName.getText().toUpperCase());
        pst.setDate(2, java.sql.Date.valueOf(dtDob.getValue()));
        pst.setString(3, txtxSession.getText());
        pst.setString(4, comboCourse.getSelectionModel().getSelectedItem());
        pst.setString(5, txtFather.getText());
        pst.setString(6, txtMother.getText());
        pst.setString(7, txtContact.getText());
        pst.setString(8, txtAddress.getText());

        if (pst.executeUpdate() == 1) {
            System.out.println("Saved successfully");
            txtAddress.setText("");
            txtContact.setText("");
            txtFather.setText("");
            txtMother.setText("");
            txtName.setText("");
            txtxSession.setText("");
            dtDob.setValue(null);
        }

    }

    @FXML
    private void viewAllStudents(ActionEvent event) throws IOException {
        //Dim stage
        Region veil = new Region();
        veil.setPrefSize(1100, 650);
        veil.setStyle("-fx-background-color:rgba(0,0,0,0.3)");
        btnSaveStudent.getScene().getWindow().hide();
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/tables/StudentTable.fxml"));
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
}
