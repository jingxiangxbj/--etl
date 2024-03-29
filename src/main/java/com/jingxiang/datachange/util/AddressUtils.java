package com.jingxiang.datachange.util;


import java.util.HashMap;
import java.util.Map;

public class AddressUtils {
    public static Map<String, String[]> model = new HashMap<>();

    static {
        model.put("北京市", new String[]{"北京市"});
        model.put("上海市", new String[]{"上海市"});
        model.put("天津市", new String[]{"天津市"});
        model.put("重庆市", new String[]{"重庆市"});
        model.put("黑龙江省", new String[]{"哈尔滨市", "齐齐哈尔市", "牡丹江市", "大庆市", "伊春市", "双鸭山市", "鹤岗市", "鸡西市", "佳木斯市", "七台河市", "黑河市", "绥化市", "大兴安岭市"});
        model.put("吉林省", new String[]{"长春市", "延边市", "吉林市", "白山市", "白城市", "四平市", "松原市", "辽源市", "大安市", "通化市"});
        model.put("辽宁省", new String[]{"沈阳市", "大连市", "葫芦岛市", "旅顺市", "本溪市", "抚顺市", "铁岭市", "辽阳市", "营口市", "阜新市", "朝阳市", "锦州市", "丹东市", "鞍山市"});
        model.put("内蒙古自治区", new String[]{"呼和浩特市", "呼伦贝尔市", "锡林浩特市", "包头市", "赤峰市", "海拉尔市", "乌海市", "鄂尔多斯市", "通辽市"});
        model.put("河北省", new String[]{"石家庄市", "唐山市", "张家口市", "廊坊市", "邢台市", "邯郸市", "沧州市", "衡水市", "承德市", "保定市", "秦皇岛市"});
        model.put("河南省", new String[]{"郑州市", "开封市", "洛阳市", "平顶山市", "焦作市", "鹤壁市", "新乡市", "安阳市", "濮阳市", "许昌市", "漯河市", "三门峡市", "南阳市", "商丘市", "信阳市", "周口市", "驻马店市"});
        model.put("山东省", new String[]{"济南市", "青岛市", "淄博市", "威海市", "曲阜市", "临沂市", "烟台市", "枣庄市", "聊城市", "济宁市", "菏泽市", "泰安市", "日照市", "东营市", "德州市", "滨州市", "莱芜市", "潍坊市"});
        model.put("山西省", new String[]{"太原市", "阳泉市", "晋城市", "晋中市", "临汾市", "运城市", "长治市", "朔州市", "忻州市", "大同市", "吕梁市"});
        model.put("江苏省", new String[]{"南京市", "苏州市", "昆山市", "南通市", "太仓市", "吴县市", "徐州市", "宜兴市", "镇江市", "淮安市", "常熟市", "盐城市", "泰州市", "无锡市", "连云港市", "扬州市", "常州市", "宿迁市"});
        model.put("安徽省", new String[]{"合肥市", "巢湖市", "蚌埠市", "安庆市", "六安市", "滁州市", "马鞍山市", "阜阳市", "宣城市", "铜陵市", "淮北市", "芜湖市", "毫州市", "宿州市", "淮南市", "池州市"});
        model.put("陕西省", new String[]{"西安市", "韩城市", "安康市", "汉中市", "宝鸡市", "咸阳市", "榆林市", "渭南市", "商洛市", "铜川市", "延安市"});
        model.put("宁夏回族自治区", new String[]{"银川市", "固原市", "中卫市", "石嘴山市", "吴忠市"});
        model.put("甘肃省", new String[]{"兰州市", "白银市", "庆阳市", "酒泉市", "天水市", "武威市", "张掖市", "甘南市", "临夏市", "平凉市", "定西市", "金昌市"});
        model.put("青海省", new String[]{"西宁市", "海北市", "海西市", "黄南市", "果洛市", "玉树市", "海东市", "海南市"});
        model.put("湖北省", new String[]{"武汉市", "宜昌市", "黄冈市", "恩施市", "荆州市", "神农架市", "十堰市", "咸宁市", "襄樊市", "孝感市", "随州市", "黄石市", "荆门市", "鄂州市"});
        model.put("湖南省", new String[]{"长沙市", "邵阳市", "常德市", "郴州市", "吉首市", "株洲市", "娄底市", "湘潭市", "益市市", "永州市", "岳阳市", "衡阳市", "怀化市", "韶山市", "张家界市"});
        model.put("浙江省", new String[]{"杭州市", "湖州市", "金华市", "宁波市", "丽水市", "绍兴市", "雁荡山市", "衢州市", "嘉兴市", "台州市", "舟山市", "温州市"});
        model.put("江西省", new String[]{"南昌市", "萍乡市", "九江市", "上饶市", "抚州市", "吉安市", "鹰潭市", "宜春市", "新余市", "景德镇市", "赣州市"});
        model.put("福建省", new String[]{"福州市", "厦门市", "龙岩市", "南平市", "宁德市", "莆田市", "泉州市", "三明市", "漳州市"});
        model.put("贵州省", new String[]{"贵阳市", "安顺市", "赤水市", "遵义市", "铜仁市", "六盘水市", "毕节市", "凯里市", "都匀市"});
        model.put("四川省", new String[]{"成都市", "泸州市", "内江市", "凉山市", "阿坝市", "巴中市", "广元市", "乐山市", "绵阳市", "德阳市", "攀枝花市", "雅安市", "宜宾市", "自贡市", "甘孜州市", "达州市", "资阳市", "广安市", "遂宁市", "眉山市", "南充市"});
        model.put("广东省", new String[]{"广州市", "深圳市", "潮州市", "韶关市", "湛江市", "惠州市", "清远市", "东莞市", "江门市", "茂名市", "肇庆市", "汕尾市", "河源市", "揭阳市", "梅州市", "中山市", "德庆市", "阳江市", "云浮市", "珠海市", "汕头市", "佛山市"});
        model.put("广西壮族自治区", new String[]{"南宁市", "桂林市", "阳朔市", "柳州市", "梧州市", "玉林市", "桂平市", "贺州市", "钦州市", "贵港市", "防城港市", "百色市", "北海市", "河池市", "来宾市", "崇左市"});
        model.put("云南省", new String[]{"昆明市", "保山市", "楚雄市", "德宏市", "红河市", "临沧市", "怒江市", "曲靖市", "思茅市", "文山市", "玉溪市", "昭通市", "丽江市", "大理市"});
        model.put("海南省", new String[]{"海口市", "三亚市", "儋州市", "琼山市", "通什市", "文昌市"});
        model.put("西藏自治区",new String[]{"拉萨市","日喀则市","昌都市","林芝市","山南市","那曲市"});
        model.put("新疆维吾尔自治区", new String[]{"乌鲁木齐市", "阿勒泰市", "阿克苏市", "昌吉市", "哈密市", "和田市", "喀什市", "克拉玛依市", "石河子市", "塔城市", "库尔勒市", "吐鲁番市", "伊宁市"});
    }

    public static String eltAddress(String address) {
        String resultAddress = address;
        try {
            if (address != null) {
                if (!address.matches(RegexUtils.addressRegex)) {
                    if (address.matches(RegexUtils.addressRegex2)) {
                        for (String key : model.keySet()
                                ) {
                            String[] value = model.get(key);
                            for (String s : value
                                    ) {
                                if (s.equals(address)) {
                                    StringBuilder stringBuilder = new StringBuilder(key);
                                    StringBuilder append = stringBuilder.append(address);
                                    resultAddress = append.toString();
                                    return resultAddress;
                                }
                            }
                        }

                    }
                } else {
                    return resultAddress;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultAddress;
    }
}

