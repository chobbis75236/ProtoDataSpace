package ch.dataspace.objects;

// A class which holds two objects used to store multiple bodies.
public class Binary {
    private Object[] bodies = new Object[2];

    public Binary(Star a, Star b) {
        bodies[0] = a;
        bodies[1] = b;
    }

    public Binary(Binary a, Star b) {
        bodies[0] = a;
        bodies[1] = b;
    }

    public Binary(Binary a, Binary b) {
        bodies[0] = a;
        bodies[1] = b;
    }
}
