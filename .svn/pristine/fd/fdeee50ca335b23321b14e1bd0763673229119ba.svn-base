package com.example.ronny_xie.gdcp.loginActivity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.util.LogTime;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by LinJiaRong on 2017/7/19.
 * TODO：
 */

public class ConnInterByOkGo {
    private static final String TAG = "ConnInterfaceByOkgo";
    private static Response response;
    private static String realName;

    public static List<String> Conn() {
        List<String> values = null;
        try {
            response = OkGo.get("http://jw2012.gdcp.cn/").execute();
            String tempHtml = response.body().string();
            List<QueryEntity> keyWords = new ArrayList<QueryEntity>();
            // 添加查询元素信息，这里新定义了一个实例类
            keyWords.add(new QueryEntity("input[name=__VIEWSTATE]", "val", null));
            values = getValuesByKeyWords(tempHtml, keyWords);
            for(int i=0;i<values.size();i++){
                String s = values.get(i);
                Log.e(TAG, "Conn: ======================="+s);
            }
          //  Log.e(TAG, "Conn: viewstate:" + values.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }


    public static int ClickIn(String[] arr, List<String> value, Handler handler) {
        String dataFromHtml = null;
        try {
            response = OkGo.post("http://jw2012.gdcp.cn/default2.aspx")
                    .headers("Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .headers("Content-Type",
                            "application/x-www-form-urlencoded")
                    .headers("Referer", "http://jw2012.gdcp.cn/")
                    .params("__VIEWSTATE", value.get(0))
                    .params("TextBox1", arr[0])
                    .params("TextBox2", arr[1])
                    .params("TextBox3", arr[2])
                    .params("RadioButtonList1", "%D1%A7%C9%FA")
                    .params("Button1", "")
                    .params("lbLanguage", "")
                    .params("hidPdrs", "")
                    .params("hidsc", "")
                    .execute();

           /* Log.e(TAG, "ClickIn: viewstate:"+value.get(0) );
            Log.e(TAG, "ClickIn: 信息："+arr[0]);
            Log.e(TAG, "ClickIn: 信息："+arr[1]);
            Log.e(TAG, "ClickIn: 信息："+arr[2]);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.code() == 200) {
            try {
                dataFromHtml = response.body().string();
              //  Log.e(TAG, "ClickIn: dataFromHrml============:" + dataFromHtml);
                List<QueryEntity> keyWords = new ArrayList<QueryEntity>();
                keyWords.add(new QueryEntity("span#xhxm", "text", null));
                realName = getValuesByKeyWords(dataFromHtml, keyWords).get(0);
             //   Log.e(TAG, "ClickIn: realName" + realName);
                if (realName == null) {
                   // Log.e(TAG, "ClickIn: realName == null");
                    return -1;
                } else {
                   // Log.e(TAG, "ClickIn: realName != null");
                    Message msg = Message.obtain();
                    msg.what = LoginPage.NAMEEXIST;
                    msg.obj = realName;
                    handler.sendMessage(msg);
                    return 1;
                }
            } catch (Exception e) {
                Document doc = Jsoup.parse(dataFromHtml + "");
                Elements elementsByTag = doc.getElementsByTag("script");
               // Log.e(TAG, "ClickIn: elementsByTag: " + elementsByTag);
                // System.out.println(dataFromHtml);
                if (elementsByTag.size() == 1) {
                    String a = elementsByTag.get(1).toString();
                  //  Log.e(TAG, "ClickIn: ++++++++++++" + a);
                    Message msg = Message.obtain();
                    msg.obj = a;
                    msg.what = LoginPage.SCRIPTTAG;
                    handler.sendMessage(msg);
                }
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "ClickIn: error++++++++++++++++++++++++++++");
        }
        return -1;
    }
    /**
     * 传入查询集合，获取需要查询元素的值，使用java反射进行封装，简化操作
     *
     * @param html          传入返回的HTML数据
     * @param queryEntities 传入要查询的QueryEntity
     * @return 返回查询的结果
     */
    public static List<String> getValuesByKeyWords(String html,
                                                   List<QueryEntity> queryEntities) throws Exception {
        List<String> values = new ArrayList<String>();
        Element body = Jsoup.parse(html).select("body").get(0);
        for (QueryEntity entity : queryEntities) {
            Element element = body.select(entity.targetSelector).get(0);
            java.lang.reflect.Method method = null;
            String value = null;
            Class<?> clazz = element.getClass();
            if (entity.methodParms == null) {
                method = clazz.getMethod(entity.methodName);
                value = (String) method.invoke(element, new Object[]{});

            } else {
                method = clazz.getMethod(entity.methodName,
                        new Class[]{String.class});
                value = (String) method.invoke(element,
                        new Object[]{entity.methodParms});

            }

            values.add(value);
        }

        return values;
    }

    /**
     * 获取验证码
     * @param view
     */
    public static void GetImageCode(final ImageView view) {
        OkGo.get("http://jw2012.gdcp.cn/CheckCode.aspx")
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
                        Log.e(TAG, "onSuccess: ");
                        view.setImageBitmap(bitmap);
                    }

                });
    }

    /**
     * 拿到教务系统的全部课程表数据，必须保证账号密码和登录系统的账号密码一致
     *
     * @param value 传入登录系统返回的Values
     * @param stuNO 传入查询学生的学号
     * @param name  传入查询学生的姓名
     * @return 返回课程表的全部HTML数据
     */
    public static String getXSKCBfromHTML(List<String> value, String stuNO, String name, String[] tem) {
        String s = "";
        try {
            response = OkGo.post("http://jw2012.gdcp.cn/xskbcx.aspx?xh=" + stuNO + "&xm="
                    + name + "&gnmkdm=N121603")
                    .headers("Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .headers("Accept-Encoding", "gzip, deflate")
                    .headers("Content-Type",
                            "application/x-www-form-urlencoded")
                    .headers("Referer",
                            "http://jw2012.gdcp.cn/xs_main.aspx?xh=" + stuNO)
                    .params("__EVENTTARGET", "xqd")
                    .params("__EVENTARGUMENT", "")
                    .params("__VIEWSTATE",
                            "dDwtODAxODI2NDQzO3Q8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+O2k8NT47PjtsPHQ8O2w8aTwxPjtpPDM+O2k8NT47aTw5Pjs+O2w8dDw7bDxpPDA+Oz47bDx0PDtsPGk8MD47aTwxPjtpPDM+O2k8Nj47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8XGU7Pj47Pjs7Pjt0PHQ8cDxwPGw8RGF0YVRleHRGaWVsZDtEYXRhVmFsdWVGaWVsZDs+O2w8eG47eG47Pj47Pjt0PGk8Mz47QDwyMDE2LTIwMTc7MjAxNS0yMDE2O1xlOz47QDwyMDE2LTIwMTc7MjAxNS0yMDE2O1xlOz4+O2w8aTwxPjs+Pjs7Pjt0PHQ8OztsPGk8MD47Pj47Oz47dDw7bDxpPDA+Oz47bDx0PHQ8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+Pjs+OztsPGk8MD47Pj47Oz47Pj47Pj47Pj47dDw7bDxpPDA+Oz47bDx0PDtsPGk8MT47aTwzPjtpPDU+O2k8Nz47aTw5Pjs+O2w8dDx0PHA8cDxsPERhdGFUZXh0RmllbGQ7RGF0YVZhbHVlRmllbGQ7PjtsPG5qO25qOz4+Oz47dDxpPDg+O0A8MjAxNjsyMDE1OzIwMTQ7MjAxMzsyMDEyOzIwMTE7MjAxMDtcZTs+O0A8MjAxNjsyMDE1OzIwMTQ7MjAxMzsyMDEyOzIwMTE7MjAxMDtcZTs+PjtsPGk8MD47Pj47Oz47dDx0PHA8cDxsPERhdGFUZXh0RmllbGQ7RGF0YVZhbHVlRmllbGQ7PjtsPHh5bWM7eHlkbTs+Pjs+O3Q8aTwxMD47QDzlnJ/mnKjlt6XnqIvlrabpmaI75rG96L2m5LiO5py65qKw5bel56iL5a2m6ZmiO+i/kOi+k+euoeeQhuWtpumZojvorqHnrpfmnLrlt6XnqIvlrabpmaI76L2o6YGT5Lqk6YCa5a2m6ZmiO+a1t+S6i+WtpumZojvnlLXlrZDkuI7pgJrkv6Hlt6XnqIvlrabpmaI75ZWG6LS45a2m6ZmiO+acuueUteW3peeoi+WtpumZojtcZTs+O0A8NTE7NTI7NTM7NTQ7NTU7NTY7NTc7NTg7NjQ7XGU7Pj47bDxpPDA+Oz4+Ozs+O3Q8dDxwPHA8bDxEYXRhVGV4dEZpZWxkO0RhdGFWYWx1ZUZpZWxkOz47bDx6eW1jO3p5ZG07Pj47Pjt0PGk8MjE+O0A85bu6562R5bel56iL566h55CG77yI6YGT6Lev5qGl5qKB77yJKDLlubTliLYpO+W3peeoi+a1i+mHj+aKgOacrzvln47luILovajpgZPkuqTpgJrlt6XnqIvmioDmnK/vvIjlronlhajmioDmnK/nrqHnkIbvvIk75Z+O5biC6L2o6YGT5Lqk6YCa5bel56iL5oqA5pyv77yI5pa95bel5rWL6YeP77yJO+W4guaUv+W3peeoi+aKgOacrygy5bm05Yi2KTvpq5jnrYnnuqflhazot6/nu7TmiqTkuI7nrqHnkIY75bu6562R5bel56iL5oqA5pyvKDLlubTliLYpO+W7uuetkeW3peeoi+aKgOacryjlu7rnrZHlraYpO+eJqeS4mueuoeeQhjvlu7rnrZHlt6XnqIvnrqHnkIYo6Lev5qGl5pa55ZCRKTvmiL/lnLDkuqfnu4/okKXkuI7kvLDku7c75Z+O5biC6L2o6YGT5Lqk6YCa5bel56iL5oqA5pyvO+mBk+i3r+ahpeaigeW3peeoi+aKgOacryjlt6XnqIvnm5HnkIbmlrnlkJEpO+WFrOi3r+S4juahpeaigeW3peeoi+aKgOacrzvluILmlL/lt6XnqIvmioDmnK875bu6562R5bel56iL5oqA5pyvO+W7uuetkeW3peeoi+aKgOacryjlt6XnqIvnm5HnkIbmlrnlkJEpO+W3peeoi+mAoOS7tzvpgZPot6/moaXmooHlt6XnqIvmioDmnK876YGT6Lev5qGl5qKB5bel56iL5oqA5pyvO1xlOz47QDwwMTAzOzAxMDg7MDE3NTswMTc2OzAyNjI7MDI3MzswMjU0OzAyNTU7MDExNDswMTEyOzAxMTM7MDE3MTswMTA1OzAxMDY7MDEwNzswMTA5OzAxMTA7MDExMTswMTA0OzAxMDI7XGU7Pj47bDxpPDA+Oz4+Ozs+O3Q8dDxwPHA8bDxEYXRhVGV4dEZpZWxkO0RhdGFWYWx1ZUZpZWxkOz47bDxiam1jO2JqZG07Pj47Pjt0PGk8MT47QDxcZTs+O0A8XGU7Pj47bDxpPDA+Oz4+Ozs+O3Q8dDxwPHA8bDxEYXRhVGV4dEZpZWxkO0RhdGFWYWx1ZUZpZWxkOz47bDx4bTt4aDs+Pjs+O3Q8aTwxPjtAPFxlOz47QDxcZTs+Pjs+Ozs+Oz4+Oz4+O3Q8O2w8aTwwPjs+O2w8dDw7bDxpPDA+O2k8Mj47aTw0PjtpPDY+O2k8OD47aTwxMT47PjtsPHQ8cDxwPGw8VGV4dDs+O2w85a2m5Y+377yaMTUxMzE1NzE0MTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85aeT5ZCN77ya6LCi5b+X5p2wOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlrabpmaLvvJrorqHnrpfmnLrlt6XnqIvlrabpmaI7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOS4k+S4mu+8mui9r+S7tuaKgOacryjova/ku7blt6XnqIvmlrnlkJEpOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzooYzmlL/nj63vvJoxNei9r+S7tuaKgOacr++8iDHvvIk7Pj47Pjs7Pjt0PHQ8OztsPGk8MD47Pj47Oz47Pj47Pj47dDxAMDxwPHA8bDxQYWdlQ291bnQ7XyFJdGVtQ291bnQ7XyFEYXRhU291cmNlSXRlbUNvdW50O0RhdGFLZXlzOz47bDxpPDE+O2k8MD47aTwwPjtsPD47Pj47Pjs7Ozs7Ozs7Ozs+Ozs+Oz4+O3Q8QDA8cDxwPGw8UGFnZUNvdW50O18hSXRlbUNvdW50O18hRGF0YVNvdXJjZUl0ZW1Db3VudDtEYXRhS2V5czs+O2w8aTwxPjtpPDE+O2k8MT47bDw+Oz4+Oz47Ozs7Ozs7Ozs7PjtsPGk8MD47PjtsPHQ8O2w8aTwxPjs+O2w8dDw7bDxpPDA+O2k8MT47aTwyPjtpPDM+O2k8ND47aTw1Pjs+O2w8dDxwPHA8bDxUZXh0Oz47bDzlhpvorq3kuI7lhaXlrabmlZnogrI7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWNouS9kOS5oDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Mjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MDMtMDMsMjAtMjA7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjs+Pjs+Pjs+Pjt0PHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Ozs+O3Q8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47bDxpPDA+Oz47bDx0PDtsPGk8MD47PjtsPHQ8QDA8cDxwPGw8UGFnZUNvdW50O18hSXRlbUNvdW50O18hRGF0YVNvdXJjZUl0ZW1Db3VudDtEYXRhS2V5czs+O2w8aTwxPjtpPDA+O2k8MD47bDw+Oz4+Oz47Ozs7Ozs7Ozs7Pjs7Pjs+Pjs+Pjt0PEAwPHA8cDxsPFBhZ2VDb3VudDtfIUl0ZW1Db3VudDtfIURhdGFTb3VyY2VJdGVtQ291bnQ7RGF0YUtleXM7PjtsPGk8MT47aTwwPjtpPDA+O2w8Pjs+Pjs+Ozs7Ozs7Ozs7Oz47Oz47dDxAMDxwPHA8bDxQYWdlQ291bnQ7XyFJdGVtQ291bnQ7XyFEYXRhU291cmNlSXRlbUNvdW50O0RhdGFLZXlzOz47bDxpPDE+O2k8MT47aTwxPjtsPD47Pj47Pjs7Ozs7Ozs7Ozs+O2w8aTwwPjs+O2w8dDw7bDxpPDE+Oz47bDx0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0Pjs+O2w8dDxwPHA8bDxUZXh0Oz47bDwyMDE1LTIwMTY7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDE7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWGm+iureS4juWFpeWtpuaVmeiCsjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85Y2i5L2Q5LmgOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwyOz4+Oz47Oz47Pj47Pj47Pj47Pj47Pit99nSz4MSGScH4HkY4/dp1Dy9t")
                    .params("xnd", tem[0])
                    .params("xqd", tem[1])
                    .execute();
            if(response.code() == 200){
          //  s=response.body().string();
                byte[] bytes = response.body().bytes();
                byte[] uncompress = uncompress(bytes);
                s = new String(uncompress, "GBK");
               // Log.e(TAG, "dataHtml: "+s);
            }else{
                Log.e(TAG, "getXSKCBfromHTML: +++++++++++++++++++error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }
    /**
     * 拿到课程表后调用此方法，拿到具体items的课程内容
     *
     * @param line 查询课程第几行
     * @param col  查询课程第几列
     * @param html 传入getXSKCBfromHTML返回的HTML数据
     * @return 返回具体课程数据
     */
    public static String getItemFromHTML(int line, int col, String html) {
        Document doc = Jsoup.parse(html);
        Element elementBy_table1 = doc.getElementById("Table1");
        Elements elementBy_tr = elementBy_table1.select("tr");
        Element index_tr = elementBy_tr.get(line);
        Elements elementsBy_td = index_tr.getElementsByTag("td");
        Element index_td = elementsBy_td.get(col);
        String returnData = index_td.text().toString();
        //Log.e(TAG, "getItemFromHTML: ++++++++++++++"+returnData);
        return returnData;
    }
    /**
     * 获取学生成绩
     * @param value      传入viewstate
     * @param stuNO      传入学号
     * @param data       传日期
     * @return 返回一个htmlData
     */
    public static String getXSCJfromHTML(List<String> value,String stuNO, String[] data){

        String s ="";
        try {
            response = OkGo.post("http://jw2012.gdcp.cn/xscj_gc.aspx?xh=" + stuNO + "&xm="
                    + "谢" + "&gnmkdm=N121603")
                    .headers("Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .headers("Accept-Encoding", "gzip, deflate")
                    .headers("Content-Type",
                            "application/x-www-form-urlencoded")
                    .headers("Referer",
                            "http://jw2012.gdcp.cn/xs_main.aspx?xh=" + stuNO)
                    .params("__VIEWSTATE", "dDwtMTY2MjI4OTM3Njt0PHA8bDx4aDtzZmRjYms7ZHlieXNjajt6eGNqY3h4czs+O2w8MTUxMzE1NzExNzswO1xlOzA7Pj47bDxpPDE+Oz47bDx0PDtsPGk8MT47aTwzPjtpPDU+O2k8Nz47aTw5PjtpPDExPjtpPDEzPjtpPDE1PjtpPDI0PjtpPDI1PjtpPDI2PjtpPDM3PjtpPDQxPjtpPDQzPjs+O2w8dDxwPHA8bDxUZXh0Oz47bDzlrablj7fvvJoxNTEzMTU3MTE3Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlp5PlkI3vvJrmnpfkvbPojaM7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWtpumZou+8muS/oeaBr+WtpumZoijlpKnmsrMpOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzkuJPkuJrvvJo7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOi9r+S7tuaKgOacryjova/ku7blt6XnqIvmlrnlkJEpOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzooYzmlL/nj63vvJoxNei9r+S7tuaKgOacr++8iDHvvIk7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIwMTUwMTU3Oz4+Oz47Oz47dDx0PDt0PGk8MTg+O0A8XGU7MjAwMS0yMDAyOzIwMDItMjAwMzsyMDAzLTIwMDQ7MjAwNC0yMDA1OzIwMDUtMjAwNjsyMDA2LTIwMDc7MjAwNy0yMDA4OzIwMDgtMjAwOTsyMDA5LTIwMTA7MjAxMC0yMDExOzIwMTEtMjAxMjsyMDEyLTIwMTM7MjAxMy0yMDE0OzIwMTQtMjAxNTsyMDE1LTIwMTY7MjAxNi0yMDE3OzIwMTctMjAxODs+O0A8XGU7MjAwMS0yMDAyOzIwMDItMjAwMzsyMDAzLTIwMDQ7MjAwNC0yMDA1OzIwMDUtMjAwNjsyMDA2LTIwMDc7MjAwNy0yMDA4OzIwMDgtMjAwOTsyMDA5LTIwMTA7MjAxMC0yMDExOzIwMTEtMjAxMjsyMDEyLTIwMTM7MjAxMy0yMDE0OzIwMTQtMjAxNTsyMDE1LTIwMTY7MjAxNi0yMDE3OzIwMTctMjAxODs+Pjs+Ozs+O3Q8cDw7cDxsPG9uY2xpY2s7PjtsPHdpbmRvdy5wcmludCgpXDs7Pj4+Ozs+O3Q8cDw7cDxsPG9uY2xpY2s7PjtsPHdpbmRvdy5jbG9zZSgpXDs7Pj4+Ozs+O3Q8cDxwPGw8VmlzaWJsZTs+O2w8bzx0Pjs+Pjs+Ozs+O3Q8O2w8aTwwPjtpPDE+O2k8Mz47aTw1PjtpPDc+O2k8OT47aTwxMT47aTwyMT47PjtsPHQ8QDA8Ozs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8Ozs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+Pjs+Ozs7Ozs7Ozs7Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDx6Zjs+Pjs+Ozs+Oz4+O3Q8QDA8Ozs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs+Pjs+Pjs+wFC/jtm3/jfy/0752fluScOAdSI=")
                    .params("ddlXN", data[0])
                    .params("ddlXQ", data[1])
                    .params("Button1", "��ѧ�ڲ�ѯ")
                    .execute();
            Log.e(TAG, "getXSCJfromHTML:value.get(0) "+value.get(0));
            Log.e(TAG, "getXSCJfromHTML: code"+response.code()+"======="+response.message());
            if(response.code()==200){

                byte[] bytes = response.body().bytes();
                byte[] uncompress = uncompress(bytes);
                s = new String(uncompress, "GBK");

            }else{
                Log.e(TAG, "getXSCJfromHTML: +++++++++error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static List<String> getClickXSCJfromHTML(String stuNO){
        String s = "";
        List<String> values = null;
        try {
            response = OkGo.post("http://jw2012.gdcp.cn/xscj_gc.aspx?xh=" + stuNO + "&xm="
                    + "谢" + "&gnmkdm=N121603")
                    .headers("Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .headers("Accept-Encoding", "gzip, deflate")
                    .headers("Content-Type",
                            "application/x-www-form-urlencoded")
                    .headers("Referer",
                            "http://jw2012.gdcp.cn/xs_main.aspx?xh=" + stuNO)
                    .execute();
            if(response.code()==200){

                byte[] bytes = response.body().bytes();
                byte[] uncompress = uncompress(bytes);
                s = new String(uncompress, "GBK");

                List<QueryEntity> keyWords = new ArrayList<QueryEntity>();
                keyWords.add(new QueryEntity("input[name=__VIEWSTATE]", "val",
                        null));
                values = getValuesByKeyWords(s, keyWords);
                values.add(s);
                return values;
            }else{
                Log.e(TAG, "getClickXSCJfromHTML: +++++error");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }
    /**
     * 将传入的bytes类型gzip数组解压为正常bytes数组
     *
     * @param bytes 传入被gzip压缩的bytes数组
     * @return 返回gzip解压的bytes数组
     */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
        }

        return out.toByteArray();
    }
}
