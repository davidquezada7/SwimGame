package com.io;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.StringTokenizer;

public class UDPListener implements Runnable {
	public int win = 0;
	
	//TYPE
	public int type = 0;
	
	//SOURCE
	public int port;								//Es el puerto en el que el servidor escucha los Datagramas
	public String incomingParams;
	public DatagramSocket listenerSocket;
	byte[] buffer = new byte[512];					//Este es buffer del datagrama que se recibira. 
	//START
	public int timetoStart = -1;
	public boolean refreshStart = false;
	//IMPULSE AUXILIAR
	public float impulseX;
	public float impulseY;
	
	//POSITION
	public float posX;
	public float posY;
	
	//UPDATE?
	public boolean refreshImpulse = false;		
	public boolean refreshPosition = false;
	public boolean refreshWin = false;
	
	public UDPListener(int port){
		this.port = port;
		try {
			listenerSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		
			
			try {
				
				while(true){
					DatagramPacket incomingPacket = new DatagramPacket(buffer,buffer.length);
					
					//WAITING FOR A PACKET
					listenerSocket.receive(incomingPacket);
					buffer = incomingPacket.getData();
					
					//BUFFER TO STRING
					incomingParams = new String(incomingPacket.getData(),0,incomingPacket.getLength());
					
					
					//SELECT THE TYPE OF PACKET
					type = getType(incomingParams);
					switch(type){
						case 0:
							start(incomingParams);
							refreshStart = true;
							break;
						case 1:
							impulse(incomingParams);
							refreshImpulse = true;
							break;
						case 2:
							position(incomingParams);
							refreshPosition = true;
							break;
						case 4:
							win(incomingParams);
							refreshWin = true;
							break;
			
					}
			
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	
	public void win(String message){
		StringTokenizer strTokenizer = new StringTokenizer(message);
		try{
			int type = Integer.parseInt(strTokenizer.nextToken());
			win = Integer.parseInt(strTokenizer.nextToken());
			System.out.println("usted " + win);
			
		}catch(Exception e){
			System.out.println("exception del metodo start"+e);
		}
	}
	
	public void start(String message){
		StringTokenizer strTokenizer = new StringTokenizer(message);
		try{
			int type = Integer.parseInt(strTokenizer.nextToken());
			timetoStart = Integer.parseInt(strTokenizer.nextToken());
			
		}catch(Exception e){
			System.out.println("exception del metodo start"+e);
		}
	}
	
	public void impulse(String message){
		StringTokenizer strTokenizer = new StringTokenizer(message);
		try{
			int type = Integer.parseInt(strTokenizer.nextToken());
			impulseX = Float.parseFloat(strTokenizer.nextToken());
			impulseY = Float.parseFloat(strTokenizer.nextToken());
			
		}catch(Exception e){
			System.out.println("exception del metodo impulse"+e);
		}
				
	}
	
	public void position(String message){
		StringTokenizer strTokenizer = new StringTokenizer(message);
		try{
			int type = Integer.parseInt(strTokenizer.nextToken());
			posX = Float.parseFloat(strTokenizer.nextToken());
			posY = Float.parseFloat(strTokenizer.nextToken());	
		}catch(Exception e){
			System.out.println("Metodo positioin "+e);
		}
		
	}
	
	public int getType(String message){
		StringTokenizer strTokenizer = new StringTokenizer(message);
		int type = Integer.parseInt(strTokenizer.nextToken());
		return type;
	}
}

