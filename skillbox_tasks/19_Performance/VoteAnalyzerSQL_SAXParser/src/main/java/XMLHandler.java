import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class XMLHandler extends DefaultHandler {


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter")) {
                String birthday = attributes.getValue("birthDay");
                String name = attributes.getValue("name");
                DBConnection.countVoter(name, birthday);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}




