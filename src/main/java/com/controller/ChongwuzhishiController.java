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

import com.entity.ChongwuzhishiEntity;
import com.entity.view.ChongwuzhishiView;

import com.service.ChongwuzhishiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.EncryptUtil;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 宠物知识
 * 后端接口
 * @author 
 * @email 
 * @date 2025-05-01 08:35:24
 */
@RestController
@RequestMapping("/chongwuzhishi")
public class ChongwuzhishiController {
    @Autowired
    private ChongwuzhishiService chongwuzhishiService;

    @Autowired
    private StoreupService storeupService;

    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ChongwuzhishiEntity chongwuzhishi,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ChongwuzhishiEntity> ew = new EntityWrapper<ChongwuzhishiEntity>();


        //查询结果
		PageUtils page = chongwuzhishiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chongwuzhishi), params), params));
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
    public R list(@RequestParam Map<String, Object> params,ChongwuzhishiEntity chongwuzhishi, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ChongwuzhishiEntity> ew = new EntityWrapper<ChongwuzhishiEntity>();

        //查询结果
		PageUtils page = chongwuzhishiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chongwuzhishi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ChongwuzhishiEntity chongwuzhishi){
       	EntityWrapper<ChongwuzhishiEntity> ew = new EntityWrapper<ChongwuzhishiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( chongwuzhishi, "chongwuzhishi")); 
        return R.ok().put("data", chongwuzhishiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ChongwuzhishiEntity chongwuzhishi){
        EntityWrapper< ChongwuzhishiEntity> ew = new EntityWrapper< ChongwuzhishiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( chongwuzhishi, "chongwuzhishi")); 
		ChongwuzhishiView chongwuzhishiView =  chongwuzhishiService.selectView(ew);
		return R.ok("查询宠物知识成功").put("data", chongwuzhishiView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ChongwuzhishiEntity chongwuzhishi = chongwuzhishiService.selectById(id);
        if(null==chongwuzhishi.getClicknum()){
            chongwuzhishi.setClicknum(0);
        }
		chongwuzhishi.setClicknum(chongwuzhishi.getClicknum()+1);
		chongwuzhishi.setClicktime(new Date());
		chongwuzhishiService.updateById(chongwuzhishi);
        chongwuzhishi = chongwuzhishiService.selectView(new EntityWrapper<ChongwuzhishiEntity>().eq("id", id));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(chongwuzhishi,deSens);
        return R.ok().put("data", chongwuzhishi);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ChongwuzhishiEntity chongwuzhishi = chongwuzhishiService.selectById(id);
        if(null==chongwuzhishi.getClicknum()){
            chongwuzhishi.setClicknum(0);
        }
		chongwuzhishi.setClicknum(chongwuzhishi.getClicknum()+1);
		chongwuzhishi.setClicktime(new Date());
		chongwuzhishiService.updateById(chongwuzhishi);
        chongwuzhishi = chongwuzhishiService.selectView(new EntityWrapper<ChongwuzhishiEntity>().eq("id", id));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(chongwuzhishi,deSens);
        return R.ok().put("data", chongwuzhishi);
    }
    


    /**
     * 赞或踩
     */
    @RequestMapping("/thumbsup/{id}")
    public R vote(@PathVariable("id") String id,String type){
        ChongwuzhishiEntity chongwuzhishi = chongwuzhishiService.selectById(id);
        if(type.equals("1")) {
        	chongwuzhishi.setThumbsupnum(chongwuzhishi.getThumbsupnum()+1);
        } else {
        	chongwuzhishi.setCrazilynum(chongwuzhishi.getCrazilynum()+1);
        }
        chongwuzhishiService.updateById(chongwuzhishi);
        return R.ok("投票成功");
    }

    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ChongwuzhishiEntity chongwuzhishi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chongwuzhishi);
        chongwuzhishiService.insert(chongwuzhishi);
        return R.ok().put("data",chongwuzhishi.getId());
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ChongwuzhishiEntity chongwuzhishi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chongwuzhishi);
        chongwuzhishiService.insert(chongwuzhishi);
        return R.ok().put("data",chongwuzhishi.getId());
    }





    /**
     * 宠物知识修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ChongwuzhishiEntity chongwuzhishi, HttpServletRequest request){
    //ValidatorUtils.validateEntity(chongwuzhishi);
    //全部更新
        chongwuzhishiService.updateById(chongwuzhishi);
        return R.ok();
    }




    

    /**
     * 宠物知识删除
     */

    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        chongwuzhishiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,ChongwuzhishiEntity chongwuzhishi, HttpServletRequest request,String pre){
        EntityWrapper<ChongwuzhishiEntity> ew = new EntityWrapper<ChongwuzhishiEntity>();
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
		params.put("sort", "clicknum");
        params.put("order", "desc");

		PageUtils page = chongwuzhishiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chongwuzhishi), params), params));
        return R.ok().put("data", page);
    }


    /**
     * 协同算法（按收藏推荐）
     */
    @RequestMapping("/autoSort2")
    public R autoSort2(@RequestParam Map<String, Object> params,ChongwuzhishiEntity chongwuzhishi, HttpServletRequest request){
        String userId = request.getSession().getAttribute("userId").toString();
        String inteltypeColumn = "zhishifenlei";
        // 查询收藏集合
        List<StoreupEntity> storeups = storeupService.selectList(new EntityWrapper<StoreupEntity>().eq("type", 1).eq("userid", userId).eq("tablename", "chongwuzhishi").orderBy("addtime", false));
        List<String> inteltypes = new ArrayList<String>();
        Integer limit = params.get("limit")==null?10:Integer.parseInt(params.get("limit").toString());
        List<ChongwuzhishiEntity> chongwuzhishiList = new ArrayList<ChongwuzhishiEntity>();
        //去重
        if(storeups!=null && storeups.size()>0) {
            List<String> typeList = new ArrayList<String>();
            for(StoreupEntity s : storeups) {
                if(typeList.contains(s.getInteltype())) continue;
                typeList.add(s.getInteltype());
                chongwuzhishiList.addAll(chongwuzhishiService.selectList(new EntityWrapper<ChongwuzhishiEntity>().eq(inteltypeColumn, s.getInteltype())));
            }
        }
        EntityWrapper<ChongwuzhishiEntity> ew = new EntityWrapper<ChongwuzhishiEntity>();
        params.put("sort", "id");
        params.put("order", "desc");
        // 根据协同结果查询结果并返回
        PageUtils page = chongwuzhishiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chongwuzhishi), params), params));
        List<ChongwuzhishiEntity> pageList = (List<ChongwuzhishiEntity>)page.getList();
        if(chongwuzhishiList.size()<limit) {
            int toAddNum = (limit-chongwuzhishiList.size())<=pageList.size()?(limit-chongwuzhishiList.size()):pageList.size();
            for(ChongwuzhishiEntity o1 : pageList) {
                boolean addFlag = true;
                for(ChongwuzhishiEntity o2 : chongwuzhishiList) {
                    if(o1.getId().intValue()==o2.getId().intValue()) {
                        addFlag = false;
                        break;
                    }
                }
                if(addFlag) {
                    chongwuzhishiList.add(o1);
                    if(--toAddNum==0) break;
                }
            }
        } else if(chongwuzhishiList.size()>limit) {
            chongwuzhishiList = chongwuzhishiList.subList(0, limit);
        }
        page.setList(chongwuzhishiList);
        return R.ok().put("data", page);
    }






}
