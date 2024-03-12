package sample;
import java.io.Serializable;
import java.security.*;
import java.util.Date;

public class Transaction implements Serializable, Comparable<Transaction>{
    private String transactionId; // this is also the hash of the transaction.
    private PublicKey sender; // senders address/public key.
    private PublicKey receiver; // Recipients address/public key.
    private byte[] signature; // this is to prevent anybody else from spending funds in our wallet.
    private double transactionAmount;
    private Date transactionDate;

    public Transaction() {
    }

    // Constructor:
    public Transaction(PublicKey sender, PublicKey receiver, double transactionAmount) {
        this.sender = sender;
        this.receiver = receiver;
        this.transactionAmount = transactionAmount;
        transactionDate = new Date();
        transactionId = calculateHash();
    }

    // This Calculates the transaction hash (which will be used as its Id)
    private String calculateHash() {
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(receiver) +
                        transactionDate.toString() +
                        transactionAmount
        );
    }

    //Signs all the data we don't wish to be tampered with.
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + transactionDate.toString() + transactionAmount;
        signature = StringUtil.applyECDSASig(privateKey,data);
    }

    //Verifies the data we signed hasn't been tampered with
    public boolean verifySignature() {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + transactionDate.toString() + transactionAmount;
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public PublicKey getSender() {
        return sender;
    }

    public PublicKey getReceiver() {
        return receiver;
    }

    public byte[] getSignature() {
        return signature;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", transactionAmount=" + transactionAmount +
                ", transactionDate=" + transactionDate +
                '}';
    }

    @Override
    public int compareTo(Transaction t) {
        return this.transactionId.compareTo(t.transactionId);
    }
}
