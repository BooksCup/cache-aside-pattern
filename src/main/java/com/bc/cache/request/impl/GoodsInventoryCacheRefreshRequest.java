package com.bc.cache.request.impl;

import com.bc.cache.entity.GoodsInventory;
import com.bc.cache.request.Request;
import com.bc.cache.service.GoodsInventoryService;

public class GoodsInventoryCacheRefreshRequest implements Request {

    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 商品库存Service
     */
    private GoodsInventoryService goodsInventoryService;

    public GoodsInventoryCacheRefreshRequest(Integer goodsId,
                                             GoodsInventoryService goodsInventoryService) {
        this.goodsId = goodsId;
        this.goodsInventoryService = goodsInventoryService;
    }

    @Override
    public void process() {
        // 从数据库中查询最新的商品库存数量
        GoodsInventory goodsInventory = goodsInventoryService.findGoodsInventory(goodsId);
        System.out.println("已查询到商品最新的库存数量，商品id: " + goodsId
                + ", 商品库存数量: " + goodsInventory.getInventoryCount());
        // 将最新的商品库存数量，刷新到redis缓存中去
        goodsInventoryService.setGoodsInventoryCache(goodsInventory);
    }

    @Override
    public Integer getGoodsId() {
        return goodsId;
    }
}
