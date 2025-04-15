package com.riceroll.controller;

import com.riceroll.dto.assets.imageDTO;
import com.riceroll.dto.assets.markdownPageDTO;
import com.riceroll.dto.assets.memeDTO;
import com.riceroll.service.impl.AssetsImpl;
import com.riceroll.utils.ApiResponse;
import com.riceroll.utils.FileValidationUtils;
import com.riceroll.vo.assets.markdownPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class assetsController {
    @Value("${static.rootpath}")
    private String rootPath;

    @Autowired
    private AssetsImpl assetsImpl;

    @GetMapping("/image/{filename}")
    public ResponseEntity<?> getImage(@PathVariable String filename, @ModelAttribute @Validated imageDTO imageDTO) {
        if (!FileValidationUtils.isValidFileExtension(filename)) {
            return ResponseEntity.ok(ApiResponse.fail(400, "非法文件名"));
        }
        imageDTO.setFilename(filename);
        Path path = Paths.get(rootPath, "image",imageDTO.getFilename()).normalize();
        Path target = path.resolve(filename).normalize();

        if (!target.startsWith(path)) {
            return ResponseEntity.ok(ApiResponse.fail(400, "非法路径"));
        }
        return resourceProcessing(path);
    }

    @GetMapping("/markdownPage")
    public ApiResponse<markdownPageVO> getMarkdownPage(@ModelAttribute @Validated markdownPageDTO markdownPageDTO) {
        if (!FileValidationUtils.isValidFileExtension(markdownPageDTO.getFile())) {
            return ApiResponse.fail(400, "非法文件名");
        }
        markdownPageVO markdownPageVO = new markdownPageVO();
        markdownPageVO.setMd(assetsImpl.getMarkdownPage(markdownPageDTO.getFile()));
        return ApiResponse.success(markdownPageVO);
    }

    @GetMapping("/meme/{pathname}/{filename}")
    public ResponseEntity<?> getMeme(@PathVariable String pathname,@PathVariable String filename, @ModelAttribute @Validated memeDTO memeDTO) {
        if (!FileValidationUtils.isValidFileExtension(filename)) {
            return ResponseEntity.ok(ApiResponse.fail(400, "非法文件名"));
        }
        Path path = Paths.get(rootPath, "meme", memeDTO.getPathname(), memeDTO.getFilename());
        Path target = path
                .resolve(memeDTO.getPathname())
                .resolve(memeDTO.getFilename())
                .normalize();

        if (!target.startsWith(path)) {
            return ResponseEntity.ok(ApiResponse.fail(400, "非法路径"));
        }
        return resourceProcessing(path);
    }

    private ResponseEntity<?> resourceProcessing(Path path) {
        if (!Files.exists(path)) {
            return ResponseEntity.ok(ApiResponse.fail(404, "资源不存在"));
        }
        try {
            byte[] imageBytes = Files.readAllBytes(path);
            String fileName = path.getFileName().toString();
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            MediaType mediaType = getMediaTypeByExtension(extension);
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.ok(ApiResponse.fail(500, "读取资源失败"));
        }
    }

    private MediaType getMediaTypeByExtension(String extension) {
        switch (extension) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "webp":
                return MediaType.valueOf("image/webp");
            case "bmp":
                return MediaType.valueOf("image/bmp");
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

}
