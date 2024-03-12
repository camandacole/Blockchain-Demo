package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/** This functionality of this class is to search and display block data and the transaction data associated with the block */
public class TableUiController implements Initializable {
    ObjectInputStream read;
    @FXML
    TextArea searchBar;
    @FXML
    Button searchButton;
    // block
    @FXML
    Text blockHashData;
    @FXML
    Text previousHashData;
    @FXML
    Text transactionData;
    @FXML
    Text timestampData;
    // transaction
    @FXML
    Text transactId;
    @FXML
    Text senderInfo;
    @FXML
    Text receiverInfo;
    @FXML
    Text sigInfo;
    @FXML
    Text amountInfo;
    @FXML
    Text transactDate;
    @FXML
    VBox transactBox;
    @FXML
    Label messageDisplay;
    @FXML
    TableView<Block> tableView;
    @FXML
    TableColumn<Block, String> blockHashColumn;
    @FXML
    TableColumn<Block, Long> ageColumn;

    /** The initialize() function displays the block on the table view. This function is automatically called
     * when this fxml file for this controller is loaded */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        blockHashColumn.setCellValueFactory(new PropertyValueFactory<>("hash"));
        blockHashColumn.setEditable(true);
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        tableView.setItems(getBlocks());
    }

    /** This function loads block from blockchain.dat file by call the function loadBlockDataToArray()
     * it loads the data into an observable list which the tableview would use to display data in itself **/
    public ObservableList<Block> getBlocks(){
        ObservableList<Block> obBlocks = FXCollections.observableArrayList();
        ArrayList<Block> blocks = LoadData.loadBlockDataToArray();
        for(Block block : blocks) {
            obBlocks.add(block);
        }

        return obBlocks;
    }

    /** This function searches for block data to be displayed after user input the block hash in the Ui search Bar
     * It loads data to an Avl tree (MyAvlTreeVersion). I created a new class called "MyAVLVersion" which extends AVL tree
     * The purpose of this tree is to modify the search() function that returns a boolean to return the block data instead
     * we then create a dummy block and set its hash to the hash inputted by the user
     * this dummy block will then be used for searching the the Avl tree for blocks that matches the block passed in the search function
     * if block is found, the modified search function would return the block data which would be displayed to user */
    public void searchBlockInfo(){
            String hash = searchBar.getText(); // get hash inputted by the user
            Block dummyBlock = new Block(); // create dummy block used for searching
            dummyBlock.setHash(hash); // set hash of dummy block to the one searched by user
            MyAvlTreeVersion<Block> blocks = LoadData.loadBlockDataToTree(); // load data and return an avl tree of blocks
               Block block = blocks.mySearch(dummyBlock); // pass dummy block to mySearch() to return the blocks in the tree that matches it
                if(block != null){ // if not null
                    transactBox.setVisible(false);// set box to false
                    blockHashData.setVisible(true); // display block hash
                    previousHashData.setVisible(true); // display block previous hash
                    transactionData.setVisible(true); // display transaction id
                    timestampData.setVisible(true); // display timestamp

                    blockHashData.setText(block.getHash()); // set block hash
                    previousHashData.setText(block.getPreviousHash()); // set previous hash
                    transactionData.setText(block.getData().getTransactionId()); // set transaction id
                    timestampData.setText(block.getTimeStamp().toString()); // set timestamp
                    messageDisplay.setVisible(true);
                    messageDisplay.setText("A search was successfully made.");
                }else{
                    messageDisplay.setVisible(true);
                    messageDisplay.setText("Could not find block associated with this input.");
                    System.out.println("Could not find block");
                }

    }

    /** This function searches for transaction data to be displayed after user clicks transaction id in the block data displayed
     * It loads data to an Avl tree (MyAvlTreeVersion). I create a new class called "MyAVLVersion" which extends AVL tree
     * The purpose of this tree is to modify the search() function that returns a boolean to return the block data instead
     * we then create a dummy transaction object and set its transaction id to the id clicked by the user
     * this dummy transaction will then be used for searching the Avl tree for transactions that matches the transaction passed
     * in the search function.
     * if transaction is found, the modified search function(MyAvlTreeVersion) would return the transaction data
     * which would be displayed to user */
    public void displayTransactionInfo(){
        String text = transactionData.getText(); // get transaction id clicked
        Transaction dummyTransaction = new Transaction(); // dummy transaction used for searching
        dummyTransaction.setTransactionId(text); // set dummy object id
        MyAvlTreeVersion<Transaction> transactions =  LoadData.loadTransactionDataToTree(); // load and return Avl tree of transactions
        Transaction transact = transactions.mySearch(dummyTransaction); // search for transaction that matches the dummy block.
            if(transact != null) { // if not null
                transactBox.setVisible(true); // display transaction block
                transactId.setText(transact.getTransactionId()); // set transaction id
                senderInfo.setText(StringUtil.getStringFromKey(transact.getSender())); // send sender public key
                receiverInfo.setText(StringUtil.getStringFromKey(transact.getReceiver())); // set receiver private key
                sigInfo.setText(StringUtil.getStringSignature(transact.getSignature())); // set signature
                amountInfo.setText(Double.toString(transact.getTransactionAmount())); // set transaction amount
                transactDate.setText(transact.getTransactionDate().toString()); // set transaction date
            }else{
                System.out.println("Could not find Transaction");
            }
        }
    }

