/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.entities;

import com.infantry.utils.DatabaseConnection;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.ArrayList;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author asus
 */
public class Pdf {
        private Connection con;
        private Statement ste;
    public Pdf()  {
        con = DatabaseConnection.getInstance().getConnection();
          
    
}
    public void add(String file,Reclamation r) throws FileNotFoundException, SQLException, DocumentException{
        
                Document my_pdf_report = new Document();
                PdfWriter.getInstance(my_pdf_report, new FileOutputStream(file));
                my_pdf_report.open();            
                //we have four columns in our table
                PdfPTable my_report_table = new PdfPTable(2);
                //create a cell object
                PdfPCell table_cell;
                                
                java.util.Date datetoday= new java.util.Date();
                              
                                table_cell=new PdfPCell(new Phrase("Nom"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(r.getNom()));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase("Prenom"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(r.getPrenom()));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase("Email"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(r.getEmail()));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase("Message"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(r.getMessage()));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase("Date"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(datetoday.toString()));
                                my_report_table.addCell(table_cell);

                                
                /* Attach report table to PDF */
                my_pdf_report.add(my_report_table);                       
                my_pdf_report.close();
                
               /* Close all DB related objects */

        
    }

    public void add(String reclamation, Reclamation SingleReclamation, Document document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
}
