package sample;
import java.io.*;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestApp {
    public static void main(String[] args) throws IOException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
       // SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
       //  Date date = new Date();
      /**  String formatted =  "src/sample/walletFiles/wallet"+"("+System.currentTimeMillis()+")"+".txt";
        System.out.println(formatted);
       File file = new File(formatted);
       file.getParentFile().mkdirs();
       file.createNewFile();
       //file.mkdirs();
        // String fileName = "wallet "+date;
        // System.out.println(date);
       //  System.out.println(fileName);
        FileOutputStream out = new FileOutputStream(file); **/

     /**   File file = new File("test.txt");
        //file.getParentFile().mkdirs();
        // file.createNewFile();
        BufferedWriter write = new BufferedWriter(new FileWriter(file));
        // CREATE NEW WALLET
        Wallet wallet = new Wallet();
        wallet.addToWallet((Math.random()*(10000-10+1)+10)); /** generate and add random money to wallet (from $10 t0 10k)
        write.write("Public Key: ");
        write.write(StringUtil.getStringFromKey(wallet.getPublicKey()));
        write.write("\n");
        write.write("Private Key: ");
        write.write(StringUtil.getStringFromKey(wallet.getPrivateKey()));
        write.write("\n");
        write.write("Amount Balance: ");
        write.write(Double.toString(wallet.getBalance()));
        write.write("\n");
        write.close();   */


     /**  File file = new File("WalletsAndItsFilePathss.dat");
       if(file.exists()){
            AppendObjectOutputStream out1 = new AppendObjectOutputStream(new FileOutputStream(file, true));
            out1.writeObject(new WalletAndItsFilePath(new Wallet(), "baby6.txt"));
       }else {
           ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file, true));
           out.writeObject(new WalletAndItsFilePath(new Wallet(), "baby.txt"));
           out.writeObject(new WalletAndItsFilePath(new Wallet(), "baby1.txt"));
           out.writeObject(new WalletAndItsFilePath(new Wallet(), "baby2.txt"));
       }**/

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("blockchain.dat"));
        try {
          //  WalletAndItsFilePath info = (WalletAndItsFilePath) in.readObject();
          //  System.out.println(info.wallet);
          //  System.out.println(info.filePath);
            Block info;
            while ((info = (Block) in.readObject()) != null){
                System.out.println("Block");
                System.out.println("Previous Hash: "+info.getPreviousHash());
                System.out.println("Hash: "+info.getHash());
                System.out.println("----------------------------");
            }
            in.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e){
            System.out.println("End of file");
        }
    }
}
