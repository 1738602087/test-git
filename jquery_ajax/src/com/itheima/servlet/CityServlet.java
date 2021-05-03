package com.itheima.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.alibaba.fastjson.JSONObject;
import com.itheima.domain.City;
import com.itheima.domain.Province;
import com.thoughtworks.xstream.XStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CityServlet extends HttpServlet {
	//声明一个Map集合，map集合的key是我们的省份对象，map集合的value是我们的每一个省份所包含的
	//list集合，集合中的每一个元素都是城市对象。
	private Map<Province, List<City>> map;

	@Override
	public void init() throws ServletException {
		map = new HashMap<Province, List<City>>();
		// 创建省份对象，并且给对象的属性赋值
		/*我们创建了一个省份，下面有两个城市哈尔滨和大庆*/
		Province p1 = new Province();
		p1.setId(1);
		p1.setName("黑龙江");

		City c11 = new City();
		c11.setId(1);
		c11.setName("哈尔滨");

		City c12 = new City();
		c12.setId(2);
		c12.setName("大庆");
		//创建一个list集合，集合中的泛型是城市对象，并且将城市对象添加到list集合中
		List<City> l1 = new ArrayList<City>();
		l1.add(c11);
		l1.add(c12);

		//向map集合中存放数据，map集合的key就是我们声明的Provice省份对象，map集合的
		//value也就是我们的list集合，集合中的每一个元素都是每一个省份下面所包含的
		//城市信息，
		map.put(p1, l1);
		/*我们创建了一个省份吉林，下面有两个城市长春和吉林*/
		Province p2 = new Province();
		p2.setId(2);
		p2.setName("吉林");

		City c21 = new City();
		c21.setId(1);
		c21.setName("长春");

		City c22 = new City();
		c22.setId(2);
		c22.setName("吉林");

		List<City> l2 = new ArrayList<City>();
		l2.add(c21);
		l2.add(c22);

		map.put(p2, l2);

		/*我们创建了一个省份辽宁，下面有两个城市长春沈阳和大连*/
		Province p3 = new Province();
		p3.setId(3);
		p3.setName("辽宁");

		City c31 = new City();
		c31.setId(1);
		c31.setName("沈阳");

		City c32 = new City();
		c32.setId(2);
		c32.setName("大连");

		List<City> l3 = new ArrayList<City>();
		l3.add(c31);
		l3.add(c32);

		map.put(p3, l3);

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 处理中文乱码
		response.setCharacterEncoding("utf-8");// 响应乱码
		request.setCharacterEncoding("utf-8"); // 请求中文乱码，只对post请求有效
		//这里因为我们前台页面中发送获取省份信息的ajax请求和获取城市信息的ajax请求的url都是
		//对应于这个CityServlet，所以我们就是首先通过在这个前台页面中发送ajax请求的url中
		//通过携带不同的请求参数来判断用户获取的是省份信息还是城市信息，所以这里前台页面传递到服务器端
		// 的请求参数有两种情况，一种是这个method=pro即表示用户获取的是省份信息，另外一种也就是
		//method=city即表示用户获取的是城市信息，所以这里我们需要在服务器端首先对用户发送过来的请求url
		//中获取到这个请求url所携带的参数到底是请求省份还是请求城市。
		String method = request.getParameter("method");
        //对我们从前台代码中获取的method的值进行打印输出，第一次也就我们在页面打开这个
		//demo5.html的时候就会首先在控制台中输出这个pro,因为页面加载完成之后我们就会向服务器端
		//发送异步的ajax请求，从服务器端获取到省份数据，当我们在页面上选择了某一个省份选项的时候。
		//这个时候就会向服务器端也就是当前的这个CityServlet重新发送一个ajax请求，只不过这个
		//时候在控制台中输出的这个method的值为City，因为这一次我们前台页面传递的请求是获取
		//城市信息，所以说传递过来的就是method=city,所以我们在控制台中打印的就是city
		System.out.println(method);

		// 判断是获取省份信息，还是获取城市信息， 下面的这种
		//情况表示的就是获取省份信息。
		if ("pro".equals(method)) {
			// 获取省份信息
			Set<Province> ps = map.keySet();

			// 将ps转换成json 利用fastjson中的方法。将这个省份信息转换成一个json格式的字符串
			//之后我们就可以直接把这个json串返回到前台ajax请求成功的回调函数中的data变量中。
			String json = JSONObject.toJSONString(ps);
			response.getWriter().write(json);

		}
		// 要获取城市信息
		if ("city".equals(method)) {

			/*这里我们在服务器端也就是获取到这个表现层前台页面传递过来的省份名称，之后我们根据这个
			* 省份名称找到该省份所对应的城市信息的集合，我们通过map集合的遍历方式，这里我们map集合的遍历是
			* 键找值的方式*/
			// 获取省份名称
			String pname = request.getParameter("pname");

			// 遍历map,根据省份名称来获取对应的List<City>
			for (Province p : map.keySet()) {
				if (pname.equals(p.getName())) {

					List<City> citys = map.get(p);
					// 将citys转换成json响应到浏览器
					String json = JSONObject.toJSONString(citys);
                    //通过输出流的方式写会到前台页面
					response.getWriter().write(json);
					break;
				}
			}

		}
		
		//返回xml数据
		if("xml".equals(method)){
			XStream xstream = new XStream();
			xstream.autodetectAnnotations(true);
			String xml=xstream.toXML(map);
			response.getWriter().write(xml);
		}

		
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
