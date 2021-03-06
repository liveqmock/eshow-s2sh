package com.logo.eshow.webapp.action;

import com.logo.eshow.bean.query.ProductCategoryQuery;
import com.logo.eshow.common.page.Page;
import com.logo.eshow.model.ProductCategory;
import com.logo.eshow.service.ProductCategoryManager;
import com.logo.eshow.webapp.action.BaseAction;
import com.logo.eshow.service.ProductManager;
import com.logo.eshow.util.PageUtil;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

@Results({ @Result(name = "input", location = "add"),
		@Result(name = "list", type = "redirect", location = ""),
		@Result(name = "success", type = "redirect", location = "view/${id}"),
		@Result(name = "redirect", type = "redirect", location = "${redirect}") })
public class ProductCategoryAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ProductCategoryManager productCategoryManager;
	@Autowired
	private ProductManager productManager;
	private List<ProductCategory> productCategories;
	private ProductCategory productCategory;
	private ProductCategoryQuery query;

	public String list() {
		productCategories = productCategoryManager.list(query);
		return LIST;
	}

	public String search() {
		Page<ProductCategory> page = productCategoryManager.search(query);
		productCategories = page.getDataList();
		saveRequest("page", PageUtil.conversion(page));
		return LIST;
	}

	public void delete() {
		ProductCategory productCategory = productCategoryManager.get(id);
		if (productCategory != null) {
			productManager.update(productCategory.getId());// 设置原产品的分类为空
			productCategory.setEnabled(Boolean.TRUE);
			productCategoryManager.save(productCategory);
			success("删除成功");
		} else {
			failure("参数不正确");
		}
	}

	public String view() {
		if (id != null) {
			productCategory = productCategoryManager.get(id);
		}
		return NONE;
	}

	public String update() throws Exception {
		ProductCategory old = productCategoryManager.get(id);
		old.setName(productCategory.getName());
		old.setDescription(productCategory.getDescription());
		productCategoryManager.save(old);
		saveMessage("修改成功");
		return LIST;
	}

	public String save() throws Exception {
		productCategoryManager.save(productCategory);
		saveMessage("添加成功");
		return LIST;
	}

	public ProductCategoryManager getProductCategoryManager() {
		return productCategoryManager;
	}

	public void setProductCategoryManager(ProductCategoryManager productCategoryManager) {
		this.productCategoryManager = productCategoryManager;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public List<ProductCategory> getProductCategories() {
		return productCategories;
	}

	public void setProductCategories(List<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public ProductCategoryQuery getQuery() {
		return query;
	}

	public void setQuery(ProductCategoryQuery query) {
		this.query = query;
	}

}