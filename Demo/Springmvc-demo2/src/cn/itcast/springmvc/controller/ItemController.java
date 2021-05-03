package cn.itcast.springmvc.controller;

import cn.itcast.springmvc.exception.CustomerException;
import cn.itcast.springmvc.pojo.Items;
import cn.itcast.springmvc.pojo.QueryVo;
import cn.itcast.springmvc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/itemlist")
    public ModelAndView getItemList() throws Exception{
        /*自定义异常测试
        if(true){
            throw new CustomerException("这是我们自定义的异常，哈哈");
        }*/
        //运行时异常，这个时候就会把这个错误的堆栈信息取出来
        //int i=1/0;
        List<Items> itemList = itemService.getItemList();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("itemList",itemList);
        modelAndView.setViewName("itemList");
        return modelAndView;
        }
    @RequestMapping("/itemEdit")
    public ModelAndView editItem(HttpServletRequest request){
        String strid = request.getParameter("id");
        Integer id = new Integer(strid);
        Items items = itemService.getItemById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("item",items);
        modelAndView.setViewName("editItem");
        return modelAndView;

    }
    @RequestMapping("/updateitem")
    public String updateItem(Items items, MultipartFile picture) throws Exception {
        /*
        我们可以在这个提交表单的时候同时把这个图片也上传过来，如果我们去单独的接收这个图片的时候，
        我们需要把这个接收图片的做成一个控件，让后再进行回显，这个思路比较麻烦，我们可以在提交表单
        的同时把这个图片也上传过来，在修改商品的时候我们接收了这个表单里面的大部分的数据，除了这个数据
        我们还需要接收一个流媒体的文件流，用这个 MultipartFile来接收我们的另一个图片参数信息，items是
        我们的这个图片数据，pic是这个图片信息。接收到这个图片信息之后，我们就需要把这个图片信息放到
        我们刚才的那个目录下面，
        * */
        //把图片保存到这个图片目录下，那么当我们去访问的时候，我们就可以像这个访问图片的虚拟路径的时候
        //访问到图片，再进行图片保存的时候，我们的文件名有可能会重复（当我们保存多个时候，有时候可能
        // 会产生覆盖），所以我们需要为每一个图片生成一个新的文件名，如何保证我们的文件名不重复我们可以使用
        //UUID   为每一个文件生成一个新的文件名
        String picname= UUID.randomUUID().toString();
        //文件名有了之后，我们文件的这个扩展名也需要进行设置一下如果我们不设置这个文件的扩展名
        //那么文件的扩展名有可能是就是jpg的，有可能是bmp，有可能是gif的,文件名的扩展名的取出我们
        // 可以截出来
        //得到原始的文件名，原始的文件名肯定包含这个扩展名.图片的扩展名是以.开头的 ，所
        // 以我们从.开始截取
        String oriFilename =picture.getOriginalFilename();
        //这里我们不需要加1，加1就是带.的，不加1就是不带.的
        String exname=oriFilename.substring(oriFilename.lastIndexOf("."));
        //我们的这里的真正的文件名依旧是文件的原始文件名+文件的扩展名
        picture.transferTo(new File("D:\\upload\\"+picname+exname));
        //把文件名保存到数据库(使用这个items的setpic()方法)，因为我们将来要显示图片回显图片，所以
        // 我们要把这个文件名取出来
        items.setPic(picname+exname);
        //更新这个商品图片，这个时候我们的数据库中就由这个图片信息了，
        itemService.updateItem(items);
        //接下来我们就要考虑页面图如何把这个图片提交给后台呢，在我们的这个editItem.jsp页面中
        //我们点击这个更新按钮就实现了把图片提交到这个后台，所以这里我们的editItem.jsp文件中的
        //商品图片的注释可以取消掉，并且那个name属性 要和这个当前方法的形参保持一致才能够实现绑定
        //此外这个上传图片的功能我们需要为这个表单添加一个上传图片是需要指定属性 enctype="multipart/form-data"
        //redirect页面跳转
        //return "redirect:itemlist.action";
        //forward页面跳转，这个时候我们的图片已经保存到这个数据库中，图片的文件名一旦
        //保存到这个数据库中，我们这个时候重新做了一个forward页面跳转，这个时候我们对
        // 页面做了判断，如果存在图片，就将图片进行展示。着整个就是图片上传
        return "forward:itemEdit.action";
    }
    @RequestMapping("/queryitem")
    public String queryItem(QueryVo queryVo,String[] ids) {
        System.out.println(queryVo.getItems().getId());
        System.out.println(queryVo.getItems().getName());
        return "success";
    }
    @RequestMapping("/itemEdit/{id}")
    public String editItem(@PathVariable Integer id, Model model){
        Items items = itemService.getItemById(id);
        model.addAttribute("item",items);
        return "editItem";

    }
    @RequestMapping("/itemlist2")
    public void itemlist2(HttpServletRequest request, HttpServletResponse respongse) throws Exception {
        //查询商品列表
        List<Items> itemList = itemService.getItemList();
        //向页面传递参数,第一个参数要和jsp中域中的名字一样，否则页面
        // 就不显示数据，第二个是我们的这个商品列表
        request.setAttribute("itemList",itemList);
        //如果使用原始的方式去做页面跳转，就必须要给jsp的完整路径
        request.getRequestDispatcher("/WEB-INF/jsp/itemList.jsp").forward(request,respongse);
    }
    /*
    * json数据交互
    * @RequestBody:接收json数据，并且把json数据转换成pojo对象
    * (在我们的这个方法中我们在我们的参数面前使用了这个注解，所以到时候就会接收我们的
    * 这个json数据，我们要在我们的客户端向我们的服务器端发送一个json格式的数据，我们应该怎么发送，
    * 一般我们发送的这个请求它并不是一个json，它都是这个key和value的字符串，如果我们想要发送一个
    * 发送一个json格式的数据，我们就需要指定一个contenttype和一个json字符串，我们可以使用这个Ajax
    * 来进行实现。我们要使用这个ajax的话，我们可以使用这个jQuery比较方便，否则的话我们就要创建各种对象
    * 就会很麻烦，)
    * @ResponseBody：响应json数据，把java对象pojo对象转换成json并响应
    *
    *
    * 这里当我们在我们的浏览器中发起一个请求的时候，就会执行这个方法，这个请求的数据是一个json
    * 由于我们接收到的数据是一个json格式，但是我们在我们的这个方法的形参前面添加了一个@RequestBody
    * 的注解，所以这个时候我们的这个注解把这个json格式的数据转换成一个items的对象，转换成
    * 该对象之后，我们就可以拿到这个数据，然后这个方法的返回值是一个Items对象，正常的情况下，浏览器是
    * 没有办法去相应一个java对象给浏览器的，所以我们又使用这个@ResponseBody注解，将我们的这个java
    * 对象转换成一个json格式的字符串。并且响应给浏览器，这个时候在我们的浏览器中所拿到的这个数据
    * 值也就是这个json数据，我们直接alert弹出显示。
    * */
    @RequestMapping("/jsonTest")
    @ResponseBody
    public Items jsonTest(@RequestBody Items items){
        return items;

    }



}
