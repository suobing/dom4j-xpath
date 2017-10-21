import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4JUtils {

	public static Document getDocument(String filename) throws DocumentException {
		SAXReader sr = new SAXReader();// 1.����һ��SaxReader
		Document document = sr.read(filename);// 2. ��ȡһ��xml�ļ����õ����xml�ļ���Document����.
		return document;
	}

	@Deprecated
	public static boolean isNodeExists(Document document, String pattern){
        List<Node> nodeList = getNodes(document, pattern);
        if(nodeList.size() != 0){
        	return true;
        }
        return false;
	}
	@Deprecated	
	public static List<Node> getNodes(Document document, String pattern){
		return document.selectNodes(pattern);
	}
	@Deprecated	
	public static Node getNode(Document document, String pattern){
		return document.selectSingleNode(pattern);
	}
	
    public static void deleteNode(Document document, String pattern) {
	    Node node = document.selectSingleNode(pattern);
	    Element parrent = node.getParent();
	    parrent.remove(node);
    }
    
    /***
     * ����<InstallationPath>
     * @param document
     * @param path
     */
    public static Element addInstallationPath(Document document, DxsInstallInfo pojo){
    	Element root = document.getRootElement();
    	Element newInstallationPath = root.addElement("InstallationPath");
    	newInstallationPath.addAttribute("path", pojo.getInstallPath());
    	
    	return newInstallationPath;
    }
    
    /***
     * ����<DxsNode>���ӽڵ�
     * @param installationPath
     * @param pojo
     */
    public static void addDxsNode(Element installationPath, DxsInstallInfo pojo){
    	Element newDxsNode = installationPath.addElement("DxsNode");
    	newDxsNode.addAttribute("systemid", pojo.getSystemId());
    	
    	newDxsNode.addElement("Version").addText(pojo.getVersion());
    	newDxsNode.addElement("EnvironmentID").addText(pojo.getEnvironmentId());
    	newDxsNode.addElement("CLIP").addText(pojo.getClIp());
    	newDxsNode.addElement("InstallTime").addText(pojo.getInstallTime());
    	
    }
    
    /***
     * ����<DxsNode>���ӽڵ�
     * @param dxsNode
     * @param pojo
     */
    public static void updateDxsNode(Element dxsNode, DxsInstallInfo pojo){
    	dxsNode.element("Version").setText(pojo.getVersion());
    	dxsNode.element("EnvironmentID").setText(pojo.getEnvironmentId());
    	dxsNode.element("CLIP").setText(pojo.getClIp());
    	dxsNode.element("InstallTime").setText(pojo.getInstallTime());
    }
    
	public static void documentToXml(Document document, String filename)
			throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint(); // ��ʽ �����xml
		format.setEncoding("UTF-8");
		format.setIndent("    "); 

		// OutputFormat format = OutputFormat.createCompactFormat(); // �޸�ʽ����
		XMLWriter writer = new XMLWriter(new FileOutputStream(filename), format);
		writer.write(document);
		writer.close();
	}
}
