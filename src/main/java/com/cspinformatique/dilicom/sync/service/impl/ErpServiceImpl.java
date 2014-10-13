package com.cspinformatique.dilicom.sync.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.dilicom.sync.entity.Reference;
import com.cspinformatique.dilicom.sync.service.ErpService;
import com.cspinformatique.odoo.adapter.company.CompanyAdapter;
import com.cspinformatique.odoo.adapter.product.CategoryAdapter;
import com.cspinformatique.odoo.adapter.product.ProductAdapter;
import com.cspinformatique.odoo.adapter.product.TemplateAdapter;
import com.cspinformatique.odoo.adapter.product.UnitOfMeasureAdapter;
import com.cspinformatique.odoo.core.OdooConfig;
import com.cspinformatique.odoo.entity.core.Company;
import com.cspinformatique.odoo.entity.product.Category;
import com.cspinformatique.odoo.entity.product.Product;
import com.cspinformatique.odoo.entity.product.Template;
import com.cspinformatique.odoo.entity.product.Template.MeasureType;
import com.cspinformatique.odoo.entity.product.Template.State;
import com.cspinformatique.odoo.entity.product.UnitOfMeasure;
import com.cspinformatique.odoo.entity.product.Template.CostMethod;

@Service
public class ErpServiceImpl implements ErpService {
	@Autowired
	private CategoryAdapter categoryAdapter;
	
	@Autowired
	private CompanyAdapter companyAdapter;
	
	@Autowired
	private UnitOfMeasureAdapter unitOfMeasureAdapter;
	
	@Autowired
	private ProductAdapter productAdapter;
	
	@Autowired
	private TemplateAdapter templateAdapter;
	
	@Autowired
	private OdooConfig odooConfig;

	private Company company;
	private Category defaultCategory;
	private UnitOfMeasure defaultUnitOfMeasure;

	@PostConstruct
	public void init() {
		company = this.companyAdapter.findbyName(odooConfig.getCompanyName());

		defaultCategory = this.categoryAdapter.findById(1);
		defaultUnitOfMeasure = this.unitOfMeasureAdapter.findById(1);
	}

	@Override
	public void publishProductToOdoo(Reference reference) {
		Template template = new Template(0, // Integer id
				reference.getTitle(), // String Name
				reference.getStandardLabel(), // String description
				reference.getStandardLabel(), // String descriptionPurchase
				reference.getStandardLabel(), // String descriptionSale
				null, defaultCategory, // Category category
				reference.getPriceTaxIn(), // Double listPrice
				reference.getPriceTaxIn(), // Double standardPrice
				null, // Float volume
				null, // Float weight
				null, // Float weightNet
				CostMethod.standard, // CostMethod costMethod
				null, // Float warranty
				true, // Boolean saleOk
				State.sellable, // State state
				defaultUnitOfMeasure, // UnitOfMeasure unitOfMeasure
				defaultUnitOfMeasure, // UnitOfMeasure unitOfMeasurePurchase
				null, // Float unitOfSaleCoefficient
				MeasureType.fixed, // MeasureType measureType
				company // Company company
		);
		
		template.setId(templateAdapter.createEntity(template));

		Product product = new Product(0, reference.getPriceTaxIn(),
				reference.getPriceTaxIn(), "", true, template,
				reference.getEan13());
		
		productAdapter.createEntity(product);
	}

}
