package com.bacali;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import java.io.*;
import java.math.BigInteger;

public class CreateFile {

    private static XWPFDocument document = new XWPFDocument();

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Enter directory path!");
        } else {
            System.out.println("Creating File ...");
            String filesPath = args[0];
            File file = new File(filesPath);
            try {
                FileOutputStream out = new FileOutputStream(file.getName() + ".docx");
                int index = 1;
                for (File f : file.listFiles()) {
                    createDocx(f, "", index++, 1);
                }
                document.write(out);
                out.close();
            } catch (FileNotFoundException e) {
                System.out.println("Directory not found!!");
            } catch (IOException e) {
                System.out.println("Directory not found!!");
            }
            System.out.println("Done.");
        }
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
                        || line.startsWith("import ")
                        || line.startsWith("package ")) continue;
                s++;
                run.setText(line);
                run.addBreak();
            }
        }
    }
}
