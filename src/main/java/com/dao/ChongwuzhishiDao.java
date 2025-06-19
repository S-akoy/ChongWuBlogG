package com.dao;

import com.entity.ChongwuzhishiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ChongwuzhishiVO;
import com.entity.view.ChongwuzhishiView;


/**
 * 宠物知识
 * 
 * @author 
 * @email 
 * @date 2025-05-01 08:35:24
 */
public interface ChongwuzhishiDao extends BaseMapper<ChongwuzhishiEntity> {
	
	List<ChongwuzhishiVO> selectListVO(@Param("ew") Wrapper<ChongwuzhishiEntity> wrapper);
	
	ChongwuzhishiVO selectVO(@Param("ew") Wrapper<ChongwuzhishiEntity> wrapper);
	
	List<ChongwuzhishiView> selectListView(@Param("ew") Wrapper<ChongwuzhishiEntity> wrapper);

	List<ChongwuzhishiView> selectListView(Pagination page,@Param("ew") Wrapper<ChongwuzhishiEntity> wrapper);

	
	ChongwuzhishiView selectView(@Param("ew") Wrapper<ChongwuzhishiEntity> wrapper);
	

}
