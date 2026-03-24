package com.safevoice.model;

public class MemberVO {
	
	String email;
	String id;
	String pw;
	String name;
	String birthDate;//
	String phone;//
	String postcode;
	String address;
	String detailAddress;
	String gender;//
	String familyCode;
	String memType;
	String childId;
	String imagePath;
	String jsonSubscription;
	String google;

	public MemberVO(String email, String id, String pw, String name, String birthDate, String phone, String postcode,
			String address, String detailAddress, String gender, String familyCode, String memType, String google) {
		super();
		this.email = email;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.birthDate = birthDate;
		this.phone = phone;
		this.postcode = postcode;
		this.address = address;
		this.detailAddress = detailAddress;
		this.gender = gender;
		this.familyCode = familyCode;
		this.memType = memType;
		this.google = google;
	}
	
	public MemberVO() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFamilyCode() {
		return familyCode;
	}

	public void setFamilyCode(String familyCode) {
		this.familyCode = familyCode;
	}

	public String getMemType() {
		return memType;
	}

	public void setMemType(String memType) {
		this.memType = memType;
	}

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getJsonSubscription() {
	    return jsonSubscription;
	}

	public void setJsonSubscription(String jsonSubscription) {
	    this.jsonSubscription = jsonSubscription;
	}
	
	public String getGoogle() {
		return google;
	}

	public void setGoogle(String google) {
		this.google = google;
	}

}
