package com.example.demo.bane;

public class Client {
    private Long id;
    private String name;
    private boolean migrated;

    public Client() {}

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
        this.migrated = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isMigrated() { return migrated; }
    public void setMigrated(boolean migrated) { this.migrated = migrated; }
}
