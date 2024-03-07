package it.vitalegi.docsmagic;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class App {

    public static void main(String[] args) throws Exception {
        var out = System.out;
        out.println("Available modes:");
        out.println("- pdf2png path/to/file.pdf");
        out.println("  export each page of the pdf as an image, target folder is the same where the pdf is");
        out.println();
        out.println("- png2pdf path/to/dir file.pdf");
        out.println("  creates a pdf starting from all the png images found in the directory");

        var mode = args[0];
        if ("pdf2png".equalsIgnoreCase(mode)) {
            var path = Path.of(args[1]);
            if (isPdf(path)) {
                var folder = path.getParent();
                new Pdf2Pngs(path, folder).convert();
            } else {
                throw new IllegalArgumentException("Path " + path + " is not a pdf");
            }
        }
        if ("png2pdf".equalsIgnoreCase(mode)) {
            var dir = Path.of(args[1]);
            if (Files.isDirectory(dir)) {
                var targetPdf = args[2];
                new Pngs2Pdf(dir, targetPdf).convert();
            } else {
                throw new IllegalArgumentException("Path " + dir + " is not a directory");
            }
        }
    }

    static boolean isPdf(Path path) {
        return Files.isRegularFile(path) && path.toFile().getName().toLowerCase().endsWith(".pdf");
    }
}