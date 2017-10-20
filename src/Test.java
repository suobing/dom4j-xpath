import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.Node;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			Document doc = Dom4JUtils.getDocument("cfg/test.xml");
			
			bar(doc);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

    
    public static void bar(Document document) {
        List<Node> list = document.selectNodes("//DxsNode");

//        Element stuElem = document.getRootElement().element("student");
        
        for(Node node: list){
        	Element element = (Element) node;
        	System.out.println(element.attributeValue("systemid"));
        	System.out.println(element.getParent().attributeValue("path"));
        }
        
        /*****************************************/
        
        String xmlpath =  "/DxsStore/InstallationPath[@path='/home/imt2/']/DxsNode[@systemid='RDI']";

        List<Node> list2= document.selectNodes(xmlpath);
        
        System.out.println(list2 == null);
        System.out.println(list2.size());

//        if(node!=null){
//        	
//        	System.out.println(node.getName());
//        	System.out.println(((Element)node).element("EnvironmentID").getText());
//        }else{
//        	System.out.println("Пе");
//        }
        
        
        Dom4JUtils.deleteDesignatedElement(document,xmlpath);
        
        String newNodePath = "/DxsStore/InstallationPath";
        Element root = document.getRootElement().addElement(newNodePath);
        
        try {
			Dom4JUtils.documentToXml(document, "cfg/result.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
//        Node node = (Node) document.selectSingleNode("//foo/bar/author");
//
//        String name = node.valueOf("@name");
    }
}
