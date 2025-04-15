package com.riceroll.mapper;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import com.riceroll.pojo.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author PolarWS
* @description 针对表【comments】的数据库操作Mapper
* @createDate 2025-04-11 15:37:20
* @Entity com.riceroll.pojo.Comments
*/
public interface CommentsMapper extends BaseMapper<Comments> {
    Integer selectCount();

    List<Comments> selectCommentsNew();

    List<Comments> selectByUrlOrderByDateDesc(@Param("url") String url, Page<Comments> page);

    List<Comments> selectByUrlAndPidIsNullOrderByDateDesc(@Param("url") String url, Page<Comments> page);

    List<Comments> selectByUuidOrderByDateDesc(@Param("uuid") String uuid);

    List<Comments> selectByRidOrderByDateDesc(@Param("rid") String rid);
}




