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

	public static Document getDocument(String filename)
			throws DocumentException {
		// 1.����һ��SaxReader
		SAXReader sr = new SAXReader();
		// 2. ��ȡһ��xml�ļ����õ����xml�ļ���Document����.
		Document document = sr.read(filename);

		return document;
	}

	public boolean isNodeExists(Document document ,String pattern){
        List<Node> nodeList = document.selectNodes(pattern);
        if(nodeList.size() != 0){
        	return true;
        }
        return false;
	}
	
    public static void deleteDesignatedElement(Document document, String pattern) {
	    Node node = document.selectSingleNode(pattern);
	    Element parrent=node.getParent();
	    parrent.remove(node);
    }
    
	public static void documentToXml(Document document, String filename)
			throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint(); // ��ʽ �����xml
		format.setEncoding("UTF-8");
		format.setIndent("    "); 

		// OutputFormat format = OutputFormat.createCompactFormat(); // �޸�ʽ����
		// xml
		XMLWriter writer = new XMLWriter(new FileOutputStream(filename), format);
		writer.write(document);
		writer.close();
	}
}
