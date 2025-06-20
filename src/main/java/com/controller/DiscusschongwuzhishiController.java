package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.lang.*;
import java.math.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import com.utils.ValidatorUtils;
import com.utils.DeSensUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.DiscusschongwuzhishiEntity;
import com.entity.view.DiscusschongwuzhishiView;

import com.service.DiscusschongwuzhishiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.EncryptUtil;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 宠物知识评论表
 * 后端接口
 * @author 
 * @email 
 * @date 2025-05-01 08:35:25
 */
@RestController
@RequestMapping("/discusschongwuzhishi")
public class DiscusschongwuzhishiController {
    @Autowired
    private DiscusschongwuzhishiService discusschongwuzhishiService;
    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,DiscusschongwuzhishiEntity discusschongwuzhishi,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<DiscusschongwuzhishiEntity> ew = new EntityWrapper<DiscusschongwuzhishiEntity>();


        //查询结果
		PageUtils page = discusschongwuzhishiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discusschongwuzhishi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,DiscusschongwuzhishiEntity discusschongwuzhishi, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<DiscusschongwuzhishiEntity> ew = new EntityWrapper<DiscusschongwuzhishiEntity>();

        //查询结果
		PageUtils page = discusschongwuzhishiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discusschongwuzhishi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DiscusschongwuzhishiEntity discusschongwuzhishi){
       	EntityWrapper<DiscusschongwuzhishiEntity> ew = new EntityWrapper<DiscusschongwuzhishiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( discusschongwuzhishi, "discusschongwuzhishi")); 
        return R.ok().put("data", discusschongwuzhishiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DiscusschongwuzhishiEntity discusschongwuzhishi){
        EntityWrapper< DiscusschongwuzhishiEntity> ew = new EntityWrapper< DiscusschongwuzhishiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( discusschongwuzhishi, "discusschongwuzhishi")); 
		DiscusschongwuzhishiView discusschongwuzhishiView =  discusschongwuzhishiService.selectView(ew);
		return R.ok("查询宠物知识评论表成功").put("data", discusschongwuzhishiView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DiscusschongwuzhishiEntity discusschongwuzhishi = discusschongwuzhishiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(discusschongwuzhishi,deSens);
        return R.ok().put("data", discusschongwuzhishi);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DiscusschongwuzhishiEntity discusschongwuzhishi = discusschongwuzhishiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(discusschongwuzhishi,deSens);
        return R.ok().put("data", discusschongwuzhishi);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DiscusschongwuzhishiEntity discusschongwuzhishi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discusschongwuzhishi);
        discusschongwuzhishiService.insert(discusschongwuzhishi);
        return R.ok().put("data",discusschongwuzhishi.getId());
    }
    
    /**
     * 文章评论
     */

    @RequestMapping("/add")
    public R add(@RequestBody DiscusschongwuzhishiEntity discusschongwuzhishi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discusschongwuzhishi);
        discusschongwuzhishiService.insert(discusschongwuzhishi);
        return R.ok().put("data",discusschongwuzhishi.getId());
    }



    /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        DiscusschongwuzhishiEntity discusschongwuzhishi = discusschongwuzhishiService.selectOne(new EntityWrapper<DiscusschongwuzhishiEntity>().eq("", username));
        return R.ok().put("data", discusschongwuzhishi);
    }


    /**
     * 文章评论更新
     */

    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody DiscusschongwuzhishiEntity discusschongwuzhishi, HttpServletRequest request){
    //ValidatorUtils.validateEntity(discusschongwuzhishi);
    //全部更新
        discusschongwuzhishiService.updateById(discusschongwuzhishi);
        return R.ok();
    }



    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        discusschongwuzhishiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,DiscusschongwuzhishiEntity discusschongwuzhishi, HttpServletRequest request,String pre){
        EntityWrapper<DiscusschongwuzhishiEntity> ew = new EntityWrapper<DiscusschongwuzhishiEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        // 组装参数
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        params.put("order", "desc");

		PageUtils page = discusschongwuzhishiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discusschongwuzhishi), params), params));
        return R.ok().put("data", page);
    }








}
