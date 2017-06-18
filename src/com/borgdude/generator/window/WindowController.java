package com.borgdude.generator.window;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.borgdude.generator.model.Student;
import com.borgdude.generator.pdf.*;
import com.itextpdf.text.DocumentException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;


public class WindowController {
	@FXML private TableView<Student> tableView;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField pictureField;
    @FXML private Button generateBtn;
    @FXML private Button picBtn;
    
    @FXML
    protected void addStudent(ActionEvent event) {
        ObservableList<Student> data = tableView.getItems();
        if(firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || pictureField.getText().isEmpty()){
        	displayAlert("Please fill out all of the student information.");
        } else {
        	data.add(new Student(
                	firstNameField.getText(),
                    lastNameField.getText(),
                    pictureField.getText()
                ));
                
            firstNameField.setText("");
            lastNameField.setText("");
            pictureField.setText("");   
        }
        
    }
    
    @FXML
    protected void deleteStudent(ActionEvent event){
    	Student selectedItem = tableView.getSelectionModel().getSelectedItem();
    	if(selectedItem == null){
    		displayAlert("Please select a student in the table.");
    	} else {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmation Dialog");
    		alert.setHeaderText(null);
    		alert.setContentText("Are you sure that you want to delete: " + selectedItem.getFullName());

    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == ButtonType.OK){
    			tableView.getItems().remove(selectedItem);
    		}
    		
    	}
    	
    }
    
    @FXML
    protected void choosePicture(ActionEvent event){
    	System.out.print("Button clicked");
    	FileChooser fc = new FileChooser();
    	fc.setTitle("Choose Picture...");
    	fc.getExtensionFilters().addAll(new ExtensionFilter("PNG Files", "*.png"));
    	File picture = fc.showOpenDialog(null);
    	
    	if (picture != null){
    		pictureField.setText(picture.toString());
    	}
    }
    
    @FXML
    protected void editStudent(ActionEvent event){
    	Student selectedItem = tableView.getSelectionModel().getSelectedItem();
    	if(selectedItem == null){
    		displayAlert("Please select a student in the table.");
    	} else{
    		tableView.getItems().remove(selectedItem);
        	
        	firstNameField.setText(selectedItem.getFirstName());
        	lastNameField.setText(selectedItem.getLastName());
        	pictureField.setText(selectedItem.getPicture());
    	}
    	
    }
    @FXML
    protected void generateBdf(ActionEvent event) throws IOException, DocumentException{
    	FileChooser fc = new FileChooser();
    	fc.getExtensionFilters().addAll(new ExtensionFilter("PDF Files", "*.pdf"));
    	
    	File pdfFile = fc.showSaveDialog(null);
    	
    	if(pdfFile != null){
    		PDFGenerator.createPdf(pdfFile.toString(), tableView.getItems());
    		
    		displaySuccessAlert(pdfFile);
    	}
    	
    }
    
    @FXML
    protected void saveToExcel(ActionEvent event){
    	FileChooser fc = new FileChooser();
    	fc.getExtensionFilters().addAll(new ExtensionFilter("Excel File(.xlsx)", "*.xlsx"));
    	File file = fc.showSaveDialog(null);
    	
    	if(file != null){
    		XSSFWorkbook workbook = new XSSFWorkbook();
	
	        //Create a blank sheet
	        XSSFSheet sheet = workbook.createSheet("Student Data");
	        
	        int rowNum = 0;
	        for(Student student: tableView.getItems()){
	        	
	        	Row row = sheet.createRow(rowNum++);
	        	
	        	String[] stuArr = new String[]{student.getFirstName(), student.getLastName(), student.getPicture()};
	        	
	        	int cellNum = 0;
	        	for(String stu: stuArr){
	        		Cell cell = row.createCell(cellNum++);
	        		cell.setCellValue((String) stu); 
	        	}
	        }
	        
	        try {
				FileOutputStream out = new FileOutputStream(file);
				workbook.write(out);
				out.close();
				System.out.println(".xlsx written successfully on disk.");
				
				displaySuccessAlert(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    	
    }
    
    @FXML
    protected void loadExcel(ActionEvent event){
    	FileChooser fc = new FileChooser();
    	fc.getExtensionFilters().addAll(new ExtensionFilter("Excel File(.xlsx)", "*.xlsx"));
    	File file = fc.showOpenDialog(null);
    	
    	if(file != null){
    		ObservableList<Student> data = tableView.getItems();
    		tableView.getItems().clear();
    		try {
    	        FileInputStream excelFile = new FileInputStream(file);

    	        //Create Workbook instance holding reference to .xlsx file
    	        XSSFWorkbook workbook = new XSSFWorkbook(excelFile);

    	        //Get first/desired sheet from the workbook
    	        XSSFSheet sheet = workbook.getSheetAt(0);

    	        //Iterate through each rows one by one
    	        Iterator<Row> rowIterator = sheet.iterator();
    	        while (rowIterator.hasNext()){
    	            Row row = rowIterator.next();
    	            
    	            //For each row, iterate through all the columns
    	            Iterator<Cell> cellIterator = row.cellIterator();

    	            data.add(new Student(
    	            		cellIterator.next().getStringCellValue(), 
    	            		cellIterator.next().getStringCellValue(), 
    	            		cellIterator.next().getStringCellValue()));
    	        }
    	        excelFile.close();
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
    	}
    }

	public void displayAlert(String content){
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText(null);
    	alert.setContentText(content);
    	alert.showAndWait();
    }
	
	
	public void displaySuccessAlert(File pdfFile){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("PDF Generated");
		alert.setHeaderText(null);
		alert.setContentText("File saved to: " + pdfFile.toString());
		alert.showAndWait();
	}
    
	
}
