package sample;

import java.io.*;
import java.util.ArrayList;

/** The purpose of this class is to load block data or transaction data to a list or tree to be used in other parts
 * of this application. **/
public class LoadData {
    private static ObjectInputStream read;

    public LoadData(){

    }

    public static ArrayList<Block> loadBlockDataToArray(){
        ArrayList<Block> blocks = new ArrayList<>();
        File file = new File("blockchain.dat");
        try {
            read = new ObjectInputStream(new FileInputStream(file));
            Block block;
            while((block = (Block)read.readObject()) != null){
                  blocks.add(block);
            }

        }catch (EOFException e) {
            System.out.println("End of file: "+ file.getName()+ " has reached.");
        } catch (IOException e) {
            System.out.println("Cant read: "+file.getName()+ " because it does not exist.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return blocks;
    }

    public static MyAvlTreeVersion<Block> loadBlockDataToTree(){
        MyAvlTreeVersion<Block> blocks = new MyAvlTreeVersion<>();
        File file = new File("blockchain.dat");
        try {
            read = new ObjectInputStream(new FileInputStream(file));
            Block block;
            while((block = (Block)read.readObject()) != null){
                blocks.add(block);
            }

        }catch (EOFException e) {
            System.out.println("End of file: "+ file.getName()+ " has reached.");
        } catch (IOException e) {
            System.out.println("Cant read: "+file.getName()+ " because it does not exist.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return blocks;
    }


    public static MyAvlTreeVersion<Transaction> loadTransactionDataToTree(){
        MyAvlTreeVersion<Transaction> transactions = new MyAvlTreeVersion<>();
        File file = new File("transactions.dat");
        try {
            read = new ObjectInputStream(new FileInputStream(file));
            Transaction transact;
            while((transact = (Transaction)read.readObject()) != null){
                transactions.add(transact);
            }

        }catch (EOFException e) {
            System.out.println("End of file: "+ file.getName()+ " has reached.");
        } catch (IOException e) {
            System.out.println("Cant read: "+file.getName()+ " because it does not exist.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static ArrayList<Transaction> loadTransactionDataToArray(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        File file = new File("transactions.dat");
        try {
            read = new ObjectInputStream(new FileInputStream(file));
            Transaction transact;
            while((transact = (Transaction)read.readObject()) != null){
                transactions.add(transact);
            }

        }catch (EOFException e) {
            System.out.println("End of file: "+ file.getName()+ " has reached.");
        } catch (IOException e) {
            System.out.println("Cant read: "+file.getName()+ " because it does not exist.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return transactions;
    }

}
