package com.riceroll.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.riceroll.mapper.MemeGroupMapper;
import com.riceroll.pojo.Meme;
import com.riceroll.pojo.MemeGroup;
import com.riceroll.service.MemeService;
import com.riceroll.mapper.MemeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author PolarWS
* @description 针对表【meme】的数据库操作Service实现
* @createDate 2025-04-12 16:01:05
*/
@Service
public class MemeServiceImpl extends ServiceImpl<MemeMapper, Meme>
    implements MemeService{
    @Autowired
    private MemeMapper memeMapper;

    @Autowired
    private MemeGroupMapper memeGroupMapper;

    public List<Meme> getMemes(Integer id) {
        return memeMapper.selectByGroupid(id);
    }

    public List<MemeGroup> getMemeGroups() {
        return memeGroupMapper.selectAll();
    }
}




