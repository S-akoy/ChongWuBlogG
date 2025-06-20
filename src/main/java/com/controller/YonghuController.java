package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.lang.*;
import java.math.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import com.entity.TokenEntity;
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

import com.entity.YonghuEntity;
import com.entity.view.YonghuView;

import com.service.YonghuService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.EncryptUtil;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 用户
 * 后端接口
 * @author 
 * @email 
 * @date 2025-05-01 08:35:24
 */
@RestController
@RequestMapping("/yonghu")
public class YonghuController {
    @Autowired
    private YonghuService yonghuService;
	@Autowired
	private TokenService tokenService;
	
	/**
	 * 用户登录
	 */



	
	/**
     * 注册
     */


	
	/**
	 * 退出
	 */

	
	/**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        YonghuEntity u = yonghuService.selectById(id);
        return R.ok().put("data", u);
    }
    
    /**
     * 密码重置
     */
    @IgnoreAuth
	@RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
    	//根据登录账号判断是否存在用户信息，否则返回错误信息
        YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("yonghuming", username));
    	if(u==null) {
    		return R.error("账号不存在");
    	}
        //重置密码为123456，并使用md5方式加密
        u.setMima(EncryptUtil.md5("123456"));
        yonghuService.updateById(u);
        return R.ok("密码已重置为：123456");
    }



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,YonghuEntity yonghu,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<YonghuEntity> ew = new EntityWrapper<YonghuEntity>();


        //查询结果
		PageUtils page = yonghuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yonghu), params), params));
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
    public R list(@RequestParam Map<String, Object> params,YonghuEntity yonghu, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<YonghuEntity> ew = new EntityWrapper<YonghuEntity>();

        //查询结果
		PageUtils page = yonghuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yonghu), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( YonghuEntity yonghu){
       	EntityWrapper<YonghuEntity> ew = new EntityWrapper<YonghuEntity>();
      	ew.allEq(MPUtil.allEQMapPre( yonghu, "yonghu")); 
        return R.ok().put("data", yonghuService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(YonghuEntity yonghu){
        EntityWrapper< YonghuEntity> ew = new EntityWrapper< YonghuEntity>();
 		ew.allEq(MPUtil.allEQMapPre( yonghu, "yonghu")); 
		YonghuView yonghuView =  yonghuService.selectView(ew);
		return R.ok("查询用户成功").put("data", yonghuView);
    }
	
    /**
     * 用户信息查看
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        YonghuEntity yonghu = yonghuService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(yonghu,deSens);
        return R.ok().put("data", yonghu);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        YonghuEntity yonghu = yonghuService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(yonghu,deSens);
        return R.ok().put("data", yonghu);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody YonghuEntity yonghu, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(yonghuService.selectCount(new EntityWrapper<YonghuEntity>().eq("yonghuming", yonghu.getYonghuming()))>0) {
            return R.error("用户名已存在");
        }
        //ValidatorUtils.validateEntity(yonghu);
        //验证账号唯一性，否则返回错误信息
        YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("yonghuming", yonghu.getYonghuming()));
        if(u!=null) {
            return R.error("用户已存在");
        }
    	yonghu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
		yonghu.setId(new Date().getTime());
        //密码使用md5方式加密
        yonghu.setMima(EncryptUtil.md5(yonghu.getMima()));
        yonghuService.insert(yonghu);
        return R.ok().put("data",yonghu.getId());
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody YonghuEntity yonghu, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(yonghuService.selectCount(new EntityWrapper<YonghuEntity>().eq("yonghuming", yonghu.getYonghuming()))>0) {
            return R.error("用户名已存在");
        }
        //ValidatorUtils.validateEntity(yonghu);
        //验证账号唯一性，否则返回错误信息
        YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("yonghuming", yonghu.getYonghuming()));
        if(u!=null) {
            return R.error("用户已存在");
        }
    	yonghu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
		yonghu.setId(new Date().getTime());
        //密码使用md5方式加密
        yonghu.setMima(EncryptUtil.md5(yonghu.getMima()));
        yonghuService.insert(yonghu);
        return R.ok().put("data",yonghu.getId());
    }


    /**
     * 发送找回密码邮件
     */
    @IgnoreAuth
    @RequestMapping("/sendemail/password")
    public R sendemailpassword(@RequestParam String email, @RequestParam String username, @RequestParam int type){
        // 设置邮箱信息
        StringBuilder sb = new StringBuilder();
        sb.append("Hi,"+username+"：" + "\r\n");
        sb.append("忘记密码了吗？别着急，请点击以下链接，我们协助您找回密码："+ "\r\n");
        if(type==1) {
            sb.append("http://localhost:8080/springboot8v545ay0/admin/dist/index.html#/register?pageFlag=security1&email="+email+"&username="+username+"&tablename=yonghu"+ "\r\n");
        } else {
            sb.append("http://localhost:8080/springboot8v545ay0/front/dist/index.html#/register?pageFlag=security1&email="+email+"&username="+username+"&tablename=yonghu"+ "\r\n");
        }
        sb.append("如果这不是您的邮件请忽略，很抱歉打扰您，请原谅。" + "\r\n");
        //发送邮件
        CommonUtil.sendEmail(email, "找回密码",sb.toString());
        return R.ok();
    }

     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        YonghuEntity yonghu = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("yonghuming", username));
        return R.ok().put("data", yonghu);
    }


    /**
     * 用户信息修改
     */

    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody YonghuEntity yonghu, HttpServletRequest request){
//ValidatorUtils.validateEntity(yonghu);
//验证字段唯⼀性，否则返回错误信息
        if(yonghuService.selectCount(new EntityWrapper<YonghuEntity>().ne("id", yonghu.getId()).eq("yonghuming", yonghu.getYonghuming()))>0) {
            return R.error("⽤户名已存在");
        }
        YonghuEntity yonghuEntity = yonghuService.selectById(yonghu.getId());
//如果密码不为空，则判断是否和输⼊密码⼀致，不⼀致则重新设置
        if(StringUtils.isNotBlank(yonghu.getMima()) && !yonghu.getMima().equals(yonghuEntity.getMima())) {
            //密码使⽤md5⽅式加密
            yonghu.setMima(EncryptUtil.md5(yonghu.getMima()));
        }
//全部更新
        yonghuService.updateById(yonghu);
        if(null!=yonghu.getYonghuming())
        {
            // 修改token
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setUsername(yonghu.getYonghuming());
            tokenService.update(tokenEntity, new EntityWrapper<TokenEntity>().eq(
                    "userid", yonghu.getId()));
        }
        return R.ok();
    }

    

    /**
     * 用户信息删除
     */

    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        yonghuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }







}
