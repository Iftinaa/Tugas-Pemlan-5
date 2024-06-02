import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TabelDataBaseMahasiswa extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public TabelDataBaseMahasiswa() {
        //Mengatur Frame
        setTitle("TABEL DATA BASE MAHASISWA");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Membuat panel utama dengan latar belakang berwarna biru
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.PINK); // Menambahkan warna latar belakang biru

        //Membuat label dan font
        JLabel label = new JLabel("Tabel Data Base Mahasiswa", SwingConstants.CENTER);
        label.setFont(new Font("Times New Roman", Font.BOLD, 22));
        label.setForeground(Color.black); // Mengatur warna teks menjadi putih
        panel.add(label, BorderLayout.NORTH);

        //Membuat tabel
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        //Membuat tombol untuk menampilkan tabel
        JButton button = new JButton("TAMPILKAN TABEL");
        panel.add(button, BorderLayout.SOUTH);

        //Menambahkan action listener untuk tombol
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTableData();
            }
        });

        //Menetapkan panel sebagai konten utama frame
        getContentPane().setBackground(Color.BLUE); 
        add(panel);
    }

    private void loadTableData() {
        //Memasukkan data base
        String filePath = "C:\\Users\\ZenBook2\\Downloads\\Tugas smt2\\mahasiswa pindahan.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            tableModel.setRowCount(0); 
            tableModel.setColumnCount(0); 
            boolean isFirstRow = true;
            boolean isSecondRow = false;

            //Membaca file perbaris, memisahkan dengan |, dan memasukkan ke dalam tabel
            while ((line = br.readLine()) != null) {
                if (line.startsWith("|")) {
                    String[] data = line.split("\\|");
                    for (int i = 0; i < data.length; i++) {
                        data[i] = data[i].trim(); 
                    }
                    if (isFirstRow) {
                        for (int i = 1; i < data.length - 1; i++) {
                            tableModel.addColumn(data[i]);
                        }
                        isFirstRow = false;
                        isSecondRow = true;
                    } else if (isSecondRow) {
                        isSecondRow = false;
                    } else {
                        if (data.length > 1) {
                            Object[] rowData = new Object[data.length - 2];
                            System.arraycopy(data, 1, rowData, 0, data.length - 2);
                            tableModel.addRow(rowData);
                        }
                    }
                }
            }
            //Jika terjadi kesalahan saat membaca file
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            //Membuat objek Tabel Data Base Mahasiswa dan menampilkannya
            @Override
            public void run() {
                new TabelDataBaseMahasiswa().setVisible(true);
            }
        });
    }
}
