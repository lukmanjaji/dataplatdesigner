/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Jaji
 */
public class JAJFileReader {
    
    File file = null;
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = null;
    Document document = null;
    XPathFactory factory = XPathFactory.newInstance();
    XPath xPath = factory.newXPath();


    
    public JAJFileReader(File file)
    {
        this.file = file;
        try
        {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file.toString());
        }
        catch(ParserConfigurationException | SAXException | IOException er){}
    }
    
    public boolean updateElement(String id, String element, String newValue)
    {
        try
        {
        String expression = "/xdata/project-info[@id='"+id+"']";
        System.out.println(expression);
        Node node = (Node) xPath.compile(expression).evaluate(document, XPathConstants.NODE);
        if(null != node) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0;null!=nodeList && i < nodeList.getLength(); i++) {
                Node nod = nodeList.item(i);
                if(nod.getNodeType() == Node.ELEMENT_NODE)
                    System.out.println(nodeList.item(i).getNodeName() + " : " + nod.getFirstChild().getNodeValue()); 
            }
        }
        }
        catch(XPathExpressionException | DOMException e)
        {
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean createElement(List listElements, String tagHost, String newElement)
    {
        Element root = document.getDocumentElement();
        Element host =  (Element) root.getElementsByTagName(tagHost).item(0);
        Element newTag = document.createElement(newElement);
        for(Object x: listElements)
        {
            String fromList[] = x.toString().split("##@");
            String tag = fromList[0];
            String textContent = fromList[1];
            Element t = document.createElement(tag);
            t.setTextContent(textContent);
        }
        return true;
    }
    
    
    public void save()
    {
        try
        {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(domSource, streamResult);
        }
        catch(TransformerException e)
        {
            
        }
    }
    
    
    
}
