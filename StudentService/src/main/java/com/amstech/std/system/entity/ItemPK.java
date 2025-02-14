package com.amstech.std.system.entity;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the item database table.
 * 
 */
@Entity
public class ItemPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
    @Id
	private int id;

	private String name;

	public ItemPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ItemPK)) {
			return false;
		}
		ItemPK castOther = (ItemPK)other;
		return 
			(this.id == castOther.id)
			&& this.name.equals(castOther.name);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.name.hashCode();
		
		return hash;
	}
}