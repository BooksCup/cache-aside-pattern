package com.bc.cache.entity;

/**
 * 商品库存
 *
 * @author zhou
 */
public class GoodsInventory {
    /**
     * 商品ID
     */
    private Integer goodsId;

    /**
     * 库存数量
     */
    private Long inventoryCount;

    public GoodsInventory() {

    }

    public GoodsInventory(Integer goodsId, Long inventoryCount) {
        this.goodsId = goodsId;
        this.inventoryCount = inventoryCount;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Long getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(Long inventoryCount) {
        this.inventoryCount = inventoryCount;
    }
}
