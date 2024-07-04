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
public class TransactionList {
    
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

  
    public static class TransactionListGUI {
        private JFrame frame;
        private JPanel panel;
        private JTable transactionTable;
        private DefaultTableModel tableModel;

        public TransactionListGUI(List<Transaction> transactions) {
            frame = new JFrame("Sort Your Expenses");
            panel = new JPanel(new BorderLayout());
            panel.setBorder(new EmptyBorder(10, 10, 10, 10));
            panel.setBackground(Color.WHITE);

            JLabel headerLabel = new JLabel("Sort your expenses", JLabel.CENTER);
            headerLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
            headerLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

            String[] columnHeaders = {"Date", "Description", "Amount"};
            tableModel = new DefaultTableModel(columnHeaders, 0) {
        
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

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

            JScrollPane scrollTable = new JScrollPane(transactionTable);
           scrollTable.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            panel.add(headerLabel, BorderLayout.NORTH);
            panel.add(scrollTable, BorderLayout.CENTER);

            JPanel sortingPanel = new JPanel(new FlowLayout());
            sortingPanel.setBackground(Color.WHITE);
            JButton sortAmountButton = createImageButton("C:\\Users\\Mikaella Gonzales\\Downloads\\BBtracker\\src\\icons\\btns\\btn_amount.png");
            JButton sortDateButton = createImageButton("C:\\Users\\Mikaella Gonzales\\Downloads\\BBtracker\\src\\icons\\btns\\btn_newest.png");
            JButton sortDateOldButton = createImageButton("C:\\Users\\Mikaella Gonzales\\Downloads\\BBtracker\\src\\icons\\btns\\btn_oldest.png");
            JButton sortCategoryButton = createImageButton("C:\\Users\\Mikaella Gonzales\\Downloads\\BBtracker\\src\\icons\\btns\\btn_category.png");
            JButton exitButton = createImageButton("C:\\Users\\Mikaella Gonzales\\Downloads\\BBtracker\\src\\icons\\btns\\btn_exit.png");

            sortingPanel.add(sortAmountButton);
            sortingPanel.add(sortDateButton);
            sortingPanel.add(sortDateOldButton);
            sortingPanel.add(sortCategoryButton);
            sortingPanel.add(exitButton);

            panel.add(sortingPanel, BorderLayout.SOUTH);
            sortAmountButton.addActionListener(e -> sortTransactionsByAmount());
            sortDateButton.addActionListener(e -> sortTransactionsByDate(true));
            sortDateOldButton.addActionListener(e -> sortTransactionsByDate(false));
            sortCategoryButton.addActionListener(e -> sortTransactionsByCategory());
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
        
        public void refreshTable() {
            tableModel.fireTableDataChanged();
        }

        
        
        private JButton createImageButton(String imagePath) {
            ImageIcon icon = new ImageIcon(imagePath);
            JButton button = new JButton(icon);
            button.setPreferredSize(new Dimension(110, 50)); 
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);
            button.setFocusable(false);
            return button;
        }
       
        private void sortTransactionsByAmount() {
            int n = tableModel.getRowCount();
            boolean swapped = true;
            int start = 0;
            while (swapped) {
                swapped = false;
                for (int i = start; i < n - 1; ++i) {
                    double amount1 = (Double) tableModel.getValueAt(i, 2);
                    double amount2 = (Double) tableModel.getValueAt(i + 1, 2);
                    if (amount1 > amount2) {
                        swapRows(i, i + 1);
                        swapped = true;
                    }
                }
                if (!swapped) break;
                swapped = false;
                for (int i = n - 2; i >= start; --i) {
                    double amount1 = (Double) tableModel.getValueAt(i, 2);
                    double amount2 = (Double) tableModel.getValueAt(i + 1, 2);
                    if (amount1 > amount2) {
                        swapRows(i, i + 1);
                        swapped = true;
                    }
                }
                ++start;
            }
        }

        
        private void sortTransactionsByDate(boolean newestFirst) {
            int n = tableModel.getRowCount();
            boolean swapped = true;
            int start = 0;
            while (swapped) {
                swapped = false;
                for (int i = start; i < n - 1; ++i) {
                    String date1 = (String) tableModel.getValueAt(i, 0);
                    String date2 = (String) tableModel.getValueAt(i + 1, 0);
                    int comparison = date1.compareTo(date2);
                    if (newestFirst && comparison < 0 || !newestFirst && comparison > 0) {
                        swapRows(i, i + 1);
                        swapped = true;
                    }
                }
                if (!swapped) break;
                swapped = false;
                for (int i = n - 2; i >= start; --i) {
                    String date1 = (String) tableModel.getValueAt(i, 0);
                    String date2 = (String) tableModel.getValueAt(i + 1, 0);
                    int comparison = date1.compareTo(date2);
                    if (newestFirst && comparison < 0 || !newestFirst && comparison > 0) {
                        swapRows(i, i + 1);
                        swapped = true;
                    }
                }
                ++start;
            }
        }

        private void sortTransactionsByCategory() {
            int rowCount = tableModel.getRowCount();
            boolean swapped = true;
            int start = 0;
            int end = rowCount - 1;

            while (swapped) {
                swapped = false;

                for (int i = start; i < end; ++i) {
                    String category1 = (String) tableModel.getValueAt(i, 1);
                    String category2 = (String) tableModel.getValueAt(i + 1, 1);
                    if (category1.compareTo(category2) > 0) {
                        swapRows(i, i + 1);
                        swapped = true;
                    }
                }
                if (!swapped) break;
                --end;
                swapped = false;
                
                for (int i = end - 1; i >= start; --i) {
                    String category1 = (String) tableModel.getValueAt(i, 1);
                    String category2 = (String) tableModel.getValueAt(i + 1, 1);
                    if (category1.compareTo(category2) > 0) {
                        swapRows(i, i + 1);
                        swapped = true;
                    }
                }
                ++start;
            }
        }
        
        private void exit (){
            Home home = new Home();
            home.setVisible(true);
            home.jLabel3.setText("@"+jTextField1.getText());
            home.pack();
            home.setLocationRelativeTo(null);
            frame.dispose();
        }
        
        private void swapRows(int row1, int row2) {
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                Object temp = tableModel.getValueAt(row1, i);
                tableModel.setValueAt(tableModel.getValueAt(row2, i), row1, i);
                tableModel.setValueAt(temp, row2, i);
            }
        }
    }

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
                        currentTransaction.addTransaction(amount, description);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                       JOptionPane.showMessageDialog(null, "Failed to load.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
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
             JOptionPane.showMessageDialog(null, "Failed to load.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

