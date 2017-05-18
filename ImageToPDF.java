package com.pdf;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ImageToPDF {
	
	private static final Logger log = Logger.getLogger("ImageToPDF");
	
	private PDDocument document = new PDDocument();
	private List<InputStream> streams;	
	
	public ImageToPDF(List<InputStream> streams) {
		this.streams = streams;
	}
	
	public ImageToPDF() {
		
	}

	public PDDocument getDocument() {
		return document;
	}

	public void setDocument(PDDocument document) {
		this.document = document;
	}
	
	public void saveDocument(String fileName) throws Exception {
		log.info("Saving document !!");
		document.save(fileName);
		document.close();
	}

	public void preparePages(InputStream in) throws Exception {
		if (in != null) {
			BufferedImage bimg = ImageIO.read(in);
			float width = bimg.getWidth();
			float height = bimg.getHeight();
			PDPage page = new PDPage(new PDRectangle(width, height));
			document.addPage(page); 
			PDImageXObject img = JPEGFactory.createFromImage(document, bimg);
			
			try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
				contentStream.drawImage(img, 0, 0);
			}
		}
	}
	
	public void processStream(String fileName) throws Exception {
		for(InputStream is : streams) {
			preparePages(is);
		}
		log.info("processStream !");
		saveDocument(fileName);
	}
	
	// test
	public static void main(String[] args) throws Exception {
		ImageToPDF itp = new ImageToPDF();
		InputStream in = new FileInputStream("C:/Users/ShameerA/Pictures/Jellyfish.jpg");
		InputStream in1 = new FileInputStream("C:/Users/ShameerA/Pictures/Capture.PNG");
		
		itp.preparePages(in1);
		itp.saveDocument("test12.pdf");
		
		in.close();
		//in1.close();
	}
}


/*{"messageCode":200,"result":{"imageurl":"http://jiomags.cdn.jio.com/content/entry/jiomags/content/","data":[{"page":1,"high_res":"732/18/high_res/18_h_0.jpg","low_res":"732/18/low_res/18_l_0.jpg"},{"page":2,"high_res":"732/18/high_res/18_h_1.jpg","low_res":"732/18/low_res/18_l_1.jpg"},{"page":3,"high_res":"732/18/high_res/18_h_2.jpg","low_res":"732/18/low_res/18_l_2.jpg"},{"page":4,"high_res":"732/18/high_res/18_h_3.jpg","low_res":"732/18/low_res/18_l_3.jpg"},{"page":5,"high_res":"732/18/high_res/18_h_4.jpg","low_res":"732/18/low_res/18_l_4.jpg"},{"page":6,"high_res":"732/18/high_res/18_h_5.jpg","low_res":"732/18/low_res/18_l_5.jpg"},{"page":7,"high_res":"732/18/high_res/18_h_6.jpg","low_res":"732/18/low_res/18_l_6.jpg"}]}}*/