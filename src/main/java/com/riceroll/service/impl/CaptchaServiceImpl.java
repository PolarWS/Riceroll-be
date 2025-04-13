package com.riceroll.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.riceroll.pojo.Captcha;
import com.riceroll.service.CaptchaService;
import com.riceroll.mapper.CaptchaMapper;
import com.riceroll.utils.BeanMapperUtils;
import com.riceroll.utils.MemoryStoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PolarWS
 * @description 针对表【captcha】的数据库操作Service实现
 * @createDate 2025-04-12 17:07:33
 */
@Service
public class CaptchaServiceImpl extends ServiceImpl<CaptchaMapper, Captcha>
        implements CaptchaService {

    @Autowired
    private CaptchaMapper captchaMapper;

    @Autowired
    private MemoryStoreUtils memoryStore;


    public void saveCaptcha() {
        List<Captcha> captchas = captchaMapper.selectAll();
        for (Captcha captcha : captchas) {
            Map<String, Object> captchaData = new HashMap<>();
            captchaData.put("url", captcha.getUrl());
            captchaData.put("rotation", captcha.getRotation());
            memoryStore.set("captcha:" + captcha.getId(), captchaData);
        }
    }

    public Captcha randomCaptcha() {
        List<Captcha> captchaList = getFromMemoryStore();

        if (captchaList == null || captchaList.isEmpty()) {
            captchaList = captchaMapper.randomSelect();
        }

        if (captchaList != null && !captchaList.isEmpty()) {
            int index = (int) (Math.random() * captchaList.size());
            return captchaList.get(index);
        }

        return null;
    }

    private List<Captcha> getFromMemoryStore() {
        List<Object> rawList = memoryStore.fuzzySearch("captcha:");
        if (rawList == null || rawList.isEmpty()) return null;

        try {
            return BeanMapperUtils.mapList(rawList, Captcha.class);
        } catch (Exception e) {
            return null;
        }
    }
}




