package it.vitalegi.docsmagic;

import it.vitalegi.docsmagic.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@AllArgsConstructor
@Slf4j
public class Pdf2Pngs {
    Path pdf;
    Path outDir;

    public void convert() {
        log.info("PDF to PNGs");
        log.info("File:       {}", pdf);
        log.info("Output dir: {}", outDir);

        try (var doc = load(pdf)) {
            var pdfRenderer = new PDFRenderer(doc);
            for (int page = 0; page < doc.getNumberOfPages(); ++page) {
                var image = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                saveImage(image, page);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected PDDocument load(Path pdf) {
        try {
            return Loader.loadPDF(pdf.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void saveImage(BufferedImage image, int page) throws IOException {
        var file = getImageFile(page, "png");
        log.info("Save as {}", file);
        ImageIO.write(image, "PNG", file);
    }

    protected File getImageFile(int page, String extension) {
        var name = pdf.toFile().getName();
        name = name.substring(0, name.length() - ".pdf".length()) + "_" + StringUtil.leftPad("" + page, 3, '0') + "." + extension;
        if (outDir != null) {
            return outDir.resolve(name).toFile();
        }
        return Path.of(name).toFile();
    }
}
