package com.jingxiang.datachange.util;

import com.jingxiang.datachange.entity.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class InsertData {
    @Autowired
    //private UserInfoRepository userInfoRepository;

    @Scheduled(fixedRate = 100000)
    public void insertData() {
        String[] city = {
                "沈阳市", "大连市", "石家庄市", "唐山市", "张家口市", "廊坊市", "邢台市", "邯郸市", "沧州市", "衡水市",
                "保定市", "秦皇岛市", "郑州市", "开封市", "洛阳市", "平顶山市", "焦作市", "新乡市", "安阳市", "许昌市",
                "漯河市", "南阳市", "商丘市", "信阳市", "周口市", "驻马店市", "济南市", "青岛市", "临沂市", "烟台市",
                "枣庄市", "菏泽市", "日照市", "德州市", "潍坊市", "太原市", "晋城市", "晋中市", "临汾市", "运城市",
                "长治市", "大同市", "吕梁市", "南京市", "苏州市", "昆山市", "南通市", "徐州市", "常熟市", "盐城市",
                "无锡市", "扬州市", "常州市", "合肥市", "巢湖市", "蚌埠市", "安庆市", "六安市", "阜阳市", "芜湖市",
                "西安市", "韩城市", "安康市", "宝鸡市", "咸阳市", "榆林市", "延安市", "武汉市", "宜昌市", "黄冈市",
                "恩施市", "荆州市", "咸宁市", "襄樊市", "孝感市", "长沙市", "邵阳市", "常德市", "湘潭市", "永州市",
                "岳阳市", "张家界市", "杭州市", "湖州市", "金华市", "宁波市", "绍兴市", "嘉兴市", "台州市", "舟山市",
                "温州市", "成都市", "绵阳市", "雅安市", "南充市", "广州市", "深圳市", "东莞市", "珠海市", "汕头市", "佛山市"};
        int i = 0;
        while (i < 100) {
            StudentInfo studentInfo = new StudentInfo();
            String idCard = IdCardUtils.getRandomIdCard();
            studentInfo.setIdCard(idCard);
            studentInfo.setAge(IdCardUtils.getAge(idCard));
            studentInfo.setBirth(IdCardUtils.getBirthday(idCard));
            if(i%2==0){
                studentInfo.setGender("女");
            }else {
            studentInfo.setGender("男");
            }
            studentInfo.setMobile(PhoneUtils.getRandomPhone());
            studentInfo.setAddress(city[i]);
            studentInfo.setName(NameUtils.getName());
            System.out.println(studentInfo);
           //  userInfoRepository.addStudent(studentInfo);
            i++;
        }
    }
}
