/*package com.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guanjunhuanyige.mapper.TbItemMapper;
import com.guanjunhuanyige.pojo.TbItem;
import com.guanjunhuanyige.pojo.TbItemExample;

public class pagehelpertest {

	@Test
	public void testPageHelper() throws Exception{
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper tm = ac.getBean(TbItemMapper.class);
		PageHelper.startPage(1, 10);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tm.selectByExample(example);
		PageInfo<TbItem> pageinfo = new PageInfo<>(list);
		System.out.println(pageinfo.getTotal());
		System.out.println(pageinfo.getPages());
		System.out.println(list.size());
	}
	
}
*/