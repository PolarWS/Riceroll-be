package com.riceroll.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.riceroll.pojo.Comments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author PolarWS
* @description 针对表【comments】的数据库操作Service
* @createDate 2025-04-11 15:37:20
*/
public interface CommentsService extends IService<Comments> {
    public List<Comments> getComments(String url, Page<Comments> page);

    public List<Comments> getCommentsByUuid(String uuid);

    public List<Comments> getCommentsByRid(String rid);
}
