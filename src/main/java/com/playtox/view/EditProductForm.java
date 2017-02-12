package com.playtox.view;

import com.playtox.dao.ProductDAO;
import com.playtox.entity.ProductEntity;
import com.playtox.entity.UserEntity;

public class EditProductForm extends AddProductPage {

    public EditProductForm(ProductDAO productDAO, UserEntity userEntity) {
        super(productDAO, userEntity);
    }

    @Override
    protected void saveProduct(ProductEntity productEntity) {
        productDAO.update(productEntity);
    }
}
