package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.Security;

public class Main extends Application {
    Parent root;
    Scene scene;
    Stage stage = new Stage();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        root = FXMLLoader.load(getClass().getResource("senderUi.fxml"));
        primaryStage.setTitle("Hello World");
        scene = new Scene(root, 600, 700);
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();

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
        }catch (IOException e){
            System.out.println("file not found");
        }

    }

    public void changeScene(String s, String p) throws Exception{
        root  =  FXMLLoader.load(getClass().getResource(s));
        scene = new Scene(root, 600, 700);
        stage.setScene(scene);
        stage.setTitle(p);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

