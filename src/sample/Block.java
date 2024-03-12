package sample;

import java.io.Serializable;
import java.util.Date;

public class Block implements Serializable, Comparable<Block>{
    private String hash;
    private String previousHash;
    private Transaction data; //our data will be a simple message.
    private Date timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce = 0;

    //Block Constructor.

    public Block() {

    }

    public Block(Transaction data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        timeStamp.toString() +
                        Integer.toString(nonce) +
                        data.toString()
        );
        return calculatedhash;
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public Transaction getData() {
        return data;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    @Override
    public int compareTo(Block b) {
         return this.hash.compareTo(b.hash);
    }
}
