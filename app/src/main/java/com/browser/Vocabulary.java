package com.browser;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * @author Anurag Gautam
 */
public class Vocabulary {
    private static Vocabulary instance = null;
    private final Context context;
    private Document document;
    private XPath xPath;


    public static Vocabulary getInstance(Context context) {
        if (instance == null) {
            instance = new Vocabulary(context);
        }
        return instance;
    }

    private Vocabulary(Context context) {
        this.context = context;

        // Lets load the vocabulary
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            document = documentBuilder.parse(context.openFileInput(context.getString(R.string.vocabulary_file_name)));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        xPath = XPathFactory.newInstance().newXPath();
    }

    public ArrayList<String> query(CharSequence path, CharSequence constraint) {
        ArrayList<String> hints = new ArrayList<>();
        if (xPath != null) {
            try {
                XPathExpression compile = xPath.compile("vocabulary/" + path + "*[contains(@value, '" + constraint + "')]");
                if (compile != null) {
                    Object result = compile.evaluate(document, XPathConstants.NODESET);
                    NodeList resultantNodes = (NodeList) result;
                    for (int i = 0; i < resultantNodes.getLength(); i++) {
                        Node node = resultantNodes.item(i);
                        if (node != null) {
                            NamedNodeMap attrs = node.getAttributes();
                            if (attrs != null) {
                                Node value = attrs.getNamedItem("value");
                                if (value != null) {
                                    hints.add(value.getNodeValue());
                                }
                            }
                        }
                    }
                }
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
        }
        return hints;
    }
}
