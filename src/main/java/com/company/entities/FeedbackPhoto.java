package com.company.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "feedback_photo")
public class FeedbackPhoto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "photo_id")
	private Long photoId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feedback_id")
	private Feedback feedback;

	@Lob
	@Column(name = "photo")
	private byte[] photo;

	public FeedbackPhoto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FeedbackPhoto(Long photoId, Feedback feedback, byte[] photo) {
		super();
		this.photoId = photoId;
		this.feedback = feedback;
		this.photo = photo;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

}