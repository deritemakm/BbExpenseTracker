package bbtracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class BubbleUp extends JFrame {
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private SimpleDateFormat dateFormat;

    public BubbleUp() {
        setTitle("Bubbles Up Day");
        setSize(800, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/icon_img.png")));
        setBackground(Color.WHITE);

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tableModel.addColumn("Week");
        tableModel.addColumn("Pass");
        tableModel.addColumn("Highest Amount");
        tableModel.addColumn("Comparisons");

        transactionTable = new JTable(tableModel);
        transactionTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        transactionTable.setBackground(Color.WHITE);

        JTableHeader header = transactionTable.getTableHeader();
        header.setFont(new Font("Century Gothic", Font.BOLD, 20));

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) transactionTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        transactionTable.setRowHeight(30);
        transactionTable.setIntercellSpacing(new Dimension(10, 5));
        transactionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        
        int[] columnWidths = {100, 100, 200, 400}; 
        for (int i = 0; i < transactionTable.getColumnCount(); i++) {
            transactionTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JScrollPane scrollTable = new JScrollPane(transactionTable);
        scrollTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollTable, BorderLayout.CENTER);
        
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

       
        setLocationRelativeTo(null);
    }

   public void displayOutput(List<Transaction> transactions) {
    int batchSize = 7;
    int numberOfBatches = (int) Math.ceil((double) transactions.size() / batchSize);

    for (int batchIndex = 0; batchIndex < numberOfBatches; batchIndex++) {
        int startIdx = batchIndex * batchSize;
        int endIdx = Math.min(startIdx + batchSize, transactions.size());
        List<Transaction> batch = transactions.subList(startIdx, endIdx);

        int n = batch.size();
        Transaction[] B = batch.toArray(new Transaction[0]);

        // Magnetic Bubble Sort
        int i = 0;
        int x = n;

        while (i < n) {
            int q = 0;
            int r = 0;
            int j = 0;
            boolean swapped = false;

            while (j < x - 1) {
                if (B[q].getAmount() < B[j + 1].getAmount()) {
                    Transaction temp = B[q];
                    B[q] = B[j + 1];
                    B[j + 1] = temp;
                    r = q + 1;
                    q++;
                    swapped = true;
                } else if (B[q].getAmount() == B[j + 1].getAmount()) {
                    r = j + 1;
                } else {
                    r = j + 1;
                    q = j + 1;
                }
                j++;
            }

            if (!swapped) {
                break; 
            }

            int m = (r - q) + 1;
            x = x - m;
            i++;
        }

        // Display sorted transactions
        for (int k = 0; k < n; k++) {
            Date transactionDate = B[k].getDate();
            double transactionAmount = B[k].getAmount();
            String comparisonsOutput = generateComparisonsOutput(batch, B, (int) transactionAmount);

            tableModel.addRow(new Object[]{
                    batchIndex + 1,
                    k + 1,
                    transactionAmount + " (" + dateFormat.format(transactionDate) + ")", // Include date in the amount column
                    comparisonsOutput
            });

            adjustRowHeight(transactionTable, transactionTable.getRowCount() - 1, 100);
        }
    }
}
    private String generateComparisonsOutput(List<Transaction> batch, Transaction[] B, int highestInPass) {
        StringBuilder sb = new StringBuilder();

        sb.append("<html>");
        sb.append("<style>");
        sb.append("ul { list-style-type: none; padding: 0; }");
        sb.append("li { margin: 5px 0; }");
        sb.append(".less { color: green; }");
        sb.append(".greater { color: red; }");
        sb.append(".equal { color: blue; }");
        sb.append("</style>");
        sb.append("<ul>");
        for (int k = 0; k < B.length; k++) {
            sb.append("<li>");
            sb.append("Date: ").append(dateFormat.format(batch.get(k).getDate())).append(" - ");
            if (B[k].getAmount() < highestInPass) {
                sb.append("<span class='less'>").append(B[k].getAmount()).append(" &#9660; ").append(highestInPass).append(" (less by ").append(highestInPass - B[k].getAmount()).append(")</span>");
            } else if (B[k].getAmount() > highestInPass) {
                sb.append("<span class='greater'>").append(B[k].getAmount()).append(" &#9650; ").append(highestInPass).append(" (greater by ").append(B[k].getAmount() - highestInPass).append(")</span>");
            } else {
                sb.append("<span class='equal'>").append(B[k].getAmount()).append(" &#61; ").append(highestInPass).append(" (equal)</span>");
            }
            sb.append("</li>");
        }
        sb.append("</ul></html>");

        return sb.toString();
    }


    public static List<Transaction> readTransactionsFromFile(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Transaction:")) {
                    int transactionNumber = Integer.parseInt(line.split(" ")[1]);
                    String dateLine = br.readLine();
                    Date date = dateFormat.parse(dateLine.trim());
                    String amountLine = br.readLine(); 
                    if (amountLine != null) {
                        double amount = Double.parseDouble(amountLine.split(", ")[0]);
                        transactions.add(new Transaction(transactionNumber, amount, date));
                    }
                    br.readLine();
                }
            }
        } catch (IOException | java.text.ParseException e) {
            JOptionPane.showMessageDialog(null,
                    "No Transactions found.",
                    "Message",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        return transactions;
    }

    static class Transaction {
        private int transactionNumber;
        private double amount;
        private Date date;

        public Transaction(int transactionNumber, double amount, Date date) {
            this.transactionNumber = transactionNumber;
            this.amount = amount;
            this.date = date;
        }

        public int getTransactionNumber() {
            return transactionNumber;
        }

        public double getAmount() {
            return amount;
        }

        public Date getDate() {
            return date;
        }
    }

    private void adjustRowHeight(JTable table, int row, int columnWidth) {
        int height = table.getRowHeight();

        for (int column = 0; column < table.getColumnCount(); column++) {
            TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(cellRenderer, row, column);
            height = Math.max(height, comp.getPreferredSize().height);
        }

        table.setRowHeight(row, height);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BubbleUp gui = new BubbleUp();
            gui.setVisible(true);
            String username = Login.jTextField1.getText();
            String fileName = username + "_expenses.txt";
            List<Transaction> transactions = readTransactionsFromFile(fileName);

            if (transactions.isEmpty()) {
                JOptionPane.showMessageDialog(gui, "No transactions found.", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                gui.displayOutput(transactions);
            }
        });
    }
}
