package com.riceroll.mapper;
import java.util.List;

import com.riceroll.pojo.Captcha;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author PolarWS
* @description 针对表【captcha】的数据库操作Mapper
* @createDate 2025-04-12 17:07:33
* @Entity com.riceroll.pojo.Captcha
*/
public interface CaptchaMapper extends BaseMapper<Captcha> {
    List<Captcha> selectAll();

    List<Captcha> randomSelect();
}




