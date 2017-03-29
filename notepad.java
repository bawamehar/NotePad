package calculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class notepad extends Application {
	TextArea ta = new TextArea();
	Scanner sc = new Scanner(System.in);
	public static void main(String args[]){
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setTitle("NotePad");
		
		ta = new TextArea();
		
		Menu topmenu1 = new Menu("file");
		Menu topmenu2 = new Menu("Edit");
		Menu topmenu3 = new Menu("Format");
		Menu topmenu4 = new Menu("Help");
		
		MenuItem neww = new MenuItem("New");
		MenuItem open = new MenuItem("open");
		MenuItem save = new MenuItem("Save");
		MenuItem exit = new MenuItem("Exit");
		MenuItem print = new MenuItem("print setup");
		

		MenuItem cut = new MenuItem("Cut");
		MenuItem copy = new MenuItem("Copy");
		MenuItem paste = new MenuItem("paste");
		MenuItem delete = new MenuItem("Delete");

		CheckMenuItem word = new CheckMenuItem("Word Wrap");
		MenuItem font = new MenuItem("Font");
						
		MenuItem about = new MenuItem("About");
		
		topmenu1.getItems().addAll(neww,open,save,exit,print);
		topmenu2.getItems().addAll(cut,copy,paste,delete);
		topmenu3.getItems().addAll(word,font);
		topmenu4.getItems().add(about);

		open.setOnAction(e->{ open();});
		cut.setOnAction(e-> ta.cut());
		copy.setOnAction(e-> ta.copy());
		paste.setOnAction(e-> ta.paste());
		delete.setOnAction(e-> ta.clear());
		save.setOnAction(e-> {savefile();});
		word.setOnAction(e-> ta.setWrapText(true));
		neww.setOnAction(e-> {neww();});
		exit.setOnAction(e-> {exit();});
		about.setOnAction(e-> {about();});
		MenuBar menubar= new MenuBar();
		menubar.getMenus().addAll(topmenu1,topmenu2,topmenu3,topmenu4);
		
		BorderPane bp = new BorderPane();
		bp.setTop(menubar);
		bp.setCenter(ta);
		Scene scene = new Scene(bp,400,400);
		arg0.setScene(scene);
		arg0.show();
		
		
	}
	
	private void about() {
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Sab Batadu tujhe?");
		alert.setContentText("Mujhe Khud kuch ghanta aata hai Google karle");
		alert.showAndWait();
	}

	private void open() {
		{
			String filename;
			System.out.println("enter filename ");
			filename= sc.nextLine();
			readFile(filename);
		}		
	}

	private void neww() {
		{ 
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setContentText("Are you Sure you want to continue?");
			Optional<ButtonType> result=alert.showAndWait();
			if (result.get()==ButtonType.OK){
				ta.setText("");
			}
			else
			{
				alert.close();
			}}		
	}

	private void exit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Exiting Editor");
		alert.setContentText("Are you sure you want to exit?");
		ButtonType save = new ButtonType("Save");
		ButtonType dontsave = new ButtonType("Don't Save");
		ButtonType cancel = new ButtonType("Cancel");
		alert.getButtonTypes().setAll(save,dontsave,cancel);
		Optional<ButtonType> result= alert.showAndWait();
		if(result.get()==save){
			savefile();
		}
		if(result.get()==dontsave) {
			alert.close();
			System.exit(0);
			}
		
			
	}

	public void savefile() {
		TextInputDialog dialog= new TextInputDialog();
		dialog.setTitle("Save As");
		dialog.setHeaderText("Save the file");
		dialog.setContentText("enter the name for the file");
		Optional<String> result= dialog.showAndWait();
		if(result.isPresent())
		{
			String filename = result.get();
				try{
						savetofile(filename,ta);
				} catch(Exception e1){
					e1.printStackTrace();
				}
		}
	} 

	public void savetofile(String filename, TextArea ta) throws Exception {
		String content = ta.getText();
		FileOutputStream fop = null;
		File file;
		try {

			file = new File(filename+".txt");
			fop = new FileOutputStream(file);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
	}

	public List <String> readFile(String filename)
	{
	List<String> records = new ArrayList<String>();
	try {
		BufferedReader reader = new BufferedReader(new FileReader(filename+".txt"));
		String line = null;
		while((line=reader.readLine())!=null){
			records.add(line);
			ta.setText(line);
		}
		reader.close();
		return records;
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
		return null;
	}
	}
}
