package com.cxy.ansj;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.pfunction.library.listIndex;
import org.apache.jena.vocabulary.VCARD;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class ansjtordf {
	public static void main(String[] args) throws Exception {
		ArrayList<String> list = readword();
		ArrayList<Member> father = new ArrayList<>();
		ArrayList<Model> listmodel = new ArrayList<>();
		for(String i:list) {
			father.add((new Member(i)));
		}
		int n = 0;
		for(Member i:father) {
			createtree(i);	
			listmodel.add(treetordf(i,ModelFactory.createDefaultModel()));
		}
		//System.out.println(father.get(0).getCa().get(2).getCa().get(0));
		listmodel.get(0).write(System.out, "RDF/XML-ABBREV");
//		System.out.println(((ArrayList<String>) m1[0]).isEmpty());
		
			
	}
	//递归的时候注意分类
   public static Model treetordf(Member i, Model model) {
	   Resource source = model.createResource("http://somewhere/"+i.getName()).addProperty(VCARD.NOTE, i.getDbsc());
		if(!i.getCa().isEmpty()) {
			for(Member j:i.getCa()) {
				if(j.getCa().isEmpty()) {
					if(j.getDbsc().length()!=0)
					 source = source.addProperty(VCARD.CATEGORIES,model.createResource("http://somewhere/"+j.getName()).addProperty(VCARD.NOTE, j.getDbsc()));
					else
						source = source.addProperty(VCARD.CATEGORIES,j.getName());
				}else {
					if(j.getDbsc().length()!=0)
						source.addProperty(VCARD.CATEGORIES,model.createResource("http://somewhere/"+j.getName()).addProperty(VCARD.NOTE, j.getDbsc()));
					else
						source.addProperty(VCARD.CATEGORIES,model.createResource("http://somewhere/"+j.getName()));
				    treetordf(j,model);
			}
		}
			}
		return model;
		}

		
	//该方法将word中读到的词保存成List
	public static ArrayList<String> readword() throws Exception{
		ArrayList<String> list = new ArrayList<>();
		 //只关注这些词性的词
        Set<String> expectedNature = new HashSet<String>() {{
        add("n");
    }};
    

    String str = inputword();
    Result result = ToAnalysis.parse(str); //分词结果的一个封装，主要是一个List<Term>的terms
    List<Term> terms = result.getTerms(); //拿到term
  
    for(int i=0; i<terms.size(); i++) {
        String word = terms.get(i).getName(); //拿到词
        String natureStr = terms.get(i).getNatureStr(); //拿到词性
        if(expectedNature.contains(natureStr)) {
        	System.out.println(word);
            list.add(word);
        }
    }
		return list;
	}
	//该方法解析word
	public static String inputword() throws Exception{
        OPCPackage opcPackage = POIXMLDocument.openPackage(ansjtomysql.class.getClassLoader().getResource("2.docx").getFile());
        POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
        String text2 = extractor.getText();
        return text2;
    }
	//该方法构建类结构 返回重构的父类
	public static void createtree(Member m) throws Exception {
		ArrayList<Member> father = new ArrayList<>();
			Thread.currentThread().sleep(100);
			Object ob[] = json.jsontoclass(json.getHttpResponse(m.getName()), m.getName());
			ArrayList<Member> ca = new ArrayList<>();
			for(String j:(ArrayList<String>)ob[0]) {
				ca.add(new Member(j));				
			}
			m.setCa(ca);
			m.setDbsc(ob[1].toString());
			System.out.println(ob[0].toString());
			if(!(((ArrayList<String>) ob[0]).isEmpty())) {
				for(Member j:ca ) {
					createtree(j);
				}
			}		
	}
}
