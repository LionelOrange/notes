package b2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.wan.entity.User;
import com.wan.service.MyService;

public class Server {
	private static List<Socket> list=new ArrayList<>();
	private static ExecutorService exec=Executors.newCachedThreadPool();
	public static void main(String[] args) throws Exception {
		ServerSocket server=new ServerSocket(1111);
		while(true){
			Socket socket=server.accept();
			list.add(socket);
			exec.execute(new ServiceTask(socket));
		}
	}
	static class ServiceTask implements Runnable{
		private Socket socket;
		private DataInputStream di;
		private String msg;
		private DataOutputStream dos;
		private MyService myService;
		private String user_name;
		private String truemsg;
		private String sendname;
		public ServiceTask(Socket socket) throws Exception{
			this.socket=socket;
			di=new DataInputStream(socket.getInputStream());
			myService=new MyService();
			String myip=socket.getInetAddress().toString().substring(1);
			int myport=socket.getPort();
			List<User> b=myService.queryS("select * from user where user_ip=? and user_port=?",new Object[]{myip,myport});
			for(User u:b)
				sendname=u.getUser_name();
		}
		@Override
		public void run() {
			try {
				while((msg=di.readUTF())!=null){
					String[] str=msg.split(":");
					user_name=str[0];
					truemsg=str[1];
					List<User> a=myService.queryS("select * from user where user_name=?",new Object[]{user_name});
					
					for(Socket s:list){
						String ip=s.getInetAddress().toString().substring(1);
						if(ip.equals(a.get(0).getUser_ip())&&(s.getPort()==a.get(0).getUser_port())){
							if(s!=socket){
								dos= new DataOutputStream(s.getOutputStream());
								dos.writeUTF("["+sendname+"]"+truemsg);
							}
						}
			
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
