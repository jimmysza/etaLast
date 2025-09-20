package main.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private final Path rootFolder = Paths.get("uploads");

    public UploadFileServiceImpl() {
        try {
            if (!Files.exists(rootFolder)) {
                Files.createDirectories(rootFolder);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creando carpeta uploads: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path filePath = rootFolder.resolve(filename).toAbsolutePath();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("No se pudo leer el archivo: " + filename);
        }
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = rootFolder.resolve(uniqueFileName).toAbsolutePath();
        Files.copy(file.getInputStream(), filePath);
        return uniqueFileName;
    }

    @Override
    public boolean delete(String filename) {
        if (filename != null && !filename.isEmpty()) {
            Path filePath = rootFolder.resolve(filename).toAbsolutePath();
            File file = filePath.toFile();
            if (file.exists() && file.canRead()) {
                return file.delete();
            }
        }
        return false;
    }
}
