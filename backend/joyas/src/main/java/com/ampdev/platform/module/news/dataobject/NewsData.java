package com.ampdev.platform.module.news.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.news.constants.NewsConstants;

import javax.persistence.*;


@NamedQueries({@NamedQuery(name = NewsConstants.QUERY_GET_ALL_NEWS, query = "from NewsData"),
        @NamedQuery(name = NewsConstants.QUERY_GET_NEWS, query = "from NewsData where id in (:ids)"),
        @NamedQuery(name = NewsConstants.QUERY_DELTE_NEWS, query = "delete from NewsData where id= :id")})
@Entity
@Table(name = "ci_news")
public class NewsData extends PersistedDataObject {
    @Id
    @GeneratedValue
    @Column(name = "news_id")
    private long id;


    @Column(name = "date")
    //  @Temporal(TemporalType.TIMESTAMP)
    private String date;

    @Column(name = "title")
    private String title;

    //TODO Create a NewsPaperData and join
    @Column(name = "newspaper_id_fk")
    private Integer newsPaperId;

    @Column(name = "article_link")
    private String articleLink;

    @Column(name = "image")
    private String image;

    @Column(name = "thumb_image")
    private String thumbImage;

    @Column(name = "summary")
    private String summary;

    @Column(name = "long_summary")
    private String longSummary;

    //TODO Create a CategoryData and join
    @Column(name = "category_id")
    private Integer categoryId;

    //TODO Create a UPSCSnippertData and join
    @Column(name = "upsc_snippet_id")
    private String upscSnippetId;

    //TODO Create a SubCategoryData and join
    @Column(name = "subcategory_id")
    private Integer subCategoryId;

    @Column(name = "language")
    private Integer language;

    @Column(name = "like_cnt")
    private Integer likeCount;

    @Column(name = "dislike_cnt")
    private Integer dislikeCount;

    @Column(name = "index_rename")
    private String indexRename;

    @Column(name = "index_slug")
    private String indexSlug;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "is_active")

    private String isActive;

    @Column(name = "is_approved")

    private String isApproved;

    @Column(name = "is_disapproved")

    private String isDisapproved;

    @Column(name = "added_date")
//    @Temporal(TemporalType.TIMESTAMP)
    private String adddedDate;

    @Column(name = "is_deleted")

    private String isDeleted;

    @Column(name = "is_upsc")

    private String isUpsc;

    public NewsData() {
    }

    @Override
    public final long getDataId() {
        return id;
    }

    public final void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNewsPaperId() {
        return newsPaperId;
    }

    public void setNewsPaperId(Integer newsPaperId) {
        this.newsPaperId = newsPaperId;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLongSummary() {
        return longSummary;
    }

    public void setLongSummary(String longSummary) {
        this.longSummary = longSummary;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getUpscSnippetId() {
        return upscSnippetId;
    }

    public void setUpscSnippetId(String upscSnippetId) {
        this.upscSnippetId = upscSnippetId;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(Integer dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public String getIndexRename() {
        return indexRename;
    }

    public void setIndexRename(String indexRename) {
        this.indexRename = indexRename;
    }

    public String getIndexSlug() {
        return indexSlug;
    }

    public void setIndexSlug(String indexSlug) {
        this.indexSlug = indexSlug;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getIsDisapproved() {
        return isDisapproved;
    }

    public void setIsDisapproved(String isDisapproved) {
        this.isDisapproved = isDisapproved;
    }

    public String getAdddedDate() {
        return adddedDate;
    }

    public void setAdddedDate(String adddedDate) {
        this.adddedDate = adddedDate;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsUpsc() {
        return isUpsc;
    }

    public void setIsUpsc(String isUpsc) {
        this.isUpsc = isUpsc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsData newsData = (NewsData) o;

        if (id != newsData.id) return false;
        if (date != null ? !date.equals(newsData.date) : newsData.date != null) return false;
        if (title != null ? !title.equals(newsData.title) : newsData.title != null) return false;
        if (newsPaperId != null ? !newsPaperId.equals(newsData.newsPaperId) : newsData.newsPaperId != null)
            return false;
        if (articleLink != null ? !articleLink.equals(newsData.articleLink) : newsData.articleLink != null)
            return false;
        if (image != null ? !image.equals(newsData.image) : newsData.image != null) return false;
        if (thumbImage != null ? !thumbImage.equals(newsData.thumbImage) : newsData.thumbImage != null) return false;
        if (summary != null ? !summary.equals(newsData.summary) : newsData.summary != null) return false;
        if (longSummary != null ? !longSummary.equals(newsData.longSummary) : newsData.longSummary != null)
            return false;
        if (categoryId != null ? !categoryId.equals(newsData.categoryId) : newsData.categoryId != null) return false;
        if (upscSnippetId != null ? !upscSnippetId.equals(newsData.upscSnippetId) : newsData.upscSnippetId != null)
            return false;
        if (subCategoryId != null ? !subCategoryId.equals(newsData.subCategoryId) : newsData.subCategoryId != null)
            return false;
        if (language != null ? !language.equals(newsData.language) : newsData.language != null) return false;
        if (likeCount != null ? !likeCount.equals(newsData.likeCount) : newsData.likeCount != null) return false;
        if (dislikeCount != null ? !dislikeCount.equals(newsData.dislikeCount) : newsData.dislikeCount != null)
            return false;
        if (indexRename != null ? !indexRename.equals(newsData.indexRename) : newsData.indexRename != null)
            return false;
        if (indexSlug != null ? !indexSlug.equals(newsData.indexSlug) : newsData.indexSlug != null) return false;
        if (userId != null ? !userId.equals(newsData.userId) : newsData.userId != null) return false;
        if (isActive != null ? !isActive.equals(newsData.isActive) : newsData.isActive != null) return false;
        if (isApproved != null ? !isApproved.equals(newsData.isApproved) : newsData.isApproved != null) return false;
        if (isDisapproved != null ? !isDisapproved.equals(newsData.isDisapproved) : newsData.isDisapproved != null)
            return false;
        if (adddedDate != null ? !adddedDate.equals(newsData.adddedDate) : newsData.adddedDate != null) return false;
        if (isDeleted != null ? !isDeleted.equals(newsData.isDeleted) : newsData.isDeleted != null) return false;
        return isUpsc != null ? isUpsc.equals(newsData.isUpsc) : newsData.isUpsc == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (newsPaperId != null ? newsPaperId.hashCode() : 0);
        result = 31 * result + (articleLink != null ? articleLink.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (thumbImage != null ? thumbImage.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (longSummary != null ? longSummary.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (upscSnippetId != null ? upscSnippetId.hashCode() : 0);
        result = 31 * result + (subCategoryId != null ? subCategoryId.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (likeCount != null ? likeCount.hashCode() : 0);
        result = 31 * result + (dislikeCount != null ? dislikeCount.hashCode() : 0);
        result = 31 * result + (indexRename != null ? indexRename.hashCode() : 0);
        result = 31 * result + (indexSlug != null ? indexSlug.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        result = 31 * result + (isApproved != null ? isApproved.hashCode() : 0);
        result = 31 * result + (isDisapproved != null ? isDisapproved.hashCode() : 0);
        result = 31 * result + (adddedDate != null ? adddedDate.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (isUpsc != null ? isUpsc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewsData{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", newsPaperId=" + newsPaperId +
                ", articleLink='" + articleLink + '\'' +
                ", image='" + image + '\'' +
                ", thumbImage='" + thumbImage + '\'' +
                ", summary='" + summary + '\'' +
                ", longSummary='" + longSummary + '\'' +
                ", categoryId=" + categoryId +
                ", upscSnippetId=" + upscSnippetId +
                ", subCategoryId=" + subCategoryId +
                ", language=" + language +
                ", likeCount=" + likeCount +
                ", dislikeCount=" + dislikeCount +
                ", indexRename='" + indexRename + '\'' +
                ", indexSlug='" + indexSlug + '\'' +
                ", userId=" + userId +
                ", isActive='" + isActive + '\'' +
                ", isApproved='" + isApproved + '\'' +
                ", isDisapproved='" + isDisapproved + '\'' +
                ", adddedDate='" + adddedDate + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                ", isUpsc='" + isUpsc + '\'' +
                '}';
    }
}
