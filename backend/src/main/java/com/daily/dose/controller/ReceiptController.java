package com.daily.dose.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.daily.dose.dto.ReceiptRequestDTO;
import com.daily.dose.dto.ReceiptResponseDTO;
import com.daily.dose.dto.ReceiptUpdateDTO;
import com.daily.dose.service.ReceiptService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")

@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class ReceiptController {

    private static final Logger logger =
            LoggerFactory.getLogger(ReceiptController.class);

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    // 🔹 CREATE RECEIPT
    @PostMapping("/receipts")
    public ResponseEntity<ReceiptResponseDTO> createReceipt(
            @Valid @RequestBody ReceiptRequestDTO requestDTO) {

        logger.info("Creating a new receipt");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receiptService.createReceipt(requestDTO));
    }

    @GetMapping("/receipts/{id}")
    public ResponseEntity<ReceiptResponseDTO> getReceiptById(
            @PathVariable Long id) {

        logger.info("Fetching receipt by id {}", id);
        return ResponseEntity.of(receiptService.findById(id));
    }

    @GetMapping("/patients/{patientId}/receipts")
    public ResponseEntity<List<ReceiptResponseDTO>> getReceiptsByPatient(
            @PathVariable Long patientId) {

        logger.info("Fetching receipts for patient {}", patientId);
        return ResponseEntity.ok(
                receiptService.findByPatient(patientId)
        );
    }
    @GetMapping(value = "/receipts/{receiptId}/pdf", produces = "application/pdf")
    public ResponseEntity<byte[]> downloadReceiptPdf(
            @PathVariable long receiptId) {

        byte[] pdfBytes = receiptService.generatePDF(receiptId);

        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename=receipt-" + receiptId + ".pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    
    
    @GetMapping("/visits/{visitId}/receipt")
    public ResponseEntity<ReceiptResponseDTO> getReceiptByVisit(
            @PathVariable Long visitId) {

        logger.info("Fetching receipt for visit {}", visitId);
        return ResponseEntity.ok(
                receiptService.getReceiptByVisit(visitId)
        );
    }
    
    @GetMapping("/receipts")
    public ResponseEntity<List<ReceiptResponseDTO>> getAllReceipts(){
    	return ResponseEntity.ok(receiptService.getAllReceipts());
    }
    @PatchMapping("/receipts/{id}")
    public ResponseEntity<ReceiptResponseDTO> updateReceipt(
            @PathVariable Long id,
            @RequestBody ReceiptUpdateDTO dto
    ) {
        return ResponseEntity.ok(
            receiptService.updateReceipt(id, dto)
        );
    }

}
