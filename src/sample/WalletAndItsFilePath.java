package sample;

import java.io.Serializable;

public class WalletAndItsFilePath implements Serializable {
   private static final long serialVersionUID = 4L;
    Wallet wallet; // stores wallet
    String filePath; //  stores files path of the wallet

    public WalletAndItsFilePath() {
    }

    public WalletAndItsFilePath(Wallet wallet, String filePath) {
        this.wallet = wallet;
        this.filePath =  filePath;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "WalletAndItsFilePath{" +
                "wallet=" + wallet +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
