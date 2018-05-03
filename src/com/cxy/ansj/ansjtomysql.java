package com.cxy.ansj;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import com.cxy.jdbc.jdbc;

import java.util.*;

//根据解析出来的词把他们的三个属性导入到数据库里
class User{
    private String text2;
    public User(String text2){//构造方法
        this.text2=text2;
    }
    //set 方法传字符串s
    public void setT(String text2){
        this.text2=text2;
    }
    public String getT(){
        return text2;
    }
}
public class ansjtomysql {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
              //只关注这些词性的词
            Set<String> expectedNature = new HashSet<String>() {{
            add("n");
        }};
        

        String str = inputword();
        Result result = ToAnalysis.parse(str); //分词结果的一个封装，主要是一个List<Term>的terms
        //System.out.println(result.getTerms());

        List<Term> terms = result.getTerms(); //拿到term
        System.out.println(terms.size());//长度
        //寻找实际长度
        int j = 0;
        for(int i=0; i<terms.size(); i++) {
            String natureStr = terms.get(i).getNatureStr(); //拿到词性
            if(expectedNature.contains(natureStr)) {
            	j++;
            }
        }
        String params[][] = new String[j][4];
        j = 0;
        for(int i=0; i<terms.size(); i++) {
            String word = terms.get(i).getName(); //拿到词
            String natureStr = terms.get(i).getNatureStr(); //拿到词性
            if(expectedNature.contains(natureStr)) {
            	Thread.currentThread().sleep(100);
                System.out.println(i+" : "+word + ":" + natureStr);
               String[] info = json.jsontoString(json.getHttpResponse(word));
                //输出测试
                System.out.println(i+" : "+word+"*"+info[0]+"*"+info[1]+"*"+info[2]);
                //装入params
                params[j][0] = word;params[j][1] = info[0];params[j][2] = info[1];params[j][3] = info[2];j++;
            }
        }
        //jdbc.insertsql(params);//执行插入
    }
    public static String inputword() throws Exception{
        OPCPackage opcPackage = POIXMLDocument.openPackage(ansjtomysql.class.getClassLoader().getResource("2.docx").getFile());
        POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
        String text2 = extractor.getText();
        return text2;
    }
}