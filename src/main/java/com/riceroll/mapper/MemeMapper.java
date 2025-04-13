package com.riceroll.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.riceroll.pojo.Meme;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author PolarWS
* @description 针对表【meme】的数据库操作Mapper
* @createDate 2025-04-12 16:01:05
* @Entity com.riceroll.pojo.Meme
*/
public interface MemeMapper extends BaseMapper<Meme> {
    List<Meme> selectByGroupid(@Param("groupid") Integer groupid);
}




