
package com.service.impl;


import java.util.*;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.TokenDao;
import com.entity.TokenEntity;
import com.entity.TokenEntity;
import com.service.TokenService;
import com.utils.CommonUtil;
import com.utils.PageUtils;
import com.utils.Query;


/**
 * token
 */
@Service("tokenService")
public class TokenServiceImpl extends ServiceImpl<TokenDao, TokenEntity> implements TokenService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<TokenEntity> page = this.selectPage(
                new Query<TokenEntity>(params).getPage(),
                new EntityWrapper<TokenEntity>()
        );
        return new PageUtils(page);
	}

	@Override
	public List<TokenEntity> selectListView(Wrapper<TokenEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params,
			Wrapper<TokenEntity> wrapper) {
		 Page<TokenEntity> page =new Query<TokenEntity>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
	}



	@Override
	public TokenEntity getTokenEntity(String token) {
		TokenEntity tokenEntity = this.selectOne(new EntityWrapper<TokenEntity>().eq("token", token));
		if(tokenEntity == null || tokenEntity.getExpiratedtime().getTime()<new Date().getTime()) {
			return null;
		}
		return tokenEntity;
	}

	@Override
	public String generateToken(Long id, String username, String users, String role) {
		// 生成唯一的 token
		String token = UUID.randomUUID().toString().replace("-", "");

		// 设置 token 的过期时间，例如 1 小时
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 1);
		Date expiratedTime = calendar.getTime();

		// 创建 TokenEntity 对象
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setUserid(id);
		tokenEntity.setUsername(username);
		tokenEntity.setTablename(users);
		tokenEntity.setRole(role);
		tokenEntity.setToken(token);
		tokenEntity.setExpiratedtime(expiratedTime);
		tokenEntity.setAddtime(new Date());

		// 保存 TokenEntity 对象到数据库
		this.insert(tokenEntity);

		return token;
	}
}
