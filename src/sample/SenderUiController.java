package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
/** This class is responsible for transaction between two wallets**/
public class SenderUiController {
    ArrayList<WalletAndItsFilePath> wallets = new ArrayList<>();
    BufferedWriter write;
    ObjectOutputStream storeObj;
    ObjectInputStream readObj;
    File file;
    @FXML
    TextArea senderPubKeyField;
    @FXML
    TextArea senderPrivKeyField;
    @FXML
    TextArea transactionAmountField;
    @FXML
    TextArea receiverPubKeyField;
    @FXML
    Label errorMessage;
    @FXML
    Label successMessage;
    @FXML
    Button blockPageButton;

    @FXML
    public void createAndStoreWallet(ActionEvent actionEvent) {
        String filePath =  "src/sample/walletFiles/wallet"+"("+System.currentTimeMillis()+")"+".txt";
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            file.createNewFile();
            write = new BufferedWriter(new FileWriter(file, true));
            // CREATE NEW WALLET
            Wallet wallet = new Wallet();
            wallet.addToWallet((Math.random()*(10000-10+1)+10)); /** generate and add random money to wallet (from $10 t0 10k) */
            write.write("Public Key: ");
            write.write(StringUtil.getStringFromKey(wallet.getPublicKey()));
            write.write("\n");
            write.write("Private Key: ");
            write.write(StringUtil.getStringFromKey(wallet.getPrivateKey()));
            write.write("\n");
            write.write("Amount Balance: ");
            write.write(Double.toString(wallet.getBalance()));
            write.write("\n");
            write.close();

            /** store wallet and its corresponding filepath. This is because we would want to update the txt file
             * after transaction has been made between wallets so we would need the corresponding filepath to update the balance
             * in the text file */
            WalletAndItsFilePath info = new WalletAndItsFilePath(wallet, filePath);
            file = new File("WalletsAndItsFilePaths.dat");
            if(file.exists()){
                AppendObjectOutputStream out = new AppendObjectOutputStream(new FileOutputStream(file, true));
                out.writeObject(info);
            }else {
                storeObj = new ObjectOutputStream(new FileOutputStream(file, true));
                storeObj.writeObject(info); /** add wallet and its file path object to a file */
            }

            System.out.println("Wallet successfully created");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void makeTransactionBetweenWallets(){ // makes transaction and store transaction object in a file
        String senderPubKey = this.senderPubKeyField.getText();
        String senderPrivKey = this.senderPrivKeyField.getText();
        double transactionAmount = Double.parseDouble(this.transactionAmountField.getText());
        String receiverPubKey = this.receiverPubKeyField.getText();

        WalletAndItsFilePath senderWallet = null;
        WalletAndItsFilePath receiverWallet = null;

        addDataToArray();  /** Load wallets and its file path data into an array list above **/

        for(int i = 0; i < wallets.size();i++){ /** find the sender wallet whose public key matches with the value from form input */
            if(senderPubKey.equals(StringUtil.getStringFromKey(wallets.get(i).wallet.getPublicKey())) &&
                    senderPrivKey.equals(StringUtil.getStringFromKey(wallets.get(i).wallet.getPrivateKey()))){
                senderWallet = wallets.get(i);
                break;
            }
        } /** loop ends here */

        for(int i = 0; i < wallets.size();i++){ /** find the receiver wallet whose public key matches with the value from form input */
            if(receiverPubKey.equals(StringUtil.getStringFromKey(wallets.get(i).wallet.getPublicKey()))){
                receiverWallet = wallets.get(i);
                break;
            }
        }/** loop ends here */
        if(senderWallet == null && receiverWallet == null) {
            errorMessage.setVisible(true);
            blockPageButton.setVisible(false);
            errorMessage.setText("No wallets matches our database");
            System.out.println("No wallets matches our database");
        }else {
            try{ /** If sender wallet has enough balance then make transaction between wallets  */
                if(senderWallet.wallet.isBalance(transactionAmount)){ // if wallet has sufficient balance, then make transaction
                    senderWallet.wallet.deductFromWallet(transactionAmount);
                    receiverWallet.wallet.addToWallet(transactionAmount);
                    Transaction transaction = new Transaction(senderWallet.wallet.getPublicKey(), receiverWallet.wallet.getPublicKey(), transactionAmount);
                    transaction.generateSignature(senderWallet.wallet.getPrivateKey());

                    /** The purpose of updating file is to update the previous balance of the wallet file before transaction */
                    updateWalletTxtFile(senderWallet); // update info in sender wallet text file
                    updateWalletTxtFile(receiverWallet); // update info in receiver wallet text file

                    file = new File("transactions.dat");
                    if(file.exists()){
                        AppendObjectOutputStream appObj = new AppendObjectOutputStream(new FileOutputStream(file, true));
                        appObj.writeObject(transaction);
                        addTransactionToBlock(transaction); // add transaction to block
                    }else {
                        storeObj = new ObjectOutputStream(new FileOutputStream(file, true));
                        storeObj.writeObject(transaction);
                        addTransactionToBlock(transaction); // add transaction to block
                    }

                    errorMessage.setVisible(false);
                    successMessage.setVisible(true); // show success message
                    successMessage.setText("You successfully made a transaction");
                    blockPageButton.setVisible(true); // show block button so user could go to block page

                    /** clear text in the text area */
                    senderPubKeyField.setText("");
                    senderPrivKeyField.setText("");
                    transactionAmountField.setText("");
                    receiverPubKeyField.setText("");
                }else{
                    System.out.println("You don't have sufficient balance");
                    errorMessage.setVisible(true);
                    errorMessage.setText("You don't have sufficient balance");
                }

            }catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /** This function Loads wallets and its file path data into an array list above **/
    public void addDataToArray(){
        /** Load wallets and its file path data into an array list above **/
        try {
            readObj = new ObjectInputStream(new FileInputStream("WalletsAndItsFilePaths.dat"));
            WalletAndItsFilePath info;
            while ((info = (WalletAndItsFilePath) readObj.readObject()) != null){
                wallets.add(info);
            }
            readObj.close();
        } catch (EOFException e) {
            System.out.println("End of File");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /** This class Adds transaction to block and stores block in a file named "Blockchain.dat" */
    public void addTransactionToBlock(Transaction transaction){
        try {
            File file = new File("blockchain.dat");

            // confirm if transaction is valid
            if(transaction.verifySignature()){
                if(file.length() == 0){  // if file has nothing then we create the first genesis block and store it
                    // create first genesis block object then store previous hash for block, transaction
                    Block genesisBlock = new Block(transaction, "0");
                    // store block in a blockchain file
                    if(file.exists()){
                        AppendObjectOutputStream appObj = new AppendObjectOutputStream(new FileOutputStream(file, true));
                        appObj.writeObject(genesisBlock);
                    }else {
                        storeObj = new ObjectOutputStream(new FileOutputStream(file, true));
                        storeObj.writeObject(genesisBlock);
                    }

                }else{ // if file has content the read the data in it, pass its has to a new block as previous has, then store new block
                    Block block;
                    Block previousBlock = null;
                    try{
                        readObj = new ObjectInputStream(new FileInputStream(file));

                        while((block = (Block)readObj.readObject()) != null){ // loop will iterate to get the last block on the blockchain file
                            previousBlock = block;  // store previous block
                        }
                    }catch(EOFException e){
                        System.out.println("End of file");
                        Block newBlock = new Block(transaction, previousBlock.getHash()); // finally, create new block then pass data and previous hash
                        if(file.exists()){
                            AppendObjectOutputStream appObj = new AppendObjectOutputStream(new FileOutputStream(file, true));
                            appObj.writeObject(newBlock);
                        }else {
                            storeObj = new ObjectOutputStream(new FileOutputStream(file, true));
                            storeObj.writeObject(newBlock);
                        }
                    }
                }

            }else{
                System.out.println("Signature is not valid");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**  Updates Wallet info in wallet file. Since two wallets are involved in a transaction, we need to update the balance
     * amount of the wallets, and update the new balance with the old one */
    public void updateWalletTxtFile(WalletAndItsFilePath info){
        System.out.println(info.filePath);
        File file = new File(info.filePath);
        try {
            if(file.delete()){
                file.createNewFile();
            }
            write = new BufferedWriter(new FileWriter(file, true));

            write.write("Public Key: ");
            write.write(StringUtil.getStringFromKey(info.wallet.getPublicKey()));
            write.write("\n");
            write.write("Private Key: ");
            write.write(StringUtil.getStringFromKey(info.wallet.getPrivateKey()));
            write.write("\n");
            write.write("Amount Balance: ");
            write.write(Double.toString(info.wallet.getBalance()));
            write.write("\n");
            write.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void displayBlockPage(){
        Main m = new Main();
        try {
            m.changeScene("TableUi.fxml", "block");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
