package Content.Service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.guanjunhuanyige.jedis.JedisClient;

public class JedisClientTest {

	@Test
	public void TestClient() {
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient jc = ac.getBean(JedisClient.class);
		jc.set("wtf", "123");
		System.out.println(jc.get("wtf"));
	}
	
}
