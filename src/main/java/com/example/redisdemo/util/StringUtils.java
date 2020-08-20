package com.example.redisdemo.util;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: taojiang
 * @Email: taojiang@66law.com
 * @Date: 2019/12/24 11:00
 * @Description: 字符串工具类
 * @version: V1.0
 */
public class StringUtils {
    private static Pattern ESCAPE = Pattern.compile("[.,()（ ），、]");
    private static Pattern yearReg = Pattern.compile("(?<year>[12]\\d{3,99})");
    public static List<String> years=new ArrayList<>(3);
    private static List<String> serial = new ArrayList<>(10);
    private static String[] filterStr=new String []{"》","《","【","】","：","，",",","。","、","？","?","！","+","-","*","/",".","@","#","$","%","^","&",":",";","\"","|","(",")","{","}"};
    private static Map<String,String> dict = new HashMap<>(128);
    private static String pageRegex = "[0-9]+、|[0-9]+\\.|\\([一二三四五六七八九零]+\\)|（[0-9]+）|\\([0-9]+\\)|（[一二三四五六七八九零]+）|[一二三四五六七八九零]+、|[0-9]+,|[0-9]+，|[①②③④⑤⑥⑦⑧⑨⑩]+|第[一二三四五六七八九零]+";
    private static List<String> labelWords = new ArrayList<>(16);
    private static String[][] serials = {
            {"一、", "二、", "三、", "四、", "五、", "六、", "七、", "八、", "九、", "十、"},
            {"一：", "二：", "三：", "四：", "五：", "六：", "七：", "八：", "九：", "十："},
            {"1)", "2)", "3)", "4)", "5)", "6)", "7)", "8)", "9)", "10)"},
            {"1>", "2>", "3>", "4>", "5>", "6>", "7>", "8>", "9>", "10>"},
            {"(\n1)", "(\n2)", "(\n3)", "(\n4)", "(\n5)", "(\n6)", "(\n7)", "(\n8)", "(\n9)", "(\n10)"},
            {"<\n1>", "<\n2>", "<\n3>", "<\n4>", "<\n5>", "<\n6>", "<\n7>", "<\n8>", "<\n9>", "<\n10>"},
            {"1）", "2）", "3）", "4）", "5）", "6）", "7）", "8）", "9）", "10）"},
            {"（\n1）", "（\n2）", "（\n3）", "（\n4）", "（\n5）", "（\n6）", "（\n7）", "（\n8）", "（\n9）", "（\n10）"},
            {"1、", "2、", "3、", "4、", "5、", "6、", "7、", "8、", "9、", "10、"},
            {"1，", "2，", "3，", "4，", "5，", "6，", "7，", "8，", "9，", "10，"},
            {"1.", "2.", "3.", "4.", "5.", "6.", "7.", "8.", "9.", "10."},
            {"首先", "其次", "然后", "最后",},
            {"一)", "二)", "三)", "四)", "五)", "六)", "七)", "八)", "九)", "十)"},
            {"(\n一)", "(\n二)", "(\n三)", "(\n四)", "(\n五)", "(\n六)", "(\n七)", "(\n八)", "(\n九)", "(\n十)"},
            {"一）", "二）", "三）", "四）", "五）", "六）", "七）", "八）", "九）", "十）"},
            {"（\n一）", "（\n二）", "（\n三）", "（\n四）", "（\n五）", "（\n六）", "（\n七）", "（\n八）", "（\n九）", "（\n十）"},
            {"㈠", "㈡", "㈢", "㈣", "㈤", "㈥", "㈦", "㈧", "㈨", "㈩"},
            {"a.", "b.", "c.", "d.", "e.", "f.", "g."},
            {"A、", "B、", "C、", "D、", "E、", "F、", "G、"},
            {"a、", "b、", "c、", "d、", "e、", "f、", "g、"},
            {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩",}};
    static {
        years.add("2019");
        years.add("2018");
        years.add("2017");
    }
    static {
        labelWords.add("蹂躏罪");
        labelWords.add("蓉城计划");
        labelWords.add("蓉城绿卡");
        labelWords.add("千人计划");
        labelWords.add("案情：");
        labelWords.add("案情:");
        labelWords.add("案情简介");
        labelWords.add("案情详解");
        labelWords.add("案情介绍相关案件报道");
        labelWords.add("根据此宗案例");
        labelWords.add("此案");
        labelWords.add("相关问题");
        labelWords.add("相关案例");
        labelWords.add("上述判决");
        labelWords.add("在本案中");
        labelWords.add("化名");
        labelWords.add("事件经过");
        labelWords.add("先生");
        labelWords.add("女士");
        labelWords.add("记者");
        labelWords.add("热门城市");
        labelWords.add("案情回顾");
        labelWords.add("习近平");
        labelWords.add("小姐");
        labelWords.add("案情经过");
        labelWords.add("问题描述");
        labelWords.add("网友");
        labelWords.add("人士");
        labelWords.add("读者");
        labelWords.add("律师说法");
        labelWords.add("法院判决");
        labelWords.add("延伸阅读");
        labelWords.add("扩展资料");
        labelWords.add("为您推荐");
        labelWords.add("热门律师");
        labelWords.add("声明");
        labelWords.add("立即下载");
        labelWords.add("本报讯");
        labelWords.add("报道");
        labelWords.add("报社");
        labelWords.add("专业人士");
        labelWords.add("办案经验");
        labelWords.add("律师代理");
        labelWords.add("优秀律师");
        labelWords.add("进群");
        labelWords.add("微信号");
        labelWords.add("律师团队");
        labelWords.add("致电");
        labelWords.add("电话号码");
        labelWords.add("联系方式");
        labelWords.add("办公地址");
        labelWords.add("添加微信");
        labelWords.add("参考资料");
        labelWords.add("我有更好回答");
        labelWords.add("推荐于");
        labelWords.add("匿名用户");
        labelWords.add("转自网络");
        labelWords.add("展开全文");
        labelWords.add("我来答");

    }
    static {
        dict.put("中华人名共和国","中华人民共和国");
        dict.put("大人常委会","大常委会");
        dict.put("中国共产常","中国共产党");
        dict.put("祖国万死","祖国万岁");
        dict.put("科学发展现","科学发展观");
        dict.put("杜会主义","社会主义");
        dict.put("友腐倡廉","反腐倡廉");
        dict.put("扶贪","扶贫");
        dict.put("严谨公款吃喝","严禁公款吃喝");
        dict.put("扫黑险恶","扫黑除恶");
        dict.put("政务公开领导小姐","政务公开领导小组");
        dict.put("藏区","藏*");
        dict.put("全国人大副委员长","全国人大常委会副委员长");
        dict.put("省人大副主任","省人大常委会副主任");
        dict.put("省省委书记","省委书记");
        dict.put("市市委书记","市委书记");
        dict.put("检察院院长","检察长");
        dict.put("村官","村干部");
        dict.put("村长","村主任");
        dict.put("装逼","**");
        dict.put("草泥马","**");
        dict.put("特么的","**");
        dict.put("撕逼","**");
        dict.put("玛拉戈壁","**");
        dict.put("爆菊","**");
        dict.put("JB","**");
        dict.put("呆逼","**");
        dict.put("本屌","**");
        dict.put("齐B短裙","**");
        dict.put("法克鱿","**");
        dict.put("丢你老母","**");
        dict.put("达菲鸡","**");
        dict.put("装13","**");
        dict.put("逼格","**");
        dict.put("蛋疼","**");
        dict.put("傻逼","**");
        dict.put("绿茶婊","**");
        dict.put("你妈的","**");
        dict.put("表砸","**");
        dict.put("屌爆了","**");
        dict.put("买了个婊","**");
        dict.put("已撸","**");
        dict.put("吉跋猫","**");
        dict.put("妈蛋","**");
        dict.put("逗比","**");
        dict.put("我靠","**");
        dict.put("碧莲","**");
        dict.put("碧池","**");
        dict.put("然并卵","**");
        dict.put("日了狗","**");
        dict.put("屁民","**");
        dict.put("吃翔","**");
        dict.put("淫家","**");
        dict.put("你妹","**");
        dict.put("浮尸国","**");
        dict.put("滚粗","**");
        dict.put("独眼龙","盲人");
        dict.put("残废人","残疾人");
        dict.put("瞎子","盲人");
        dict.put("聋子","聋人");
        dict.put("傻子","智力障碍者");
        dict.put("呆子","智力障碍者");
        dict.put("弱智","智力障碍者");
        dict.put("影帝","著名演员");
        dict.put("影后","著名演员");
        dict.put("巨星","著名演员");
        dict.put("天王","著名演员");
        dict.put("男神","著名演员");
        dict.put("女神","著名演员");
        dict.put("内港","内地与香港");
        dict.put("内澳","内地与澳门");
        dict.put("港澳台游客来华（国内）","港澳台游客来内地(大陆)");
        dict.put("雨伞运动","");
        dict.put("非党人士","");
        dict.put("占中","");
        dict.put("少数民族上层人士","");
        dict.put("占中三子","");
        dict.put("“一带一路”战略","“一带一路”倡议");
        dict.put("践行‘八荣八耻’","践行社会主义荣辱观");
    }
    static {
        serial.add("一、");
        serial.add("二、");
        serial.add("三、");
        serial.add("四、");
        serial.add("五、");
        serial.add("六、");
        serial.add("七、");
        serial.add("八、");
        serial.add("九、");
        serial.add("十、");
    }




    /**
     * 是否包含2019关键字
     * @param content content
     * @param keyword keyword
     * @return Boolean
     */
    public static Boolean containsKeyword(String content, String keyword){
        return content.contains(keyword);
    }

    /**
     * 生产mongodb id
     * @param prefix 前缀
     * @param id id
     * @return mongodbID
     */
    public static String generateMongoDBID(Long prefix,Long id){
        return String.valueOf(prefix + id);
    }


    /**
     * 是否包含时间词
     * @param text text
     * @return false/true
     */
    public static Boolean isContainsTimeWord(String text){
        Boolean res = false;
        if(text != null && !text.isEmpty()){
            Matcher matcher = yearReg.matcher(text);
            if(matcher.find()){
                Integer year = Integer.valueOf(matcher.group("year"));
                if(year >= 1970 && year <= 2020){
                    res = true;
                }
            }
        }
        return res;
    }

    /**
     * 文本包含时间但是存在不等于给定时间的时间词
     * @param text 文本
     * @param yearString 给定时间
     * @return
     */
    public static Boolean notEqualsTimeWord(String text,String yearString){
        boolean res = false;
        if(text != null && !text.isEmpty()){
            Matcher matcher = yearReg.matcher(text);
            while (matcher.find()){
                String group = matcher.group();
                if(!group.equals(yearString)){
                    res = true;
                    break;
                }
            }
        }
        return res;
    }





    /**
     * 处理字符串中的 年
     * 规则：带年月日的字符串部分不处理
     * @param s 需要处理的字符串
     * @param years 需要处理的年 如:2019
     * @param o 年需要替换的字符串，传入""则为删除
     * @return
     */
    public static String handleYear(String s, List<String> years, String o) {
        StringBuilder stringBuilder = new StringBuilder(s);
        for (String year : years) {
            int i=-1;
            while ((i=stringBuilder.indexOf(year,i+1))>=0){
                if(i+7>=stringBuilder.length()||(stringBuilder.charAt(i+7)!='月'&&stringBuilder.charAt(i+6)!='月')){
                    if ((i+4)<stringBuilder.length()&&'年'==stringBuilder.charAt(i+4)){
                        if (o==null||"".equals(o)){
                            stringBuilder.replace(i,i+5,o);
                        }else {
                            stringBuilder.replace(i,i+5,o+"年");
                        }
                    }else {
                        stringBuilder.replace(i,i+4,o);
                    }
                }
            }
        }

        return stringBuilder.toString();
    }
    public static String handleYear(String str,String year){
        return str.replaceAll("[0123456789]{4}[年]?",year);
    }

    /**
     * (房,买,合同)(房,销售)(房,认购)(楼,买)(房,买)(买,房)
     * (房,买,合同)(买,房) =>   (.*?房.*?买.*?合同.*?)|(.*?买.*?房.*?)
     * 规则转换为正则
     */
    public static String ruleToReg(String rule){
        if (rule.charAt(0)=='('&&rule.charAt(rule.length()-1)==')'){
            rule=rule.replaceAll("\\(","\\(.*?");
            rule=rule.replaceAll(",",".*?");
            rule=rule.replaceAll("\\)",".*?)|");
            rule=rule.substring(0,rule.length()-1);
        }else {
            rule="(.*?"+rule+".*?)";
        }
        return rule;
    }
    public static String dealOnlyYear(String newText){
        if(!newText.contains("月")){
            newText = newText.replaceAll("20[012][0123456789]年", "");
        }
        return newText;
    }

    /**
     * 场景转换为正则
     * (商铺)(店面)(门市)=》
     */

    /**
     * 去掉字符中的html标签
     * @param str
     * @return
     */
    public static String StripHTML(String str){
        //如果有双引号将其先转成单引号
        String htmlStr = str.replaceAll("\"", "'");
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    /**
     * 将字符串转会为标题->答案
     * @return
     */
    public static Map<String,String> stringToTittleAndAnswer(List<String> split){
        Map<String,String> map = new HashMap<>(8);
        String key = null;
        StringBuilder value = null;
        for (String arr : split) {
            if(null == arr || arr.trim().length() == 0){
                continue;
            }
            if("延伸阅读：".equals(arr)){
                break;
            }
            if(arr.length()>=2 && serial.contains(arr.substring(0,2))){
                if(null != key && null != value){
                    key = removeSpecialCode(key);
                    String removeSpecialCode = removeSpecialCode(value.toString());
                    map.put(replaceWithoutYear(key),removeSpecialCode);
                    value = null;
                }
                key = arr.substring(arr.indexOf("、")+1);
                continue;
            }

            if(null != key){
                if(null == value){
                    value = new StringBuilder();
                }
                value.append(arr).append("\n");
            }
        }
        if(null != key){
            if (value == null) {
                key = removeSpecialCode(key);
                map.put(replaceWithoutYear(key), null);
            } else {
                key = removeSpecialCode(key);
                String removeSpecialCode = removeSpecialCode(value.toString());
                map.put(replaceWithoutYear(key),removeSpecialCode);
            }
        }
        return map;
    }






    /**
     * 延伸阅读处理
     * @param str 原始string
     * @return 处理结果
     */
    public static String expandReaderDeal(String str){
        if(str.contains("延伸阅读:") && !str.startsWith("延伸阅读:")){
            return  str.substring(0,str.indexOf("延伸阅读:"));
        }
        return str;
    }

    /**
     * 删除多余空行
     * @param input
     * @return
     */
    public static String removeSpaceLine(String input) {
        input=input.replaceAll("\\n{2,}", "\n");

        return input;
    }

    public  static  String  replaceWithoutYear(String str){
        String regEx = "\\d*年";
        Pattern p_script = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(str);
        return m_script.replaceAll("");
    }
    public static String removeEndCode(String str){
        boolean tf = false;
        for (String contain : filterStr) {
            if(str.contains(contain)){
                if(str.indexOf(contain) != str.length()-1){
                    tf = true;
                    break;
                }
                if(str.indexOf(contain) == str.length()-1){
                    str =  str.substring(0,str.length()-1);
                }
            }
        }
        if(!tf){
            return str;
        }
        return null;

    }

    /**
     * 将文本进行分段处理
     * @param orgContent 原始文本
     * @return
     */
    public static String serialString(String orgContent) {
        String content = orgContent.replace("\n","");
        String secondContent = content;
        for (String[] serial : serials) {
            if (secondContent.contains(serial[0]) && secondContent.contains(serial[1])) {
                for (String s : serial) {
                    String subStr = s.replace("\n", "");
                    String subStr2 = String.format("\n%s", subStr);
                    secondContent = secondContent.replace(s, subStr2);
                }
            }
        }
        if(secondContent.equals(content)){
            return orgContent.replaceAll("\\s{2}","\n");
        }
        return secondContent.replaceAll("\\s{2}","\n");
    }
    public static String dealPage(String content){
        content =content.replace("\n","");
        Matcher matcher = Pattern.compile(pageRegex).matcher(content);
        while (matcher.find()){
            String group = matcher.group();
            int i = content.indexOf(group);
            if(i != 0){
                char c = content.charAt(i - 1);
                while (c == ' ' || c == '　' ){
                    i = i - 1;
                    c = content.charAt(i);
                    if(i == 0){
                        break;
                    }
                }
                //c == '。' || c == '；' || c == '：'|| c == ';'|| c == '.'|| c == ':' ||c == ','||c == '，'
                if(matchPunctuationPage(c)){
                    content =content.replaceFirst(escape(group),"\n"+group);
                }
            }
        }
        return content.replaceAll("\\s{2,}|\\S]{2,}","\n");
    }

    /**
     * 转义
     * @param group
     * @return
     */
    private static String escape(String group) {
        Matcher pattern = ESCAPE.matcher(group);
        while (pattern.find()){
            String s = pattern.group();
            group = group.replace(s,"\\"+s);
        }
        return group;
    }

    public static void main(String[] args) {
        String s = dealPage("根据《最高人民法院关于审理人身损害赔偿案件若干问题的解释》规定：直接受害人因损害事故死亡，其近亲属作为间接受害人享有独立的损害赔偿请求权。　　第一，近亲属的范围：配偶、父母、子女、兄弟姐妹、祖父母、外祖父母、孙子女、外孙子女。　　第二，关于财产中的死亡赔偿金，因司法解释采取继承丧失说，应当按照《中华人民共和国继承法》第十条规定的法定继承顺序，由配偶、父母和子女作为第一顺序继承人共同继承。没有第一顺序继承人的，由第二顺序继承人继承。被继承人子女先于被继承人死亡的，由被继承人子女的晚辈直系血亲代位继承。但同一继承顺序中，原则上按照共同生活的紧密程度决定继承份额，而不适用《继承法》第十三条规定的同一顺序一般应当均等的原则。即死亡赔偿金原则上应由家庭生活共同体成员共同取得。当事人未请求分割的，人民法院不予分割。当事人请求分割的，在考虑家庭共同生活紧密程度前提下，还应当同时考虑同一顺序继承人中可否单独请求被抚养人生活费的情况，予以适当平衡。　　根据上述司法解释精神，本人认为，在该争议中父母和妻子对丈夫的死亡赔偿金享有共同所有权。如要求法院分割，因父母可以就被抚养人生活费另外得到赔偿，法院可考虑家庭生活紧密程度对妻子分配适当多一些，予以平衡。");
        System.out.println(s);

        // String s = replaceSensitiveWords("非党人士草泥马,今天是倒了八辈子血霉中华人名共和国,四川省省委书记德玛西亚是一个瞎子");
        // List<String> s = aimLabel("非党人士草泥马,今天是倒案情:了八辈子血霉中华人名共和国,化名四川省省委书记德玛西亚是一个瞎子蓉城计划");
//        String s = removeSelfInfo("今台南百度百科我就是要测试网址信息https://www.yuque.com/easyexcel/doc/easyexcel找法网:http://www.gov.cn/xinwen/2020-08/04/content_5532393.htm");
//        String s = handleYear("1995我萨达","2020年");
//        System.out.println(s);
//        String s = removeSpaceLine("此项营业税由城市维护建设税、教育费附加、地方教育附加和销售营业税组成，征收税率为5.6%。\n" +
//                "\n" +
//                "个人购买超过5年(含5年)的普通住宅对外销售的，免征营业税。\n" +
//                "\n" +
//                "3、个税(买方支付)\n" +
//                "\n\n\n" +
//                "核定征收方式：应纳个人所得税计税价格×1%(或1.5%、3%)，我市个人住房转让个人所得税核定征收率标准为：普通住房1%，非普通住房或非住宅类房产为1.5%，拍卖房产为3%。\n" +
//                "\n" +
//                "对于个人转让自用5年以上、并且是家庭唯一住宅，免征个人所得税。\n" +
//                "\n" +
//                "4、印花税(买卖双方各0.05%)\n" +
//                "\n" +
//                "印花税是针对合同或者具有合同性质的凭证，产权转移书据，营业账簿，权利、许可证照和经财政部确定征税的其他凭证所收的一类税费");
//        System.out.println("2018年,\n\n\n20   19年,我都在四川");
//        System.out.println(s);
//        String s = removeSpecialCode("测试数据{VIEWLIST},我都在quot四川");
//        System.out.println(s);
//        String s = similarPage("测试数据大空打击萨克达", "测试数据,cx大个空打官府撒大苏打撒.", 2);
//        System.out.println(s);



    }
    /**
     * 命中敏感词给出标注
     * @param str 被检测语句
     * @return  List<String> 处理后包含的敏感词
     */
    public static List<String> aimLabel(String str){
        List<String> result = new ArrayList<>() ;
        for (String word : labelWords) {
            if(str.contains(word)){
                result.add(word);
            }
        }
        return result;
    }


    /**
     * 删除个人信息
     * （1）将回复中的律师个人信息删除（致电xx律师；xx律师；电联；详情加微信；微信同号：；联系方式；电话；博客链接等）
     * （2）将回复中的除了gov政府类的链接，其他的；链接进行删除；
     * （3）将回复中含有竞品信息的进行删除
     *  找法网，法律快车，律师365，大律师网，法邦网，法妞，百度百科，百度知道，百度知识图谱，律图等等
     * @param str 待处理字符串
     * @return String 处理好的字符串
     */
    public static String removeSelfInfo(String str){
        //URL
        String reGex= "(https?|ftp|file)://[-\\w+&@#/%=~|?!:,.;]+[-\\w+&@#/%=~|]";
        String infoGex = "致电.*律师|电联[0-9]|详情加微信[a-zA-Z0-9]|微信同号[a-zA-Z0-9]|联系方式[0-9]|电话[0-9]";
        String complateGex = "找法网|法律快车|法妞问答|律师365|大律师网|法邦网|法妞|百度百科|百度知道|百度知识图谱|律图|365律师|百度反诈骗联盟团队|知道合伙人|法律365";
        Matcher matcher = Pattern.compile(reGex,Pattern.CASE_INSENSITIVE).matcher(str);
        while (matcher.find()){
            String group = matcher.group();
            if(!group.contains(".gov")){
                str = str.replaceAll(matcher.group(),"");
            }
        }
        str = str.replaceAll(infoGex,"");
        str = str.replaceAll(complateGex,"");
        return str;

    }

    public static boolean titleContainsWords(String title){
        String complateGex = "司法解释全文|法全文|解释|批复|新规|施行|两会|经济普查数据|两高|司法解释|出台法律政策|草案|审稿|修正|中华人民共和国|修订|法";
        Matcher matcher = Pattern.compile(complateGex,Pattern.CASE_INSENSITIVE).matcher(title);
        return matcher.matches();
    }
    /**
     * 敏感词替换/删除
     * eg:草泥马-->**
     * 中华人名共和国-->中华人民共和国
     * 瞎子--->盲人
     * 非党人士-->删除
     * @param str 被检测语句
     * @return String 处理后的语句
     */
    public static String replaceSensitiveWords(String str){
        for (Map.Entry<String, String> entry : dict.entrySet()) {
            if(str.contains(entry.getKey())){
                str = str.replaceAll(entry.getKey(),entry.getValue());
            }
        }
        return str;

    }

    /**
     * 处理字符串包含代码
     * @param strWithCode
     * @return
     */
    public static String removeSpecialCode(String strWithCode){
        return strWithCode.replaceAll("[e-zE-Z+|@|#|&|=|δ|α|￥|*|~|\\[|\\]|／|\\/|<br>|○|{VIEWLIST}|+|-|—|quot|amlt|nbsp|rn]","");
    }

    /**
     * 比较两个字符串的相似部分并返回相似部分
     * eg :
     * content:我想知道是否有任何库,方法或最佳实践会返回,我哪些字符串与其他字符串更相似的字符串
     * reply:我想知道是否有几何库,方法和最佳实践会返回,以上是我的总结
     * fault:2
     * 返回:我想知道是否有几何库,方法和最佳实践会返回
     * @param content 去匹配的字符串
     * @param replay 被比较的字符串
     * @param fault 容错度
     * @return 相似部分
     */
    public static String similarPage(String content,String replay,int fault){
        char[] arrayReply = replay.toCharArray();
        char[] arrayCon = content.toCharArray();
        int index = 0;
        List<Character> list = new ArrayList<>();
        List<Character> listTemp = new ArrayList<>();
        a:for (char lawContent : arrayCon) {
            boolean contains = false;

            for (int i = index; i <= index + fault; i++) {
                if(i == replay.length()){
                    break a;
                }
                if(lawContent == arrayReply[i]){
                    if(listTemp.size()!=0){
                        list.addAll(listTemp);
                        listTemp.clear();
                    }
                    list.add(arrayReply[i]);
                    contains = true;
                    index = i + 1;
                    break ;
                }
                listTemp.add(arrayReply[i]);
            }
            if(!contains){
                break ;
            }

        }
        if(list.size() != 0){
            if(list.size() != replay.length()){
                Character character = list.get(list.size() - 1);
                while (!matchPunctuation(character)){
                    character = arrayReply[list.size()];
                    list.add(arrayReply[list.size()]);
                    if(list.size() == replay.length()){
                        break;
                    }
                }
            }
            StringBuilder strAppend = new StringBuilder();
            for (Character character : list) {
                strAppend.append(character);
            }
            return strAppend.toString();
        }
        return null;

    }
    private static boolean matchPunctuation(Character character){
        String c = character.toString();
        Matcher pattern = Pattern.compile("[?.,;!？，。；！]").matcher(c);
        return pattern.matches();
    }
    private static boolean matchPunctuationPage(Character character){
        String c = character.toString();
        Matcher pattern = Pattern.compile("[?.,;!？，。；！：:]").matcher(c);
        return pattern.matches();
    }
}
