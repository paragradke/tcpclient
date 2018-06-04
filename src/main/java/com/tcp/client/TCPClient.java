package com.tcp.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
  private Socket socket;
  private Scanner scanner;

  private TCPClient(InetAddress serverAddress, int serverPort) throws Exception {
    this.socket = new Socket(serverAddress, serverPort);
    this.scanner = new Scanner(System.in);
  }

  private void start() throws IOException {
    String input;
    DataInputStream dis = new DataInputStream(this.socket.getInputStream());
    DataOutputStream dos = new DataOutputStream(this.socket.getOutputStream());
    while (true) {
      System.out.println(dis.readUTF());
      input = scanner.nextLine();
      dos.writeUTF(input);

      if (input.equals("Exit")) {
        System.out.println("Closing this connection : " + socket);
        String response = dis.readUTF();
        System.out.println(response);
        socket.close();
        break;
      }

      String response = dis.readUTF();
      System.out.println(response);

//      PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
//      out.println(input);
//      out.flush();
    }

    this.scanner.close();
    dis.close();
    dos.close();
  }

  public static void main(String[] args) throws Exception {
    TCPClient client = new TCPClient(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));

    System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
    try {
      client.start();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

}
