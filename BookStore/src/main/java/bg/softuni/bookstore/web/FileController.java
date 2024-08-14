package bg.softuni.bookstore.web;

import bg.softuni.bookstore.application.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        return "upload-image";
    }

    @PostMapping("/api/files/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String imageUrl = fileService.uploadFile(file);
            model.addAttribute("imageUrl", imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error uploading file");
        }
        return "upload-image";
    }
}
