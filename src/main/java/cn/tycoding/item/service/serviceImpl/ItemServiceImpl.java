package cn.tycoding.item.service.serviceImpl;

import cn.tycoding.common.service.impl.BaseServiceImpl;
import cn.tycoding.item.controller.Model.ItemModel;
import cn.tycoding.item.mapper.ItemColorMapper;
import cn.tycoding.item.mapper.ItemMapper;
import cn.tycoding.item.mapper.ItemSizeMapper;
import cn.tycoding.item.service.ItemService;
import cn.tycoding.system.entity.Item;
import cn.tycoding.system.entity.ItemColor;
import cn.tycoding.system.entity.ItemSize;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author YoSaukit
 * @date 2019/11/18 20:49
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemColorMapper itemColorMapper;
    @Autowired
    private ItemSizeMapper itemSizeMapper;

    @Override
    public List<Item> getItemByFields(Item item) {
        try {
            Example example = new Example(Item.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(item.getTitle())){
                criteria.andCondition("title=",item.getTitle());
            }
            if (StringUtils.isNotBlank(item.getType())){
                criteria.andCondition("type=",item.getType());
            }
            if (StringUtils.isNotBlank(item.getTimeField())){
                String[] split = item.getTimeField().split(",");
                criteria.andCondition("time >=", split[0]);
                criteria.andCondition("time <=", split[1]);
            }
            example.setOrderByClause("time desc");
            return this.selectByExample(example);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void add(ItemModel itemModel) {
        Item item = new Item();
        BeanUtils.copyProperties(itemModel,item);
        this.save(item);
        String[] colorList = itemModel.getColor().split(",");
        String[] sizeList = itemModel.getSize().split(",");
        for (String color :
                colorList) {
            ItemColor itemColor = new ItemColor(item.getId(), color);
            itemColorMapper.insert(itemColor);
        }
        for (String size :
                sizeList) {
            ItemSize itemSize = new ItemSize(item.getId(), size);
            itemSizeMapper.insert(itemSize);
        }

    }
    @Override
    public ItemModel findById(int id) {
        ItemModel itemModel = new ItemModel();
        Item item = itemMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(item,itemModel);
        Example example = new Example(ItemColor.class);
        example.createCriteria().andCondition("item_id=",id);
        List<ItemColor> itemColorList = itemColorMapper.selectByExample(example);
        String color = "";
        for (ItemColor itemColor:
             itemColorList) {
            color+=(itemColor.getColor())+",";
        }
        itemModel.setColor(color.substring(0,color.length()-1));

        Example example2 = new Example(ItemSize.class);
        example2.createCriteria().andCondition("item_id=",id);
        List<ItemSize> itemSizeList = itemSizeMapper.selectByExample(example2);
        String size = "";
        for (ItemSize itemSize :
                itemSizeList) {
            size+=(itemSize.getSize())+",";
        }
        itemModel.setSize(size.substring(0,size.length()-1));
        return itemModel;
    }

    @Override
    public void update(ItemModel itemModel){
        Item item = new Item();
        BeanUtils.copyProperties(itemModel,item);
        this.updateNotNull(item);
        String[] colorList = itemModel.getColor().split(",");
        String[] sizeList = itemModel.getSize().split(",");
        for (String color :
                colorList) {
            ItemColor itemColor = new ItemColor(itemModel.getId(), color);
            itemColorMapper.updateByExampleSelective(itemColor);
        }
        for (String size :
                sizeList) {
            ItemSize itemSize = new ItemSize(itemModel.getId(), size);
            itemSizeMapper.insert(itemSize);
        }
    }
}
