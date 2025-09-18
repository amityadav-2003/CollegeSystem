package com.amstech.student.util;

import com.amstech.std.system.entity.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PdfGenerator {

    private final List<User> users;

    public PdfGenerator(List<User> users) {
        this.users = users;
    }

    public void generate(HttpServletResponse response) throws IOException, DocumentException {
        // Step 1: Create a new PDF Document
        Document document = new Document(PageSize.A4);
        
        // Step 2: Get a PdfWriter instance to write to the response's output stream
        PdfWriter.getInstance(document, response.getOutputStream());
        
        // Step 3: Open the document
        document.open();

        // Add a title to the PDF
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        Paragraph title = new Paragraph("User List Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add some space
        document.add(new Paragraph("\n"));
        
        // Create a table with 4 columns
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[] {1f, 2f, 2.5f, 2f});
        
        // Add table headers
        PdfPCell headerCell;
        
        headerCell = new PdfPCell(new Paragraph("ID"));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerCell);
        
        headerCell = new PdfPCell(new Paragraph("Name"));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerCell);
        
        headerCell = new PdfPCell(new Paragraph("Email"));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerCell);
        
        headerCell = new PdfPCell(new Paragraph("Phone Number"));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerCell);

        // Add user data to the table
        for (User user : users) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getFirstName() + " " + user.getLastName());
            table.addCell(user.getEmail());
            table.addCell(user.getMobileNumber());
        }

        // Add table to the document
        document.add(table);

        // Close the document
        document.close();
    }
}