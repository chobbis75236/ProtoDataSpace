package ch.dataspace.framework;

public enum LumClass {
    Ia(0), Ib(1), II(2), III(3), IV(4), V(5), VII(6);

    private int rank;

    LumClass(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
