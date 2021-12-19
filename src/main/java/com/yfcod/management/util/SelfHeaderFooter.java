package com.yfcod.management.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelfHeaderFooter extends PdfPageEventHelper {
    // 总页数
    PdfTemplate totalPage;
    Font hfFont;

    {
        try {
            hfFont = new Font(BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED), 8, Font.NORMAL);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        totalPage = cb.createTemplate(30, 16);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(3);
        try {
            table.setTotalWidth(PageSize.A4.getWidth() - 100);
            table.setWidths(new int[] { 24, 24, 3});
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(-10);
            table.getDefaultCell().setBorder(Rectangle.BOTTOM);

            table.addCell(new Paragraph("厦门大学考试管理系统印制 日期：" +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), hfFont));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new Paragraph("第" + writer.getPageNumber() + "页/", hfFont));

            PdfPCell cell = new PdfPCell(Image.getInstance(totalPage));
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            table.writeSelectedRows(0,
                    -1,
                    50,
                    PageSize.A4.getHeight() - 20,
                    writer.getDirectContent());
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        String text = "总" + (writer.getPageNumber()) + "页";
        ColumnText.showTextAligned(totalPage,
                Element.ALIGN_LEFT, new Paragraph(text,hfFont), 2, 2, 0);
    }

}
