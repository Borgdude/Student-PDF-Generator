package com.borgdude.generator.pdf;

import com.borgdude.generator.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.ObservableList;

public class PDFGenerator {
	
    public static void createPdf(String dest, ObservableList<Student> items) throws IOException, DocumentException {
    	int studentsLeft = items.size() % 4;
    	int mainStudents = items.size() - studentsLeft;
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfPTable table = new PdfPTable(4);
        for(int i = 0; i < mainStudents; i++){
        	Image img = Image.getInstance(items.get(i).getPicture());
        	img.scaleToFit(90f, 90f);
        	Paragraph p = new Paragraph(new Chunk(img, 0, 0, true));
        	p.add("\n" + items.get(i).getFullName());
        	p.setAlignment(Element.ALIGN_CENTER);
        	PdfPCell cell = new PdfPCell();
        	cell.addElement(p);
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	cell.setBorder(Rectangle.NO_BORDER);
        	cell.setPaddingBottom(30);
        	cell.setPaddingLeft(5);
        	cell.setPaddingRight(5);
            table.addCell(cell);
        }
        table.completeRow();
        document.add(table);
        
        if(studentsLeft > 0){
        	PdfPTable tableBot = new PdfPTable(studentsLeft);
            for(int i = mainStudents; i < items.size(); i++){
            	Image img = Image.getInstance(items.get(i).getPicture());
            	img.scaleToFit(90f, 90f);
            	Paragraph p = new Paragraph(new Chunk(img, 0, 0, true));
            	p.add("\n" + items.get(i).getFullName());
            	p.setAlignment(Element.ALIGN_CENTER);
            	PdfPCell cell = new PdfPCell();
            	cell.addElement(p);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setBorder(Rectangle.NO_BORDER);
            	cell.setPaddingBottom(30);
            	cell.setPaddingLeft(5);
            	cell.setPaddingRight(5);
                tableBot.addCell(cell);
            }
            tableBot.completeRow();
            document.add(tableBot);	
        }

        document.close();
        System.out.println("document printed");
    }
}
