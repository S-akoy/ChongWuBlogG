package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ChongwuzhishiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ChongwuzhishiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ChongwuzhishiView;


/**
 * 宠物知识
 *
 * @author 
 * @email 
 * @date 2025-05-01 08:35:24
 */
public interface ChongwuzhishiService extends IService<ChongwuzhishiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ChongwuzhishiVO> selectListVO(Wrapper<ChongwuzhishiEntity> wrapper);
   	
   	ChongwuzhishiVO selectVO(@Param("ew") Wrapper<ChongwuzhishiEntity> wrapper);
   	
   	List<ChongwuzhishiView> selectListView(Wrapper<ChongwuzhishiEntity> wrapper);
   	
   	ChongwuzhishiView selectView(@Param("ew") Wrapper<ChongwuzhishiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ChongwuzhishiEntity> wrapper);

   	

}

