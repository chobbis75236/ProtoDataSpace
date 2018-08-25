package ch.dataspace.framework;

public enum StarType {
    GIANT(0), CLASS_A(1), CLASS_F(2), CLASS_G(3), CLASS_K(4), CLASS_M(5), WHITE_DWARF(6), BROWN_DWARF(7), SPECIAL(8);

    private int rank;

    StarType(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
