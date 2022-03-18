import java.util.ArrayList;

/**
 * Represents a HoneyComb blockchain
 */
public class Blockchain {
    /**
     * Current blockchain
     */
    private static ArrayList<Block> chain;
    /**
     * Current block difficulty
     */
    private static int difficulty;
    /**
     * Latest network-known nonce
     */
    private static int nonce;

    /**
     * Blockchain constructor; Initializes a HoneyComb blockchain with _existing_ blocks
     * @param chain Current blockchain to be initialized with (usually only consisting of the genesis block)
     * @param difficulty Current blockchain difficulty
     */
    public Blockchain(ArrayList<Block> chain, int difficulty) {
        Blockchain.chain = chain;
        //! Change the nonce to be dynamic for each block (1 is too easy for every block)
        Blockchain.nonce = 1;
        Blockchain.difficulty = difficulty;
    }

    /**
     * Blockchain constructor; Initializes an _empty_ HoneyComb Blockchain
     */
    public Blockchain() {
        this(new ArrayList<>(), 0);
    }

    /**
     * Get current blockchain
     * @return Current blockchain
     */
    public ArrayList<Block> getChain() {
        return Blockchain.chain;
    }

    /**
     * Gets the current blockchain difficulty
     * @return Difficulty
     */
    public int getCurrentDifficulty() {
        return Blockchain.difficulty;
    }

    /**
     * Gets the current blockchain nonce
     * @return Current blockchain nonce
     */
    public int getNonce() {
        return Blockchain.nonce;
    }

    /**
     * Setters
     * @param block Block you want to add to the blockchain
     * */
    public void addBlock(Block block) {
        chain.add(block);
        // we reached the 1000-block adjustment period, so readjust difficulty
        if (Blockchain.chain.size() % 1000 == 0) Blockchain.difficulty = this.calculateBlockDifficulty();
    }

    /**
     * Set the blockchain nonce
     * @param newNonce New nonce to set
     */
    public void setNonce(int newNonce) {
        Blockchain.nonce = newNonce;
    }

    /**
     * Checks whether the blockchain is valid
     * @return Whether the blockchain is valid
     */
    public boolean isValid() {
        ArrayList<Block> chain = this.getChain();
        for (Block block: chain)
            if (
                !Constants.EMPTY_BLOCK_HASH.equals(block.getPreviousHash()) &&
                !block.getPreviousHash().equals(chain.get(chain.indexOf(block) - 1).getHash())
            )
                return false;
        return true;
    }

    /**
     * Calculate new block difficulty
     * @return New block difficulty
     */
    private int calculateBlockDifficulty() {
        ArrayList<Block> chain = this.getChain();
        int lastNBlockTime = 0;
        for (int i = 0; i < Constants.DIFF_ADJUSTMENT_BLOCK_COUNT; i++) {
            Block currentBlock = chain.get(chain.size() - 1 - i);
            lastNBlockTime += (currentBlock.getTimestamp() - currentBlock.getPreviousTimestamp());
        }
        return this.getCurrentDifficulty() * (lastNBlockTime / Constants.BLOCK_TIME_PER_ADJUSTMENT_PERIOD);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("**********\n* Blocks *\n**********\n\n");
        ArrayList<Block> chain = this.getChain();
        for (Block block: chain) {
            ret.append("**************************************\n");
            ret.append(block);
            ret.append("\n**************************************\n\n");
        }
        return ret.toString();
    }
}
