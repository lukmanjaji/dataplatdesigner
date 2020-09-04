/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.xml;

/**
 *
 * @author Jaji
 */
 
import com.jajitech.desktop.xdata.constants.Constants;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
 
/**
 * @author Crunchify.com
 */
 
public class JAJFileWriter {
    
    DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder icBuilder;
 
    public JAJFileWriter()
    {
        
    }
    
    public boolean saveNewProject(String name, String location, String platform)
    {
        Constants c = new Constants();
        String uniqueID = c.randomAlphaNumeric(15);
        boolean result = false;
        
        try
        {
            if(!location.endsWith("\\"))
            {
                location = location + "\\";
            }
            location = location + Constants.APP_NAME+" Projects" + "\\";
            new File(location+name).mkdir();
            File file = new File(location +name+"\\"+name+".jaj");
            if(file.exists())
            {
                boolean b = c.alert(null, "confirm", "Project already exists in location. Replace?");
                if(b==false)
                {
                    return false;
                }
            }
            result = true;
        }
        catch(Exception er)
        {
            er.printStackTrace();
            result = false;
        }
        return result;
    }
 
    private Node getProjectInfo(Document doc, String uniqueID, String name, String location, String platform) {
        Element inforoot = doc.createElement("project-info");
        Element info = doc.createElement("project-info");
        info.appendChild(getCompanyElements(doc, info, "project-id", uniqueID));
        info.appendChild(getCompanyElements(doc, info, "project-name", name));
        info.appendChild(getCompanyElements(doc, info, "project-location", location));
        info.appendChild(getCompanyElements(doc, info, "project-target", platform));
        inforoot.appendChild(info);
        return info;
    }
 
    private Node getCompanyElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    
    public String getStringFromDoc(org.w3c.dom.Document doc)    {
    DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
    LSSerializer lsSerializer = domImplementation.createLSSerializer();
    return lsSerializer.writeToString(doc);   
}
}
