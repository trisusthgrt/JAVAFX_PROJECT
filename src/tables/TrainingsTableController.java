/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import placementfx.DbHandler;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class TrainingsTableController implements Initializable {

    @FXML
    private TableView tableTrainings;
    private Connection conn;
    private DbHandler handler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = new DbHandler();
        populateTable();
    }

    private void populateTable() {
        try {
            conn = handler.getConnection();
            String query = "SELECT students.reg_no as \"REG NO\",`names` as \"STUDENT NAMES\","
                    + "company,duration as \"DURATION ( Months)\" FROM  trainings"
                    + " INNER JOIN students ON students.reg_no=trainings.reg_no";
            ResultSet rst = conn.createStatement().executeQuery(query);
            ObservableList<ObservableList> data = FXCollections.observableArrayList();
            tableTrainings.getColumns().clear();
            int cols = rst.getMetaData().getColumnCount();

            for (int i = 0; i < cols; i++) {
                final int j = i;
                TableColumn col = new TableColumn(rst.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());

                    }
                });

                col.setPrefWidth(130);
                tableTrainings.getColumns().addAll(col);
            }
            while (rst.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int k = 1; k <= rst.getMetaData().getColumnCount(); k++) {
                    row.add(rst.getString(k));
                }
                data.add(row);
            }
            tableTrainings.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(TrainingsTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
