
// Byan alkas- project 4
package application;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane; 
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class client extends Application {
	
	private DataOutputStream toServer = null; // IO streams
	private DataInputStream fromServer = null; // IO streams
	private TextArea tServer = new TextArea(); // set text area to display
	private TextArea tClient = new TextArea();
    
	//override the start method
	@Override
	public void start(Stage primaryStage){
			GridPane grid = new GridPane();
			tServer.setEditable(false); // set the other screen so it can show as two screen at the same time. 
			grid.add(new Label("sent"), 0, 0);
			grid.add(new ScrollPane(tServer), 0, 1);
			
			grid.add(new Label("receive"), 0, 2);
			grid.add(new ScrollPane(tClient), 0, 3);
			
			Scene scene = new Scene(grid, 450, 400);
			primaryStage.setTitle("Client chat application(friend)");
			primaryStage.setScene(scene);
			primaryStage.show();	    
	
		new Thread( () -> {
		      try {
		        // Create a server socket
		        ServerSocket serverSocket = new ServerSocket(8000);
		        
		        Socket socket = serverSocket.accept();
		        
		        DataInputStream inputFromServer = new DataInputStream(socket.getInputStream());
		        DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
		    
		        while (true) {
		          
		          String msg = inputFromServer.readUTF(); //receive message from server
		          outputToClient.writeUTF(msg);
		          
		          String msgOut =""; 
		          outputToClient.writeUTF(msgOut);
		    
		          Platform.runLater( () -> { 
		        	  tServer.appendText("You: " + msg + "\n");
		          });
		        }
		      }
		      catch(IOException err) {
		        err.printStackTrace(); // handle exception and error..
		      }
		    }).start();
		 
		 tClient.setOnKeyPressed( e -> {
			 if (e.getCode() == KeyCode.ENTER) { // SEND THE MESSAGE WHEN YOU PRESS ENTER
				 String msg = null;
				 msg = tClient.getText().trim();
				 if(toServer == null) {
					 try {
						 Socket socket = new Socket("localhost", 8080); // create socket to connect to the server. 
						 
						 fromServer = new DataInputStream(socket.getInputStream());
						 toServer = new DataOutputStream(socket.getOutputStream());
						 
						 InetAddress inetAddress = socket.getInetAddress();
					 }
					 catch(Exception er) {
						 er.printStackTrace();
					 }
				 }
				 
				 try {
					 toServer.writeUTF(msg);
					 toServer.flush();
				 }
				 catch(Exception err){
					 err.printStackTrace();
				 }
				 tClient.clear();

			 }
		 });
		  
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
    
