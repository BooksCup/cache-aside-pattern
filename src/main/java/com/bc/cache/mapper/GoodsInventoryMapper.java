package com.bc.cache.mapper;

import com.bc.cache.entity.GoodsInventory;

/**
 * 库存数量Mapper
 *
 * @author zhou
 */
public interface GoodsInventoryMapper {

    /**
     * 更新库存数量
     *
     * @param goodsInventory 商品库存
     */
    void updateGoodsInventory(GoodsInventory goodsInventory);

    /**
     * 根据商品id查询商品库存信息
     *
     * @param goodsId 商品id
     * @return 商品库存信息
     */
    GoodsInventory findGoodsInventory(Integer goodsId);
}
