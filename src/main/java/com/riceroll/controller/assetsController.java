package com.riceroll.controller;

import com.riceroll.dto.assets.imageDTO;
import com.riceroll.dto.assets.markdownPageDTO;
import com.riceroll.dto.assets.memeDTO;
import com.riceroll.utils.ApiResponse;
import com.riceroll.vo.assets.markdownPageVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class assetsController {
    @GetMapping("/image/{filename}")
    public ResponseEntity<?> getImage(@PathVariable String filename, imageDTO imageDTO){
        imageDTO.setFilename(filename);
        return null;
    }

    @GetMapping("/markdownPage")
    public ApiResponse<markdownPageVO> getMarkdownPage(@ModelAttribute markdownPageDTO markdownPageDTO){
        return null;
    }

    @GetMapping("/meme/{pathname}/{filename}")
    public ResponseEntity<?> getMeme(@RequestParam String pathname, @ModelAttribute String filename, memeDTO memeDTO){
        memeDTO.setPathname(pathname);
        memeDTO.setFilename(filename);
        return null;
    }
}
