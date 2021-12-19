package com.yfcod.management.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author YFCodeDream
 * @version 1.0.3
 * @date 2021/12/18
 * @description print pdf util
 */
public class PrintPdfUtil {
    private static Font titleFont;
    private static Font keyFont;
    private static Font textFont;

    private static final int maxWidth = 520;
    private static final int maxRecordNumPerPage = 35;

    static {
        try {
            BaseFont bfChinese = BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titleFont = new Font(bfChinese, 24, Font.NORMAL);
            keyFont = new Font(bfChinese, 10, Font.NORMAL);
            textFont = new Font(bfChinese, 10, Font.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printPdf(String pdfPath,
                                HashMap<String, String> studentInfo,
                                List<String> examIds,
                                List<String> courses,
                                List<String> scores) {
        try {
            Document document = new Document(PageSize.A4);

            File file = new File(pdfPath);
            boolean newFile = file.createNewFile();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new Watermark("XMU-MIS"));
            writer.setPageEvent(new SelfHeaderFooter());

            document.open();
            document.addTitle("XMU@PDF-Java");
            document.addAuthor("Author@yfcod");
            document.addCreator("Creator@yfcod-mis");

            generatePDF(
                    document,
                    studentInfo,
                    examIds,
                    courses,
                    scores
            );

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generatePDF(Document document,
                                    HashMap<String, String> studentInfo,
                                    List<String> examIds,
                                    List<String> courses,
                                    List<String> scores)
            throws DocumentException, IOException {
        Image image = Image.getInstance("src/main/resources/com/yfcod/management/img/xmu-logo.png");
        image.setAlignment(Image.ALIGN_LEFT);
        image.scalePercent(30);

        Paragraph paragraph = new Paragraph("学生成绩单", titleFont);
        paragraph.setAlignment(1);
        paragraph.setIndentationLeft(12);
        paragraph.setIndentationRight(12);
        paragraph.setFirstLineIndent(24);
        paragraph.setLeading(20f);
        paragraph.setSpacingBefore(5f);
        paragraph.setSpacingAfter(10f);

        Paragraph studentIdParagraph = new Paragraph("学生学号：" + studentInfo.get("studentId"), textFont);
        Paragraph studentNameParagraph = new Paragraph("学生姓名：" + studentInfo.get("studentName"), textFont);
        Paragraph majorParagraph = new Paragraph("专业：" + studentInfo.get("major"), textFont);

        Paragraph lineParagraph = new Paragraph();
        lineParagraph.add(new Chunk(new LineSeparator()));

        Paragraph dotParagraph = new Paragraph();
        dotParagraph.add(new Chunk(new DottedLineSeparator()));

        int totalPage = examIds.size() / maxRecordNumPerPage +
                (examIds.size() % maxRecordNumPerPage == 0 ? 0 : 1);

        int currentRecordNum = 0;
        for (int i = 0; i < totalPage; i++) {
            PdfPTable table = createTable(new float[]{100, 120, 120});
            table.addCell(createCell("考试编号", keyFont, Element.ALIGN_CENTER));
            table.addCell(createCell("课程名称", keyFont, Element.ALIGN_CENTER));
            table.addCell(createCell("考试成绩", keyFont, Element.ALIGN_CENTER));

            for (int j = currentRecordNum; j < currentRecordNum + maxRecordNumPerPage; j++) {
                if (j >= examIds.size()) {
                    break;
                }
                table.addCell(createCell(examIds.get(j), textFont));
                table.addCell(createCell(courses.get(j), textFont));
                table.addCell(createCell(scores.get(j), textFont));
            }

            currentRecordNum += maxRecordNumPerPage;

            document.add(image);
            document.add(paragraph);
            document.add(dotParagraph);
            document.add(studentIdParagraph);
            document.add(studentNameParagraph);
            document.add(majorParagraph);
            document.add(lineParagraph);
            document.add(table);
            if (i < totalPage - 1) {
                document.newPage();
            }
        }
    }


    private static PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    @SuppressWarnings("SameParameterValue")
    private static PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    private static PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
}
