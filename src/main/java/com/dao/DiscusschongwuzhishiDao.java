package com.dao;

import com.entity.DiscusschongwuzhishiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.DiscusschongwuzhishiVO;
import com.entity.view.DiscusschongwuzhishiView;


/**
 * 宠物知识评论表
 * 
 * @author 
 * @email 
 * @date 2025-05-01 08:35:25
 */
public interface DiscusschongwuzhishiDao extends BaseMapper<DiscusschongwuzhishiEntity> {
	
	List<DiscusschongwuzhishiVO> selectListVO(@Param("ew") Wrapper<DiscusschongwuzhishiEntity> wrapper);
	
	DiscusschongwuzhishiVO selectVO(@Param("ew") Wrapper<DiscusschongwuzhishiEntity> wrapper);
	
	List<DiscusschongwuzhishiView> selectListView(@Param("ew") Wrapper<DiscusschongwuzhishiEntity> wrapper);

	List<DiscusschongwuzhishiView> selectListView(Pagination page,@Param("ew") Wrapper<DiscusschongwuzhishiEntity> wrapper);

	
	DiscusschongwuzhishiView selectView(@Param("ew") Wrapper<DiscusschongwuzhishiEntity> wrapper);
	

}
