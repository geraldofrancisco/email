package com.thor.email.domain;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParserFactoryFixture extends SAXParserFactory {

  public static boolean throwOnFeature;

  @Override
  public SAXParser newSAXParser() {
    return new FixtureSaxParser();
  }

  @Override
  public void setFeature(String name, boolean value) {
    if (throwOnFeature) {
      throw new IllegalStateException(name);
    }
  }

  @Override
  public boolean getFeature(String name) {
    return false;
  }

  private static class FixtureSaxParser extends SAXParser {

    @Override
    public void parse(InputSource input, DefaultHandler handler) throws SAXException {
      handler.endElement("", "", "orphan");
    }

    @Override
    public org.xml.sax.Parser getParser() {
      return null;
    }

    @Override
    public XMLReader getXMLReader() {
      return null;
    }

    @Override
    public boolean isNamespaceAware() {
      return false;
    }

    @Override
    public boolean isValidating() {
      return false;
    }

    @Override
    public void setProperty(String name, Object value) throws SAXNotRecognizedException,
        SAXNotSupportedException {
      throw new SAXNotRecognizedException(name);
    }

    @Override
    public Object getProperty(String name) throws SAXNotRecognizedException,
        SAXNotSupportedException {
      throw new SAXNotRecognizedException(name);
    }
  }
}
