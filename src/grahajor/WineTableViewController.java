/*
    WineTableViewController.java
    Author: H.D
    Date: n / a

    Description
    A controller class for FXMLWineTableView.fxml file

    note: i added this header comment because it was missing for some reason
            - Jordan Graham
*/
package grahajor;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Wine;

public class WineTableViewController implements Initializable {

    @FXML private TableView<Wine> tblWines;
    @FXML private TableColumn<Wine, Integer> idCol;
    @FXML private TableColumn<Wine, String> estCol;
    @FXML private TableColumn<Wine, String> grapeCol;
    @FXML private TableColumn<Wine, Integer> yearCol;
    @FXML private TableColumn<Wine, Integer> qtyCol;
    @FXML private TableColumn<Wine, Double> priceCol;
    
    private ObservableList<Wine> olWines;
    
    // TODO Part: 1 - to be repeated in MainController as well
    // Declare as constants the length of the String fields in characters and the 
    // length of the record in bytes
    private final byte STRING_LENGTH = 15; // the number of Chars each string is
    private final byte RECORD_LENGTH = 80; // the total number of bytes/record
    //END of Part 1
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("wineID"));
        estCol.setCellValueFactory(new PropertyValueFactory<>("estate"));
        grapeCol.setCellValueFactory(new PropertyValueFactory<>("grape"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        //load values from file here
        loadWines();
    }

    /**
     * Method for loading the content of the file on to the TableView
     */
    private void loadWines() {
        olWines = FXCollections.observableArrayList();
        /**
         * TODO: Part 3 - Load the records on to the TableView
         * 1. Create a RandomAccessFile object in read only mode. It will
         * create/open a file located at "src/res/wines.dat"
         * 2. Position the pointer at the beginning of the file and read each
         * value in each record considering the order and type of data in the file
         * (int, String(15 characters), String(15 characters), int, int, double.
         * 3. Use readString() method (Part 2) to read the strings of the required
         * length.
         * 4. Create a wine object using the multi-parameter constructor. You can
         * trim the String values to remove the spaced added to the end when it 
         * was written.
         * 5. Set the wineID since it was not set by the constructor
         * 6. Add the wine object on to the ObservableLiist. Remember TableViews
         * are just like ListViews we covered during our course.
         * 7. Add the ObservableList to the TableView so it can be displayed.
         */
        try {
            // creates a random acces file to read from
            RandomAccessFile file = new RandomAccessFile("src/res/wines.dat",
                    "r");
            
            // sets the pointer to the beginning of the file
            file.seek(0);
            // the number of records is the file length divided by the length
            // of the record
            long num = file.length() / RECORD_LENGTH;
            
            for (int i = 0; i < num; i++) {
                // loops through each record in the file, reading the 
                // info of each one
                int id = file.readInt();
                // have to pass STRING_LENGTH / 2 because it's an int parameter
                // field and 1 int = 2 bytes
                String estate = readString(file, STRING_LENGTH);
                String grape = readString(file, STRING_LENGTH);
                int year = file.readInt();
                int quantity = file.readInt();
                double price = file.readDouble();
                
                // creates a WIne object based on the collected data
                Wine wine = new Wine(estate, grape, year, quantity, price);
                
                // sets the wine's id to the id collected from the record
                wine.setWineID(id);
                
                // adds the wine object to the observable list
                olWines.add(wine);
            }
            
            // displays the observable list
            tblWines.setItems(olWines);
            
        } catch (Exception e) {
        }
        //END of Part 3
    }

    /**
     * Method for reading a String of a length size using a RandomAccessFile 
     * object that is passes as a parameter. It reads the string one character 
     * at a time and concatinates them into a string that is returned back
     * @param raf RandomAccessFile object
     * @param size the length od the string that needs to be read
     * @return  the String that was read from the file
     * @throws IOException throws back the IOExceptin thrown by readChar() method
     */
    private String readString(RandomAccessFile raf, int size) throws IOException {
        //TODO: Part 2:
        //Read the record one character at a time, concatinate them and 
        // return back as String
        
        // inititializes the String to be returned
        String word = "";
        while (word.length() < size) {
            // concatenates each character to the string
            word += String.valueOf(raf.readChar());
        }
        // returns the resulting String
        return word; // to be replaced by the actual return
        //END of Part 2
    }

    /**
     * Accessor method for getting the item (row) that is selected in the 
     * TableView and return the wine object that correspond to that table row.
     * This method can be used in other controller to find out which row/wine
     * was selected
     * @return the wine object represented by the tableView selection
     */
    public Wine getSelectedWine(){
        Wine w = tblWines.getSelectionModel().getSelectedItem();
        return w;
    }

    /**
     * Accessor for getting the observable list that is used by the TableView.
     * This method can be used in other controllers
     * @return the ObservableList with Wine objects used in the TableView
     */
    public ObservableList<Wine> getObservableList() {
        return olWines;
    }
    
    /**
     * Accessor for getting the TableView from other classes (controllers)
     * @return the TableView object used in this controller
     */
    public TableView<Wine> getTableView(){
        return tblWines;
    }
}
