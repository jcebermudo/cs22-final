// https://www.youtube.com/watch?v=yKrd7ddrjyc

import java.io.*;
import java.net.*;

public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;

    public GameServer() {
        System.out.println("=== GAME SERVER ===");
        numPlayers = 0;
        maxPlayers = 2;

        try {
            ss = new ServerSocket(1000);
        } catch (IOException ex) {
            System.out.println("IOException from Gameserver constructor");
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");

            while(numPlayers < maxPlayers) {
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
            }
        } catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");

        }
    }
}