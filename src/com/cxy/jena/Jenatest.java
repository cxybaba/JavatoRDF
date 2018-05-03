package com.cxy.jena;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class Jenatest {
	public static void main(String[] args) {
		// 声明变量
		String personURI    = "http://somewhere/JohnSmith";
		String givenName    = "John";
		String familyName   = "Smith";
		String fullName     = givenName + " " + familyName;
		// 创建一个空的模型
		Model model = ModelFactory.createDefaultModel();
		// 创建资源
		// 并且按照层叠式添加属性。
		// model.createResource() 创建了一个空白节点
		Resource johnSmith
		  = model.createResource(personURI)
		         .addProperty(VCARD.NAME, fullName)
		         .addProperty(VCARD.NAME,
		                      model.createResource()
		                           .addProperty(VCARD.NAME, givenName)
		                           .addProperty(VCARD.NAME, familyName));
		       // 声明变量
				String personURI1    = "http://somewhere/JohnSmith1";
				String givenName1    = "John";
				String familyName1  = "Smith";
				String fullName1     = givenName + " " + familyName;
				// 创建一个空的模型
				Model model1 = ModelFactory.createDefaultModel();
				// 创建资源
				// 并且按照层叠式添加属性。
				// model.createResource() 创建了一个空白节点
				model1.createResource(personURI1)
				         .addProperty(VCARD.NAME, fullName1)
				         .addProperty(VCARD.NAME,
				                      model1.createResource()
				                           .addProperty(VCARD.NAME, givenName1)
				                           .addProperty(VCARD.NAME, familyName1));
				// 声明变量
				String personURI2    = "http://somewhere/JohnSmith2";
				String givenName2    = "John";
				String familyName2  = "Smith";
				String fullName2     = givenName + " " + familyName;
				// 创建一个空的模型
				Model model2 = ModelFactory.createDefaultModel();
				// 创建资源
				// 并且按照层叠式添加属性。
				// model.createResource() 创建了一个空白节点  去遍历一层一层的add
				model2.createResource(personURI2)
				         .addProperty(VCARD.NAME, fullName2)
				         .addProperty(VCARD.NAME,
				                      model2.createResource()
				                           .addProperty(VCARD.NAME, givenName2)
				                           .addProperty(VCARD.NAME, familyName2));
			//合并
//			Model models = model.union(model1).union(model2);
		model.write(System.out, "RDF/XML-ABBREV");
		
	}
	
}