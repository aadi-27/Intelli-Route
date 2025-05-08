package controller ;

import java.util.* ;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

class Admin {
    String name;
    String email;

    public Admin(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Admin admin = (Admin) obj;
        return name.equals(admin.name) && email.equals(admin.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}

public class AdminAuthenticator {

    private static final String ADMINS_FILE = "admins.txt";
    private Set<Admin> admins = new HashSet<>();

    public AdminAuthenticator() {
        loadAdmins();
    }

    private void loadAdmins() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMINS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    admins.add(new Admin(parts[0].trim(), parts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error reading admins file: " + e.getMessage());
        }
    }

    public boolean isAdmin(String name, String email) {
        return admins.contains(new Admin(name, email));
    }
}