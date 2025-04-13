package com.riceroll.mapper;
import java.util.List;

import com.riceroll.pojo.MemeGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author PolarWS
* @description 针对表【memeGroup】的数据库操作Mapper
* @createDate 2025-04-12 16:01:11
* @Entity com.riceroll.pojo.Memegroup
*/
public interface MemeGroupMapper extends BaseMapper<MemeGroup> {
    List<MemeGroup> selectAll();
}




