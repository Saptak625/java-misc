package com.saptakdas.misc;

import com.saptakdas.util.UtilityClass;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.awt.*;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class EmailNotifier {
    public static LinkedList<Integer> usedUIDs=new LinkedList<>();
    public static void main(String[] args) throws InterruptedException {
        String user = UtilityClass.input("Enter email address: ");
        String password = UtilityClass.input("Enter password: ");
        while(true) {
            getEmails(user, password);
            TimeUnit.SECONDS.sleep(15);
        }
    }

    public static void getEmails(String user, String password){
        // create properties field
        Properties properties = System.getProperties();
        properties.setProperty("mail.imaps.host", "imap.gmail.com");
        properties.setProperty("mail.imaps.port", "993");
        properties.setProperty("mail.imaps.connectiontimeout", "5000");
        properties.setProperty("mail.imaps.timeout", "5000");
        try {
            Session session = Session.getDefaultInstance(properties, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", user, password);
            Folder emailFolder = store.getFolder("[Gmail]/All Mail");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
            Message[] messages = emailFolder.search(unseenFlagTerm);
            System.out.println(messages.length);
            System.out.println(usedUIDs);
            for (Message m: messages) {
                System.out.println(m.getMessageNumber());
                if(!EmailNotifier.usedUIDs.contains(m.getMessageNumber())) {
                    EmailNotifier.usedUIDs.addLast(m.getMessageNumber());
                    System.out.println(m.toString());
                    System.out.println(m.getSubject());
                    System.out.println(m.getFolder());
                    System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
                    displayTray(m.getSubject(), m.getFrom());
                }
            }
            // close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (AuthenticationFailedException e){
            System.out.println("Email or password is incorrect. Please try again.");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayTray(String subject, Address[] recipient) throws AWTException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);
        StringBuilder recipientString = new StringBuilder();
        for (Address a: recipient) {
            recipientString.append(a.toString());
        }
        trayIcon.displayMessage(subject, "From: "+recipientString, TrayIcon.MessageType.INFO);
    }
}