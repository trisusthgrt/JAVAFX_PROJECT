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
public class CourseController implements Initializable {

    @FXML
    private JFXButton btnShowCoursesList;
    @FXML
    private JFXTextField txtCourseName;
    @FXML
    private JFXTextField txtxCourseBranch;
    @FXML
    private JFXButton btnSave;
    private DbHandler handler;
    private Connection conn;
    @FXML
    private MaterialDesignIconView closeIcon;

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
    private void showCoursesList(ActionEvent event) throws IOException {
        //Dim stage
        Region veil = new Region();
        veil.setPrefSize(1100, 650);
        veil.setStyle("-fx-background-color:rgba(0,0,0,0.3)");
        btnSave.getScene().getWindow().hide();
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/tables/CoursesTable.fxml"));
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
    private void saveCourse(ActionEvent event) throws SQLException {
        conn = handler.getConnection();
        String saveQuery = "INSERT INTO courses(course_name,department) VALUES (?,?)";
        if (txtCourseName.getText().isEmpty() || txtxCourseBranch.getText().isEmpty()) {
            return;
        }
        try (PreparedStatement ps = conn.prepareStatement(saveQuery)) {
            ps.setString(1, txtCourseName.getText().toUpperCase());
            ps.setString(2, txtxCourseBranch.getText().toUpperCase());
            int success = ps.executeUpdate();
            if (success == 1) {
                txtCourseName.setText("");
                txtxCourseBranch.setText("");
            }
        }
        conn.close();

    }

    @FXML
    private void closeStage(MouseEvent event) {
        btnSave.getScene().getWindow().hide();
    }

}
