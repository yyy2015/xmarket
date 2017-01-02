package me.jcala.xmarket.server.admin.init;

import java.util.ArrayList;
import java.util.List;

/**
 * 学校名字列表的初始化数据
 */
public class SchoolInitData {

    private final List<String> schoolList=new ArrayList<>();

    public SchoolInitData(){
        addSchool();
    }

    private void addSchool(){
        schoolList.add("南京大学");
        schoolList.add("东南大学");
        schoolList.add("南京师范大学");
        schoolList.add("南京工业大学");
    }

    public List<String> getSchoolList(){
        return this.schoolList;
    }
}
