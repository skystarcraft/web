package Content.Service;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestCase {
	@Test
	public void jedis() {
		Jedis jedis = new Jedis("139.199.197.143",6379);
		jedis.ping();
		jedis.set("test", "test01");
		System.out.println(jedis.get("test"));
		jedis.close();
	}
	
	
	@Test
	public void jedisPool() {
		JedisPool jp = new JedisPool("139.199.197.143",6379);
		Jedis js = jp.getResource();
		System.out.println(js.ping());
		js.close();
		jp.close();
	}
}
