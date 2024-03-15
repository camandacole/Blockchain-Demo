# BLOCKCHAIN DEMO

The goal of this project is to a simplified version of Transaction process for BlockChain Crytography. Blockchain is a "constantly growing ledger which keeps a permanent record of all transactrions that have taken place in a secure, chronological, immutable way".
It is organized as a chain of blocks where each block records a recent transactions. In addition  each block points to the previous block allowin tracking of recent anabd previous transactions. When a transaction is made, a block is created for it and its appended to the blockchain

# BLOCKCHAIN TRANSACTION
IN ORDER TO MAKE A TRANSACTION YOU NEED 3 KEY THINGS:
- A Public Key
- A private Key
- A wallet
  
When the project, the first page you encounter is the transaction page below:

![transaction ](https://github.com/camandacole/Blockchain-Demo/assets/44839897/b6a0d407-ae6a-448c-a71f-1303ffc0a594)

- First click on the "create wallet" button. This will create a wallet that contains a private ansd publick key and it loads a random amount from $10 dollars to 10k. The wallet info would be saved to a file.
- To demonstrate a transaction between a sender and receiver, we get the senders public key, private key saved and the receivers public key and fill it as input in the form below:

![transaction2](https://github.com/camandacole/Blockchain-Demo/assets/44839897/651f1b1b-514b-43ec-8ecc-0a882f7c55a4)

- If transaction was sucessful, then a block is created for it and transaction is signed to ensure legitimacy. We can view the table of blocks below. One you click on a specific block, its transaction is displayed

![blocks](https://github.com/camandacole/Blockchain-Demo/assets/44839897/b4c1948f-e006-4244-99e5-686d13f3838c)

## HASHING ALGORITHM USED FOR THIS PROJECT

As you see in the above picture, each block has a hash value which is geneted based on the block information. Hashing maintains the immutability of a bloackchain, making it difficult to compromise. This project uses the SHA 256 Hashing algorithm, a cryptographic algorithm
that uses a sophisticated mathematical function.

### REFERENCES:

https://www.javatpoint.com/how-block-hashes-work-in-blockchain
https://www.ssldragon.com/blog/sha-256-algorithm/#:~:text=SHA%2D256%20refers%20to%20a,uniquely%20representing%20the%20original%20data.
