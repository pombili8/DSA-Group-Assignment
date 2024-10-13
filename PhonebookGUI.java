package javaapplication34;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Contact {
    String name;
    String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return name + " - " + phoneNumber;
    }
}

public class PhonebookGUI extends JFrame {
    private ArrayList<Contact> phonebook;
    private JTextArea displayArea;

    public PhonebookGUI() {
        phonebook = new ArrayList<>();
        createGUI();
    }

    private void createGUI() {
        setTitle("Phonebook Application");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center the window
        setLocationRelativeTo(null);
        
        // Set purple background color for the entire frame
        getContentPane().setBackground(new Color(128, 0, 128));  // Purple
        
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(173, 216, 230));  // Light Blue

        JMenu displayMenu = new JMenu("Display Contacts");
        JMenuItem displayButton = new JMenuItem("Display All");
        JMenu sortMenu = new JMenu("Sort");
        JMenuItem sortAZButton = new JMenuItem("Sort A-Z");
        JMenuItem sortZAButton = new JMenuItem("Sort Z-A");
        JMenuItem displayOriginalButton = new JMenuItem("Original Order");

        // Add sorting options to the sort submenu
        sortMenu.add(sortAZButton);
        sortMenu.add(sortZAButton);
        sortMenu.add(displayOriginalButton);

        // Add items to display menu
        displayMenu.add(displayButton);
        displayMenu.add(sortMenu);

        // Add display menu to the menu bar
        menuBar.add(displayMenu);
        setJMenuBar(menuBar);

        // Set up the display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setBackground(new Color(240, 255, 255));  // Light Blue background for display
        displayArea.setForeground(Color.BLACK);  // Black text

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Contacts"));
        add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the top buttons
        JPanel topPanel = new JPanel(new GridLayout(1, 4));  
        topPanel.setBackground(new Color(128, 0, 128));  // Purple background for top panel

        JButton insertButton = new JButton("Insert Contact");
        JButton searchButton = new JButton("Search Contact");
        JButton deleteButton = new JButton("Delete Contact");
        JButton updateButton = new JButton("Update Contact");

        customizeButton(insertButton);
        customizeButton(searchButton);
        customizeButton(deleteButton);
        customizeButton(updateButton);

        // Add buttons to the top panel
        topPanel.add(insertButton);
        topPanel.add(searchButton);
        topPanel.add(deleteButton);
        topPanel.add(updateButton);

        add(topPanel, BorderLayout.NORTH);

        // Add ActionListeners to buttons
        insertButton.addActionListener(e -> insertContactGUI());
        searchButton.addActionListener(e -> searchContactGUI());
        deleteButton.addActionListener(e -> deleteContactGUI());
        updateButton.addActionListener(e -> updateContactGUI());
        displayButton.addActionListener(e -> displayContacts());
        sortAZButton.addActionListener(e -> displayContactsSorted(true));  // Sort A-Z
        sortZAButton.addActionListener(e -> displayContactsSorted(false));  // Sort Z-A
        displayOriginalButton.addActionListener(e -> displayContactsOriginal());  // Original order
    }

    private void customizeButton(JButton button) {
        button.setBackground(new Color(173, 216, 230));  // Light Blue button background
        button.setForeground(new Color(128, 0, 128));  // Purple text
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createLineBorder(new Color(240, 255, 255), 2));  // Light Blue border
    }

    private void insertContactGUI() {
        JFrame insertFrame = createSubFrame("Insert Contact", 300, 150);
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField();
        JButton submitButton = new JButton("Submit");

        insertFrame.add(nameLabel);
        insertFrame.add(nameField);
        insertFrame.add(phoneLabel);
        insertFrame.add(phoneField);
        insertFrame.add(new JLabel());
        insertFrame.add(submitButton);

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String phoneNumber = phoneField.getText();
            if (isNumeric(phoneNumber)) {
                insertContact(name, phoneNumber);
                JOptionPane.showMessageDialog(null, "Contact added successfully.");
                insertFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid phone number. Please enter numeric values.");
            }
        });

        insertFrame.setVisible(true);
    }

    private JFrame createSubFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setLayout(new GridLayout(3, 2));
        frame.setLocationRelativeTo(this);
        frame.getContentPane().setBackground(new Color(240, 255, 255));  // Light Blue background for subframes
        return frame;
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");  // Checks if the string contains only digits
    }

    private void searchContactGUI() {
        String name = JOptionPane.showInputDialog("Enter the name of the contact to search:");
        if (name != null && !name.isEmpty()) {
            for (Contact contact : phonebook) {
                if (contact.name.equalsIgnoreCase(name)) {
                    displayArea.setText("Found contact:\n" + contact);
                    return;
                }
            }
            displayArea.setText("Contact not found.");
        } else {
            JOptionPane.showMessageDialog(null, "Name cannot be empty.");
        }
    }

    private void deleteContactGUI() {
        String name = JOptionPane.showInputDialog("Enter the name of the contact to delete:");
        if (name != null && !name.isEmpty()) {
            for (int i = 0; i < phonebook.size(); i++) {
                if (phonebook.get(i).name.equalsIgnoreCase(name)) {
                    phonebook.remove(i);
                    displayArea.setText("Contact deleted.");
                    return;
                }
            }
            displayArea.setText("Contact not found.");
        } else {
            JOptionPane.showMessageDialog(null, "Name cannot be empty.");
        }
    }

    private void updateContactGUI() {
        String name = JOptionPane.showInputDialog("Enter the name of the contact to update:");
        if (name != null && !name.isEmpty()) {
            for (Contact contact : phonebook) {
                if (contact.name.equalsIgnoreCase(name)) {
                    String newPhoneNumber = JOptionPane.showInputDialog("Enter the new phone number:");
                    if (newPhoneNumber != null && !newPhoneNumber.isEmpty() && isNumeric(newPhoneNumber)) {
                        contact.phoneNumber = newPhoneNumber;
                        displayArea.setText("Contact updated:\n" + contact);
                        return;
                    } else {
                        JOptionPane.showMessageDialog(null, "Phone number cannot be empty or non-numeric.");
                        return;
                    }
                }
            }
            displayArea.setText("Contact not found.");
        } else {
            JOptionPane.showMessageDialog(null, "Name cannot be empty.");
        }
    }

    public void displayContacts() {
        if (phonebook.isEmpty()) {
            displayArea.setText("No contacts available.");
        } else {
            StringBuilder builder = new StringBuilder();
            for (Contact contact : phonebook) {
                builder.append(contact).append("\n");
            }
            displayArea.setText(builder.toString());
        }
    }

    public void insertContact(String name, String phoneNumber) {
        Contact newContact = new Contact(name, phoneNumber);
        phonebook.add(newContact);
    }

    public void displayContactsOriginal() {
        if (phonebook.isEmpty()) {
            displayArea.setText("No contacts available.");
        } else {
            StringBuilder builder = new StringBuilder();
            for (Contact contact : phonebook) {
                builder.append(contact).append("\n");
            }
            displayArea.setText(builder.toString());
        }
    }

    public void displayContactsSorted(boolean ascending) {
        if (phonebook.isEmpty()) {
            displayArea.setText("No contacts available.");
        } else {
            ArrayList<Contact> sortedList = new ArrayList<>(phonebook);
            sortedList.sort((c1, c2) -> ascending ? c1.name.compareToIgnoreCase(c2.name)
                    : c2.name.compareToIgnoreCase(c1.name));

            StringBuilder builder = new StringBuilder();
            for (Contact contact : sortedList) {
                builder.append(contact).append("\n");
            }
            displayArea.setText(builder.toString());
        }
    }

    private void analyzeSearchEfficiency() {
        displayArea.setText("Search Algorithm Time Complexity: O(n)");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PhonebookGUI gui = new PhonebookGUI();
            gui.setVisible(true);
        });
    }
}
