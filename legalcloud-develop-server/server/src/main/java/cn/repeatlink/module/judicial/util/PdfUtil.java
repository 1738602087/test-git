/**
 * 
 */
package cn.repeatlink.module.judicial.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * @author LAI
 * @date 2021-01-05 10:43
 */
public class PdfUtil {
	
	public static String getFullText(InputStream inputStream) {
		try {
			PDDocument doc = PDDocument.load(inputStream);
			return getFullText(doc);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static String getFullText(File pdfFile) {
		try {
			PDDocument doc = PDDocument.load(pdfFile);
			return getFullText(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getFullText(PDDocument doc) {
		try {
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(doc);
			return text;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(doc != null) {
				try {
					doc.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

}
