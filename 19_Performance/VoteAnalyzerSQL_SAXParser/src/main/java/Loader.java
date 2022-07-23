import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Loader {

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        String fileName = "res/data-18M.xml";

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();

        parser.parse(new File(fileName), handler);

        DBConnection.executeMultiInsert();

        //Printing results

        DBConnection.printVoterCounts();

        System.out.println((System.currentTimeMillis() - start));
    }


}
