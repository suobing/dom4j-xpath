import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.Node;

public class Test {

	public static void main(String[] args) {
		try {
			Document doc = Dom4JUtils.getDocument("cfg/result.xml");
//			bar(doc);
			
			DxsInstallInfo pojo = new DxsInstallInfo();
			pojo.setInstallPath("/home/imt4/");
			pojo.setSystemId("CSTP");
			pojo.setVersion("V1.3.0");
			pojo.setEnvironmentId("UAT2");
			pojo.setClIp("192.1.1.1");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式			
			pojo.setInstallTime(df.format(new Date()));
			
			Element dxsNode = (Element) doc.selectSingleNode("/DxsStore/InstallationPath[@path='"+pojo.getInstallPath()+"']/DxsNode[@systemid='"+ pojo.getSystemId()+"']");
			//如果已安装（同路径，同系统），则覆盖			
			if(dxsNode != null){
				System.out.println("覆盖安装...");
				Dom4JUtils.updateDxsNode(dxsNode, pojo);
			}else {
				Element installPathNode = (Element)doc.selectSingleNode("/DxsStore/InstallationPath[@path='"+pojo.getInstallPath()+"']");
				if(installPathNode == null){//如果安装路径是新的
					System.out.println("创建新路径...");
					installPathNode = Dom4JUtils.addInstallationPath(doc, pojo);
				}
				System.out.println("创建新DxsNode...");
				Dom4JUtils.addDxsNode(installPathNode, pojo);
			}
			
	        try {
				Dom4JUtils.documentToXml(doc, "cfg/result.xml");
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
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
//        	System.out.println("空");
//        }
        
        
        Dom4JUtils.deleteNode(document,xmlpath);
        
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
