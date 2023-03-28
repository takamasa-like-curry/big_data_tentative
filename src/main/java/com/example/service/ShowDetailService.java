package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.repository.ItemsRepository;

@Service
public class ShowDetailService {

	@Autowired
	private ItemsRepository itemsRepository;

	public Item showDetail(Integer itemId) {
		return itemsRepository.load(itemId);
	}
}
