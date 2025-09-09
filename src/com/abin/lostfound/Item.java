package com.abin.lostfound;

public class Item {
    private int id;
    private String name;
    private String description;
    private String finder;
    private String contact;
    private String status; // FOUND or CLAIMED
    private String foundDate; // ISO date
    private String claimedBy;
    private String claimedContact;
    private String claimedDate;

    public Item(int id, String name, String description, String finder, String contact, String status, String foundDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.finder = finder;
        this.contact = contact;
        this.status = status;
        this.foundDate = foundDate;
        this.claimedBy = "";
        this.claimedContact = "";
        this.claimedDate = "";
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getFinder() { return finder; }
    public String getContact() { return contact; }
    public String getStatus() { return status; }
    public String getFoundDate() { return foundDate; }
    public String getClaimedBy() { return claimedBy; }
    public String getClaimedContact() { return claimedContact; }
    public String getClaimedDate() { return claimedDate; }

    public void setStatus(String status) { this.status = status; }
    public void setClaimedBy(String claimedBy) { this.claimedBy = claimedBy; }
    public void setClaimedContact(String claimedContact) { this.claimedContact = claimedContact; }
    public void setClaimedDate(String claimedDate) { this.claimedDate = claimedDate; }

    private static String esc(String s) { return s == null ? "" : s.replace("|", "/"); }
    private static String unesc(String s) { return s == null ? "" : s.replace("/", "|"); }

    // CSV format: id|name|desc|finder|contact|status|foundDate|claimedBy|claimedContact|claimedDate
    public String toCSV() {
        return id + "|" + esc(name) + "|" + esc(description) + "|" + esc(finder) + "|" + esc(contact)
                + "|" + esc(status) + "|" + esc(foundDate) + "|" + esc(claimedBy) + "|" + esc(claimedContact)
                + "|" + esc(claimedDate);
    }

    public static Item fromCSV(String line) {
        String[] p = line.split("\\|", -1);
        int id = Integer.parseInt(p[0]);
        Item it = new Item(id, unesc(p[1]), unesc(p[2]), unesc(p[3]), unesc(p[4]), unesc(p[5]), unesc(p[6]));
        if (p.length > 7) it.claimedBy = unesc(p[7]);
        if (p.length > 8) it.claimedContact = unesc(p[8]);
        if (p.length > 9) it.claimedDate = unesc(p[9]);
        return it;
    }
}
