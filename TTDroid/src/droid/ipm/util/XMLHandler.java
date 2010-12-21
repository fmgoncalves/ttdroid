package droid.ipm.util;
 
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
 
public class XMLHandler extends DefaultHandler{
 
        // ===========================================================
        // Fields
        // ===========================================================
       
        private boolean in_directionsresponse = false;
        private boolean in_route = false;
        private boolean in_leg = false;
        private boolean in_step = false;
        private boolean in_distance = false;
        private boolean in_text = false;
       
        private PathInformation myParsedDataSet = new PathInformation();
 
        // ===========================================================
        // Getter & Setter
        // ===========================================================
 
        public PathInformation getParsedData() {
                return this.myParsedDataSet;
        }
 
        // ===========================================================
        // Methods
        // ===========================================================
        @Override
        public void startDocument() throws SAXException {
                this.myParsedDataSet = new PathInformation();
        }
 
        @Override
        public void endDocument() throws SAXException {
                // Nothing to do
        }
 
        /** Gets be called on opening tags like:
         * <tag>
         * Can provide attribute(s), when xml was like:
         * <tag attribute="attributeValue">*/
        @Override
        public void startElement(String namespaceURI, String localName,
                        String qName, Attributes atts) throws SAXException {
                if (localName.equals("DirectionsResponse")) {
                        this.in_directionsresponse = true;
                }else if (localName.equals("route")) {
                        this.in_route = true;
                }else if (localName.equals("leg")) {
                        this.in_leg = true;
                }else if (localName.equals("step")) {
                    this.in_step = true;
                }else if (localName.equals("distance")) {
                    this.in_distance = true;
                }else if (localName.equals("text")) {
                    this.in_text = true;
                }
        }
       
        /** Gets be called on closing tags like:
         * </tag> */
        @Override
        public void endElement(String namespaceURI, String localName, String qName)
                        throws SAXException {
        		if (localName.equals("DirectionsResponse")) {
        			this.in_directionsresponse = false;
        		}else if (localName.equals("route")) {
        			this.in_route = false;
        		}else if (localName.equals("leg")) {
        			this.in_leg = false;
        		}else if (localName.equals("step")) {
        			this.in_step = false;
        		}else if (localName.equals("distance")) {
        			this.in_distance = false;
        		}else if (localName.equals("text")) {
        			this.in_text = false;
        		}
        }
       
        /** Gets be called on the following structure:
         * <tag>characters</tag> */
        @Override
    public void characters(char ch[], int start, int length) {
        if(this.in_distance && !this.in_step && this.in_text){
        	System.out.println(new String(ch, start, length));
               myParsedDataSet.setDistance(new String(ch, start, length));
        }
    }
}