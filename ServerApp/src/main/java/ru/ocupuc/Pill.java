package ru.ocupuc;

import java.util.Objects;

public class Pill {
    private int x;
    private int y;

    public Pill(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // equals and hashCode methods for correct comparison in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pill pill = (Pill) o;
        return x == pill.x && y == pill.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
