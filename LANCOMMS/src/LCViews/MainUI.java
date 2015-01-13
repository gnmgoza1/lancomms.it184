/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LCViews;

import LCControllers.Contacts;
import LCControllers.Login;
import LCControllers.Session;
import com.sun.security.auth.module.NTSystem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

/**
 *
 * @author user
 */
public class MainUI extends JFrame {

    Session sess;
    int userId;
    /**
     * Creates new form MainUI
     */
    public MainUI(int uid) {
        sess = new Session(uid);
        userId = sess.getId();
        initComponents();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setExtendedState(JFrame.ICONIFIED);
                }
            });       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainTabs = new javax.swing.JTabbedPane();
        ContactsList = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Contacts contact = new Contacts();
        TableColumn idColumn = null;
        jTable1 = new javax.swing.JTable(contact.displayContacts(userId));
        ConvoList = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        UserNameDisplay = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        LancommsMenu = new javax.swing.JMenu();
        settingsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        logoutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ContactsList.setForeground(new java.awt.Color(255, 255, 255));

        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.setInheritsPopupMenu(true);
        jTable1.setIntercellSpacing(new java.awt.Dimension(2, 8));
        jTable1.setRowHeight(25);
        jTable1.setRowMargin(8);
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jTable1.setRowSelectionAllowed(true);
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e){
                int row = jTable1.getSelectedRow();
                Object data = (Object)jTable1.getModel().getValueAt(row, 0);
                int idrow = Integer.parseInt(data.toString());
                Object data2 = (Object)jTable1.getModel().getValueAt(row, 1);
                String namerow = data2.toString();
                System.out.println(idrow);
                System.out.println(namerow);
            }
        });
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JTable theTable = (JTable) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = theTable.rowAtPoint(mouseEvent.getPoint());
                    if (index >= 0) {
                        ChatWindowUI cw = new ChatWindowUI();
                        cw.show();
                    }
                }
            }
        };
        jTable1.addMouseListener(mouseListener);
        idColumn = jTable1.getColumnModel().getColumn(0);
        jTable1.removeColumn(idColumn);
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout ContactsListLayout = new javax.swing.GroupLayout(ContactsList);
        ContactsList.setLayout(ContactsListLayout);
        ContactsListLayout.setHorizontalGroup(
            ContactsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContactsListLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ContactsListLayout.setVerticalGroup(
            ContactsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContactsListLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        MainTabs.addTab("Contacts", ContactsList);

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setToolTipText("");
        jScrollPane2.setViewportView(jList2);

        javax.swing.GroupLayout ConvoListLayout = new javax.swing.GroupLayout(ConvoList);
        ConvoList.setLayout(ConvoListLayout);
        ConvoListLayout.setHorizontalGroup(
            ConvoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
        );
        ConvoListLayout.setVerticalGroup(
            ConvoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
        );

        MainTabs.addTab("Conversations", ConvoList);

        NTSystem NTSystem = new NTSystem();//test
        UserNameDisplay.setText(System.getProperty("user.name"));

        LancommsMenu.setText("LANCOMMS");

        settingsMenuItem.setText("Settings");
        settingsMenuItem.setIconTextGap(1);
        settingsMenuItem.setInheritsPopupMenu(true);
        settingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsMenuItemActionPerformed(evt);
            }
        });
        LancommsMenu.add(settingsMenuItem);

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        LancommsMenu.add(aboutMenuItem);

        logoutMenuItem.setText("Log Out");
        logoutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutMenuItemActionPerformed(evt);
            }
        });
        LancommsMenu.add(logoutMenuItem);

        jMenuBar1.add(LancommsMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainTabs)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(UserNameDisplay)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(UserNameDisplay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(MainTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void settingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsMenuItemActionPerformed
        new SettingsUI();
    }//GEN-LAST:event_settingsMenuItemActionPerformed

    private void logoutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        this.dispose();
        Login logoutTime = new Login();
        logoutTime.logoutTime(userId);
        LoginUI loggedout = new LoginUI();
        loggedout.setVisible(rootPaneCheckingEnabled);
    }                                            
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        AboutUI about = new AboutUI();
        about.setVisible(true);
    }                                            
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ContactsList;
    private javax.swing.JPanel ConvoList;
    private javax.swing.JMenu LancommsMenu;
    private javax.swing.JTabbedPane MainTabs;
    private javax.swing.JLabel UserNameDisplay;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JList jList2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem logoutMenuItem;
    private javax.swing.JMenuItem settingsMenuItem;
    // End of variables declaration//GEN-END:variables
     
    /*public static void main(String[] args) {
        // TODO code application logic here
        new MainUI(); 
    }*/
}