import java.util.ArrayList;

public class Blockchain {
    private static ArrayList<Block> chain;
    private static int difficulty;
    private static int nonce;

    public Blockchain(ArrayList<Block> chain) {
        Blockchain.chain = chain;
        //! Change the nonce to be dynamic for each block (1 is too easy for every block)
        Blockchain.nonce = 1;
    }

    /**
    * Getters
    * */
    public ArrayList<Block> getChain() {
        return Blockchain.chain;
    }
    public int getCurrentDifficulty() {
        return Blockchain.difficulty;
    }
    public int getNonce() {
        return Blockchain.nonce;
    }

    /**
     * Setters
     * */
    public void addBlock(Block block) {
        chain.add(block);
        // we reached the 1000-block adjustment period, so readjust difficulty
        if (Blockchain.chain.size() % 1000 == 0) Blockchain.difficulty = this.calculateBlockDifficulty();
    }
    public void setNonce(int newNonce) {
        Blockchain.nonce = newNonce;
    }

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

    private int calculateBlockDifficulty() {
        ArrayList<Block> chain = this.getChain();
        int last1000BlockTime = 0;
        for (int i = 0; i < Constants.DIFF_ADJUSTMENT_BLOCK_COUNT; i++) {
            Block currentBlock = chain.get(chain.size() - 1 - i);
            last1000BlockTime += (currentBlock.getTimestamp() - currentBlock.getPreviousTimestamp());
        }
        return this.getCurrentDifficulty() * (last1000BlockTime / Constants.BLOCK_TIME_PER_ADJUSTMENT_PERIOD);
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
