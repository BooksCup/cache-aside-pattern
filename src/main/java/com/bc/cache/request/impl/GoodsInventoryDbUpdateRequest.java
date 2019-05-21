package com.bc.cache.request.impl;

import com.bc.cache.entity.GoodsInventory;
import com.bc.cache.request.Request;
import com.bc.cache.service.GoodsInventoryService;

public class GoodsInventoryDbUpdateRequest implements Request {

    private GoodsInventory goodsInventory;

    private GoodsInventoryService goodsInventoryService;

    public GoodsInventoryDbUpdateRequest(GoodsInventory goodsInventory,
                                         GoodsInventoryService goodsInventoryService) {
        this.goodsInventory = goodsInventory;
        this.goodsInventoryService = goodsInventoryService;
    }

    @Override
    public void process() {
        System.out.println("数据库更新请求开始执行，商品id: " + goodsInventory.getGoodsId()
                + ", 商品库存数量: " + goodsInventory.getInventoryCount());
        // 删除redis中的缓存
        goodsInventoryService.removeGoodsInventoryCache(goodsInventory);
        // 为了模拟演示先删除了redis中的缓存，然后还没更新数据库的时候，读请求过来了，这里可以人工sleep一下
//		try {
//			Thread.sleep(20000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
        // 修改数据库中的库存
        goodsInventoryService.updateGoodsInventory(goodsInventory);

    }

    @Override
    public Integer getGoodsId() {
        return goodsInventory.getGoodsId();
    }
}
