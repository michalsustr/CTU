package rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;


public class RSSFeedParser {
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String CHANNEL = "channel";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";
	static final String LINK = "link";
	static final String AUTHOR = "author";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String GUID = "guid";

	final URL url;

	public RSSFeedParser(String feedUrl) {
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("null")

	public ArrayList<Post> readFeed() {
		ArrayList<Post> feed= new ArrayList<Post>();
		try {

			boolean isFeedHeader = true;
			// Set header values intial to the empty string
			String description="";
			String title="";
			String link="";
			String pubdateunp = "";
			Date pubdate = null;

			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = read();
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// Read the XML document
			while (eventReader.hasNext()) {

				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					if (event.asStartElement().getName().getLocalPart() == (ITEM)) {
						if (isFeedHeader) {
							isFeedHeader = false;
						}
						event = eventReader.nextEvent();
						continue;
					}

					if (event.asStartElement().getName().getLocalPart() == (TITLE)) {
						event = eventReader.nextEvent();
						title = event.asCharacters().getData();
						continue;
					}
					if (event.asStartElement().getName().getLocalPart() == (DESCRIPTION)) {
						event = eventReader.nextEvent();
						description= event.asCharacters().getData();
						continue;
					}

					if (event.asStartElement().getName().getLocalPart() == (LINK)) {
						event = eventReader.nextEvent();
						link= event.asCharacters().getData();
						continue;
					}
					if (event.asStartElement().getName().getLocalPart() == (PUB_DATE)) {
						event = eventReader.nextEvent();
						pubdateunp= event.asCharacters().getData();
						SimpleDateFormat formatovac = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
						try {
							pubdate = formatovac.parse(pubdateunp);
						} catch (ParseException ex) {
							Logger.getLogger(RSSFeedParser.class.getName()).log(Level.SEVERE, null, ex);
						}

						continue;
					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
						Post post = new Post(
							null,
							title,
							link,
							description,
							pubdate
						);
						feed.add(post);

						event = eventReader.nextEvent();
						continue;
					}
				}
			}
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
		return feed;

	}

	private InputStream read() {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}