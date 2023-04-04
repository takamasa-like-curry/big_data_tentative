package com.example.domain;

import java.util.List;

/**
 * DOM操作を実行するために必要な情報を扱うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
public class pageInfo {

	private List<Item> itemList;
	private Integer thisPage;
	private Integer totalPage;

	@Override
	public String toString() {
		return "pageInfo [itemList=" + itemList + ", thisPage=" + thisPage + ", totalPage=" + totalPage + "]";
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public Integer getThisPage() {
		return thisPage;
	}

	public void setThisPage(Integer thisPage) {
		this.thisPage = thisPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

}
