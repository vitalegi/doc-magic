package it.vitalegi.docsmagic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class Pngs2Pdf {

    Path dir;
    String outPdf;

    public void convert() {
        log.info("PNGs to PDF");
        log.info("Dir:    {}", dir);
        var out = dir.resolve(outPdf).toString();
        log.info("Target: {}", out);

        try (var doc = new PDDocument()) {
            var images = getImages(".png");
            if (images.isEmpty()) {
                throw new IllegalArgumentException("No images found");
            }
            for (var image : images) {
                log.info("Add {}", image);
                addImage(doc, PDRectangle.A4, image);
            }
            doc.save(out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void addImage(PDDocument doc, PDRectangle size, Path image) throws IOException {
        var page = new PDPage(size);
        doc.addPage(page);
        BufferedImage awtImage = ImageIO.read(image.toFile());
        var pdImageXObject = LosslessFactory.createFromImage(doc, awtImage);
        var contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);
        float pageW = size.getWidth();
        float pageH = size.getHeight();
        float pageR = pageW / pageH;
        int imageW = awtImage.getWidth();
        int imageH = awtImage.getHeight();
        float imageR = ((float) imageW) / imageH;

        float targetW = Math.min(pageW, imageW);
        float targetH = targetW / imageR;

        //contentStream.drawImage(pdImageXObject, 200, 300, awtImage.getWidth() / 2, awtImage.getHeight() / 2);
        contentStream.drawImage(pdImageXObject, (pageW - targetW) / 2, (pageH - targetH) / 2, targetW, targetH);
        contentStream.close();
    }

    protected List<Path> getImages(String extension) {
        try {
            return Files.list(dir).filter(Files::isRegularFile).filter(f -> f.toFile().getName().toLowerCase().endsWith(extension)).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
