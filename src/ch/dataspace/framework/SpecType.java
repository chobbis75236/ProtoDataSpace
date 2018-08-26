package ch.dataspace.framework;

public enum SpecType {
    O(0), B(1), A(2), F(3), G(4), K(5), M(6);

    private int rank;

    SpecType(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
