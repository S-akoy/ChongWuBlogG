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


import com.dao.DiscusschongwuzhishiDao;
import com.entity.DiscusschongwuzhishiEntity;
import com.service.DiscusschongwuzhishiService;
import com.entity.vo.DiscusschongwuzhishiVO;
import com.entity.view.DiscusschongwuzhishiView;

@Service("discusschongwuzhishiService")
public class DiscusschongwuzhishiServiceImpl extends ServiceImpl<DiscusschongwuzhishiDao, DiscusschongwuzhishiEntity> implements DiscusschongwuzhishiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DiscusschongwuzhishiEntity> page = this.selectPage(
                new Query<DiscusschongwuzhishiEntity>(params).getPage(),
                new EntityWrapper<DiscusschongwuzhishiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<DiscusschongwuzhishiEntity> wrapper) {
		  Page<DiscusschongwuzhishiView> page =new Query<DiscusschongwuzhishiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<DiscusschongwuzhishiVO> selectListVO(Wrapper<DiscusschongwuzhishiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public DiscusschongwuzhishiVO selectVO(Wrapper<DiscusschongwuzhishiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<DiscusschongwuzhishiView> selectListView(Wrapper<DiscusschongwuzhishiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public DiscusschongwuzhishiView selectView(Wrapper<DiscusschongwuzhishiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
