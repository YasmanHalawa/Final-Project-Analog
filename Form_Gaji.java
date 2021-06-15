/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Karyawan;

import Koneksi.db_Koneksi;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.text.Document;
import sun.util.logging.PlatformLogger;

/**
 *
 * @author Yasman
 */
public class Form_Gaji extends javax.swing.JFrame {

    private DefaultTableModel model;
    String   nama, jabatan;
    int nik, gapok, transport, gaber;
    /**
     * Creates new form Form_Gaji
     */
    public Form_Gaji() {
        initComponents();
        
        //memberi penamaan pada judul kolom tabel_gaji
        model = new DefaultTableModel();
        tblGaji.setModel(model);
        model.addColumn("Nik");
        model.addColumn("Nama");
        model.addColumn("Jabatan");
        model.addColumn("Gaji Pokok");
        model.addColumn("Transport");
        model.addColumn("Gaji Bersih");
        
        getData();        
    }
    
    public void getData(){
        //menghapus perulangan isi tabel tblGaji        
        model.getDataVector().removeAllElements(); 
        model.fireTableDataChanged();
        try {
            //membuat statement pemanggilan data table tblGaji dari database
            Statement stat = (Statement)db_Koneksi.getKoneksi().createStatement();
            String sql = "Select * FROM tabel_gaji"; 
            ResultSet res = stat.executeQuery(sql);
            
            
            while(res.next()){
               Object[] obj = new Object[6];
                obj[0] = res.getString("Nik");
                obj[1] = res.getString("Nama");
                obj[2] = res.getString("Jabatan");
                obj[3] = res.getString("Gapok");
                obj[4] = res.getString("Transport");
                obj[5] = res.getString("Gaber"); 
                
                model.addRow(obj); //kita +kan struktur ke "Model" tabel yg kita buat.
               
             
            }}catch (SQLException err){
                
             JOptionPane.showMessageDialog(null, err.getMessage());
        }                
    }
    public void loadData(){
            
        nik = Integer.parseInt(txtNik.getText());
        nama = txtNama.getText();
        jabatan = (String) comboxJabatan.getSelectedItem();        
        gapok = Integer.parseInt(txtGapok.getText());
        transport = Integer.parseInt(txtTransport.getText());
        gaber = Integer.parseInt(txtGaber.getText());
    
    }
    public void loadGaji(){
        //ketika klik jabatan di combobox maka langsung diproses besar gajinya
        //
        jabatan = "" + comboxJabatan.getSelectedItem();
        switch(jabatan){
            case "Manager":
                gapok = 5000000;
            break;
            case "Asisten Manager":
                gapok = 4500000;
            break;
            case "Kepala HRD":
                gapok = 4000000;
            break;
            case "Staf Keuangan":
                gapok = 3500000;
            break;
            case "Karyawan":
                gapok = 3000000;
            break;
            case "Office Boy":
                gapok = 2500000;
            break;
        }
        transport = (int) (gapok * 0.1);
        gaber = gapok + transport;
        txtGapok.setText(""+gapok);
        txtTransport.setText(""+transport);
        txtGaber.setText(""+gaber);
    }
    
    public void saveData(){
        loadData();
        try{
            Statement stat = (Statement) db_Koneksi.getKoneksi().createStatement();
            String sql = "INSERT INTO tabel_gaji VALUES ('" + nik +"','"
                    + nama + "','" + jabatan + "','" + gapok + "','"
                    + transport + "','" + gaber + "')";
            PreparedStatement p = (PreparedStatement) db_Koneksi.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            getData();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void Reset(){
        //untuk mengosongkan setiap kolom

        nik = 0;
        nama = "";
        jabatan = "";
        gapok = 0;
        transport = 0;
        gaber = 0;
        txtNik.setText("");
        txtNama.setText(nama);
        txtGapok.setText("");
        txtTransport.setText("");
        txtGaber.setText("");
    }
    
    public void dataSelect(){
        //untuk menampilkan data yang kita pilih 
        int i = tblGaji.getSelectedRow();
        if (i == -i){
            //tidak ada baris terpilih
            return;
        }
        txtNik.setText(""+model.getValueAt(i, 0));
        txtNama.setText(""+model.getValueAt(i, 1));
        comboxJabatan.setSelectedItem(""+model.getValueAt(i, 2));
        txtGapok.setText(""+model.getValueAt(i, 3));
        txtTransport.setText(""+model.getValueAt(i, 4));
        txtGaber.setText(""+model.getValueAt(i, 5));
                
    }
    
    public void updateData(){
        loadData();
        try{
            Statement stat = (Statement) db_Koneksi.getKoneksi().createStatement();
            String sql = "UPDATE tabel_gaji SET nama = '"+nama+"',"
                                           + "Jabatan ='"+jabatan+"',"
                                           + "Gapok ='"+gapok+"',"
                                           + "Transport ='"+transport+"',"
                                           + "Gaber ='"+gaber+"' WHERE nik='"+nik+"'";
            PreparedStatement p = (PreparedStatement) db_Koneksi.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            /*memanggil class getData() agar setelah proses update berhasil data yang telah diubah
            akan ditampilkan pada tabel*/
            getData();
            
            /*memanggil class reset() agar setelah proses update berhasil, maka data yg terdapat
            pada textbox akan dikosongkan*/
            Reset();
            JOptionPane.showMessageDialog(null, "Update Berhasil");
        }catch(SQLException er){
            JOptionPane.showMessageDialog(null, er.getMessage());
        }
    }
    
    public void deleteData(){
        loadData();
    
        //menampilkan pesan konfirmasi OK dan cancel sebelum dilakukan proses 
        //jika user memilih OK maka proses Delete akan dilakukan
        int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data "+nik+"?","Konfirmasi",
                    JOptionPane.OK_CANCEL_OPTION);
        
        if (pesan == JOptionPane.OK_OPTION){
            try{
                Statement stat = (Statement) db_Koneksi.getKoneksi().createStatement();
                String sql = "DELETE FROM tabel_gaji WHERE nik = '"+nik+"'";
                PreparedStatement p = (PreparedStatement) db_Koneksi.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                getData();
                Reset();
                JOptionPane.showMessageDialog(null, "Menghapus Data Berhasil");
            }catch(SQLException er){
                JOptionPane.showMessageDialog(null, er.getMessage());
            }
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        comboxJabatan = new javax.swing.JComboBox<>();
        txtGapok = new javax.swing.JTextField();
        txtTransport = new javax.swing.JTextField();
        txtGaber = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNik = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnExitt = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGaji = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menghitung Gaji");
        setBackground(new java.awt.Color(153, 255, 102));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 255, 102));

        jLabel7.setBackground(new java.awt.Color(153, 255, 153));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setText("Gaji Bersih");

        comboxJabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manager", "Asisten Manager", "Kepala HRD", "Staf Keuangan", "Karyawan", "Office Boy" }));
        comboxJabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboxJabatanActionPerformed(evt);
            }
        });

        txtGapok.setEditable(false);

        txtTransport.setEditable(false);

        txtGaber.setEditable(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setText("NIK");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("Nama");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setText("Jabatan");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel5.setText("Gaji Pokok");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel6.setText("Transport");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtNama, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNik, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboxJabatan, 0, 284, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtGapok)
                    .addComponent(txtTransport)
                    .addComponent(txtGaber, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtGapok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTransport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtGaber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(comboxJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 117, 860, 220));

        jPanel2.setBackground(new java.awt.Color(255, 255, 102));

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDelete)
                .addGap(95, 95, 95))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addComponent(btnReset)
                .addGap(160, 160, 160)
                .addComponent(btnUpdate)
                .addContainerGap(315, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnSave))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 73, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnReset))
                .addGap(48, 48, 48))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 333, 860, 150));

        btnExitt.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnExitt.setText("Keluar");
        btnExitt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExittActionPerformed(evt);
            }
        });
        getContentPane().add(btnExitt, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 770, -1, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton1.setText("Back Data Main");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 770, -1, -1));

        tblGaji.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblGaji.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGajiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGaji);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 482, 860, 260));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Karyawan/gaji.jpg"))); // NOI18N
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 184, 140));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel1.setText("DATA GAJI KE-1");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 329, 50));

        jLabel9.setFont(new java.awt.Font("Tempus Sans ITC", 0, 16)); // NOI18N
        jLabel9.setText("081375335811");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tempus Sans ITC", 0, 16)); // NOI18N
        jLabel10.setText("H1051201048@student.untan.ac.id");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, -1, 20));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 858, 118));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboxJabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboxJabatanActionPerformed
        // TODO add your handling code here:
        loadGaji();
    }//GEN-LAST:event_comboxJabatanActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        saveData();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        Reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateData();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteData();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblGajiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGajiMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_tblGajiMouseClicked

    private void btnExittActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExittActionPerformed
        // TODO add your handling code here:
        System.exit(0);

    }//GEN-LAST:event_btnExittActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.hide();
        DataMain dm = new DataMain();
        dm.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form_Gaji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Gaji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Gaji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Gaji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Gaji().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExitt;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> comboxJabatan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblGaji;
    private javax.swing.JTextField txtGaber;
    private javax.swing.JTextField txtGapok;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNik;
    private javax.swing.JTextField txtTransport;
    // End of variables declaration//GEN-END:variables
}
