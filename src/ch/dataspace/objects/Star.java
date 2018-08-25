package ch.dataspace.objects;

class Star {

    private static int count = 0;

    private int id;

    // Temporary variable to test what type of star is generated
    private String type;

    // Used for a completely random star.
    Star(Object parent) {
        this.id = count;
        count++;
        type = "primary";
    }

    // Used for a binary star.
    Star(Object parent, Star primary) {
        this.id = count;
        count++;
        type = "secondary";
    }

    @Override
    public String toString() {
        String out = "  - STAR #" + id + " (type: " + type + ")";
        return out;
    }
}
