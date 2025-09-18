package com.amstech.std.system.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the cuisine database table.
 * 
 */
@Entity
@NamedQuery(name="Cuisine.findAll", query="SELECT c FROM Cuisine c")
public class Cuisine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	//bi-directional many-to-one association to Restaurant
	@ManyToOne
	private Restaurant restaurant;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="cuisine")
	private List<Item> items;

	public Cuisine() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Restaurant getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Item addItem(Item item) {
		getItems().add(item);
		item.setCuisine(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setCuisine(null);

		return item;
	}

}