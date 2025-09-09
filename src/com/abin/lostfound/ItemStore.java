package com.abin.lostfound;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDate;

public class ItemStore {
    private static final String FILE = "items.csv";
    private Map<Integer, Item> items = new HashMap<>();
    private int nextId = 1;

    public ItemStore() {
        load();
    }

    private void load() {
        Path p = Paths.get(FILE);
        if (!Files.exists(p)) return;
        try (BufferedReader br = Files.newBufferedReader(p)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Item it = Item.fromCSV(line);
                items.put(it.getId(), it);
                if (it.getId() >= nextId) nextId = it.getId() + 1;
            }
        } catch (IOException e) {
            System.err.println("Failed to load items: " + e.getMessage());
        }
    }

    public synchronized Item addItem(String name, String desc, String finder, String contact) {
        Item it = new Item(nextId++, name, desc, finder, contact, "FOUND", LocalDate.now().toString());
        items.put(it.getId(), it);
        save();
        return it;
    }

    public synchronized List<Item> listItems() {
        ArrayList<Item> list = new ArrayList<>(items.values());
        list.sort(Comparator.comparingInt(Item::getId));
        return list;
    }

    public synchronized Item getById(int id) {
        return items.get(id);
    }

    public synchronized boolean claimItem(int id, String claimerName, String claimerContact) {
        Item it = items.get(id);
        if (it == null || "CLAIMED".equals(it.getStatus())) return false;
        it.setStatus("CLAIMED");
        it.setClaimedBy(claimerName);
        it.setClaimedContact(claimerContact);
        it.setClaimedDate(LocalDate.now().toString());
        save();
        return true;
    }

    public synchronized boolean deleteItem(int id) {
        if (items.remove(id) != null) {
            save();
            return true;
        }
        return false;
    }

    private void save() {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(FILE))) {
            for (Item it : listItems()) {
                bw.write(it.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save items: " + e.getMessage());
        }
    }
}
