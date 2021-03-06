import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Represents an individual blockchain block
 */
public class Block {
    private final String hash;
    private final String previousHash;
    private final String data;
    private final long timestamp;
    private final long previousTimestamp;
    private final long nonce;

    /**
     * Block constructor; Initializes a HoneyComb block
     * @param data Block data to be included in the block
     * @param previousHash Previous block hash
     * @param timestamp Timestamp the block was created
     * @param previousTimestamp Timestamp of the previous block
     * @param nonce Block's "magic number" for solving it
     */
    public Block(String data, String previousHash, long timestamp, long previousTimestamp, long nonce) {
        this.data = data;
        this.hash = this.calculateBlockHash();
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.previousTimestamp = previousTimestamp;
        this.nonce = nonce;
    }

    /* Getters */

    /**
     * Get block data
     * @return Block data
     */
    public String getData() {
        return this.data;
    }

    /**
     * Get block created timestamp
     * @return Block created timestamp
     */
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Get previous block hash
     * @return Previous block hash
     */
    public String getPreviousHash() {
        return this.previousHash;
    }

    /**
     * Get block hash
     * @return Block hash
     */
    public String getHash() {
        return this.hash;
    }

    /**
     * Get block nonce
     * @return Block nonce
     */
    public long getNonce() {
        return this.nonce;
    }

    /**
     * Get previous block timestamp
     * @return Previous block timestamp
     */
    public long getPreviousTimestamp() {
        return this.previousTimestamp;
    }

    /* Block initialization instance methods */

    /**
     * Calculate block hash
     * @return Block hash
     */
    private String calculateBlockHash() {
        String dataToHash = previousHash + this.timestamp + this.nonce + this.data;
        MessageDigest digest;
        byte[] bytes;
        try {
            digest = MessageDigest.getInstance("SHA3-512");
            bytes = digest.digest(dataToHash.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Unsupported algorithm:\n\n" + ex);
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    @Override
    public String toString() {
        return String.format(
                "Hash: %s\nPrevious Hash: %s\nTimestamp: %s\nPrevious Timestamp: %s\nNonce: %s\nData: %s",
                this.getHash().equals(Constants.EMPTY_BLOCK_HASH) ? "(None)" : this.getHash(),
                this.getPreviousHash(),
                this.getTimestamp(),
                this.getPreviousTimestamp(),
                this.getNonce(),
                this.getData()
        );
    }
}
