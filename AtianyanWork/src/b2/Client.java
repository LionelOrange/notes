package b2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.wan.entity.User;
import com.wan.service.MyService;

public class Client {
	private static ExecutorService exec=Executors.newCachedThreadPool();
	private static String user_name;
	private static String user_ip;
	private static int user_port;
	private static MyService myService=new MyService();
	public static void main(String[] args) throws Exception {
		while(true){
			Scanner in=new Scanner(System.in);
			System.out.println("请输入客户端用户名:");
			user_name=in.next();
			System.out.println("请输入客户端ip：");
			user_ip=in.next();
			System.out.println("请输入客户端口:");
			user_port=in.nextInt();
			List<User> list=myService.queryS("select * from user where user_name=? and user_ip=? and user_port=?",
					new Object[]{user_name,user_ip,user_port});
			if(!list.isEmpty()){
				myService.modify("update user set user_status=? where user_name=? and user_ip=? and user_port=?",new Object[]{1,user_name,user_ip,user_port});
				break;
			}else{
				myService.add("insert into user(user_name,user_ip,user_port,user_status)values(?,?,?,?)",
						new Object[]{user_name,user_ip,user_port,1});
				break;
			}
		}
		Socket socket=new Socket("localhost",1111,InetAddress.getByName(user_ip),user_port);
		exec.execute(new ClientTask(socket));
		String ms;
		DataInputStream di=new DataInputStream(socket.getInputStream());
		while((ms=di.readUTF())!=null){
			System.out.println(ms);
		}
	}
	static class ClientTask implements Runnable{
		private Socket socket;
		private DataOutputStream dos;
		public ClientTask(Socket socket) throws Exception {
			this.socket=socket;
			dos=new DataOutputStream(socket.getOutputStream());
		}
		@Override
		public void run() {
			String str;
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			try {
				while((str=br.readLine())!=null){
					dos.writeUTF(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
