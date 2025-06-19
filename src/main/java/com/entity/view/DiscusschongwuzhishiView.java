package com.entity.view;

import com.entity.DiscusschongwuzhishiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.utils.EncryptUtil;
 

/**
 * 宠物知识评论表
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2025-05-01 08:35:25
 */
@TableName("discusschongwuzhishi")
public class DiscusschongwuzhishiView  extends DiscusschongwuzhishiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public DiscusschongwuzhishiView(){
	}
 
 	public DiscusschongwuzhishiView(DiscusschongwuzhishiEntity discusschongwuzhishiEntity){
 	try {
			BeanUtils.copyProperties(this, discusschongwuzhishiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
