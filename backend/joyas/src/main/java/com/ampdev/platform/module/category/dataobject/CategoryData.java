package com.ampdev.platform.module.category.dataobject;

import com.ampdev.platform.module.category.constants.CategoryConstants;
import com.ampdev.platform.module.common.dataobject.PersistedDataObject;

import javax.persistence.*;


@NamedQueries({@NamedQuery(name = CategoryConstants.QUERY_GET_ALL_CATEGORIES, query = "from CategoryData"),
        @NamedQuery(name = CategoryConstants.QUERY_GET_CATEGORIES, query = "from CategoryData where id in (:ids)"),
        @NamedQuery(name = CategoryConstants.QUERY_DELTE_CATEGORY, query = "delete from CategoryData where id= :id")})
@Entity
@Table(name = "ci_categories")
public class CategoryData extends PersistedDataObject {

    /**
     * Generated serial version id
     */
    private static final long serialVersionUID = -7812405339777738148L;

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private long id;

    @Column(name = "name")
    private String categoryName;

    //TODO Cretae Language Data and join it
    @Column(name = "cat_language")
    private int language;

    @Column(name ="description")
    private String description;

    @Column(name = "parent_id")
    private int parentId;

    @Column(name = "meta_title")
    private String metaTitle;

    @Column(name = "meta_desc")
    private String metaDesc;

    @Column(name = "meta_keywords")
    private String metaKeywords;

    @Column(name = "order_id")
    private int orderId;

    public CategoryData() {
    }

    public CategoryData(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    @Override
    public final long getDataId() {
        return id;
    }

    public final String getCategoryName() {
        return categoryName;
    }

    public final void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public final void setId(long id) {
        this.id = id;
    }


}
