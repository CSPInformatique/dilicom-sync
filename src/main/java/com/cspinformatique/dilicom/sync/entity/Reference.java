package com.cspinformatique.dilicom.sync.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.NestedField;

@Document(indexName = "reference")
public class Reference {
	@Id
	private String ean13;

	@MultiField(mainField = @Field(type = FieldType.String, index = FieldIndex.analyzed), otherFields = @NestedField(dotSuffix = "raw", type = FieldType.String, index = FieldIndex.not_analyzed))
	private String title;

	@Field(type=FieldType.String)
	private String standardLabel;

	@MultiField(mainField = @Field(type = FieldType.String, index = FieldIndex.analyzed), otherFields = @NestedField(dotSuffix = "raw", type = FieldType.String, index = FieldIndex.not_analyzed))
	private String author;

	@Field(type=FieldType.String)
	private String shortCollection;

	@MultiField(mainField = @Field(type = FieldType.String, index = FieldIndex.analyzed), otherFields = @NestedField(dotSuffix = "raw", type = FieldType.String, index = FieldIndex.not_analyzed))
	private String publisherName;

	@Field(type=FieldType.String)
	private String isbnOrShortTitle;

	@Field(type=FieldType.String)
	private String theme;

	@Field(type=FieldType.String)
	private String coverImageUrl;

	@Field(type=FieldType.Date)
	private Date publicationDate;

	@Field(type=FieldType.String)
	private String dilicomUrl;

	@Field(type=FieldType.Boolean)
	private boolean hided;

	@Field(type=FieldType.Boolean)
	private boolean loadedIntoErp;
	
	@Field(type=FieldType.Double)
	private Double priceTaxIn;

	@Field(type=FieldType.Double)
	private Double priceTaxOut;

	@Field(type=FieldType.Double)
	private Double taxAmount;

	public Reference() {

	}

	public Reference(String ean13, String title, String standardLabel,
			String author, String shortCollection, String publisherName,
			String isbnOrShortTitle, String theme, String coverImageUrl,
			Date publicationDate, String dilicomUrl, boolean hided,
			boolean loadedIntoErp, Double priceTaxIn, Double priceTaxOut,
			Double taxAmount) {
		this.ean13 = ean13;
		this.title = title;
		this.standardLabel = standardLabel;
		this.author = author;
		this.shortCollection = shortCollection;
		this.publisherName = publisherName;
		this.isbnOrShortTitle = isbnOrShortTitle;
		this.theme = theme;
		this.coverImageUrl = coverImageUrl;
		this.publicationDate = publicationDate;
		this.dilicomUrl = dilicomUrl;
		this.hided = hided;
		this.loadedIntoErp = loadedIntoErp;
		this.priceTaxIn = priceTaxIn;
		this.priceTaxOut = priceTaxOut;
		this.taxAmount = taxAmount;
	}

	public String getEan13() {
		return ean13;
	}

	public void setEan13(String ean13) {
		this.ean13 = ean13;
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

	public String getIsbnOrShortTitle() {
		return isbnOrShortTitle;
	}

	public void setIsbnOrShortTitle(String isbnOrShortTitle) {
		this.isbnOrShortTitle = isbnOrShortTitle;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getDilicomUrl() {
		return dilicomUrl;
	}

	public void setDilicomUrl(String dilicomUrl) {
		this.dilicomUrl = dilicomUrl;
	}

	public boolean isHided() {
		return hided;
	}

	public void setHided(boolean hided) {
		this.hided = hided;
	}

	public boolean isLoadedIntoErp() {
		return loadedIntoErp;
	}

	public void setLoadedIntoErp(boolean loadedIntoErp) {
		this.loadedIntoErp = loadedIntoErp;
	}

	public Double getPriceTaxIn() {
		return priceTaxIn;
	}

	public void setPriceTaxIn(Double priceTaxIn) {
		this.priceTaxIn = priceTaxIn;
	}

	public Double getPriceTaxOut() {
		return priceTaxOut;
	}

	public void setPriceTaxOut(Double priceTaxOut) {
		this.priceTaxOut = priceTaxOut;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

}
