package sample;

import java.io.Serializable;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet implements Serializable {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private double balance = 0;

    public Wallet(){
        generateKeyPair();
    }

    public Wallet(double balance){
        generateKeyPair();
        this.balance = balance;
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
            KeyPair keyPair = keyGen.generateKeyPair();
            // Set the public and private keys from the keyPair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isBalance(double transactionAmount){
             if(transactionAmount > balance){
                  return false;
             }else{
                 return true;
             }
    }

   public double deductFromWallet(double transactionAmount){
        if(isBalance(transactionAmount)){
            balance -= transactionAmount;
            return transactionAmount;
        }else{
             return 0;
        }

   }

    public void addToWallet(double transactionAmount){
        balance += transactionAmount;
    }
}
