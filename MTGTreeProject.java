import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MTGGUI extends JFrame {

    private JLabel nameLabel, ageLabel, phoneNumberLabel, startDateLabel, endDateLabel, budgetLabel, cityLabel, outputLabel, deleteLabel;
    private JTextField nameField, ageField, phoneNumberField, startDateField, endDateField, budgetField, deleteField;
    private JComboBox<String> cityComboBox;
    private JTextArea outputArea;
    private JButton submitButton, traverseButton, deleteButton , searchButton;
    private TravelItineraryTree travelTree;

    public MTGGUI() {
        setTitle("Travel Itinerary");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new GridLayout(11, 2));
        add(leftPanel);

        nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        leftPanel.add(nameLabel);
        leftPanel.add(nameField);

        ageLabel = new JLabel("Age:");
        ageField = new JTextField();
        leftPanel.add(ageLabel);
        leftPanel.add(ageField);

        phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField();
        leftPanel.add(phoneNumberLabel);
        leftPanel.add(phoneNumberField);

        startDateLabel = new JLabel("Start Date (dd/mm/yyyy):");
        startDateField = new JTextField();
        leftPanel.add(startDateLabel);
        leftPanel.add(startDateField);

        endDateLabel = new JLabel("End Date (dd/mm/yyyy):");
        endDateField = new JTextField();
        leftPanel.add(endDateLabel);
        leftPanel.add(endDateField);

        budgetLabel = new JLabel("Enter your budget (in SAR):");
        budgetField = new JTextField();
        leftPanel.add(budgetLabel);
        leftPanel.add(budgetField);

        cityLabel = new JLabel("Choose a city:");
        String[] cities = {"Riyadh", "Jeddah", "Abha"};
        cityComboBox = new JComboBox<>(cities);
        leftPanel.add(cityLabel);
        leftPanel.add(cityComboBox);

        submitButton = new JButton("Submit");
        leftPanel.add(submitButton);

        traverseButton = new JButton("Traverse Tree");
        leftPanel.add(traverseButton);

        deleteLabel = new JLabel("Enter day to delete:");
        deleteField = new JTextField();
        leftPanel.add(deleteLabel);
        leftPanel.add(deleteField);

        deleteButton = new JButton("Delete Day");
        leftPanel.add(deleteButton);
        
        searchButton = new JButton("Search City");
        leftPanel.add(searchButton);

        JPanel rightPanel = new JPanel(new BorderLayout());
        add(rightPanel);

        outputLabel = new JLabel("Itinerary:");
        rightPanel.add(outputLabel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve user inputs
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String phoneNumber = phoneNumberField.getText();
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                String selectedCity = (String) cityComboBox.getSelectedItem();
                int budget = Integer.parseInt(budgetField.getText());

                String itinerary = generateItinerary(name, age, phoneNumber, startDate, endDate, selectedCity, budget);

                // Display itinerary
                outputArea.setText(itinerary);
            }
        });

        traverseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                traverseTree();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dayToDelete = deleteField.getText();
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete activities for " + dayToDelete + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteDay(dayToDelete);
                    JOptionPane.showMessageDialog(null, "Activities for " + dayToDelete + " deleted successfully!");
                }
            }
        });
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cityName = JOptionPane.showInputDialog(null, "Enter the name of the city to search:");
                searchCity(cityName);
            }
        });

        // Initialize the tree
        travelTree = new TravelItineraryTree();
    }

    private String generateItinerary(String name, int age, String phoneNumber, String startDate, String endDate, String selectedCity, int budget) {
        StringBuilder itineraryBuilder = new StringBuilder();
        itineraryBuilder.append("Itinerary for ").append(name).append(":\n")
                .append("Age: ").append(age).append("\n")
                .append("Phone Number: ").append(phoneNumber).append("\n")
                .append("Trip Dates: ").append(startDate).append(" - ").append(endDate).append("\n")
                .append("Selected City: ").append(selectedCity).append("\n")
                .append("Budget: ").append(budget).append("\n\n");

        // Create city objects with their respective schedules
        City riyadh = new City("Riyadh");
        riyadh.addHighBudgetActivity("Sunday", "Visit Kingdom Tower");
        riyadh.addHighBudgetActivity("Monday", "Explore National Museum");
        riyadh.addHighBudgetActivity("Tuesday", "Enjoy Edge of the World");
        riyadh.addHighBudgetActivity("Wednesday", "Visit Al-Turaif District");
        riyadh.addHighBudgetActivity("Thursday", "Explore Diriyah");
        riyadh.addHighBudgetActivity("Friday", "Visit Riyadh Park");
        riyadh.addHighBudgetActivity("Saturday", "Enjoy Nakheel Mall");

        riyadh.addLowBudgetActivity("Sunday", "Stroll through Al-Musmak Fortress");
        riyadh.addLowBudgetActivity("Monday", "Visit Al-Thumamah Nature Park");
        riyadh.addLowBudgetActivity("Tuesday", "Enjoy King Fahd Park");
        riyadh.addLowBudgetActivity("Wednesday", "Visit Masmak Fortress");
        riyadh.addLowBudgetActivity("Thursday", "Explore Al-Bathaa District");
        riyadh.addLowBudgetActivity("Friday", "Visit National Museum of Saudi Arabia");
        riyadh.addLowBudgetActivity("Saturday", "Enjoy Al-Faisaliah Tower and Mall");

        City jeddah = new City("Jeddah");
        jeddah.addHighBudgetActivity("Sunday", "Visit Al-Balad Historic District");
        jeddah.addHighBudgetActivity("Monday", "Explore Red Sea Mall");
        jeddah.addHighBudgetActivity("Tuesday", "Enjoy Jeddah Water Village");
        jeddah.addHighBudgetActivity("Wednesday", "Visit King Fahd's Fountain");
        jeddah.addHighBudgetActivity("Thursday", "Explore Al-Shallal Theme Park");
        jeddah.addHighBudgetActivity("Friday", "Visit Al-Tayebat Museum");
        jeddah.addHighBudgetActivity("Saturday", "Enjoy Jeddah Corniche");

        jeddah.addLowBudgetActivity("Sunday", "Stroll through Al-Rawdah District");
        jeddah.addLowBudgetActivity("Monday", "Visit Al-Rahma Mosque");
        jeddah.addLowBudgetActivity("Tuesday", "Explore Al-Faisaliah City Center");
        jeddah.addLowBudgetActivity("Wednesday", "Visit King Abdulaziz Mosque");
        jeddah.addLowBudgetActivity("Thursday", "Enjoy Al-Qadimah Neighborhood");
        jeddah.addLowBudgetActivity("Friday", "Visit Jeddah Waterfront");
        jeddah.addLowBudgetActivity("Saturday", "Explore Al-Salam Mall");

        City abha = new City("Abha");
        abha.addHighBudgetActivity("Sunday", "Explore Shada Palace");
        abha.addHighBudgetActivity("Monday", "Visit Abha Regional Museum");
        abha.addHighBudgetActivity("Tuesday", "Enjoy Al-Aqeeq Village");
        abha.addHighBudgetActivity("Wednesday", "Visit Rijal Alma'a Village");
        abha.addHighBudgetActivity("Thursday", "Explore Al-Muftaha Village");
        abha.addHighBudgetActivity("Friday", "Visit Green Mountain Park");
        abha.addHighBudgetActivity("Saturday", "Enjoy Al-Soudah Palace");

        abha.addLowBudgetActivity("Sunday", "Hike in Asir National Park");
        abha.addLowBudgetActivity("Monday", "Visit Al-Soudah Park");
        abha.addLowBudgetActivity("Tuesday", "Explore Al-Mandhara Village");
        abha.addLowBudgetActivity("Wednesday", "Visit Al-Bawadi Village");
        abha.addLowBudgetActivity("Thursday", "Enjoy Al-Habala Village");
        abha.addLowBudgetActivity("Friday", "Visit Al-Baha Museum");
        abha.addLowBudgetActivity("Saturday", "Explore Al-Aridah National Park");

        City chosenCity;
        boolean isHighBudget;

        switch (selectedCity) {
            case "Riyadh":
                chosenCity = riyadh;
                break;
            case "Jeddah":
                chosenCity = jeddah;
                break;
            case "Abha":
                chosenCity = abha;
                break;
            default:
                return "Invalid choice. Exiting program.";
        }

        isHighBudget = budget > 2500;

        // Append city schedule to itinerary
        itineraryBuilder.append(chosenCity.name).append(" Schedule:\n");
        Map<String, List<String>> schedule = isHighBudget ? chosenCity.highBudgetSchedule : chosenCity.lowBudgetSchedule;
        for (Map.Entry<String, List<String>> entry : schedule.entrySet()) {
            itineraryBuilder.append("Day: ").append(entry.getKey()).append("\n");
            itineraryBuilder.append("Activities:\n");
            for (String activity : entry.getValue()) {
                itineraryBuilder.append("- ").append(activity).append("\n");
            }
            itineraryBuilder.append("\n");
        }

        // Insert the chosen city into the tree
        travelTree.insert(chosenCity);

        return itineraryBuilder.toString();
    }

    private void traverseTree() {
        StringBuilder traversalResult = new StringBuilder();
        traversalResult.append("Inorder Traversal:\n");
        traversalResult.append(travelTree.inorderTraversal()).append("\n\n");
        traversalResult.append("Preorder Traversal:\n");
        traversalResult.append(travelTree.preorderTraversal()).append("\n\n");
        traversalResult.append("Postorder Traversal:\n");
        traversalResult.append(travelTree.postorderTraversal());
        outputArea.setText(traversalResult.toString());
    }

    private void deleteDay(String dayToDelete) {
        travelTree.deleteDay(dayToDelete);
    }
    
    private void searchCity(String cityName) {
        if (travelTree.search(cityName)) {
            JOptionPane.showMessageDialog(null, cityName + " exists.");
        } else {
            JOptionPane.showMessageDialog(null, cityName + " does not exist.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MTGGUI gui = new MTGGUI();
                gui.setVisible(true);
            }
        });
    }

    // Base class for cities
    static class City {
        String name;
        Map<String, List<String>> highBudgetSchedule;
        Map<String, List<String>> lowBudgetSchedule;

        City(String name) {
            this.name = name;
        }

        void addHighBudgetActivity(String day, String activity) {
            if (highBudgetSchedule == null)
                highBudgetSchedule = new java.util.HashMap<>();
            if (!highBudgetSchedule.containsKey(day)) {
                highBudgetSchedule.put(day, new ArrayList<>());
            }
            highBudgetSchedule.get(day).add(activity);
        }

        void addLowBudgetActivity(String day, String activity) {
            if (lowBudgetSchedule == null)
                lowBudgetSchedule = new java.util.HashMap<>();
            if (!lowBudgetSchedule.containsKey(day)) {
                lowBudgetSchedule.put(day, new ArrayList<>());
            }
            lowBudgetSchedule.get(day).add(activity);
        }
    }

    static class TravelItineraryTree {
        private TreeNode root;

        private class TreeNode {
            City city;
            TreeNode left;
            TreeNode right;

            TreeNode(City city) {
                this.city = city;
                left = null;
                right = null;
            }
        }

        // Insertion method
        public void insert(City city) {
            root = insertRecursive(root, city);
        }

        private TreeNode insertRecursive(TreeNode root, City city) {
            if (root == null) {
                return new TreeNode(city);
            }

            if (city.name.compareTo(root.city.name) < 0) {
                root.left = insertRecursive(root.left, city);
            } else if (city.name.compareTo(root.city.name) > 0) {
                root.right = insertRecursive(root.right, city);
            }

            return root;
        }

        // Deletion method
        public void deleteDay(String dayToDelete) {
            root = deleteRecursive(root, dayToDelete);
        }

        private TreeNode deleteRecursive(TreeNode root, String dayToDelete) {
            if (root == null) {
                return null;
            }

            root.city.highBudgetSchedule.remove(dayToDelete);
            root.city.lowBudgetSchedule.remove(dayToDelete);

            root.left = deleteRecursive(root.left, dayToDelete);
            root.right = deleteRecursive(root.right, dayToDelete);

            return root;
        }

        // Traversal methods (inorder, preorder, postorder)
        public String inorderTraversal() {
            return inorderRecursive(root);
        }

        private String inorderRecursive(TreeNode root) {
            StringBuilder result = new StringBuilder();
            if (root != null) {
                result.append(inorderRecursive(root.left));
                result.append(root.city.name).append("\n");
                result.append(inorderRecursive(root.right));
            }
            return result.toString();
        }

        public String preorderTraversal() {
            return preorderRecursive(root);
        }

        private String preorderRecursive(TreeNode root) {
            StringBuilder result = new StringBuilder();
            if (root != null) {
                result.append(root.city.name).append("\n");
                result.append(preorderRecursive(root.left));
                result.append(preorderRecursive(root.right));
            }
            return result.toString();
        }

        public String postorderTraversal() {
            return postorderRecursive(root);
        }

        private String postorderRecursive(TreeNode root) {
            StringBuilder result = new StringBuilder();
            if (root != null) {
                result.append(postorderRecursive(root.left));
                result.append(postorderRecursive(root.right));
                result.append(root.city.name).append("\n");
            }
            return result.toString();
        }
        
        // search method
    
        public boolean search(String cityName) {
            return searchRecursive(root, cityName);
        }

        private boolean searchRecursive(TreeNode root, String cityName) {
            if (root == null) {
                return false;
            }

            if (root.city.name.equals(cityName)) {
                return true;
            } else if (cityName.compareTo(root.city.name) < 0) {
                return searchRecursive(root.left, cityName);
            } else {
                return searchRecursive(root.right, cityName);
            }
        }   
        
    }
}


