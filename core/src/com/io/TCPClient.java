package com.io;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient implements Runnable {
	public Socket clientSocket;  
    public DataOutputStream outputStream;
    public BufferedReader bfreader;
    public String ipServer;
	
	public TCPClient(String ipServer, int port){
		this.ipServer = ipServer;
		try {
			
			clientSocket = new Socket(ipServer, port);
	        outputStream = new DataOutputStream(clientSocket.getOutputStream());
	        bfreader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        
		}catch(UnknownHostException e) {
	            System.err.println("Don't know the host");
	    } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection");
	    }
	}
	
	public void run(){
		
		if(clientSocket.isConnected()){
			System.out.println("Conectado");
			try {
            	String word = null;
            	Scanner keyboard = new Scanner(System.in);
            	String respuesta;

                word = keyboard.nextLine();
                while (!word.equals("exit")){
                	System.out.println("cliente:" + word);
        			outputStream.writeBytes(word+"\n");
                    respuesta = bfreader.readLine();
                    System.out.println("Server: " + respuesta);
                    word = keyboard.nextLine();
                }
        		outputStream.close();
        		bfreader.close();
                clientSocket.close();   
                keyboard.close();
            } catch (UnknownHostException e) {
                System.err.println("Trying to connect to unknown host: " + e);
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
		}
		
	}
}
