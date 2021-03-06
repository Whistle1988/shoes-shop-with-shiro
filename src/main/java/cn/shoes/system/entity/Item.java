package cn.shoes.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Data
@ToString
@Table(name = "tb_item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;

    /**
     * 图片
     */
    private String picture;

    private String title;

    /**
     * 价格
     */
    private Double price;

    /**
     * 上架时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    @Transient
    private String timeField;

    public Item(String type, String picture, String title, Double price, Date time, String timeField) {
        this.type = type;
        this.picture = picture;
        this.title = title;
        this.price = price;
        this.time = time;
        this.timeField = timeField;
    }

    public Item() {
    }
}