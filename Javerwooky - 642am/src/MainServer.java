/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Gio
 */
import LCControllers.ClientObject;
import LCControllers.ParseRoute.ParseRoute;
import LCControllers.ChatMessage;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/*
 * The server that can be run both as a console application or a GUI
 */
public class MainServer {

    // a unique ID for each connection
    private static int uniqueId;
    // an ArrayList to keep the list of the Client
    private ArrayList<ClientThread> al;
    // if I am in a GUI
    private MainServerGUI sg;
    // to display time
    private SimpleDateFormat sdf;
    // the port number to listen for connection
    private int port = 1500;
    // the boolean that will be turned of to stop the server
    private boolean keepGoing;

    private ArrayList<ClientObject> alCo = new ArrayList<ClientObject>();

    /*
     *  server constructor that receive the port to listen to for connection as parameter
     *  in console
     */
    public MainServer(MainServerGUI sg) {
        // GUI or not
        this.sg = sg;
        // to display hh:mm:ss
        sdf = new SimpleDateFormat("HH:mm:ss");
        // ArrayList for the Client list
        al = new ArrayList<ClientThread>();
    }

    public void start() {
        keepGoing = true;
        /* create socket server and wait for connection requests */
        try {
            ParseRoute pr = new ParseRoute();
            InetAddress addr = InetAddress.getByName(pr.getLocalIPAddress());
            // the socket used by the server
            ServerSocket serverSocket = new ServerSocket(port, 50, addr);

            // infinite loop to wait for connections
            while (keepGoing) {
                // format message saying we are waiting
                display("Server waiting for Clients on port " + port + " and IP " + addr + ".");

                Socket socket = serverSocket.accept();  	// accept connection
                // if I was asked to stop
                if (!keepGoing) {
                    break;
                }
                ClientThread t = new ClientThread(socket);  // make a thread of it
                al.add(t);									// save it in the ArrayList
                t.start();
            }
            // I was asked to stop
            try {
                serverSocket.close();
                for (int i = 0; i < al.size(); ++i) {
                    ClientThread tc = al.get(i);
                    try {
                        tc.sInput.close();
                        tc.sOutput.close();
                        tc.socket.close();
                    } catch (IOException ioE) {
                        // not much I can do
                    }
                }
            } catch (Exception e) {
                display("Exception closing the server and clients: " + e);
            }
        } // something went bad
        catch (IOException e) {
            String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
            display(msg);
        }
    }
    /*
     * For the GUI to stop the server
     */

    protected void stop() {
        keepGoing = false;
        // connect to myself as Client to exit statement 
        // Socket socket = serverSocket.accept();
        try {
            new Socket("localhost", port);
        } catch (Exception e) {
            // nothing I can really do
        }
    }
    /*
     * Display an event (not a message) to the console or the GUI
     */

    private void display(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        if (sg == null) {
            System.out.println(time);
        } else {
            sg.appendEvent(time + "\n");
        }
    }

    private void addToContactList(ClientObject e) {
        sg.appendContact(e);
    }

    private void deleteFromContactList(ClientObject e) {
        sg.deleteContact(e);
    }
    /*
     *  to broadcast a message to all Clients
     */

    public synchronized void broadcast(ChatMessage message) {
        // add HH:mm:ss and \n to the message
        String time = sdf.format(new Date());
        String messageLf = time + " " + message + "\n";
        // display message on console or GUI
        if (sg == null) {
            System.out.print(messageLf);
        } else {
//            sg.appendRoom(messageLf);     // append in the room window
        }
        // we loop in reverse order in case we would have to remove a Client
        // because it has disconnected
        for (int i = al.size(); --i >= 0;) {
            ClientThread ct = al.get(i);
            ct.writeMessage(message);
            // try to write to the Client if it fails remove it from the list
//            ct.writeMessage(new ChatMessage(ChatMessage.BROADCAST, message));
        }
    }

    // for a client who logoff using the LOGOUT message
    synchronized void remove(int id) {
        // scan the array list until we found the Id
        for (int i = 0; i < al.size(); ++i) {
            ClientThread ct = al.get(i);
            // found it
            if (ct.id == id) {
                al.remove(i);
                return;
            }
        }
    }

    /*
     *  To run as a console application just open a console window and: 
     * > java Server
     * > java Server portNumber
     * If the port number is not specified 1500 is used
     */
    public static void main(String[] args) {
        // start server on port 1500 unless a PortNumber is specified 
        int portNumber = 1500;
        switch (args.length) {
            case 1:
                try {
                    portNumber = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    System.out.println("Invalid port number.");
                    System.out.println("Usage is: > java Server [portNumber]");
                    return;
                }
            case 0:
                break;
            default:
                System.out.println("Usage is: > java Server [portNumber]");
                return;

        }
        // create a server object and start it
//		Server server = new Server(portNumber);
//		server.start();
    }

    public boolean hasUsersConnected() {
        boolean retval = false;
        if (alCo.size() > 0) {
            retval = true;
        }
        return retval;
    }

    /**
     * One instance of this thread will run for each client
     */
    class ClientThread extends Thread {

        // the socket where to listen/talk
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        // my unique id (easier for deconnection)
        int id;
        // the Username of the Client
        String username;
        // the only type of message a will receive
        ChatMessage cm;
        // the date I connect
        String date;

        ClientObject test;
        boolean tKeepGoing = true;

        // Constructore
        ClientThread(Socket socket) {
            // a unique id

            this.socket = socket;
            /* Creating both Data Stream */
            System.out.println("Thread trying to create Object Input/Output Streams");
            if (!socket.isClosed()) {
                id = ++uniqueId;
                try {
                    // create output first
                    sOutput = new ObjectOutputStream(socket.getOutputStream());
                    sInput = new ObjectInputStream(socket.getInputStream());

                } catch (IOException e) {
//                 remove(id);
                    e.printStackTrace();
                    tKeepGoing = false;
                    display("Exception creating new Input/output Streams: " + e);
                    return;
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
                } // have to catch ClassNotFoundException
            } else {
                return;
            }
            date = new Date().toString() + "\n";
        }

        // what will run forever
        public void run() {
            // to loop until LOGOUT

            while (tKeepGoing) {
                if (test == null) {
                    try {
                        test = (ClientObject) sInput.readObject();
                        display(test.getUsername());
                    } catch (IOException ex) {
                        Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
                        break;
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
                        break;
                    }
                    sg.appendEvent(test.getUsername() + " just connected.\n");

                    addToContactList(test);
                    alCo.add(test);
                    for (ClientThread ct : al) {
                        display(ct.test.getUsername());
                        ct.updateOnlineList(new ChatMessage(ChatMessage.UPDATELIST, alCo));
                    }
                }

                // read a String (which is an object)
                try {
                    cm = (ChatMessage) sInput.readObject();
                } catch (IOException e) {
//                    display(username + " Exception reading Streams: " + e);
                    break;
                } catch (ClassNotFoundException e2) {
                    break;
                }
//                break;
                // the messaage part of the ChatMessage
                String message = cm.getMessage();

                // Switch on the type of message receive
                switch (cm.getType()) {

                    case ChatMessage.MESSAGE:
                        for (ClientThread ct : al) {
//                            display("Setting status to: " + message);
//                            display(ct.test.getUsername());
//                            test.setStatus(message);
                            ct.updateOnlineList(new ChatMessage(ChatMessage.MESSAGE, "Olah From main :)"));
                        }
                        break;

                    case ChatMessage.STATUS:
                        for (ClientThread ct : al) {
                            display("Setting status to: " + message);
//                            display(ct.test.getUsername());
                            test.setStatus(message);
                            ct.updateOnlineList(new ChatMessage(ChatMessage.UPDATELIST, alCo));
                        }
                        break;
                    case ChatMessage.LOGOUT:
                        display(username + " disconnected with a LOGOUT message.");
                        tKeepGoing = false;
                        break;
                    case ChatMessage.CLIENTBROADCAST: // NEEDS FIXING//
                        int i = al.size();
                        for (ClientThread ct : al) {
                            System.out.println("CHATMESSAGEARRAY SIZE: " + cm.getList().size() + "CLIENTTSIZE: " + al.size());
                            String user = ct.test.getFullName();
                            Iterator<ClientObject> ite = cm.getList().iterator();
                            do {
                                if (ite.next().getFullName().contentEquals(user)) {
                                    writeMessage(cm);
                                }
                            } while (ite.hasNext());

                        }
                        break;
                }
            }

            // remove myself from the arrayList containing the list of the
            // connected Clients
            remove(id);
            deleteFromContactList(test);

            alCo.remove(test);
            for (ClientThread cT : al) {
                cT.updateOnlineList(new ChatMessage(ChatMessage.UPDATELIST, alCo)); // updates the online list for each client thread
            }
//            display(test.getUsername() + " disconnected.");
            close();

        }

        // try to close everything
        private void close() {
            // try to close the connection
            try {
                if (sOutput != null) {
                    sOutput.close();
                }
            } catch (Exception e) {
            }
            try {
                if (sInput != null) {
                    sInput.close();
                }
            } catch (Exception e) {
            };
            try {
                if (socket != null) {
                    this.socket.close();
                }
            } catch (Exception e) {
            }
        }

        /*
         * Pass the current online list
         */
        //ORIG REFERENCE
//        private boolean updateOnlineList(ArrayList<ClientObject> co) {
//            // if Client is still connected send the message to it
//            if (!socket.isConnected()) {
//                close();
//                return false;
//            }
//            // write the message to the stream
//            try {
//
//                sOutput.writeObject(co);
//                sOutput.reset();
//            } // if an error occurs, do not abort just inform the user
//            catch (IOException e) {
//                display("Error sending message to " + username);
//                display(e.toString());
//            }
//            return true;
//        }
        private boolean updateOnlineList(ChatMessage co) {
            // if Client is still connected send the message to it
            if (!socket.isConnected()) {
                close();
                return false;
            }
            // write the message to the stream
            try {

                sOutput.writeObject(co);
                sOutput.reset();
            } // if an error occurs, do not abort just inform the user
            catch (IOException e) {
                display("Error sending message to " + username);
                display(e.toString());
            }
            return true;
        }


        /*
         * Write a String to the Client output stream
         */
        private boolean writeMsg(String msg) {
            // if Client is still connected send the message to it
            if (!socket.isConnected()) {
                close();
                return false;
            }
            // write the message to the stream
            try {
                sOutput.reset();
                sOutput.writeObject(msg);
            } // if an error occurs, do not abort just inform the user
            catch (IOException e) {
                display("Error sending message to " + username);
                display(e.toString());
            }
            return true;
        }

        private boolean writeMessage(ChatMessage co) {
            // if Client is still connected send the message to it
            if (!socket.isConnected()) {
                close();
                return false;
            }
            // write the message to the stream
            try {

                sOutput.writeObject(co);
                sOutput.reset();
            } // if an error occurs, do not abort just inform the user
            catch (IOException e) {
                display("Error sending message to " + username);
                display(e.toString());
            }
            return true;
        }
    }
}
