package bbtracker;

import static bbtracker.Login.jTextField1;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class UserExpense {
    // Transaction class to hold the transaction details
    static class Transaction {
        private String date;
        private List<Double> amounts;
        private List<String> descriptions;

        public Transaction(String date) {
            this.date = date;
            this.amounts = new ArrayList<>();
            this.descriptions = new ArrayList<>();
        }

        public String getDate() {
            return date;
        }

        public void addTransaction(double amount, String description) {
            amounts.add(amount);
            descriptions.add(description);
        }

        public List<Double> getAmounts() {
            return amounts;
        }

        public List<String> getDescriptions() {
            return descriptions;
        }
    }

    // GUI class to display the transactions
    public static class TransactionListGUI {
        private JFrame frame;
        private JPanel panel;
        private JTable transactionTable;
        private DefaultTableModel tableModel;

        public TransactionListGUI(List<Transaction> transactions) {
            frame = new JFrame("Expenses Lists");
            panel = new JPanel(new BorderLayout());
            panel.setBorder(new EmptyBorder(10, 10, 10, 10));
            panel.setBackground(Color.WHITE);

            JLabel headerLabel = new JLabel("Expenses Lists", JLabel.CENTER);
            headerLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
            headerLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

            // Create table model with headers
            String[] columnHeaders = {"Date", "Description", "Amount ($)"};
             tableModel = new DefaultTableModel(columnHeaders, 0) {
             
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are not editable
                return false;
            }
        };

            // Populate table model with transaction data
            for (Transaction transaction : transactions) {
                String date = transaction.getDate();
                List<Double> amounts = transaction.getAmounts();
                List<String> descriptions = transaction.getDescriptions();
                int maxEntries = Math.max(amounts.size(), descriptions.size());

                for (int i = 0; i < maxEntries; i++) {
                    double amount = (i < amounts.size()) ? amounts.get(i) : 0.0;
                    String description = (i < descriptions.size()) ? descriptions.get(i) : "";

                    Object[] rowData = {date, description, amount};
                    tableModel.addRow(rowData);
                }
            }

            transactionTable = new JTable(tableModel);
            transactionTable.setFont(new Font("Century Gothic", Font.PLAIN, 16));
            transactionTable.setRowHeight(30);
            transactionTable.setBackground(Color.WHITE);
            transactionTable.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 18));
            transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            transactionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            transactionTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (value instanceof Double) {
                        setText(String.format("%.2f", (Double) value));
                        setHorizontalAlignment(SwingConstants.RIGHT);
                    }
                    return c;
                }
            });

            JScrollPane scrollPane = new JScrollPane(transactionTable);
            scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            panel.add(headerLabel, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);

            // Add sorting buttons
            JPanel sortingPanel = new JPanel(new FlowLayout());
            sortingPanel.setBackground(Color.WHITE);
            JButton exitButton = createImageButton("C:\\Users\\Mikaella Gonzales\\Downloads\\BBtracker\\src\\icons\\btns\\btn_exit.png");


            sortingPanel.add(exitButton);

            panel.add(sortingPanel, BorderLayout.SOUTH);
            exitButton.addActionListener(e -> exit());

            frame.add(panel);
            frame.setSize(800, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setResizable(false);
            frame. setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/icon_img.png")));
            frame.setBackground(Color.WHITE);
            frame.setLocationRelativeTo(null);
        }
        
       // Method to refresh the table display
    public void refreshTable() {
        tableModel.fireTableDataChanged();
    }

        
        
        private JButton createImageButton(String imagePath) {
            ImageIcon icon = new ImageIcon(imagePath);
            JButton button = new JButton(icon);
            button.setPreferredSize(new Dimension(110, 50)); // Adjust size as needed
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);
            button.setFocusable(false);
            return button;
        }
        // Method to sort transactions by amount using Magnetic Bubble Sort
       

        // Method to sort transactions by date (newest or oldest first)
        

        
        
        private void exit (){
            Home home = new Home();
            home.setVisible(true);
            home.jLabel3.setText("@"+jTextField1.getText());
            home.pack();
            home.setLocationRelativeTo(null);
            frame.dispose();
        }


        // Helper method to swap rows in the table model
        private void swapRows(int row1, int row2) {
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                Object temp = tableModel.getValueAt(row1, i);
                tableModel.setValueAt(tableModel.getValueAt(row2, i), row1, i);
                tableModel.setValueAt(temp, row2, i);
            }
        }
    }

    // Method to parse transactions from file
    public static List<Transaction> parseTransactionsFromFile(String fileName) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        Transaction currentTransaction = null;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Transaction:")) {
                    if (currentTransaction != null) {
                        transactions.add(currentTransaction);
                    }
                    String date = br.readLine().trim();
                    currentTransaction = new Transaction(date);
                } else if (line.matches("^\\d+\\.\\d+, .+$")) {
                    String[] parts = line.split(", ");
                    try {
                        double amount = Double.parseDouble(parts[0]);
                        String description = parts[1];
                        // Add each transaction separately
                        currentTransaction.addTransaction(amount, description);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Add the last transaction to the list
            if (currentTransaction != null) {
                transactions.add(currentTransaction);
            }
        }

        return transactions;
    }

    public static void main(String[] args) {
        String username = Login.jTextField1.getText();
        String fileName = username + "_expenses.txt";

        try {
            List<Transaction> transactions = parseTransactionsFromFile(fileName);
            SwingUtilities.invokeLater(() -> new TransactionListGUI(transactions));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
