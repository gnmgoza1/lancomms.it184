/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LCViews;

import LCControllers.Call;
import LCControllers.ChatMessage;
import LCControllers.Client;
import LCControllers.ClientObject;
import LCModels.Message;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import static javax.swing.JComponent.WHEN_FOCUSED;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 *
 * @author Gio
 */
public class ChatWindowUI extends javax.swing.JFrame {

    /**
     * Creates new form ChatWindowUI
     */
    private String userNameTo;
    private Call call;
    private ClientObject fromCo;
    private ClientObject toCo;
    private Client client;
    private String toServer;
    private int toPort;
    private MainUI mainui;

    private Socket socket;
    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private boolean sentSelf = false;
    private boolean isCallInstanced = false;
    private boolean isCallDisabled = false;

    public ChatWindowUI(String username, String s, int p) {
        toServer = s;
        toPort = p;
        this.userNameTo = username;
        initComponents();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                disconnect();//cierra aplicacion
            }
        });
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setResizable(false);
    }

    public ChatWindowUI(ClientObject to, ClientObject from) {
        toCo = to;
        fromCo = from;
        userNameTo = to.getUsername();

        //connect to a client
        try {
            socket = new Socket(toCo.getServer(), toCo.getPort());
            System.out.println(socket.toString());
            System.out.println("I : " + from.getUsername() + " Will SEND TO SERVER: " + toCo.getServer() + " PORT: " + toCo.getPort());
            try {
                sInput = new ObjectInputStream(socket.getInputStream());
                sOutput = new ObjectOutputStream(socket.getOutputStream());

            } catch (IOException ex) {
                System.out.println("Exception creating new Input/output Streams: " + ex);
                return;
            }
        } catch (IOException ex) {
            System.out.println("Exception creating new Input/output Streams: " + ex);
            return;
        }
        System.out.println("Chat window created by doubleclick");
        initComponents();
        senderDetails(fromCo);
        this.setLocationByPlatform(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public void setMainUI(MainUI mui) {
        this.mainui = mui;
    }

    public void setSendDisabled() {
        sendButton.setEnabled(false);
        message.setEditable(false);
    }

    public void disconnect() {
        sendLogoutToChatter();
        try {
            if (sInput != null) {
                sInput.close();
            }
        } catch (Exception e) {
        } // not much else I can do
        try {
            if (sOutput != null) {
                sOutput.close();
            }
        } catch (Exception e) {
        } // not much else I can do
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        } // not much else I can do

        // inform the GUI
        if (this != null) {
            this.dispose();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        chat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        message = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        videoCall = new javax.swing.JButton();
        connectionStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        chat.setEditable(false);
        chat.setColumns(20);
        chat.setLineWrap(true);
        chat.setRows(5);
        chat.setWrapStyleWord(true);
        jScrollPane1.setViewportView(chat);

        message.setColumns(20);
        message.setLineWrap(true);
        message.setRows(5);
        message.setWrapStyleWord(true);
        // start our set up of key bindings

        // to get the correct InputMap
        int condition = WHEN_FOCUSED;
        // get our maps for binding from the chatEnterArea JTextArea
        InputMap inputMap = message.getInputMap(condition);
        ActionMap actionMap = message.getActionMap();

        // the key stroke we want to capture
        KeyStroke enterStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        // tell input map that we are handling the enter key
        inputMap.put(enterStroke, enterStroke.toString());

        // tell action map just how we want to handle the enter key
        actionMap.put(enterStroke.toString(), new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        jScrollPane2.setViewportView(message);

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lancomms/pp2.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText(toCo.getName());

        videoCall.setText("Video Call");
        videoCall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                videoCallActionPerformed(evt);
            }
        });

        connectionStatus.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(videoCall)
                        .addGap(105, 105, 105)
                        .addComponent(connectionStatus))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(38, 38, 38)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectionStatus)
                    .addComponent(videoCall))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        // TODO add your handling code here:
        ChatMessage cmsg = new ChatMessage(ChatMessage.MESSAGE, message.getText());
        if (message.getText().length() > 0) {
            append(fromCo.getUsername() + ": " + message.getText() + "\n");
        }
        message.setText("");

        if (sentSelf == false) {
            senderDetails(fromCo);
        }
        sendMessage(cmsg);
        return;

    }//GEN-LAST:event_sendButtonActionPerformed

    public void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
            if (msg.getType() == 1) {
                Message mlog = new Message();
                mlog.messageTime(fromCo.getMyId(), msg.getMessage(), toCo.getMyId());
            }
        } catch (IOException e) {
            System.out.println("Exception writing to contact's server: " + e);
        }
    }

    public void sendLogoutToChatter() {

        try {
            sOutput.writeObject(new ChatMessage(ChatMessage.LOGOUT, "Bye"));
        } catch (IOException e) {
            System.out.println("Exception writing to server: " + e);
        }
    }

    public void sendResponse(ChatMessage cmsg) {
        if (sentSelf == false) {
            senderDetails(fromCo);
        }
        sendMessage(cmsg);
    }

    boolean senderDetails(ClientObject msg) {

        try {
            sOutput.writeObject(msg);
            sOutput.reset();
        } catch (IOException e) {
            System.out.println("Exception writing to server: " + e);
        }
        sentSelf = true;
        System.out.println("Sent myself");
        return true;
    }

    private void videoCallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_videoCallActionPerformed
        try {
            // TODO add your handling code here:
            if (!isCallDisabled) {
                int callPort = toCo.getPort();
                if (isCallInstanced == false) {
                    call = new Call(toCo.getServer(), Integer.toString(callPort), Integer.toString(fromCo.getPort()), this, toCo.getUsername());
                }
                this.isCallInstanced = true; //associate instance to chatwindow
                ChatMessage cmsg = new ChatMessage(ChatMessage.CALL, "call");
                append("\nAttempting to call " + toCo.getUsername() + "\n");
                if (sentSelf == false) {
                    senderDetails(fromCo);
                }
                sendMessage(cmsg);
                disableCall();
            }
        } catch (Exception ex) {
            Logger.getLogger(ChatWindowUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_videoCallActionPerformed

    public boolean isCallInstanced() {
        return isCallInstanced;
    }

    public Call getCallInstance() {
        return call;
    }

    public void setCallInstance() {
        isCallInstanced = true;
    }

    public void setCallDisabled() {
        isCallDisabled = true;
        videoCall.setEnabled(false);
    }

    public void setCallEnabled() {
        isCallDisabled = false;
        videoCall.setEnabled(true);
    }

    public void disableCall() {
        mainui.setCallDisabled();
    }

    public void enableCall() {
        mainui.setCallEnabled();
    }

    public String getUsername() {
        return userNameTo;
    }

    public int getId() {
        return fromCo.getMyId();
    }

    public int getToId() {
        return toCo.getMyId();
    }

    public JTextArea getMessageArea() {
        return chat;
    }

    public void setMessageArea(JTextArea message) {
        this.chat = message;
    }

    public void append(String str) {
        chat.append(str);
        chat.setCaretPosition(chat.getText().length() - 1);
    }
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chat;
    private javax.swing.JLabel connectionStatus;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea message;
    private javax.swing.JButton sendButton;
    private javax.swing.JButton videoCall;
    // End of variables declaration//GEN-END:variables

}
