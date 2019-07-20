package com.jingxiang.datachange;

import com.jingxiang.datachange.entity.*;
import com.jingxiang.datachange.mapper.EtlMapper;
import com.jingxiang.datachange.mapper.UserMapper;
import com.jingxiang.datachange.repository.EtlRepository;
import com.jingxiang.datachange.repository.InsertDataRepository;
import com.jingxiang.datachange.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatachangeApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;
    @Autowired
    private LogInfoService logInfoService;
    @Autowired
    private EsInfoService esInfoService;
@Autowired
private EtlRepository etlRepository;
@Autowired
private StudentInfoService studentInfoService;
@Autowired
private EtlMapper etlMapper;
@Autowired
private InsertDataRepository insertDataRepository;
@Autowired
private MailService mailService;
@Autowired
private UserMapper userMapper;

    @Test
    public void contextLoads() throws IOException, NoSuchAlgorithmException, ParseException {
        List<User> select = userMapper.select();
        System.out.println(select);
      //  redisTemplate.boundValueOps("new_key").set("new_value");
//        mailService.sendMail(new Email());
       // insertDataRepository.insertData();
       // esInfoService.getClusterInfo();
      //  List<Map<String, Object>> hour = esInfoService.getIndexDocChange("HOUR");
     //   System.out.println(hour);
//        StudentInfoResult studentInfoResult = new StudentInfoResult();
//studentInfoResult.setGender("");
//studentInfoResult.setName("");
//
////etlRepository.addStudentResult(studentInfoResult);
//        List<StudentInfoResult> allStudentResult1 = etlMapper.findStudentResult(studentInfoResult);
//        System.out.println(allStudentResult1);
//        StudentInfoResult studentInfoResult = new StudentInfoResult();
//        studentInfoResult.setName("zhangsan");
//        studentInfoResult.setGender("女");
//        Integer integer = etlRepository.addStudentResult(studentInfoResult);
//        System.out.println(integer);
//        StudentInfo studentInfo = new StudentInfo();
//        studentInfo.setName("aaa");
//        Integer integer1 = etlRepository.addStudent(studentInfo);
//        System.out.println(integer1);
//
//        List<StudentInfoResult> allStudentResult = etlRepository.findAllStudentResult(studentInfoResult);
//        System.out.println(allStudentResult);
//        List<StudentResultExt> studentResultExt = etlRepository.countHobby();
//        System.out.println(studentResultExt);

//        User superuser = userService.findByName("superuser");
//        MD5Util.validPassword("1234567",superuser.getPassword());
       //  logInfoService.findLogUseForBarChar("transform", "SUCCESS", "HOUR");
//        System.out.println(logUseForBarChar);
//        List<Map<String, Object>> indexStore = esInfoService.getIndexStore();
//        System.out.println(indexStore);
//        Map<String, Object> clusterInfo = esInfoService.getClusterInfo();
//        System.out.println(clusterInfo);
//        List<Map<String, Object>> indexStore = esInfoService.getIndexStore();
//        System.out.println(indexStore);
//Integer age = 0;
//        System.out.println("age"+age == null);
//       // System.out.println(NameUtils.getRandomName());
//        StudentInfo studentInfo = new StudentInfo();
//        System.out.println(studentInfo);
//        System.out.println(studentInfo.toString());
//        InsertData insertData = new InsertData();
//        insertData.insertData();
//        String address = "信阳市";
//        boolean matches = address.matches(RegexUteils.addressRegex);
//        System.out.println(matches);
//       String address = AddressUtils.eltAddress("河南省信阳市");

//        System.out.println("地址"+address);
//        String name = "";
//        int agee = 21;
//        if ( name.equals("rr") || agee==20)  {
//
//           // System.out.println(false);
//        }
//        else {
//           // System.out.println(true);
//        }
//        if (name==null || name.length() == 0 ){//&&还具有短路的功能，即如果第一个表达式为false，则不再计算第二个表达式。
//            System.out.println(name+"false");//||同样有类似短路的功能，即第一个条件若为true，则不计算后面的表达式。
//        }else {
//            System.out.println(name+"true");
//        }
//        String dateStr = DateUtils.dateStr2DateStr("2019/4/2");
//        String toyMd = DateUtils.dateStrToyMd("2019/4/5");
//        System.out.println(toyMd);
//        System.out.println("1111"+dateStr);
//        String s = DateUtils.getDateStrFromStr("2019/04/20");
//        System.out.println("3333"+s);
//    }
//    public static List<Map<String,String>> addressResolution(String address){
//        String regex="(?<province>[^省]+省|.+自治区)(?<city>[^自治州]+自治州|[^市]+市|[^盟]+盟|[^地区]+地区|.+区划)(?<county>[^市]+市|[^县]+县|[^旗]+旗|.+区)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
//        Matcher m=Pattern.compile(regex).matcher(address);
//        String province=null,city=null,county=null,town=null,village=null;
//        List<Map<String,String>> list=new ArrayList<Map<String,String>>();
//        Map<String,String> row=null;
//        while(m.find()){
//            row=new LinkedHashMap<String,String>();
//            province=m.group("province");
//            row.put("province", province==null?"":province.trim());
//            city=m.group("city");
//            row.put("city", city==null?"":city.trim());
//            county=m.group("county");
//            row.put("county", county==null?"":county.trim());
//            town=m.group("town");
//            row.put("town", town==null?"":town.trim());
//            village=m.group("village");
//            row.put("village", village==null?"":village.trim());
//            list.add(row);
//        }
//        return list;
    }
}
