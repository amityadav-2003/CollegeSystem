package com.amstech.std.system.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the restaurant database table.
 * 
 */
@Entity
@NamedQuery(name="Restaurant.findAll", query="SELECT r FROM Restaurant r")
public class Restaurant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="city_id")
	private int cityId;

	private String email;

	@Lob
	private String location;

	@Lob
	@Column(name="mobile_number")
	private String mobileNumber;

	private String name;

	//bi-directional many-to-one association to Cuisine
	@OneToMany(mappedBy="restaurant")
	private List<Cuisine> cuisines;

	//bi-directional many-to-one association to Role
	@OneToMany(mappedBy="restaurant")
	private List<Role> roles;

	public Restaurant() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCityId() {
		return this.cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Cuisine> getCuisines() {
		return this.cuisines;
	}

	public void setCuisines(List<Cuisine> cuisines) {
		this.cuisines = cuisines;
	}

	public Cuisine addCuisine(Cuisine cuisine) {
		getCuisines().add(cuisine);
		cuisine.setRestaurant(this);

		return cuisine;
	}

	public Cuisine removeCuisine(Cuisine cuisine) {
		getCuisines().remove(cuisine);
		cuisine.setRestaurant(null);

		return cuisine;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Role addRole(Role role) {
		getRoles().add(role);
		role.setRestaurant(this);

		return role;
	}

	public Role removeRole(Role role) {
		getRoles().remove(role);
		role.setRestaurant(null);

		return role;
	}

}