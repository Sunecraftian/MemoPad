package edu.jsu.mcis.cs408.memopad;

import androidx.annotation.NonNull;

public class Memo {

    private int id;
    private final String name;

    public Memo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Memo(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public int getID() { return id; }

    @NonNull
    @Override
    public String toString() {
        return id + " | " + name + '\n';
    }
}
