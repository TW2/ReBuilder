package org.wingate.rebuilder.widget;

import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ReFrame extends WidgetAbstract<JFrame> {

    private Dimension previewParentSize;
    private Dimension previewDimension;

    public ReFrame() {
        name = "JFrame";
        widget = new JFrame();
        previewParentSize = null;
        previewDimension = new Dimension(700, 600);
    }

    public Dimension getPreviewParentSize() {
        return previewParentSize;
    }

    public void setPreviewParentSize(Dimension previewParentSize) {
        this.previewParentSize = previewParentSize;
    }

    public Dimension getPreviewDimension() {
        return previewDimension;
    }

    public void setPreviewDimension(Dimension previewDimension) {
        this.previewDimension = previewDimension;
    }

    @Override
    public void draw(Graphics2D g) {
        if(previewParentSize != null){
            g.setColor(new Color(233, 233, 233));
            double x = (previewParentSize.getWidth() - previewDimension.getWidth()) / 2;
            double y = (previewParentSize.getHeight() - previewDimension.getHeight()) / 2;
            g.fill(new Rectangle2D.Double(
                    x, y - 30d,
                    previewDimension.getWidth(), previewDimension.getHeight() + 30d
            ));

            g.setColor(new Color(50, 50, 50));
            g.draw(new Rectangle2D.Double(
                    x, y - 30d,
                    previewDimension.getWidth(), previewDimension.getHeight() + 30d
            ));
            g.draw(new Line2D.Double(x, y, x + previewDimension.getWidth(), y));

            g.setColor(new Color(100, 100, 255));
            g.fill(new Ellipse2D.Double(
                    x + previewDimension.getWidth() - 20, y - 20, 10, 10
            ));
            g.fill(new Ellipse2D.Double(
                    x + previewDimension.getWidth() - 40, y - 20, 10, 10
            ));
            g.fill(new Ellipse2D.Double(
                    x + previewDimension.getWidth() - 60, y - 20, 10, 10
            ));
        }
    }

    // XML
    public static void saveXML(String path, ReFrame data){
        try {
            XMLReader myReader = new ReFrameReader();
            InputSource mySource = new ReFrameSource(data);
            Source source = new SAXSource(myReader, mySource);
            Result result = new StreamResult(new File(path));

            TransformerFactory fac = TransformerFactory.newInstance();
            Transformer transformer = fac.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public static ReFrame readXML(String path) {
        try {
            SAXParserFactory fac = SAXParserFactory.newInstance();
            SAXParser parser = fac.newSAXParser();
            ReFrameHandler handler = new ReFrameHandler();
            parser.parse(new File(path), handler);
            return handler.getData();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String saveXMLToText(ReFrame data){
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()){
            XMLReader myReader = new ReFrameReader();
            InputSource mySource = new ReFrameSource(data);
            Source source = new SAXSource(myReader, mySource);
            Result result = new StreamResult(os);

            TransformerFactory fac = TransformerFactory.newInstance();
            Transformer transformer = fac.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(source, result);

            return os.toString(StandardCharsets.UTF_8);
        } catch (TransformerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ReFrame readXMLFromText(String text){
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        try(ByteArrayInputStream in = new ByteArrayInputStream(data)){
            SAXParserFactory fac = SAXParserFactory.newInstance();
            SAXParser parser = fac.newSAXParser();
            ReFrameHandler handler = new ReFrameHandler();
            parser.parse(in, handler);
            return handler.getData();
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    // Reader
    public static class ReFrameHandler extends DefaultHandler {

        // Get
        private ReFrame data;
        // Inline variables
        private String cWidth, cHeight, pWidth, pHeight;
        // Inner content (between '>' and '<')
        private StringBuffer buffer;

        public ReFrameHandler(){
            super();
        }

        public ReFrame getData() {
            return data;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            switch(qName){
                case "data" -> data = new ReFrame();
                case "dimension" -> {
                    buffer = new StringBuffer();
                    cWidth = attributes.getValue("width");
                    cHeight = attributes.getValue("height");
                    pWidth = attributes.getValue("pw");
                    pHeight = attributes.getValue("ph");
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            switch(qName){
                case "dimension" -> {
                    // Real dimensions
                    int w = Integer.parseInt(cWidth);
                    int h = Integer.parseInt(cHeight);
                    data.getWidget().setSize(w, h);
                    // Preview dimensions
                    w = Integer.parseInt(pWidth);
                    h = Integer.parseInt(pHeight);
                    data.setPreviewDimension(new Dimension(w, h));

                    buffer = null;
                }
            }
        }

        @Override
        public void characters(char[] ch,int start, int length) {
            String r = new String(ch, start, length);
            if(buffer != null) {
                buffer.append(r);
            }
        }
    }

    // Writer
    public static class ReFrameSource extends InputSource {
        private final ReFrame data;

        public ReFrameSource(ReFrame data) {
            this.data = data;
        }

        public ReFrame getData() {
            return data;
        }
    }

    // Writer (required by)
    public static class ReFrameReader implements XMLReader {

        /*
        <data>
            <dimension width= height= pw= ph= />
        </data>
         */

        private ContentHandler chandler;
        private final AttributesImpl attributes = new AttributesImpl();
        private final Map<String,Boolean> features = new HashMap<>();
        private final Map<String,Object> properties = new HashMap<>();
        private EntityResolver resolver;
        private DTDHandler dhandler;
        private ErrorHandler ehandler;

        @Override
        public boolean getFeature(String name) {
            return features.get(name);
        }

        @Override
        public void setFeature(String name, boolean value) {
            try{
                features.put(name, value);
            }catch(Exception _){
            }
        }

        @Override
        public Object getProperty(String name) {
            return properties.get(name);
        }

        @Override
        public void setProperty(String name, Object value) {
            try{
                properties.put(name, value);
            }catch(Exception _){
            }
        }

        @Override
        public void setEntityResolver(EntityResolver resolver) {
            this.resolver = resolver;
        }

        @Override
        public EntityResolver getEntityResolver() {
            return resolver;
        }

        @Override
        public void setDTDHandler(DTDHandler handler) {
            this.dhandler = handler;
        }

        @Override
        public DTDHandler getDTDHandler() {
            return dhandler;
        }

        @Override
        public void setContentHandler(ContentHandler handler) {
            this.chandler = handler;
        }

        @Override
        public ContentHandler getContentHandler() {
            return chandler;
        }

        @Override
        public void setErrorHandler(ErrorHandler handler) {
            this.ehandler = handler;
        }

        @Override
        public ErrorHandler getErrorHandler() {
            return ehandler;
        }

        @Override
        public void parse(InputSource input) throws SAXException {

            ReFrameSource source = (ReFrameSource)input;
            ReFrame data = source.getData();
            ContentHandler ch = getContentHandler();

            // Main element - beginning
            ch.startDocument();
            ch.startElement("", "data", "data", attributes);
            attributes.clear();

            // TemplateGen element - beginning
            String width = Integer.toString(data.getWidget().getWidth());
            String height = Integer.toString(data.getWidget().getHeight());
            String pw = Integer.toString((int)data.getPreviewDimension().getWidth());
            String ph = Integer.toString((int)data.getPreviewDimension().getHeight());
            attributes.addAttribute("", "", "width", "width", width);
            attributes.addAttribute("", "", "height", "height", height);
            attributes.addAttribute("", "", "pw", "pw", pw);
            attributes.addAttribute("", "", "ph", "ph", ph);
            ch.startElement("", "dimension", "dimension", attributes);
            attributes.clear();

            // TemplateGen element - end
            ch.endElement("", "dimension", "dimension");
            attributes.clear();

            // Main element - end
            ch.endElement("", "data", "data");
            ch.endDocument();
        }

        @Override
        public void parse(String systemId) {

        }
    }
}
