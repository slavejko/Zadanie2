package helpers;

public class TranspositionKey{

    int blockSize;
    Integer[] encPerm;
    Integer[] decPerm;
    Double score;

    public TranspositionKey(Integer[] encPerm) {
        this.blockSize = encPerm.length;
        this.encPerm = encPerm;
        this.decPerm = Permutations.inverse(encPerm);
        this.score = new Double(0);
    }

    //TODO setDecPerm

    public int getBlockSize() {
        return blockSize;
    }

    public Integer[] getDecPerm() {
        return decPerm;
    }

    public Integer[] getEncPerm() {
        return encPerm;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}