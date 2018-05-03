package com.bacali;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import java.io.*;
import java.math.BigInteger;

public class CreatePDF {

    private static XWPFDocument document = new XWPFDocument();
    private static String filesPath = "D:\\ACM-Algorithms\\src\\com\\eniso\\acm";
    private static String docPath = "C:\\Users\\BacAli\\Desktop\\ACM NoteBook 2018.docx";

    public static void main(String[] args) throws IOException {
        initFile();
        File file = new File(filesPath);
        try {
            FileOutputStream out = new FileOutputStream(docPath);
            int index = 1;
            for (File f : file.listFiles()) {
                createDocx(f, "", index++, 1);
            }
            document.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initFile() {
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(720L));
        pageMar.setTop(BigInteger.valueOf(720L));
        pageMar.setRight(BigInteger.valueOf(720L));
        pageMar.setBottom(BigInteger.valueOf(720L));
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setFontFamily("LM Roman 10");
        run.addBreak();
        run.addBreak();
        run.setFontSize(16);
        run.setText("ecole nationale d'ingenieurs de sousse".toUpperCase());
        run.addBreak();
        run.addBreak();
        run.setText("CLUB ACM ENISo");
        run.addBreak();
        run.addBreak();
        run.addBreak();
        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph.createRun();
        run.setFontFamily("LM Roman 10");
        run.setFontSize(27);
        run.setBold(true);
        run.setText("ACM Notebook 2018");
        run.addBreak(BreakType.PAGE);
    }

    private static void createDocx(File file, String order, int index, int depth) throws IOException {
        if (file.isDirectory()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setSpacingAfter(200);
            paragraph.setSpacingBefore(200);
            XWPFRun run = paragraph.createRun();
            run.setFontFamily("LM Roman 10");
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                builder.append("    ");
            }
            builder.append(order).append(index).append(". ").append(file.getName());
            run.setText(builder.toString());
            run.setFontSize(15);
            int idx = 1;
            for (File f : file.listFiles()) {
                createDocx(f, order + (index) + ".", idx++, depth + 1);
            }
        } else {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setSpacingAfter(100);
            paragraph.setSpacingBefore(100);
            XWPFRun run = paragraph.createRun();
            run.setFontFamily("LM Roman 10");
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                builder.append("    ");
            }
            builder.append(order).append(index).append(". ").append(file.getName().subSequence(0, file.getName().indexOf(".")));
            run.setText(builder.toString());
            run.setFontSize(13);
            run.addBreak();
            run.addBreak();
            paragraph.setSpacingAfter(0);
            paragraph.setSpacingBefore(0);
            run = paragraph.createRun();
            run.setFontSize(10);
            run.setFontFamily("Monospac821 BT");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int s = 0;
            while ((line = reader.readLine()) != null) {
                if ((line.equals("") && s == 0)
                        || line.contains("com.eniso.acm")
                        || line.contains("import java.")) continue;
                s++;
                run.setText(line);
                run.addBreak();
            }
        }
    }
}
