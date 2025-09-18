package com.amstech.std.system.entity;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ItemPK id;

	@Lob
	@Column(name="is_spicy")
	private String isSpicy;

	private double quantity;

	//bi-directional many-to-one association to Cuisine
	@ManyToOne
	private Cuisine cuisine;

	public Item() {
	}

	public ItemPK getId() {
		return this.id;
	}

	public void setId(ItemPK id) {
		this.id = id;
	}

	public String getIsSpicy() {
		return this.isSpicy;
	}

	public void setIsSpicy(String isSpicy) {
		this.isSpicy = isSpicy;
	}

	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Cuisine getCuisine() {
		return this.cuisine;
	}

	public void setCuisine(Cuisine cuisine) {
		this.cuisine = cuisine;
	}

}