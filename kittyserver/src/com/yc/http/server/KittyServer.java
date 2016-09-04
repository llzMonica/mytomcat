package com.yc.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class KittyServer {
	
	

	public static void main(String[] args) throws Exception {
		KittyServer ks = new KittyServer();
		ks.startServer();
	}

	private void startServer() throws Exception {
		ServerSocket ss = null;
		int port = parseServerXml();
		try {
			ss = new ServerSocket(port);
			YcConstants.logger.debug("kitty server is starting, and listening to port "+ ss.getLocalPort() );
		} catch (IOException e) {
			YcConstants.logger.error("kitty server's port "+ port+" is already in use..." );
			return;
		}
		while (true) {
			try {
				Socket s = ss.accept();
				YcConstants.logger.debug("a client "+ s.getInetAddress()+" is connecting to kitty server....");
				TaskService ts=new TaskService(   s   );
				Thread t=new Thread( ts);
				t.start();
			} catch (IOException e) {
				YcConstants.logger.error("client is down, cause:"+ e.getMessage()  );
			}
		}
	}

	/**
	 * ����xml�õ�port
	 * @throws Exception 
	 */
	private int parseServerXml() throws Exception {
		List<Integer> list = new ArrayList<Integer>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // ͨ��DocumentBulderFoctory����XML������
		try {
			DocumentBuilder bulider = factory.newDocumentBuilder(); // ͨ������������һ�����Լ��ز�����XML��DocumentBuilder
			Document doc = bulider.parse(YcConstants.SERVERCONFIG); // ͨ��DocumentBuilder���ز�����һ��XML��.Document�����ʵ��
			NodeList nl = doc.getElementsByTagName("Connector"); // ͨ��Document���Ա��������.����ȡ��Ӧ�ڵ��е�����
			for (int i = 0; i < nl.getLength(); i++) {
				Element node = (Element) nl.item(i);
				list.add(Integer.parseInt(node.getAttribute("port")));
			}
		} catch (Exception e) {
			throw e;
		}
		return list.get(0);
	}

}