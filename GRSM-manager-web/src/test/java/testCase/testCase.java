package testCase;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.guanjunhuanyige.utils.FastDFSClient;

public class testCase {

	@Test
	public void testFastDfs() throws Exception{
		
		ClientGlobal.init("E:\\java_storage\\GRSM-manager-web\\src\\main\\resources\\conf\\client.conf");
		
		TrackerClient tc = new TrackerClient();
		
		TrackerServer ts = tc.getConnection();
		
		StorageServer s = null;
		
		StorageClient sc = new StorageClient(ts,s);
		
		String[] str =  sc.upload_file("F:\\图片\\星际2.jpg", "jpg", null);
		
		for (String c : str) {
			System.out.println(c);
		}
		
	}
	
	
	@Test
	public void testFastClient() throws Exception {
		
		FastDFSClient fdfs = new FastDFSClient("E:\\java_storage\\GRSM-manager-web\\src\\main\\resources\\conf\\client.conf");
		
		String string = fdfs.uploadFile("F:\\图片\\123.jpg");
		
		System.out.println(string);
		
	}
}