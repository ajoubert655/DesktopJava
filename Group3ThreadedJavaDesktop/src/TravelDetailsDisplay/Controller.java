/*Author: Andries Joubert
Threaded Project Term 3
Project Workshop 6(Java)
Group: Group 3
 */

package TravelDetailsDisplay;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import javax.swing.*;
import java.lang.Object;
import static javax.swing.JOptionPane.showMessageDialog;




public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane TravelFormPane;

    @FXML
    private TextField txtCustFirstName;

    @FXML
    private TextField txtCustLastName;

    @FXML
    private TextField txtCustCountry;

    @FXML
    private TextField txtCustCity;

    @FXML
    private TextField txtBookingId;

    @FXML
    private TextField txtBookingDate;

    @FXML
    private TextField txtTripTypeId;

    @FXML
    private TextField txtPackageId;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox<CustomerBooking> cmbCustomerId;

    @FXML
    private Button btnEdit;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private Button btnAdd;

    @FXML
    private Label lblcmbId;

    @FXML
    void initialize() {
        assert TravelFormPane != null : "fx:id=\"TravelFormPane\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert txtCustFirstName != null : "fx:id=\"txtCustFirstName\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert txtCustLastName != null : "fx:id=\"txtCustLastName\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert txtCustCountry != null : "fx:id=\"txtCustCountry\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert txtCustCity != null : "fx:id=\"txtCustCity\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert txtBookingId != null : "fx:id=\"txtBookingId\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert txtBookingDate != null : "fx:id=\"txtBookingDate\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert txtTripTypeId != null : "fx:id=\"txtTripTypeId\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert txtPackageId != null : "fx:id=\"txtPackageId\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert txtCustomerId != null : "fx:id=\"txtCustomerId\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";
        assert lblcmbId != null : "fx:id=\"lblcmbId\" was not injected: check your FXML file 'TravelDetailsDisplay.fxml'.";


        //Booking and customer ID would always be disabled and the rest of the code would only be editable depending on whether the add or edit button was pressed.
        txtCustomerId.setEditable(false);
        txtCustFirstName.setEditable(false);
        txtCustLastName.setEditable(false);
        txtCustCity.setEditable(false);
        txtCustCountry.setEditable(false);
        txtBookingId.setEditable(false);
        txtBookingId.setDisable(true);
        txtCustomerId.setDisable(true);
        txtBookingDate.setEditable(false);
        txtTripTypeId.setEditable(false);
        txtPackageId.setEditable(false);

        //Code for the color gradient on the anchor pane of the form.
        Stop[] stops = new Stop[] {new Stop(0, Color.DEEPSKYBLUE), new Stop(1, Color.WHITE)};
        LinearGradient lgcolor = new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE, stops);
        BackgroundFill bgfill = new BackgroundFill(lgcolor, CornerRadii.EMPTY, Insets.EMPTY);
        TravelFormPane.setBackground(new Background(bgfill));

        //A few columns from two tables (customers and bookings) were selected. The columns chosen also correspond to columns in the Packages and Trip Types tables so that the travel agents get a broad overview of the customer information.
        Connection conn = connectDB();
        ObservableList<CustomerBooking> list = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            String sql = "select customers.CustomerId, CustFirstName, CustLastName, CustCountry, CustCity, BookingId, BookingDate,TripTypeId, PackageId  FROM customers, bookings WHERE customers.CustomerId = bookings.CustomerId";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                list.add(new CustomerBooking(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getInt(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
            }
            cmbCustomerId.setItems(list);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        btnCancel.setCancelButton(true);
        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) btnCancel.getScene().getWindow(); stage.close();
            }
        });

        //When the agent presses the edit button all the fields except for customer ID, booking ID and package ID should become editable.
        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                    cmbCustomerId.setDisable(false);
                    txtCustomerId.setEditable(true);
                    txtCustFirstName.setEditable(true);
                    txtCustLastName.setEditable(true);
                    txtCustCity.setEditable(true);
                    txtCustCountry.setEditable(true);
                    txtBookingDate.setEditable(true);
                    txtTripTypeId.setEditable(true);
                    txtPackageId.setEditable(true);
                    btnSave.setDisable(false);
                    txtCustomerId.setDisable(true);
                    txtBookingId.setDisable(true);
                    cmbCustomerId.setVisible(true);
                    lblcmbId.setVisible(true);
                    txtPackageId.setDisable(true);
            }
        });

        //When an agent presses an add button, the combobox and its label is set to invisible to prevent confusion. All the rest of the fields except for customer ID and booking ID are editable.
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                txtCustFirstName.setEditable(true);
                txtCustLastName.setEditable(true);
                txtCustCity.setEditable(true);
                txtCustCountry.setEditable(true);
                txtBookingDate.setEditable(true);
                txtTripTypeId.setEditable(true);
                txtPackageId.setEditable(true);
                txtBookingId.setDisable(true);
                txtCustomerId.setDisable(true);
                cmbCustomerId.setDisable(true);
                cmbCustomerId.setVisible(false);
                lblcmbId.setVisible(false);
                txtCustomerId.clear();
                txtCustFirstName.clear();
                txtCustLastName.clear();
                txtCustCity.clear();
                txtCustCountry.clear();
                txtBookingId.clear();
                txtBookingDate.clear();
                txtTripTypeId.clear();
                txtPackageId.clear();
                txtPackageId.setDisable(false);
            }
        });

        //The combobox shows both booking ID and customer ID.
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerBooking>() {

            @Override
            public void changed(ObservableValue<? extends CustomerBooking> observableValue, CustomerBooking customerBooking, CustomerBooking t1) {
                txtCustomerId.setText(t1.getCustomerId()+ "");
                txtCustFirstName.setText(t1.getCustFirstName());
                txtCustLastName.setText(t1.getCustLastName());
                txtCustCity.setText(t1.getCustCity());
                txtCustCountry.setText(t1.getCustCountry());
                txtBookingId.setText(t1.getBookingId()+ "");
                txtBookingDate.setText(t1.getBookingDate());
                txtTripTypeId.setText(t1.getTripTypeId()+"");
                txtPackageId.setText(t1.getPackageId() +"");
                txtCustFirstName.setEditable(false);
                txtCustLastName.setEditable(false);
                txtCustCity.setEditable(false);
                txtCustCountry.setEditable(false);
                txtBookingId.setDisable(true);
                txtCustomerId.setDisable(true);
                txtBookingDate.setEditable(false);
                txtTripTypeId.setEditable(false);
                txtPackageId.setEditable(false);
                btnEdit.setDisable(false);
            }

        });

        //There is an "if" statement in the "Save" button method that determines whether to run the coding for "update" or for "insert". Since the combobox is invisible when the add button is clicked, that serves as the condition to determine whether to insert or update the data in the database.
        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (cmbCustomerId.isVisible()) {
                    Connection conn = connectDB();
                    Connection conn3 = connectDB();
                    String sql = "UPDATE `customers` SET `CustFirstName`=?,`CustLastName`=?,`CustCity`=?,`CustCountry`=? WHERE CustomerId=?";
                    String sql2 = "UPDATE `bookings` SET `CustomerId`=?,`BookingDate`=?,`TripTypeId`=? WHERE BookingId=?";
                    try {
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, txtCustFirstName.getText());
                        stmt.setString(2, txtCustLastName.getText());
                        stmt.setString(3, txtCustCity.getText());
                        stmt.setString(4, txtCustCountry.getText());
                        stmt.setInt(5, Integer.parseInt(txtCustomerId.getText()));

                        PreparedStatement stmt2 = conn3.prepareStatement(sql2);
                        stmt2.setInt(1, Integer.parseInt(txtCustomerId.getText()));
                        stmt2.setString(2, txtBookingDate.getText());
                        stmt2.setString(3, txtTripTypeId.getText());
                        stmt2.setInt(4, Integer.parseInt(txtBookingId.getText()));

                        int numRows = stmt.executeUpdate();
                        int numRows2 = stmt2.executeUpdate();

                        if (numRows == 0 && numRows2 == 0) {
                            System.out.println("failed customers and bookings");
                        } else {
                            System.out.println("updated customers and bookings");
                            showMessageDialog(null, "Record has been successfully Updated");
                        }
                        conn.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                                throwables.getMessage(),
                                "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,
                                e.getMessage(),
                                "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    Connection conn2 = connectDB();
                    String sql3 = "INSERT INTO customers (CustFirstName,CustLastName,CustCity,CustCountry)" + " values(?,?,?,?)";
                    String sql4 = "INSERT INTO bookings (BookingDate, TripTypeId, PackageId)" + "values(?,?,?)";
                    try {
                        PreparedStatement stmt3 = conn2.prepareStatement(sql3);
                        stmt3.setString(1, txtCustFirstName.getText());
                        stmt3.setString(2, txtCustLastName.getText());
                        stmt3.setString(3, txtCustCity.getText());
                        stmt3.setString(4, txtCustCountry.getText());

                        PreparedStatement stmt4 = conn2.prepareStatement(sql4);
                        stmt4.setString(1, txtBookingDate.getText());
                        stmt4.setString(2, txtTripTypeId.getText());
                        stmt4.setInt(3, Integer.parseInt(txtPackageId.getText()));

                        int numRows = stmt3.executeUpdate();
                        int numRows2 = stmt4.executeUpdate();

                        if (numRows == 0 && numRows2 == 0) {
                            System.out.println("failed customers and bookings");
                        } else {
                            System.out.println("Added customers and bookings");
                            showMessageDialog(null, "Record has been successfully Added");
                        }
                        conn2.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                                throwables.getMessage(),
                                "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null,
                                e.getMessage(),
                                "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                    }



                }
            }

        });

    }


    private Connection connectDB() {
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}

