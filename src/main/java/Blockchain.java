import java.util.ArrayList;
import java.util.Objects;

public class Blockchain {
    private ArrayList<Block> chain;

    public Blockchain() {
        chain = new ArrayList<>();
    }
    public Blockchain(ArrayList<Block> chain) {
        this.chain = chain;
    }

    /**
    * Getters
    * */
    public ArrayList<Block> getChain() {
        return this.chain;
    }

    /**
     * Setters
     * */
    public void addBlock(Block block) {
        chain.add(block);
    }
    public void removeBlock(int index) {
        chain.remove(index);
    }
    public void removeBlock(Block block) {
        chain.remove(block);
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
