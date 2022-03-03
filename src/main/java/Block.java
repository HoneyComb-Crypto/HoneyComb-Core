import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Block {
    private final String hash;
    private final String previousHash;
    private final String data;
    private final long timestamp;
    private final long previousTimestamp;
    private final String nonce;
    private long difficulty;

    public Block(String data, String previousHash, long timestamp, long previousTimestamp, long previousDifficulty) {
        this.data = data;
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.previousTimestamp = previousTimestamp;

        // Calculate difficulty (≈120-second block time, +-1 difficulty steps)
        if (timestamp - previousTimestamp < 120)
            // the block was too easy
            this.difficulty = previousDifficulty + 1;
        else if (timestamp - previousTimestamp > 120 && difficulty > 0)
            // block was too hard
            this.difficulty = previousDifficulty - 1;

        String nonce = "";
        for (int i = 0; i < this.difficulty; i++)
            nonce += "0";
        this.nonce = nonce;

        this.hash = this.calculateBlockHash();
    }

    /**
    * Getters
    * */
    public String getData() {
        return this.data;
    }
    public long getTimestamp() {
        return this.timestamp;
    }
    public String getPreviousHash() {
        return this.previousHash;
    }
    public String getHash() {
        return this.hash;
    }
    public String getNonce() {
        return this.nonce;
    }
    public long getDifficulty() {
        return this.difficulty;
    }
    public long getPreviousTimestamp() {
        return this.previousTimestamp;
    }

    /**
    * Block initialization instance methods
    * */
    private String calculateBlockHash() {
        String dataToHash = this.getPreviousHash() + Long.toString(this.getTimestamp()) + this.getNonce();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-512");
            byte[] bytes = digest.digest(dataToHash.getBytes(UTF_8));
            StringBuffer buffer = new StringBuffer();
            for (byte b : bytes) {
                buffer.append(String.format("%02x", b));
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Unsupported Algorithm:\n\n" + ex);
            return "";
        }
    }

    public String toString() {
        return String.format(
                "Hash: %s\nDifficulty: %s\nPrevious Hash: %s\nTimestamp: %s\nPrevious Timestamp: %s\nNonce: %s\nData: %s",
                Objects.equals(this.getHash(), Constants.EMPTY_BLOCK_HASH) ? "(None)" : this.getHash(),
                this.getDifficulty(),
                this.getPreviousHash(),
                this.getTimestamp(),
                this.getPreviousTimestamp(),
                this.getNonce(),
                this.getData()
        );
    }
}
