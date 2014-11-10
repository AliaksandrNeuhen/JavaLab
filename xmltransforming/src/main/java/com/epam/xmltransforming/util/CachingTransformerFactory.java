package com.epam.xmltransforming.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.TransformerFactoryImpl;

public class CachingTransformerFactory 
				extends TransformerFactoryImpl{
	private static Map<String, TemplatesCacheEntry> templatesCache = 
			new HashMap<String, TemplatesCacheEntry>();
	
	@Override
	public Transformer newTransformer(final Source source) 
			throws TransformerConfigurationException {
		
		// Check if source is a stream source
		if (source instanceof StreamSource) {
			try {
				// Create URI of the source
				String sourceSystemId = source.getSystemId();
				final URI uri = new URI(sourceSystemId);
				// If URI points to the file, load transformer from
				// the file or from the cache
				String uriScheme = uri.getScheme();
				if ("file".equalsIgnoreCase(uriScheme)) {
					File xsltFile = new File(uri);
					Transformer resultTransformer = newTransformer(xsltFile);
					return resultTransformer;
				}
			} catch (URISyntaxException e) {
				throw new TransformerConfigurationException(e);
			}
		}
		return super.newTransformer(source);
	}
	
	protected Transformer newTransformer(final File file) 
			throws TransformerConfigurationException{
		String fileAbsolutePath = file.getAbsolutePath();
		// Search the cache for the templates entry
		TemplatesCacheEntry templatesCacheEntry = read(file.getAbsolutePath());
		// Check the timestamp of modification
		if ((templatesCacheEntry != null) && (templatesCacheEntry.lastModified < 
				templatesCacheEntry.templatesFile.lastModified())) {
			templatesCacheEntry = null;
		}
		// If no entries was found or this entry was obsolete
		if (templatesCacheEntry == null) {
			  System.out.println("Loading transformation [" + fileAbsolutePath + "].");
			// If this file doesn't exist, throw an exception
			if (!file.exists()) {
				throw new TransformerConfigurationException("Requested transformation [" + 
						fileAbsolutePath + "] doesn't exist");
			}
			
			// Create new cache entry
			Source templatesSource = new StreamSource(file);
			Templates templates = newTemplates(templatesSource);
			templatesCacheEntry = new TemplatesCacheEntry(templates, file);
			
			// Save this entry to the cache
			write(fileAbsolutePath, templatesCacheEntry);
		} else {
			System.out.println("Using cached transformation [" + fileAbsolutePath + "].");
		}
		Transformer resultTransformer = templatesCacheEntry.templates.newTransformer();
		return resultTransformer;
	}
	
	protected TemplatesCacheEntry read(String absolutePath) {
		TemplatesCacheEntry templatesCacheEntry = templatesCache.get(absolutePath);
		return templatesCacheEntry;
	}
	
	protected void write (String absolutePath, TemplatesCacheEntry templatesCacheEntry) {
		templatesCache.put(absolutePath, templatesCacheEntry);
	}
	
	private class TemplatesCacheEntry
	{
	  /** When was the cached entry last modified. */
	  private long lastModified;

	  /** Cached templates object. */
	  private Templates templates;

	  /** Templates file object. */
	  private File templatesFile;

	  /**
	   * Constructs a new cache entry.
	   * @param templates templates to cache.
	   * @param templatesFile file, from which this transformer was loaded.
	   */
	  private TemplatesCacheEntry(final Templates templates, final File templatesFile)
	  {
	    this.templates = templates;
	    this.templatesFile = templatesFile;
	    this.lastModified = templatesFile.lastModified();
	  }
	}
}
