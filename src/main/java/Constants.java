/**
 * Globally-used constants that can be easily changed as per a fork specification.
 * Kept here for simplicity and ease of development.
 * Hard-coding these values in code defeats the purpose of being developer-friendly.
 */
public class Constants {
    /**
     * Block hash that signifies its empty
     */
    public static final String EMPTY_BLOCK_HASH = "0";
    /**
     * Target time to mine a block (in seconds)
     */
    public static final short BLOCK_TIME = 120;
    /**
     * Number of blocks before difficulty adjustment is undergone
     */
    public static final short DIFF_ADJUSTMENT_BLOCK_COUNT = 1000;
    /**
     * Time it takes for DIFF_ADJUSTMENT_BLOCK_COUNT amount of blocks to be mined
     */
    public static final int BLOCK_TIME_PER_ADJUSTMENT_PERIOD = BLOCK_TIME * DIFF_ADJUSTMENT_BLOCK_COUNT;
    /**
     * Number of leading zeroes that a block hash should have/target
     */
    public static final short MAX_BLOCK_HASH_LEADING_ZEROES = 48;
    /**
     * HoneyComb Node IPs/Hosts dedicated to running the network
     */
    public static final String[] BLOCKCHAIN_SEED_NODES = {"66.188.193.32"};
}
