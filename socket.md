# **socket总结**
通过一个java控制台输入数据，多个客户端通过服务端信息相互传输，类似于qq聊天的项目，
来巩固最近所学的多线程，jdbc连接数据库，socket网络编程的Java基础内容。

* 服务端创建serversocket，绑定服务器地址及端口并开始监听，服务端
通过serversocket的accept方法接收客户端传来的socket的连接,由于同时会有多个socket连接服务端的并发，
所以需要新建线程接收信息和发送信息。针对socket的读取和写入应用DataInputStream和DataOutputStream,
BufferReader是写进内存的，不会写入socket。为了避免过多线程浪费资源,我通过线程池来运行线程。
数据从服务端发送到各个客户端需要经过他们各自的socket来写入,所以通过ArrayList来存放服务端接收的
所有socket,具体的服务端的代码如下:
```java
public class Server {
	//缓冲线程池，存放线程
	private static ExecutorService exec=Executors.newCachedThreadPool();
	//存放服务端接收到的所有socket
	private static List<Socket> list=new ArrayList<>();
	public static void main(String[] args) throws Exception {
		//服务端创建ServerSocket，绑定服务器地址及端口并开始监听
		ServerSocket ss=new ServerSocket(6666);
		while(true){
			//阻塞操作，接收来自客户端的socket的连接
			Socket socket=ss.accept();
			list.add(socket);
			//将任务添加进线程池
			exec.execute(new ServerTask(socket));
		}
	}
	static class ServerTask implements Runnable{
		private DataInputStream dis;
		private DataOutputStream dos;
		private Socket socket;
		private String msg="";
		public ServerTask(Socket socket) throws Exception {
			this.socket=socket;
			//socket通过getInputStream读取客户端的信息
			dis=new DataInputStream(socket.getInputStream());
		}
		@Override
		public void run() {
			
			try {
				//readUTF为阻塞操作
				while((msg=dis.readUTF())!=null){
					sendmessage();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private void sendmessage() throws Exception{
			//遍历list中的所有socket
			for(Socket s:list){
				if(s!=socket){
					//通过socket得到所有getOutputStream的输出通道，然后将信息发送到各个客户端
					dos=new DataOutputStream(s.getOutputStream());
					dos.writeUTF(msg);
				}
			}
		}
	}
}
```
  *  客户端建立socket连接，创建Socket对象，由于同时存在读取服务端的信息和读取控制台的输入信息
  的并发，所以新建一个线程处理读取控制台信息并发送信息到客户端
  ```java
  public class Client {
	private static ExecutorService exec=Executors.newCachedThreadPool();
	public static void main(String[] args) throws Exception {
		//创建连接服务端的socket对象
		Socket socket=new Socket("localhost",6666);
		//将读取控制台信息并发送信息到服务端的任务添加到线程池
		exec.execute(new clientTask(socket));
		DataInputStream dis=new DataInputStream(socket.getInputStream());
		String str="";
		//阻塞操作，读取从服务端传来的消息并显示在客户端的控制台上
		while((str=dis.readUTF())!=null){
			System.out.println(str);
		}
	}
	static class clientTask implements Runnable{
		private Socket socket;
		private DataOutputStream dos;
		public clientTask(Socket socket) throws Exception {
			this.socket=socket;
			//通过getOutputStream得到socket的输出流
			dos=new DataOutputStream(socket.getOutputStream());
		}
		@Override
		public void run() {
			//读取控制台信息并写入输出流
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			try {
				String str="";
				while((str=br.readLine())!=null){
					dos.writeUTF(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
  ```
具体代码请看[https://github.com/LionelOrange/notes/tree/master/AtianyanWork/src](https://github.com/LionelOrange/notes/tree/master/AtianyanWork/src)
