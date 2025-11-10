package com.example.demo.bane;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class MigrationController {

    private final List<Client> legacyClients = Collections.synchronizedList(new ArrayList<>());
    private final List<Client> newClients = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGen = new AtomicLong(1);

    @PostConstruct
    public void init() {
        legacyClients.add(new Client(idGen.getAndIncrement(), "Google"));
        legacyClients.add(new Client(idGen.getAndIncrement(), "Microsoft"));
        legacyClients.add(new Client(idGen.getAndIncrement(), "Meta"));
    }

    @GetMapping("/legacy/clients")
    public List<Client> getLegacyClients() {
        return legacyClients;
    }

    @GetMapping("/new/clients")
    public List<Client> getNewClients() {
        return newClients;
    }

    @PostMapping("/migrate/{id}")
    public ResponseEntity<?> migrateClient(@PathVariable Long id) {
        Optional<Client> maybe = legacyClients.stream().filter(c -> c.getId().equals(id)).findFirst();
        if (maybe.isEmpty()) return ResponseEntity.status(404).body(Map.of("error", "Client not found"));

        Client client = maybe.get();
        if (client.isMigrated()) return ResponseEntity.status(400).body(Map.of("error", "Client already migrated"));

        client.setMigrated(true);
        newClients.add(new Client(client.getId(), client.getName()));

        return ResponseEntity.ok(Map.of("message", "Migrated client " + id + " successfully"));
    }
}
