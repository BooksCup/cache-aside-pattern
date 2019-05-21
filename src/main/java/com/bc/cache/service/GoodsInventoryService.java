package com.bc.cache.service;

import com.bc.cache.entity.GoodsInventory;

public interface GoodsInventoryService {
    void updateGoodsInventory(GoodsInventory goodsInventory);

    void removeGoodsInventoryCache(GoodsInventory goodsInventory);

    GoodsInventory findGoodsInventory(Integer goodsId);
    /**
     * 设置商品库存的缓存
     * @param goodsInventory 商品库存
     */
    void setGoodsInventoryCache(GoodsInventory goodsInventory);

    /**
     * 获取商品库存的缓存
     *
     * @param goodsId 商品ID
     * @return 商品库存缓存
     */
    GoodsInventory getGoodsInventoryCache(Integer goodsId);
}
