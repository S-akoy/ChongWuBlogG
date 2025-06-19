package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.ChongwuzhishiDao;
import com.entity.ChongwuzhishiEntity;
import com.service.ChongwuzhishiService;
import com.entity.vo.ChongwuzhishiVO;
import com.entity.view.ChongwuzhishiView;

@Service("chongwuzhishiService")
public class ChongwuzhishiServiceImpl extends ServiceImpl<ChongwuzhishiDao, ChongwuzhishiEntity> implements ChongwuzhishiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ChongwuzhishiEntity> page = this.selectPage(
                new Query<ChongwuzhishiEntity>(params).getPage(),
                new EntityWrapper<ChongwuzhishiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ChongwuzhishiEntity> wrapper) {
		  Page<ChongwuzhishiView> page =new Query<ChongwuzhishiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ChongwuzhishiVO> selectListVO(Wrapper<ChongwuzhishiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ChongwuzhishiVO selectVO(Wrapper<ChongwuzhishiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ChongwuzhishiView> selectListView(Wrapper<ChongwuzhishiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ChongwuzhishiView selectView(Wrapper<ChongwuzhishiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
