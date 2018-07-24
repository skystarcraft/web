package com.guanjunhuanyige.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.guanjunhuanyige.content.service.contentService;
import com.guanjunhuanyige.jedis.JedisClient;
import com.guanjunhuanyige.mapper.TbContentMapper;
import com.guanjunhuanyige.pojo.TbContent;
import com.guanjunhuanyige.pojo.TbContentExample;
import com.guanjunhuanyige.pojo.TbContentExample.Criteria;
import com.guanjunhuanyige.utils.JsonUtils;
import com.guanjunhuanyige.utils.TaojinResult;

/*
 * 内容管理
 */
@Service
public class contentServiceImpl implements contentService{

	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	
	@Autowired
	private TbContentMapper cm;
	@Autowired
	private JedisClient jedisClient;
	
	@Override
	public TaojinResult addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		cm.insert(content);
		
		//缓存同步
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		
		return TaojinResult.ok();
	}

	
	/*
	 * 根据内容分类id查询内容列表
	 * @see com.guanjunhuanyige.content.service.contentService#getContentListByCid(long)
	 */
	@Override
	public List<TbContent> getContentListByCid(long cid) {
		try {
			String json = jedisClient.hget(CONTENT_LIST, cid + "");
			if (StringUtils.isNotBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		Criteria c = example.createCriteria();
		c.andCategoryIdEqualTo(cid);
		List<TbContent> list = cm.selectByExampleWithBLOBs(example);
		
		try {
			jedisClient.hset(CONTENT_LIST, cid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
