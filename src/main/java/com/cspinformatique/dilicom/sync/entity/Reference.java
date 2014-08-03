package com.cspinformatique.dilicom.sync.entity;

public class Reference {
	// Functionnal Data.
	private String title;
	private String standardLabel;
	private String author;
	private String shortCollection;
	private String publisherName;
	private String publisherIsbn;
	private String theme;
	private String publicationDate;

	// Technical info
	private String dilicomUrl;

	public Reference(String title, String standardLabel, String author,
			String shortCollection, String publisherName, String publisherIsbn,
			String theme, String publicationDate, String dilicomUrl) {
		this.title = title;
		this.standardLabel = standardLabel;
		this.author = author;
		this.shortCollection = shortCollection;
		this.publisherName = publisherName;
		this.publisherIsbn = publisherIsbn;
		this.theme = theme;
		this.publicationDate = publicationDate;
		this.dilicomUrl = dilicomUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStandardLabel() {
		return standardLabel;
	}

	public void setStandardLabel(String standardLabel) {
		this.standardLabel = standardLabel;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getShortCollection() {
		return shortCollection;
	}

	public void setShortCollection(String shortCollection) {
		this.shortCollection = shortCollection;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublisherIsbn() {
		return publisherIsbn;
	}

	public void setPublisherIsbn(String publisherIsbn) {
		this.publisherIsbn = publisherIsbn;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getDilicomUrl() {
		return dilicomUrl;
	}

	public void setDilicomUrl(String dilicomUrl) {
		this.dilicomUrl = dilicomUrl;
	}

}
