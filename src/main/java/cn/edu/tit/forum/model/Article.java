package cn.edu.tit.forum.model;

import lombok.Data;

//@Document(indexName = "index_article", type = "article")
@Data
public class Article {
//    @Id
    private Long id;

//    @Field(type = FieldType.Long)
    private Long authorId;

//    @Field(type = FieldType.text, analyzer = "ik_smart", store = true)
    private String title;

//    @Field(type = FieldType.text, analyzer = "ik_smart", store = true)
    private String content;

//    @Field(type = FieldType.text, analyzer = "ik_smart", store = true)
    private String tag;

//    @Field(type = FieldType.Integer)
    private Integer viewCount;

//    @Field(type = FieldType.Integer)
    private Integer likeCount;

//    @Field(type = FieldType.Integer)
    private Integer commentCount;

//    @Field(type = FieldType.Long)
    private Long gmtCreate;

//    @Field(type = FieldType.Long)
    private Long gmtModified;
}