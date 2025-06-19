package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DiscusschongwuzhishiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.DiscusschongwuzhishiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.DiscusschongwuzhishiView;


/**
 * 宠物知识评论表
 *
 * @author 
 * @email 
 * @date 2025-05-01 08:35:25
 */
public interface DiscusschongwuzhishiService extends IService<DiscusschongwuzhishiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DiscusschongwuzhishiVO> selectListVO(Wrapper<DiscusschongwuzhishiEntity> wrapper);
   	
   	DiscusschongwuzhishiVO selectVO(@Param("ew") Wrapper<DiscusschongwuzhishiEntity> wrapper);
   	
   	List<DiscusschongwuzhishiView> selectListView(Wrapper<DiscusschongwuzhishiEntity> wrapper);
   	
   	DiscusschongwuzhishiView selectView(@Param("ew") Wrapper<DiscusschongwuzhishiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<DiscusschongwuzhishiEntity> wrapper);

   	

}

