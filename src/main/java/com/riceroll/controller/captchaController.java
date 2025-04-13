package com.riceroll.controller;

import com.riceroll.dto.captcha.captchaCheckDTO;
import com.riceroll.dto.captcha.captchaImgDTO;
import com.riceroll.pojo.Captcha;
import com.riceroll.service.impl.CaptchaServiceImpl;
import com.riceroll.utils.ApiResponse;
import com.riceroll.utils.MemoryStoreUtils;
import com.riceroll.utils.MouseUtils;
import com.riceroll.vo.captcha.captchaCheckVO;
import com.riceroll.vo.captcha.captchaCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class captchaController {
    @Autowired
    private MemoryStoreUtils memoryStore;

    @Autowired
    private CaptchaServiceImpl captchaService;

    @Value("${static.rootpath}")
    private String rootPath;

    @PostMapping("/captchaCreate")
    public ApiResponse<captchaCreateVO> captchaCreate() {
        String captchaUserID = java.util.UUID.randomUUID().toString().replace("-", "");
        // 这里可以优化内存，只存id就行了，但是为了方便，就不优化了
        Captcha captcha = captchaService.randomCaptcha();
        if (captcha == null) {
            return ApiResponse.fail(404, "缺少验证码资源");
        }
        memoryStore.set("captchaUser:" + captchaUserID, captcha, 1000 * 60 * 10);
        captchaCreateVO captchaCreateVO = new captchaCreateVO(captchaUserID);
        return ApiResponse.success(captchaCreateVO);
    }

    @GetMapping("/captchaImg")
    public ResponseEntity<?> getCaptchaImg(@ModelAttribute captchaImgDTO captchaImgDTO) {
        Captcha captcha = (Captcha) memoryStore.get("captchaUser:" + captchaImgDTO.getId());
        if(captcha == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail(400, "验证码已过期"));
        }
        String url = captcha.getUrl();
        String path = rootPath + "/captchaImg/" + url;
        Path imagePath = Paths.get(path);
        if (!Files.exists(imagePath)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail(404, "验证码图片不存在"));
        }
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(path));
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail(500, "读取验证码图片失败"));
        }
    }

    @PostMapping("/captchaCheck")
    public ApiResponse<captchaCheckVO> captchaCheck(@RequestBody captchaCheckDTO captchaCheckDTO) {
        if (memoryStore.exists("captchaUser:" + captchaCheckDTO.getId())) {
            Captcha captcha = (Captcha) memoryStore.get("captchaUser:" + captchaCheckDTO.getId());
            if (captcha == null) {
                return ApiResponse.fail(400, "验证失败");
            }
            // 这里的鼠标轨迹认证应该还要加上切片去搜索历史记录（防止用爆破）之类的，但是为了方便，就不优化了
            if (Math.abs(captchaCheckDTO.getRotation() - captcha.getRotation()) < 10 && MouseUtils.isHuman(captchaCheckDTO.getMouseData().subList(0, Math.min(100, captchaCheckDTO.getMouseData().size())))) {
                memoryStore.delete("captchaUser:" + captchaCheckDTO.getId());
                String captchaPassID = java.util.UUID.randomUUID().toString().replace("-", "");
                memoryStore.set("captchaPassID:" + captchaPassID, captcha, 1000 * 60 * 10);
                captchaCheckVO captchaCheckVO = new captchaCheckVO(captchaPassID);
                return ApiResponse.success("验证成功", captchaCheckVO);
            }
            memoryStore.delete("captchaUser:" + captchaCheckDTO.getId());
        }
        return ApiResponse.fail(400, "验证失败");
    }
}
