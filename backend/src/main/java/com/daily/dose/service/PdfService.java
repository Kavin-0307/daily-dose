package com.daily.dose.service;

import java.awt.Color;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import org.openpdf.text.Document;
import org.openpdf.text.Font;
import org.openpdf.text.FontFactory;
import org.openpdf.text.HeaderFooter;
import org.openpdf.text.Image;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.daily.dose.entity.Receipts;

@Service
public class PdfService {
	
	@Value("${daily.dose.storage.base-dir}")
	private String baseDir;

	/**
	 * Generates a PDF receipt for a given receipt entity.
	 *
	 * This method is intentionally file-system based and synchronous.
	 * It assumes the receipt and its linked entities are already validated.
	 *
	 * Returns the path to the generated PDF for further use (download, email, etc.).
	 */
	public Path generateReceiptPDF(Receipts receipt) {
		FontFactory.register(
			    PdfService.class
			        .getClassLoader()
			        .getResource("fonts/OpenSauceSans-Regular.ttf")
			        .toString(),
			    "OpenSauceSans"
			);

	FontFactory.register(
	    PdfService.class
	        .getClassLoader()
	        .getResource("fonts/OpenSauceSans-Bold.ttf")
	        .toString(),
	    "OpenSauceSans-Bold"
	);
	Font titleFont   = FontFactory.getFont("OpenSauceSans-Bold", 16,Font.BOLD);
	Font sectionFont = FontFactory.getFont("OpenSauceSans-Bold", 12);
	Font labelFont   = FontFactory.getFont("OpenSauceSans-Bold", 10);
	Font valueFont   = FontFactory.getFont("OpenSauceSans", 10);

		Document document=new Document(org.openpdf.text.PageSize.A4);
		try {
			
			int year=receipt.getCreatedAt().getYear();
			// Year-based directory structure for better file organization
			Path receiptDir=Path.of(baseDir,"Receipts",String.valueOf(year));
		
			
			Files.createDirectories(receiptDir);
			String fileName=receipt.getReceiptNumber()+".pdf";
			Path pdfPath=receiptDir.resolve(fileName);
			
            float width = document.getPageSize().getWidth();
            float height = document.getPageSize().getHeight();
            
			PdfWriter.getInstance(document,new FileOutputStream(pdfPath.toFile()));
			document.open();
			PdfPTable headerTable = new PdfPTable(new float[]{1f, 3f});
			headerTable.setWidthPercentage(100);
			headerTable.setSpacingAfter(20f);

			// LEFT: Logo cell
			PdfPCell logoCell = new PdfPCell();
			logoCell.setBorder(PdfPCell.NO_BORDER);

			try {
			    Image img = Image.getInstanceFromClasspath("DD_background.png");
			    img.scaleToFit(250,250);
			    img.setAlignment(Image.ALIGN_LEFT);
			    logoCell.addElement(img);
			} catch (Exception e) {
			    // logo optional
			}

			headerTable.addCell(logoCell);

			// RIGHT: Clinic details
			Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

			Paragraph clinicInfo = new Paragraph(
			        "Clinic: DAILY DOSE\n" +
			        "Phone No: 9509577767\n" +
			        "Email: dailydosephysio@gmail.com\n" +
			        "Location: Basement-1, Signature Prime Mukut,\n" +
			        "Plot No.101, Nemi Nagar Vistar, Vishal Marg,\n" +
			        "Vaishali Nagar",
			        headerFont
			);
			clinicInfo.setAlignment(Paragraph.ALIGN_RIGHT);

			PdfPCell infoCell = new PdfPCell();
			infoCell.setBorder(PdfPCell.NO_BORDER);
			infoCell.addElement(clinicInfo);

			headerTable.addCell(infoCell);

			document.add(headerTable);
			
			PdfPTable divider = new PdfPTable(1);
			divider.setWidthPercentage(100);
			PdfPCell line = new PdfPCell();
			line.setBorder(PdfPCell.BOTTOM);
			line.setBorderWidthBottom(1f);
			line.setFixedHeight(5f);
			line.setBorderColor(Color.GRAY);
			divider.addCell(line);
			document.add(divider);
			
			
			Paragraph metaStrip=new Paragraph("Reciept Number:"+receipt.getReceiptNumber(),labelFont) ;
			document.add(metaStrip);
			Paragraph metaStrip1=new Paragraph("Date:"+LocalDate.now(),labelFont);
			document.add(metaStrip1);
			Paragraph metaStrip2=new Paragraph("Patient Number:"+receipt.getPatient().getPatientNumber(),labelFont);
			document.add(metaStrip2);
			//"\t\t+"\t
			Paragraph patientHeader = new Paragraph("Patient Information", sectionFont);
			patientHeader.setSpacingBefore(10f);
			patientHeader.setSpacingAfter(8f);
			document.add(patientHeader);

			// subtle divider
			PdfPTable patientDivider = new PdfPTable(1);
			patientDivider.setWidthPercentage(100);
			PdfPCell dLine = new PdfPCell();
			dLine.setBorder(PdfPCell.BOTTOM);
			dLine.setBorderColor(Color.BLACK);
			dLine.setBorderWidthBottom(0.8f);
			dLine.setFixedHeight(5f);
			patientDivider.addCell(dLine);
			document.add(patientDivider);


			
			PdfPTable patientInfo = new PdfPTable(new float[]{1.2f, 2.8f});
			patientInfo.setWidthPercentage(100);
			patientInfo.setSpacingBefore(12f);
			patientInfo.setSpacingAfter(20f);

			patientInfo.addCell(infoLabel("Patient Name"));
			patientInfo.addCell(infoValue(receipt.getPatient().getPatientName()));

			patientInfo.addCell(infoLabel("Age"));
			patientInfo.addCell(infoValue(String.valueOf(receipt.getPatient().getPatientAge())));

			patientInfo.addCell(infoLabel("Address"));
			patientInfo.addCell(infoValue(receipt.getPatient().getPatientAddress()));

			patientInfo.addCell(infoLabel("Physiotherapist"));
			patientInfo.addCell(infoValue(receipt.getStaff().getStaffName()));

			patientInfo.addCell(infoLabel("Treatment"));
			patientInfo.addCell(infoValue(
			        receipt.getVisit() != null
			                ? receipt.getVisit().getVisitTreatment()
			                : "N/A"
			));

			document.add(patientInfo);
			Paragraph treatmentHeader = new Paragraph("TREATMENT SUMMARY", sectionFont);
			treatmentHeader.setSpacingBefore(15f);
			treatmentHeader.setSpacingAfter(5f);
			document.add(treatmentHeader);

			// Divider
			PdfPTable tDivider = new PdfPTable(1);
			tDivider.setWidthPercentage(100);
			PdfPCell tLine = new PdfPCell();
			tLine.setBorder(PdfPCell.BOTTOM);
			tLine.setBorderWidthBottom(1f);
			tLine.setBorderColor(Color.GRAY);
			tDivider.addCell(tLine);
			document.add(tDivider);

			PdfPTable treatmentTable = new PdfPTable(new float[]{3f, 1f, 1f,1f,1f});
			treatmentTable.setWidthPercentage(100);
			treatmentTable.setSpacingBefore(10f);

			// Header row
			treatmentTable.addCell(tableHeader("TREATMENT"));
			treatmentTable.addCell(tableHeader("PRICE"));
			treatmentTable.addCell(tableHeader("TOTAL"));
			treatmentTable.addCell(tableHeader("PAYMENT METHOD"));

			treatmentTable.addCell(tableHeader("PAID STATUS"));
			// Data row
			String treatmentName =
			        receipt.getVisit() != null
			                ? receipt.getVisit().getVisitTreatment()
			                : "N/A";

			String price = String.valueOf(receipt.getReceiptAmount());
			String total = "₹" + receipt.getReceiptAmount();
			String receiptPaymentMethod=receipt.getReceiptPaymentMethod();
			String receiptStatus=receipt.isReceiptPaid()!=false?"TRUE":"FALSE";
			treatmentTable.addCell(tableValue(treatmentName, PdfPCell.ALIGN_LEFT));
			treatmentTable.addCell(tableValue(price, PdfPCell.ALIGN_RIGHT));
			treatmentTable.addCell(tableValue(total, PdfPCell.ALIGN_RIGHT));

			treatmentTable.addCell(tableValue(receiptPaymentMethod, PdfPCell.ALIGN_RIGHT));

			treatmentTable.addCell(tableValue(receiptStatus, PdfPCell.ALIGN_RIGHT));

			// Total row
			PdfPCell totalLabel = new PdfPCell(
			        new Phrase("Total", FontFactory.getFont("OpenSauceSans-Bold", 10))
			);
			totalLabel.setColspan(2);
			totalLabel.setBorder(PdfPCell.TOP);
			totalLabel.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			totalLabel.setPadding(8f);

			PdfPCell totalValue = new PdfPCell(
			        new Phrase(total, FontFactory.getFont("OpenSauceSans-Bold", 10))
			);
			totalValue.setBorder(PdfPCell.TOP);
			totalValue.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			totalValue.setPadding(8f);

			treatmentTable.addCell(totalLabel);
			treatmentTable.addCell(totalValue);

			document.add(treatmentTable);
			PdfPTable t1Divider = new PdfPTable(1);
			t1Divider.setWidthPercentage(100);
			PdfPCell t1Line = new PdfPCell();
			t1Line.setBorder(PdfPCell.BOTTOM);
			t1Line.setBorderWidthBottom(1f);
			t1Line.setBorderColor(Color.BLACK);
			t1Divider.addCell(t1Line);
			document.add(t1Divider);
			
            Paragraph footer = new Paragraph(
                    "MANUAL THERAPHIST | HYBRID TRAINING | PREHAB AND REHAB SPECIALIST | SPORTS REHAB",
                    FontFactory.getFont(FontFactory.HELVETICA, 8,Font.BOLD)
            );
            footer.setAlignment(Paragraph.ALIGN_CENTER);
            footer.setSpacingBefore(30f);

            document.add(footer);

            
            
            document.close();
            return pdfPath;
		}catch ( Exception e) {
			throw new RuntimeException("Failed to generate receipt",e);
		}
		
	}private PdfPCell paymentLabelCell(String text) {
	    Font font = FontFactory.getFont("OpenSauceSans-Bold", 10,Font.BOLD);
	    PdfPCell cell = new PdfPCell(new Phrase(text, font));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    cell.setPaddingTop(5f);
	    cell.setPaddingBottom(5f);
	    return cell;
	}

	private PdfPCell paymentValueCell(String text) {
	    Font font = FontFactory.getFont("OpenSauceSans", 10);
	    PdfPCell cell = new PdfPCell(new Phrase(text, font));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	    cell.setPaddingTop(5f);
	    cell.setPaddingBottom(5f);
	    return cell;
	}
	private PdfPCell tableHeader(String text) {
	    Font font = FontFactory.getFont("OpenSauceSans-Bold", 10,Font.BOLD);
	    PdfPCell cell = new PdfPCell(new Phrase(text, font));
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell.setPadding(8f);
	    return cell;
	}

	private PdfPCell tableValue(String text, int align) {
	    Font font = FontFactory.getFont("OpenSauceSans", 10);
	    PdfPCell cell = new PdfPCell(new Phrase(text, font));
	    cell.setHorizontalAlignment(align);
	    cell.setPadding(8f);
	    return cell;
	}
	private PdfPCell infoLabel(String text) {
	    Font font = FontFactory.getFont("OpenSauceSans-Bold", 10,Font.BOLD);
	    PdfPCell cell = new PdfPCell(new Phrase(text.toUpperCase(), font));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setPaddingTop(6f);
	    cell.setPaddingBottom(6f);
	    cell.setPaddingRight(10f);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    return cell;
	}

	private PdfPCell infoValue(String text) {
	    Font font = FontFactory.getFont("OpenSauceSans", 10);
	    PdfPCell cell = new PdfPCell(new Phrase(text, font));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setPaddingTop(6f);
	    cell.setPaddingBottom(6f);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    return cell;
	}



}
