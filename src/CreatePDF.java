import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreatePDF {

    static final String DEST = "results/tables/nestedtable.pdf";
    static final String CUSTOMER_REFERENCE = "Customer reference:\n12345678901234567\n";
    static final String DEPARTMENT_REFERENCE = "Department reference:\n1234567890";
    static final String SPECIAL_INSTRUCTIONS = "Special Instructions 123456789\n123456789 Special Instructions";
    static final String RETURN_TO = "RETURN TO: ";
    static final String code1D = "AA012345675AA";
    static final String code2D = "AA-012 345 6789-AAA AAA AAC";
    static final String DIRECTORY = "C:/Users/compu/OneDrive/Documents/GitHub/AddressPrint/";

    //Fonts
    private static BaseFont arialBase;
    private static BaseFont chevinLightBase;
    private static BaseFont chevinBoldBase;

    private static Font CHEVIN_LIGHT_8;
    private static Font CHEVIN_LIGHT_17;
    private static Font CHEVIN_BOLD_15;
    private static Font CHEVIN_BOLD_29;
    private static Font CHEVIN_BOLD_48;
    private static Font ARIAL_REGULAR_7;
    private static Font ARIAL_REGULAR_10;
    private static Font ARIAL_REGULAR_11;

    public static void createPdf() throws IOException, DocumentException {

        int smallPadding = 5;
        int largePadding = 10;

        //Fonts
        arialBase = BaseFont.createFont(DIRECTORY + "fonts/arial.ttf", BaseFont.WINANSI, true);
        chevinLightBase = BaseFont.createFont(DIRECTORY + "fonts/chevin_light.otf", BaseFont.WINANSI, true);
        chevinBoldBase = BaseFont.createFont(DIRECTORY + "fonts/chevin_bold.ttf", BaseFont.WINANSI, true);


        CHEVIN_LIGHT_8 = new Font(chevinLightBase, 8, Font.NORMAL);
        CHEVIN_LIGHT_17 = new Font(chevinLightBase, 17, Font.NORMAL);
        CHEVIN_BOLD_15 = new Font(chevinBoldBase, 15, Font.NORMAL);
        CHEVIN_BOLD_29 = new Font(chevinBoldBase, 29, Font.NORMAL);
        CHEVIN_BOLD_48 = new Font(chevinBoldBase, 48, Font.NORMAL);
        ARIAL_REGULAR_7 = new Font(arialBase, 7f, Font.NORMAL);
        ARIAL_REGULAR_10 = new Font(arialBase, 10f, Font.NORMAL);
        ARIAL_REGULAR_11 = new Font(arialBase, 11f, Font.NORMAL);

        //File creation
        File file = new File(DEST);
        file.getParentFile().mkdirs();

        //Document setup
        Rectangle pageSize = new Rectangle(280, 415);
        Document document = new Document(pageSize,5,5,5,5);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
        document.open();

        PdfContentByte cb = writer.getDirectContent();

        //Frame
        PdfPTable frameTable = new PdfPTable(1);
        frameTable.setWidthPercentage(100);

        //(1)Service Type
        PdfPTable serviceTypeTable = CreatePDF.serviceTypeCell();
        frameTable.addCell(serviceTypeTable);
        /*PdfPTable serviceTypeTable = new PdfPTable((3));
        serviceTypeTable.setWidths(new int[] {10, 5, 7});

        PdfPTable trackedTable = new PdfPTable(1);
        trackedTable.setWidthPercentage(100);

        PdfPCell tracked = new PdfPCell(new Phrase("Tracked", CHEVIN_BOLD_28));
        tracked.setPaddingLeft(largePadding);
        tracked.setPaddingTop(largePadding);
        tracked.setBorder(Rectangle.NO_BORDER);
        trackedTable.addCell(tracked);

        PdfPCell signature = new PdfPCell(new Phrase("Signature", CHEVIN_LIGHT_17));
        signature.setPaddingLeft(largePadding + smallPadding);
        signature.setBorder(Rectangle.NO_BORDER);
        trackedTable.addCell(signature);

        PdfPCell trackedTableCell = new PdfPCell(trackedTable);
        trackedTableCell.setBorder(Rectangle.NO_BORDER);
        serviceTypeTable.addCell(trackedTableCell);

        PdfPCell timeScale = new PdfPCell(new Phrase("48", CHEVIN_BOLD_48));
        timeScale.setBorder(Rectangle.NO_BORDER);
        //timeScale.setFixedHeight(52);
        //timeScale.setVerticalAlignment(Element.ALIGN_MIDDLE);
        //timeScale.setHorizontalAlignment(Element.ALIGN_CENTER);
        timeScale.setPaddingTop(7);
        serviceTypeTable.addCell(timeScale);

        PdfPTable rmCruciformTable = new PdfPTable(1);
        rmCruciformTable.setWidthPercentage(100);

        PdfPCell deliveryBy = new PdfPCell(new Phrase("Delivery By", CHEVIN_LIGHT_8));
        deliveryBy.setBorder(Rectangle.NO_BORDER);
        deliveryBy.setFixedHeight(14);
        deliveryBy.setHorizontalAlignment(Element.ALIGN_CENTER);
        rmCruciformTable.addCell(deliveryBy);

        Image image = Image.getInstance("drawable" + File.separator + "rm_cruciform.png");
        PdfPCell rmCruciform = new PdfPCell(image, true);
        rmCruciform.setHorizontalAlignment(Element.ALIGN_CENTER);
        rmCruciform.setBorder(Rectangle.NO_BORDER);
        rmCruciform.setFixedHeight(45);
        rmCruciformTable.addCell(rmCruciform);

        PdfPCell postOnAccount = new PdfPCell(new Phrase("Postage on Account GB", CHEVIN_LIGHT_8));
        postOnAccount.setBorder(Rectangle.NO_BORDER);
        postOnAccount.setFixedHeight(14);
        postOnAccount.setHorizontalAlignment(Element.ALIGN_CENTER);
        rmCruciformTable.addCell(postOnAccount);

        PdfPCell rmCruciformCell = new PdfPCell(rmCruciformTable);
        rmCruciformCell.setBorder(Rectangle.NO_BORDER);
        serviceTypeTable.addCell(rmCruciformCell);

        frameTable.addCell(serviceTypeTable);

        /*Image image = Image.getInstance("drawable" + File.separator + "Royal Mail Top.jpg");
        PdfPCell serviceType = new PdfPCell(image, true);
        serviceType.setFixedHeight(70);
        serviceType.setPadding(smallPadding);
        serviceType.setHorizontalAlignment(Element.ALIGN_CENTER);
        frameTable.addCell(serviceType);*/

        //(2)Enhancements - NOT REQUIRED

        //(3)Barcodes
        PdfPTable barcodes = new PdfPTable(2);// 2x2 table

        // (0,0)
        PdfPCell code2DCell = new PdfPCell(new Phrase(code2D , CHEVIN_LIGHT_8));
        code2DCell.setPaddingLeft(largePadding);
        code2DCell.setFixedHeight(20);
        code2DCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        code2DCell.setBorder(Rectangle.NO_BORDER);
        barcodes.addCell(code2DCell);
        // (0,1)
        PdfPCell emptyCell = new PdfPCell();
        emptyCell.setBorder(Rectangle.NO_BORDER);
        barcodes.addCell(emptyCell);
        // (1,0)
        BarcodeDatamatrix dm = new BarcodeDatamatrix();
        dm.setHeight(44);
        dm.setWidth(44);
        dm.generate(code2D);
        Image dmImage = dm.createImage();
        dmImage.scaleToFit(70,70);
        PdfPCell code2DImageCell = new PdfPCell(dmImage);
        code2DImageCell.setBorder(Rectangle.NO_BORDER);
        code2DImageCell.setFixedHeight(100);
        code2DImageCell.setPadding(largePadding);
        barcodes.addCell(code2DImageCell);
        // (1,1)
        Barcode128 code128 = new Barcode128();
        code128.setBarHeight(70);
        code128.setFont(null);
        code128.setCode(code1D);
        code128.setCodeType(Barcode128.CODE128);
        Image code1DImage = code128.createImageWithBarcode(cb, null, null);
        PdfPCell code1DImageCell = new PdfPCell();
        code1DImageCell.addElement(code1DImage);
        Paragraph code1DCell = new Paragraph(code1D, CHEVIN_LIGHT_8);
        code1DCell.setAlignment(Element.ALIGN_CENTER);
        code1DImageCell.addElement(code1DCell);
        code1DImageCell.setBorder(Rectangle.NO_BORDER);
        code1DImageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        code1DImageCell.setPadding(largePadding);
        barcodes.addCell(code1DImageCell);

        frameTable.addCell(barcodes);

        //(4)Addresses
        PdfPTable addresses = new PdfPTable(2);
        addresses.setWidths(new int[]{4, 1});

        PdfPCell customerAddress = new PdfPCell(new Phrase(CustomerAddressArray.createArray(), ARIAL_REGULAR_11));
        customerAddress.setBorder(Rectangle.NO_BORDER);
        customerAddress.setFixedHeight(95);
        customerAddress.setPaddingLeft(largePadding);
        customerAddress.setVerticalAlignment(Element.ALIGN_MIDDLE);
        addresses.addCell(customerAddress);

        PdfPCell senderAddress = new PdfPCell(new Phrase(RETURN_TO, ARIAL_REGULAR_7));
        senderAddress.setBorder(Rectangle.NO_BORDER);
        senderAddress.setRotation(90);
        senderAddress.setPadding(smallPadding);
        addresses.addCell(senderAddress);

        frameTable.addCell(addresses);

        //(5)Special Instructions
        PdfPCell specialInstructions = new PdfPCell(new Phrase(SPECIAL_INSTRUCTIONS, ARIAL_REGULAR_10));
        specialInstructions.setVerticalAlignment(Element.ALIGN_MIDDLE);
        specialInstructions.setPaddingLeft(largePadding);
        specialInstructions.setFixedHeight(35);
        frameTable.addCell(specialInstructions);

        //(6)References
        PdfPCell references = new PdfPCell(new Phrase(CUSTOMER_REFERENCE + DEPARTMENT_REFERENCE, CHEVIN_LIGHT_8));
        references.setFixedHeight(70);
        references.setPaddingLeft(largePadding);
        references.setVerticalAlignment(Element.ALIGN_MIDDLE);
        frameTable.addCell(references);

        document.add(frameTable);
        document.close();
    }

    private static PdfPTable serviceTypeCell() throws IOException, DocumentException{

        PdfPTable serviceTypeTable = new PdfPTable((3));
        serviceTypeTable.setWidths(new int[] {10, 5, 7});

        PdfPTable trackedTable = new PdfPTable(1);
        trackedTable.setWidthPercentage(100);

        /*PdfPCell tracked = new PdfPCell(new Phrase("Tracked", CHEVIN_BOLD_29));
        tracked.setPaddingLeft(10);
        tracked.setPaddingTop(10);
        tracked.setBorder(Rectangle.NO_BORDER);
        trackedTable.addCell(tracked);*/

        PdfPCell tracked = new PdfPCell(new Phrase("Special Delivery", CHEVIN_BOLD_15));
        tracked.setPaddingLeft(10);
        tracked.setPaddingTop(20);
        tracked.setBorder(Rectangle.NO_BORDER);
        trackedTable.addCell(tracked);

        /*PdfPCell signature = new PdfPCell(new Phrase("Signature", CHEVIN_LIGHT_17));
        signature.setPaddingLeft(15);
        signature.setBorder(Rectangle.NO_BORDER);
        trackedTable.addCell(signature);*/

        PdfPCell signature = new PdfPCell(new Phrase("Guaranteed By", CHEVIN_BOLD_15));
        signature.setPaddingLeft(10);
        signature.setBorder(Rectangle.NO_BORDER);
        trackedTable.addCell(signature);

        PdfPCell trackedTableCell = new PdfPCell(trackedTable);
        trackedTableCell.setBorder(Rectangle.NO_BORDER);
        serviceTypeTable.addCell(trackedTableCell);

        /*PdfPCell timeScale = new PdfPCell(new Phrase("48", CHEVIN_BOLD_48));
        timeScale.setBorder(Rectangle.NO_BORDER);
        timeScale.setPaddingTop(7);
        serviceTypeTable.addCell(timeScale);*/

        PdfPCell timeScale = new PdfPCell();
        Paragraph para = new Paragraph();
        Chunk c1 = new Chunk("1", CHEVIN_BOLD_48);
        Chunk c2 = new Chunk("pm", CHEVIN_BOLD_15);
        para.add(c1);
        para.add(c2);
        timeScale.addElement(para);
        timeScale.setBorder(Rectangle.NO_BORDER);
        timeScale.setPaddingTop(38);
        timeScale.setPaddingLeft(5);
        timeScale.setVerticalAlignment(Element.ALIGN_CENTER);
        serviceTypeTable.addCell(timeScale);

        PdfPTable rmCruciformTable = new PdfPTable(1);
        rmCruciformTable.setWidthPercentage(100);

        PdfPCell deliveryBy = new PdfPCell(new Phrase("Delivery By", CHEVIN_LIGHT_8));
        deliveryBy.setBorder(Rectangle.NO_BORDER);
        deliveryBy.setFixedHeight(14);
        deliveryBy.setHorizontalAlignment(Element.ALIGN_CENTER);
        rmCruciformTable.addCell(deliveryBy);

        Image image = Image.getInstance("drawable" + File.separator + "rm_cruciform.png");
        PdfPCell rmCruciform = new PdfPCell(image, true);
        rmCruciform.setHorizontalAlignment(Element.ALIGN_CENTER);
        rmCruciform.setBorder(Rectangle.NO_BORDER);
        rmCruciform.setFixedHeight(45);
        rmCruciformTable.addCell(rmCruciform);

        PdfPCell postOnAccount = new PdfPCell(new Phrase("Postage on Account GB", CHEVIN_LIGHT_8));
        postOnAccount.setBorder(Rectangle.NO_BORDER);
        postOnAccount.setFixedHeight(14);
        postOnAccount.setHorizontalAlignment(Element.ALIGN_CENTER);
        rmCruciformTable.addCell(postOnAccount);

        PdfPCell rmCruciformCell = new PdfPCell(rmCruciformTable);
        rmCruciformCell.setBorder(Rectangle.NO_BORDER);
        serviceTypeTable.addCell(rmCruciformCell);

        return serviceTypeTable;
    }
}

