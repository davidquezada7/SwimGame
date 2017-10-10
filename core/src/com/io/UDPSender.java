package com.io;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPSender implements Runnable {
	public int end = 0;
	
	//Not changing parameters
	public String ip = null;									//Ip de la maquina a la que vamos a enviar el paquete
	public int port;											//Puerto a enviar el datagrama. Puerto de entrada del servidor
	
	//PACKETS
	public DatagramPacket outgoingPacket;
	public byte parameters[]; 
	public DatagramSocket socket;
	public InetAddress host;
	
	//TYPE
	public int type = 0;
	public String parametersString;
	
	//POSITIOIN
	public float posX;
	public float posY;
	
	//IMPULSE
	public float impulseX;
	public float impulseY;
	
	public UDPSender(int port,String ip){
		
		this.port = port;
		this.ip = ip;
		
		try {
			host = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		
		switch(type){
			case 0:
				start();
				break;
			case 1:
				impulse();
				break;
			case 2:
				position();
				break;
			case 3:
				end();
				break;
		}
			try {
				socket  = new DatagramSocket();
				outgoingPacket = new DatagramPacket(parameters,parameters.length,host,port);
				socket.send(outgoingPacket);
				
				parametersString = null;
				parameters = null;
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}
	public void end(){
		parametersString = Integer.toString(type).toString();
		parametersString = parametersString +" " + Integer.toString(end).toString();
		parameters = parametersString.getBytes();
	}
	public void start(){
		
	}
	
	public void impulse(){
		parametersString = Integer.toString(type).toString();
		parametersString = parametersString +" " + Float.toString(impulseX).toString();
		parametersString = parametersString +" " + Float.toString(impulseY).toString();
		parameters = parametersString.getBytes();
	}
	
	public void position(){
		parametersString = Integer.toString(type).toString();
		parametersString = parametersString +" " + Float.toString(posX).toString();
		parametersString = parametersString +" " + Float.toString(posY).toString();
		parameters = parametersString.getBytes();
	}
}
