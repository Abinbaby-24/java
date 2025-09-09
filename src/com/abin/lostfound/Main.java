package com.abin.lostfound;

import java.util.List;
import java.util.Scanner;

import com.abin.lostfound.ItemStore;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ItemStore store = new ItemStore();
        System.out.println("=== Lost & Found Console App ===");

        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addItem(store); break;
                case "2": listItems(store); break;
                case "3": viewItem(store); break;
                case "4": claimItem(store); break;
                case "5": deleteItem(store); break;
                case "0": System.out.println("Bye!"); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1) Add found item");
        System.out.println("2) List items");
        System.out.println("3) View item by id");
        System.out.println("4) Claim item");
        System.out.println("5) Delete item");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    private static void addItem(ItemStore store) {
        System.out.print("Item name: ");
        String name = sc.nextLine().trim();
        System.out.print("Description: ");
        String desc = sc.nextLine().trim();
        System.out.print("Finder name: ");
        String finder = sc.nextLine().trim();
        System.out.print("Contact: ");
        String contact = sc.nextLine().trim();

        Item it = store.addItem(name, desc, finder, contact);
        System.out.println("Added item with id: " + it.getId());
    }

    private static void listItems(ItemStore store) {
        List<Item> list = store.listItems();
        if (list.isEmpty()) { System.out.println("No items found."); return; }
        for (Item it : list) {
            System.out.printf("#%d | %s | finder: %s | date: %s | status: %s%n",
                    it.getId(), it.getName(), it.getFinder(), it.getFoundDate(), it.getStatus());
        }
    }

    private static void viewItem(ItemStore store) {
        try {
            System.out.print("Enter id: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            Item it = store.getById(id);
            if (it == null) { System.out.println("Item not found."); return; }
            System.out.println("ID: " + it.getId());
            System.out.println("Name: " + it.getName());
            System.out.println("Description: " + it.getDescription());
            System.out.println("Finder: " + it.getFinder());
            System.out.println("Finder contact: " + it.getContact());
            System.out.println("Found date: " + it.getFoundDate());
            System.out.println("Status: " + it.getStatus());
            if ("CLAIMED".equals(it.getStatus())) {
                System.out.println("Claimed by: " + it.getClaimedBy());
                System.out.println("Claim contact: " + it.getClaimedContact());
                System.out.println("Claimed date: " + it.getClaimedDate());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
        }
    }

    private static void claimItem(ItemStore store) {
        try {
            System.out.print("Enter id to claim: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Your name: ");
            String name = sc.nextLine().trim();
            System.out.print("Your contact: ");
            String contact = sc.nextLine().trim();
            boolean ok = store.claimItem(id, name, contact);
            System.out.println(ok ? "Item marked as CLAIMED." : "Could not claim (not found or already claimed).");
        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
        }
    }

    private static void deleteItem(ItemStore store) {
        try {
            System.out.print("Enter id to delete: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            boolean ok = store.deleteItem(id);
            System.out.println(ok ? "Deleted." : "Not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
        }
    }
}
