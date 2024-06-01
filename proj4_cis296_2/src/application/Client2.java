// Byan Alkas - project 4
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

	public class Client2 extends Application{
		
		private DataOutputStream toServer = null; // IO streams
		private DataInputStream fromServer = null; // IO streams
		private TextArea tServer = new TextArea(); // set text area to display
		private TextArea tClient = new TextArea();
		
		
		@Override
		public void start(Stage primaryStage){ //override the start method
			GridPane grid = new GridPane();
			tServer.setEditable(false); // set the other screen so it can show as two screen at the same time. 
			grid.add(new Label("received"), 0, 0); // set the name of receiver message. 
			grid.add(new ScrollPane(tServer), 0, 1);
			grid.setStyle("color: green");
			
			grid.add(new Label("Sent"), 0, 2); // set the label name of the send screen.
			grid.add(new ScrollPane(tClient), 0, 3);
			
			Scene scene = new Scene(grid, 450, 400);
			primaryStage.setTitle("Client2 chat application (You)");
			primaryStage.setScene(scene); // place the scene in the stage. 
			primaryStage.show(); // display the stage
			
			new Thread( () -> {
				 try {
				        ServerSocket serverSocket = new ServerSocket(8080); // Create a server socket
				        Socket socket = serverSocket.accept(); // Listen for a connection request.. 
				        
				        DataInputStream inputFromServer = new DataInputStream(socket.getInputStream()); // create data input stream .
				        DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream()); // create data output stream.
				    
				        while (true) {
				          
				          String msg = inputFromServer.readUTF(); //receive message from server
				          outputToClient.writeUTF(msg); // send message back to client 1 (You)
				    
				          Platform.runLater( () -> { 
				        	  tServer.appendText("Friend: " + msg + "\n"); // set up the way to send the message.. 
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
							 Socket socket = new Socket("localhost", 8000); // create socket to connect to the server. 
							 
							 fromServer = new DataInputStream(socket.getInputStream()); // create input stream to receive data from the server
							 toServer = new DataOutputStream(socket.getOutputStream()); // create output stream to send data from the server
						}
						 catch(Exception er) {
							 er.printStackTrace(); // handle errors. 
						 }
					 }
					 
					 try {
						 toServer.writeUTF(msg); // write string and send data to client 1
						 toServer.flush(); // clear the writer after its written
					 }
					 catch(Exception err){
						 err.printStackTrace();
					 }
					 tClient.clear(); // clear the text after pressing enter 
				 }
				 });
			 
		}
		
		public static void main(String[] args) {
			Application.launch(args); 
		} // end main. 
	} 

			
