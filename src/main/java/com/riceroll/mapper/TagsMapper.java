package com.riceroll.mapper;
import java.util.List;

import com.riceroll.pojo.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author PolarWS
* @description 针对表【tags】的数据库操作Mapper
* @createDate 2025-04-15 14:44:46
* @Entity com.riceroll.domain.Tags
*/
public interface TagsMapper extends BaseMapper<Tags> {
    List<Tags> selectHots();
}




