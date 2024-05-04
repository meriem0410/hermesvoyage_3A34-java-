package edu.esprit.gui;

import edu.esprit.entities.reservation;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.web.WebView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class VoucherGenerator {

    public void generateAndPrintVoucher(reservation reservation) {
        // Create voucher data based on the reservation details
        String voucherData = createVoucherData(reservation);

        // Generate PDF voucher
        try {
            File voucherFile = generatePDFVoucher(voucherData);

            // Print the PDF voucher
            printPDFVoucher(voucherFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createVoucherData(reservation reservation) {
        // Create voucher data based on reservation details
        // This can be a formatted string containing reservation information
        return "Voucher Data";
    }

    private File generatePDFVoucher(String voucherData) throws IOException {
        // Generate a PDF voucher using Apache PDFBox or any other library
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        document.save("voucher.pdf");
        document.close();

        return new File("voucher.pdf");
    }

    public void printPDFVoucher(File voucherFile) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            try {
                // Create a WebView to display the PDF file
                WebView webView = new WebView();
                webView.getEngine().load(voucherFile.toURI().toURL().toString());

                // Print the content of the WebView
                if (job.showPrintDialog(null)) {
                    job.printPage(webView);
                    job.endJob();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
